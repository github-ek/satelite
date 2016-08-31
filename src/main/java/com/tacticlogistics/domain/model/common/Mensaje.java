package com.tacticlogistics.domain.model.common;

public interface Mensaje {

	SeveridadType getSeveridad();

	String getCodigo();

	Object getData();
	
	String getTexto();

	String getGrupo();
}