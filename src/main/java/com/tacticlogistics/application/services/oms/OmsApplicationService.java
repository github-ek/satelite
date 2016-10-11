package com.tacticlogistics.application.services.oms;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tacticlogistics.application.dto.common.ItemGenerico;
import com.tacticlogistics.application.dto.common.MensajeDto;
import com.tacticlogistics.application.dto.common.MensajesDto;
import com.tacticlogistics.application.dto.oms.OmsOrdenDto;
import com.tacticlogistics.domain.model.common.SeveridadType;
import com.tacticlogistics.domain.model.crm.ClienteRequerimientoAlistamientoAssociation;
import com.tacticlogistics.domain.model.crm.ClienteRequerimientoDistribucionAssociation;
import com.tacticlogistics.domain.model.oms.CausalAnulacion;
import com.tacticlogistics.domain.model.oms.EstadoOrdenType;
import com.tacticlogistics.domain.model.oms.OmsOrden;
import com.tacticlogistics.domain.model.seguridad.Usuario;
import com.tacticlogistics.infrastructure.persistence.crm.ClienteRepository;
import com.tacticlogistics.infrastructure.persistence.oms.CausalAnulacionRepository;
import com.tacticlogistics.infrastructure.persistence.oms.OmsOrdenRepository;
import com.tacticlogistics.infrastructure.persistence.seguridad.UsuarioRepository;
import com.tacticlogistics.infrastructure.services.Basic;

@Service
@Transactional(readOnly = true)
public class OmsApplicationService {
	private static final int SERVICIO_DESPACHOS_PRIMARIA = 1;
	private static final int SERVICIO_DESPACHOS_SECUNDARIA = 3;
	private static final int SERVICIO_RECIBOS = 4;
	private static final int SERVICIO_RECOGIDAS_SECUNDARIA = 5;
	private static final int SERVICIO_TRASLADOS = 6;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private CausalAnulacionRepository causalAnulacionOrdenRepository;

	@Autowired
	private OmsOrdenRepository ordenRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public OmsOrdenDto findOneOrdenPorId(Integer id) throws DataAccessException {
		OmsOrden orden = ordenRepository.findOne(id);
		OmsOrdenDto dto = null;

		if (orden != null) {
			dto = map(orden);
		}

		return dto;
	}

	// ----------------------------------------------------------------------------------------------------------------
	// -- Vistas Resumen
	// ----------------------------------------------------------------------------------------------------------------
	public List<Object> findResumenPorCediDespachosPrimaria(Integer usuarioId, LocalDate fechaDesde, LocalDate fechaHasta) {
		return findResumenEstadoOrdenesPorCeDiOrigen(usuarioId, fechaDesde, fechaHasta, SERVICIO_DESPACHOS_PRIMARIA,
				getQueryResumenEstadoOrdenesDeDespachoPorCeDiOrigen());
	}

	public List<Object> findResumenEstadoOrdenesDeDespachoPorCeDiOrigen(Integer usuarioId, LocalDate fechaDesde,
			LocalDate fechaHasta) {
		return findResumenEstadoOrdenesPorCeDiOrigen(usuarioId, fechaDesde, fechaHasta, SERVICIO_DESPACHOS_SECUNDARIA,
				getQueryResumenEstadoOrdenesDeDespachoPorCeDiOrigen());

	}

	public List<Object> findResumenPorCediRecibosPrimaria(Integer usuarioId, LocalDate fechaDesde, LocalDate fechaHasta) {
		return findResumenEstadoOrdenesPorCeDiOrigen(usuarioId, fechaDesde, fechaHasta, SERVICIO_RECIBOS,
				getQueryResumenPorCediRecibos());

	}

	public List<Object> findResumenPorCediRecogidasSecundaria(Integer usuarioId, LocalDate fechaDesde, LocalDate fechaHasta) {
		return findResumenEstadoOrdenesPorCeDiOrigen(usuarioId, fechaDesde, fechaHasta, SERVICIO_RECOGIDAS_SECUNDARIA,
				getQueryResumenPorCediRecibos());

	}

