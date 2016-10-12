package com.tacticlogistics.application.services.clientes.dicermex;

import static org.springframework.data.domain.ExampleMatcher.matching;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tacticlogistics.application.dto.common.MensajesDto;
import com.tacticlogistics.domain.model.clientes.dicermex.LineaOrdenDeCompra;
import com.tacticlogistics.domain.model.clientes.dicermex.OrdenDeCompra;
import com.tacticlogistics.domain.model.common.SeveridadType;
import com.tacticlogistics.domain.model.common.valueobjects.Contacto;
import com.tacticlogistics.domain.model.common.valueobjects.Dimensiones;
import com.tacticlogistics.domain.model.crm.Cliente;
import com.tacticlogistics.domain.model.crm.ClienteBodegaAssociation;
import com.tacticlogistics.domain.model.crm.TipoServicio;
import com.tacticlogistics.domain.model.oms.EstadoAlmacenamientoType;
import com.tacticlogistics.domain.model.oms.EstadoCumplidosType;
import com.tacticlogistics.domain.model.oms.EstadoDistribucionType;
import com.tacticlogistics.domain.model.oms.EstadoOrdenType;
import com.tacticlogistics.domain.model.ordenes.LineaOrden;
import com.tacticlogistics.domain.model.ordenes.Orden;
import com.tacticlogistics.domain.model.wms.Bodega;
import com.tacticlogistics.domain.model.wms.Producto;
import com.tacticlogistics.domain.model.wms.ProductoUnidadAssociation;
import com.tacticlogistics.domain.model.wms.Unidad;
import com.tacticlogistics.infrastructure.persistence.clientes.dicermex.OrdenDeCompraRepository;
import com.tacticlogistics.infrastructure.persistence.crm.ClienteRepository;
import com.tacticlogistics.infrastructure.persistence.crm.TipoServicioRepository;
import com.tacticlogistics.infrastructure.persistence.ordenes.OrdenRepository;
import com.tacticlogistics.infrastructure.persistence.wms.BodegaRepository;
import com.tacticlogistics.infrastructure.persistence.wms.ProductoRepository;
import com.tacticlogistics.presentation.api.clientes.dicermex.compras.LineaOrdenDeCompraDto;
import com.tacticlogistics.presentation.api.clientes.dicermex.compras.OrdenDeCompraDto;

import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@Slf4j
public class OrdenesDeCompraService {

	private static final String CODIGO_CLIENTE = "DICERMEX";

	private static final String CODIGO_SERVICIO = "COMPRAS";

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private TipoServicioRepository tipoServicioRepository;

	@Autowired
	private BodegaRepository bodegaRepository;

	@Autowired
	private ProductoRepository productoRepository;

	@Autowired
	private OrdenRepository ordenRepository;

	@Autowired
	private OrdenDeCompraRepository ordenDeCompraRepository;

	private static Cliente cliente;

	private static TipoServicio servicio;

	public MensajesDto preAlertarOrConfirmarOrdenDeCompra(OrdenDeCompraDto dto) {
		Orden compra = ordenRepository.findFirstByClienteIdAndNumeroOrden(getCliente().getId(), dto.getNumeroOrden());

		if (compra == null) {
			return this.preAlertarOrdenDeCompra(dto);
		} else {
			return this.confirmarOrdeDeCompra(dto, compra.getId());
		}
	}

	public List<Orden> getOrdenesDeCompraPendientesPorAlertarAlWms() {
		TipoServicio servicio = new TipoServicio();
		servicio.setId(this.getServicio().getId());

		//@formatter:off
		Orden probe = Orden
				.builder()
				.tipoServicio(servicio)
				.requiereServicioDistribucion(false)
				.estadoOrden(EstadoOrdenType.ACEPTADA)
				.estadoAlmacenamiento(EstadoAlmacenamientoType.NO_ALERTADA)
				.build();
		
		ExampleMatcher matcher = matching()
				.withIgnorePaths("requiereConfirmacionCitaEntrega")  
				.withIgnorePaths("requiereConfirmacionCitaRecogida")
				.withIgnorePaths("tipoServicio.admiteBodegasComoDestino")
				.withIgnorePaths("tipoServicio.admiteBodegasComoOrigen")
				.withIgnorePaths("tipoServicio.admiteDireccionesComoDestino")
				.withIgnorePaths("tipoServicio.admiteDireccionesComoOrigen")
				.withIgnorePaths("tipoServicio.activo")
				.withIgnorePaths("tipoServicio.ordinal");
		//@formatter:on

		List<Orden> ordenes = ordenRepository.findAll(Example.of(probe, matcher));

		for (Orden e : ordenes) {
			Cliente cliente = e.getCliente();
			log.info("load {}", cliente.getCodigoAlternoWms());
			Set<LineaOrden> lineas = e.getLineas();
			for (LineaOrden l : lineas) {
				log.info("load {}", l.getNumeroItem());
			}
		}
		return ordenes;
	}

