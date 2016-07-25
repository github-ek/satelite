package com.tacticlogistics.domain.model.calendario;

public enum DiaDeSemanaType {
	LUNES("L", "LUNES", 0, false), 
	MARTES("M", "MARTES", 0, false), 
	MIERCOLES("W", "MIERCOLES", 0, false), 
	JUEVES("J","JUEVES", 0,false), 
	VIERNES("V", "VIERNES", 0, false), 
	SABADO("S", "SABADO", 0, true), 
	DOMINGO("D", "DOMINGO", 0, true);

	private final String codigo;
	private final String nombre;
	private final int ordinal;
	private final boolean finDeSemana;

	private DiaDeSemanaType(String codigo, String nombre, int ordinal, boolean finDeSemana) {
		this.codigo = codigo;
		this.nombre = nombre;
		this.ordinal = ordinal;
		this.finDeSemana = finDeSemana;
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

	public boolean isFinDeSemana() {
		return finDeSemana;
	}
}
