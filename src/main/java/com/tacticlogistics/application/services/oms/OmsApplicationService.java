package com.tacticlogistics.application.services.oms;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import com.tacticlogistics.application.dto.common.ItemGenerico;
import com.tacticlogistics.application.dto.common.MensajeDto;
import com.tacticlogistics.application.dto.common.MensajesDto;
import com.tacticlogistics.application.dto.oms.OmsOrdenDto;
import com.tacticlogistics.application.dto.oms.OrdenDetailDto;
import com.tacticlogistics.application.dto.oms.OrdenPivotDto;
import com.tacticlogistics.domain.model.common.SeveridadType;
import com.tacticlogistics.domain.model.common.valueobjects.Contacto;
import com.tacticlogistics.domain.model.common.valueobjects.Dimensiones;
import com.tacticlogistics.domain.model.crm.ClienteRequerimientoAlistamientoAssociation;
import com.tacticlogistics.domain.model.crm.ClienteRequerimientoDistribucionAssociation;
import com.tacticlogistics.domain.model.oms.CausalSolicitudRevisionCliente;
import com.tacticlogistics.domain.model.oms.CausalSolicitudRevisionPlaneacionLogistica;
import com.tacticlogistics.domain.model.oms.EstadoAlistamientoType;
import com.tacticlogistics.domain.model.oms.EstadoDistribucionType;
import com.tacticlogistics.domain.model.oms.EstadoOrdenType;
import com.tacticlogistics.domain.model.oms.OmsCausalAnulacionOrden;
import com.tacticlogistics.domain.model.oms.OmsOrden;
import com.tacticlogistics.domain.model.seguridad.Usuario;
import com.tacticlogistics.infrastructure.persistence.crm.ClienteRepository;
import com.tacticlogistics.infrastructure.persistence.oms.CausalSolicitudRevisionClienteRepository;
import com.tacticlogistics.infrastructure.persistence.oms.CausalSolicitudRevisionPlaneacionLogisticaRepository;
import com.tacticlogistics.infrastructure.persistence.oms.OmsCausalAnulacionOrdenRepository;
import com.tacticlogistics.infrastructure.persistence.oms.OmsOrdenRepository;
import com.tacticlogistics.infrastructure.persistence.seguridad.UsuarioRepository;
import com.tacticlogistics.infrastructure.services.Basic;

@Service
@Transactional(readOnly = true)
public class OmsApplicationService {
	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private CausalSolicitudRevisionClienteRepository causalSolicitudRevisionClienteRepository;

	@Autowired
	private CausalSolicitudRevisionPlaneacionLogisticaRepository causalSolicitudRevisionPlaneacionLogisticaRepository;

	@Autowired
	private OmsCausalAnulacionOrdenRepository causalAnulacionOrdenRepository;

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

	private static final int SERVICIO_DESPACHOS_PRIMARIA = 1;
	private static final int SERVICIO_DESPACHOS_SECUNDARIA = 3;
	private static final int SERVICIO_RECIBOS = 4;
	private static final int SERVICIO_RECOGIDAS_SECUNDARIA = 5;
	private static final int SERVICIO_TRASLADOS = 6;

	// ----------------------------------------------------------------------------------------------------------------
	// -- Vistas Resumen
	// ----------------------------------------------------------------------------------------------------------------
	public List<Object> findResumenPorCediDespachosPrimaria(Integer usuarioId, Date fechaDesde, Date fechaHasta) {
		return findResumenEstadoOrdenesPorCeDiOrigen(usuarioId, fechaDesde, fechaHasta, SERVICIO_DESPACHOS_PRIMARIA,
				getQueryResumenEstadoOrdenesDeDespachoPorCeDiOrigen());
	}

	public List<Object> findResumenEstadoOrdenesDeDespachoPorCeDiOrigen(Integer usuarioId, Date fechaDesde, Date fechaHasta) {
		return findResumenEstadoOrdenesPorCeDiOrigen(
				usuarioId, 
				fechaDesde, 
				fechaHasta, 
				SERVICIO_DESPACHOS_SECUNDARIA,
				getQueryResumenEstadoOrdenesDeDespachoPorCeDiOrigen());

	}

	public List<Object> findResumenPorCediRecibosPrimaria(Integer usuarioId, Date fechaDesde, Date fechaHasta) {
		return findResumenEstadoOrdenesPorCeDiOrigen(usuarioId, fechaDesde, fechaHasta, SERVICIO_RECIBOS, getQueryResumenPorCediRecibos());

	}

	public List<Object> findResumenPorCediRecogidasSecundaria(Integer usuarioId, Date fechaDesde, Date fechaHasta) {
		return findResumenEstadoOrdenesPorCeDiOrigen(usuarioId, fechaDesde, fechaHasta, SERVICIO_RECOGIDAS_SECUNDARIA,
				getQueryResumenPorCediRecibos());

	}

	public List<Object> findResumenPorCediTraslados(Integer usuarioId, Date fechaDesde, Date fechaHasta) {
		return findResumenPorCeDiTraslados(usuarioId, fechaDesde, fechaHasta, SERVICIO_TRASLADOS,
				getQueryResumenPorCediTraslados());

	}

	public List<Object> findResumenPorCediDespachosTraslado(Integer usuarioId, Date fechaDesde, Date fechaHasta) {
		return findResumenEstadoOrdenesPorCeDiOrigen(usuarioId, fechaDesde, fechaHasta, SERVICIO_TRASLADOS,
				getQueryResumenEstadoOrdenesDeDespachoPorCeDiOrigen());

	}

	public List<Object> findResumenPorCediRecibosTraslado(Integer usuarioId, Date fechaDesde, Date fechaHasta) {
		return findResumenEstadoOrdenesPorCeDiOrigen(usuarioId, fechaDesde, fechaHasta, SERVICIO_TRASLADOS,
				getQueryResumenPorCediRecibos());

	}
	
