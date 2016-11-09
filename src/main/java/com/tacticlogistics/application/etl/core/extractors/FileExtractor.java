package com.tacticlogistics.application.etl.core.extractors;

import java.io.IOException;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.tacticlogistics.application.etl.core.dto.ArchivoDTO;
import com.tacticlogistics.application.etl.core.dto.RegistroDTO;
import com.tacticlogistics.application.etl.core.dto.RegistroDTO.Status;
import com.tacticlogistics.application.etl.core.extractors.readers.Reader;

public abstract class FileExtractor<T> {
	protected static final String SEPARADOR_DE_REGISTRO_NUEVA_LINEA = "\n";
	protected static final String SEPARADOR_DEE_CAMPO_TABULADOR = "\t";
	protected static final String CAMPO_IGNORAR = "IGNORAR";

	public ArchivoDTO<T> extract(final Path archivo) {
		List<RegistroDTO<T>> data = null;

		try {
			data = tabular(incluirEncabezados(read(archivo)));
		} catch (RuntimeException e) {
			data = new ArrayList<>();
			// @formatter:off
			data.add(
					RegistroDTO
					.<T>builder()
					.estado(Status.ERROR)
					.causaError(e.getMessage())
					.build()
					);
			// @formatter:on
		}
		// @formatter:off
		return ArchivoDTO
				.<T>builder()
				.path(archivo)
				.data(data)
				.build();
 		// @formatter:on
	}

	// -------------------------------------------------------------------------
	abstract protected Reader getReader();

	protected String getSeparadorRegistros() {
		return SEPARADOR_DE_REGISTRO_NUEVA_LINEA;
	}

	protected String getSeparadorCampos() {
		return SEPARADOR_DEE_CAMPO_TABULADOR;
	}

	abstract protected List<String> getCampos();

	protected List<String> getCamposRequeridos() {
		// @formatter:off
		return getCampos()
				.stream()
				.filter(a -> !a.equals(CAMPO_IGNORAR))
				.collect(Collectors.toList());
 		// @formatter:on
	}

	// -------------------------------------------------------------------------
	protected String read(final Path archivo) {
		try {
			return getReader().read(archivo).toUpperCase();
		} catch (IOException e) {
			throw new RuntimeException("Error al extraer el contenido del archivo: " + archivo.getFileName(), e);
		}
	}

	// -------------------------------------------------------------------------
	protected String incluirEncabezados(final String texto) {
		StringBuffer sb = new StringBuffer();

		if (seDebenIncluirEncabezados()) {
			// @formatter:off
			sb.append(getEncabezados())
			  .append(texto);
 			// @formatter:on
			return sb.toString();
		}

		return texto;
	}

	protected boolean seDebenIncluirEncabezados() {
		return false;
	}

	protected String getEncabezados() {
		List<String> campos = getCampos();

		if (campos == null || campos.isEmpty()) {
			throw new RuntimeException("No se ha definido la lista de campos que componen el archivo");
		}

		StringBuffer sb = new StringBuffer();
		// @formatter:off
		campos.forEach(a -> {
			sb.append(a)
			  .append(getSeparadorCampos());
		});
		// @formatter:on
		sb.setLength(sb.length() - getSeparadorCampos().length());
		sb.append(getSeparadorRegistros());

		return sb.toString();
	}

	// -------------------------------------------------------------------------
	protected List<RegistroDTO<T>> tabular(final String data) {
		Map<String, Integer> mapNameToIndex = new HashMap<>();
		Map<Integer, String> mapIndexToName = new HashMap<>();

		String regExpSeparadorRegistros = "[" + getSeparadorRegistros() + "]";
		String regExpSeparadorCampos = "[" + getSeparadorCampos() + "]";
		boolean seHaEncontradoElPrimerRegistro = false;

		List<RegistroDTO<T>> result = new ArrayList<>();

		for (String registro : data.split(regExpSeparadorRegistros)) {
			RegistroDTO<T> dto;

			// --------------------------------------------------------------------------------------------
			dto = ignorarRegistro(registro);
			if (dto != null) {
				result.add(dto);
				continue;
			}

			// --------------------------------------------------------------------------------------------
			String[] campos = limpiarCampos(registro.split(regExpSeparadorCampos, -1));

			if (!seHaEncontradoElPrimerRegistro) {
				seHaEncontradoElPrimerRegistro = true;
				configurarMapping(campos, mapNameToIndex, mapIndexToName);
				continue;
			}

			// --------------------------------------------------------------------------------------------
			dto = rechazarRegistroPorNumeroDeCampos(registro, campos, mapNameToIndex, mapIndexToName);
			if (dto != null) {
				result.add(dto);
				continue;
			}

			// --------------------------------------------------------------------------------------------
			dto = ignorarRegistroSeparadoPorCampos(registro, campos, mapNameToIndex, mapIndexToName);
			if (dto != null) {
				result.add(dto);
				continue;
			}

			// --------------------------------------------------------------------------------------------
			dto = transformar(registro, campos, mapNameToIndex, mapIndexToName);
			result.add(dto);
		}
		return result;
	}

