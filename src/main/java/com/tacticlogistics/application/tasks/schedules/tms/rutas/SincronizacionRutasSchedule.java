package com.tacticlogistics.application.tasks.schedules.tms.rutas;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tacticlogistics.domain.model.tms.EstadoRutaType;
import com.tacticlogistics.domain.model.tms.EstadoSincronizacionRutaType;

@Component
public class SincronizacionRutasSchedule {
	private static final Logger log = LoggerFactory.getLogger(SincronizacionRutasSchedule.class);

	@Value("${tms.rutas.programacion.apiUrl}")
	private String apiUrl;

	@Value("${tms.rutas.programacion.apiToken}")
	private String apiToken;

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Scheduled(fixedDelay = 1000 * 60 * 1)
	public void cron() {
		try {
			//TODO incluir emails de la orden
			List<RutaDto> request = getRutasPendientesPorSincronizar();

			if(!request.isEmpty()){
				ResultadosDto result = this.send(
						request
						.stream()
						.filter(a -> !a.getLineas().isEmpty())
						.collect(Collectors.toList()));
				
				this.procesarResultados(request, result);
				this.actualizarEstadoSincronizacion(request);
			}
		} catch (RuntimeException e) {
			log.error("", e);
		}
	}

	private List<RutaDto> getRutasPendientesPorSincronizar() {
		Map<String, Object> parameters = new HashMap<>();
		String sql = "SELECT * FROM tms.SincronizacionRutasRutasPendientes()";

		List<RutaDto> list = namedParameterJdbcTemplate.query(sql, parameters, (rs, rowNum) -> {
			RutaDto dto = new RutaDto();

			dto.setRutaId(rs.getInt("id_ruta"));
			dto.setEstadoRuta(EstadoRutaType.valueOf(rs.getString("id_estado_ruta")));
			dto.setEstadoSincronizacionRuta(EstadoSincronizacionRutaType.valueOf(rs.getString("id_estado_sincronizacion_ruta")));
			dto.setIntentosSincronizacion(rs.getInt("intentos_sincronizacion"));
			dto.setPlaca(rs.getString("placa"));
			dto.setMovil(rs.getString("movil"));
			dto.setDistribucionPrimaria(false);
			return dto;
		});

		for (RutaDto ruta : list) {
			Map<Integer, List<String>> suscriptores = new HashMap<>();
			ruta.getLineas().addAll(getOrdenesPorRuta(ruta.getRutaId(),ruta.getPlaca()));

			for (LineaRutaDto linea : ruta.getLineas()) {
				if (!suscriptores.containsKey(linea.getClienteId())) {
					suscriptores.put(linea.getClienteId(), getSuscriptoresPorRutaPorCliente(ruta.getRutaId(),
							linea.getClienteId(), "CONFIRMACION_ENTREGA"));
				}

				linea.getCorreos().setFinalizaRuta(suscriptores.get(linea.getClienteId()));
			}

			LineaRutaDto[] lineas = ruta.getLineas().toArray(new LineaRutaDto[0]);
			for (int i = 0; i < lineas.length - 1; i++) {
				lineas[i].getCorreos().setSiguienteDestino(lineas[i + 1].getCorreos().getFinalizaRuta());
			}

			ruta.getDocumentos().add(new DocumentoDto("FACTURA", 1));
		}

		return list;
	}

	private List<LineaRutaDto> getOrdenesPorRuta(int rutaId,String placa) {
		Map<String, Object> parameters = new HashMap<>();
		String sql = "SELECT * FROM tms.SincronizacionRutasOrdenesPorRuta(:rutaId)  ORDER BY secuencia_ruta";

		parameters.put("rutaId", rutaId);

		List<LineaRutaDto> list = namedParameterJdbcTemplate.query(sql, parameters, (rs, rowNum) -> {
			LineaRutaDto dto = new LineaRutaDto();

			LocalDateTime date = rs.getTimestamp("fecha_entrega_estimada").toLocalDateTime();
			LocalTime time = rs.getTime("fecha_entrega_estimada").toLocalTime();

			dto.setOrdenId(rs.getInt("id_orden"));
			dto.setClienteId(rs.getInt("id_cliente"));
			dto.setSecuencia(rs.getInt("secuencia_ruta"));
			dto.setHora(time == null ? null : time);
			dto.setRecaudo(rs.getInt("valor_recaudo"));
			dto.setDireccion(rs.getString("destino_direccion"));
			dto.setNumeroOrden(rs.getString("numero_orden"));
			dto.setDestinoNombre(dto.getNumeroOrden() + " - " + rs.getString("destinatario_nombre") + " - " + rs.getString("destinatario_numero_identificacion"));
			dto.setCxD(rs.getFloat("destino_cx"));
			dto.setCyD(rs.getFloat("destino_cy"));
			dto.setCxO("");
			dto.setCyO("");
			dto.setPlaca(placa);
			dto.setClienteNumeroIdentificacion(rs.getString("cliente_numero_identificacion"));
			dto.setFechaInicial(date == null ? null : date.toLocalDate());
			dto.setFechaFinal(dto.getFechaInicial());
			dto.setCorreos(new SuscripcionDto());

			String email; 
			email= rs.getString("destino_contacto_email");
			if(!email.isEmpty()){
				dto.getCorreos().getFinalizaRuta().add(email);
			}
			email= rs.getString("origen_contacto_email");
			if(!email.isEmpty()){
				dto.getCorreos().getFinalizaRuta().add(email);
			}

			return dto;
		});

		return list;
	}

