package com.tacticlogistics.presentation.api.clientes.dicermex.compras;

import java.util.Date;

public class LineaOrdenCompraDto {
		private Date fechaEntrega;
		private int cantidad;
		private String productoCodigo;
		private String unidadCodigo;
		private String notas;
		private String talla;
		private String color;
		private String lote;
		private String bodegaDestinoCodigo;
		private String ubicacionId;

	public LineaOrdenCompraDto() {
		super();
	}

	public LineaOrdenCompraDto(Date fechaEntrega, int cantidad, String productoCodigo, String unidadCodigo,
			String notas, String talla, String color, String lote, String bodegaDestinoCodigo, String ubicacionId) {
		super();
		this.fechaEntrega = fechaEntrega;
		this.cantidad = cantidad;
		this.productoCodigo = productoCodigo;
		this.unidadCodigo = unidadCodigo;
		this.notas = notas;
		this.talla = talla;
		this.color = color;
		this.lote = lote;
		this.bodegaDestinoCodigo = bodegaDestinoCodigo;
		this.ubicacionId = ubicacionId;
	}

	public Date getFechaEntrega() {
		return fechaEntrega;
	}

	public int getCantidad() {
		return cantidad;
	}

	public String getProductoCodigo() {
		return productoCodigo;
	}

	public String getUnidadCodigo() {
		return unidadCodigo;
	}

	public String getNotas() {
		return notas;
	}

	public String getTalla() {
		return talla;
	}

	public String getColor() {
		return color;
	}

	public String getLote() {
		return lote;
	}

	public String getBodegaDestinoCodigo() {
		return bodegaDestinoCodigo;
	}

	public String getUbicacionId() {
		return ubicacionId;
	}

}