	public List<Object> findResumenPorCediTraslados(Integer usuarioId, LocalDate fechaDesde, LocalDate fechaHasta) {
		return findResumenPorCeDiTraslados(usuarioId, fechaDesde, fechaHasta, SERVICIO_TRASLADOS,
				getQueryResumenPorCediTraslados());

	}

	public List<Object> findResumenPorCediDespachosTraslado(Integer usuarioId, LocalDate fechaDesde, LocalDate fechaHasta) {
		return findResumenEstadoOrdenesPorCeDiOrigen(usuarioId, fechaDesde, fechaHasta, SERVICIO_TRASLADOS,
				getQueryResumenEstadoOrdenesDeDespachoPorCeDiOrigen());

	}

	public List<Object> findResumenPorCediRecibosTraslado(Integer usuarioId, LocalDate fechaDesde, LocalDate fechaHasta) {
		return findResumenEstadoOrdenesPorCeDiOrigen(usuarioId, fechaDesde, fechaHasta, SERVICIO_TRASLADOS,
				getQueryResumenPorCediRecibos());

	}

	private List<Object> findResumenEstadoOrdenesPorCeDiOrigen(Integer usuarioId, LocalDate fechaDesde, LocalDate fechaHasta,
			int tipoServicioId, String sql) {
		Map<String, Object> parameters = new HashMap<>();

		parameters.put("usuarioId", usuarioId);
		parameters.put("tipoServicioId", tipoServicioId);
		parameters.put("fechaDesde", fechaDesde);
		parameters.put("fechaHasta", fechaHasta);

		return namedParameterJdbcTemplate.query(sql, parameters, (rs, rowNum) -> {
			Map<String, Object> dto = new LinkedHashMap<>();

			dto.put("id_cliente", rs.getInt("id_cliente"));
			dto.put("cliente_codigo", rs.getString("cliente_codigo"));
			dto.put("cliente_ordinal", rs.getInt("cliente_ordinal"));

			dto.put("id_estado_orden", rs.getString("id_estado_orden"));
			dto.put("estado_orden_nombre", rs.getString("estado_orden_nombre"));
			dto.put("estado_orden_ordinal", rs.getInt("estado_orden_ordinal"));

			dto.put("id_ciudad", rs.getInt("id_ciudad"));
			dto.put("ciudad_nombre", rs.getString("ciudad_nombre"));
			dto.put("ciudad_ordinal", rs.getInt("ciudad_ordinal"));

			dto.put("id_bodega", rs.getInt("id_bodega"));
			dto.put("bodega_nombre", rs.getString("bodega_nombre"));
			dto.put("bodega_ordinal", rs.getInt("bodega_ordinal"));

			dto.put("id_row", rs.getString("id_row"));
			dto.put("ciudad_columna", rs.getString("ciudad_columna"));
			dto.put("bodega_columna", rs.getString("bodega_columna"));

			dto.put("n", rs.getInt("n"));

			return dto;
		});
	}

	private List<Object> findResumenPorCeDiTraslados(Integer usuarioId, LocalDate fechaDesde, LocalDate fechaHasta,
			int tipoServicioId, String sql) {
		Map<String, Object> parameters = new HashMap<>();

		parameters.put("usuarioId", usuarioId);
		parameters.put("tipoServicioId", tipoServicioId);
		parameters.put("fechaDesde", fechaDesde);
		parameters.put("fechaHasta", fechaHasta);

		return namedParameterJdbcTemplate.query(sql, parameters, (rs, rowNum) -> {
			Map<String, Object> dto = new LinkedHashMap<>();

			dto.put("id_row", rs.getString("id_row"));
			dto.put("id_cliente", rs.getInt("id_cliente"));
			dto.put("id_estado_orden", rs.getString("id_estado_orden"));
			dto.put("id_ciudad_origen", rs.getInt("id_ciudad_origen"));
			dto.put("id_bodega_origen", rs.getInt("id_bodega_origen"));
			dto.put("id_ciudad_destino", rs.getInt("id_ciudad_destino"));
			dto.put("id_bodega_destino", rs.getInt("id_bodega_destino"));

			dto.put("cliente", rs.getString("cliente"));
			dto.put("estado", rs.getString("estado"));
			dto.put("ciudad_origen", rs.getString("ciudad_origen"));
			dto.put("bodega_origen", rs.getString("bodega_origen"));
			dto.put("ciudad_destino", rs.getString("ciudad_destino"));
			dto.put("bodega_destino", rs.getString("bodega_destino"));
			dto.put("n", rs.getInt("n"));

			return dto;
		});
	}

