package com.tacticlogistics.domain.model.ordenes;

public enum EstadoOrdenType {	
	NO_CONFIRMADA("01", "NO CONFIRMADA", false), 
	CONFIRMADA("02", "CONFIRMADA", false),

	ACEPTADA("10", "ACEPTADA", false), 
	NO_ACEPTADA("11", "NO ACEPTADA", false), 
	DEVUELTA_POR_CONTROL_DE_CALIDAD("12",
			"DEVUELTA POR CONTROL DE CALIDAD", false),

	EN_EJECCUCION("20", "EN EJECCUCION", false),

	FINALIZADA("30", "FINALIZADA", true), ANULADA("31", "ANULADA", true);

	private final String codigo;

	private final String nombre;

	private final boolean terminal;

	private EstadoOrdenType(String codigo, String nombre, boolean terminal) {
		this.codigo = codigo;
		this.nombre = nombre;
		this.terminal = terminal;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public boolean isTerminal() {
		return terminal;
	}
}
