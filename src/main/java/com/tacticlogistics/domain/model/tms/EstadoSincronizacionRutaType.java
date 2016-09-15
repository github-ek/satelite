package com.tacticlogistics.domain.model.tms;

public enum EstadoSincronizacionRutaType {
	PENDIENTE("PENDIENTE"),
	PROBLEMAS_CONFIGURACION("PROBLEMAS CONFIGURACION"),
	REINTENTO("REINTENTO"),
	SINCRONIZADO("SINCRONIZADO"),
	ERROR("ERROR");
	
	private final String nombre;

	private EstadoSincronizacionRutaType(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}
}