	private String getQueryResumenEstadoOrdenesDeDespachoPorCeDiOrigen() {
		return "" + " SELECT " + "     a.* "
				+ " FROM oms.ResumenEstadoOrdenesDeDespachoPorCediOrigen (:usuarioId,:tipoServicioId,:fechaDesde,:fechaHasta) a "
				+ " ORDER BY " + "     a.cliente_ordinal,a.estado_orden_ordinal,a.ciudad_ordinal,a.bodega_ordinal "
				+ "";
	}

	private String getQueryResumenPorCediRecibos() {
		return "" + " SELECT " + "     a.* "
				+ " FROM [oms].[ResumenPorCediRecibos] (:usuarioId,:tipoServicioId,:fechaDesde,:fechaHasta) a "
				+ " ORDER BY " + "     a.cliente,a.id_estado_orden,a.bodega " + "";
	}

	private String getQueryResumenPorCediTraslados() {
		return "" + " SELECT " + "     a.* "
				+ " FROM [oms].[ResumenPorCediTraslados] (:usuarioId,:tipoServicioId,:fechaDesde,:fechaHasta) a "
				+ " ORDER BY "
				+ "     a.cliente,a.ciudad_origen,a.bodega_origen,a.id_estado_orden,a.ciudad_destino,a.bodega_destino "
				+ "";
	}

	// ----------------------------------------------------------------------------------------------------------------
	// -- Vistas Solicitudes
	// ----------------------------------------------------------------------------------------------------------------
	public List<Object> findSolcitudesDeDespachoSecundaria(Integer usuarioId, LocalDate fechaDesde, LocalDate fechaHasta,
			int clienteId, String estadoOrdenId, int bodegaOrigenId) {
		return findSolcitudesDeDespacho(usuarioId, fechaDesde, fechaHasta, clienteId, estadoOrdenId, bodegaOrigenId,
				SERVICIO_DESPACHOS_SECUNDARIA, getQueryOrdenesDeDespachoPorClienteEstadoOrdenCediOrigen());

	}

	private List<Object> findSolcitudesDeDespacho(Integer usuarioId, LocalDate fechaDesde, LocalDate fechaHasta, int clienteId,
			String estadoOrdenId, int bodegaOrigenId, int tipoServicioId, String sql) {
		Map<String, Object> parameters = new HashMap<>();

		parameters.put("usuarioId", usuarioId);
		parameters.put("tipoServicioId", tipoServicioId);
		parameters.put("fechaDesde", fechaDesde);
		parameters.put("fechaHasta", fechaHasta);
		parameters.put("clienteId", clienteId);
		parameters.put("estadoOrdenId", estadoOrdenId);
		parameters.put("bodegaOrigenId", bodegaOrigenId);

		return namedParameterJdbcTemplate.query(sql, parameters, (rs, rowNum) -> {
			Map<String, Object> dto = new LinkedHashMap<>();

			dto.put("id_orden", rs.getInt("id_orden"));
			dto.put("numero_orden", rs.getString("numero_orden"));
			dto.put("fecha_orden", rs.getDate("fecha_orden"));
			dto.put("numero_orden_compra", rs.getString("numero_orden_compra"));

			dto.put("fecha_confirmacion", rs.getString("fecha_confirmacion"));
			dto.put("destino_ciudad_nombre", rs.getString("destino_ciudad_nombre"));

			dto.put("id_estado_orden", rs.getString("id_estado_orden"));
			dto.put("id_estado_alistamiento", rs.getString("id_estado_alistamiento"));
			dto.put("id_estado_distribucion", rs.getString("id_estado_distribucion"));
			dto.put("cliente_recoge", rs.getString("cliente_recoge"));
			dto.put("id_estado_cumplidos", rs.getString("id_estado_cumplidos"));

			dto.put("canal_codigo", rs.getString("canal_codigo"));
			dto.put("destinatario_numero_identificacion", rs.getString("destinatario_numero_identificacion"));
			dto.put("destinatario_nombre", rs.getString("destinatario_nombre"));
			dto.put("destino_direccion", rs.getString("destino_direccion"));
			dto.put("destino_nombre", rs.getString("destino_nombre"));
			dto.put("valor_recaudo", rs.getString("valor_recaudo"));

			dto.put("confirmar_cita", rs.getString("confirmar_cita"));
			dto.put("fecha_entrega_sugerida", rs.getString("fecha_entrega_sugerida"));
			dto.put("entrega_sugerida_AMPM", rs.getString("entrega_sugerida_AMPM"));
			dto.put("fecha_cita", rs.getString("fecha_cita"));
			dto.put("cita_AMPM", rs.getString("cita_AMPM"));
			dto.put("fecha_alistamiento", rs.getString("fecha_alistamiento"));
			dto.put("alistamiento_AMPM", rs.getString("alistamiento_AMPM"));
			dto.put("fecha_aceptacion", rs.getString("fecha_aceptacion"));

			return dto;
		});
	}

