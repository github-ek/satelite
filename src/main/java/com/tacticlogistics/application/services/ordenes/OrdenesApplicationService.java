package com.tacticlogistics.application.services.ordenes;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tacticlogistics.application.dto.etl.ETLLineaOrdenDto;
import com.tacticlogistics.application.dto.etl.ETLOrdenDto;
import com.tacticlogistics.application.dto.oms.ConsultaOrdenesDto;
import com.tacticlogistics.application.dto.ordenes.DestinatarioDto;
import com.tacticlogistics.application.dto.ordenes.LineaOrdenDto;
import com.tacticlogistics.application.dto.ordenes.OrdenDto;
import com.tacticlogistics.application.services.geo.CiudadesApplicationService;
import com.tacticlogistics.application.services.ordenes.OrdenViewModelAdapter.LineaOrdenViewModelAdpater;
import com.tacticlogistics.application.services.ordenes.OrdenViewModelAdapter.MensajeViewModelAdpater;
import com.tacticlogistics.application.services.ordenes.OrdenViewModelAdapter.OrdenDatosBodegaDestinoOrigenViewModelAdapter;
import com.tacticlogistics.application.services.ordenes.OrdenViewModelAdapter.OrdenDatosDestinoOrigenViewModelAdapter;
import com.tacticlogistics.application.services.ordenes.OrdenViewModelAdapter.OrdenDatosEntregaRecogidaViewModelAdapter;
import com.tacticlogistics.application.services.ordenes.OrdenViewModelAdapter.OrdenDatosFacturacionViewModelAdapter;
import com.tacticlogistics.application.services.ordenes.OrdenViewModelAdapter.OrdenDatosOtrosViewModelAdapter;
import com.tacticlogistics.domain.model.common.IdentificacionType;
import com.tacticlogistics.domain.model.common.TipoContenido;
import com.tacticlogistics.domain.model.common.UsoUbicacionType;
import com.tacticlogistics.domain.model.common.valueobjects.Contacto;
import com.tacticlogistics.domain.model.common.valueobjects.Dimensiones;
import com.tacticlogistics.domain.model.common.valueobjects.OmsDireccion;
import com.tacticlogistics.domain.model.crm.Canal;
import com.tacticlogistics.domain.model.crm.Cliente;
import com.tacticlogistics.domain.model.crm.ClienteBodegaAssociation;
import com.tacticlogistics.domain.model.crm.DestinatarioRemitente;
import com.tacticlogistics.domain.model.crm.DestinoOrigen;
import com.tacticlogistics.domain.model.crm.TipoFormaPago;
import com.tacticlogistics.domain.model.crm.TipoServicio;
import com.tacticlogistics.domain.model.geo.Ciudad;
import com.tacticlogistics.domain.model.oms.EstadoOrdenType;
import com.tacticlogistics.domain.model.ordenes.Consolidado;
import com.tacticlogistics.domain.model.ordenes.EstadoOrden;
import com.tacticlogistics.domain.model.ordenes.LineaOrden;
import com.tacticlogistics.domain.model.ordenes.Orden;
import com.tacticlogistics.domain.model.requerimientos.Requerimiento;
import com.tacticlogistics.domain.model.wms.Bodega;
import com.tacticlogistics.domain.model.wms.Producto;
import com.tacticlogistics.domain.model.wms.ProductoUnidadAssociation;
import com.tacticlogistics.domain.model.wms.Unidad;
import com.tacticlogistics.infrastructure.persistence.crm.CanalRepository;
import com.tacticlogistics.infrastructure.persistence.crm.ClienteRepository;
import com.tacticlogistics.infrastructure.persistence.crm.DestinatarioRemitenteRepository;
import com.tacticlogistics.infrastructure.persistence.crm.DestinoOrigenRepository;
import com.tacticlogistics.infrastructure.persistence.crm.TipoFormaPagoRepository;
import com.tacticlogistics.infrastructure.persistence.crm.TipoServicioRepository;
import com.tacticlogistics.infrastructure.persistence.geo.CiudadRepository;
import com.tacticlogistics.infrastructure.persistence.ordenes.ConsolidadoRepository;
import com.tacticlogistics.infrastructure.persistence.ordenes.EstadoOrdenRepository;
import com.tacticlogistics.infrastructure.persistence.ordenes.OrdenRepository;
import com.tacticlogistics.infrastructure.persistence.ordenes.TipoContenidoRepository;
import com.tacticlogistics.infrastructure.persistence.wms.BodegaRepository;
import com.tacticlogistics.infrastructure.persistence.wms.ProductoRepository;
import com.tacticlogistics.infrastructure.persistence.wms.UnidadRepository;
import com.tacticlogistics.infrastructure.services.Basic;

@Service
@Transactional(readOnly = true)
public class OrdenesApplicationService {
	//@Autowired
	//private CiudadesApplicationService ciudadesService;

	@Autowired
	private OrdenRepository ordenRepository;
	@Autowired
	private EstadoOrdenRepository estadoOrdenRepository;
	@Autowired
	private TipoServicioRepository tipoServicioRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private ConsolidadoRepository consolidadoRepository;
	@Autowired
	private CanalRepository canalRepository;
	@Autowired
	private DestinatarioRemitenteRepository terceroRepository;
	@Autowired
	private DestinoOrigenRepository puntoRepository;
	@Autowired
	private CiudadRepository ciudadRepository;
	@Autowired
	private BodegaRepository bodegaRepository;
	@Autowired
	private TipoFormaPagoRepository tipoFormaPagoRepository;
	@Autowired
	private ProductoRepository productoRepository;
	@Autowired
	private UnidadRepository unidadRepository;
	@Autowired
	private TipoContenidoRepository tipoContenidoRepository;

