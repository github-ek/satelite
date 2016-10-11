package com.tacticlogistics.application.services.ordenes;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tacticlogistics.application.dto.common.MensajesDto;
import com.tacticlogistics.application.dto.etl.ETLLineaOrdenDto;
import com.tacticlogistics.application.dto.etl.ETLOrdenDto;
import com.tacticlogistics.domain.model.common.SeveridadType;
import com.tacticlogistics.domain.model.common.UsoUbicacionType;
import com.tacticlogistics.domain.model.common.valueobjects.Contacto;
import com.tacticlogistics.domain.model.common.valueobjects.Dimensiones;
import com.tacticlogistics.domain.model.common.valueobjects.OmsDireccion;
import com.tacticlogistics.domain.model.crm.Canal;
import com.tacticlogistics.domain.model.crm.Cliente;
import com.tacticlogistics.domain.model.crm.ClienteBodegaAssociation;
import com.tacticlogistics.domain.model.crm.Destinatario;
import com.tacticlogistics.domain.model.crm.Destino;
import com.tacticlogistics.domain.model.crm.TipoServicio;
import com.tacticlogistics.domain.model.geo.Ciudad;
import com.tacticlogistics.domain.model.oms.CausalAnulacion;
import com.tacticlogistics.domain.model.oms.EstadoOrdenType;
import com.tacticlogistics.domain.model.ordenes.LineaOrden;
import com.tacticlogistics.domain.model.ordenes.Orden;
import com.tacticlogistics.domain.model.seguridad.Usuario;
import com.tacticlogistics.domain.model.wms.Bodega;
import com.tacticlogistics.domain.model.wms.Producto;
import com.tacticlogistics.domain.model.wms.ProductoUnidadAssociation;
import com.tacticlogistics.domain.model.wms.Unidad;
import com.tacticlogistics.infrastructure.persistence.crm.CanalRepository;
import com.tacticlogistics.infrastructure.persistence.crm.ClienteRepository;
import com.tacticlogistics.infrastructure.persistence.crm.DestinatarioRepository;
import com.tacticlogistics.infrastructure.persistence.crm.DestinoRepository;
import com.tacticlogistics.infrastructure.persistence.crm.TipoServicioRepository;
import com.tacticlogistics.infrastructure.persistence.geo.CiudadRepository;
import com.tacticlogistics.infrastructure.persistence.oms.CausalAnulacionRepository;
import com.tacticlogistics.infrastructure.persistence.ordenes.OrdenRepository;
import com.tacticlogistics.infrastructure.persistence.seguridad.UsuarioRepository;
import com.tacticlogistics.infrastructure.persistence.wms.BodegaRepository;
import com.tacticlogistics.infrastructure.persistence.wms.ProductoRepository;
import com.tacticlogistics.infrastructure.services.Basic;

@Service
@Transactional(readOnly = true)
public class OrdenesApplicationService {
	@Autowired
	private OrdenRepository ordenRepository;
	@Autowired
	private TipoServicioRepository tipoServicioRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private CanalRepository canalRepository;
	@Autowired
	private DestinatarioRepository terceroRepository;
	@Autowired
	private DestinoRepository puntoRepository;
	@Autowired
	private CiudadRepository ciudadRepository;
	@Autowired
	private BodegaRepository bodegaRepository;
	@Autowired
	private ProductoRepository productoRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private CausalAnulacionRepository causalAnulacionRepository;

