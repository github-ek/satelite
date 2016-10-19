package com.tacticlogistics.presentation.api.oms;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tacticlogistics.application.dto.common.MensajesDto;
import com.tacticlogistics.application.services.crm.ClientesApplicationService;
import com.tacticlogistics.application.services.crm.DestinatariosApplicationService;
import com.tacticlogistics.application.services.crm.DestinosApplicationService;
import com.tacticlogistics.application.services.oms.OmsApplicationService;
import com.tacticlogistics.application.services.ordenes.OrdenesApplicationService;
import com.tacticlogistics.application.services.seguridad.UsuarioApplicationService;
import com.tacticlogistics.application.services.wms.ProductosApplicationService;
import com.tacticlogistics.domain.model.oms.EstadoOrdenType;
import com.tacticlogistics.domain.model.ordenes.Orden;
import com.tacticlogistics.presentation.util.BadRequestException;

@CrossOrigin
@RestController
@RequestMapping("/oms/ordenes")
public class OmsController {

	@Autowired
	private UsuarioApplicationService usuariosService;

	@Autowired
	private ClientesApplicationService clientesService;

	@Autowired
	private DestinatariosApplicationService destinatariosRemitentesService;

	@Autowired
	private DestinosApplicationService destinosService;

	@Autowired
	private ProductosApplicationService productosService;

	@Autowired
	private OmsApplicationService omsService;

	@Autowired
	private OrdenesApplicationService ordenesService;

	// ----------------------------------------------------------------------------------------------------------------
	// -- Vistas Resumen tms-s
	// ----------------------------------------------------------------------------------------------------------------
	@RequestMapping(path = "/tms-s/despachos/resumenes/estado-ordenes-origen", method = { RequestMethod.GET })
	public List<Object> getResumenPorCediDespachosSecundaria(
			@RequestParam(value = "usuarioId", required = true) Integer usuarioId,
			@RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaDesde,
			@RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaHasta

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
			@RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaDesde,
			@RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaHasta

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
			@RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaDesde,
			@RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaHasta,
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
			@RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaDesde,
			@RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaHasta,
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
			@RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaDesde,
			@RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaHasta,
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
			@RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaDesde,
			@RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaHasta

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
			@RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaDesde,
			@RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaHasta

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
			@RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaDesde,
			@RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaHasta

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
			@RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaDesde,
			@RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaHasta

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
				m.put(b, Orden.transicionPermitida(a, b));
			}
		}
		return map;
	}
//
//	@RequestMapping("/causales/anulacion/orden")
//	public List<ItemGenerico<Integer>> getCausalesAnulacionOrden() {
//		List<ItemGenerico<Integer>> list = new ArrayList<>();
//
//		try {
//			//list = omsService.findCausalesAnulacion();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return list;
//	}

	// ----------------------------------------------------------------------------------------------------------------
	// -- Modificaciones
	// ----------------------------------------------------------------------------------------------------------------
	@RequestMapping(value = "/aceptar", method = RequestMethod.POST)
	public MensajesDto aceptarOrdenes(@RequestBody AceptarOrdenDto dto) {
		//		MensajesDto mensajes = new MensajesDto();
		//		mensajes.addMensaje(SeveridadType.FATAL, "Dummy");
		//		throw new BadRequestException(mensajes);
		try {
			return ordenesService.aceptarOrdenes(dto.getIds(), dto.getNotas(), dto.getUsuarioId());
		} catch (Exception e) {
			MensajesDto mensajes = new MensajesDto();
			mensajes.add(e);
			throw new BadRequestException(mensajes);
		}
	}

	@RequestMapping(value = "/anular", method = RequestMethod.POST)
	public MensajesDto anaularOrdenes(@RequestBody AnularOrdenDto dto) {
		try {
			return null;//omsService.anularOrdenes(dto.getUsuarioId(), dto.getIds(), dto.getCausalId(), dto.getNotas());
		} catch (Exception e) {
			MensajesDto mensajes = new MensajesDto();
			mensajes.add(e);
			return mensajes;
		}
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

	// Destinos habilitados para un destinatario, ciudad y tipo de servicio
	@RequestMapping("/destinos_origenes-x-destinatario_remitente-x-tipo_servicio-x-ciudad")
	public List<Map<String, Object>> getDestinosPorDestinatarioPorTipoServicioPorCiudad(
			@RequestParam(required = true) Integer destinatarioId,
			@RequestParam(required = true) Integer tipoServicioId, @RequestParam(required = true) Integer ciudadId) {

		List<Map<String, Object>> list = new ArrayList<>();
		try {
			list = destinosService.findDestinosPorDestinatarioPorTipoServicioPorCiudad(
					destinatarioId, ciudadId);
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
	public List<Map<String, Object>> getRequerimientosDistribucionPorClientePorTipoServicioDestinatario(
			@RequestParam(required = true) Integer clienteId, @RequestParam(required = true) Integer tipoServicioId,
			@RequestParam(required = true) Integer destinatarioId) {

		List<Map<String, Object>> list = new ArrayList<>();
		try {
			list = omsService.findRequerimientosDistribucionPorClientePorTipoServicioDestinatario(clienteId,
					tipoServicioId, destinatarioId);
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
	public List<Map<String, Object>> getRequerimientosAlistamientoPorClientePorTipoServicioDestinatario(
			@RequestParam(required = true) Integer clienteId, @RequestParam(required = true) Integer tipoServicioId,
			@RequestParam(required = true) Integer destinatarioId) {

		List<Map<String, Object>> list = new ArrayList<>();
		try {
			list = omsService.findRequerimientosAlistamientoPorClientePorTipoServicioDestinatario(clienteId,
					tipoServicioId, destinatarioId);
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
}

