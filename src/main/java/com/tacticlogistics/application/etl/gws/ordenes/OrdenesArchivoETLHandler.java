package com.tacticlogistics.application.etl.gws.ordenes;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.tacticlogistics.application.etl.core.ArchivoHandler;
import com.tacticlogistics.application.etl.core.extractors.FileExtractor;
import com.tacticlogistics.application.etl.core.loaders.FileLoader;
import com.tacticlogistics.application.etl.gws.ordenes.dto.OrdenDTO;

@Component
public class OrdenesArchivoETLHandler extends ArchivoHandler {

	@Value("${etl.directorio.entradas}")
	private String subDirectorioDeEntradas;
	
	@Autowired
	private OrdenesFileExtractor etlFileExtractor;
	
	@Override
	protected Pattern getPattern() {
		return PATTERN_TXT;
	}
	
	@Override
	protected String getWorkingDirectory() {
		return subDirectorioDeEntradas+"\\ORDENES";
	}

	@Override
	protected FileExtractor<OrdenDTO> getFileExtractor() {
		return etlFileExtractor;
	}

	@Override
	protected FileLoader getFileLoader() {
		return new FileLoader();
	}
}