	// TODO HOMOLOGAR CODIGOS ALTERNOS DE SERVICIOS
	// TODO IMPLEMENTAR RGLAS DE NEGOCIO EN CONFIRMAR
	// TODO RETORNAR ERRORES DE NEGOCIO
	// TODO IMPLEMENTAR UN CACHE DE DATOS
	// TODO QUITAR LOS CODIGOS ALTERNO DE LAS LINEAS
	// TODO VALIDAR ERRORES DE LONGITUD DE CARACTERES POR MEDIO DE SPRING
	@Transactional(readOnly = false)
	public Orden saveOrdenDespachosSecundaria(ETLOrdenDto dto) throws DataAccessException {
		StringBuffer errores = new StringBuffer();

		TipoServicio tipoServicio = null;
		Cliente cliente = null;
		Ciudad ciudad = null;
		Destino punto = null;
		Canal canal = null;
		Destinatario tercero = null;

		cliente = clienteRepository.findByCodigoIgnoringCase(dto.getClienteCodigo());
		if (cliente == null) {
			throw new RuntimeException("(cliente == null)");
		}

		tipoServicio = homologarTipoServicio(cliente.getId(), dto.getTipoServicioCodigo(),
				dto.getTipoServicioCodigoAlterno());
		if (tipoServicio == null) {
			throw new RuntimeException("(tipoServicio == null)");
		}

		Orden model = checkOrdenExistente(dto, cliente.getId());
		if (model != null) {
			throw new RuntimeException("Ya existe una solicitud con el mismo numero");
		}

		// ------------------------------------------------------------------------------------------------
		String usuario = dto.getUsuarioConfirmacion();
		LocalDateTime fecha = LocalDateTime.now();

		ciudad = homologarCiudad(cliente.getId(), dto.getDestinoCiudadNombreAlterno());
		tercero = homologarTercero(dto, cliente.getId(), dto.getDestinatarioNumeroIdentificacion(), usuario);

		// ------------------------------------------------------------------------------------------------
		if (tercero != null) {
			canal = canalRepository.findOne(tercero.getCanalId());

			usarContactoPredeterminadoDelTercero(dto, tercero);
		}

		// ------------------------------------------------------------------------------------------------
		if (tercero != null && ciudad != null) {
			punto = homologarPunto(tercero.getId(), ciudad.getId(), dto.getDestinoDireccion(),
					dto.getDestinoIndicaciones(), dto.getDestinoCodigo(), dto.getDestinoNombre(),
					dto.getDestinoContacto(), usuario, errores);
		}

		if (punto != null) {
			usarContactoPredeterminadoDelPuntoDestino(dto, punto);
		}

		// ------------------------------------------------------------------------------------------------
		model = setDatosOrden(dto, cliente, tipoServicio, canal, tercero, ciudad, punto, null, null, usuario, fecha);

		// ------------------------------------------------------------------------------------------------
		for (ETLLineaOrdenDto linea : dto.getLineas()) {
			saveLineaOrden(dto, linea, model);
		}

		// ------------------------------------------------------------------------------------------------
		// TODO PASAR PARTE DE ESTE CODIGO A UN METODO DE SOLO TRASLADOS
		setDireccionesTraslados(tipoServicio, model);

		// ------------------------------------------------------------------------------------------------
		model.confirmar(usuario, fecha, dto.getNotasConfirmacion());
		model = ordenRepository.save(model);

		return model;
	}