	private String getQueryOrdenesDeDespachoPorClienteEstadoOrdenCediOrigen() {
		return "" + " SELECT " + "     a.* "
				+ " FROM oms.OrdenesDeDespachoPorClienteEstadoOrdenCediOrigen (:usuarioId,:tipoServicioId,:fechaDesde,:fechaHasta,:clienteId,:estadoOrdenId,:bodegaOrigenId) a "
				+ " ORDER BY " + "     a.fecha_confirmacion,a.destino_ciudad_nombre" + "";
	}

	public List<Object> findLineasOrdenDeDespacho(Integer ordenId) {
		String sql = this.getQueryLineasOrdenesDeDespachoPorOrdenId();

		Map<String, Object> parameters = new HashMap<>();

		parameters.put("ordenId", ordenId);

		return namedParameterJdbcTemplate.query(sql, parameters, (rs, rowNum) -> {
			Map<String, Object> dto = new LinkedHashMap<>();

			dto.put("id_orden", rs.getInt("id_orden"));
			dto.put("id_linea_orden", rs.getInt("id_linea_orden"));

			dto.put("numero_item", rs.getInt("numero_item"));
			dto.put("producto_codigo", rs.getString("producto_codigo"));
			dto.put("descripcion", rs.getString("descripcion"));

			dto.put("cantidad_solicitada", rs.getInt("cantidad_solicitada"));
			dto.put("cantidad_planificada", (Integer) rs.getObject("cantidad_planificada"));
			dto.put("cantidad_alistada", (Integer) rs.getObject("cantidad_alistada"));
			dto.put("cantidad_entregada", (Integer) rs.getObject("cantidad_entregada"));
			dto.put("cantidad_no_entregada", (Integer) rs.getObject("cantidad_no_entregada"));
			dto.put("cantidad_no_entregada_legalizada", (Integer) rs.getObject("cantidad_no_entregada_legalizada"));
			dto.put("cantidad_sobrante", (Integer) rs.getObject("cantidad_sobrante"));
			dto.put("cantidad_sobrante_legalizada", (Integer) rs.getObject("cantidad_sobrante_legalizada"));

			dto.put("bodega_origen_codigo_alterno", rs.getString("bodega_origen_codigo_alterno"));
			dto.put("id_estado_inventario_origen", rs.getString("id_estado_inventario_origen"));
			dto.put("numero_orden_wms_origen", rs.getString("numero_orden_wms_origen"));

			dto.put("valor_declarado_por_unidad", (Integer) rs.getObject("valor_declarado_por_unidad"));
			dto.put("predistribucion_destino_final", rs.getString("predistribucion_destino_final"));
			dto.put("predistribucion_rotulo", rs.getString("predistribucion_rotulo"));
			dto.put("volumen_por_unidad", rs.getObject("volumen_por_unidad"));
			dto.put("peso_bruto_por_unidad", rs.getObject("peso_bruto_por_unidad"));

			dto.put("lote", rs.getString("lote"));
			dto.put("serial", rs.getString("serial"));
			dto.put("cosecha", rs.getString("cosecha"));
			dto.put("requerimiento_estampillado", rs.getString("requerimiento_estampillado"));
			dto.put("requerimiento_salud", rs.getString("requerimiento_salud"));
			dto.put("requerimiento_importe", rs.getString("requerimiento_importe"));
			dto.put("requerimiento_distribuido", rs.getString("requerimiento_distribuido"));
			dto.put("requerimiento_nutricional", rs.getString("requerimiento_nutricional"));
			dto.put("requerimiento_bl", rs.getString("requerimiento_bl"));
			dto.put("requerimiento_fondo_cuenta", rs.getString("requerimiento_fondo_cuenta"));
			dto.put("requerimiento_origen", rs.getString("requerimiento_origen"));

			dto.put("fecha_actualizacion", rs.getDate("fecha_actualizacion"));
			dto.put("usuario_actualizacion", rs.getString("usuario_actualizacion"));

			return dto;
		});
	}

