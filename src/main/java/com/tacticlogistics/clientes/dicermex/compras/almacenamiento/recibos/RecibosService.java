package com.tacticlogistics.clientes.dicermex.compras.almacenamiento;

import static org.springframework.data.domain.ExampleMatcher.matching;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tacticlogistics.application.dto.common.MensajesDto;
import com.tacticlogistics.clientes.dicermex.compras.almacenamiento.LineaResultadoAlmacenamientoDto;
import com.tacticlogistics.clientes.dicermex.compras.almacenamiento.ResultadoAlmacenamientoDto;
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

	@Transactional(readOnly = false)
	public void confirmarReciboDeOrdenesDeCompra(List<ResultadoAlmacenamientoDto> resultados) {
		for (ResultadoAlmacenamientoDto e : resultados) {
			String partes[] = e.getNumeroOrdenWms().split("-");
			int id = Integer.parseInt(partes[1]);
			
			Orden compra = ordenRepository.findOne(id);
			if (compra != null) {
				String clienteCodigoWms = compra.getCliente().getCodigoAlternoWms();
				LineaOrden lo = compra.getLineas().stream().findFirst().get();
				Bodega bodega = lo.getBodegaOrigen();
				String bodegaCodigo = lo.getBodegaDestinoCodigo();
				String bodegaCodigoAlterno = lo.getBodegaDestinoCodigoAlterno();
				List<LineaOrden> nuevasLineas = new ArrayList<>();
				
				// val max = compra.getLineas().stream().mapToInt((x) ->
				// a.getNumeroItem()).summaryStatistics().getMax();
				int secuenciaNumeroItem = 9000;

				if (!clienteCodigoWms.equals(e.getClienteCodigoWms())) {
					throw new RuntimeException(
							"Orden " + e.getNumeroOrdenWms() + ":" + "Cliente diferente al original");
				}

				if (!bodegaCodigo.equals(e.getBodegaCodigo())) {
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
//						LineaOrden linea = compra.getLineas().stream().filter(a -> a.getNumeroItem() == l.getNumeroItem())
//								.findFirst();
//						if (!linea.isPresent()) {
//							throw new RuntimeException("Orden " + e.getNumeroOrdenWms() + "Linea " + l.getNumeroItem()
//									+ ":" + "No existe el numero de linea");
//						}
//
//						if (linea.get().getCantidadPlanificada() != l.getCantidadPlanificada()) {
//							throw new RuntimeException("Orden " + e.getNumeroOrdenWms() + "Linea " + l.getNumeroItem()
//									+ ":" + "La cantidad planificada no es igual");
//						}
//
//						update(compra.getCliente().getId(),linea.get(), l.getProductoCodigo(),l.getEstadoInventarioId(), l.getCantidadReal());
					} else {
						secuenciaNumeroItem++;
						//nuevasLineas.add(insert(compra.getCliente().getId(),secuenciaNumeroItem,bodega,bodegaCodigo,bodegaCodigoAlterno,e,l);
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

	private LineaOrden insert(
			Integer clienteId,
			String numeroOrdenWms,
			int secuenciaNumeroItem, 
			Bodega bodega,
			String bodegaCodigoAlterno,
			LineaResultadoAlmacenamientoDto l,
			LocalDateTime fechaUpd,
			String usuarioUpd) {
		Producto producto = null;
		Optional<ProductoUnidadAssociation> huella = Optional.empty();
		Unidad unidad = null;
		Dimensiones dimensiones = null;

		
		//@formatter:off
		return LineaOrden
			.builder()
			.numeroItem(secuenciaNumeroItem)
			.descripcion(producto.getNombreLargo())
			.cantidadSolicitada(l.getCantidadReal())
			.cantidadPlanificada(l.getCantidadReal())
			.cantidadEntregada(l.getCantidadReal())
			.producto(producto)
			.productoCodigo(producto.getCodigo())
			.unidad(unidad)
			.unidadCodigo(unidad.getCodigo())
			.dimensiones(dimensiones)
			
			.bodegaDestino(bodega)
			.bodegaDestinoCodigo(bodega.getCodigo())
			.bodegaDestinoCodigoAlterno(bodegaCodigoAlterno)
			.estadoInventarioDestinoId(l.getEstadoInventarioId())
			.numeroOrdenWmsDestino(numeroOrdenWms)
			
			.lote("")
			.notas("")
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
			.build();
		//@formatter:on
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
