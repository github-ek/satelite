package com.tacticlogistics.clientes.dicermex.compras.wms.recibos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tacticlogistics.domain.model.clientes.dicermex.LineaOrdenDeCompra;
import com.tacticlogistics.domain.model.clientes.dicermex.OrdenDeCompra;
import com.tacticlogistics.domain.model.common.valueobjects.Dimensiones;
import com.tacticlogistics.domain.model.crm.Cliente;
import com.tacticlogistics.domain.model.crm.ClienteBodegaAssociation;
import com.tacticlogistics.domain.model.crm.TipoServicio;
import com.tacticlogistics.domain.model.oms.EstadoAlmacenamientoType;
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

import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@Slf4j
public class RecibosService {

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

	// TODO Rescribir
	@Transactional(readOnly = false)
	public List<AcuseReciboDto> confirmarReciboDeOrdenesDeCompra(List<ResultadoReciboDto> recibos) {
		Map<AcuseReciboDto, OrdenDeCompra> map = new HashMap<>();
		validarConfirmacionesDeRecibo(recibos, map);
		confirmarRecibos(recibos, map);

		return map.keySet().stream().collect(Collectors.toList());
	}

	private void validarConfirmacionesDeRecibo(List<ResultadoReciboDto> resultados,
			Map<AcuseReciboDto, OrdenDeCompra> map) {
		for (ResultadoReciboDto e : resultados) {
			validarConfirmacionDeRecibo(e, map);
		}
	}