	@Transactional(readOnly = false)
	public Orden saveOrdenReciboPrimaria(ETLOrdenDto dto) throws DataAccessException {
		StringBuffer errores = new StringBuffer();

		TipoServicio tipoServicio = null;
		Cliente cliente = null;
		Ciudad ciudad = null;
		Destino punto = null;
		Canal canal = null;
		Destinatario tercero = null;

		cliente = clienteRepository.findByCodigoIgnoringCase(dto.getClienteCodigo());
		if (cliente == null) {
			throw new RuntimeException("(cliente == null)");
		}

		tipoServicio = homologarTipoServicio(cliente.getId(), dto.getTipoServicioCodigo(),
				dto.getTipoServicioCodigoAlterno());
		if (tipoServicio == null) {
			throw new RuntimeException("(tipoServicio == null)");
		}

		Orden model = checkOrdenExistente(dto, cliente.getId());
		if (model != null) {
			throw new RuntimeException("Ya existe una solicitud con el mismo numero");
		}

		// ------------------------------------------------------------------------------------------------
		String usuario = dto.getUsuarioConfirmacion();
		LocalDateTime fecha = LocalDateTime.now();

		ciudad = homologarCiudad(cliente.getId(), dto.getOrigenCiudadNombreAlterno());
		tercero = homologarTercero(dto, cliente.getId(), dto.getDestinatarioNumeroIdentificacion(), usuario);

		// ------------------------------------------------------------------------------------------------
		if (tercero != null) {
			canal = canalRepository.findOne(tercero.getCanalId());

			usarContactoPredeterminadoDelTercero(dto, tercero);
		}

		// ------------------------------------------------------------------------------------------------
		if (tercero != null && ciudad != null) {
			punto = homologarPunto(tercero.getId(), ciudad.getId(), dto.getOrigenDireccion(),
					dto.getOrigenIndicaciones(), dto.getOrigenCodigo(), dto.getOrigenNombre(), dto.getOrigenContacto(),
					usuario, errores);
		}

		if (punto != null) {
			usarContactoPredeterminadoDelPuntoOrigen(dto, punto);
		}

		// ------------------------------------------------------------------------------------------------
		model = setDatosOrden(dto, cliente, tipoServicio, canal, tercero, null, null, ciudad, punto, usuario, fecha);

		// ------------------------------------------------------------------------------------------------
		for (ETLLineaOrdenDto linea : dto.getLineas()) {
			saveLineaOrden(dto, linea, model);
		}

		// ------------------------------------------------------------------------------------------------
		// TODO PASAR PARTE DE ESTE CODIGO A UN METODO DE SOLO TRASLADOS
		setDireccionesTraslados(tipoServicio, model);

		model.confirmar(usuario, fecha, dto.getNotasConfirmacion());
		model = ordenRepository.save(model);

		return model;
	}

	private void setDireccionesTraslados(TipoServicio tipoServicio, Orden model) {
		if (tipoServicio.isAdmiteBodegasComoDestino() && tipoServicio.isAdmiteBodegasComoOrigen()) {

			Optional<Bodega> bodega;

			bodega = model.getLineas().stream().map(a -> a.getBodegaOrigen()).filter(a -> a != null)
					.collect(Collectors.groupingBy(a -> a, Collectors.counting())).entrySet().stream()
					.max((entry1, entry2) -> (int) (entry1.getValue() - entry2.getValue())).map(a -> a.getKey());

			if (bodega.isPresent()) {
				OmsDireccion direccion = bodega.get().getDireccion();
				Ciudad c = ciudadRepository.findOne(direccion.getCiudadId());
				model.setDatosDireccionOrigen(c, direccion.getDireccion(), direccion.getIndicacionesDireccion());
			}

			bodega = model.getLineas().stream().map(a -> a.getBodegaDestino()).filter(a -> a != null)
					.collect(Collectors.groupingBy(a -> a, Collectors.counting())).entrySet().stream()
					.max((entry1, entry2) -> (int) (entry1.getValue() - entry2.getValue())).map(a -> a.getKey());
			if (bodega.isPresent()) {
				OmsDireccion direccion = bodega.get().getDireccion();
				Ciudad c = ciudadRepository.findOne(direccion.getCiudadId());
				model.setDatosDireccionDestino(c, direccion.getDireccion(), direccion.getIndicacionesDireccion());
			}
		}
	}

