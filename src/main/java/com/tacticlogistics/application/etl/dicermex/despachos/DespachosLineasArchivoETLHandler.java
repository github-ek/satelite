package com.tacticlogistics.application.etl.dicermex.despachos;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.tacticlogistics.application.etl.core.ArchivoHandler;
import com.tacticlogistics.application.etl.core.extractors.FileExtractor;
import com.tacticlogistics.application.etl.core.loaders.FileLoader;
import com.tacticlogistics.application.etl.dicermex.despachos.dto.DespachoLineaDTO;

@Component
public class DespachosLineasArchivoETLHandler extends ArchivoHandler {
	private static final Pattern PATTERN = Pattern.compile("(?i:(.*)ESBOrdenesDeDespacho(Parcial?)_(\\d+)_DETALLE\\.txt)");

	@Value("${etl.directorio.entradas}")
	private String subDirectorioDeEntradas;

	@Override
	protected Pattern getPattern() {
		return PATTERN;
	}

	@Override
	protected String getWorkingDirectory() {
		return subDirectorioDeEntradas;
	}

	@Override
	protected FileExtractor<DespachoLineaDTO> getFileExtractor() {
		return new DespachosLineasETLFileExtractor();
	}

	@Override
	protected FileLoader getFileLoader() {
		return new FileLoader();
	}
}