	private void validarConfirmacionDeRecibo(ResultadoReciboDto recibo, Map<AcuseReciboDto, OrdenDeCompra> map) {
		OrdenDeCompra compra = null;
		boolean error = false;
		String mensaje = "";

		if (!recibo.getNumeroOrdenWms().matches("TC-\\d+-(.)+")) {
			// WMS Solo va a notificar resultado de ordenes cuyo numero de orden
			// en WMS tenga la forma TC-{id orden tc}-{numero orden TC}
			error = true;
		} else {
			String partes[] = recibo.getNumeroOrdenWms().split("-");
			int id = Integer.parseInt(partes[1]);

			compra = ordenDeCompraRepository.findOne(id);
			if (compra == null) {
				// La orden debe existir
				error = true;
			}
		}

		if (!error) {
			Orden orden = compra.getOrden();
			String clienteCodigoWms = orden.getCliente().getCodigoAlternoWms();
			LineaOrden lo = orden.getLineas().stream().findFirst().get();
			Bodega bodega = lo.getBodegaDestino();
			String bodegaCodigo = lo.getBodegaDestinoCodigo();

			if (!clienteCodigoWms.equals(recibo.getClienteCodigoWms())) {
				error = true;
				// El cliente y bodega deben ser los mismos que se enviaron
				// originalmente
			}

			if (!bodegaCodigo.equals(recibo.getBodegaCodigo())) {
				// El cliente y bodega deben ser los mismos que se enviaron
				// originalmente
				// throw new RuntimeException("Orden " + e.getNumeroOrdenWms() +
				// ":"
				// + "Bodega diferente al original");
				error = true;
			}

			if (!orden.getEstadoAlmacenamiento().equals(EstadoAlmacenamientoType.ALERTADA)) {
				// La orden en TC debe tener un estado de almacenamiento igual a
				// ALERTADA
				// throw new RuntimeException("Orden " + e.getNumeroOrdenWms() +
				// ":"
				// + "Se encuentra en el estado "
				// + compra.getEstadoAlmacenamiento().toString() + ". La orden
				// debe
				// estar en estado "
				// + EstadoAlmacenamientoType.ALERTADA.toString());
				error = true;
			}

			List<LineaResultadoReciboDto> nuevas = recibo.getLineas();
			List<LineaOrden> originales = orden.getLineas().stream().collect(Collectors.toList());

			for (val nueva : nuevas) {

				// @formatter:off
				long repeticiones = nuevas
						.stream()
						.filter(a -> 
						a.getNumeroItem() == nueva.getNumeroItem() && 
						a.getEstadoInventarioId().equals(nueva.getEstadoInventarioId())
						)
						.count();
 
				// @formatter:on

				if (repeticiones > 1) {
					// Dentro de una orden los valores numero de linea (invlin)
					// y
					// estado (rcvsts), deben ser valores unicos
					error = true;
				}

				// @formatter:off
				Optional<LineaOrden> original = originales
						.stream()
						.filter(a -> 
						a.getNumeroItem() == nueva.getNumeroItem()
						).findFirst();
 				// @formatter:on

				boolean existente = false;
				if (original.isPresent()) {
					if (original.get().getEstadoInventarioDestinoId().equals(nueva.getEstadoInventarioId())) {
						existente = true;
					}
					if (!original.get().getProductoCodigo().equals(nueva.getProductoCodigo())) {
						// El producto de una linea original no puede cambiar en
						// la
						// respuesta
						error = true;
					}
				}

				if (existente) {
					if (nueva.getCantidadReal() < 0) {
						// Lo mineino es cero si existe
						error = true;
					}

				} else {
					if (nueva.getCantidadReal() <= 0) {
						// Si se esta enviando algo nuevo debe tener una catidad
						// mayor que cero
						error = true;
					}

					if (!original.isPresent()) {
						if (nueva.getNumeroItem() < 9001) {
							// Si se recibieron referencias que no estaban
							// incluidas
							// en
							// la orden original,
							// se deben enviar lineas adicionales con el
							// atributo
							// invlin
							// con un valor >=9000
							error = true;
						} else {
							Producto producto = null;
							Optional<ProductoUnidadAssociation> huella = Optional.empty();
							Unidad unidad = null;
							Dimensiones dimensiones = null;

							ClienteBodegaAssociation clienteBodega = null;
							val list = bodegaRepository.findByClienteIdAndBodegaIdAndEstadoInventarioId(
									getCliente().getId(), bodega.getId(), nueva.getEstadoInventarioId());

							if(!list.isEmpty()){
								clienteBodega = list.get(0);
							}
							
							producto = productoRepository.findByClienteIdAndCodigo(getCliente().getId(),
									nueva.getProductoCodigo());
							if (producto != null) {
								huella = producto.getProductoUnidadAssociation().stream().filter(a -> a.getNivel() == 1)
										.findFirst();
								if (huella.isPresent()) {
									unidad = huella.get().getUnidad();
									dimensiones = huella.get().getDimensiones();
								}
							}

							if (clienteBodega == null) {
								// no se pudo omologar el estado de invetario y
								// la
								// bodega a la bodega de wms
								error = true;
							}

							if (producto == null) {
								// el producto debe existir
								error = true;
							} else {
								if (unidad == null) {
									// No se encontro la huella
									error = true;
								}
								if (dimensiones == null) {
									// No se encontro la dimension
									error = true;
								}
							}

							// @formatter:off
							long otros = nuevas
									.stream()
									.filter(a -> 
									a.getNumeroItem() < 9000 && 
									a.getProductoCodigo().equals(nueva.getProductoCodigo()))
									.count();
 							// @formatter:on

							if (otros > 0) {
								// Las lineas nuevas no pueden contener
								// referencias
								// de
								// productos existentes en algunas de las lineas
								// de
								// la
								// orden original.
								error = true;
							}
						}
					}
				}
			}

			for (LineaOrden original : originales) {
				long repeticiones = nuevas.stream().filter(a -> a.getNumeroItem() == original.getNumeroItem()
						&& a.getEstadoInventarioId().equals(original.getEstadoInventarioDestinoId())).count();
				if (repeticiones == 0) {
					// Cada linea originalmente enviada debe tener una
					// respuesta.
					error = true;
				}
			}
		}
		if (error) {
			mensaje = "Se encontraron errores";
			compra = null;
		}

		AcuseReciboDto acuse = AcuseReciboDto.builder().id(recibo.getId()).error(error).mensaje(mensaje).build();
		map.put(acuse, compra);
	}

