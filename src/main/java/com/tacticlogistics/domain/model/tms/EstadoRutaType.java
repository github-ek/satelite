package com.tacticlogistics.domain.model.tms;

public enum EstadoRutaType {
	PLANIFICADA("PLANIFICADA"),
	PLANILLADA("PLANILLADA"),
	TRANSITO_ORIGEN("TRANSITO ORIGEN"),
	EN_ORIGEN("EN ORIGEN"),
	CARGANDO("CARGANDO"),
	TRANSITO("TRANSITO"),
	EN_DESTINO("EN DESTINO"),
	DESCARGANDO("DESCARGANDO"),
	FINALIZADA("FINALIZADA"),
	CANCELADA("CANCELADA"),
	RECHAZADA("RECHAZADA");

	private final String nombre;

	private EstadoRutaType(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}
}