	private String getQueryLineasOrdenesDeDespachoPorOrdenId() {
		return "" + " SELECT " + "     a.* " + " FROM oms.LineasOrdenesDeDespachoPorOrdenId (:ordenId) a "
				+ " ORDER BY " + "     a.numero_item" + "";
	}

	// ----------------------------------------------------------------------------------------------------------------
	// -- Vistas Excepciones
	// ----------------------------------------------------------------------------------------------------------------
	public List<Object> findExcepcionesDeDespachoSecundaria(Integer usuarioId, LocalDate fechaDesde, LocalDate fechaHasta,
			int clienteId, String estadoOrdenId, int bodegaOrigenId) {
		return findExcepcionesDeDespacho(usuarioId, fechaDesde, fechaHasta, clienteId, estadoOrdenId, bodegaOrigenId,
				SERVICIO_DESPACHOS_SECUNDARIA, getQueryExcepcionesDeDespachoPorClienteEstadoOrdenCediOrigen());

	}

	private List<Object> findExcepcionesDeDespacho(Integer usuarioId, LocalDate fechaDesde, LocalDate fechaHasta, int clienteId,
			String estadoOrdenId, int bodegaOrigenId, int tipoServicioId, String sql) {
		Map<String, Object> parameters = new HashMap<>();

		parameters.put("usuarioId", usuarioId);
		parameters.put("tipoServicioId", tipoServicioId);
		parameters.put("fechaDesde", fechaDesde);
		parameters.put("fechaHasta", fechaHasta);
		parameters.put("clienteId", clienteId);
		parameters.put("estadoOrdenId", estadoOrdenId);
		parameters.put("bodegaOrigenId", bodegaOrigenId);

		return namedParameterJdbcTemplate.query(sql, parameters, (rs, rowNum) -> {
			Map<String, Object> dto = new LinkedHashMap<>();

			dto.put("id_orden", rs.getInt("id_orden"));
			dto.put("error_codigo", rs.getString("error_codigo"));
			dto.put("error_nombre", rs.getString("error_nombre"));

			dto.put("numero_orden", rs.getString("numero_orden"));
			dto.put("numero_item", (Integer) rs.getObject("numero_item"));

			dto.put("fecha_confirmacion", rs.getString("fecha_confirmacion"));
			dto.put("destino_ciudad_nombre", rs.getString("destino_ciudad_nombre"));
			dto.put("id_estado_orden", rs.getString("id_estado_orden"));
			dto.put("cliente_recoge", rs.getString("cliente_recoge"));
			dto.put("canal_codigo", rs.getString("canal_codigo"));
			dto.put("destinatario_numero_identificacion", rs.getString("destinatario_numero_identificacion"));
			dto.put("destinatario_nombre", rs.getString("destinatario_nombre"));
			dto.put("destino_direccion", rs.getString("destino_direccion"));
			dto.put("destino_nombre", rs.getString("destino_nombre"));

			dto.put("confirmar_cita", rs.getString("confirmar_cita"));
			dto.put("fecha_entrega_sugerida", rs.getString("fecha_entrega_sugerida"));
			dto.put("entrega_sugerida_AMPM", rs.getString("entrega_sugerida_AMPM"));

			dto.put("fecha_cita", rs.getString("fecha_cita"));
			dto.put("cita_AMPM", rs.getString("cita_AMPM"));

			dto.put("descripcion", rs.getString("descripcion"));
			dto.put("producto_codigo", rs.getString("producto_codigo"));
			dto.put("cantidad_solicitada", (Integer) rs.getObject("cantidad_solicitada"));
			dto.put("unidad_codigo", rs.getString("unidad_codigo"));

			dto.put("bodega_origen_codigo_alterno", rs.getString("bodega_origen_codigo_alterno"));
			dto.put("bodega_origen_codigo", rs.getString("bodega_origen_codigo"));
			dto.put("id_estado_inventario_origen", rs.getString("id_estado_inventario_origen"));

			dto.put("alto_por_unidad", rs.getObject("alto_por_unidad"));
			dto.put("ancho_por_unidad", rs.getObject("ancho_por_unidad"));
			dto.put("largo_por_unidad", rs.getObject("largo_por_unidad"));
			dto.put("peso_bruto_por_unidad", rs.getObject("peso_bruto_por_unidad"));

			return dto;
		});
	}