	private void confirmarRecibos(List<ResultadoReciboDto> recibos, Map<AcuseReciboDto, OrdenDeCompra> map) {
		val fechaUpd = LocalDateTime.now();
		val usuarioUpd = "INTEGRACION WMS-TC";

		val acuses = map.keySet();

		for (AcuseReciboDto acuse : acuses) {
			if (!acuse.isError()) {
				// @formatter:off
				ResultadoReciboDto recibo = recibos
						.stream()
						.filter(a -> a.getId().equals(acuse.getId()))
						.findFirst()
						.get();
				// @formatter:on
				OrdenDeCompra compra = map.get(acuse);
				confirmarRecibo(recibo, compra, acuse, fechaUpd, usuarioUpd);
			}
		}
	}

	private void confirmarRecibo(ResultadoReciboDto recibo, OrdenDeCompra compra, AcuseReciboDto acuse,
			LocalDateTime fechaUpd, String usuarioUpd) {

		LineaOrden lo = compra.getOrden().getLineas().stream().findFirst().get();
		Bodega bodega = lo.getBodegaOrigen();

		List<LineaOrdenDeCompra> lineas = compra.getLineas().stream().collect(Collectors.toList());
		List<LineaOrden> originales = compra.getOrden().getLineas().stream().collect(Collectors.toList());
		List<LineaResultadoReciboDto> nuevas = recibo.getLineas();

		Set<LineaOrdenDeCompra> l = new HashSet<>();
		Set<LineaOrden> o = new HashSet<>();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		String fechaEntrega = fechaUpd.format(formatter);
		boolean novedades = false;
		int secuenciaNumeroItem = 8000;

		for (LineaResultadoReciboDto nueva : nuevas) {
			val original = originales.stream().filter(a -> a.getNumeroItem() == nueva.getNumeroItem()).findFirst();

			if (original.isPresent()) {
				if (original.get().getEstadoInventarioDestinoId().equals(nueva.getEstadoInventarioId())) {
					if (original.get().getCantidadPlanificada() != nueva.getCantidadReal()) {
						novedades = true;
					}
					val linea = lineas.stream().filter(a -> a.getNumeroRegistro() == nueva.getNumeroItem()).findFirst()
							.get();
					actualizarLinea(linea, original.get(), nueva, fechaEntrega, fechaUpd, usuarioUpd);
				} else {
					novedades = true;
					secuenciaNumeroItem++;
					crearNuevaLinea(recibo, compra, l, o, nueva, secuenciaNumeroItem, fechaEntrega, bodega, fechaUpd,
							usuarioUpd);
				}
			} else {
				novedades = true;
				crearNuevaLinea(recibo, compra, l, o, nueva, nueva.getNumeroItem(), fechaEntrega, bodega, fechaUpd,
						usuarioUpd);
			}
		}

		if (!novedades) {
			compra.getOrden().setEstadoOrden(EstadoOrdenType.ENTREGADA);
			compra.getOrden().setEstadoAlmacenamiento(EstadoAlmacenamientoType.PROCESADA);
		} else {
			compra.getOrden().setEstadoOrden(EstadoOrdenType.NOVEDADES);
			compra.getOrden().setEstadoAlmacenamiento(EstadoAlmacenamientoType.NOVEDADES);
		}
		compra.getOrden().setDatosActualizacion(fechaUpd, usuarioUpd);

		try {
			ordenDeCompraRepository.saveAndFlush(compra);
			compra.getLineas().addAll(l);
			compra.getOrden().getLineas().addAll(o);
			ordenDeCompraRepository.saveAndFlush(compra);
		} catch (Exception e) {
			acuse.setError(true);
			acuse.setMensaje(e.getMessage());
		}
	}

	private void actualizarLinea(LineaOrdenDeCompra linea, final LineaOrden lineaOrden, LineaResultadoReciboDto nueva,
			String fechaEntrega, LocalDateTime fechaUpd, String usuarioUpd) {

		linea.setCantidad(nueva.getCantidadReal());
		linea.setFechaEntrega(fechaEntrega);

		lineaOrden.setCantidadEntregada(nueva.getCantidadReal());
		lineaOrden.setFechaActualizacion(fechaUpd);
		lineaOrden.setUsuarioActualizacion(usuarioUpd);
	}