	// TODO HOMOLOGAR CODIGOS ALTERNOS DE SERVICIOS
	// TODO IMPLEMENTAR RGLAS DE NEGOCIO EN CONFIRMAR
	// TODO RETORNAR ERRORES DE NEGOCIO
	// TODO IMPLEMENTAR UN CACHE DE DATOS
	// TODO QUITAR LOS CODIGOS ALTERNO DE LAS LINEAS
	// TODO INCLUIR PINADO
	// TODO VALIDAR ERRORES DE LONGITUD DE CARACTERES
	@Transactional(readOnly = false)
	public Orden saveOrdenDespachosSecundaria(ETLOrdenDto dto) throws DataAccessException {
		StringBuffer errores = new StringBuffer();

		Cliente cliente = null;
		TipoServicio tipoServicio = null;
		Ciudad ciudad = null;
		DestinoOrigen punto = null;
		Canal canal = null;
		DestinatarioRemitente tercero = null;
		Consolidado consolidado = null;

		tipoServicio = tipoServicioRepository.findByCodigoIgnoringCase(dto.getTipoServicioCodigo());
		if (tipoServicio == null) {
			throw new RuntimeException("(tipoServicio == null)");
		}

		cliente = clienteRepository.findByCodigoIgnoringCase(dto.getClienteCodigo());
		if (cliente == null) {
			throw new RuntimeException("(cliente == null)");
		}

		Orden model = checkOrdenExistente(dto, cliente.getId());
		if (model != null) {
			throw new RuntimeException("Ya existe una solicitud con el mismo numero");
		}

		// ------------------------------------------------------------------------------------------------
		String usuario = dto.getUsuarioConfirmacion();
		Date fecha = new Date();

		consolidado = homologarConsolidado(dto, cliente);
		ciudad = homologarCiudad(cliente.getId(), dto.getDestinoCiudadNombreAlterno());
		tercero = homologarTercero(dto, cliente.getId(), dto.getDestinatarioNumeroIdentificacion(), usuario);
		if (tercero != null && ciudad != null) {
			punto = homologarPunto(tercero.getId(), ciudad.getId(), dto.getDestinoDireccion(),
					dto.getDestinoIndicaciones(), dto.getDestinoNombre(), dto.getDestinoContacto(), usuario, errores);
		}

		// ------------------------------------------------------------------------------------------------
		if (tercero != null) {
			canal = canalRepository.findOne(tercero.getCanalId());

			usarContactoPredeterminadoDelTercero(dto, tercero);
		}
		if (punto != null) {
			usarContactoPredeterminadoDelPuntoDestino(dto, punto);
		}

		// ------------------------------------------------------------------------------------------------
		model = setDatosOrden(dto, cliente, tipoServicio, canal, tercero, ciudad, punto, null, null, consolidado,
				usuario, fecha);

		// ------------------------------------------------------------------------------------------------
		for (ETLLineaOrdenDto linea : dto.getLineas()) {
			saveLineaOrden(dto, linea, model);
		}

		// ------------------------------------------------------------------------------------------------
		model.confirmar(usuario, fecha, dto.getNotasConfirmacion());
		model = ordenRepository.save(model);

		return model;
	}

	@Transactional(readOnly = false)
	public Orden saveOrdenRecibo(ETLOrdenDto dto) throws DataAccessException {
		StringBuffer errores = new StringBuffer();

		Cliente cliente = null;
		TipoServicio tipoServicio = null;
		Ciudad ciudad = null;
		DestinoOrigen punto = null;
		Canal canal = null;
		DestinatarioRemitente tercero = null;
		Consolidado consolidado = null;

		tipoServicio = tipoServicioRepository.findByCodigoIgnoringCase(dto.getTipoServicioCodigo());
		if (tipoServicio == null) {
			throw new RuntimeException("(tipoServicio == null)");
		}

		cliente = clienteRepository.findByCodigoIgnoringCase(dto.getClienteCodigo());
		if (cliente == null) {
			throw new RuntimeException("(cliente == null)");
		}

		Orden model = checkOrdenExistente(dto, cliente.getId());
		if (model != null) {
			throw new RuntimeException("Ya existe una solicitud con el mismo numero");
		}

		// ------------------------------------------------------------------------------------------------
		String usuario = dto.getUsuarioConfirmacion();
		Date fecha = new Date();

		consolidado = homologarConsolidado(dto, cliente);
		ciudad = homologarCiudad(cliente.getId(), dto.getOrigenCiudadNombreAlterno());
		tercero = homologarTercero(dto, cliente.getId(), dto.getDestinatarioNumeroIdentificacion(), usuario);
		if (tercero != null && ciudad != null) {
			punto = homologarPunto(tercero.getId(), ciudad.getId(), dto.getOrigenDireccion(),
					dto.getOrigenIndicaciones(), dto.getOrigenNombre(), dto.getOrigenContacto(), usuario, errores);
		}

		// ------------------------------------------------------------------------------------------------
		if (tercero != null) {
			canal = canalRepository.findOne(tercero.getCanalId());

			usarContactoPredeterminadoDelTercero(dto, tercero);
		}
		if (punto != null) {
			usarContactoPredeterminadoDelPuntoOrigen(dto, punto);
		}

		// ------------------------------------------------------------------------------------------------
		model = setDatosOrden(dto, cliente, tipoServicio, canal, tercero, null, null, ciudad, punto, consolidado,
				usuario, fecha);

		// ------------------------------------------------------------------------------------------------
		for (ETLLineaOrdenDto linea : dto.getLineas()) {
			saveLineaOrden(dto, linea, model);
		}

		// ------------------------------------------------------------------------------------------------
		model.confirmar(usuario, fecha, dto.getNotasConfirmacion());
		model = ordenRepository.save(model);

		return model;
	}