	@Transactional(readOnly = false)
	private void saveLineaOrden(ETLOrdenDto dtoOrden, ETLLineaOrdenDto dto, Orden orden) {
		LineaOrden model = null;
		Producto producto = null;
		Optional<ProductoUnidadAssociation> huella = null;
		Unidad unidad = null;
		Dimensiones dimensiones = null;
		Bodega bodegaOrigen = null;
		Bodega bodegaDestino = null;

		model = new LineaOrden();
		orden.addLinea(model);

		// ----------------------------------------------------------------------------------------------------
		producto = homologarProducto(dto, orden.getCliente().getId());
		huella = homologarHuella(producto);

		if (huella.isPresent()) {
			unidad = huella.get().getUnidad();
			dto.setUnidadCodigo(unidad.getCodigo());
			dimensiones = huella.get().getDimensiones();
		}

		// ----------------------------------------------------------------------------------------------------
		bodegaOrigen = homologarBodega(dto, dto.getBodegaOrigenCodigo(), orden.getCliente().getId(),
				dto.getBodegaOrigenCodigoAlterno(), true);

		bodegaDestino = homologarBodega(dto, dto.getBodegaDestinoCodigo(), orden.getCliente().getId(),
				dto.getBodegaDestinoCodigoAlterno(), false);

		// ----------------------------------------------------------------------------------------------------
		model.setDescripcion(dto.getDescripcion());

		model.setCantidadSolicitada(dto.getCantidadSolicitada());
		model.setCantidadPlanificada(dto.getCantidadSolicitada());

		model.setProducto(producto);
		model.setProductoCodigo(dto.getProductoCodigo());
		// model.setProductoCodigoAlterno("");

		model.setUnidad(unidad);
		model.setUnidadCodigo(dto.getUnidadCodigo());
		// model.setUnidadCodigoAlterno("");

		model.setTipoContenido(null);
		model.setTipoContenidoCodigo("");
		// model.setTipoContenidoCodigoAlterno("");

		model.setDimensiones(dimensiones);

		model.setBodegaOrigen(bodegaOrigen);
		model.setBodegaOrigenCodigo(dto.getBodegaOrigenCodigo());
		model.setBodegaOrigenCodigoAlterno(dto.getBodegaOrigenCodigoAlterno());
		model.setEstadoInventarioOrigenId(dto.getEstadoInventarioOrigen());

		model.setBodegaDestino(bodegaDestino);
		model.setBodegaDestinoCodigo(dto.getBodegaDestinoCodigo());
		model.setBodegaDestinoCodigoAlterno(dto.getBodegaDestinoCodigoAlterno());
		model.setEstadoInventarioDestinoId(dto.getEstadoInventarioDestino());

		model.setLote(dto.getLote());
		model.setSerial(dto.getSerial());
		model.setCosecha(dto.getCosecha());

		model.setRequerimientoEstampillado(dto.getRequerimientoEstampillado());
		model.setRequerimientoSalud(dto.getRequerimientoSalud());
		model.setRequerimientoImporte(dto.getRequerimientoImporte());
		model.setRequerimientoDistribuido(dto.getRequerimientoDistribuido());
		model.setRequerimientoNutricional(dto.getRequerimientoNutricional());
		model.setRequerimientoBl(dto.getRequerimientoBl());
		model.setRequerimientoFondoCuenta(dto.getRequerimientoFondoCuenta());
		model.setRequerimientoOrigen(dto.getRequerimientoOrigen());

		model.setPredistribucionDestinoFinal(dto.getPredistribucionDestinoFinal());
		model.setPredistribucionRotulo(dto.getPredistribucionRotulo());
		model.setValorDeclaradoPorUnidad(dto.getValorDeclaradoPorUnidad());
		model.setNotas(dto.getNotas());

		model.setFechaActualizacion(LocalDateTime.now());
		model.setUsuarioActualizacion(dtoOrden.getUsuarioConfirmacion());

		model.setFechaCreacion(model.getFechaActualizacion());
		model.setUsuarioCreacion(model.getUsuarioActualizacion());
	}

