package com.tacticlogistics.application.task.etl.components.tactic.tms.rutas;

import java.io.File;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tacticlogistics.application.task.etl.components.ETLFlatFileStrategy;
import com.tacticlogistics.application.task.etl.components.tactic.tms.rutas.dto.LineaRutaDto;
import com.tacticlogistics.application.task.etl.components.tactic.tms.rutas.dto.RutaDto;
import com.tacticlogistics.application.task.etl.readers.ExcelWorkSheetReader;
import com.tacticlogistics.application.task.etl.readers.Reader;
import com.tacticlogistics.infrastructure.services.Basic;

@Component("TACTIC.TMS.RUTAS.TOURSOLVER")
public class ConfirmacionesDeRutas extends ETLFlatFileStrategy<RutaDto> {
	private static final Logger log = LoggerFactory.getLogger(ConfirmacionesDeRutas.class);

	// ---------------------------------------------------------------------------------------------------------------------------------------
	public static final String IDENTIFICADOR_VEHICULO = "IDENTIFICADOR";
	public static final String SECUENCIA = "ORDEN";
	public static final String IDENTIFICADOR_ENTREGA = "CLIENTE";
	public static final String HORA = "HORA";
	public static final String ENTREGA_ID = "ID_SOLICITUD";
	public static final String DESTINO_BARRIO = "BARRIO";

	// public static final String LATITUD = "LATITUD";
	// public static final String LONGITUD = "LONGITUD";
	// public static final String NUMERO_DOCUMENTO_ENTREGA = "NOMBRE";
	// public static final String DESTINATARIO_NOMBRE = "NOMBRE_DESTINATARIO";
	// public static final String DESTINO_DIRECCION = "DIRECCION";

	// public static final String DESTINO_NOMBRE = "NOMBRE_DESTINO";
	// public static final String FECHA_ENTREGA_PLANEADA =
	// "FECHA_ENTREGA_PLANEADA";
	// public static final String CLIENTE_CODIGO = "CODIGO_CLIENTE";
	//
	// public static final String IDENTIFICADOR_MOVIL = "IDENTIFICADOR_MOVIL";
	// public static final String CLIENTE_NUMERO_IDENTIFICACION =
	// "CLIENTE_NUMERO_IDENTIFICACION";
	// public static final String FECHA_HORA_ENTREGA = "FECHA_HORA_ENTREGA";

	// ---------------------------------------------------------------------------------------------------------------------------------------
	@Autowired
	private ExcelWorkSheetReader reader;

	@Value("${tms.rutas.programacion.apiUrl}")
	private String apiUrl;

	@Value("${tms.rutas.programacion.apiToken}")
	private String apiToken;

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

	public String getApiUrl() {
		return apiUrl;
	}

	public String getApiToken() {
		return apiToken;
	}