	private List<Object> findResumenEstadoOrdenesPorCeDiOrigen(Integer usuarioId, Date fechaDesde, Date fechaHasta, int tipoServicioId,
			String sql) {
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

	private List<Object> findResumenPorCeDiTraslados(Integer usuarioId, Date fechaDesde, Date fechaHasta,
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
		return "" 
				+ " SELECT " 
				+ "     a.* "
				+ " FROM oms.ResumenEstadoOrdenesDeDespachoPorCediOrigen (:usuarioId,:tipoServicioId,:fechaDesde,:fechaHasta) a "
				+ " ORDER BY " 
				+ "     a.cliente_ordinal,a.estado_orden_ordinal,a.ciudad_ordinal,a.bodega_ordinal " 
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
				+ " ORDER BY " + "     a.cliente,a.ciudad_origen,a.bodega_origen,a.id_estado_orden,a.ciudad_destino,a.bodega_destino " + "";
	}
	
	// ----------------------------------------------------------------------------------------------------------------
	// -- Vistas Solicitudes
	// ----------------------------------------------------------------------------------------------------------------
	public List<Object> findSolcitudesDeDespachoSecundaria(Integer usuarioId, Date fechaDesde, Date fechaHasta,int clienteId, String estadoOrdenId,int bodegaOrigenId) {
		return findSolcitudesDeDespacho(
				usuarioId, 
				fechaDesde, 
				fechaHasta,
				clienteId,
				estadoOrdenId,
				bodegaOrigenId,
				SERVICIO_DESPACHOS_SECUNDARIA,
				getQueryOrdenesDeDespachoPorClienteEstadoOrdenCediOrigen());

	}
	
	private List<Object> findSolcitudesDeDespacho(
			Integer usuarioId, 
			Date fechaDesde, 
			Date fechaHasta,
			int clienteId, 
			String estadoOrdenId,
			int bodegaOrigenId,
			int tipoServicioId,
			String sql) {
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
		return "" 
				+ " SELECT " 
				+ "     a.* "
				+ " FROM oms.OrdenesDeDespachoPorClienteEstadoOrdenCediOrigen (:usuarioId,:tipoServicioId,:fechaDesde,:fechaHasta,:clienteId,:estadoOrdenId,:bodegaOrigenId) a "
				+ " ORDER BY " 
				+ "     a.fecha_confirmacion,a.destino_ciudad_nombre" 
				+ "";
	}
	
	public List<Object> findLineasOrdenDeDespacho(
			Integer ordenId
			) {
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
			dto.put("cantidad_planificada", (Integer)rs.getObject("cantidad_planificada"));
			dto.put("cantidad_alistada", (Integer)rs.getObject("cantidad_alistada"));
			dto.put("cantidad_entregada", (Integer)rs.getObject("cantidad_entregada"));
			dto.put("cantidad_no_entregada", (Integer)rs.getObject("cantidad_no_entregada"));
			dto.put("cantidad_no_entregada_legalizada", (Integer)rs.getObject("cantidad_no_entregada_legalizada"));
			dto.put("cantidad_sobrante", (Integer)rs.getObject("cantidad_sobrante"));
			dto.put("cantidad_sobrante_legalizada", (Integer)rs.getObject("cantidad_sobrante_legalizada"));
			
			dto.put("bodega_origen_codigo_alterno", rs.getString("bodega_origen_codigo_alterno"));
			dto.put("id_estado_inventario_origen", rs.getString("id_estado_inventario_origen"));
			dto.put("numero_orden_wms_origen", rs.getString("numero_orden_wms_origen"));
			
			dto.put("valor_declarado_por_unidad", (Integer)rs.getObject("valor_declarado_por_unidad"));
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
		return "" 
				+ " SELECT " 
				+ "     a.* "
				+ " FROM oms.LineasOrdenesDeDespachoPorOrdenId (:ordenId) a "
				+ " ORDER BY " 
				+ "     a.numero_item" 
				+ "";
	}
	
	// ----------------------------------------------------------------------------------------------------------------
	// -- Vistas Excepciones
	// ----------------------------------------------------------------------------------------------------------------
	public List<Object> findExcepcionesDeDespachoSecundaria(Integer usuarioId, Date fechaDesde, Date fechaHasta,int clienteId, String estadoOrdenId,int bodegaOrigenId) {
		return findExcepcionesDeDespacho(
				usuarioId, 
				fechaDesde, 
				fechaHasta,
				clienteId,
				estadoOrdenId,
				bodegaOrigenId,
				SERVICIO_DESPACHOS_SECUNDARIA,
				getQueryExcepcionesDeDespachoPorClienteEstadoOrdenCediOrigen());

	}
	
	private List<Object> findExcepcionesDeDespacho(
			Integer usuarioId, 
			Date fechaDesde, 
			Date fechaHasta,
			int clienteId, 
			String estadoOrdenId,
			int bodegaOrigenId,
			int tipoServicioId,
			String sql) {
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
			dto.put("numero_item", (Integer)rs.getObject("numero_item"));
			
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
			dto.put("cantidad_solicitada", (Integer)rs.getObject("cantidad_solicitada"));
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
		return "" 
				+ " SELECT " 
				+ "     a.* "
				+ " FROM oms.ExcepcionesDeOrdenesDeDespachoPorClienteEstadoOrdenCediOrigen (:usuarioId,:tipoServicioId,:fechaDesde,:fechaHasta,:clienteId,:estadoOrdenId,:bodegaOrigenId) a "
				+ " ORDER BY " 
				+ "     a.fecha_confirmacion,a.destino_ciudad_nombre,a.numero_orden,a.numero_item,a.error_nombre" 
				+ "";
	}

	// ----------------------------------------------------------------------------------------------------------------
	// -- Vistas Entregas
	// ----------------------------------------------------------------------------------------------------------------
	public List<Object> findEntregasDeDespachosSecundaria(Integer usuarioId, Date fechaDesde, Date fechaHasta,int clienteId, String estadoOrdenId,int bodegaOrigenId) {
		return findEntregasDeDespachos(
				usuarioId, 
				fechaDesde, 
				fechaHasta,
				clienteId,
				estadoOrdenId,
				bodegaOrigenId,
				SERVICIO_DESPACHOS_SECUNDARIA,
				getQueryEntregasDeDespachosPorClienteEstadoOrdenCediOrigen());

	}
	
	private List<Object> findEntregasDeDespachos(
			Integer usuarioId, 
			Date fechaDesde, 
			Date fechaHasta,
			int clienteId, 
			String estadoOrdenId,
			int bodegaOrigenId,
			int tipoServicioId,
			String sql) {
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
			
			dto.put("numero_item", (Integer)rs.getObject("numero_item"));
			dto.put("descripcion", rs.getString("descripcion"));
			dto.put("producto_codigo", rs.getString("producto_codigo"));
			dto.put("cantidad", (Integer)rs.getObject("cantidad"));
			dto.put("unidad_codigo", rs.getString("unidad_codigo"));
		
			dto.put("responsable_novedad_nombre", rs.getString("responsable_novedad_nombre"));
			dto.put("responsable_interno_novedad_nombre", rs.getString("responsable_interno_novedad_nombre"));
			dto.put("fecha_creacion", rs.getDate("fecha_creacion"));
			dto.put("usuario_creacion", rs.getString("usuario_creacion"));
			dto.put("fecha_actualizacion", rs.getDate("fecha_actualizacion"));
			dto.put("usuario_actualizacion", rs.getString("usuario_actualizacion"));
			dto.put("id_orden_novedad", (Integer)rs.getObject("id_orden_novedad"));
			dto.put("id_causal_novedad_distribucion", (Integer)rs.getObject("id_causal_novedad_distribucion"));
			dto.put("id_responsable_novedad", (Integer)rs.getObject("id_responsable_novedad"));
			dto.put("id_responsable_interno_novedad", (Integer)rs.getObject("id_responsable_interno_novedad"));
			dto.put("id_indicador_otif", rs.getString("id_indicador_otif"));
			dto.put("id_tipo_novedad_distribucion", (Integer)rs.getObject("id_tipo_novedad_distribucion"));
			dto.put("id_producto", (Integer)rs.getObject("id_producto"));
			dto.put("id_unidad", (Integer)rs.getObject("id_unidad"));

			return dto;
		});
	}

	private String getQueryEntregasDeDespachosPorClienteEstadoOrdenCediOrigen() {
		return "" 
				+ " SELECT " 
				+ "     a.* "
				+ " FROM oms.EntregasDeDespachosPorClienteEstadoOrdenCediOrigen (:usuarioId,:tipoServicioId,:fechaDesde,:fechaHasta,:clienteId,:estadoOrdenId,:bodegaOrigenId) a "
				+ " ORDER BY " 
				+ "     a.fecha_confirmacion,a.destino_ciudad_nombre,a.numero_orden,a.numero_item" 
				+ "";
	}

	// ----------------------------------------------------------------------------------------------------------------
	// -- DEPRECATED
	// ----------------------------------------------------------------------------------------------------------------
	@Deprecated
	public List<Object> findOrdenesResumenPorFechaConfirmacion(Integer usuarioId, Date fechaConfirmacion)
			throws DataAccessException {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("usuarioId", usuarioId);
		parameters.put("fechaConfirmacion", fechaConfirmacion);

		return namedParameterJdbcTemplate.query(getQueryOrdenesResumenPorFechaConfirmacion(), parameters,
				(rs, rowNum) -> {
					Map<String, Object> dto = new LinkedHashMap<>();

					dto.put("id_cliente", rs.getInt("id_cliente"));
					dto.put("cliente_codigo", rs.getString("cliente_codigo"));
					dto.put("id_tipo_servicio", rs.getInt("id_tipo_servicio"));
					dto.put("servicio_codigo", rs.getString("servicio_codigo"));
					dto.put("servicio_ordinal", rs.getInt("servicio_ordinal"));
					dto.put("ciudad_bodega_nombre", rs.getString("ciudad_bodega_nombre"));
					dto.put("ciudad_bodega_ordinal", rs.getInt("ciudad_bodega_ordinal"));
					dto.put("bodega_codigo", rs.getString("bodega_codigo"));
					dto.put("id_bodega_origen", (Integer) rs.getObject("id_bodega_origen"));
					dto.put("id_bodega_destino", (Integer) rs.getObject("id_bodega_destino"));
					dto.put("CONFIRMADA", rs.getInt("CONFIRMADA"));
					dto.put("ACEPTADA", rs.getInt("ACEPTADA"));
					dto.put("EJECUCION", rs.getInt("EJECUCION"));
					dto.put("FINALIZADA", rs.getInt("FINALIZADA"));
					dto.put("REPROGRAMADA", rs.getInt("REPROGRAMADA"));
					dto.put("ANULADA", rs.getInt("ANULADA"));
					dto.put("NO_CONFIRMADA", rs.getInt("NO_CONFIRMADA"));

					return dto;
				});
	}

	public List<Object> findOrdenesPorCriterio(Integer usuarioId, Integer clienteId, Integer tipoServicioId,
			Integer bodegaOrigenId, Integer bodegaDestinoId, EstadoOrdenType estadoOrdenId, Date fechaConfirmacion)
			throws DataAccessException {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("usuarioId", usuarioId);
		parameters.put("clienteId", clienteId);
		parameters.put("tipoServicioId", tipoServicioId);
		parameters.put("bodegaOrigenId", bodegaOrigenId);
		parameters.put("bodegaDestinoId", bodegaDestinoId);
		parameters.put("estadoOrdenId", estadoOrdenId.toString());
		parameters.put("fechaConfirmacion", fechaConfirmacion);

		return namedParameterJdbcTemplate.query(getQueryOrdenePorCriterio(), parameters, (rs, rowNum) -> {
			Map<String, Object> dto = new LinkedHashMap<>();

			dto.put("id_orden", rs.getInt("id_orden"));
			dto.put("id_linea_orden", rs.getInt("id_linea_orden"));
			dto.put("inconsistente", rs.getBoolean("inconsistente"));
			dto.put("cliente_codigo", rs.getString("cliente_codigo"));
			dto.put("tipo_servicio_codigo", rs.getString("tipo_servicio_codigo"));
			dto.put("numero_orden", rs.getString("numero_orden"));
			dto.put("id_estado_orden", rs.getString("id_estado_orden"));
			dto.put("id_estado_distribucion", rs.getString("id_estado_distribucion"));
			dto.put("id_estado_alistamiento", rs.getString("id_estado_alistamiento"));
			dto.put("cumplidos_transportador", rs.getBoolean("cumplidos_transportador"));
			dto.put("cumplidos_cliente", rs.getBoolean("cumplidos_cliente"));
			dto.put("requiere_servicio_distribucion", rs.getBoolean("requiere_servicio_distribucion"));
			dto.put("destino_ciudad_nombre_alterno", rs.getString("destino_ciudad_nombre_alterno"));
			dto.put("destino_direccion", rs.getString("destino_direccion"));
			dto.put("requiere_confirmacion_cita_entrega", rs.getBoolean("requiere_confirmacion_cita_entrega"));
			dto.put("fecha_entrega_sugerida_minima", rs.getDate("fecha_entrega_sugerida_minima"));
			dto.put("fecha_entrega_sugerida_maxima", rs.getDate("fecha_entrega_sugerida_maxima"));
			dto.put("hora_entrega_sugerida_minima", rs.getTime("hora_entrega_sugerida_minima"));
			dto.put("hora_entrega_sugerida_maxima", rs.getTime("hora_entrega_sugerida_maxima"));
			dto.put("canal_codigo", rs.getString("canal_codigo"));
			dto.put("destinatario_numero_identificacion", rs.getString("destinatario_numero_identificacion"));
			dto.put("destinatario_nombre", rs.getString("destinatario_nombre"));
			dto.put("destino_nombre", rs.getString("destino_nombre"));
			dto.put("valor_recaudo", (Integer) rs.getObject("valor_recaudo"));
			dto.put("notas_confirmacion", rs.getString("notas_confirmacion"));
			dto.put("numero_item", rs.getInt("numero_item"));
			dto.put("descripcion", rs.getString("descripcion"));
			dto.put("cantidad_solicitada", rs.getInt("cantidad_solicitada"));
			dto.put("producto_codigo", rs.getString("producto_codigo"));
			dto.put("unidad_codigo", rs.getString("unidad_codigo"));
			dto.put("valor_declarado_por_unidad", (Integer) rs.getObject("valor_declarado_por_unidad"));
			dto.put("bodega_origen_codigo", rs.getString("bodega_origen_codigo"));
			dto.put("id_estado_inventario_origen", rs.getString("id_estado_inventario_origen"));
			dto.put("numero_orden_wms_origen", rs.getString("numero_orden_wms_origen"));
			dto.put("bodega_destino_codigo", rs.getString("bodega_destino_codigo"));
			dto.put("id_estado_inventario_destino", rs.getString("id_estado_inventario_destino"));
			dto.put("numero_orden_wms_destino", rs.getString("numero_orden_wms_destino"));
			dto.put("predistribucion_destino_final", rs.getString("predistribucion_destino_final"));
			dto.put("predistribucion_rotulo", rs.getString("predistribucion_rotulo"));
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
			dto.put("usuario_confirmacion", rs.getString("usuario_confirmacion"));
			dto.put("fecha_confirmacion", rs.getDate("fecha_confirmacion"));
			dto.put("fecha_aprobacion_planificacion", rs.getDate("fecha_aprobacion_planificacion"));
			dto.put("usuario_aprobacion_planificacion", rs.getString("usuario_aprobacion_planificacion"));
			dto.put("fecha_actualizacion", rs.getDate("fecha_actualizacion"));
			dto.put("usuario_actualizacion", rs.getString("usuario_actualizacion"));

			return dto;
		});
	}

	public List<Object> findOrdenesPorFechaConfirmacion(Integer usuarioId, Date fechaDesde) throws DataAccessException {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("usuarioId", usuarioId);
		parameters.put("fechaDesde", fechaDesde);

		return namedParameterJdbcTemplate.query(getQueryOrdenesPorFechaConfirmacion(), parameters, (rs, rowNum) -> {
			OrdenPivotDto dto = new OrdenPivotDto();
			mapPivot(rs, dto);
			return dto;
		});
	}

	@Deprecated
	public List<Object> findOrdenesPorId(Integer usuarioId, List<Integer> ids) {
		StringBuilder sb = new StringBuilder();
		for (Integer i : ids) {
			sb.append("(").append(i).append("),");
		}

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("usuarioId", usuarioId);

		String sql = getQueryOrdenesPorid();
		sql = sql.replaceAll("\\{\\$values\\}", sb.toString());

		return namedParameterJdbcTemplate.query(sql, parameters, (rs, rowNum) -> {

			OrdenDetailDto dto = new OrdenDetailDto();

			mapPivot(rs, dto);
			mapDetail(rs, dto);

			return dto;
		});
	}

	@Transactional(readOnly = false)
	public MensajesDto cambiarEstadoOrdenes(Integer usuarioId, List<Integer> ids, EstadoOrdenType nuevoEstado,
			String notas) throws DataAccessException {
		MensajesDto msg = new MensajesDto();
		Usuario usuario = usuarioRepository.findOne(usuarioId);

		String usuarioUpd = usuario.getUsuario();
		Date fechaUpd = new Date();
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

	@Transactional(readOnly = false)
	public MensajesDto anularOrdenes(Integer usuarioId, List<Integer> ids, int causalId, String notas)
			throws DataAccessException {
		MensajesDto msg = new MensajesDto();
		Usuario usuario = usuarioRepository.findOne(usuarioId);

		String usuarioUpd = usuario.getUsuario();
		Date fechaUpd = new Date();
		notas = Basic.coalesce(notas, "");

		for (Integer id : ids) {
			OmsOrden e = ordenRepository.findOne(id);
			EstadoOrdenType nuevoEstado = EstadoOrdenType.ANULADA;

			if (OmsOrden.transicionPermitida(e.getEstadoOrden(), nuevoEstado)) {
				e.anular(usuarioUpd, fechaUpd, notas, null);
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
	private void mapPivot(ResultSet rs, OrdenPivotDto dto) throws SQLException {
		dto.setId(rs.getInt("id"));
		dto.setNumeroOrden(rs.getString("numeroOrden"));
		dto.setNumeroConsolidado(rs.getString("numeroConsolidado"));
		dto.setEstadoOrden(EstadoOrdenType.valueOf(rs.getString("estadoOrden")));
		dto.setEstadoDistribucion(rs.getString("estadoDistribucion") == null ? null
				: EstadoDistribucionType.valueOf(rs.getString("estadoDistribucion")));
		dto.setEstadoAlistamiento(rs.getString("estadoAlistamiento") == null ? null
				: EstadoAlistamientoType.valueOf(rs.getString("estadoAlistamiento")));

		dto.setClienteId(rs.getInt("clienteId"));
		dto.setClienteCodigo(rs.getString("clienteCodigo"));
		dto.setClienteNombre(rs.getString("clienteNombre"));

		dto.setTipoServicioId(rs.getInt("tipoServicioId"));
		dto.setTipoServicioCodigo(rs.getString("tipoServicioCodigo"));
		dto.setTipoServicioCodigoAlterno(rs.getString("tipoServicioCodigoAlterno"));
		dto.setTipoServicioNombre(rs.getString("tipoServicioNombre"));

		dto.setRequiereServicioDistribucion(rs.getBoolean("requiereServicioDistribucion"));
		dto.setRequiereServicioAlistamiento(rs.getBoolean("requiereServicioAlistamiento"));

		dto.setCiudadDestinoId((Integer) rs.getObject("ciudadDestinoId"));
		dto.setCiudadDestinoNombre(rs.getString("ciudadDestinoNombre"));
		dto.setDestinoDireccion(rs.getString("destinoDireccion"));

		dto.setCiudadOrigenId((Integer) rs.getObject("ciudadOrigenId"));
		dto.setCiudadOrigenNombre(rs.getString("ciudadOrigenNombre"));
		dto.setOrigenDireccion(rs.getString("origenDireccion"));

		dto.setRequiereConfirmacionCitaEntrega(rs.getBoolean("requiereConfirmacionCitaEntrega"));
		dto.setFechaEntregaMinima(rs.getDate("fechaEntregaMinima"));
		dto.setFechaEntregaMaxima(rs.getDate("fechaEntregaMaxima"));
		dto.setHoraEntregaMinima(rs.getTime("horaEntregaMinima"));
		dto.setHoraEntregaMaxima(rs.getTime("horaEntregaMaxima"));
		dto.setHoraEntregaMinimaAdicional(rs.getTime("horaEntregaMinimaAdicional"));
		dto.setHoraEntregaMaximaAdicional(rs.getTime("horaEntregaMaximaAdicional"));

		dto.setCanalId((Integer) rs.getObject("canalId"));
		dto.setCanalCodigo(rs.getString("canalCodigo"));
		dto.setCanalNombre(rs.getString("canalNombre"));
		dto.setCanalCodigoAlterno(rs.getString("canalCodigoAlterno"));

		dto.setDestinatarioId((Integer) rs.getObject("destinatarioId"));
		dto.setDestinatarioIdentificacion(rs.getString("destinatarioIdentificacion"));
		dto.setDestinatarioNombre(rs.getString("destinatarioNombre"));
		dto.setDestinatarioContacto(new Contacto(rs.getString("destinatarioContactoNombre"),
				rs.getString("destinatarioContactoEmail"), rs.getString("destinatarioContactoTelefono")));

		dto.setDestinoId((Integer) rs.getObject("destinoId"));
		dto.setDestinoNombre(rs.getString("destinoNombre"));
		dto.setDestinoContacto(new Contacto(rs.getString("destinoContactoNombre"), rs.getString("destinoContactoEmail"),
				rs.getString("destinoContactoTelefono")));

		dto.setOrigenId((Integer) rs.getObject("origenId"));
		dto.setOrigenNombre(rs.getString("origenNombre"));
		dto.setOrigenContacto(new Contacto(rs.getString("origenContactoNombre"), rs.getString("origenContactoEmail"),
				rs.getString("origenContactoTelefono")));

		dto.setNotasRequerimientosDistribucion(rs.getString("notasRequerimientosDistribucion"));
		dto.setNotasRequerimientosAlistamiento(rs.getString("notasRequerimientosAlistamiento"));
		dto.setValorRecaudo((Integer) rs.getObject("valorRecaudo"));

		dto.setNotasConfirmacion(rs.getString("notasConfirmacion"));
		dto.setFechaConfirmacion(rs.getDate("fechaConfirmacion"));
		dto.setUsuarioConfirmacion(rs.getString("usuarioConfirmacion"));

		// dto.setFechaAlistamientoPlanificada(rs.getDate("fechaAlistamientoPlanificada"));
		// dto.setHoraAlistamientoPlanificadaMinima((rs.getTime("horaAlistamientoPlanificadaMinima"));
		// dto.setHoraAlistamientoPlanificadaMaxima((rs.getTime("horaAlistamientoPlanificadaMaxima"));
		// dto.setTipoVehiculoPlanificadoId(rs.getInt("tipoVehiculoPl)anificadoId"));
		// dto.setTipoVehiculoPlanificadoCodigo(rs.getString("tipoVehiculoPlanificadoCodigo"));
		// dto.setTipoVehiculoPlanificadoNombre(rs.getString("tipoVehiculoPlanificadoNombre"));
		// dto.setValorFletePlanificado((Integer)rs.getObject("valorFletePlanificado"));
		dto.setNotasAprobacionPlanificacion(rs.getString("notasAprobacionPlanificacion"));
		dto.setFechaAprobacionPlanificacion(rs.getDate("fechaAprobacionPlanificacion"));
		dto.setUsuarioAprobacionPlanificacion(rs.getString("usuarioAprobacionPlanificacion"));

		// dto.setRutaId
		// dto.setFechaAsignacionRuta
		// dto.setUsuarioAsignacionRuta

		dto.setFechaCreacion(rs.getDate("fechaCreacion"));
		dto.setUsuarioCreacion(rs.getString("usuarioCreacion"));
		dto.setFechaActualizacion(rs.getDate("fechaActualizacion"));
		dto.setUsuarioActualizacion(rs.getString("usuarioActualizacion"));

		dto.setNotasAnulacion(rs.getString("notasAnulacion"));
		dto.setCausalAnulacionId(rs.getInt("causalAnulacionId"));
		dto.setFechaAnulacion(rs.getDate("fechaAnulacion"));
		dto.setUsuarioAnulacion(rs.getString("usuarioAnulacion"));
	}

	private void mapDetail(ResultSet rs, OrdenDetailDto dto) throws SQLException {
		dto.setNumeroItem(rs.getInt("numeroItem"));
		dto.setDescripcion(rs.getString("descripcion"));
		dto.setCantidadSolicitada((Integer) rs.getObject("cantidadSolicitada"));
		dto.setCantidadPlanificada((Integer) rs.getObject("cantidadPlanificada"));
		dto.setCantidadAlistada((Integer) rs.getObject("cantidadAlistada"));
		dto.setCantidadEntregada((Integer) rs.getObject("cantidadEntregada"));
		dto.setCantidadNoEntregada((Integer) rs.getObject("cantidadNoEntregada"));
		dto.setCantidadNoEntregadaLegalizada((Integer) rs.getObject("cantidadNoEntregadaLegalizada"));
		dto.setCantidadSobrante((Integer) rs.getObject("cantidadSobrante"));
		dto.setCantidadSobranteLegalizada((Integer) rs.getObject("cantidadSobranteLegalizada"));

		dto.setProductoId((Integer) rs.getObject("productoId"));
		dto.setProductoCodigo(rs.getString("productoCodigo"));
		dto.setProductoCodigoAlterno(rs.getString("productoCodigoAlterno"));

		dto.setUnidadId((Integer) rs.getObject("unidadId"));
		dto.setUnidadCodigo(rs.getString("unidadCodigo"));
		dto.setUnidadCodigoAlterno(rs.getString("unidadCodigoAlterno"));

		dto.setDimensiones(new Dimensiones((BigDecimal) rs.getObject("largoPorUnidad"),
				(BigDecimal) rs.getObject("anchoPorUnidad"), (BigDecimal) rs.getObject("altoPorUnidad"),
				(BigDecimal) rs.getObject("pesoBrutoPorUnidad")));

		dto.setBodegaOrigenId((Integer) rs.getObject("bodegaOrigenId"));
		dto.setBodegaOrigenCodigo(rs.getString("bodegaOrigenCodigo"));
		dto.setBodegaOrigenCodigoAlterno(rs.getString("bodegaOrigenCodigoAlterno"));
		dto.setEstadoInventarioOrigenId(rs.getString("estadoInventarioOrigenId"));
		dto.setNumeroOrdenWmsOrigen(rs.getString("numeroOrdenWmsOrigen"));

		dto.setBodegaDestinoId((Integer) rs.getObject("bodegaDestinoId"));
		dto.setBodegaDestinoCodigo(rs.getString("bodegaDestinoCodigo"));
		dto.setBodegaDestinoCodigoAlterno(rs.getString("bodegaDestinoCodigoAlterno"));
		dto.setEstadoInventarioDestinoId(rs.getString("estadoInventarioDestinoId"));
		dto.setNumeroOrdenWmsDestino(rs.getString("numeroOrdenWmsDestino"));

		dto.setLote(rs.getString("lote"));
		dto.setSerial(rs.getString("Serial"));
		dto.setCosecha(rs.getString("cosecha"));
		dto.setRequerimientoEstampillado(rs.getString("requerimientoEstampillado"));
		dto.setRequerimientoSalud(rs.getString("requerimientoSalud"));
		dto.setRequerimientoImporte(rs.getString("requerimientoImporte"));
		dto.setRequerimientoDistribuido(rs.getString("requerimientoDistribuido"));
		dto.setRequerimientoNutricional(rs.getString("requerimientoNutricional"));
		dto.setRequerimientoBl(rs.getString("requerimientoBl"));
		dto.setRequerimientoFondoCuenta(rs.getString("requerimientoFondoCuenta"));
		dto.setRequerimientoOrigen(rs.getString("requerimientoOrigen"));

		dto.setNumeroOrdenTms(rs.getString("numeroOrdenTms"));
		dto.setFechaOrdenTms(rs.getDate("fechaOrdenTms"));
		dto.setPredistribucionDestinoFinal(rs.getString("predistribucionDestinoFinal"));
		dto.setPredistribucionRotulo(rs.getString("predistribucionRotulo"));
		dto.setValorDeclaradoPorUnidad((Integer) rs.getObject("valorDeclaradoPorUnidad"));
		dto.setNotas(rs.getString("notas"));
	}

	private String getQueryOrdenesResumenPorFechaConfirmacion() {
		return "" + " SELECT  " + "     a.*  "
				+ " FROM oms.OrdenesPivotEstadoPorFechaConfirmacion (:usuarioId,:fechaConfirmacion) a " + " ORDER BY "
				+ "     a.servicio_ordinal,a.cliente_codigo,a.ciudad_bodega_ordinal,a.bodega_codigo " + "";
	}

	private String getQueryOrdenePorCriterio() {
		return "  " + " SELECT " + "     * "
				+ " FROM [oms].[OrdenesPorCriterios](:usuarioId,:fechaConfirmacion,:clienteId,:tipoServicioId,:estadoOrdenId,:bodegaOrigenId,:bodegaDestinoId) a "
				+ " ORDER BY "
				+ "     a.fecha_confirmacion DESC,a.fecha_aprobacion_planificacion DESC,a.numero_orden,numero_item "
				+ "  ";
	}

	private String getQueryOrdenesPorFechaConfirmacion() {
		return "  " + " SELECT " + "     a.id_orden AS id, " + "     a.numero_orden AS numeroOrden, "
				+ "     COALESCE(a.numero_consolidado,'') AS numeroConsolidado, "
				+ "     a.id_estado_orden AS estadoOrden, " + "     a.id_estado_distribucion AS estadoDistribucion, "
				+ "     a.id_estado_alistamiento AS estadoAlistamiento, " + "      "
				+ "     a.id_cliente AS clienteId, " + "     c.codigo AS clienteCodigo, "
				+ "     c.nombre AS clienteNombre, " + "      " + "     a.id_tipo_servicio AS tipoServicioId, "
				+ "     d.codigo AS tipoServicioCodigo, "
				+ "     COALESCE(a.tipo_servicio_codigo_alterno,'') AS tipoServicioCodigoAlterno, "
				+ "     d.nombre AS tipoServicioNombre, "
				+ "     a.requiere_servicio_distribucion AS requiereServicioDistribucion, "
				+ "     a.requiere_servicio_alistamiento AS requiereServicioAlistamiento, " + "  "
				+ "     a.id_ciudad_destino AS ciudadDestinoId, "
				+ "     COALESCE(a.destino_ciudad_nombre_alterno,'') AS ciudadDestinoNombre, "
				+ "     COALESCE(a.destino_direccion,'') AS destinoDireccion, " + "      "
				+ "     a.id_ciudad_origen AS ciudadOrigenId, "
				+ "     COALESCE(a.origen_ciudad_nombre_alterno,'') AS ciudadOrigenNombre, "
				+ "     COALESCE(a.origen_direccion,'') AS origenDireccion, " + "      "
				+ "     a.requiere_confirmacion_cita_entrega AS requiereConfirmacionCitaEntrega, "
				+ "     a.fecha_entrega_sugerida_minima AS fechaEntregaMinima, "
				+ "     a.fecha_entrega_sugerida_maxima AS fechaEntregaMaxima, "
				+ "     a.hora_entrega_sugerida_minima AS horaEntregaMinima, "
				+ "     a.hora_entrega_sugerida_maxima AS horaEntregaMaxima, "
				+ "     a.hora_entrega_sugerida_minima_adicional AS horaEntregaMinimaAdicional, "
				+ "     a.hora_entrega_sugerida_maxima_adicional AS horaEntregaMaximaAdicional, " + "  "
				+ "     a.id_canal AS canalId, " + "     COALESCE(d.codigo,'') AS canalCodigo, "
				+ "     COALESCE(a.canal_codigo_alterno,'') AS canalCodigoAlterno, "
				+ "     COALESCE(e.nombre,'') AS canalNombre, " + "     a.id_destinatario AS destinatarioId, "
				+ "     COALESCE(a.destinatario_numero_identificacion,'') AS destinatarioIdentificacion, "
				+ "     COALESCE(a.destinatario_nombre,'') AS destinatarioNombre, "
				+ "     COALESCE(a.destinatario_contacto_nombres,'') AS destinatarioContactoNombre, "
				+ "     COALESCE(a.destinatario_contacto_email,'') AS destinatarioContactoEmail, "
				+ "     COALESCE(a.destinatario_contacto_telefonos,'') AS destinatarioContactoTelefono, " + "      "
				+ "     a.id_destino AS destinoId, " + "     COALESCE(a.destino_nombre,'') AS destinoNombre, "
				+ "     COALESCE(a.destino_contacto_nombres,'') AS destinoContactoNombre, "
				+ "     COALESCE(a.destino_contacto_email,'') AS destinoContactoEmail, "
				+ "     COALESCE(a.destino_contacto_telefonos,'') AS destinoContactoTelefono, " + "  "
				+ "     a.id_origen AS origenId, " + "     COALESCE(a.origen_nombre,'') AS origenNombre, "
				+ "     COALESCE(a.origen_contacto_nombres,'') AS origenContactoNombre, "
				+ "     COALESCE(a.origen_contacto_email,'') AS origenContactoEmail, "
				+ "     COALESCE(a.origen_contacto_telefonos,'') AS origenContactoTelefono, " + "      "
				+ "     COALESCE(a.notas_requerimientos_distribucion,'') AS notasRequerimientosDistribucion, "
				+ "     COALESCE(a.notas_requerimientos_alistamiento,'') AS notasRequerimientosAlistamiento, "
				+ "     COALESCE(a.valor_recaudo,0) AS valorRecaudo, " + "      "
				+ "     a.notas_confirmacion AS notasConfirmacion, "
				+ "     a.fecha_confirmacion AS fechaConfirmacion, "
				+ "     a.usuario_confirmacion AS usuarioConfirmacion, " + "  "
				+ "     a.fecha_alistamiento_planificada AS fechaAlistamientoPlanificada, "
				+ "     a.hora_alistamiento_planificada_minima AS horaAlistamientoPlanificadaMinima, "
				+ "     a.hora_alistamiento_planificada_maxima AS horaAlistamientoPlanificadaMaxima, " + "      "
				+ "     a.id_tipo_vehiculo_planificado AS tipoVehiculoPlanificadoId, "
				+ "     COALESCE(f.codigo,'') AS tipoVehiculoPlanificadoCodigo, "
				+ "     COALESCE(f.nombre,'') AS tipoVehiculoPlanificadoNombre, "
				+ "     COALESCE(a.valor_flete_planificado,0) AS valorFletePlanificado, " + "      "
				+ "     COALESCE(a.notas_aprobacion_planificacion,'') AS notasAprobacionPlanificacion, "
				+ "     a.fecha_aprobacion_planificacion AS fechaAprobacionPlanificacion, "
				+ "     a.usuario_aprobacion_planificacion AS usuarioAprobacionPlanificacion, " + "      "
				+ "     a.id_ruta AS rutaId, " + "     a.fecha_asignacion_ruta AS fechaAsignacionRuta, "
				+ "     a.usuario_asignacion_ruta AS usuarioAsignacionRuta, " + "  "
				+ "     a.fecha_creacion AS fechaCreacion, " + "     a.usuario_creacion AS usuarioCreacion, "
				+ "     a.fecha_actualizacion AS fechaActualizacion, "
				+ "     a.usuario_actualizacion AS usuarioActualizacion, " + "  "
				+ "     COALESCE(a.notas_anulacion,'') AS notasAnulacion, "
				+ "     a.id_causal_anulacion AS causalAnulacionId, "
				+ "     COALESCE(z.codigo,'') AS causalAnulacionCodigo, "
				+ "     COALESCE(z.nombre,'') AS causalAnulacionNombre, " + "     a.fecha_anulacion AS fechaAnulacion, "
				+ "     a.usuario_anulacion AS usuarioAnulacion " + " FROM oms.ordenes a "
				+ " INNER JOIN seguridad.usuarios_clientes b ON " + "     b.id_cliente = a.id_cliente "
				+ " AND b.id_usuario = :usuarioId " + " INNER JOIN crm.clientes c ON "
				+ "     c.id_cliente = a.id_cliente " + " INNER JOIN crm.tipos_servicios d ON "
				+ "     d.id_tipo_servicio = a.id_tipo_servicio " + " LEFT OUTER JOIN crm.segmentos e ON "
				+ "     e.id_segmento = a.id_canal " + " LEFT OUTER JOIN tms.tipos_vehiculo f ON "
				+ "     f.id_tipo_vehiculo = a.id_tipo_vehiculo_planificado " + "  "
				+ " LEFT OUTER JOIN oms.causales_anulacion_orden z ON "
				+ "     z.id_causal_anulacion_orden = a.id_causal_anulacion " + " WHERE "
				+ "     a.fecha_confirmacion >= :fechaDesde " + "  ";
	}

	private String getQueryOrdenesPorid() {
		return "  " + " ;WITH " + " cte_00 AS " + " ( " + " SELECT a.id FROM  " + " (VALUES  " + " {$values} "
				+ " (-1)) AS a(id)  " + "  WHERE a.id >=0  " + " ) " + " SELECT " + "     a.id_orden AS id, "
				+ "     a.numero_orden AS numeroOrden, "
				+ "     COALESCE(a.numero_consolidado,'') AS numeroConsolidado, "
				+ "     a.id_estado_orden AS estadoOrden, " + "     a.id_estado_distribucion AS estadoDistribucion, "
				+ "     a.id_estado_alistamiento AS estadoAlistamiento, " + "      "
				+ "     a.id_cliente AS clienteId, " + "     c.codigo AS clienteCodigo, "
				+ "     c.nombre AS clienteNombre, " + "      " + "     a.id_tipo_servicio AS tipoServicioId, "
				+ "     d.codigo AS tipoServicioCodigo, "
				+ "     COALESCE(a.tipo_servicio_codigo_alterno,'') AS tipoServicioCodigoAlterno, "
				+ "     d.nombre AS tipoServicioNombre, "
				+ "     a.requiere_servicio_distribucion AS requiereServicioDistribucion, "
				+ "     a.requiere_servicio_alistamiento AS requiereServicioAlistamiento, " + "  "
				+ "     a.id_ciudad_destino AS ciudadDestinoId, "
				+ "     COALESCE(a.destino_ciudad_nombre_alterno,'') AS ciudadDestinoNombre, "
				+ "     COALESCE(a.destino_direccion,'') AS destinoDireccion, " + "      "
				+ "     a.id_ciudad_origen AS ciudadOrigenId, "
				+ "     COALESCE(a.origen_ciudad_nombre_alterno,'') AS ciudadOrigenNombre, "
				+ "     COALESCE(a.origen_direccion,'') AS origenDireccion, " + "      "
				+ "     a.requiere_confirmacion_cita_entrega AS requiereConfirmacionCitaEntrega, "
				+ "     a.fecha_entrega_sugerida_minima AS fechaEntregaMinima, "
				+ "     a.fecha_entrega_sugerida_maxima AS fechaEntregaMaxima, "
				+ "     a.hora_entrega_sugerida_minima AS horaEntregaMinima, "
				+ "     a.hora_entrega_sugerida_maxima AS horaEntregaMaxima, "
				+ "     a.hora_entrega_sugerida_minima_adicional AS horaEntregaMinimaAdicional, "
				+ "     a.hora_entrega_sugerida_maxima_adicional AS horaEntregaMaximaAdicional, " + "  "
				+ "     a.id_canal AS canalId, " + "     COALESCE(d.codigo,'') AS canalCodigo, "
				+ "     COALESCE(a.canal_codigo_alterno,'') AS canalCodigoAlterno, "
				+ "     COALESCE(e.nombre,'') AS canalNombre, " + "     a.id_destinatario AS destinatarioId, "
				+ "     COALESCE(a.destinatario_numero_identificacion,'') AS destinatarioIdentificacion, "
				+ "     COALESCE(a.destinatario_nombre,'') AS destinatarioNombre, "
				+ "     COALESCE(a.destinatario_contacto_nombres,'') AS destinatarioContactoNombre, "
				+ "     COALESCE(a.destinatario_contacto_email,'') AS destinatarioContactoEmail, "
				+ "     COALESCE(a.destinatario_contacto_telefonos,'') AS destinatarioContactoTelefono, " + "      "
				+ "     a.id_destino AS destinoId, " + "     COALESCE(a.destino_nombre,'') AS destinoNombre, "
				+ "     COALESCE(a.destino_contacto_nombres,'') AS destinoContactoNombre, "
				+ "     COALESCE(a.destino_contacto_email,'') AS destinoContactoEmail, "
				+ "     COALESCE(a.destino_contacto_telefonos,'') AS destinoContactoTelefono, " + "  "
				+ "     a.id_origen AS origenId, " + "     COALESCE(a.origen_nombre,'') AS origenNombre, "
				+ "     COALESCE(a.origen_contacto_nombres,'') AS origenContactoNombre, "
				+ "     COALESCE(a.origen_contacto_email,'') AS origenContactoEmail, "
				+ "     COALESCE(a.origen_contacto_telefonos,'') AS origenContactoTelefono, " + "      "
				+ "     COALESCE(a.notas_requerimientos_distribucion,'') AS notasRequerimientosDistribucion, "
				+ "     COALESCE(a.notas_requerimientos_alistamiento,'') AS notasRequerimientosAlistamiento, "
				+ "     COALESCE(a.valor_recaudo,0) AS valorRecaudo, " + "      "
				+ "     a.notas_confirmacion AS notasConfirmacion, "
				+ "     a.fecha_confirmacion AS fechaConfirmacion, "
				+ "     a.usuario_confirmacion AS usuarioConfirmacion, " + "  "
				+ "     a.fecha_alistamiento_planificada AS fechaAlistamientoPlanificada, "
				+ "     a.hora_alistamiento_planificada_minima AS horaAlistamientoPlanificadaMinima, "
				+ "     a.hora_alistamiento_planificada_maxima AS horaAlistamientoPlanificadaMaxima, " + "      "
				+ "     a.id_tipo_vehiculo_planificado AS tipoVehiculoPlanificadoId, "
				+ "     COALESCE(f.codigo,'') AS tipoVehiculoPlanificadoCodigo, "
				+ "     COALESCE(f.nombre,'') AS tipoVehiculoPlanificadoNombre, "
				+ "     COALESCE(a.valor_flete_planificado,0) AS valorFletePlanificado, " + "      "
				+ "     COALESCE(a.notas_aprobacion_planificacion,'') AS notasAprobacionPlanificacion, "
				+ "     a.fecha_aprobacion_planificacion AS fechaAprobacionPlanificacion, "
				+ "     a.usuario_aprobacion_planificacion AS usuarioAprobacionPlanificacion, " + "      "
				+ "     a.id_ruta AS rutaId, " + "     a.fecha_asignacion_ruta AS fechaAsignacionRuta, "
				+ "     a.usuario_asignacion_ruta AS usuarioAsignacionRuta, " + "  "
				+ "     a.fecha_creacion AS fechaCreacion, " + "     a.usuario_creacion AS usuarioCreacion, "
				+ "     a.fecha_actualizacion AS fechaActualizacion, "
				+ "     a.usuario_actualizacion AS usuarioActualizacion, " + "  "
				+ "     COALESCE(a.notas_anulacion,'') AS notasAnulacion, "
				+ "     a.id_causal_anulacion AS causalAnulacionId, "
				+ "     COALESCE(z.codigo,'') AS causalAnulacionCodigo, "
				+ "     COALESCE(z.nombre,'') AS causalAnulacionNombre, " + "     a.fecha_anulacion AS fechaAnulacion, "
				+ "     a.usuario_anulacion AS usuarioAnulacion, " + "  " + "     aa.numero_item AS numeroItem, "
				+ "     aa.descripcion AS descripcion, " + "     aa.cantidad_solicitada AS cantidadSolicitada, "
				+ "     aa.cantidad_planificada AS cantidadPlanificada, "
				+ "     aa.cantidad_alistada AS cantidadAlistada, "
				+ "     aa.cantidad_entregada AS cantidadEntregada, "
				+ "     aa.cantidad_no_entregada AS cantidadNoEntregada, "
				+ "     aa.cantidad_no_entregada_legalizada AS cantidadNoEntregadaLegalizada, "
				+ "     aa.cantidad_sobrante AS cantidadSobrante, "
				+ "     aa.cantidad_sobrante_legalizada AS cantidadSobranteLegalizada, "
				+ "     aa.id_producto AS productoId, " + "     aa.producto_codigo AS productoCodigo, "
				+ "     aa.producto_codigo_alterno AS productoCodigoAlterno, " + "      "
				+ "     aa.id_unidad AS unidadId, " + "     aa.unidad_codigo AS unidadCodigo, "
				+ "     aa.unidad_codigo_alterno AS unidadCodigoAlterno, " + "  "
				+ "     aa.largo_por_unidad AS largoPorUnidad, " + "     aa.ancho_por_unidad AS anchoPorUnidad, "
				+ "     aa.alto_por_unidad AS altoPorUnidad, " + "     aa.peso_bruto_por_unidad AS pesoBrutoPorUnidad, "
				+ "  " + "     aa.id_bodega_origen AS bodegaOrigenId, "
				+ "     aa.bodega_origen_codigo AS bodegaOrigenCodigo, "
				+ "     aa.bodega_origen_codigo_alterno AS bodegaOrigenCodigoAlterno, "
				+ "     aa.id_estado_inventario_origen AS estadoInventarioOrigenId, "
				+ "     aa.numero_orden_wms_origen AS numeroOrdenWmsOrigen, " + "  "
				+ "     aa.id_bodega_destino AS bodegaDestinoId, "
				+ "     aa.bodega_destino_codigo AS bodegaDestinoCodigo, "
				+ "     aa.bodega_destino_codigo_alterno AS bodegaDestinoCodigoAlterno, "
				+ "     aa.id_estado_inventario_destino AS estadoInventarioDestinoId, "
				+ "     aa.numero_orden_wms_destino AS numeroOrdenWmsDestino, " + "  " + "     aa.lote AS lote, "
				+ "     aa.serial AS serial, " + "     aa.cosecha AS cosecha, "
				+ "     aa.requerimiento_estampillado AS requerimientoEstampillado, "
				+ "     aa.requerimiento_salud AS requerimientoSalud, "
				+ "     aa.requerimiento_importe AS requerimientoImporte, "
				+ "     aa.requerimiento_distribuido AS requerimientoDistribuido, "
				+ "     aa.requerimiento_nutricional AS requerimientoNutricional, "
				+ "     aa.requerimiento_bl AS requerimientoBl, "
				+ "     aa.requerimiento_fondo_cuenta AS requerimientoFondoCuenta, "
				+ "     aa.requerimiento_origen AS requerimientoOrigen, " + "      "
				+ "     aa.numero_orden_tms AS numeroOrdenTms, " + "     aa.fecha_orden_tms AS fechaOrdenTms, "
				+ "      " + "     aa.predistribucion_destino_final AS predistribucionDestinoFinal, "
				+ "     aa.predistribucion_rotulo AS predistribucionRotulo, "
				+ "     aa.valor_declarado_por_unidad AS valorDeclaradoPorUnidad, " + "     aa.notas AS notas "
				+ " FROM oms.ordenes a " + " INNER JOIN seguridad.usuarios_clientes b ON "
				+ "     b.id_cliente = a.id_cliente " + " AND b.id_usuario = :usuarioId "
				+ " INNER JOIN crm.clientes c ON " + "     c.id_cliente = a.id_cliente "
				+ " INNER JOIN crm.tipos_servicios d ON " + "     d.id_tipo_servicio = a.id_tipo_servicio "
				+ " LEFT OUTER JOIN crm.segmentos e ON " + "     e.id_segmento = a.id_canal "
				+ " LEFT OUTER JOIN tms.tipos_vehiculo f ON "
				+ "     f.id_tipo_vehiculo = a.id_tipo_vehiculo_planificado " + "  "
				+ " INNER JOIN oms.lineas_orden aa ON " + "     aa.id_orden = a.id_orden "
				+ " LEFT OUTER JOIN oms.causales_anulacion_orden z ON "
				+ "     z.id_causal_anulacion_orden = a.id_causal_anulacion " + " INNER JOIN cte_00 za ON "
				+ "     za.id = a.id_orden " + "  ";
	}

	// ---------------------------------------------------------------------------------------------------------

	// ---------------------------------------------------------------------------------------------------------

	// ---------------------------------------------------------------------------------------------------------
	private Pageable createPageRequest(int start, int length) {
		start = (start < 0) ? 0 : start;
		length = (length < 0) ? 10 : length;
		return new PageRequest(start, length);
	}

	public List<Map<String, Object>> findRequerimientosDistribucionPorClientePorTipoServicioDestinatarioRemitente(
			Integer clienteId, Integer tipoServicioId, Integer destinatarioRemitenteId) throws DataAccessException {
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

	public List<Map<String, Object>> findRequerimientosAlistamientoPorClientePorTipoServicioDestinatarioRemitente(
			Integer clienteId, Integer tipoServicioId, Integer destinatarioRemitenteId) throws DataAccessException {
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

	public List<ItemGenerico<Integer>> findCausalesSolicitudRevisionCliente() throws DataAccessException {
		List<CausalSolicitudRevisionCliente> entityList = causalSolicitudRevisionClienteRepository
				.findAll(new Sort("ordinal"));

		List<ItemGenerico<Integer>> list = new ArrayList<>();
		entityList.forEach(a -> {
			list.add(new ItemGenerico<Integer>(a.getId(), a.getCodigo(), a.getDescripcion()));
		});
		return list;
	}

	public List<ItemGenerico<Integer>> findCausalesSolicitudRevisionPlaneacionLogistica() throws DataAccessException {
		List<CausalSolicitudRevisionPlaneacionLogistica> entityList = causalSolicitudRevisionPlaneacionLogisticaRepository
				.findAll(new Sort("ordinal"));

		List<ItemGenerico<Integer>> list = new ArrayList<>();
		entityList.forEach(a -> {
			list.add(new ItemGenerico<Integer>(a.getId(), a.getCodigo(), a.getDescripcion()));
		});
		return list;
	}

	public List<ItemGenerico<Integer>> findCausalesAnulacion() throws DataAccessException {
		List<OmsCausalAnulacionOrden> entityList = causalAnulacionOrdenRepository.findAll(new Sort("ordinal"));

		List<ItemGenerico<Integer>> list = new ArrayList<>();
		entityList.forEach(a -> {
			list.add(new ItemGenerico<Integer>(a.getId(), a.getCodigo(), a.getNombre()));
		});
		return list;
	}

	protected OmsOrdenDto map(OmsOrden orden) {
		ModelMapper modelMapper = new ModelMapper();

		OmsOrdenDto dto;
		dto = modelMapper.map(orden, OmsOrdenDto.class);

		if (orden.getTipoServicio() != null) {
			dto.setTipoServicioNombre(orden.getTipoServicio().getNombre());
		}

		dto.setClienteCodigo(orden.getClienteCodigo());
		if (orden.getCliente() != null) {
			dto.setClienteNombre(orden.getCliente().getNombre());
		}

		if (orden.getNumeroConsolidado() != null) {
			dto.setNumeroDocumentoConsolidadoCliente(orden.getNumeroConsolidado());
		}

		if (orden.getCiudadDestino() != null) {
			dto.setDestinoCiudadNombre(orden.getCiudadDestino().getNombreAlterno());
		}

		if (orden.getCiudadOrigen() != null) {
			dto.setDestinoCiudadNombre(orden.getCiudadOrigen().getNombreAlterno());
		}

		dto.setCanalCodigoAlterno(orden.getCanalCodigoAlterno());
		if (orden.getCanal() != null) {
			dto.setCanalCodigo(orden.getCanal().getCodigo());
			dto.setCanalNombre(orden.getCanal().getNombre());
		}

		if (orden.getDestinatario() != null) {
			dto.setDestinatarioNombre(orden.getDestinatarioNombre());
			dto.setDestinatarioNumeroIdentificacion(orden.getDestinatarioNumeroIdentificacion());
		}

		if (orden.getDestino() != null) {
			dto.setDestinoNombre(orden.getDestinoNombre());
		}

		if (orden.getOrigen() != null) {
			dto.setDestinoNombre(orden.getDestinoNombre());
		}

		return dto;
	}

}