	private Orden setDatosOrden(ETLOrdenDto dto, Cliente cliente, TipoServicio tipoServicio, Canal canal,
			Destinatario tercero, Ciudad ciudadDestino, Destino puntoDestino, Ciudad ciudadOrigen, Destino puntoOrigen,
			String usuario, LocalDateTime fecha) {

		Orden model = new Orden();

		// ---------------------------------------------------------------------------------------------------------
		model.setDatosOrden(dto.getNumeroOrden(), dto.getFechaOrden(), dto.getNumeroOrdenCompra(), cliente,
				tipoServicio, dto.getTipoServicioCodigoAlterno(), dto.isRequiereServicioDistribucion());

		// ---------------------------------------------------------------------------------------------------------
		model.setDatosDireccionDestino(ciudadDestino, dto.getDestinoDireccion(), dto.getDestinoIndicaciones());
		model.setDatosDireccionOrigen(ciudadOrigen, dto.getOrigenDireccion(), dto.getOrigenIndicaciones());

		// ---------------------------------------------------------------------------------------------------------
		model.setRequiereConfirmacionCitaEntrega(dto.isRequiereConfirmacionCitaEntrega());
		model.setDatosCitaEntregaSugerida(dto.getFechaEntregaSugeridaMinima(), dto.getFechaEntregaSugeridaMaxima(),
				dto.getHoraEntregaSugeridaMinima(), dto.getHoraEntregaSugeridaMaxima());
		if (!dto.isRequiereConfirmacionCitaEntrega()) {
			if (dto.getFechaEntregaSugeridaMaxima() != null) {
				if (dto.getFechaEntregaSugeridaMaxima().equals(dto.getFechaEntregaSugeridaMinima())) {
					model.setDatosCitaEntrega(dto.getFechaEntregaSugeridaMaxima(), dto.getHoraEntregaSugeridaMinima(),
							dto.getHoraEntregaSugeridaMaxima());
				}
			}
		}
		// ---------------------------------------------------------------------------------------------------------
		model.setRequiereConfirmacionCitaRecogida(dto.isRequiereConfirmacionCitaRecogida());
		model.setDatosCitaRecogidaSugerida(dto.getFechaRecogidaSugeridaMinima(), dto.getFechaRecogidaSugeridaMaxima(),
				dto.getHoraRecogidaSugeridaMinima(), dto.getHoraRecogidaSugeridaMaxima());
		if (!dto.isRequiereConfirmacionCitaRecogida()) {
			if (dto.getFechaRecogidaSugeridaMaxima() != null) {
				if (dto.getFechaRecogidaSugeridaMaxima().equals(dto.getFechaRecogidaSugeridaMinima())) {
					model.setDatosCitaRecogida(dto.getFechaRecogidaSugeridaMaxima(),
							dto.getHoraRecogidaSugeridaMinima(), dto.getHoraRecogidaSugeridaMaxima());
				}
			}
		}

		// ---------------------------------------------------------------------------------------------------------
		model.setDatosDestinatario(canal, dto.getCanalCodigoAlterno(), tercero, dto.getDestinatarioContacto());
		model.setDatosPuntoDestino(puntoDestino, dto.getDestinoContacto());
		model.setDatosPuntoOrigen(puntoOrigen, dto.getOrigenContacto());

		// ---------------------------------------------------------------------------------------------------------
		model.setValorRecaudo(dto.getValorRecaudo());
		model.setDatosCreacion(usuario, fecha);
		model.setDatosActualizacion(fecha, usuario);
		// notas_requerimientos_distribucion
		// notas_requerimientos_alistamiento
		// Listas de requerimientos

		return model;
	}

	private Orden checkOrdenExistente(ETLOrdenDto dto, int clienteId) {
		Orden model;
		model = ordenRepository.findFirstByClienteIdAndNumeroOrden(clienteId, dto.getNumeroOrden());

		if (model != null) {
			switch (model.getEstadoOrden()) {
			case ANULADA:
				ordenRepository.delete(model.getId());
				model = null;
				break;
			default:
				break;
			}
		}

		return model;
	}

	private TipoServicio homologarTipoServicio(int clienteId, String codigo, String codigoAlterno) {
		TipoServicio tipoServicio = null;

		if (!codigo.isEmpty()) {
			tipoServicio = tipoServicioRepository.findByCodigoIgnoringCase(codigo);
		} else if (!codigoAlterno.isEmpty()) {
			tipoServicio = tipoServicioRepository.findFisrtByClienteIdAndCodigoAlterno(clienteId, codigoAlterno);
		}
		return tipoServicio;
	}

