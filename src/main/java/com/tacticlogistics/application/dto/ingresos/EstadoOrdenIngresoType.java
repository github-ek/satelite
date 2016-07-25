package com.tacticlogistics.application.dto.ingresos;

public enum EstadoOrdenIngresoType {
	RECEPCIONADO("RECEPCIONADO", "RECEPCIONADO", 1, true), 
	INTEGRADO_WMS("INTEGRADO_WMS", "INTEGRADO_WMS", 1,
			true), 
	REALIMENTADO_WMS("REALIMENTADO_WMS", "REALIMENTADO_WMS", 1, true);

	private final String codigo;
	
	private final String nombre;
	
	private final int ordinal;
	
	private final boolean activo;

	public String getCodigo() {
		return codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public Integer getOrdinal() {
		return ordinal;
	}
	public boolean isActivo() {
		return activo;
	}
	private EstadoOrdenIngresoType(String codigo, String nombre, int ordinal, boolean activo) {
		this.codigo = codigo;
		this.nombre = nombre;
		this.ordinal = ordinal;
		this.activo = activo;
	}
	
}