	// TODO INCLUIR FECHA Y HORA DE PRE ALERTA AL WMS
	@Transactional(readOnly = false)
	public void preAlertarOrdenesDeCompraAlWms(List<Integer> ordenesId) {
		for (Integer id : ordenesId) {
			Orden compra = ordenRepository.findOne(id);
			if (compra != null) {
				compra.setEstadoOrden(EstadoOrdenType.EJECUCION);
				compra.setEstadoAlmacenamiento(EstadoAlmacenamientoType.ALERTADA_NO_CONFIRMADA);
				compra.setDatosActualizacion(LocalDateTime.now(), "INTEGRACION TC-WMS");
				for (val e : compra.getLineas()) {
					e.setNumeroOrdenWmsDestino("TC-" + compra.getId() + '-' + compra.getNumeroOrden());
				}
				ordenRepository.save(compra);
			}
		}
	}

	// TODO INCLUIR COOLUMNA PARA EL MENSAJE DE ERROR
	@Transactional(readOnly = false)
	public void confirmarAlertaDeOrdenesDeCompraAlWms(List<ResultadoPreAlertaOrdenDeCompraDto> resultados) {
		for (val e : resultados) {
			String partes[] = e.getNumeroOrdenWms().split("-");
			int id = Integer.parseInt(partes[1]);

			Orden compra = ordenRepository.findOne(id);
			if (compra != null) {
				if (e.getResultado() == ResultadoPreAlertaType.OK) {
					compra.setEstadoAlmacenamiento(EstadoAlmacenamientoType.ALERTADA);
				} else {
					compra.setEstadoAlmacenamiento(EstadoAlmacenamientoType.ALERTADA_CON_ERROR);
				}
				compra.setDatosActualizacion(LocalDateTime.now(), "INTEGRACION TC-WMS");
				ordenRepository.save(compra);
			}
		}
	}

	@Transactional(readOnly = false)
	public void confirmarReciboDeOrdenesDeCompra(List<ResultadoAlmacenamientoOrdenDeCompraDto> resultados) {
		for (val e : resultados) {
			String partes[] = e.getNumeroOrdenWms().split("-");
			int id = Integer.parseInt(partes[1]);

			Orden compra = ordenRepository.findOne(id);
			if (compra != null) {
				String clienteCodigoWms = compra.getCliente().getCodigoAlternoWms();
				String bodegaDestinoCodigo = compra.getLineas().stream().findFirst().get().getBodegaDestinoCodigo();
				// val max = compra.getLineas().stream().mapToInt((x) ->
				// a.getNumeroItem()).summaryStatistics().getMax();
				int secuenciaNumeroItem = 9000;

				if (!clienteCodigoWms.equals(e.getClienteCodigoWms())) {
					throw new RuntimeException(
							"Orden " + e.getNumeroOrdenWms() + ":" + "Cliente diferente al original");
				}

				if (!bodegaDestinoCodigo.equals(e.getBodegaCodigo())) {
					throw new RuntimeException("Orden " + e.getNumeroOrdenWms() + ":" + "Bodega diferente al original");
				}

				if (!compra.getEstadoAlmacenamiento().equals(EstadoAlmacenamientoType.ALERTADA)) {
					throw new RuntimeException("Orden " + e.getNumeroOrdenWms() + ":" + "Se encuentra en el estado "
							+ compra.getEstadoAlmacenamiento().toString() + ". La orden debe estar en estado "
							+ EstadoAlmacenamientoType.ALERTADA.toString());
				}
				// Validar lineas y sublineas no repetidas
				for (val l : e.getLineas()) {
					if (l.getNumeroSubLinea() == 0) {
						// UPDATE
						val linea = compra.getLineas().stream().filter(a -> a.getNumeroItem() == l.getNumeroItem())
								.findFirst();
						if (!linea.isPresent()) {
							throw new RuntimeException("Orden " + e.getNumeroOrdenWms() + "Linea " + l.getNumeroItem()
									+ ":" + "No existe el numero de linea");
						}

						if (linea.get().getCantidadPlanificada() != l.getCantidadPlanificada()) {
							throw new RuntimeException("Orden " + e.getNumeroOrdenWms() + "Linea " + l.getNumeroItem()
									+ ":" + "La cantidad planificada no es igual");
						}

						update(compra.getCliente().getId(),linea.get(), l.getProductoCodigo(), l.getEstadoInventarioId(), l.getCantidadReal());
					} else {
						secuenciaNumeroItem++;
						// INSERT
					}
				}
				// TODO NOVEDADES
				compra.setEstadoAlmacenamiento(EstadoAlmacenamientoType.ALISTADA);
				compra.setDatosActualizacion(LocalDateTime.now(), "INTEGRACION TC-WMS");
				ordenRepository.save(compra);
			} else {
				throw new RuntimeException("Orden " + e.getNumeroOrdenWms() + ":" + "Orden no existe");
			}
		}
	}

