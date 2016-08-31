package com.tacticlogistics.domain.model.tms;

public enum EstadoRutaType {
	PLANIFICADA("PLANIFICADA",false,true),
	TRANSITO_ORIGEN("TRANSITO ORIGEN",false,true),
	EN_ORIGEN("EN ORIGEN",false,true),
	CARGANDO("CARGANDO",false,true),
	TRANSITO("TRANSITO",false,true),
	EN_DESTINO("EN DESTINO",false,true),
	DESCARGANDO("DESCARGANDO",false,true),
	FINALIZADA("FINALIZADA",false,true),
	CANCELADA("CANCELADA",false,true),
	RECHAZADA("RECHAZADA",false,true);

	private final String nombre;
	private final boolean usoExterno;
	private final boolean usoInterno;

	private EstadoRutaType(String nombre, boolean usoExterno, boolean usoInterno) {
		this.nombre = nombre;
		this.usoExterno = usoExterno;
		this.usoInterno = usoInterno;
	}

	public String getNombre() {
		return nombre;
	}

	public boolean isUsoExterno() {
		return usoExterno;
	}

	public boolean isUsoInterno() {
		return usoInterno;
	}
}