	@Transactional(readOnly = false)
	protected void saveLineaOrden(ETLOrdenDto dtoOrden, ETLLineaOrdenDto dto, Orden orden) {
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
		model.setProductoCodigoAlterno("");

		model.setUnidad(unidad);
		model.setUnidadCodigo(dto.getUnidadCodigo());
		model.setUnidadCodigoAlterno("");

		model.setTipoContenido(null);
		model.setTipoContenidoCodigo("");
		model.setTipoContenidoCodigoAlterno("");

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

		model.setFechaActualizacion(new Date());
		model.setUsuarioActualizacion(dtoOrden.getUsuarioConfirmacion());

		model.setFechaCreacion(model.getFechaActualizacion());
		model.setUsuarioCreacion(model.getUsuarioActualizacion());
	}

	private Orden setDatosOrden(ETLOrdenDto dto, Cliente cliente, TipoServicio tipoServicio, Canal canal,
			DestinatarioRemitente tercero, Ciudad ciudadDestino, DestinoOrigen puntoDestino, Ciudad ciudadOrigen,
			DestinoOrigen puntoOrigen, Consolidado consolidado, String usuario, Date fecha) {

		Orden model = new Orden();

		// ---------------------------------------------------------------------------------------------------------
		model.setDatosOrden(
				dto.getNumeroOrden(), 
				null, 
				"", 
				consolidado, 
				cliente, 
				tipoServicio, 
				dto.getTipoServicioCodigoAlterno(),
				dto.isRequiereServicioDistribucion());

		// ---------------------------------------------------------------------------------------------------------
		model.setDatosDireccionDestino(ciudadDestino, dto.getDestinoDireccion(), dto.getDestinoIndicaciones());
		model.setDatosDireccionOrigen(ciudadOrigen, dto.getOrigenDireccion(), dto.getOrigenIndicaciones());

		// ---------------------------------------------------------------------------------------------------------
		model.setRequiereConfirmacionCitaEntrega(dto.isRequiereConfirmacionCitaEntrega());
		model.setDatosCitaEntregaSugerida(
				dto.getFechaEntregaSugeridaMinima(), 
				dto.getFechaEntregaSugeridaMaxima(),
				dto.getHoraEntregaSugeridaMinima(), 
				dto.getHoraEntregaSugeridaMaxima());

		// ---------------------------------------------------------------------------------------------------------
		model.setRequiereConfirmacionCitaRecogida(dto.isRequiereConfirmacionCitaRecogida());
		model.setDatosCitaRecogidaSugerida(
				dto.getFechaRecogidaSugeridaMinima(), 
				dto.getFechaRecogidaSugeridaMaxima(),
				dto.getHoraRecogidaSugeridaMinima(), 
				dto.getHoraRecogidaSugeridaMaxima());

		// ---------------------------------------------------------------------------------------------------------
		model.setDatosDestinatario(canal, dto.getCanalCodigoAlterno(), tercero, dto.getDestinatarioContacto());
		model.setDatosPuntoDestino(puntoDestino, dto.getDestinoContacto());
		model.setDatosPuntoDestino(puntoOrigen, dto.getOrigenContacto());

		// ---------------------------------------------------------------------------------------------------------
		model.setValorRecaudo(dto.getValorRecaudo());

		model.setDatosCreacion(usuario, fecha);
		model.setDatosActualizacion(usuario, fecha);

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

	private Consolidado homologarConsolidado(ETLOrdenDto dto, Cliente cliente) {
		Consolidado consolidado = null;

		if (!dto.getNumeroConsolidado().isEmpty()) {
			consolidado = consolidadoRepository.findByClienteIdAndNumeroDocumentoConsolidadoClienteIgnoringCase(
					cliente.getId(), dto.getNumeroConsolidado());
			if (consolidado == null) {
				consolidado = CrearConsolidado(dto, cliente);
			}
		}
		return consolidado;
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

	private DestinatarioRemitente homologarTercero(ETLOrdenDto dto, int clienteId, String identificacion,
			String usuario) {
		DestinatarioRemitente tercero = null;
		if (!identificacion.isEmpty()) {
			tercero = terceroRepository.findByClienteIdAndNumeroIdentificacion(clienteId, identificacion);
			if (tercero == null) {
				tercero = CrearDestintarioRemitente(dto, clienteId, usuario);
			}
		}
		return tercero;
	}

	private DestinoOrigen homologarPunto(int terceroId, int ciudadId, String direccion, String indicaciones,
			String puntoNombre, Contacto contacto, String usuario, StringBuffer errores) {

		DestinoOrigen punto = null;
		List<DestinoOrigen> puntos;

		puntos = puntoRepository
				.findAllByDestinatarioRemitenteIdAndDireccionCiudadIdAndDireccionDireccionOrderByCodigoAscNombreAsc(
						terceroId, ciudadId, direccion);

		if (puntos.isEmpty()) {
			punto = this.CrearPunto(terceroId, ciudadId, direccion, indicaciones, puntoNombre, contacto, usuario);
		} else {
			if (puntos.size() > 1) {
				errores.append("ADVERTENCIA: Se detectaron " + puntos.size() + " puntos con esta misma dirección.");
			}
			punto = puntos.get(0);
		}

		return punto;
	}

	private void usarContactoPredeterminadoDelTercero(ETLOrdenDto dto, DestinatarioRemitente tercero) {
		// Solo si la informacion de contacto del destinatario no se ha
		// personalizado en el dto, solo entoces
		// se usa la del destinatario
		Contacto contacto = dto.getDestinatarioContacto(); 
		if (contacto.isEmpty()) {
			dto.setDestinatarioContacto(tercero.getContacto());
		}
	}

	private void usarContactoPredeterminadoDelPuntoDestino(ETLOrdenDto dto, DestinoOrigen punto) {
		// Solo si la informacion de contacto del destino no se ha
		// personalizado en el dto, solo entoces
		// se usa la del destino
		Contacto contacto = dto.getDestinoContacto(); 
		if (contacto.isEmpty()) {
			dto.setDestinoContacto(punto.getContacto());
		}
	}

	private void usarContactoPredeterminadoDelPuntoOrigen(ETLOrdenDto dto, DestinoOrigen punto) {
		// Solo si la informacion de contacto del destino no se ha
		// personalizado en el dto, solo entoces
		// se usa la del destino
		Contacto contacto = dto.getOrigenContacto(); 
		if (contacto.isEmpty()) {
			dto.setDestinoContacto(punto.getContacto());
		}
	}

	// TODO HOMOLOGAR CANAL
	protected DestinatarioRemitente CrearDestintarioRemitente(ETLOrdenDto dto, int clienteId, String usuario) {
		DestinatarioRemitente model = null;
		if (dto.getDestinatarioNumeroIdentificacion().isEmpty()) {
			return model;
		}

		model = new DestinatarioRemitente();

		model.setClienteId(clienteId);
		model.setCanalId(0);

		model.setCodigo("");
		model.setIdentificacionType(IdentificacionType.NI);
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

	protected DestinoOrigen CrearPunto(int terceroId, int ciudadId, String direccion, String indicaciones,
			String puntoNombre, Contacto contacto, String usuario) {
		DestinoOrigen model = null;

		if (direccion.isEmpty()) {
			return model;
		}

		model = new DestinoOrigen();
		model.setDestinatarioRemitenteId(terceroId);
		model.setCodigo("");
		model.setNombre(puntoNombre);
		model.setUsoUbicacionType(UsoUbicacionType.PUNTO_RUTA);

		model.setDireccion(new OmsDireccion(ciudadId, direccion, indicaciones));
		model.setContacto(contacto);

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

	// TODO ELIMINAR

	// ----------------------------------------------------------------------------------------------------------------
	// -- Gestionar Ordenes
	// ----------------------------------------------------------------------------------------------------------------
	@Deprecated
	public List<Object> findAllEstadoOrden() throws DataAccessException {
		List<EstadoOrden> entityList = estadoOrdenRepository.findAllByOrderByOrdinalAsc();

		List<Object> list = new ArrayList<>();
		entityList.forEach(a -> {
			list.add(estadoOrdenToViewModel(a));
		});
		return list;
	}

	@Deprecated
	public List<OrdenDto> findAllOrdenPorUsuarioPorTipoServicioPorEstadoOrden(Integer usuarioId, Integer tipoServicioId,
			EstadoOrdenType estadoOrdenId, Integer clienteId) throws DataAccessException {
		List<Orden> entityList = ordenRepository.findByUsuarioIdAndTipoServicioIdAndEstadoOrden(usuarioId,
				tipoServicioId, estadoOrdenId, clienteId);

		List<OrdenDto> list = new ArrayList<>();
		entityList.forEach((orden) -> {
			boolean incluir = false;
			if (orden.getFechaConfirmacion() == null) {
				incluir = true;
			} else {
				Date desde = Basic.addDays(Basic.truncate(new Date()), -8);
				if (orden.getFechaConfirmacion().after(desde)) {
					incluir = true;
				}
			}
			if (incluir) {
				list.add(ordenToViewModel(orden));
			}
		});
		return list;
	}

	@Deprecated
	public Map<String, Object> findAllOrdenPorFechaConfirmacion(Integer usuarioId, Date fechaDesde, Date fechaHasta)
			throws DataAccessException {
		Map<String, Object> entityList = new HashMap<>();
		entityList.put("usuarioId", usuarioId);
		entityList.put("fechaDesde", fechaDesde);
		entityList.put("fechaHasta", fechaHasta);

		// List<Orden> entityList =
		// ordenRepository.findByUsuarioIdAndTipoServicioIdAndEstadoOrden(usuarioId,
		// tipoServicioId, estadoOrdenId, clienteId);
		//
		// List<OrdenDto> list = new ArrayList<>();
		// entityList.forEach((orden) -> {
		// boolean incluir = false;
		// if (orden.getFechaConfirmacion() == null) {
		// incluir = true;
		// } else {
		// Date desde = Basic.addDays(Basic.truncate(new Date()), -8);
		// if (orden.getFechaConfirmacion().after(desde)) {
		// incluir = true;
		// }
		// }
		// if (incluir) {
		// list.add(ordenToViewModel(orden));
		// }
		// });
		return entityList;
	}

	@Deprecated
	public Page<ConsultaOrdenesDto> findAllOrdenPorUsuarioPorTipoServicioPorEstadoOrdenConPaginacion(Integer usuarioId,
			Integer tipoServicioId, EstadoOrdenType estadoOrdenId, Integer clienteId, Integer pageNumber,
			Integer pageSize) throws DataAccessException {

		Pageable pageRequest = createPageRequest(pageNumber, pageSize);

		Page<ConsultaOrdenesDto> searchResultPage = ordenRepository
				.findByUsuarioIdAndTipoServicioIdAndEstadoOrdenConPaginacion(
						// usuarioId,
						tipoServicioId, estadoOrdenId, clienteId, pageRequest);

		return searchResultPage;
	}

	private Pageable createPageRequest(int start, int length) {
		start = (start < 0) ? 0 : start;
		length = (length < 0) ? 10 : length;
		return new PageRequest(start, length);
	}

	static Page<ConsultaOrdenesDto> mapEntityPageIntoDTOPage(Pageable pageRequest, Page<Orden> source) {
		final List<ConsultaOrdenesDto> dtos = new ArrayList<>();

		source.getContent().forEach(a -> {

			ConsultaOrdenesDto dto = map(a);

			Date fmin = dto.getFechaSugeridaEntregaMinima();
			Date fmax = dto.getFechaSugeridaEntregaMaxima();

			if ((dto.getFechaPlaneadaEntregaMinima() != null) || (dto.getFechaPlaneadaEntregaMaxima() != null)) {
				fmin = dto.getFechaPlaneadaEntregaMinima();
				fmax = dto.getFechaPlaneadaEntregaMaxima();
			}

			dtos.add(dto);
		});

		return new PageImpl<>(dtos, pageRequest, source.getTotalElements());
	}

	static protected ConsultaOrdenesDto map(Orden orden) {
		PropertyMap<Orden, ConsultaOrdenesDto> ordenMap = new PropertyMap<Orden, ConsultaOrdenesDto>() {
			protected void configure() {
				map().setDestinoDireccion(source.getDestinoDireccion().getDireccion());
			}
		};

		ModelMapper modelMapper = new ModelMapper();

		modelMapper.addMappings(ordenMap);

		ConsultaOrdenesDto dto;
		dto = modelMapper.map(orden, ConsultaOrdenesDto.class);

		/*
		 * if (orden.getTipoServicio() != null) {
		 * dto.setTipoServicioNombre(orden.getTipoServicio().getNombre()); }
		 * 
		 * if (orden.getCliente() != null) {
		 * dto.setClienteCodigo(model.getCodigo());
		 * dto.setClienteNombre(model.getNombre()); }
		 * 
		 * if (orden.getConsolidadoId() != null) { Consolidado model =
		 * consolidadoRepository.findOne(orden.getConsolidadoId()); if (model !=
		 * null) { dto.setNumeroDocumentoConsolidadoCliente(model.
		 * getNumeroDocumentoConsolidadoCliente()); } }
		 * 
		 * if (orden.getCanalId() != null) { Canal model =
		 * canalRepository.findOne(orden.getCanalId()); if (model != null) {
		 * dto.setCanalCodigo(model.getCodigo());
		 * dto.setCanalNombre(model.getNombre()); } }
		 * 
		 * if (orden.getDestinatarioId() != null) { DestinatarioRemitente model
		 * = destinatarioRemitenteRepository.findOne(orden.getDestinatarioId());
		 * if (model != null) { dto.setDestinatarioCodigo(model.getCodigo());
		 * dto.setDestinatarioNombre(model.getNombre());
		 * dto.setDestinatarioNumeroIdentificacion(model.getNumeroIdentificacion
		 * ()); } }
		 * 
		 * if (orden.getDestinatarioDireccion().getCiudadId() != null) { Ciudad
		 * model =
		 * ciudadRepository.findOne(orden.getDestinatarioDireccion().getCiudadId
		 * ()); if (model != null) {
		 * dto.setDestinatarioCiudadNombre(model.getNombreAlterno()); } }
		 * 
		 * if (orden.getDestinoId() != null) { DestinoOrigen model =
		 * destinoOrigenRepository.findOne(orden.getDestinoId()); if (model !=
		 * null) { dto.setDestinoCodigo(model.getCodigo());
		 * dto.setDestinoNombre(model.getNombre()); } }
		 * 
		 * if (orden.getBodegaDestinoId() != null) { Bodega model =
		 * bodegaRepository.findOne(orden.getBodegaDestinoId()); if (model !=
		 * null) { dto.setDestinoCodigo(model.getCodigo());
		 * dto.setDestinoNombre(model.getNombre()); } }
		 * 
		 * if (orden.getDestinoDireccion().getCiudadId() != null) { Ciudad model
		 * =
		 * ciudadRepository.findOne(orden.getDestinoDireccion().getCiudadId());
		 * if (model != null) {
		 * dto.setDestinoCiudadNombre(model.getNombreAlterno()); } }
		 */
		return dto;
	}

	@Deprecated
	public OrdenDto findOneOrdenPorId(Integer id) throws DataAccessException {
		return ordenToViewModel(ordenRepository.findOne(id));
	}

	// ----------------------------------------------------------------------------------------------------------------
	// -- Configuración
	// ----------------------------------------------------------------------------------------------------------------
	@Deprecated
	public Object[] findAllConfiguracionClientePorTipoServicio(Integer clienteId, Integer tipoServicioId)
			throws DataAccessException {
		Cliente cliente = clienteRepository.findOne(clienteId);

		Map<String, Collection<Map<String, Object>>> config = new HashMap<String, Collection<Map<String, Object>>>(0);

		if (cliente != null) {
			if (cliente.isActivo()) {
				Map<Integer, Map<String, Object>> m = new HashMap<Integer, Map<String, Object>>(0);

				cliente.getRequerimientoClienteAssociation().forEach((a) -> {
					if (a.getTipoServicio().isActivo() && a.getTipoServicio().getId().equals(tipoServicioId)) {
						Requerimiento requerimiento = a.getRequerimiento();
						if (requerimiento.isActivo()) {
							if (!m.containsKey(requerimiento.getId())) {
								Map<String, Object> o = new HashMap<String, Object>();

								o.put("id", requerimiento.getId());
								o.put("codigo", requerimiento.getCodigo());
								o.put("nombre", requerimiento.getNombre());
								o.put("ordinal", 0);
								o.put("incluirSiempre", a.isIncluirSiempre());

								o.put("requerido", true);
								o.put("puedePersonalizarOpcionesDeArchivosAdjuntos",
										a.getPermisos().isPuedePersonalizarOpcionesDeArchivosAdjuntos());
								o.put("puedePersonalizarOpcionesParaDispositivosMoviles",
										a.getPermisos().isPuedePersonalizarOpcionesParaDispositivosMoviles());
								o.put("puedePersonalizarOpcionesParaRequerimientosDeCumplimiento",
										a.getPermisos().isPuedePersonalizarOpcionesParaRequerimientosDeCumplimiento());
								o.put("puedePersonalizarOpcionesParaRequerimientosDocumentales",
										a.getPermisos().isPuedePersonalizarOpcionesParaRequerimientosDocumentales());

								o.put("numeroMinimoDeArchivosAdjuntos",
										a.getOpciones().getNumeroMinimoDeArchivosAdjuntos());
								o.put("numeroMaximoDeArchivosAdjuntos",
										a.getOpciones().getNumeroMaximoDeArchivosAdjuntos());
								o.put("tamanoMaximoPorArchivoAdjuntos",
										a.getOpciones().getTamanoMaximoPorArchivoAdjuntos());

								o.put("habilitarOpcionParaAdjuntarArchivosEnDispositivosMoviles",
										a.getOpciones().isHabilitarOpcionParaAdjuntarArchivosEnDispositivosMoviles());

								o.put("textoPreguntaRequerimientoDeCumplimiento",
										a.getOpciones().getTextoPreguntaRequerimientoDeCumplimiento());
								o.put("requiereNotasEnCasoDeConformidad",
										a.getOpciones().isRequiereNotasEnCasoDeConformidad());
								o.put("requiereNotasEnCasoDeNoConformidad",
										a.getOpciones().isRequiereNotasEnCasoDeNoConformidad());

								o.put("requiereNumeroDeReferenciaDelDocumento",
										a.getOpciones().isRequiereNumeroDeReferenciaDelDocumento());
								o.put("requiereDocumentoFisico", a.getOpciones().isRequiereDocumentoFisico());

								m.put(requerimiento.getId(), o);
							}
						}
					}
				});

				config.put("requerimientosDocumentales", m.values());

				Map<Integer, Map<String, Object>> n = new HashMap<Integer, Map<String, Object>>(0);

				Map<String, Object> o;

				o = new HashMap<String, Object>();
				o.put("codigo", "AM");
				o.put("horaMinima", "05:30");
				o.put("horaMaxima", "12:00");
				n.put(1, o);

				o = new HashMap<String, Object>();
				o.put("codigo", "PM");
				o.put("horaMinima", "13:00");
				o.put("horaMaxima", "19:00");
				n.put(2, o);

				config.put("jornadas", n.values());
			}

		}

		return config.entrySet().toArray();
	}

	// ----------------------------------------------------------------------------------------------------------------
	// -- Ship To (Bodega Destino/Bodega Origen)
	// ----------------------------------------------------------------------------------------------------------------
	@Deprecated
	public List<Object> findAllBodegaPorCliente(Integer clienteId, Integer ciudadId, Integer tipoServicioId)
			throws DataAccessException {
		List<Object> list = new ArrayList<>();

		List<Bodega> entityList = bodegaRepository.findAllByClienteIdAndCiudadIdAndTipoServicioId(clienteId, ciudadId,
				tipoServicioId);

		entityList.forEach(a -> {
			list.add(bodegaToViewModel(a));
		});
		return list;
	}

	// ----------------------------------------------------------------------------------------------------------------
	// -- Lineas de Productos
	// ----------------------------------------------------------------------------------------------------------------
	@Deprecated
	public List<Object> findAllBodegaPorProductoPorCiudad(Integer productoId, Integer ciudadId)
			throws DataAccessException {
		List<Bodega> entityList = bodegaRepository.findAllByProductoIdAndCiudadId(productoId, ciudadId);

		List<Object> list = new ArrayList<>();
		entityList.forEach(a -> {
			list.add(bodegaToViewModel(a));
		});
		return list;
	}

	// ----------------------------------------------------------------------------------------------------------------
	// -- Lineas de Paquetes
	// ----------------------------------------------------------------------------------------------------------------
	@Deprecated
	public List<Object> findAllTipoContenido() throws DataAccessException {
		List<TipoContenido> entityList = tipoContenidoRepository.findAllByOrderByOrdinalAsc();

		List<Object> list = new ArrayList<>();
		entityList.forEach(a -> {
			list.add(tipoContenidoToViewModel(a));
		});

		return list;
	}

	// ----------------------------------------------------------------------------------------------------------------
	// -- Otros
	// ----------------------------------------------------------------------------------------------------------------
	@Deprecated
	public List<Object> findAllTipoFormaPagoPorClientePorTipoServicio(Integer clienteId, Integer tipoServicioId)
			throws DataAccessException {
		List<TipoFormaPago> entityList = tipoFormaPagoRepository.findAll();

		List<Object> list = new ArrayList<>();
		entityList.forEach(a -> {
			list.add(tipoFormaPagoToViewModel(a));
		});
		return list;
	}

	// -------------------------------------------------------------------------------------------------------------------------------------------------
	// -------------------------------------------------------------------------------------------------------------------------------------------------
	@Deprecated
	@Transactional(readOnly = false)
	public Orden saveOrden(DestinatarioDto dto) throws DataAccessException {
		OrdenDto ordenDto = new OrdenDto();
		ordenDto.setDatosFacturacion(dto);
		ordenDto.setUsuarioActualizacion(dto.getUsuario());

		return saveOrden(ordenDto, EstadoOrdenType.NO_CONFIRMADA);
	}

	@Deprecated
	@Transactional(readOnly = false)
	public Orden saveOrden(OrdenDto dto, EstadoOrdenType nuevoEstadoOrdenType) throws DataAccessException {
		Orden model = null;

		if (dto.getIdOrden() == null) {
			model = new Orden();
			// model.setTipoServicio(tipoServicioRepository.getOne(dto.getDatosFacturacion().getTipoServicio()));
			// model.setCliente(clienteRepository.getOne(dto.getDatosFacturacion().getCliente()));
		} else {
			model = ordenRepository.findOne(dto.getIdOrden());
		}

		// -------------------------------------------------------------------------------------------------------------------
		if (notEqualId(dto.getDatosFacturacion().getTipoServicio(),
				(model.getTipoServicio() == null) ? null : model.getTipoServicio().getId())) {

		}
		if (notEqualId(dto.getDatosFacturacion().getCliente(),
				(model.getCliente() == null) ? null : model.getCliente().getId())) {

		}

		// model.setDatosOrden(dto.getDatosFacturacion().getNumeroOrden(), null,
		// "", null);

		// -------------------------------------------------------------------------------------------------------------------
		// Consolidado consolidado;
		// CausalAnulacionOrden causalAnulacion;

		// -------------------------------------------------------------------------------------------------------------------
		if (notEqualId(dto.getDatosFacturacion().getSegmento(),
				(model.getCanal() == null) ? null : model.getCanal().getId())) {
			model.setCanal(canalRepository.getOne(dto.getDatosFacturacion().getSegmento()));
		}
		if (notEqualId(dto.getDatosFacturacion().getDestinatario(),
				(model.getDestinatario() == null) ? null : model.getDestinatario().getId())) {
			model.setDestinatario(terceroRepository.getOne(dto.getDatosFacturacion().getDestinatario()));
		}
		// TODO
		// model.setDatosDestinatario(null, canalCodigoAlterno, destinatario,
		// contacto);
		// model.cambiarDatosAlternosDestinatarioRemitente(
		// dto.getDatosFacturacion().getCodigoAlternoSegmento(),
		// dto.getDatosFacturacion().getNumeroIdentificacionAlternoDestinatario(),
		// dto.getDatosFacturacion().getCodigoAlternoDestinatario(),
		// dto.getDatosFacturacion().getNombreAlternoDestinatario());

		model.setDestinatarioContacto(new Contacto(dto.getDatosFacturacion().getNombre(),
				dto.getDatosFacturacion().getEmail(), dto.getDatosFacturacion().getTelefonos()));

		// -------------------------------------------------------------------------------------------------------------------
		if (notEqualId(dto.getDestinoOrigen().getDestinoId(),
				(model.getDestino() == null) ? null : model.getDestino().getId())) {
			if (dto.getDestinoOrigen().getDestinoId() != null) {
				model.setDestino(puntoRepository.getOne(dto.getDestinoOrigen().getDestinoId()));
			} else {
				model.setDestino(null);
			}
		}
		// TODO
		// model.setDatosPuntoDestino(ubicacion, contacto);
		// model.cambiarDatosAlternosDestino(
		// dto.getDestinoOrigen().getDestinoCodigoAlterno(),
		// dto.getDestinoOrigen().getDestinoNombreAlterno());

		// -------------------------------------------------------------------------------------------------------------------

		// -------------------------------------------------------------------------------------------------------------------
		// TODO HOMOLOGA4R PARTA BODEGAS, PERMITIR QUE LA BODEGA RECIBA SOLO LA
		// CIUDAD DESTINO
		// Ciudad ciudad = null;
		Integer ciudadId = null;
		if (model.getDestinoDireccion() != null) {
			if (model.getDestinoDireccion().getCiudad() != null) {
				if (model.getDestinoDireccion().getCiudad().getId() != null) {
					// ciudad = model.getDestinoDireccion().getCiudad();
					ciudadId = model.getDestinoDireccion().getCiudad().getId();
				}
			}
		}

		if (notEqualId(dto.getDestinoOrigen().getCiudadId(), ciudadId)) {
			if (dto.getDestinoOrigen().getCiudadId() != null) {
				ciudadId = dto.getDestinoOrigen().getCiudadId();
			}
		}

		Ciudad ciudad = null;
		if (ciudadId != null) {
			ciudad = ciudadRepository.getOne(ciudadId);
		}

		// model.setDestinoDireccion(new Direccion(ciudad,
		// dto.getDestinoOrigen().getDireccion(),
		// dto.getDestinoOrigen().getIndicacionesDireccion()));

		model.setDestinoContacto(new Contacto(dto.getDestinoOrigen().getContactoNombres(),
				dto.getDestinoOrigen().getContactoEmail(), dto.getDestinoOrigen().getContactoTelefonos()));

		// -------------------------------------------------------------------------------------------------------------------
		Time horaMinima = null;
		if (dto.getDatosEntregaRecogida().getHoraMinima() != null) {
			try {
				horaMinima = Time.valueOf(dto.getDatosEntregaRecogida().getHoraMinima() + ":00");
			} catch (IllegalArgumentException e) {

			}
		}
		// -------------------------------------------------------------------------------------------------------------------
		Time horaMaxima = null;
		if (dto.getDatosEntregaRecogida().getHoraMaxima() != null) {
			try {
				horaMaxima = Time.valueOf(dto.getDatosEntregaRecogida().getHoraMaxima() + ":00");
			} catch (IllegalArgumentException e) {

			}
		}

		model.setDatosCitaEntregaSugerida(dto.getDatosEntregaRecogida().getFechaMinima(),
				dto.getDatosEntregaRecogida().getFechaMaxima(), horaMinima, horaMaxima);

		// -------------------------------------------------------------------------------------------------------------------
		model.setValorRecaudo(dto.getDatosOtros().getValorDeclarado());
		model.setNotasConfirmacion(dto.getDatosOtros().getNotas());

		// TODO Aparte de los cargue por PDF donde mas se haria una
		// actualización de este tipo
		for (LineaOrdenDto m : dto.getLineas()) {
			updateLineaOrdenFromViewModel(model, m);
		}
		// TODO
		// model.cambiarEstado(nuevoEstadoOrdenType,
		// dto.getUsuarioActualizacion());

		return ordenRepository.save(model);
	}

	// -------------------------------------------------------------------------------------------------------------------------------------------------
	// -------------------------------------------------------------------------------------------------------------------------------------------------
	@Deprecated
	@Transactional(readOnly = false)
	public Orden saveLineaOrdenDeprecated(LineaOrdenDto model) throws DataAccessException {
		Orden entity = ordenRepository.findOne(model.getIdOrden());

		if (entity == null) {
			throw new RuntimeException("No se encontro la orden con id=" + model.getIdOrden());
		}

		updateLineaOrdenFromViewModel(entity, model);

		ordenRepository.save(entity);

		return entity;
	}

	@Deprecated
	@Transactional(readOnly = false)
	public Orden deleteLineaOrdenDeprecated(Integer ordenId, Integer lineaOrdenId, String usuario)
			throws DataAccessException {
		Orden entity = ordenRepository.findOne(ordenId);

		if (entity == null) {
			throw new RuntimeException("No se encontro la orden con id=" + ordenId);
		}

		LineaOrden e = entity.getLinea(lineaOrdenId);

		if (e == null) {
			throw new RuntimeException(
					"No se encontro la linea con id = " + lineaOrdenId + ",de la orden con id=" + ordenId);
		}

		entity.removeLinea(e);

		ordenRepository.save(entity);

		return entity;
	}

	// ----------------------------------------------------------------------------------------------------------------
	@Deprecated
	private Consolidado CrearConsolidado(ETLOrdenDto dto, Cliente cliente) {
		Consolidado model = new Consolidado();

		model.setCliente(cliente);
		model.setNumeroDocumentoConsolidadoCliente(dto.getNumeroConsolidado());
		model.setFechaActualizacion(new Date());
		model.setUsuarioActualizacion(dto.getClienteCodigo());

		return consolidadoRepository.save(model);
	}

	// ----------------------------------------------------------------------------------------------------------------
	@Deprecated
	public void updateLineaOrdenFromViewModel(Orden entity, LineaOrdenDto model) {
		LineaOrden e = null;

		if (model.getIdLineaOrden() == null) {
			e = new LineaOrden();
			entity.addLinea(e);
		} else {
			e = entity.getLinea(model.getIdLineaOrden());
		}

		if (notEqualId(model.getProducto(), (e.getProducto() == null) ? null : e.getProducto().getId())) {
			e.setProducto(productoRepository.findOne(model.getProducto()));
		}
		if (notEqualId(model.getUnidad(), (e.getUnidad() == null) ? null : e.getUnidad().getId())) {
			e.setUnidad(unidadRepository.findOne(model.getUnidad()));
		}
		if (notEqualId(model.getBodega(), (e.getBodegaOrigen() == null) ? null : e.getBodegaOrigen().getId())) {
			e.setBodegaOrigen(bodegaRepository.getOne(model.getBodega()));
		}
		if (notEqualId(model.getTipoContenido(),
				(e.getTipoContenido() == null) ? null : e.getTipoContenido().getId())) {
			e.setTipoContenido(tipoContenidoRepository.getOne(model.getTipoContenido()));
		}

		LineaOrdenViewModelAdpater.updateLineaOrdenFromViewModel(e, model);

		// entity.modificado(e.getUsuarioActualizacion());
	}

	@Deprecated
	protected boolean notEqualId(Integer a, Integer b) {
		if (a == null) {
			return (b != null);
		}
		return !a.equals(b);
	}

	// -------------------------------------------------------------------------------------------------------------------------------------------------
	@Deprecated
	protected Map<String, Object> estadoOrdenToViewModel(EstadoOrden entity) {
		Map<String, Object> o = new HashMap<String, Object>();

		o.put("id", entity.getEstadoOrden());
		o.put("nombre", entity.getDescripcion());
		o.put("ordinal", entity.getOrdinal());

		return o;
	}

	@Deprecated
	protected Map<String, Object> bodegaToViewModel(Bodega entity) {
		Map<String, Object> o = new HashMap<String, Object>();

		o.put("id", entity.getId());
		o.put("codigo", entity.getCodigo());
		o.put("nombre", entity.getNombre());
		//o.put("direccion", ciudadesService.direccionToViewModel(entity.getDireccion()));
		o.put("manejaRenta", entity.isManejaRenta());

		return o;
	}

	@Deprecated
	protected Map<String, Object> tipoContenidoToViewModel(TipoContenido entity) {
		Map<String, Object> o = new HashMap<String, Object>();

		o.put("id", entity.getId());
		o.put("codigo", entity.getCodigo());
		o.put("nombre", entity.getNombre());

		return o;
	}

	@Deprecated
	protected Map<String, Object> tipoFormaPagoToViewModel(TipoFormaPago entity) {
		Map<String, Object> o = new HashMap<String, Object>();

		o.put("id", entity.getId());
		o.put("codigo", entity.getCodigo());
		o.put("nombre", entity.getNombre());

		return o;
	}

	@Deprecated
	public OrdenDto ordenToViewModel(Orden entity) {
		if (entity == null) {
			return null;
		}
		System.out.println(Locale.getDefault().toString());

		OrdenDto model = new OrdenDto();

		model.setIdOrden(entity.getId());
		// ---------------------------------------------------------------------------------------------------------
		model.setDatosFacturacion(OrdenDatosFacturacionViewModelAdapter.ordenToViewModel(entity));
		model.setDestinoOrigen(OrdenDatosDestinoOrigenViewModelAdapter.ordenToViewModel(entity, ciudadRepository));
		model.setBodegaDestinoOrigen(OrdenDatosBodegaDestinoOrigenViewModelAdapter.ordenToViewModel(entity));
		model.setDatosEntregaRecogida(OrdenDatosEntregaRecogidaViewModelAdapter.ordenToViewModel(entity));
		model.setDatosOtros(OrdenDatosOtrosViewModelAdapter.ordenToViewModel(entity));
		// ---------------------------------------------------------------------------------------------------------
		model.setLineas(LineaOrdenViewModelAdpater.ordenToViewModel(entity));
		model.setMensajes(MensajeViewModelAdpater.ordenToViewModel(entity));
		// TODO CAMBIOS
		// TODO DOCUMENTOS
		// TODO REQUERIMIENTOS

		// ---------------------------------------------------------------------------------------------------------
		model.setFechaConfirmacion(entity.getFechaConfirmacion());
		model.setUsuarioConfirmacion(entity.getUsuarioConfirmacion());

		model.setFechaAceptacion(entity.getFechaAceptacion());
		model.setUsuarioAceptacion(entity.getUsuarioAceptacion());

		model.setFechaActualizacion(entity.getFechaActualizacion());
		model.setUsuarioActualizacion(entity.getUsuarioActualizacion());

		model.setNuevoEstadoOrden(entity.getEstadoOrden());

		return model;
	}

}