	private Ciudad homologarCiudad(int clienteId, String ciudadNombreAlterno) {
		Ciudad ciudad = null;
		if (!ciudadNombreAlterno.isEmpty()) {
			ciudad = ciudadRepository.findTop1ByClienteIdAndNombreAlterno(clienteId, ciudadNombreAlterno);
			if (ciudad == null) {
				ciudad = ciudadRepository.findByNombreAlternoIgnoringCase(ciudadNombreAlterno);
				if (ciudad == null) {
					ciudad = ciudadRepository.findByCodigo(ciudadNombreAlterno);
				}
			}
		}
		return ciudad;
	}

	private Destinatario homologarTercero(ETLOrdenDto dto, int clienteId, String identificacion, String usuario) {
		Destinatario tercero = null;
		if (!identificacion.isEmpty()) {
			tercero = terceroRepository.findByClienteIdAndNumeroIdentificacion(clienteId, identificacion);
			if (tercero == null) {
				tercero = CrearDestintarioRemitente(dto, clienteId, usuario);
			}
		}
		return tercero;
	}

	private Destino homologarPunto(int terceroId, int ciudadId, String direccion, String indicaciones,
			String puntoCodigo, String puntoNombre, Contacto contacto, String usuario, StringBuffer errores) {

		Destino punto = null;
		List<Destino> puntos;

		puntos = puntoRepository
				.findAllByDestinatarioIdAndDireccionCiudadIdAndDireccionDireccionOrderByCodigoAscNombreAsc(terceroId,
						ciudadId, direccion);

		if (puntos.isEmpty()) {
			punto = this.CrearPunto(terceroId, ciudadId, direccion, indicaciones, puntoCodigo, puntoNombre, contacto,
					usuario);
		} else {
			if (puntos.size() > 1) {
				errores.append("ADVERTENCIA: Se detectaron " + puntos.size() + " puntos con esta misma dirección.");
			}
			punto = puntos.get(0);
		}

		return punto;
	}

	private void usarContactoPredeterminadoDelTercero(ETLOrdenDto dto, Destinatario tercero) {
		// Solo si la informacion de contacto del destinatario no se ha
		// personalizado en el dto, solo entoces
		// se usa la del destinatario
		Contacto contacto = dto.getDestinatarioContacto();
		if (contacto.isEmpty()) {
			dto.setDestinatarioContacto(tercero.getContacto());
		}
	}

	private void usarContactoPredeterminadoDelPuntoDestino(ETLOrdenDto dto, Destino punto) {
		// Solo si la informacion de contacto del destino no se ha
		// personalizado en el dto, solo entoces
		// se usa la del destino
		Contacto contacto = dto.getDestinoContacto();
		if (contacto.isEmpty()) {
			dto.setDestinoContacto(punto.getContacto());
		}
	}

	private void usarContactoPredeterminadoDelPuntoOrigen(ETLOrdenDto dto, Destino punto) {
		// Solo si la informacion de contacto del destino no se ha
		// personalizado en el dto, solo entoces
		// se usa la del destino
		Contacto contacto = dto.getOrigenContacto();
		if (contacto.isEmpty()) {
			dto.setDestinoContacto(punto.getContacto());
		}
	}

