package com.tacticlogistics.application.tasks.etl.components.tactic.tms.rutas.planificacion;

import java.io.File;
import java.text.ParseException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
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

import com.tacticlogistics.application.tasks.etl.components.ETLFlatFileStrategy;
import com.tacticlogistics.application.tasks.etl.readers.ExcelWorkSheetReader;
import com.tacticlogistics.application.tasks.etl.readers.Reader;
import com.tacticlogistics.infrastructure.services.Basic;

@Component("TACTIC.TMS.RUTAS.TOURSOLVER")
public class PlanificacionDeRutas extends ETLFlatFileStrategy<RutaDto> {
	private static final Logger log = LoggerFactory.getLogger(PlanificacionDeRutas.class);

	// ---------------------------------------------------------------------------------------------------------------------------------------
	public static final String PLACA = "IDENTIFICADOR";
	public static final String SECUENCIA = "ORDEN";
	public static final String NUMERO_ORDEN = "CLIENTE";
	public static final String HORA = "HORA";
	public static final String ID_ORDEN = "ID_SOLICITUD";
	public static final String BARRIO = "BARRIO";

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
	public PlanificacionDeRutas() {
		super();
	}

	@Override
	protected List<String> getCamposEsperados() {
		List<String> list = new ArrayList<>();

		list.add(PLACA);
		list.add(ID_ORDEN);
		list.add(NUMERO_ORDEN);
		list.add(SECUENCIA);
		list.add(HORA);

		return list;
	}

	@Override
	protected void preProcesarDirectorio() {
		super.preProcesarDirectorio();
		((ExcelWorkSheetReader) getReader()).setWorkSheetName("Informe");
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
		value = getValorCampo(NUMERO_ORDEN, campos, mapNameToIndex);

		if (value.isEmpty() || value.equals("ESPERA") || value.equals("FIN")) {
			return true;
		}

		return false;
	}

	@Override
	protected String generarIdentificadorRegistro(String[] campos, Map<String, Integer> mapNameToIndex) {
		StringBuffer sb = new StringBuffer();
		sb.append(getValorCampo(PLACA, campos, mapNameToIndex));

		return sb.toString().toLowerCase();
	}

	@Override
	protected void adicionar(String key, Map<String, RutaDto> map, String[] campos, Map<String, Integer> mapNameToIndex,
			Map<Integer, String> mapIndexToName) {
		if (!map.containsKey(key)) {
			LocalTime timeValue;
			String value;

			value = getValorCampo(HORA, campos, mapNameToIndex);
			timeValue = getValorCampoHoraTourSolver(key, HORA.toString(), value);

			RutaDto dto = new RutaDto(key, timeValue);

			map.put(key, dto);
		}
	}