	public NamedParameterJdbcTemplate getJdbcTemplate() {
		return namedParameterJdbcTemplate;
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------
	public ConfirmacionesDeRutas() {
		super();
	}

	@Override
	protected List<String> getCamposEsperados() {
		List<String> list = new ArrayList<>();

		list.add(IDENTIFICADOR_VEHICULO);
		list.add(SECUENCIA);
		list.add(IDENTIFICADOR_ENTREGA);
		list.add(HORA);
		list.add(ENTREGA_ID);
		list.add(DESTINO_BARRIO);

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
		value = getValorCampo(IDENTIFICADOR_ENTREGA, campos, mapNameToIndex);

		if (value.isEmpty() || value.equals("ESPERA") || value.equals("FIN")) {
			return true;
		}

		return false;
	}

	@Override
	protected String generarIdentificadorRegistro(String[] campos, Map<String, Integer> mapNameToIndex) {
		StringBuffer sb = new StringBuffer();
		sb.append(getValorCampo(IDENTIFICADOR_VEHICULO, campos, mapNameToIndex));

		return sb.toString().toLowerCase();
	}

	@Override
	protected void adicionar(String key, Map<String, RutaDto> map, String[] campos, Map<String, Integer> mapNameToIndex,
			Map<Integer, String> mapIndexToName) {
		if (!map.containsKey(key)) {
			Time timeValue;
			String value;

			DateFormat format = getFormatoHoraHHmmUTC();
			value = getValorCampo(HORA, campos, mapNameToIndex);
			value = format.format(getValorCampoHoraTourSolver(key, HORA.toString(), value, format));
			timeValue = getValorCampoHora(key, HORA.toString(), value, getFormatoHoraHHmm());

			RutaDto dto = new RutaDto(key, false, timeValue);

			map.put(key, dto);
		}
	}

	@Override
	protected void modificar(String key, Map<String, RutaDto> map, String[] campos, Map<String, Integer> mapNameToIndex,
			Map<Integer, String> mapIndexToName) {

		if (map.containsKey(key)) {
			String value;
			Integer integerValue;
			value = getValorCampo(IDENTIFICADOR_ENTREGA, campos, mapNameToIndex);

			if (value.equalsIgnoreCase("INICIO")) {
				return;
			}

			LineaRutaDto dto = new LineaRutaDto();

			value = getValorCampo(SECUENCIA, campos, mapNameToIndex);
			integerValue = getValorCampoDecimal(key, SECUENCIA.toString(), value, getFormatoEntero());
			dto.setSecuencia(integerValue);

			value = getValorCampo(ENTREGA_ID, campos, mapNameToIndex);
			integerValue = getValorCampoDecimal(key, ENTREGA_ID.toString(), value, getFormatoEntero());
			dto.setOrdenId(integerValue);

			DateFormat format = getFormatoHoraHHmmUTC();
			value = getValorCampo(HORA, campos, mapNameToIndex);
			Date timeValue = getValorCampoHoraTourSolver(key, HORA.toString(), value, format);
			value = (timeValue == null) ? "" : format.format(timeValue);
			// timeValue = getValorCampoHora(key, HORA.toString(), value,
			// getFormatoHoraHHmm());
			dto.setHora(value);

			value = getValorCampo(DESTINO_BARRIO, campos, mapNameToIndex);

			dto.setBarrio(value);

			map.get(key).getLineas().add(dto);
		}
	}

	private Date getValorCampoHoraTourSolver(String key, String campo, String value, DateFormat fmt) {
		Date time = null;
		value = value.replace(",", ".");
		try {
			Float floatValue = getFormatoCoordenada().parse(value).floatValue();
			if (floatValue >= 1.0) {
				throw new RuntimeException("La hora suminitrada supera las 24 horas");
			}
			time = new Date((long) ((24L * 60L * 60L * 1000L) * floatValue));
		} catch (ParseException e) {
			logParseException(key, HORA, value, getFormatoHoraHHmm().toPattern());
		} catch (RuntimeException e) {
			logParseException(key, HORA, value, getFormatoHoraHHmm().toPattern());
		}
		return time;
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------
	@Override
	@Transactional(readOnly = false)
	protected void cargar(Map<String, RutaDto> map) {
		log.info("Begin cargar");

		Date fechaAsignacionRuta = new Date();

		Integer corteRutaId = null;
		for (RutaDto ruta : map.values()) {
			LineaRutaDto[] lineas = ruta.getLineas().toArray(new LineaRutaDto[0]);

			Integer rutaId = null;
			for (LineaRutaDto entrega : lineas) {
				if (corteRutaId == null) {
					corteRutaId = getCorteRutaIdPorOrdenId(entrega.getOrdenId());
				}

				if (rutaId == null) {
					rutaId = crearRuta(corteRutaId, ruta.getIdentificadorMovil(), ruta.getHoraCitaCargue(),
							fechaAsignacionRuta);
				}

				Time time;
				try {
					// TODO
					time = Basic.toHora(entrega.getHora(), null, getFormatoHoraHHmm());
					incluirOrdenEnRuta(entrega.getOrdenId(), rutaId, entrega.getSecuencia(), time);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		finalizarCorteRuta(corteRutaId, fechaAsignacionRuta);

		log.info("End cargar");
	}

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

	private Integer crearRuta(Integer corteRutaId, String placa, Time horaCitaCargue, Date fechaAsignacionRuta) {
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall((JdbcTemplate) (getJdbcTemplate().getJdbcOperations()))
				.withSchemaName("tms").withProcedureName("PlanificacionRutasCrearRuta");

		Map<String, Object> inParamMap = new HashMap<String, Object>();
		inParamMap.put("corteRutaId", corteRutaId);
		inParamMap.put("placa", placa);
		inParamMap.put("horaCitaCargue", horaCitaCargue);
		inParamMap.put("fechaAsignacionRuta", fechaAsignacionRuta);

		SqlParameterSource in = new MapSqlParameterSource(inParamMap);

		Map<String, Object> result = simpleJdbcCall.execute(in);

		return (Integer) result.get("rutaId");
	}

	private void incluirOrdenEnRuta(Integer ordenId, Integer rutaId, Integer secuenciaRuta,
			Time horaEstimadaDeEntrega) {
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall((JdbcTemplate) (getJdbcTemplate().getJdbcOperations()))
				.withSchemaName("tms").withProcedureName("PlanificacionRutasIncluirOrdenEnRuta");

		Map<String, Object> inParamMap = new HashMap<String, Object>();
		inParamMap.put("ordenId", ordenId);
		inParamMap.put("rutaId", rutaId);
		inParamMap.put("secuenciaRuta", secuenciaRuta);
		inParamMap.put("horaEstimadaDeEntrega", horaEstimadaDeEntrega);

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

	//
	// public static ResultadosProgramacionRutasDto send(String apiUrl, String
	// apiToken, List<RutaDto> request)
	// throws IOException, JsonGenerationException, JsonMappingException {
	//
	// MappingJackson2HttpMessageConverter mapping = new
	// MappingJackson2HttpMessageConverter();
	// RestTemplate restTemplate = new RestTemplate();
	// restTemplate.getMessageConverters().add(new
	// MappingJackson2HttpMessageConverter());
	//
	// MultiValueMap<String, String> headers = new LinkedMultiValueMap<String,
	// String>();
	// headers.add("Content-Type", "application/json");
	// headers.add("token", apiToken);
	//
	// HttpEntity<?> requestEntity = new HttpEntity<Object>(request, headers);
	// log.info(mapping.getObjectMapper().writeValueAsString(request));
	//
	// try {
	// if (false) {
	// ResultadosProgramacionRutasDto result =
	// restTemplate.postForObject(apiUrl, requestEntity,
	// ResultadosProgramacionRutasDto.class);
	// log.info(mapping.getObjectMapper().writeValueAsString(result));
	//
	// return result;
	// } else {
	// List<ResultadoProgramacionRutaDto> resultado = new LinkedList<>();
	//
	// for (RutaDto ruta : request) {
	// resultado.add(new
	// ResultadoProgramacionRutaDto(ruta.getIdentificadorMovil(), "OK", ""));
	// }
	//
	// return new ResultadosProgramacionRutasDto("OK", resultado);
	// }
	// } catch (HttpClientErrorException hcee) {
	// log.error(hcee.getMessage());
	// throw new RuntimeException(hcee);
	// } catch (Exception e) {
	// log.error(e.getMessage());
	// throw new RuntimeException(e);
	// }
	// }
	//
	// protected void generarLogResultados(List<RutaDto> rutas,
	// ResultadosProgramacionRutasDto result) {
	// Map<String, String> mapMovilVehiculo = new HashMap<>();
	// for (RutaDto rutaDto : rutas) {
	// String value = "";
	// if (rutaDto.getLineas().size() > 0) {
	// value = rutaDto.getLineas().get(0).getIdentificadorVehiculo();
	// }
	// mapMovilVehiculo.put(rutaDto.getIdentificadorMovil(), value);
	// }
	//
	// for (ResultadoProgramacionRutaDto dto : result.getRutas()) {
	// String key = mapMovilVehiculo.get(dto.getIdentificadorMovil());
	// if (dto.getStatus().equalsIgnoreCase("OK")) {
	// logInfo(key, "", dto.getStatus());
	// } else {
	// logError(key, "", dto.getError());
	// }
	// }
	// }
	private SimpleDateFormat formatoHoraHHmmUTC = null;

	protected SimpleDateFormat getFormatoHoraHHmmUTC() {
		if (formatoHoraHHmmUTC == null) {
			formatoHoraHHmmUTC = new SimpleDateFormat("HH:mm");
			formatoHoraHHmmUTC.setTimeZone(TimeZone.getTimeZone("UTC"));
		}
		return formatoHoraHHmmUTC;
	}
}