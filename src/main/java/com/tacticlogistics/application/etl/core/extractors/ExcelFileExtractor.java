package com.tacticlogistics.application.etl.core.extractors;

import org.springframework.beans.factory.annotation.Autowired;

import com.tacticlogistics.application.etl.core.extractors.readers.ExcelWorkSheetReaderBeta;
import com.tacticlogistics.application.etl.core.extractors.readers.Reader;

public abstract class ExcelFileExtractor<T> extends FileExtractor<T> {

	@Autowired
	private ExcelWorkSheetReaderBeta reader;

	@Override
	protected Reader getReader() {
		return reader;
	}

	@Override
	protected String[] limpiarCampos(String[] campos) {
		campos = super.limpiarCampos(campos);
		for (int i = 0; i < campos.length; i++) {
			campos[i] = limpiarCaracteresEspecialesDeExcel(campos[i]);
		}
		return campos;
	}

	protected String limpiarCaracteresEspecialesDeExcel(final String texto) {
		if (texto == null) {
			return texto;
		} else {
			return texto.replace(String.valueOf((char) 160), "");
		}
	}
}
