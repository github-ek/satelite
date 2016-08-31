package com.tacticlogistics.presentation.api.oms;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tacticlogistics.application.dto.common.CustomPage;
import com.tacticlogistics.application.dto.common.ItemGenerico;
import com.tacticlogistics.application.dto.common.MensajesDto;
import com.tacticlogistics.application.dto.oms.OmsOrdenDto;
import com.tacticlogistics.application.services.crm.ClientesApplicationService;
import com.tacticlogistics.application.services.crm.DestinatariosRemitentesApplicationService;
import com.tacticlogistics.application.services.crm.DestinosOrigenesApplicationService;
import com.tacticlogistics.application.services.geo.CiudadesApplicationService;
import com.tacticlogistics.application.services.oms.OmsApplicationService;
import com.tacticlogistics.application.services.seguridad.UsuarioApplicationService;
import com.tacticlogistics.application.services.wms.BodegasApplicationService;
import com.tacticlogistics.application.services.wms.ProductosApplicationService;
import com.tacticlogistics.domain.model.oms.EstadoOrdenType;
import com.tacticlogistics.domain.model.oms.OmsOrden;

@CrossOrigin
@RestController
@RequestMapping("/oms/ordenes")
public class OmsController {

	@Autowired
	private UsuarioApplicationService usuariosService;

	@Autowired
	private ClientesApplicationService clientesService;

	@Autowired
	private DestinatariosRemitentesApplicationService destinatariosRemitentesService;

	@Autowired
	private DestinosOrigenesApplicationService destinosOrigenesService;

	@Autowired
	private ProductosApplicationService productosService;

	@Autowired
	private BodegasApplicationService bodegasService;

	@Autowired
	private OmsApplicationService omsService;

	// ----------------------------------------------------------------------------------------------------------------
	// -- Consultas
	// ----------------------------------------------------------------------------------------------------------------
	@RequestMapping(path = "/{id}", method = { RequestMethod.GET })
	public OmsOrdenDto getOrdenPorId(@PathVariable Integer id) {
		OmsOrdenDto dto = null;

		try {
			dto = omsService.findOneOrdenPorId(id);
		} catch (Exception e) {
			// TODO e.printStackTrace()
			e.printStackTrace();
		}
		return dto;
	}

