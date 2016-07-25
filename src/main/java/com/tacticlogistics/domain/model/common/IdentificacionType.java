package com.tacticlogistics.domain.model.common;

public enum IdentificacionType {
	NI("NI", "Número De Identificación Tributario", 0, true), CC("CC", "Cédula de Ciudadanía", 1, true);

	private final String codigo;
	private final String nombre;
	private final int ordinal;
	private final boolean activo;

	private IdentificacionType(String codigo, String nombre, int ordinal, boolean activo) {
		this.codigo = codigo;
		this.nombre = nombre;
		this.ordinal = ordinal;
		this.activo = activo;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public int getOrdinal() {
		return ordinal;
	}

	public boolean isActivo() {
		return activo;
	}
}