	private void update(int clienteId, LineaOrden linea, String productoCodigo, String estadoInventarioId,
			int cantidadRecibida) {
		// VALIDAR ESTADO INVENTARIO
		Producto producto = null;
		Optional<ProductoUnidadAssociation> huella = Optional.empty();
		Unidad unidad = null;
		Dimensiones dimensiones = null;

		producto = productoRepository.findByClienteIdAndCodigo(clienteId, productoCodigo);
		if (producto != null) {
			huella = producto.getProductoUnidadAssociation().stream().filter(a -> a.getNivel() == 1).findFirst();
			if (huella.isPresent()) {
				unidad = huella.get().getUnidad();
				dimensiones = huella.get().getDimensiones();
			}
		}

		if (estadoInventarioId == null) {
			throw new RuntimeException(String.format(
					"La línea con número de registro %d, tiene el código de bodega destino \"%s\", el cual no se pudo homologar a un estado de inventario valido.",
					linea.getNumeroItem(), estadoInventarioId));
		}
		if (producto == null) {
			throw new RuntimeException(String.format(
					"La línea con número de registro %d, tiene el código de producto \"%s\", el cual no existe.",
					linea.getNumeroItem(), productoCodigo));
		} else {
			if (unidad == null) {
				throw new RuntimeException(String.format(
						"La línea con número de registro %d, tiene el código de producto \"%s\", el cual no tiene una huella de primer nivel.",
						linea.getNumeroItem(), productoCodigo));
			}
			if (dimensiones == null) {
				throw new RuntimeException(String.format(
						"La línea con número de registro %d, tiene el código de producto \"%s\", el cual no tiene dimensiones.",
						linea.getNumeroItem(), productoCodigo));
			}
		}

		linea.setProducto(producto);
		linea.setDescripcion(producto.getNombreLargo());
		linea.setProductoCodigo(productoCodigo);
		linea.setUnidad(unidad);
		linea.setUnidadCodigo(unidad.getCodigo());
		linea.setDimensiones(dimensiones);

		linea.setEstadoInventarioDestinoId(estadoInventarioId);
		linea.setCantidadEntregada(cantidadRecibida);

		linea.setUsuarioCreacion("INTEGRACION WMS-TC");
		linea.setFechaActualizacion(LocalDateTime.now());
	}

	// -------------------------------------------------------------------------------------------
	@Transactional(readOnly = false)
	private MensajesDto preAlertarOrdenDeCompra(OrdenDeCompraDto dto) {
		final MensajesDto mensajes = new MensajesDto();

		final OrdenDeCompra compraCliente = validarNuevaOrdenDeCompraCliente(dto, mensajes);

		try {
			ordenDeCompraRepository.saveAndFlush(compraCliente);

			mensajes.addMensaje(SeveridadType.INFO, "Orden creada exitosamente");
		} catch (Exception e) {
			mensajes.addMensaje(e);
		}

		return mensajes;
	}

