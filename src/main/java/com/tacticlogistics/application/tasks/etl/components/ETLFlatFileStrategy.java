package com.tacticlogistics.application.tasks.etl.components;

import java.io.File;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.tacticlogistics.application.dto.common.MensajesDto;
import com.tacticlogistics.application.tasks.etl.readers.FlatFileReader;
import com.tacticlogistics.application.tasks.etl.readers.Reader;
import com.tacticlogistics.infrastructure.services.Basic;

public abstract class ETLFlatFileStrategy<E> extends ETLFileStrategy<E> {
	protected static final String IGNORAR = "IGNORAR";

	@Autowired
	private FlatFileReader reader;

	// --------------------------------------------------------------------------------------------
	@Override
	public Pattern getPattern() {
		return PATTERN_TXT;
	}

	@Override
	protected Reader<File, String> getReader() {
		return reader;
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------
	@Override
	protected String preTransformar(String texto, MensajesDto mensajes) {
		texto = super.preTransformar(texto, mensajes);

		if (generarEncabezadoConLosNombresDeLosCamposEsperados()) {
			StringBuffer sb = new StringBuffer();
			List<String> esperados = getCamposEsperados();

			if (!esperados.isEmpty()) {
				esperados.forEach(a -> {
					sb.append(a).append(getSeparadorCampos());
				});
				sb.setLength(sb.length() - 1);
				sb.append(getSeparadorRegistros());
			}

			sb.append(texto);
			return sb.toString();
		} else {
			return texto;
		}
	}

	@Override
	protected Map<String, E> transformar(String texto, MensajesDto mensajes) {
		boolean seHaEncontradoElPrimerRegistro = false;

		Map<String, E> map = new HashMap<>();
		Map<String, Integer> mapNameToIndex = new HashMap<>();
		Map<Integer, String> mapIndexToName = new HashMap<>();

		String regExpSeparadorRegistros = "[" + getSeparadorRegistros() + "]";
		String regExpSeparadorCampos = "[" + getSeparadorCampos() + "]";

		String registros[] = texto.split(regExpSeparadorRegistros);

		for (int i = 0; i < registros.length; i++) {
			if (ignorarRegistroAntesDeSerSeparadoPorCampos(registros[i])) {
				continue;
			}

			String campos[] = registros[i].split(regExpSeparadorCampos, -1);

			try {
				if (!seHaEncontradoElPrimerRegistro) {
					seHaEncontradoElPrimerRegistro = true;
					configurarMapping(campos, mapNameToIndex, mapIndexToName);
				} else {
					if (!checkNumeroCamposEsperados(i, campos, mapIndexToName, mensajes)) {
						System.out.println("IGNORADO:" + registros[i]);
						System.out.println("IGNORADO:(" + campos.length + "," + mapIndexToName.size() + ")");
						continue;
					}

					if (ignorarRegistroDespuesDeSerSeparadoPorCampos(campos, mapNameToIndex)) {
						System.out.println("IGNORADO:" + registros[i]);
						continue;
					}

					limpiarCampos(campos, mapNameToIndex);

					String key = generarIdentificadorRegistro(campos, mapNameToIndex);

					if (!map.containsKey(key)) {
						adicionar(key, map, campos, mapNameToIndex, mapIndexToName, mensajes);
					}
					modificar(key, map, campos, mapNameToIndex, mapIndexToName, mensajes);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}

		return map;
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------
	protected abstract String generarIdentificadorRegistro(String[] campos, Map<String, Integer> mapNameToIndex);

	protected abstract void adicionar(String key, Map<String, E> map, String[] campos,
			Map<String, Integer> mapNameToIndex, Map<Integer, String> mapIndexToName, MensajesDto mensajes);

	protected abstract void modificar(String key, Map<String, E> map, String[] campos,
			Map<String, Integer> mapNameToIndex, Map<Integer, String> mapIndexToName, MensajesDto mensajes);

	// ---------------------------------------------------------------------------------------------------------------------------------------
	protected String getSeparadorRegistros() {
		return "\n";
	}

	protected String getSeparadorCampos() {
		return "\t";
	}

	protected boolean generarEncabezadoConLosNombresDeLosCamposEsperados() {
		return false;
	}

	protected List<String> getCamposEsperados() {
		return new ArrayList<>();
	}

	protected List<String> getCamposRequeridos() {
		return getCamposEsperados().stream().filter(a -> !a.equals(IGNORAR)).collect(Collectors.toList());
	}

	protected boolean ignorarRegistroAntesDeSerSeparadoPorCampos(String registro) {
		return (registro == null) || (registro.trim().isEmpty());
	}

	protected boolean ignorarRegistroDespuesDeSerSeparadoPorCampos(String[] campos,
			Map<String, Integer> mapNameToIndex) {
		return false;
	}

	protected void configurarMapping(String campos[], Map<String, Integer> mapNameToIndex,
			Map<Integer, String> mapIndexToName) {
		for (int i = 0; i < campos.length; i++) {
			campos[i] = campos[i].trim();

			if (!IGNORAR.equals(campos[i])) {
				mapNameToIndex.put(campos[i], i);
			}
			mapIndexToName.put(i, campos[i]);
		}

		checkCamposRequeridos(mapNameToIndex);
	}

	protected void checkCamposRequeridos(Map<String, Integer> mapNameToIndex) {
		List<String> camposRequeridas = getCamposRequeridos();
		List<String> camposRequeridosNoIncluidos = new ArrayList<>();

		for (String campo : camposRequeridas) {
			if (!mapNameToIndex.containsKey(campo)) {
				camposRequeridosNoIncluidos.add(campo);
			}
		}
		if (!camposRequeridosNoIncluidos.isEmpty()) {
			StringBuffer sb = new StringBuffer();

			sb.append("Las siguientes columnas no fueron incluidas en el archivo y son requeridas:");
			for (String campo : camposRequeridosNoIncluidos) {
				sb.append("\n").append("[").append(campo).append("]");
			}
			sb.append("\n");
			throw new RuntimeException(sb.toString());
		}
	}

	protected boolean checkNumeroCamposEsperados(int index, String[] campos, Map<Integer, String> mapIndexToName,
			MensajesDto mensajes) {
		if (campos.length != mapIndexToName.size()) {
			String texto = MessageFormat.format("Error en el numero de columnas.Esperado:{0}, Identificadas{1}",
					mapIndexToName.size(), campos.length);
			logError(mensajes, "Fila:" + index + ":" + texto, getArchivo().getName(),"",null);
			return false;
		}
		return true;
	}

	protected void limpiarCampos(String[] campos, Map<String, Integer> mapNameToIndex) {
		for (int j = 0; j < campos.length; j++) {
			if (campos[j].startsWith("\"") && campos[j].endsWith("\"")) {
				campos[j] = campos[j].substring(1, campos[j].length() - 1);
			}
			campos[j] = campos[j].trim();
		}
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------
	protected String getValorCampo(String key, String[] campos, Map<String, Integer> mapNameToIndex) {
		return getValorCampo(key, campos, mapNameToIndex, "");
	}

	protected String getValorCampo(String key, String[] campos, Map<String, Integer> mapNameToIndex,
			String defaultValue) {
		if (!mapNameToIndex.containsKey(key)) {
			return defaultValue;
		}
		return campos[mapNameToIndex.get(key)];
	}

	protected String getValorCampo(Enum<?> key, String[] campos, Map<String, Integer> mapNameToIndex) {
		return getValorCampo(key.toString(), campos, mapNameToIndex, "");
	}

	protected String getValorCampo(Enum<?> key, String[] campos, Map<String, Integer> mapNameToIndex,
			String defaultValue) {
		return getValorCampo(key.toString(), campos, mapNameToIndex, defaultValue);
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------
	protected LocalDate getValorCampoFecha(MensajesDto mensajes, String key, Enum<?> campo, String value,
			DateTimeFormatter sdfmt) {
		return getValorCampoFecha(mensajes, key, campo.toString(), value, sdfmt);
	}

	protected LocalDate getValorCampoFecha(MensajesDto mensajes, String key, String campo, String value,
			DateTimeFormatter sdfmt) {
		LocalDate dateValue = null;

		try {
			dateValue = Basic.toFecha(value, null, sdfmt);
		} catch (ParseException e) {
			logParseException(mensajes, key, campo, value, "", "");
		}
		return dateValue;
	}

	protected LocalDateTime getValorCampoFechaHora(MensajesDto mensajes, String key, Enum<?> campo,
			String value, DateTimeFormatter sdfmt) {
		return getValorCampoFechaHora(mensajes, key, campo.toString(), value, sdfmt);
	}

	protected LocalDateTime getValorCampoFechaHora(MensajesDto mensajes, String key, String campo, String value,
			DateTimeFormatter sdfmt) {
		LocalDateTime dateValue = null;

		try {
			dateValue = Basic.toFechaHora(value, null, sdfmt);
		} catch (ParseException e) {
			logParseException(mensajes, key, campo, value, "", "");
		}
		return dateValue;
	}

	protected LocalTime getValorCampoHora(MensajesDto mensajes, String key, Enum<?> campo, String value,
			DateTimeFormatter sdfmt) {
		return getValorCampoHora(mensajes, key, campo.toString(), value, sdfmt);
	}

	protected LocalTime getValorCampoHora(MensajesDto mensajes, String key, String campo, String value,
			DateTimeFormatter sdfmt) {
		LocalTime timeValue = null;

		try {
			timeValue = Basic.toHora(value, null, sdfmt);
		} catch (ParseException e) {
			logParseException(mensajes, key, campo, value, "", "");
		}
		return timeValue;
	}

	protected Integer getValorCampoDecimal(MensajesDto mensajes, String key, String campo, String value,
			DecimalFormat fmt) {
		Integer integerValue = null;

		try {
			integerValue = Basic.toEntero(value, null, fmt);
		} catch (ParseException e) {
			logParseException(mensajes, key, campo, value, fmt.toPattern(), "");
		}
		return integerValue;
	}

	protected Integer getValorCampoMoneda(MensajesDto mensajes, String key, String campo, String value,
			DecimalFormat fmt) {
		value = value.replaceAll("[$\\s]+", "");
		return getValorCampoDecimal(mensajes, key, campo, value, fmt);
	}
}