	protected Destinatario CrearDestintarioRemitente(ETLOrdenDto dto, int clienteId, String usuario) {
		Destinatario model = null;
		if (dto.getDestinatarioNumeroIdentificacion().isEmpty() || dto.getCanalCodigoAlterno().isEmpty()
				|| dto.getDestinatarioNombre().isEmpty()) {
			return model;
		}

		Canal canal = canalRepository.findByNombreIgnoringCase(dto.getCanalCodigoAlterno());

		model = new Destinatario();

		model.setClienteId(clienteId);
		model.setCanalId((canal == null) ? 0 : canal.getId());

		model.setCodigo("");
		model.setNumeroIdentificacion(dto.getDestinatarioNumeroIdentificacion());
		model.setDigitoVerificacion("");

		model.setNombre(dto.getDestinatarioNombre());
		model.setNombreComercial(dto.getDestinatarioNombre());

		model.setDireccion(new OmsDireccion(null, "", ""));

		model.setContacto(new Contacto(dto.getDestinatarioContactoNombres(), dto.getDestinatarioContactoEmail(),
				dto.getDestinatarioContactoTelefonos()));

		model.setFechaActualizacion(new Date());
		model.setUsuarioActualizacion(usuario);

		return terceroRepository.save(model);
	}

	protected Destino CrearPunto(int terceroId, int ciudadId, String direccion, String indicaciones, String puntoCodigo,
			String puntoNombre, Contacto contacto, String usuario) {
		Destino model = null;

		if (direccion.isEmpty()) {
			return model;
		}

		model = new Destino();
		model.setDestinatarioId(terceroId);
		model.setDireccion(new OmsDireccion(ciudadId, direccion, indicaciones));

		model.setCodigo(puntoCodigo);
		model.setNombre(puntoNombre);
		model.setContacto(contacto);

		model.setUsoUbicacionType(UsoUbicacionType.PUNTO_RUTA);

		model.setFechaActualizacion(new Date());
		model.setUsuarioActualizacion(usuario);

		return puntoRepository.save(model);
	}

	private Producto homologarProducto(ETLLineaOrdenDto dto, int clienteid) {
		Producto producto = null;

		if (!dto.getProductoCodigo().isEmpty()) {
			producto = productoRepository.findByClienteIdAndCodigo(clienteid, dto.getProductoCodigo());
			if (producto != null) {
				dto.setProductoCodigo(producto.getCodigo());
				dto.setDescripcion(producto.getNombreLargo());
			}
		}
		return producto;
	}

	private Optional<ProductoUnidadAssociation> homologarHuella(Producto producto) {
		Optional<ProductoUnidadAssociation> huella = Optional.empty();

		if (producto != null) {
			huella = producto.getProductoUnidadAssociation().stream().filter(a -> a.getNivel() == 1).findFirst();
		}
		return huella;
	}

	private Bodega homologarBodega(ETLLineaOrdenDto dto, String bodegaCodigo, int clienteId, String bodegaCodigoAlterno,
			boolean origen) {
		Bodega bodega = null;

		ClienteBodegaAssociation clienteBodega;

		if (!bodegaCodigo.isEmpty()) {
			bodega = bodegaRepository.findByCodigoIgnoringCase(bodegaCodigo);
		} else if (!dto.getBodegaOrigenCodigoAlterno().isEmpty()) {
			clienteBodega = bodegaRepository.findFirstByClienteIdAndCodigoAlterno(clienteId, bodegaCodigoAlterno);
			if (clienteBodega != null) {
				bodega = bodegaRepository.findOne(clienteBodega.getBodegaId());
				if (origen) {
					dto.setEstadoInventarioOrigen(clienteBodega.getEstadoInventarioId());
				} else {
					dto.setEstadoInventarioDestino(clienteBodega.getEstadoInventarioId());
				}
			}
		}
		if (bodega != null) {
			if (origen) {
				dto.setBodegaOrigenCodigo(bodega.getCodigo());
			} else {
				dto.setBodegaDestinoCodigo(bodega.getCodigo());
			}
		}
		return bodega;
	}

