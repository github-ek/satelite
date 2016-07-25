package com.tacticlogistics.domain.model.entregas;

public enum EstadoEntregaType {
	NO_DISPONIBLE_PARA_PROGRAMACION_RUTA("01", "NO DISPONIBLE PARA PROGRAMACION RUTA",false), 
	DISPONIBLE_PARA_PROGRAMACION_RUTA("02", "DISPONIBLE PARA PROGRAMACION RUTA", false),

	EN_PROGRAMACION_DE_RUTA("10", "EN PROGRAMACION DE RUTA", false), 
	CON_RUTA_PROGRAMADA("11", "CON RUTA PROGRAMADA",false),

	EN_ORIGEN("20", "EN ORIGEN", false), 
	EN_TRANSITO("21", "EN TRANSITO", false), 
	EN_DESTINO("22", "EN DESTINO",false), 
	CON_ENTREGA_REPORTADA("23", "CON_ENTREGA_REPORTADA", false), 
	NO_ENTREGADA("24", "NO ENTREGADA",false),

	ENTREGADA("30", "ENTREGADA", true), 
	REPROGRAMADA("31", "REPROGRAMADA", true), 
	ANULADA("32", "ANULADA", true)
	;

	private final String codigo;

	private final String nombre;

	private final boolean terminal;

	private EstadoEntregaType(String codigo, String nombre, boolean terminal) {
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