	@Transactional(readOnly = false)
	private MensajesDto confirmarOrdeDeCompra(OrdenDeCompraDto dto, int compraId) {
		final MensajesDto mensajes = new MensajesDto();
		final LocalDateTime fechaUpd = LocalDateTime.now();
		final String usuarioUpd = dto.getTerceroCompradorId();

		final OrdenDeCompra ordenDeCompra = ordenDeCompraRepository.findOne(compraId);

		if (ordenDeCompra == null) {
			mensajes.addMensaje(SeveridadType.ERROR,
					"La orden no corresponde a una orden de compra del cliente " + this.getCliente().getCodigo());
		}
		if (!ordenDeCompra.getOrden().getEstadoOrden().equals(EstadoOrdenType.NO_CONFIRMADA)) {
			mensajes.addMensaje(SeveridadType.ERROR,
					"La orden se encuentra en el estado " + ordenDeCompra.getOrden().getEstadoOrden().toString()
							+ ".Solo es posible confirmar ordenes en estado "
							+ EstadoOrdenType.NO_CONFIRMADA.toString());
		}

		val lineasOC = validarLineasOrdenDeCompra(dto.getLineas(), fechaUpd, usuarioUpd, mensajes);
		val lineasOCCliente = validarLineasOrdenDeCompraCliente(dto.getLineas(), mensajes);

		if (mensajes.getSeveridadMaxima().equals(SeveridadType.ERROR)) {
			return mensajes;
		}

		Orden compra = ordenDeCompra.getOrden();
		{
			compra.getLineas().clear();

			compra.setFechaOrden(dto.getFechaOrdenFromFechaDocumento());
			compra.setEstadoOrden(EstadoOrdenType.CONFIRMADA);
			compra.setNotasConfirmacion(dto.getNotasDocumento());
			compra.setFechaConfirmacion(fechaUpd);
			compra.setUsuarioConfirmacion(usuarioUpd);
			compra.setFechaActualizacion(fechaUpd);
			compra.setUsuarioActualizacion(usuarioUpd);
		}

		{
			ordenDeCompra.getLineas().clear();

			ordenDeCompra.setFechaDocumento(dto.getFechaDocumento());
			ordenDeCompra.setTerceroProveedor(dto.getTerceroProveedor());
			ordenDeCompra.setNotasDocumento(dto.getNotasDocumento());
			ordenDeCompra.setSucursalProveedor(dto.getSucursalProveedor());
			ordenDeCompra.setTerceroCompradorId(dto.getTerceroCompradorId());
			ordenDeCompra.setNumeroDocumentoReferencia(dto.getNumeroDocumentoReferencia());
			ordenDeCompra.setMonedaDocumento(dto.getMonedaDocumento());
			ordenDeCompra.setMonedaConversion(dto.getMonedaConversion());
			ordenDeCompra.setCentroOperacionOrdenCompra(dto.getCentroOperacionOrdenCompra());
			ordenDeCompra.setConsecutivoDocumentoOrdenCompra(dto.getConsecutivoDocumentoOrdenCompra());
		}

		val oc = ordenDeCompraRepository.saveAndFlush(ordenDeCompra);

		oc.getOrden().getLineas().addAll(lineasOC);
		oc.getLineas().addAll(lineasOCCliente);

		ordenDeCompraRepository.saveAndFlush(oc);

		mensajes.addMensaje(SeveridadType.INFO, "Orden confirmada exitosamente");

		return mensajes;
	}

