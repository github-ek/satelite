package com.tacticlogistics.application.task.etl.components.tactic.tms.rutas;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tacticlogistics.application.task.etl.components.ETLFlatFileStrategy;
import com.tacticlogistics.application.task.etl.readers.ExcelWorkSheetReader;
import com.tacticlogistics.application.task.etl.readers.Reader;
import com.tacticlogistics.domain.model.oms.EstadoDistribucionType;
import com.tacticlogistics.infrastructure.services.Basic;

@Component("TACTIC.TMS.RUTAS.RUTACONTROL")
public class FinalizacionDeRutas extends ETLFlatFileStrategy<Map<String,Object>> {
	private static final Logger log = LoggerFactory.getLogger(FinalizacionDeRutas.class);

	// ---------------------------------------------------------------------------------------------------------------------------------------
	public static final String MOVIL = "MOVIL";
	public static final String PLACA = "NOMBRE TÉCNICO";
	public static final String FECHA_RUTA = "FECHA";
	public static final String CLIENTE_NIT = "DESCRIPCION DEL SERVICIO";
	public static final String NUMERO_ORDEN = "CODIGO DEL CLIENTE";
	public static final String ESTADO_RUTA = "ESTADO FINAL DEL SERVICIO";
	public static final String HORA_INICIO = "HORA DE INICIO";
	public static final String HORA_FIN = "HORA FINAL";

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
		list.add(CLIENTE_NIT);
		list.add(ESTADO_RUTA);
		list.add(HORA_INICIO);
		list.add(HORA_FIN);
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
		sb.append(getValorCampo(MOVIL, campos, mapNameToIndex));
		sb.append("-");
		sb.append(getValorCampo(PLACA, campos, mapNameToIndex));
		sb.append("-");
		sb.append(getValorCampo(FECHA_RUTA, campos, mapNameToIndex));
		sb.append("-");
		sb.append(getValorCampo(CLIENTE_NIT, campos, mapNameToIndex));
		sb.append("-");
		sb.append(getValorCampo(NUMERO_ORDEN, campos, mapNameToIndex));
		
		return sb.toString().toLowerCase();
	}

	@Override
	protected void adicionar(String key, Map<String, Map<String,Object>> map, String[] campos, Map<String, Integer> mapNameToIndex,
			Map<Integer, String> mapIndexToName) {
		if (!map.containsKey(key)) {
			String value;
			
			Map<String,Object> dto = new HashMap<>();

			dto.put("MOVIL", getValorCampo(MOVIL, campos, mapNameToIndex));
			dto.put("PLACA", getValorCampo(PLACA, campos, mapNameToIndex));
			dto.put("FECHA_RUTA", getValorCampo(FECHA_RUTA, campos, mapNameToIndex));
			dto.put("CLIENTE_NIT", getValorCampo(CLIENTE_NIT, campos, mapNameToIndex));
			dto.put("NUMERO_ORDEN", getValorCampo(NUMERO_ORDEN, campos, mapNameToIndex));
			
			EstadoDistribucionType estado = EstadoDistribucionType.DESCONOCIDO;
			value = getValorCampo(ESTADO_RUTA, campos, mapNameToIndex);
			 
			switch (value) {
			case "ENTREGADO":
				estado = EstadoDistribucionType.ENTREGADA;
				break;
			case "REPROGRAMADO":
				estado = EstadoDistribucionType.NO_ENTREGADA;
				break;
			case "NOVEDAD EN ENTREGA":
				estado = EstadoDistribucionType.NOVEDADES;
				break;
			case "VISITA RETRASADA":
				estado = EstadoDistribucionType.TRANSITO;
				break;
			case "VISITA INICIADA":
				estado = EstadoDistribucionType.EN_DESTINO;
				break;
			default:
				break;
			}
			dto.put("ESTADO_RUTA", estado);
			
			value = getValorCampo(HORA_INICIO, campos, mapNameToIndex);
			if(value.equals("NULL")){
				value = "";
			}
			dto.put("HORA_INICIO", value);

			value = getValorCampo(HORA_FIN, campos, mapNameToIndex);
			if(value.equals("NULL")){
				value = "";
			}
			dto.put("HORA_FIN", value);

			map.put(key, dto);
		}
	}

	@Override
	protected void modificar(String key, Map<String, Map<String,Object>> map, String[] campos, Map<String, Integer> mapNameToIndex,
			Map<Integer, String> mapIndexToName) {

		if (map.containsKey(key)) {
		}
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------
	@Override
	@Transactional(readOnly = false)
	protected void cargar(Map<String, Map<String,Object>> map) {
		log.info("Begin cargar");
		
		Collection<Map<String,Object>> c = map.values();
		for (Map<String, Object> dto : c) {
			System.out.println(dto);
		}		

		log.info("End cargar");
	}
}