	// ----------------------------------------------------------------------------------------------------------------
	// -- Vistas Resumen tms-s
	// ----------------------------------------------------------------------------------------------------------------
	@RequestMapping(path = "/tms-s/despachos/resumenes/estado-ordenes-origen", method = { RequestMethod.GET })
	public List<Object> getResumenPorCediDespachosSecundaria(
			@RequestParam(value = "usuarioId", required = true) Integer usuarioId,
			@RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaDesde,
			@RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaHasta

	) {
		List<Object> list = new ArrayList<>();

		try {
			list = omsService.findResumenEstadoOrdenesDeDespachoPorCeDiOrigen(usuarioId, fechaDesde, fechaHasta);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@RequestMapping(path = "/tms-s/resumenes/cedi/recogidas", method = { RequestMethod.GET })
	public List<Object> getResumenPorCediRecogidasSecundaria(
			@RequestParam(value = "usuarioId", required = true) Integer usuarioId,
			@RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaDesde,
			@RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaHasta

	) {
		List<Object> list = new ArrayList<>();

		try {
			list = omsService.findResumenPorCediRecogidasSecundaria(usuarioId, fechaDesde, fechaHasta);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@RequestMapping(path = "/tms-s/despachos/ordenes/solicitudes-despacho", method = { RequestMethod.GET })
	public List<Object> getSolicitudesDeDespacho(
			@RequestParam(required = true) Integer usuarioId,
			@RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaDesde,
			@RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaHasta,
			@RequestParam(required = true) int clienteId, @RequestParam(required = true) String estadoOrdenId,
			@RequestParam(required = true) int bodegaOrigenId
	) {
		List<Object> list = new ArrayList<>();

		try {
			list = omsService.findSolcitudesDeDespachoSecundaria(usuarioId, fechaDesde, fechaHasta, clienteId, estadoOrdenId, bodegaOrigenId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@RequestMapping(path = "/tms-s/despachos/ordenes/lineas-orden-despacho", method = { RequestMethod.GET })
	public List<Object> getLineasOrdenDeDespacho(
			@RequestParam(required = true) int ordenId
	) {
		List<Object> list = new ArrayList<>();

		try {
			list = omsService.findLineasOrdenDeDespacho(ordenId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@RequestMapping(path = "/tms-s/despachos/ordenes/excepciones-despacho", method = { RequestMethod.GET })
	public List<Object> getExcepcionesDeDespacho(
			@RequestParam(required = true) Integer usuarioId,
			@RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaDesde,
			@RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaHasta,
			@RequestParam(required = true) int clienteId, @RequestParam(required = true) String estadoOrdenId,
			@RequestParam(required = true) int bodegaOrigenId
	) {
		List<Object> list = new ArrayList<>();

		try {
			list = omsService.findExcepcionesDeDespachoSecundaria(usuarioId, fechaDesde, fechaHasta, clienteId, estadoOrdenId, bodegaOrigenId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@RequestMapping(path = "/tms-s/despachos/ordenes/entregas-despacho", method = { RequestMethod.GET })
	public List<Object> getEntregasDeDespacho(
			@RequestParam(required = true) Integer usuarioId,
			@RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaDesde,
			@RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaHasta,
			@RequestParam(required = true) int clienteId, @RequestParam(required = true) String estadoOrdenId,
			@RequestParam(required = true) int bodegaOrigenId
	) {
		List<Object> list = new ArrayList<>();

		try {
			list = omsService.findEntregasDeDespachosSecundaria(usuarioId, fechaDesde, fechaHasta, clienteId, estadoOrdenId, bodegaOrigenId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}


	// ----------------------------------------------------------------------------------------------------------------
	// -- Vistas Resumen tms-p
	// ----------------------------------------------------------------------------------------------------------------
	@RequestMapping(path = "/tms-p/resumenes/cedi/despachos", method = { RequestMethod.GET })
	public List<Object> getResumenPorCediDespachosPrimaria(
			@RequestParam(value = "usuarioId", required = true) Integer usuarioId,
			@RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaDesde,
			@RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaHasta

	) {
		List<Object> list = new ArrayList<>();

		try {
			list = omsService.findResumenPorCediDespachosPrimaria(usuarioId, fechaDesde, fechaHasta);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@RequestMapping(path = "/tms-p/resumenes/cedi/recibos", method = { RequestMethod.GET })
	public List<Object> getResumenPorCediRecibosPrimaria(
			@RequestParam(value = "usuarioId", required = true) Integer usuarioId,
			@RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaDesde,
			@RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaHasta

	) {
		List<Object> list = new ArrayList<>();

		try {
			list = omsService.findResumenPorCediRecibosPrimaria(usuarioId, fechaDesde, fechaHasta);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@RequestMapping(path = "/tms-p/resumenes/cedi/despachos-traslados", method = { RequestMethod.GET })
	public List<Object> getResumenPorCediDespachosTraslado(
			@RequestParam(value = "usuarioId", required = true) Integer usuarioId,
			@RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaDesde,
			@RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaHasta

	) {
		List<Object> list = new ArrayList<>();

		try {
			list = omsService.findResumenPorCediDespachosTraslado(usuarioId, fechaDesde, fechaHasta);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@RequestMapping(path = "/tms-p/resumenes/cedi/recibos-traslados", method = { RequestMethod.GET })
	public List<Object> getResumenPorCediRecibosTraslado(
			@RequestParam(value = "usuarioId", required = true) Integer usuarioId,
			@RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaDesde,
			@RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaHasta

	) {
		List<Object> list = new ArrayList<>();

		try {
			list = omsService.findResumenPorCediRecibosTraslado(usuarioId, fechaDesde, fechaHasta);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// ----------------------------------------------------------------------------------------------------------------
	// -- Vistas Resumen
	// ----------------------------------------------------------------------------------------------------------------

	@RequestMapping(path = "/ordenes_por_criterios", method = { RequestMethod.GET })
	public List<Object> getOrdenesPorCriteriosVarios(@RequestParam(required = true) Integer usuarioId,
			@RequestParam(required = true) Integer clienteId, @RequestParam(required = true) Integer tipoServicioId,
			@RequestParam() Integer bodegaOrigenId, @RequestParam() Integer bodegaDestinoId,
			@RequestParam() EstadoOrdenType estadoOrdenId,
			@RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaConfirmacion) {
		List<Object> list = new ArrayList<>();

		try {
			list = omsService.findOrdenesPorCriterio(usuarioId, clienteId, tipoServicioId, bodegaOrigenId,
					bodegaDestinoId, estadoOrdenId, fechaConfirmacion);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@RequestMapping(path = "/por_fecha_corte", method = { RequestMethod.GET })
	public List<Object> getOrdenesFechaCorte(@RequestParam(value = "usuarioId", required = true) Integer usuarioId,
			@RequestParam(value = "fechaDesde", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaDesde) {
		List<Object> list = new ArrayList<>();

		try {
			list = omsService.findOrdenesPorFechaConfirmacion(usuarioId, fechaDesde);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@RequestMapping(value = "/por_ids", method = RequestMethod.POST)
	public List<Object> getOrdenesPorId(@RequestBody Map<String, Object> model) {
		List<Object> list = new ArrayList<>();
		Integer usuarioId = (Integer) model.get("usuarioId");
		List<Integer> ids = ((List<Integer>) model.get("ids"));
		try {
			list = omsService.findOrdenesPorId(usuarioId, ids);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@RequestMapping(value = "/estados/orden/uso_externo", method = RequestMethod.GET)
	public Map<EstadoOrdenType, String> getEstadosUsoExterno() {
		Map<EstadoOrdenType, String> map = new LinkedHashMap<>();

		for (EstadoOrdenType e : EstadoOrdenType.values()) {
			if (e.isUsoExterno()) {
				map.put(e, e.getNombre());
			}
		}
		return map;
	}

	@RequestMapping(value = "/estados/orden/uso_interno", method = RequestMethod.GET)
	public Map<EstadoOrdenType, String> getEstadosUsoInterno() {
		Map<EstadoOrdenType, String> map = new LinkedHashMap<>();

		for (EstadoOrdenType e : EstadoOrdenType.values()) {
			if (e.isUsoInterno()) {
				map.put(e, e.getNombre());
			}
		}
		return map;
	}

	@RequestMapping(value = "/transiciones/orden", method = RequestMethod.GET)
	public Map<EstadoOrdenType, Map<EstadoOrdenType, Boolean>> getTransicionesOrdenes() {
		Map<EstadoOrdenType, Map<EstadoOrdenType, Boolean>> map = new LinkedHashMap<>();

		for (EstadoOrdenType a : EstadoOrdenType.values()) {
			Map<EstadoOrdenType, Boolean> m = new LinkedHashMap<>();
			map.put(a, m);
			for (EstadoOrdenType b : EstadoOrdenType.values()) {
				m.put(b, OmsOrden.transicionPermitida(a, b));
			}
		}
		return map;
	}

	@RequestMapping("/causales/anulacion/orden")
	public List<ItemGenerico<Integer>> getCausalesAnulacionOrden() {
		List<ItemGenerico<Integer>> list = new ArrayList<>();

		try {
			list = omsService.findCausalesAnulacion();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// ----------------------------------------------------------------------------------------------------------------
	// -- Modificaciones
	// ----------------------------------------------------------------------------------------------------------------
	@RequestMapping(value = "/cambiar_estado", method = RequestMethod.POST)
	public MensajesDto cambiarEstadoOrdenes(@RequestBody CambiarEstadoOrdenDto dto) {
		try {
			return omsService.cambiarEstadoOrdenes(dto.getUsuarioId(), dto.getIds(), dto.getNuevoEstadoId(),
					dto.getNotas());
		} catch (Exception e) {
			MensajesDto mensajes = new MensajesDto();
			mensajes.addMensaje(e, dto);
			return mensajes;
		}
	}

	@RequestMapping(value = "/anular", method = RequestMethod.POST)
	public MensajesDto anaularOrdenes(@RequestBody AnularOrdenDto dto) {
		try {
			return omsService.anularOrdenes(dto.getUsuarioId(), dto.getIds(), dto.getCausalId(), dto.getNotas());
		} catch (Exception e) {
			MensajesDto mensajes = new MensajesDto();
			mensajes.addMensaje(e, dto);
			return mensajes;
		}
	}

	// ----------------------------------------------------------------------------------------------------------------
	// Listar Ordenes
	@RequestMapping(path = "/ordenes-x-usuario-x-tipo_servicio-x-cliente-x-estado_orden", method = { RequestMethod.GET,
			RequestMethod.POST })
	public CustomPage<String[]> getOrdenesPorUsuarioIdAndTipoServicioIdAndEstadoOrden(
			@RequestParam(required = true) Integer usuarioId, @RequestParam(required = true) Integer tipoServicioId,
			@RequestParam(required = false) Integer clienteId,
			@RequestParam(required = false) EstadoOrdenType estadoOrdenId, @RequestParam(required = true) int start,
			@RequestParam(required = true) int length) {

		CustomPage<String[]> page = new CustomPage<>(0L, null);
		try {
			// page =
			// omsService.findOrdenesByUsuarioIdAndTipoServicioIdAndEstadoOrden(usuarioId,
			// tipoServicioId,
			// clienteId, estadoOrdenId, start, length);
		} catch (Exception e) {
			// TODO e.printStackTrace()
			e.printStackTrace();
		}
		return page;
	}

	// ----------------------------------------------------------------------------------------------------------------
	// -- Bill To
	// ----------------------------------------------------------------------------------------------------------------
	// Clientes habilitados para el usuario actual y un tipo de servicio
	@RequestMapping(value = "/clientes-x-usuario-x-tipo_servicio", method = RequestMethod.GET)
	public List<Object> getClientesPorUsuarioPorTipoServicio(@RequestParam(required = true) Integer usuarioId,
			@RequestParam(required = true) Integer tipoServicioId) {
		List<Object> list = new ArrayList<>();

		try {
			list = usuariosService.findClientesPorUsuarioIdPorTipoServicioId(usuarioId, tipoServicioId);
		} catch (Exception e) {
			// TODO e.printStackTrace()
			e.printStackTrace();
		}
		return list;
	}

	// Canales habilitados para un cliente y tipo de servicio
	@RequestMapping(value = "/canales-x-cliente-x-tipo_servicio", method = RequestMethod.GET)
	public List<Map<String, Object>> getCanalesPorClientePorTipoServicio(
			@RequestParam(required = true) Integer clienteId, @RequestParam(required = true) Integer tipoServicioId) {
		List<Map<String, Object>> list = new ArrayList<>();

		try {
			list = clientesService.findCanalesPorCliente(clienteId);
		} catch (Exception e) {
			// TODO e.printStackTrace()
			e.printStackTrace();
		}
		return list;
	}

	// Destinatarios/Remitentes para un cliente, tipo de servicio y filtro
	@RequestMapping(value = "/destinatarios_remitentes-x-cliente-x-tipo_servicio-x-canal-x-filtro", method = RequestMethod.GET)
	public List<Map<String, Object>> getDestinatariosRemitentesPorClientePorTipoServicioPorCanalPorFiltro(
			@RequestParam(required = true) Integer clienteId, @RequestParam(required = true) Integer tipoServicioId,
			@RequestParam(required = false) Integer canalId,
			@RequestParam(required = false, defaultValue = "") String filtro) {

		List<Map<String, Object>> list = new ArrayList<>();
		try {
			list = destinatariosRemitentesService.findDestinatariosRemitentesPorClientePorTipoServicioPorCanalPorFiltro(
					clienteId, tipoServicioId, canalId, filtro);
		} catch (Exception e) {
			// TODO e.printStackTrace()
			e.printStackTrace();
		}
		return list;
	}

	// ----------------------------------------------------------------------------------------------------------------
	// -- Ship To (Destino)
	// ----------------------------------------------------------------------------------------------------------------
	// Ciudades habilitadas para un destinatario y tipo de servicio
//	@RequestMapping(value = "/ciudades-x-destinatario_remitente-x-tipo_servicio", method = RequestMethod.GET)
//	public List<Map<String, Object>> getCiudadesPorDestinatarioRemitentePorTipoServicio(
//			@RequestParam(required = true) Integer destinatarioRemitenteId,
//			@RequestParam(required = true) Integer tipoServicioId) {
//
//		List<Map<String, Object>> list = new ArrayList<>();
//		try {
//			list = ciudadesService.findCiudadesPorDestinatarioRemitentePorTipoServicio(destinatarioRemitenteId,
//					tipoServicioId);
//		} catch (Exception e) {
//			// TODO e.printStackTrace()
//			e.printStackTrace();
//		}
//		return list;
//	}

	// Destinos habilitados para un destinatario, ciudad y tipo de servicio
	@RequestMapping("/destinos_origenes-x-destinatario_remitente-x-tipo_servicio-x-ciudad")
	public List<Map<String, Object>> getDestinosOrigenesPorDestinatarioRemitentePorTipoServicioPorCiudad(
			@RequestParam(required = true) Integer destinatarioRemitenteId,
			@RequestParam(required = true) Integer tipoServicioId, @RequestParam(required = true) Integer ciudadId) {

		List<Map<String, Object>> list = new ArrayList<>();
		try {
			list = destinosOrigenesService.findDestinosOrigenesPorDestinatarioRemitentePorTipoServicioPorCiudad(
					destinatarioRemitenteId, ciudadId);
		} catch (Exception e) {
			// TODO e.printStackTrace()
			e.printStackTrace();
		}
		return list;
	}

	// TODO Malla horaria x cliente, canal, destinatario, ciudad, destino

	// ----------------------------------------------------------------------------------------------------------------
	// -- Ship To (Bodega Destino)
	// ----------------------------------------------------------------------------------------------------------------
	// TODO Ciudades con bodegas habilitadas para un cliente y tipo de servicio
	// @RequestMapping("/ciudades-con-bodega-x-cliente")
	// public List<Object> getAllCiudadPorClientePorTipoServicio(
	// @RequestParam(value = "id_cliente", required = true) Integer clienteId,
	// @RequestParam(value = "id_tipo_servicio", required = true) Integer
	// tipoServicioId) {
	// List<Object> list = new ArrayList<>();
	//
	// try {
	// list = ciudadesService.findAllCiudadPorClientePorTipoServicio(clienteId,
	// tipoServicioId);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return list;
	// }

	// TODO Bodegas habilitados para un cliente, ciudad y tipo de servicio
	// @RequestMapping("/bodegas-x-ciudad-x-cliente")
	// public List<Object> getAllBodegaPorCliente(@RequestParam(value =
	// "id_cliente", required = true) Integer clienteId,
	// @RequestParam(value = "id_ciudad", required = true) Integer ciudadId,
	// @RequestParam(value = "id_tipo_servicio", required = true) Integer
	// tipoServicioId) {
	// List<Object> list = new ArrayList<>();
	//
	// try {
	// list = ordenesService.findAllBodegaPorCliente(clienteId, ciudadId,
	// tipoServicioId);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return list;
	// }

	// ----------------------------------------------------------------------------------------------------------------
	// -- Requerimientos
	// ----------------------------------------------------------------------------------------------------------------
	// Requerimientos de Distribución x cliente x tipo de servicio x
	// destinatario remitente
	@RequestMapping("/requerimientos_distribucion-x-cliente-x-tipo_servicio-x-destinatario_remitente")
	public List<Map<String, Object>> getRequerimientosDistribucionPorClientePorTipoServicioDestinatarioRemitente(
			@RequestParam(required = true) Integer clienteId, @RequestParam(required = true) Integer tipoServicioId,
			@RequestParam(required = true) Integer destinatarioRemitenteId) {

		List<Map<String, Object>> list = new ArrayList<>();
		try {
			list = omsService.findRequerimientosDistribucionPorClientePorTipoServicioDestinatarioRemitente(clienteId,
					tipoServicioId, destinatarioRemitenteId);
		} catch (Exception e) {
			// TODO e.printStackTrace()
			e.printStackTrace();
		}
		return list;
	}

	// Requerimientos de Alistamiento x cliente x tipo de servicio x
	// destinatario
	// remitente
	@RequestMapping("/requerimientos_alistamiento-x-cliente-x-tipo_servicio-x-destinatario_remitente")
	public List<Map<String, Object>> getRequerimientosAlistamientoPorClientePorTipoServicioDestinatarioRemitente(
			@RequestParam(required = true) Integer clienteId, @RequestParam(required = true) Integer tipoServicioId,
			@RequestParam(required = true) Integer destinatarioRemitenteId) {

		List<Map<String, Object>> list = new ArrayList<>();
		try {
			list = omsService.findRequerimientosAlistamientoPorClientePorTipoServicioDestinatarioRemitente(clienteId,
					tipoServicioId, destinatarioRemitenteId);
		} catch (Exception e) {
			// TODO e.printStackTrace()
			e.printStackTrace();
		}
		return list;
	}

	// ----------------------------------------------------------------------------------------------------------------
	// -- Lineas de Productos
	// ----------------------------------------------------------------------------------------------------------------
	// Productos x cliente y tipo de servicio
	@RequestMapping("/productos-x-cliente-x-tipo_servicio")
	public List<Object> getProductosPorClientePorTipoServicio(@RequestParam(required = true) Integer clienteId,
			@RequestParam(required = true) Integer tipoServicioId) {
		List<Object> list = new ArrayList<>();

		try {
			list = productosService.findAllProductoPorCliente(clienteId);
		} catch (Exception e) {
			// TODO e.printStackTrace()
			e.printStackTrace();
		}
		return list;
	}

	// Unidades x producto
	@RequestMapping("/unidades-x-producto")
	public List<Object> getUnidadesPorProducto(@RequestParam(required = true) Integer productoId) {
		List<Object> list = new ArrayList<>();

		try {
			list = productosService.findAllUnidadPorProducto(productoId);
		} catch (Exception e) {
			// TODO e.printStackTrace()
			e.printStackTrace();
		}
		return list;
	}

	// Ciudades donde existen bodegas habilitadas para un producto
//	@RequestMapping("/ciudades-x-producto")
//	public List<Object> getCiudadesPorProductoPorCiudad(@RequestParam(required = true) Integer productoId) {
//		List<Object> list = new ArrayList<>();
//
//		try {
//			list = ciudadesService.findCiudadesPorProducto(productoId);
//		} catch (Exception e) {
//			// TODO e.printStackTrace()
//			e.printStackTrace();
//		}
//		return list;
//	}

	// Bodegas habilitadas para un producto y ciudad
	@RequestMapping("/bodegas-x-producto-x-ciudad")
	public List<Object> getBodegasPorProductoPorCiudad(@RequestParam(required = true) Integer productoId,
			@RequestParam(required = true) Integer ciudadId) {
		List<Object> list = new ArrayList<>();

		try {
			list = bodegasService.findBodegasPorProductoPorCiudad(productoId, ciudadId);
		} catch (Exception e) {
			// TODO e.printStackTrace()
			e.printStackTrace();
		}
		return list;
	}

	// ----------------------------------------------------------------------------------------------------------------
	// -- Causales
	// ----------------------------------------------------------------------------------------------------------------
	// Causales de Solicitud de Revision cuando lo solicte el cliente
	@RequestMapping("/causales_solicitud_revision_cliente")
	public List<ItemGenerico<Integer>> getCausalesSolicitudRevisionCliente() {
		List<ItemGenerico<Integer>> list = new ArrayList<>();

		try {
			list = omsService.findCausalesSolicitudRevisionCliente();
		} catch (Exception e) {
			// TODO e.printStackTrace()
			e.printStackTrace();
		}
		return list;
	}

	// Causales de Solicitud de Revision cuando lo solicite planeación
	@RequestMapping("/causales_solicitud_revision_planeacion")
	public List<ItemGenerico<Integer>> getCausalesSolicitudRevisionPlaneacion() {
		List<ItemGenerico<Integer>> list = new ArrayList<>();

		try {
			list = omsService.findCausalesSolicitudRevisionPlaneacionLogistica();
		} catch (Exception e) {
			// TODO e.printStackTrace()
			e.printStackTrace();
		}
		return list;
	}
}
