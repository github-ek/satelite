package com.tacticlogistics.domain.model.oms;

public enum EstadoOrdenType {
	NO_CONFIRMADA("NO CONFIRMADA",true,true), 
	CONFIRMADA("CONFIRMADA",true,true), 
	ACEPTADA("ACEPTADA",false,true),
	
	EJECUCION("EJECUCION",false,false), 

	NO_ENTREGADA("NO_ENTREGADA",false,false),

	ENTREGADA("ENTREGADA",false,false),
	REPROGRAMADA("REPROGRAMADA",true,true),
	NO_REPROGRAMADA("NO REPROGRAMADA",true,true),
	NOVEDADES("NOVEDADES",false,false),

	FINALIZADA("FINALIZADA",false,true), 
	ANULADA("ANULADA",true,true),
	
	EN_EJECCUCION("EN EJECCUCION", false,false)
	;

	private final String nombre;
	private final boolean usoExterno;
	private final boolean usoInterno;

	private EstadoOrdenType(String nombre, boolean usoExterno, boolean usoInterno) {
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
