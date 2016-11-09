package com.tacticlogistics.application.etl.tactic;

import org.springframework.stereotype.Component;

import com.tacticlogistics.application.etl.ClienteCodigoType;
import com.tacticlogistics.application.etl.core.ArchivoHandler;
import com.tacticlogistics.application.etl.core.DirectorioClienteHandler;

@Component
public class TacticDirectorioClienteETLHandler extends DirectorioClienteHandler {

	@Override
	protected String getWorkingDirectory() {
		return ClienteCodigoType.TACTIC.toString();
	}


	@Override
	protected ArchivoHandler getArchivoETLHandlerChain() {
		// TODO Auto-generated method stub
		return null;
	}
}
