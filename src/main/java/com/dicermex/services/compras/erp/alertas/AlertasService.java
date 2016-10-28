package com.dicermex.services.compras.erp.alertas;

import static com.dicermex.services.compras.Constantes.CODIGO_CLIENTE;
import static com.dicermex.services.compras.Constantes.CODIGO_SERVICIO_COMPRAS;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dicermex.domain.model.compras.LineaOrdenDeCompra;
import com.dicermex.domain.model.compras.OrdenDeCompra;
import com.dicermex.infrastructure.persistence.compras.OrdenDeCompraRepository;
import com.dicermex.services.compras.erp.alertas.dto.LineaOrdenDeCompraDTO;
import com.dicermex.services.compras.erp.alertas.dto.OrdenDeCompraDTO;
import com.tacticlogistics.application.dto.common.MensajesDTO;
import com.tacticlogistics.domain.model.common.SeveridadType;
import com.tacticlogistics.domain.model.common.valueobjects.Contacto;
import com.tacticlogistics.domain.model.common.valueobjects.Dimensiones;
import com.tacticlogistics.domain.model.crm.Cliente;
import com.tacticlogistics.domain.model.crm.ClienteBodegaAssociation;
import com.tacticlogistics.domain.model.crm.Servicio;
import com.tacticlogistics.domain.model.oms.EstadoAlmacenamientoType;
import com.tacticlogistics.domain.model.oms.EstadoCumplidosType;
import com.tacticlogistics.domain.model.oms.EstadoDistribucionType;
import com.tacticlogistics.domain.model.oms.EstadoNotificacionType;
import com.tacticlogistics.domain.model.oms.EstadoOrdenType;
import com.tacticlogistics.domain.model.oms.LineaOrden;
import com.tacticlogistics.domain.model.oms.Orden;
import com.tacticlogistics.domain.model.wms.Bodega;
import com.tacticlogistics.domain.model.wms.Producto;
import com.tacticlogistics.domain.model.wms.ProductoUnidadAssociation;
import com.tacticlogistics.domain.model.wms.Unidad;
import com.tacticlogistics.infrastructure.persistence.crm.ClienteRepository;
import com.tacticlogistics.infrastructure.persistence.crm.ServicioRepository;
import com.tacticlogistics.infrastructure.persistence.wms.BodegaRepository;
import com.tacticlogistics.infrastructure.persistence.wms.ProductoRepository;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.val;

