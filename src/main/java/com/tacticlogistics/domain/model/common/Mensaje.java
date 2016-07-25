package com.tacticlogistics.domain.model.common;

public interface Mensaje {

	SeveridadType getSeveridad();

	String getCodigo();

	String getTexto();

	String getGrupo();
}