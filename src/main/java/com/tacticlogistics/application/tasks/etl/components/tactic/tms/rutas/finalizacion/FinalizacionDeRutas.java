package com.tacticlogistics.application.tasks.etl.components.tactic.tms.rutas.finalizacion;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tacticlogistics.application.dto.common.MensajesDTO;
import com.tacticlogistics.application.tasks.etl.components.ETLFlatFileStrategy;
import com.tacticlogistics.application.tasks.etl.readers.ExcelWorkSheetReader;
import com.tacticlogistics.application.tasks.etl.readers.Reader;
import com.tacticlogistics.domain.model.oms.EstadoDistribucionType;
import com.tacticlogistics.infrastructure.services.Basic;

@Component("TACTIC.TMS.RUTAS.RUTACONTROL")
public class FinalizacionDeRutas extends ETLFlatFileStrategy<EntregaDto> {
	private static final Logger log = LoggerFactory.getLogger(FinalizacionDeRutas.class);

	// ---------------------------------------------------------------------------------------------------------------------------------------
	public static final String MOVIL = "MOVIL";
	public static final String PLACA = "NOMBRE TÉCNICO";
	public static final String FECHA_RUTA = "FECHA";
	public static final String NUMERO_ORDEN = "CODIGO DEL CLIENTE";
	public static final String CLIENTE_NUMERO_IDENTIFICACION = "DESCRIPCION DEL SERVICIO";
	public static final String ESTADO_DISTRIBUCION = "ESTADO FINAL DEL SERVICIO";
	public static final String FECHA_ENTREGA_INICIO = "HORA DE INICIO";
	public static final String FECHA_ENTREGA_FIN = "HORA FINAL";

	// ---------------------------------------------------------------------------------------------------------------------------------------
	@Autowired
	private ExcelWorkSheetReader reader;

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	// ---------------------------------------------------------------------------------------------------------------------------------------
	@Override
	public Pattern getPattern() {
		return PATTERN_XLS;
	}

	@Override
	protected Reader<File, String> getReader() {
		return reader;
	}