	// -------------------------------------------------------------------------------------------------------------------------------------------------
	// TODO IMPLEMENTAR REGLAS DE NEGOCIO
	// TODO HACER USO DE ENTIDADES REST Y DE EXCEPCIONES
	@Transactional(readOnly = false)
	public MensajesDto aceptarOrdenes(List<Integer> ids, String notas, Integer usuarioId) throws DataAccessException {
		Usuario usuario = usuarioRepository.findOne(usuarioId);

		LocalDateTime fechaUpd = LocalDateTime.now();
		String usuarioUpd = usuario.getUsuario();
		notas = Basic.coalesce(notas, "");

		MensajesDto msg = new MensajesDto();
		for (Integer id : ids) {
			Orden orden = ordenRepository.findOne(id);
			if (orden != null) {
				msg.AddMensajes(aceptarOrden(orden, fechaUpd, usuarioUpd, notas));
			} else {
				msg.addMensaje(SeveridadType.ERROR, id, "No se encontró la orden con id: " + id);
				// throw new BadRequestException();
			}
		}
		return msg;
	}

	private MensajesDto aceptarOrden(Orden orden, LocalDateTime fechaUpd, String usuarioUpd, String notas) {
		EstadoOrdenType nuevoEstado = EstadoOrdenType.ACEPTADA;

		MensajesDto msg = new MensajesDto();

		if (Orden.transicionPermitida(orden.getEstadoOrden(), nuevoEstado)) {
			orden.aceptar(fechaUpd, usuarioUpd, notas);
			try {
				ordenRepository.save(orden);
				msg.addMensaje(SeveridadType.INFO, orden.getId(), "OK");
			} catch (RuntimeException re) {
				msg.addMensaje(re, orden.getId());
			}
		} else {
			msg.addMensaje(SeveridadType.ERROR, orden.getNumeroOrden(), "El cambio de estado desde "
					+ orden.getEstadoOrden().getNombre() + " a " + nuevoEstado.getNombre() + ", no esta permitido");
		}
		return msg;
	}

	// TODO IMPLEMENTAR REGLAS DE NEGOCIO
	// TODO HACER USO DE ENTIDADES REST Y DE EXCEPCIONES
	@Transactional(readOnly = false)
	public MensajesDto anularOrdenes(List<Integer> ids, Integer usuarioId, String notas, int causalId)
			throws DataAccessException {
		Usuario usuario = usuarioRepository.findOne(usuarioId);
		CausalAnulacion causal = causalAnulacionRepository.findOne(causalId);

		LocalDateTime fechaUpd = LocalDateTime.now();
		String usuarioUpd = usuario.getUsuario();
		notas = Basic.coalesce(notas, "");

		MensajesDto msg = new MensajesDto();

		if (causal == null) {
			msg.addMensaje(SeveridadType.ERROR, null, "El id de la causal no existe: " + causalId);
		} else {
			for (Integer id : ids) {
				Orden orden = ordenRepository.findOne(id);
				if (orden != null) {
					msg.AddMensajes(anularOrden(orden, fechaUpd, usuarioUpd, notas, causal));
				} else {
					msg.addMensaje(SeveridadType.ERROR, id, "No se encontró la orden con id: " + id);
				}
			}
		}

		return msg;
	}

	private MensajesDto anularOrden(Orden orden, LocalDateTime fechaUpd, String usuarioUpd, String notas,
			CausalAnulacion causal) {
		EstadoOrdenType nuevoEstado = EstadoOrdenType.ANULADA;

		MensajesDto msg = new MensajesDto();

		if (Orden.transicionPermitida(orden.getEstadoOrden(), nuevoEstado)) {
			orden.anular(fechaUpd, usuarioUpd, notas, causal);
			try {
				ordenRepository.save(orden);
				msg.addMensaje(SeveridadType.INFO, orden.getId(), "OK");
			} catch (RuntimeException re) {
				msg.addMensaje(re, orden.getId());
			}
		} else {
			msg.addMensaje(SeveridadType.ERROR, orden.getNumeroOrden(), "El cambio de estado desde "
					+ orden.getEstadoOrden().getNombre() + " a " + nuevoEstado.getNombre() + ", no esta permitido");
		}
		return msg;
	}

}
