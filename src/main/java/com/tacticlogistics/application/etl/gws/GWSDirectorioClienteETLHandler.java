package com.tacticlogistics.application.etl.gws;

import org.springframework.stereotype.Component;

import com.tacticlogistics.application.etl.ClienteCodigoType;
import com.tacticlogistics.application.etl.core.ArchivoHandler;
import com.tacticlogistics.application.etl.core.DirectorioClienteHandler;

@Component
public class GWSDirectorioClienteETLHandler extends DirectorioClienteHandler {

	private static final String WORKING_DIRECTORY = ClienteCodigoType.GWS.toString();

	@Override
	protected String getWorkingDirectory() {
		return ClienteCodigoType.GWS.toString();
	}

	@Override
	protected ArchivoHandler getArchivoETLHandlerChain() {
		// TODO Auto-generated method stub
		return null;
	}


}