	private String getQueryExcepcionesDeDespachoPorClienteEstadoOrdenCediOrigen() {
		return "" + " SELECT " + "     a.* "
				+ " FROM oms.ExcepcionesDeOrdenesDeDespachoPorClienteEstadoOrdenCediOrigen (:usuarioId,:tipoServicioId,:fechaDesde,:fechaHasta,:clienteId,:estadoOrdenId,:bodegaOrigenId) a "
				+ " ORDER BY "
				+ "     a.fecha_confirmacion,a.destino_ciudad_nombre,a.numero_orden,a.numero_item,a.error_nombre" + "";
	}

	// ----------------------------------------------------------------------------------------------------------------
	// -- Vistas Entregas
	// ----------------------------------------------------------------------------------------------------------------
	public List<Object> findEntregasDeDespachosSecundaria(Integer usuarioId, LocalDate fechaDesde, LocalDate fechaHasta,
			int clienteId, String estadoOrdenId, int bodegaOrigenId) {
		return findEntregasDeDespachos(usuarioId, fechaDesde, fechaHasta, clienteId, estadoOrdenId, bodegaOrigenId,
				SERVICIO_DESPACHOS_SECUNDARIA, getQueryEntregasDeDespachosPorClienteEstadoOrdenCediOrigen());

	}