	private List<String> getSuscriptoresPorRutaPorCliente(int rutaId, int clienteId, String notificacionCodigo) {
		Map<String, Object> parameters = new HashMap<>();
		String sql = "SELECT * FROM tms.SincronizacionRutasSuscriptores(:rutaId,:clienteId,:notificacionCodigo)";

		parameters.put("rutaId", rutaId);
		parameters.put("clienteId", clienteId);
		parameters.put("notificacionCodigo", notificacionCodigo);

		List<String> list = namedParameterJdbcTemplate.query(sql, parameters, (rs, rowNum) -> {
			return rs.getString("contacto_email");
		});

		return list;
	}

	//------------------------------------------------------------------------------------------------------
	private ResultadosDto send(List<RutaDto> request) {
		final String FATAL = "FATAL";

		ResultadosDto result = null;

		MappingJackson2HttpMessageConverter mapping = new MappingJackson2HttpMessageConverter();
		try {
			log.info(mapping.getObjectMapper().writeValueAsString(request));
		} catch (JsonProcessingException e) {
			log.warn("No fue posible convertir la lista de rutas al formato JSON:", e);
		}

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(mapping);

		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("Content-Type", "application/json");
		headers.add("token", this.apiToken);

		try {
			HttpEntity<?> requestEntity = new HttpEntity<Object>(request, headers);
			result = restTemplate.postForObject(this.apiUrl, requestEntity, ResultadosDto.class);
		} catch (RestClientException e) {
			log.error("Ocurrio el siguiente error al intentar hacer el llamado:", e);
			result = new ResultadosDto();
			result.setStatus(FATAL);
			for (RutaDto ruta : request) {
				result.getRutas().add(new ResultadoDto(ruta.getMovil(), FATAL, e.getMessage()));
			}
		}

		try {
			log.info(mapping.getObjectMapper().writeValueAsString(result));
		} catch (JsonProcessingException e) {
			log.warn("No fue posible convertir la lista de mensajes de respuesta al formato JSON:", e);
		}

		return result;

	}

	private void procesarResultados(List<RutaDto> request, ResultadosDto result) {
		for (RutaDto ruta : request) {
			boolean found = false;
			EstadoSincronizacionRutaType estadoSincronizacion = ruta.getEstadoSincronizacionRuta();
			String errorSincronizacion = "";

			for (ResultadoDto dto : result.getRutas()) {
				if(ruta.getMovil().equals(dto.getMovil())){
					found = true;
					switch (dto.getStatus()) {
					case "OK":
						estadoSincronizacion = EstadoSincronizacionRutaType.SINCRONIZADO;
						break;
					case "ERROR":
						estadoSincronizacion = EstadoSincronizacionRutaType.ERROR;
						errorSincronizacion = dto.getError();
						break;
					case "FATAL":
						estadoSincronizacion = EstadoSincronizacionRutaType.REINTENTO;
						errorSincronizacion = dto.getError();
						break;
					default:
						break;
					}
					break;
				}
			}
			
			if(!found){
				estadoSincronizacion = EstadoSincronizacionRutaType.REINTENTO;
				errorSincronizacion = "Ruta Control no incluyo esta ruta en la respuesta";
			}
			
			ruta.setEstadoSincronizacionRuta(estadoSincronizacion);
			ruta.setErrorSincronizacion(errorSincronizacion);
			
		}
	}

	private void actualizarEstadoSincronizacion(List<RutaDto> request) {
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall((JdbcTemplate) (getJdbcTemplate().getJdbcOperations()))
				.withSchemaName("tms")
				.withProcedureName("RutaActualizarEstadoSincronizacion");

		for (RutaDto ruta : request) {
			Map<String, Object> inParamMap = new HashMap<String, Object>();
			inParamMap.put("rutaId", ruta.getRutaId());
			inParamMap.put("estadoSincronizacionRuta", ruta.getEstadoSincronizacionRuta());
			inParamMap.put("errorSincronizacion", ruta.getErrorSincronizacion());

			SqlParameterSource in = new MapSqlParameterSource(inParamMap);

			simpleJdbcCall.execute(in);
		}
	}

	private NamedParameterJdbcTemplate getJdbcTemplate() {
		return this.namedParameterJdbcTemplate;
	}

}