	private OrdenDeCompra validarNuevaOrdenDeCompraCliente(final OrdenDeCompraDto dto, final MensajesDto mensajes) {
		final LocalDateTime fechaUpd = LocalDateTime.now();
		final String usuarioUpd = dto.getTerceroCompradorId();

		final Orden compra = validarNuevaOrdenDeCompra(dto, fechaUpd, usuarioUpd, mensajes);
		if (mensajes.getSeveridadMaxima().equals(SeveridadType.ERROR)) {
			return null;
		}

		final Set<LineaOrdenDeCompra> lineas = validarLineasOrdenDeCompraCliente(dto.getLineas(), mensajes);
		if (mensajes.getSeveridadMaxima().equals(SeveridadType.ERROR)) {
			return null;
		}

		//@formatter:off
		OrdenDeCompra ordenDeCompra = OrdenDeCompra
				.builder()
				.orden(compra)
				.centroOperacion(dto.getCentroOperacion())
				.consecutivoDocumento(dto.getConsecutivoDocumento())
				.fechaDocumento(dto.getFechaDocumento())
				.terceroProveedor(dto.getTerceroProveedor())
				.notasDocumento(dto.getNotasDocumento())
				.sucursalProveedor(dto.getSucursalProveedor())
				.terceroCompradorId(dto.getTerceroCompradorId())
				.numeroDocumentoReferencia(dto.getNumeroDocumentoReferencia())
				.monedaDocumento(dto.getMonedaDocumento())
				.monedaConversion(dto.getMonedaConversion())
				.centroOperacionOrdenCompra(dto.getCentroOperacionOrdenCompra())
				.tipoDocumentoOrdenCompra(dto.getTipoDocumentoOrdenCompra())
				.consecutivoDocumentoOrdenCompra(dto.getConsecutivoDocumentoOrdenCompra())
				.lineas(lineas)
				.build();
		//@formatter:on

		return ordenDeCompra;
	}

	private Orden validarNuevaOrdenDeCompra(final OrdenDeCompraDto dto, final LocalDateTime fechaUpd,
			final String usuarioUpd, final MensajesDto mensajes) {
		final Contacto contacto = new Contacto("", "", "");

		val lineas = validarLineasOrdenDeCompra(dto.getLineas(), fechaUpd, usuarioUpd, mensajes);

		//@formatter:off
		final Orden orden = Orden
				.builder()
				.numeroOrden(dto.getNumeroOrden())
				.fechaOrden(dto.getFechaOrdenFromFechaDocumento())
				.estadoOrden(EstadoOrdenType.NO_CONFIRMADA)
				.estadoDistribucion(EstadoDistribucionType.NO_PLANIFICADA)
				.estadoAlmacenamiento(EstadoAlmacenamientoType.NO_ALERTADA)
				.estadoCumplidos(EstadoCumplidosType.NO_REPORTADOS)
				.cliente(getCliente())
				.clienteCodigo(getCliente().getCodigo())
				.tipoServicio(getServicio())
				.tipoServicioCodigoAlterno("")
				.requiereServicioDistribucion(false)
				.requiereConfirmacionCitaEntrega(false)
				.fechaEntregaSugeridaMinima(null)
				.fechaEntregaSugeridaMaxima(null)
				.horaEntregaSugeridaMinima(null)
				.horaEntregaSugeridaMaxima(null)
				.notasConfirmacion(dto.getNotasDocumento())
				.fechaConfirmacion(null)
				.usuarioConfirmacion("")
				
				.fechaCreacion(fechaUpd)
				.usuarioCreacion(usuarioUpd)
				.fechaActualizacion(fechaUpd)
				.usuarioActualizacion(usuarioUpd)
				
				.numeroOrdenCompra("")
				
				.destinoCiudadNombre("")
				.destinoDireccion("")
				.destinoIndicaciones("")
				
				.origenCiudadNombre("")
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
				
				.lineas(lineas)
				
				.build();
			//@formatter:on

		return orden;
	}

