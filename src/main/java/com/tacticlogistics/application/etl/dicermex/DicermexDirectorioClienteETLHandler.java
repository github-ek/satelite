package com.tacticlogistics.application.etl.dicermex;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tacticlogistics.application.etl.ClienteCodigoType;
import com.tacticlogistics.application.etl.core.ArchivoHandler;
import com.tacticlogistics.application.etl.core.DirectorioClienteHandler;
import com.tacticlogistics.application.etl.dicermex.despachos.DespachosArchivoETLHandler;
import com.tacticlogistics.application.etl.dicermex.despachos.DespachosLineasArchivoETLHandler;

@Component
public class DicermexDirectorioClienteETLHandler extends DirectorioClienteHandler {
	
	private static final String WORKING_DIRECTORY = ClienteCodigoType.DICERMEX.toString();

	@Autowired
	private DespachosArchivoETLHandler despachos;

	@Autowired
	private DespachosLineasArchivoETLHandler despachosLineas;

	@Override
	protected String getWorkingDirectory() {
		return WORKING_DIRECTORY;
	}

	@Override
	protected ArchivoHandler getArchivoETLHandlerChain() {
		ArchivoHandler rootChain = despachos;
		rootChain.setNextHandler(despachosLineas);
		return rootChain;
	}
}