	@Override
	protected void modificar(String key, Map<String, RutaDto> map, String[] campos, Map<String, Integer> mapNameToIndex,
			Map<Integer, String> mapIndexToName) {

		if (map.containsKey(key)) {
			String value;
			Integer integerValue;
			LocalTime timeValue;

			value = getValorCampo(NUMERO_ORDEN, campos, mapNameToIndex);
			if (value.equalsIgnoreCase("INICIO")) {
				return;
			}

			LineaRutaDto dto = new LineaRutaDto();

			value = getValorCampo(SECUENCIA, campos, mapNameToIndex);
			integerValue = getValorCampoDecimal(key, SECUENCIA.toString(), value, getFormatoEntero());
			dto.setSecuencia(integerValue);

			value = getValorCampo(ID_ORDEN, campos, mapNameToIndex);
			integerValue = getValorCampoDecimal(key, ID_ORDEN.toString(), value, getFormatoEntero());
			dto.setOrdenId(integerValue);

			value = getValorCampo(HORA, campos, mapNameToIndex);
			timeValue = getValorCampoHoraTourSolver(key, HORA.toString(), value);
			dto.setHoraEstimada(timeValue);

			map.get(key).getLineas().add(dto);
		}
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------
	// TODO SI EN ESTE PUNTO SALE ESTE ERROR QUIERE DECIR QUE
	// HUBO UN CAMBIO EN LOS DATOS DE LA ORDEN
	// TODO TAMBIEN ES FACTIBLE QUE ENVIEN EL MISMO ARCHIVO, NO
	// SE DEBE PERMITIR VOLVER A MODIFICAR UN CORTE SI YA ESTA
	// FINALIZADO
	@Override
	@Transactional(readOnly = false)
	protected void cargar(Map<String, RutaDto> map) {
		log.info("Begin cargar");

		Integer corteRutaId = null;
		Date fechaAsignacionRuta = new Date();

		for (RutaDto ruta : map.values()) {

			Integer rutaId = null;
			for (LineaRutaDto entrega : ruta.getLineas()) {
				if (corteRutaId == null) {
					corteRutaId = getCorteRutaIdPorOrdenId(entrega.getOrdenId());
				}
				if (corteRutaId == null) {
					continue;
				}

				if (rutaId == null) {
					rutaId = crearRuta(corteRutaId, ruta.getPlaca(), ruta.getHoraCitaCargue(), fechaAsignacionRuta);
				}

				incluirOrdenEnRuta(entrega.getOrdenId(), rutaId, entrega.getSecuencia(), entrega.getHoraEstimada());
			}
		}
		finalizarCorteRuta(corteRutaId, fechaAsignacionRuta);

		log.info("End cargar");
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------
	private Integer getCorteRutaIdPorOrdenId(int ordenId) {
		Map<String, Object> parameters = new HashMap<>();
		String sql = getQueryCorteRutaIdPorOrdenId();

		parameters.put("ordenId", ordenId);

		List<Integer> list = namedParameterJdbcTemplate.query(sql, parameters, (rs, rowNum) -> {
			return (Integer) rs.getObject("id_corte_ruta");
		});

		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	private String getQueryCorteRutaIdPorOrdenId() {
		return "SELECT * FROM tms.CorteRutaIdPorOrdenId(:ordenId)";
	}

	// TODO CAMBIAR TABLA DE BD TACTIC A TMS.VEHICULOS
	private Integer crearRuta(Integer corteRutaId, String placa, LocalTime localTime, Date fechaAsignacionRuta) {
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall((JdbcTemplate) (getJdbcTemplate().getJdbcOperations()))
				.withSchemaName("tms").withProcedureName("PlanificacionRutasCrearRuta");
		Map<String, Object> inParamMap = new HashMap<String, Object>();
		inParamMap.put("corteRutaId", corteRutaId);
		inParamMap.put("placa", placa);
		inParamMap.put("horaCitaCargue", localTime);
		inParamMap.put("fechaAsignacionRuta", fechaAsignacionRuta);
		inParamMap.put("rutaId", null);

		SqlParameterSource in = new MapSqlParameterSource(inParamMap);

		Map<String, Object> result = simpleJdbcCall.execute(in);

		return (Integer) result.get("rutaId");
	}

	private void incluirOrdenEnRuta(Integer ordenId, Integer rutaId, Integer secuenciaRuta,
			LocalTime localTime) {
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall((JdbcTemplate) (getJdbcTemplate().getJdbcOperations()))
				.withSchemaName("tms").withProcedureName("PlanificacionRutasIncluirOrdenEnRuta");

		Map<String, Object> inParamMap = new HashMap<String, Object>();
		inParamMap.put("ordenId", ordenId);
		inParamMap.put("rutaId", rutaId);
		inParamMap.put("secuenciaRuta", secuenciaRuta);
		inParamMap.put("horaEstimadaDeEntrega", localTime);

		SqlParameterSource in = new MapSqlParameterSource(inParamMap);

		simpleJdbcCall.execute(in);
	}

	private void finalizarCorteRuta(Integer corteRutaId, Date fechaCorteFin) {
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall((JdbcTemplate) (getJdbcTemplate().getJdbcOperations()))
				.withSchemaName("tms").withProcedureName("PlanificacionRutasFinalizarCorte");

		Map<String, Object> inParamMap = new HashMap<String, Object>();
		inParamMap.put("corteRutaId", corteRutaId);
		inParamMap.put("fechaCorteFin", fechaCorteFin);

		SqlParameterSource in = new MapSqlParameterSource(inParamMap);

		simpleJdbcCall.execute(in);
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------
	private LocalTime getValorCampoHoraTourSolver(String key, String campo, String value) {
		LocalTime time = null;
		value = value.replace(",", ".");
		try {
			Float floatValue = getFormatoCoordenada().parse(value).floatValue();
			if (floatValue >= 1.0) {
				throw new RuntimeException("La hora suminitrada supera las 24 horas");
			}
			time = LocalTime.ofSecondOfDay((long) ((24L * 60L * 60L * 1L) * floatValue));
		} catch (ParseException e) {
			logParseException(key, HORA, value, "");
		} catch (RuntimeException e) {
			logParseException(key, HORA, value, "");
		}
		return time;
	}
}