	private List<Object> findEntregasDeDespachos(Integer usuarioId, LocalDate fechaDesde, LocalDate fechaHasta, int clienteId,
			String estadoOrdenId, int bodegaOrigenId, int tipoServicioId, String sql) {
		Map<String, Object> parameters = new HashMap<>();

		parameters.put("usuarioId", usuarioId);
		parameters.put("tipoServicioId", tipoServicioId);
		parameters.put("fechaDesde", fechaDesde);
		parameters.put("fechaHasta", fechaHasta);
		parameters.put("clienteId", clienteId);
		parameters.put("estadoOrdenId", estadoOrdenId);
		parameters.put("bodegaOrigenId", bodegaOrigenId);

		return namedParameterJdbcTemplate.query(sql, parameters, (rs, rowNum) -> {
			Map<String, Object> dto = new LinkedHashMap<>();

			dto.put("fecha_confirmacion", rs.getString("fecha_confirmacion"));
			dto.put("destino_ciudad_nombre", rs.getString("destino_ciudad_nombre"));
			dto.put("id_orden", rs.getInt("id_orden"));
			dto.put("numero_orden", rs.getString("numero_orden"));
			dto.put("canal_codigo", rs.getString("canal_codigo"));
			dto.put("destinatario_nombre", rs.getString("destinatario_nombre"));
			dto.put("destinatario_numero_identificacion", rs.getString("destinatario_numero_identificacion"));
			dto.put("destino_direccion", rs.getString("destino_direccion"));
			dto.put("fecha_cita", rs.getString("fecha_cita"));
			dto.put("fecha_entrega", rs.getString("fecha_entrega"));

			dto.put("id_estado_distribucion", rs.getString("id_estado_distribucion"));
			dto.put("causal_novedad_distribucion_nombre", rs.getString("causal_novedad_distribucion_nombre"));
			dto.put("indicador_otif_nombre", rs.getString("indicador_otif_nombre"));
			dto.put("notas", rs.getString("notas"));
			dto.put("tipo_novedad_distribucion_nombre", rs.getString("tipo_novedad_distribucion_nombre"));

			dto.put("numero_item", (Integer) rs.getObject("numero_item"));
			dto.put("descripcion", rs.getString("descripcion"));
			dto.put("producto_codigo", rs.getString("producto_codigo"));
			dto.put("cantidad", (Integer) rs.getObject("cantidad"));
			dto.put("unidad_codigo", rs.getString("unidad_codigo"));

			dto.put("responsable_novedad_nombre", rs.getString("responsable_novedad_nombre"));
			dto.put("responsable_interno_novedad_nombre", rs.getString("responsable_interno_novedad_nombre"));
			dto.put("fecha_creacion", rs.getDate("fecha_creacion"));
			dto.put("usuario_creacion", rs.getString("usuario_creacion"));
			dto.put("fecha_actualizacion", rs.getDate("fecha_actualizacion"));
			dto.put("usuario_actualizacion", rs.getString("usuario_actualizacion"));
			dto.put("id_orden_novedad", (Integer) rs.getObject("id_orden_novedad"));
			dto.put("id_causal_novedad_distribucion", (Integer) rs.getObject("id_causal_novedad_distribucion"));
			dto.put("id_responsable_novedad", (Integer) rs.getObject("id_responsable_novedad"));
			dto.put("id_responsable_interno_novedad", (Integer) rs.getObject("id_responsable_interno_novedad"));
			dto.put("id_indicador_otif", rs.getString("id_indicador_otif"));
			dto.put("id_tipo_novedad_distribucion", (Integer) rs.getObject("id_tipo_novedad_distribucion"));
			dto.put("id_producto", (Integer) rs.getObject("id_producto"));
			dto.put("id_unidad", (Integer) rs.getObject("id_unidad"));

			return dto;
		});
	}

	private String getQueryEntregasDeDespachosPorClienteEstadoOrdenCediOrigen() {
		return "" + " SELECT " + "     a.* "
				+ " FROM oms.EntregasDeDespachosPorClienteEstadoOrdenCediOrigen (:usuarioId,:tipoServicioId,:fechaDesde,:fechaHasta,:clienteId,:estadoOrdenId,:bodegaOrigenId) a "
				+ " ORDER BY " + "     a.fecha_confirmacion,a.destino_ciudad_nombre,a.numero_orden,a.numero_item" + "";
	}