	public NamedParameterJdbcTemplate getJdbcTemplate() {
		return namedParameterJdbcTemplate;
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------
	public FinalizacionDeRutas() {
		super();
	}

	@Override
	protected List<String> getCamposEsperados() {
		List<String> list = new ArrayList<>();

		list.add(MOVIL);
		list.add(PLACA);
		list.add(FECHA_RUTA);
		list.add(NUMERO_ORDEN);
		list.add(IGNORAR);
		list.add(CLIENTE_NUMERO_IDENTIFICACION);
		list.add(ESTADO_DISTRIBUCION);
		list.add(FECHA_ENTREGA_INICIO);
		list.add(FECHA_ENTREGA_FIN);
		list.add(IGNORAR);

		return list;
	}

	@Override
	protected boolean generarEncabezadoConLosNombresDeLosCamposEsperados() {
		return true;
	}

	@Override
	protected void preProcesarDirectorio() {
		super.preProcesarDirectorio();
		((ExcelWorkSheetReader) getReader()).setWorkSheetName("Resumen_Diario");
	}

	@Override
	protected String limpiar(String texto) {
		return Basic.limpiarCaracterEspecialDeEspacioDeExcel(super.limpiar(texto));
	}

	@Override
	protected boolean ignorarRegistroDespuesDeSerSeparadoPorCampos(String[] campos,
			Map<String, Integer> mapNameToIndex) {
		if (super.ignorarRegistroDespuesDeSerSeparadoPorCampos(campos, mapNameToIndex)) {
			return true;
		}
		String value;
		value = getValorCampo(MOVIL, campos, mapNameToIndex);

		if (value.isEmpty() || !value.startsWith("TACTIC")) {
			return true;
		}

		return false;
	}

	@Override
	protected String generarIdentificadorRegistro(String[] campos, Map<String, Integer> mapNameToIndex) {
		StringBuffer sb = new StringBuffer();

		sb.append(getValorCampo(CLIENTE_NUMERO_IDENTIFICACION, campos, mapNameToIndex));
		sb.append("-");
		sb.append(getValorCampo(NUMERO_ORDEN, campos, mapNameToIndex));
		sb.append("-");
		sb.append(getValorCampo(FECHA_RUTA, campos, mapNameToIndex));
		sb.append("-");
		sb.append(getValorCampo(MOVIL, campos, mapNameToIndex));
		sb.append("-");
		sb.append(getValorCampo(PLACA, campos, mapNameToIndex));

		return sb.toString().toLowerCase();
	}

	@Override
	protected void adicionar(String key, Map<String, EntregaDto> map, String[] campos,
			Map<String, Integer> mapNameToIndex, Map<Integer, String> mapIndexToName, MensajesDTO<?> mensajes) {
		if (!map.containsKey(key)) {
			String value;
			LocalDate dateValue;
			LocalDateTime dateTimeValue;

			EntregaDto dto = new EntregaDto();

			value = getValorCampo(CLIENTE_NUMERO_IDENTIFICACION, campos, mapNameToIndex);
			dto.setClienteNumeroIdentificacion(value);

			value = getValorCampo(NUMERO_ORDEN, campos, mapNameToIndex);
			dto.setNumeroOrden(value);

			value = getValorCampo(FECHA_RUTA, campos, mapNameToIndex);
			dateValue = getValorCampoFecha(mensajes,key, FECHA_RUTA, value, getFormatoFechaCorta());
			dto.setFechaRuta(dateValue);

			value = getValorCampo(MOVIL, campos, mapNameToIndex);
			dto.setMovil(value);

			value = getValorCampo(PLACA, campos, mapNameToIndex);
			dto.setPlaca(value);

			EstadoDistribucionType estado;
			value = getValorCampo(ESTADO_DISTRIBUCION, campos, mapNameToIndex);
			estado = decodeEstadoRutaControl(value);
			dto.setEstadoDistribucion(estado);

			value = getValorCampo(FECHA_ENTREGA_INICIO, campos, mapNameToIndex);
			value = Basic.substringSafe(value, 0, 19);
			if (value.equals("NULL")) {
				dateTimeValue = null;
			} else {
				dateTimeValue = getValorCampoFechaHora(mensajes,key, FECHA_ENTREGA_INICIO, value, getFormatoFechaLarga());
			}
			dto.setFechaEntregaInicio(dateTimeValue);

			value = getValorCampo(FECHA_ENTREGA_FIN, campos, mapNameToIndex);
			value = Basic.substringSafe(value, 0, 19);
			if (value.equals("NULL")) {
				dateTimeValue = null;
			} else {
				dateTimeValue = getValorCampoFechaHora(mensajes,key, FECHA_ENTREGA_FIN, value, getFormatoFechaLarga());
			}
			dto.setFechaEntregaFin(dateTimeValue);

			map.put(key, dto);
		}
	}

	@Override
	protected void modificar(String key, Map<String, EntregaDto> map, String[] campos,
			Map<String, Integer> mapNameToIndex, Map<Integer, String> mapIndexToName, MensajesDTO<?> mensajes) {
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------
	@Override
	@Transactional(readOnly = false)
	protected void cargar(Map<String, EntregaDto> map, MensajesDTO<?> mensajes) {
		log.info("Begin cargar");

		for (EntregaDto dto : map.values()) {
			if (dto.getEstadoDistribucion() == null) {
				continue;
			}

			actualizarEstadoDistribucion(dto);
		}
		
		actualizarEstadoRuta();

		log.info("End cargar");
	}

	private void actualizarEstadoDistribucion(EntregaDto dto) {
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall((JdbcTemplate) (getJdbcTemplate().getJdbcOperations()))
				.withSchemaName("tms").withProcedureName("NotificacionesRutasCambioEstadoDistribucion");

		System.out.println(dto);

		Map<String, Object> inParamMap = new HashMap<String, Object>();
		inParamMap.put("clienteNumeroIdentificacion", dto.getClienteNumeroIdentificacion());
		inParamMap.put("numeroOrden", dto.getNumeroOrden());
		inParamMap.put("fechaRuta", dto.getFechaRuta());
		inParamMap.put("movil", dto.getMovil());
		inParamMap.put("placa", dto.getPlaca());
		inParamMap.put("estadoDistribucion", dto.getEstadoDistribucion());
		inParamMap.put("fechaEntregaInicio", dto.getFechaEntregaInicio());
		inParamMap.put("fechaEntregaFin", dto.getFechaEntregaFin());

		SqlParameterSource in = new MapSqlParameterSource(inParamMap);

		simpleJdbcCall.execute(in);
	}

	private void actualizarEstadoRuta() {
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall((JdbcTemplate) (getJdbcTemplate().getJdbcOperations()))
				.withSchemaName("tms").withProcedureName("NotificacionesRutasCambioEstadoRuta");

		simpleJdbcCall.execute();
	}
	
	private EstadoDistribucionType decodeEstadoRutaControl(String value) {
		switch (value) {
		case "ENTREGADO":
			return EstadoDistribucionType.ENTREGADA;
		case "REPROGRAMADO":
			return EstadoDistribucionType.NO_ENTREGADA;
		case "NOVEDAD EN ENTREGA":
			return EstadoDistribucionType.NOVEDADES;
		case "TRANSITO":
			return EstadoDistribucionType.TRANSITO;
		case "VISITA RETRASADA":
			return EstadoDistribucionType.RETRASADA;
		case "VISITA INICIADA":
			return EstadoDistribucionType.EN_DESTINO;
		default:
			return null;
		}
	}

	private DateTimeFormatter formatoFechaCorta = null; 
	@Override
	protected DateTimeFormatter getFormatoFechaCorta() {
		if (formatoFechaCorta == null) {
			formatoFechaCorta = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		}
		return formatoFechaCorta;
	}
}