	// -------------------------------------------------------------------------
	protected RegistroDTO<T> ignorarRegistro(final String registro) {
		if (ignorarRegistroAntesDeSerSeparadoPorCampos(registro)) {
			// @formatter:off
			return RegistroDTO
					.<T>builder()
					.registro(registro)
					.estado(Status.IGNORADO)
					.build();
			// @formatter:on				
		}
		return null;
	}

	protected boolean ignorarRegistroAntesDeSerSeparadoPorCampos(final String registro) {
		return (registro == null) || (registro.trim().isEmpty());
	}

	// -------------------------------------------------------------------------
	protected void configurarMapping(final String[] campos, final Map<String, Integer> mapNameToIndex,
			final Map<Integer, String> mapIndexToName) {
		for (int i = 0; i < campos.length; i++) {
			campos[i] = campos[i].trim();

			if (!CAMPO_IGNORAR.equals(campos[i])) {
				mapNameToIndex.put(campos[i], i);
			}
			mapIndexToName.put(i, campos[i]);
		}

		checkCamposRequeridos(mapNameToIndex);
	}

	protected void checkCamposRequeridos(final Map<String, Integer> mapNameToIndex) {
		List<String> camposRequeridos = getCamposRequeridos();
		List<String> camposRequeridosNoIncluidos = new ArrayList<>();

		for (String campo : camposRequeridos) {
			if (!mapNameToIndex.containsKey(campo)) {
				camposRequeridosNoIncluidos.add(campo);
			}
		}
		if (!camposRequeridosNoIncluidos.isEmpty()) {
			StringBuffer sb = new StringBuffer();

			sb.append("Los siguientes campos no fueron incluidos en el archivo y son requeridas:");
			for (String campo : camposRequeridosNoIncluidos) {
				sb.append("\n").append("[").append(campo).append("]");
			}
			sb.append("\n");
			throw new RuntimeException(sb.toString());
		}
	}

	// -------------------------------------------------------------------------
	protected RegistroDTO<T> rechazarRegistroPorNumeroDeCampos(final String registro, final String[] campos,
			final Map<String, Integer> mapNameToIndex, final Map<Integer, String> mapIndexToName) {
		RegistroDTO<T> result = null;

		if (!checkNumeroCamposEsperados(campos, mapIndexToName)) {
			// @formatter:off
			String causaError = MessageFormat.format("Error en el número de campos.Esperados:{0}, Identificados{1}",
					mapIndexToName.size(), 
					campos.length);
			return RegistroDTO
					.<T>builder()
					.registro(registro)
					.estado(Status.ERROR)
					.causaError(causaError)
					.build();
			// @formatter:on
		}

		return result;
	}

	protected boolean checkNumeroCamposEsperados(final String[] campos, final Map<Integer, String> mapIndexToName) {
		return campos.length == mapIndexToName.size();
	}

	
	// -------------------------------------------------------------------------
	protected String[] limpiarCampos(final String[] campos) {
		for (int i = 0; i < campos.length; i++) {
			campos[i] = campos[i].trim();

			if (campos[i].startsWith("\"") && campos[i].endsWith("\"")) {
				campos[i] = campos[i].substring(1, campos[i].length() - 1);
			}

			if (campos[i].startsWith("'") && campos[i].endsWith("'")) {
				campos[i] = campos[i].substring(1, campos[i].length() - 1);
			}

			campos[i] = campos[i].trim();
		}

		return campos;
	}

	// -------------------------------------------------------------------------
	protected RegistroDTO<T> ignorarRegistroSeparadoPorCampos(final String registro, final String[] campos,
			final Map<String, Integer> mapNameToIndex, final Map<Integer, String> mapIndexToName) {
		RegistroDTO<T> result = null;

		if (ignorarRegistroDespuesDeSerSeparadoPorCampos(campos, mapNameToIndex)) {
			// @formatter:off
			return RegistroDTO
					.<T>builder()
					.registro(registro)
					.estado(Status.IGNORADO)
					.build();
			// @formatter:on
		}

		return result;
	}

	protected boolean ignorarRegistroDespuesDeSerSeparadoPorCampos(final String[] campos,
			final Map<String, Integer> mapNameToIndex) {
		return false;
	}

	// -------------------------------------------------------------------------
	abstract protected RegistroDTO<T> transformar(final String registro, final String[] campos,
			final Map<String, Integer> mapNameToIndex, final Map<Integer, String> mapIndexToName);
}