	// ----------------------------------------------------------------------------------------------------------------
	// -- DEPRECATED
	// ----------------------------------------------------------------------------------------------------------------
	@Transactional(readOnly = false)
	public MensajesDto cambiarEstadoOrdenes(Integer usuarioId, List<Integer> ids, EstadoOrdenType nuevoEstado,
			String notas) throws DataAccessException {
		MensajesDto msg = new MensajesDto();
		Usuario usuario = usuarioRepository.findOne(usuarioId);

		String usuarioUpd = usuario.getUsuario();
		LocalDateTime fechaUpd = LocalDateTime.now();
		notas = Basic.coalesce(notas, "");

		for (Integer id : ids) {
			OmsOrden e = ordenRepository.findOne(id);

			if (OmsOrden.transicionPermitida(e.getEstadoOrden(), nuevoEstado)) {
				if (e.getEstadoOrden() == EstadoOrdenType.ANULADA) {
					e.revertirAnulacion(usuarioUpd, fechaUpd, nuevoEstado);
				}

				switch (nuevoEstado) {
				case NO_CONFIRMADA:
					e.revertirConfirmacion(usuarioUpd, fechaUpd);
					break;
				case CONFIRMADA:
					e.revertirAceptacion(usuarioUpd, fechaUpd);
					// SE DEBEN REVERTIR LOS CAMBIOS REALIZADOS DURANTE LA
					// OPERACIÓN?
					break;
				case ACEPTADA:
					e.aceptar(usuarioUpd, fechaUpd, notas);
					break;	
				case ANULADA:
					e.anular(usuarioUpd, fechaUpd, notas, null);
					// SE DEBEN REVERTIR LOS CAMBIOS REALIZADOS DURANTE LA
					// OPERACIÓN?
				case EJECUCION:
					// LO VA A HACER EL SISTEMA
				case FINALIZADA:
					// LO VA A HACER EL SISTEMA
				case REPROGRAMADA:
					// TODO
					break;
				default:
					break;
				}

				try {
					ordenRepository.save(e);
					msg.addMensaje(SeveridadType.INFO, e.getId(), "OK");
				} catch (RuntimeException re) {
					msg.addMensaje(re, e.getId());
				}
			} else {
				msg.AddMensaje(new MensajeDto(SeveridadType.ERROR, id, "El cambio de estado desde " + e.getEstadoOrden()
						+ " a " + nuevoEstado + ", no esta permitido"));
			}
		}
		return msg;
	}

	// ---------------------------------------------------------------------------------------------------------

	// ---------------------------------------------------------------------------------------------------------

	// ---------------------------------------------------------------------------------------------------------

	// ---------------------------------------------------------------------------------------------------------
	public List<Map<String, Object>> findRequerimientosDistribucionPorClientePorTipoServicioDestinatario(
			Integer clienteId, Integer tipoServicioId, Integer destinatarioId) throws DataAccessException {
		List<ClienteRequerimientoDistribucionAssociation> entityList = clienteRepository
				.findClienteRequerimientoDistribucionAssociationByClienteIdAndTipoServicioId(clienteId, tipoServicioId);

		List<Map<String, Object>> list = new ArrayList<>();
		entityList.forEach(a -> {
			Map<String, Object> map = new HashMap<>();
			map.put("codigoAlterno", a.getCodigoAlterno());
			map.put("requerimientoDistribucionId", a.getRequerimientoDistribucionId());
			map.put("descripcion", a.getDescripcion());
			list.add(map);
		});
		return list;
	}

	public List<Map<String, Object>> findRequerimientosAlistamientoPorClientePorTipoServicioDestinatario(
			Integer clienteId, Integer tipoServicioId, Integer destinatarioId) throws DataAccessException {
		List<ClienteRequerimientoAlistamientoAssociation> entityList = clienteRepository
				.findClienteRequerimientoAlistamientoAssociationByClienteIdAndTipoServicioId(clienteId, tipoServicioId);

		List<Map<String, Object>> list = new ArrayList<>();
		entityList.forEach(a -> {
			Map<String, Object> map = new HashMap<>();
			map.put("codigoAlterno", a.getCodigoAlterno());
			map.put("requerimientoAlistamientoId", a.getRequerimientoAlistamientoId());
			map.put("descripcion", a.getDescripcion());
			list.add(map);
		});
		return list;
	}

	public List<ItemGenerico<Integer>> findCausalesAnulacion() throws DataAccessException {
		List<CausalAnulacion> entityList = causalAnulacionOrdenRepository.findAll(new Sort("ordinal"));

		List<ItemGenerico<Integer>> list = new ArrayList<>();
		entityList.forEach(a -> {
			list.add(new ItemGenerico<Integer>(a.getId(), "", a.getNombre()));
		});
		return list;
	}

	protected OmsOrdenDto map(OmsOrden orden) {
		
		return null;
	}
}