	private void crearNuevaLinea(ResultadoReciboDto recibo, OrdenDeCompra compra, Set<LineaOrdenDeCompra> l,
			Set<LineaOrden> o, LineaResultadoReciboDto nueva, int secuenciaNumeroItem, String fechaEntrega,
			Bodega bodega, LocalDateTime fechaUpd, String usuarioUpd) {

		Producto producto = null;
		Optional<ProductoUnidadAssociation> huella = Optional.empty();
		Unidad unidad = null;
		Dimensiones dimensiones = null;

		ClienteBodegaAssociation clienteBodega = null;
		val list = bodegaRepository.findByClienteIdAndBodegaIdAndEstadoInventarioId(
				getCliente().getId(), bodega.getId(), nueva.getEstadoInventarioId());

		if(!list.isEmpty()){
			clienteBodega = list.get(0);
		}
		
		producto = productoRepository.findByClienteIdAndCodigo(getCliente().getId(), nueva.getProductoCodigo());
		if (producto != null) {
			huella = producto.getProductoUnidadAssociation().stream().filter(a -> a.getNivel() == 1).findFirst();
			if (huella.isPresent()) {
				unidad = huella.get().getUnidad();
				dimensiones = huella.get().getDimensiones();
			}
		}
		// @formatter:off
		val nuevaLineaOrdenDeCompra = LineaOrdenDeCompra
				.builder()
				.centroOperacion(compra.getCentroOperacion())
				.consecutivoDocumento(compra.getConsecutivoDocumento())
				.bodegaId(recibo.getBodegaCodigo())
				
				.numeroRegistro(secuenciaNumeroItem)
				.itemId(nueva.getProductoCodigo())
				.cantidad(nueva.getCantidadReal())
				.fechaEntrega(fechaEntrega)
				
				.ubicacionId("")
				.loteId("")
				.unidadMedida(unidad.getCodigo())
				.notasMovimiento("")
				.talla("")
				.color("")
				.centroCosto("")
				.proyectoId("")
				.build();
		// @formatter:on

		// @formatter:off
		val nuevaLineaOrden = LineaOrden
				.builder()
				.numeroItem(secuenciaNumeroItem)
				.descripcion(producto.getNombreLargo())
				.cantidadSolicitada(nueva.getCantidadReal())
				.cantidadPlanificada(nueva.getCantidadReal())
				.cantidadEntregada(nueva.getCantidadReal())
				.producto(producto)
				.productoCodigo(producto.getCodigo())
				.unidad(unidad)
				.unidadCodigo(unidad.getCodigo())
				.dimensiones(dimensiones)
				
				.tipoContenido(null)
				.tipoContenidoCodigo("")

				.bodegaOrigen(null)
				.bodegaOrigenCodigo("")
				.bodegaOrigenCodigoAlterno("")
				.estadoInventarioOrigenId("")
				.numeroOrdenWmsOrigen("")

				.bodegaDestino(bodega)
				.bodegaDestinoCodigo(bodega.getCodigo())
				.bodegaDestinoCodigoAlterno(clienteBodega.getCodigoAlterno())
				.estadoInventarioDestinoId(nueva.getEstadoInventarioId())
				.numeroOrdenWmsDestino(recibo.getNumeroOrdenWms())

				.lote("")
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
				.valorDeclaradoPorUnidad(null)
				.notas("")

				.fechaCreacion(fechaUpd)
				.usuarioCreacion(usuarioUpd)
				.fechaActualizacion(fechaUpd)
				.usuarioActualizacion(usuarioUpd)

				.build();
		// @formatter:on		

		l.add(nuevaLineaOrdenDeCompra);
		o.add(nuevaLineaOrden);
	}

	private Cliente getCliente() {
		if (cliente == null) {
			cliente = clienteRepository.findByCodigoIgnoringCase(CODIGO_CLIENTE);
		}
		return cliente;
	}

	@SuppressWarnings("unused")
	private TipoServicio getServicio() {
		if (servicio == null) {
			servicio = tipoServicioRepository.findByCodigoIgnoringCase(CODIGO_SERVICIO);
		}
		return servicio;
	}
}