	private Set<LineaOrden> validarLineasOrdenDeCompra(List<LineaOrdenDeCompraDto> lineas, LocalDateTime fechaUpd,
			String usuarioUpd, MensajesDto mensajes) {
		final Set<LineaOrden> list = new HashSet<>();

		int clienteId = getCliente().getId();

		for (val e : lineas) {
			Bodega bodega = null;
			String estadoInventario = null;
			Producto producto = null;
			Optional<ProductoUnidadAssociation> huella = Optional.empty();
			Unidad unidad = null;
			Dimensiones dimensiones = null;

			if (!e.getBodegaId().isEmpty()) {
				ClienteBodegaAssociation clienteBodega;
				clienteBodega = bodegaRepository.findFirstByClienteIdAndCodigoAlterno(clienteId, e.getBodegaId());
				if (clienteBodega != null) {
					bodega = bodegaRepository.findOne(clienteBodega.getBodegaId());
					estadoInventario = clienteBodega.getEstadoInventarioId();
				}
			}

			if (!e.getItemId().isEmpty()) {
				producto = productoRepository.findByClienteIdAndCodigo(clienteId, e.getItemId());
				if (producto != null) {
					huella = producto.getProductoUnidadAssociation().stream().filter(a -> a.getNivel() == 1)
							.findFirst();
					if (huella.isPresent()) {
						unidad = huella.get().getUnidad();
						dimensiones = huella.get().getDimensiones();
					}
				}
			}

			if (bodega == null) {
				mensajes.addMensaje(SeveridadType.ERROR,
						String.format(
								"La línea con número de registro %d, tiene el código de bodega destino \"%s\", el cual no se pudo homologar a una bodega valida.",
								e.getNumeroRegistro(), e.getBodegaId()));
			}
			if (estadoInventario == null) {
				mensajes.addMensaje(SeveridadType.ERROR,
						String.format(
								"La línea con número de registro %d, tiene el código de bodega destino \"%s\", el cual no se pudo homologar a un estado de inventario valido.",
								e.getNumeroRegistro(), e.getBodegaId()));
			}
			if (producto == null) {
				mensajes.addMensaje(SeveridadType.ERROR,
						String.format(
								"La línea con número de registro %d, tiene el código de producto \"%s\", el cual no existe.",
								e.getNumeroRegistro(), e.getItemId()));
			} else {
				if (unidad == null) {
					mensajes.addMensaje(SeveridadType.ERROR,
							String.format(
									"La línea con número de registro %d, tiene el código de producto \"%s\", el cual no tiene una huella de primer nivel.",
									e.getNumeroRegistro(), e.getItemId()));
				}
				if (dimensiones == null) {
					mensajes.addMensaje(SeveridadType.ERROR,
							String.format(
									"La línea con número de registro %d, tiene el código de producto \"%s\", el cual no tiene dimensiones.",
									e.getNumeroRegistro(), e.getItemId()));
				}
			}

			if (!mensajes.getSeveridadMaxima().equals(SeveridadType.ERROR)) {
				//@formatter:off
				list.add(
					LineaOrden
					.builder()
					.numeroItem(e.getNumeroRegistro())
					.descripcion(producto.getNombreLargo())
					.cantidadSolicitada(e.getCantidad())
					.cantidadPlanificada(e.getCantidad())
					.producto(producto)
					.productoCodigo(producto.getCodigo())
					.unidad(unidad)
					.unidadCodigo(unidad.getCodigo())
					.dimensiones(dimensiones)
					
					.bodegaDestino(bodega)
					.bodegaDestinoCodigo(bodega.getCodigo())
					.bodegaDestinoCodigoAlterno(e.getBodegaId())
					.estadoInventarioDestinoId(estadoInventario)
					.numeroOrdenWmsDestino("")
					
					.lote(e.getLoteId())
					.notas(e.getNotasMovimiento())
					.fechaCreacion(fechaUpd)
					.usuarioCreacion(usuarioUpd)
					.fechaActualizacion(fechaUpd)
					.usuarioActualizacion(usuarioUpd)

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
					.build()
					);
				//@formatter:on
			} else {
				list.clear();
			}
		}

		return list;
	}

	private Set<LineaOrdenDeCompra> validarLineasOrdenDeCompraCliente(List<LineaOrdenDeCompraDto> lineas,
			MensajesDto mensajes) {
		final Set<LineaOrdenDeCompra> list = new HashSet<>();

		for (val e : lineas) {
			//@formatter:off
			list
			.add(
					LineaOrdenDeCompra
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
					.build()
				);
			//@formatter:on
		}
		return list;
	}

	private Cliente getCliente() {
		if (cliente == null) {
			cliente = clienteRepository.findByCodigoIgnoringCase(CODIGO_CLIENTE);
		}
		return cliente;
	}

	private TipoServicio getServicio() {
		if (servicio == null) {
			servicio = tipoServicioRepository.findByCodigoIgnoringCase(CODIGO_SERVICIO);
		}
		return servicio;
	}
}
