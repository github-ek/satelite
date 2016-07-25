package com.tacticlogistics.application.dto.ordenes;

public class OtrosDatosDto {
	private Integer valorDeclarado;

	private Integer tipoFormaPago;
	private String codigoTipoFormaPago;
	private String nombreTipoFormaPago;

	private boolean requiereMaquila;

	private String notas;

	public Integer getValorDeclarado() {
		return valorDeclarado;
	}

	public void setValorDeclarado(Integer valorDeclarado) {
		this.valorDeclarado = valorDeclarado;
	}

	public Integer getTipoFormaPago() {
		return tipoFormaPago;
	}

	public void setTipoFormaPago(Integer tipoFormaPago) {
		this.tipoFormaPago = tipoFormaPago;
	}

	public String getCodigoTipoFormaPago() {
		return codigoTipoFormaPago;
	}

	public void setCodigoTipoFormaPago(String codigoTipoFormaPago) {
		this.codigoTipoFormaPago = codigoTipoFormaPago;
	}

	public String getNombreTipoFormaPago() {
		return nombreTipoFormaPago;
	}

	public void setNombreTipoFormaPago(String nombreTipoFormaPago) {
		this.nombreTipoFormaPago = nombreTipoFormaPago;
	}

	public boolean isRequiereMaquila() {
		return requiereMaquila;
	}

	public void setRequiereMaquila(boolean requiereMaquila) {
		this.requiereMaquila = requiereMaquila;
	}

	public String getNotas() {
		return notas;
	}

	public void setNotas(String notas) {
		this.notas = notas;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OrdenDatosOtrosViewModel [");
		if (valorDeclarado != null)
			builder.append("valorDeclarado=").append(valorDeclarado).append(", ");
		if (tipoFormaPago != null)
			builder.append("tipoFormaPago=").append(tipoFormaPago).append(", ");
		if (codigoTipoFormaPago != null)
			builder.append("codigoTipoFormaPago=").append(codigoTipoFormaPago).append(", ");
		if (nombreTipoFormaPago != null)
			builder.append("nombreTipoFormaPago=").append(nombreTipoFormaPago).append(", ");
		builder.append("requiereMaquila=").append(requiereMaquila).append(", ");
		if (notas != null)
			builder.append("notas=").append(notas);
		builder.append("]");
		return builder.toString();
	}
}