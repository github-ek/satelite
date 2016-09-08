package com.tacticlogistics.presentation.api.clientes.dicermex.compras;

import java.util.ArrayList;
import java.util.List;

public class OrdenCompraDto {
	private String centroOperacion;
	private String tipoDocumento;
	private String numeroOrden;
	private String estadoOrden;

	List<LineaOrdenCompraDto> lineas = new ArrayList<>();

	public OrdenCompraDto() {
		super();
	}

	public OrdenCompraDto(String centroOperacion, String tipoDocumento, String numeroOrden, String estadoOrden,
			List<LineaOrdenCompraDto> lineas) {
		super();
		this.centroOperacion = centroOperacion;
		this.tipoDocumento = tipoDocumento;
		this.numeroOrden = numeroOrden;
		this.estadoOrden = estadoOrden;
		this.lineas = lineas;
	}

	public String getCentroOperacion() {
		return centroOperacion;
	}

	public void setCentroOperacion(String centroOperacion) {
		this.centroOperacion = centroOperacion;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getNumeroOrden() {
		return numeroOrden;
	}

	public void setNumeroOrden(String numeroOrden) {
		this.numeroOrden = numeroOrden;
	}

	public String getEstadoOrden() {
		return estadoOrden;
	}

	public void setEstadoOrden(String estadoOrden) {
		this.estadoOrden = estadoOrden;
	}

	public List<LineaOrdenCompraDto> getLineas() {
		return lineas;
	}

	public void setLineas(List<LineaOrdenCompraDto> lineas) {
		this.lineas = lineas;
	}

}