@Service
@Transactional(readOnly = true)
public class AlertasService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ServicioRepository servicioRepository;

	@Autowired
	private BodegaRepository bodegaRepository;

	@Autowired
	private ProductoRepository productoRepository;

	@Autowired
	private OrdenDeCompraRepository comprasRepository;

	private static Cliente cliente;

	private static Servicio servicio;

	@Transactional
	public ResultadoAlertaDTO alertarOrdenDeCompra(OrdenDeCompraDTO dto) {
		Contexto ctx = new Contexto(getCliente());

		OrdenDeCompra compra = findOrdenDeCompra(dto);
		if (compra == null) {
			return preAlertarOrdenDeCompra(ctx, dto);
		} else {
			return confirmarOrdenDeCompra(ctx, dto, compra);
		}
	}

	// ------------------------------------------------------------------------
	// --Pre Alerta y Confirmación
	// ------------------------------------------------------------------------
	private OrdenDeCompra findOrdenDeCompra(OrdenDeCompraDTO dto) {
		// @formatter:off
		return comprasRepository
				.findFirstByOrdenClienteIdAndOrdenNumeroOrden(
					getCliente().getId(), 
					dto.getNumeroOrden());
		// @formatter:on
	}

	private ResultadoAlertaDTO preAlertarOrdenDeCompra(Contexto ctx, OrdenDeCompraDTO dto) {
		ResultadoAlertaDTO mensajes = new ResultadoAlertaDTO(ResultadoAlertaOrdenDeCompraType.CREADA);

		homologarDatosDelCliente(ctx, dto);
		if (validarNuevaOrdenDeCompra(ctx, dto, mensajes)) {
			OrdenDeCompra compra = crearOrdenDeCompra(ctx, dto);
			mensajes.add(SeveridadType.INFO, String.format("Orden %s creada", compra.getOrden().getNumeroOrden()));
		}

		return mensajes;
	}

	private ResultadoAlertaDTO confirmarOrdenDeCompra(Contexto ctx, OrdenDeCompraDTO dto, OrdenDeCompra compra) {
		ResultadoAlertaDTO mensajes = new ResultadoAlertaDTO(ResultadoAlertaOrdenDeCompraType.CONFIRMADA);

		homologarDatosDelCliente(ctx, dto);
		if (validarConfirmacionDeOrdenDeCompra(ctx, dto, compra, mensajes)) {
			OrdenDeCompra oc = realizarConfirmacionOrdenDeCompra(ctx, dto, compra);
			mensajes.add(SeveridadType.INFO, String.format("Orden %s confirmada", oc.getOrden().getNumeroOrden()));
		}

		return mensajes;
	}

	// ------------------------------------------------------------------------
	// --Homologaciones
	// ------------------------------------------------------------------------
	private void homologarDatosDelCliente(Contexto ctx, OrdenDeCompraDTO dto) {
		for (val e : dto.getLineas()) {
			homologarBodega(e.getBodegaId(), ctx);
			homologarProducto(e.getItemId(), ctx);
		}
	}

	private void homologarBodega(String bodegaCodigoAlterno, Contexto ctx) {
		Bodega bodega;
		String estadoInventario;
		int clienteId = ctx.getCliente().getId();

		if (!ctx.getBodegas().containsKey(bodegaCodigoAlterno)) {
			if (!bodegaCodigoAlterno.isEmpty()) {
				ClienteBodegaAssociation clienteBodega;
				clienteBodega = bodegaRepository.findFirstByClienteIdAndCodigoAlterno(clienteId, bodegaCodigoAlterno);
				if (clienteBodega != null) {
					bodega = bodegaRepository.findOne(clienteBodega.getBodegaId());
					estadoInventario = clienteBodega.getEstadoInventarioId();
					ctx.getBodegas().put(bodegaCodigoAlterno, bodega);
					ctx.getEstados().put(bodegaCodigoAlterno, estadoInventario);
				}
			}
		}
	}

	private void homologarProducto(String productoCodigo, Contexto ctx) {
		Producto producto = null;
		Unidad unidad = null;
		Dimensiones dimension = null;
		int clienteId = ctx.getCliente().getId();

		if (!ctx.getProductos().containsKey(productoCodigo)) {
			if (!productoCodigo.isEmpty()) {
				producto = productoRepository.findByClienteIdAndCodigo(clienteId, productoCodigo);
				if (producto != null) {
					Optional<ProductoUnidadAssociation> huella = Optional.empty();
					huella = producto.getProductoUnidadAssociation().stream().filter(a -> a.getNivel() == 1)
							.findFirst();
					if (huella.isPresent()) {
						unidad = huella.get().getUnidad();
						dimension = huella.get().getDimensiones();
					}
				}
				ctx.getProductos().put(productoCodigo, producto);
				ctx.getUnidades().put(productoCodigo, unidad);
				ctx.getDimensiones().put(productoCodigo, dimension);
			}
		}
	}

	// ------------------------------------------------------------------------
	// --Validaciones Nueva Orden
	// ------------------------------------------------------------------------

	// TODO QUE VALIDACIONES SE PODRIAN REALIZAR AL ENCABEZADO DE LA ORDEN?
	private boolean validarNuevaOrdenDeCompra(Contexto ctx, OrdenDeCompraDTO dto, ResultadoAlertaDTO mensajes) {
		boolean resultado = true;
		resultado &= validarLineasNuevaOrdenDeCompra(ctx, dto, mensajes);
		return resultado;
	}

	// ------------------------------------------------------------------------
	// --Validaciones COnfirmacion
	// ------------------------------------------------------------------------
	private boolean validarConfirmacionDeOrdenDeCompra(Contexto ctx, OrdenDeCompraDTO dto, OrdenDeCompra compra,
			ResultadoAlertaDTO mensajes) {
		boolean resultado = true;
		resultado &= validarSiOrdenAConfirmarEsUnaOrdenDeCompra(ctx, dto, compra, mensajes);
		resultado &= validarSiEstadoDeLaOrdenPermiteSuConfirmacion(ctx, dto, compra, mensajes);
		resultado &= validarLineasNuevaOrdenDeCompra(ctx, dto, mensajes);
		return resultado;
	}

	private boolean validarSiOrdenAConfirmarEsUnaOrdenDeCompra(Contexto ctx, OrdenDeCompraDTO dto, OrdenDeCompra compra,
			ResultadoAlertaDTO mensajes) {
		boolean resultado = true;

		if (compra == null) {
			String mensaje = String.format("La orden no corresponde a una orden de compra del cliente {0}",
					this.getCliente().getCodigo());

			mensajes.add(SeveridadType.ERROR, mensaje);

			resultado = false;
		}

		return resultado;
	}

	private boolean validarSiEstadoDeLaOrdenPermiteSuConfirmacion(Contexto ctx, OrdenDeCompraDTO dto,
			OrdenDeCompra compra, ResultadoAlertaDTO mensajes) {
		boolean resultado = true;

		if (compra != null) {
			if ((!compra.getOrden().getEstadoOrden().equals(EstadoOrdenType.NO_CONFIRMADA))) {
				String mensaje = "La orden se encuentra en el estado " + compra.getOrden().getEstadoOrden().toString()
						+ ".Solo es posible confirmar ordenes en estado " + EstadoOrdenType.NO_CONFIRMADA.toString();

				mensajes.add(SeveridadType.ERROR, mensaje);

				resultado = false;
			}
		}

		return resultado;
	}

	// ------------------------------------------------------------------------
	// --Validaciones Lineas
	// ------------------------------------------------------------------------

	private boolean validarLineasNuevaOrdenDeCompra(Contexto ctx, OrdenDeCompraDTO dto, ResultadoAlertaDTO mensajes) {
		boolean resultado = true;

		for (val e : dto.getLineas()) {
			resultado &= validarBodega(ctx, e, mensajes);
			resultado &= validarProducto(ctx, e, mensajes);
		}

		return resultado;
	}

	private boolean validarBodega(Contexto ctx, LineaOrdenDeCompraDTO e, ResultadoAlertaDTO mensajes) {
		boolean resultado = true;

		Bodega bodega = null;
		String estadoInventario = null;

		bodega = ctx.getBodegas().get(e.getBodegaId());
		estadoInventario = ctx.getEstados().get(e.getBodegaId());

		if (bodega == null) {
			mensajes.add(SeveridadType.ERROR,
					String.format(
							"La línea con número de registro %d, tiene el código de bodega destino \"%s\", el cual no se pudo homologar a una bodega valida.",
							e.getNumeroRegistro(), e.getBodegaId()));
			resultado = false;
		}
		if (estadoInventario == null) {
			mensajes.add(SeveridadType.ERROR,
					String.format(
							"La línea con número de registro %d, tiene el código de bodega destino \"%s\", el cual no se pudo homologar a un estado de inventario valido.",
							e.getNumeroRegistro(), e.getBodegaId()));
			resultado = false;
		}

		return resultado;
	}

	private boolean validarProducto(Contexto ctx, LineaOrdenDeCompraDTO e, ResultadoAlertaDTO mensajes) {
		boolean resultado = true;

		Producto producto = null;
		Unidad unidad = null;
		Dimensiones dimension = null;

		producto = ctx.getProductos().get(e.getItemId());
		unidad = ctx.getUnidades().get(e.getItemId());
		dimension = ctx.getDimensiones().get(e.getItemId());

		if (producto == null) {
			mensajes.add(SeveridadType.ERROR,
					String.format(
							"La línea con número de registro %d, tiene el código de producto \"%s\", el cual no existe.",
							e.getNumeroRegistro(), e.getItemId()));
			resultado = false;
		} else {
			if (unidad == null) {
				mensajes.add(SeveridadType.ERROR,
						String.format(
								"La línea con número de registro %d, tiene el código de producto \"%s\", el cual no tiene una huella de primer nivel.",
								e.getNumeroRegistro(), e.getItemId()));
				resultado = false;
			}
			if (dimension == null) {
				mensajes.add(SeveridadType.ERROR,
						String.format(
								"La línea con número de registro %d, tiene el código de producto \"%s\", el cual no tiene dimensiones.",
								e.getNumeroRegistro(), e.getItemId()));
				resultado = false;
			}
		}
		return resultado;
	}

	// ------------------------------------------------------------------------
	// --Creación
	// ------------------------------------------------------------------------
	private OrdenDeCompra crearOrdenDeCompra(Contexto ctx, OrdenDeCompraDTO dto) {

		dto.setFechaActualizacion(LocalDateTime.now());
		dto.generarNumerosDeItems();

		Orden orden = crearOrden(ctx, dto);
		List<LineaOrdenDeCompra> lineas = crearLineasOrdenDeCompra(ctx, dto);

		//@formatter:off
		OrdenDeCompra compra = OrdenDeCompra
				.builder()
				.orden(orden)
				.centroOperacion(dto.getCentroOperacion())
				.consecutivoDocumento(dto.getConsecutivoDocumento())
				.fechaDocumento(dto.getFechaDocumento().toString())
				.terceroProveedor(dto.getTerceroProveedor())
				.notasDocumento(dto.getNotasDocumento())
				.sucursalProveedor(dto.getSucursalProveedor())
				.terceroCompradorId(dto.getTerceroCompradorId())
				.monedaDocumento(dto.getMonedaDocumento())
				.monedaConversion(dto.getMonedaConversion())
				.centroOperacionOrdenCompra(dto.getCentroOperacionOrdenCompra())
				.tipoDocumentoOrdenCompra(dto.getTipoDocumentoOrdenCompra())
				.consecutivoDocumentoOrdenCompra(dto.getConsecutivoDocumentoOrdenCompra())
				.build();
		compra.lineasReplaceAll(lineas);
		//@formatter:on

		return comprasRepository.saveAndFlush(compra);
	}

	private Orden crearOrden(Contexto ctx, OrdenDeCompraDTO dto) {
		List<LineaOrden> lineas = crearLineasOrden(ctx, dto);

		Contacto contacto = new Contacto("", "", "");

		//@formatter:off
		Orden orden = Orden
				.builder()
				.numeroOrden(dto.getNumeroOrden())
				.fechaOrden(dto.getFechaOrdenFromFechaDocumento())
				
				.estadoOrden(EstadoOrdenType.NO_CONFIRMADA)
				.estadoDistribucion(EstadoDistribucionType.NO_PLANIFICADA)
				.estadoAlmacenamiento(EstadoAlmacenamientoType.NO_ALERTADA)
				.estadoCumplidos(EstadoCumplidosType.NO_REPORTADOS)
				.estadoNotificacion(EstadoNotificacionType.SIN_NOTIFICAR)
				
				.cliente(ctx.getCliente())
				.clienteCodigo(ctx.getCliente().getCodigo())
				.servicio(getServicio())
				.servicioCodigoAlterno("")
				.requiereServicioDistribucion(false)
				
				.requiereConfirmacionCita(false)
				.fechaSugeridaMinima(null)
				.fechaSugeridaMaxima(null)
				.horaSugeridaMinima(null)
				.horaSugeridaMaxima(null)
				
				.notasConfirmacion(dto.getNotasDocumento())
				.fechaConfirmacion(null)
				.usuarioConfirmacion("")
				
				.fechaCreacion(dto.getFechaActualizacion())
				.usuarioCreacion(dto.getUsuarioActualizacion())
				.fechaActualizacion(dto.getFechaActualizacion())
				.usuarioActualizacion(dto.getUsuarioActualizacion())
				
				.numeroOrdenCompra("")
				
				.ciudadDestinoNombre("")
				.destinoDireccion("")
				.destinoIndicaciones("")
				
				.ciudadOrigenNombre("")
				.origenDireccion("")
				.origenIndicaciones("")
				
				.canalCodigoAlterno("")
				.destinatarioNumeroIdentificacion("")
				.destinatarioNombre("")
				.destinatarioContacto(contacto)
				
				.destinoCodigo("")
				.destinoNombre("")
				.destinoContacto(contacto)
				
				.origenCodigo("")
				.origenNombre("")
				.origenContacto(contacto)
				
				.usuarioAsignacionCita("")
				
				.notasAceptacion("")
				.usuarioAceptacion("")
				
				.usuarioCorteRuta("")
				.usuarioAsignacionRuta("")
				
				.notasAnulacion("")
				.usuarioAnulacion("")
				
				.notasReprogramacion("")
				.usuarioReprogramacion("")
				
				.build();
		orden.lineasReplaceAll(lineas);
		//@formatter:on

		return orden;

	}

	// ------------------------------------------------------------------------
	// -- Modificacion
	// ------------------------------------------------------------------------
	private OrdenDeCompra realizarConfirmacionOrdenDeCompra(Contexto ctx, OrdenDeCompraDTO dto, OrdenDeCompra compra) {

		dto.setFechaActualizacion(LocalDateTime.now());
		dto.generarNumerosDeItems();

		realizarConfirmacionOrden(ctx, dto, compra.getOrden());
		List<LineaOrden> lo = crearLineasOrden(ctx, dto);
		List<LineaOrdenDeCompra> loc = crearLineasOrdenDeCompra(ctx, dto);

		// @formatter:off
		compra.setFechaDocumento(compra.getFechaDocumento().toString());
		compra.setTerceroProveedor(compra.getTerceroProveedor());
		compra.setNotasDocumento(compra.getNotasDocumento());
		compra.setSucursalProveedor(compra.getSucursalProveedor());
		compra.setTerceroCompradorId(compra.getTerceroCompradorId());
		compra.setMonedaDocumento(compra.getMonedaDocumento());
		compra.setMonedaConversion(compra.getMonedaConversion());
		compra.setCentroOperacionOrdenCompra(compra.getCentroOperacionOrdenCompra());
		compra.setConsecutivoDocumentoOrdenCompra(compra.getConsecutivoDocumentoOrdenCompra());
		// @formatter:on

		compra.getOrden().lineasClear();
		compra.lineasClear();
		comprasRepository.saveAndFlush(compra);

		compra.getOrden().lineasReplaceAll(lo);
		compra.lineasReplaceAll(loc);

		return comprasRepository.saveAndFlush(compra);
	}

	private void realizarConfirmacionOrden(Contexto ctx, OrdenDeCompraDTO dto, Orden orden) {
		// @formatter:off
		orden.setFechaOrden(dto.getFechaOrdenFromFechaDocumento());
		orden.setEstadoOrden(EstadoOrdenType.CONFIRMADA);
		orden.setNotasConfirmacion(dto.getNotasDocumento());
		orden.setFechaConfirmacion(dto.getFechaActualizacion());
		orden.setUsuarioConfirmacion(dto.getUsuarioActualizacion());
		orden.setFechaActualizacion(dto.getFechaActualizacion());
		orden.setUsuarioActualizacion(dto.getUsuarioActualizacion());
		// @formatter:on
	}

	// ------------------------------------------------------------------------
	// -- Creación de Lineas
	// ------------------------------------------------------------------------
	private List<LineaOrden> crearLineasOrden(Contexto ctx, OrdenDeCompraDTO dto) {
		List<LineaOrden> list = new ArrayList<>();

		for (val e : dto.getLineas()) {
			list.add(crearLineaOrden(ctx, dto, e));
		}

		return list;
	}

	private LineaOrden crearLineaOrden(Contexto ctx, OrdenDeCompraDTO dto, LineaOrdenDeCompraDTO e) {
		Bodega bodega = null;
		String estadoInventario = null;
		Producto producto = null;
		Unidad unidad = null;
		Dimensiones dimension = null;

		bodega = ctx.getBodegas().get(e.getBodegaId());
		estadoInventario = ctx.getEstados().get(e.getBodegaId());
		producto = ctx.getProductos().get(e.getItemId());
		unidad = ctx.getUnidades().get(e.getItemId());
		dimension = ctx.getDimensiones().get(e.getItemId());

		// @formatter:off
		return LineaOrden
				.builder()
				.numeroItem(e.getNumeroRegistro())
				.descripcion(producto.getNombre())
				.cantidadSolicitada(e.getCantidad())
				.cantidadPlanificada(e.getCantidad())
				.producto(producto)
				.productoCodigo(producto.getCodigo())
				.unidad(unidad)
				.unidadCodigo(unidad.getCodigo())
				.dimensiones(dimension)
				
				.bodegaDestino(bodega)
				.bodegaDestinoCodigo(bodega.getCodigo())
				.bodegaDestinoCodigoAlterno(e.getBodegaId())
				.estadoInventarioDestinoId(estadoInventario)
				.numeroOrdenWmsDestino("")
				
				.lote(e.getLoteId())
				.notas(e.getNotasMovimiento())
				.fechaCreacion(dto.getFechaActualizacion())
				.usuarioCreacion(dto.getUsuarioActualizacion())
				.fechaActualizacion(dto.getFechaActualizacion())
				.usuarioActualizacion(dto.getUsuarioActualizacion())

				.tipoContenidoCodigo("")
				.bodegaOrigenCodigo("")
				.bodegaOrigenCodigoAlterno("")
				.estadoInventarioOrigenId("")
				.numeroOrdenWmsOrigen("")
				
				.serial("")
				.cosecha("")
				.requerimientoEstampillado("")
				.requerimientoSalud("")
				.requerimientoImporte("")
				.requerimientoDistribuido("")
				.requerimientoNutricional("")
				.requerimientoBl("")
				.requerimientoFondoCuenta("")
				.requerimientoOrigen("")
				.requerimientoPinado("")
				.predistribucionDestinoFinal("")
				.predistribucionRotulo("")
				.build();
		// @formatter:on
	}

	private List<LineaOrdenDeCompra> crearLineasOrdenDeCompra(Contexto ctx, OrdenDeCompraDTO dto) {
		List<LineaOrdenDeCompra> list = new ArrayList<>();

		for (val e : dto.getLineas()) {
			list.add(crearLineaOrdenDeCompra(ctx, dto, e));
		}

		return list;
	}

	private LineaOrdenDeCompra crearLineaOrdenDeCompra(Contexto ctx, OrdenDeCompraDTO dto, LineaOrdenDeCompraDTO e) {
		//@formatter:off
		return LineaOrdenDeCompra
			.builder()
			.centroOperacion(e.getCentroOperacion())
			.consecutivoDocumento(e.getConsecutivoDocumento())
			.numeroRegistro(e.getNumeroRegistro())
			.bodegaId(e.getBodegaId())
			.ubicacionId(e.getUbicacionId())
			.loteId(e.getLoteId())
			.unidadMedida(e.getUnidadMedida())
			.fechaEntrega(e.getFechaEntrega())
			.cantidad(e.getCantidad())
			.notasMovimiento(e.getNotasMovimiento())
			.itemId(e.getItemId())
			.talla(e.getTalla())
			.color(e.getColor())
			.centroCosto(e.getCentroCosto())
			.proyectoId(e.getProyectoId())
			.build();
		//@formatter:on
	}

	// ------------------------------------------------------------------------
	// --Otros
	// ------------------------------------------------------------------------
	private Cliente getCliente() {
		if (cliente == null) {
			cliente = clienteRepository.findByCodigoIgnoringCase(CODIGO_CLIENTE);
		}
		return cliente;
	}

	private Servicio getServicio() {
		if (servicio == null) {
			servicio = servicioRepository.findByCodigoIgnoringCase(CODIGO_SERVICIO_COMPRAS);
		}
		return servicio;
	}

	// ------------------------------------------------------------------------
	// --Contexto
	// ------------------------------------------------------------------------
	@Data
	private static class Contexto {
		public Contexto(Cliente cliente) {
			this.cliente = cliente;
		}

		@Setter(AccessLevel.NONE)
		Cliente cliente;
		@Setter(AccessLevel.NONE)
		Map<String, Bodega> bodegas = new TreeMap<>();
		@Setter(AccessLevel.NONE)
		Map<String, String> estados = new TreeMap<>();
		@Setter(AccessLevel.NONE)
		Map<String, Producto> productos = new TreeMap<>();
		@Setter(AccessLevel.NONE)
		Map<String, Unidad> unidades = new TreeMap<>();
		@Setter(AccessLevel.NONE)
		Map<String, Dimensiones> dimensiones = new TreeMap<>();
	}

	public static enum ResultadoAlertaOrdenDeCompraType {
		CREADA, CONFIRMADA, ERROR
	}

	public static class ResultadoAlertaDTO extends MensajesDTO<ResultadoAlertaOrdenDeCompraType> {
		public ResultadoAlertaDTO(ResultadoAlertaOrdenDeCompraType resultado) {
			this.setData(resultado);
		}

		@Override
		public ResultadoAlertaOrdenDeCompraType getData() {
			if (this.hasErrors()) {
				return ResultadoAlertaOrdenDeCompraType.ERROR;
			}
			return super.getData();
		}
	}

}
