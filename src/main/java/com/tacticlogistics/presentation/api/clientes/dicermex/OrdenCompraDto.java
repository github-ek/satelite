package com.tacticlogistics.presentation.api.clientes.dicermex;

import java.util.List;

public class OrdenCompraDto {
	private String numeroOrden;
	
	List<LineaOrdenCompraDto> lineas;
	
	public OrdenCompraDto() {
		super();
	}

	public OrdenCompraDto(String numeroOrden, List<LineaOrdenCompraDto> lineas) {
		super();
		this.numeroOrden = numeroOrden;
		this.lineas = lineas;
	}

	public String getNumeroOrden() {
		return numeroOrden;
	}

	public List<LineaOrdenCompraDto> getLineas() {
		return lineas;
	}

}
