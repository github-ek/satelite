package com.tacticlogistics.application.dto.wms;

import java.math.BigDecimal;

public class ProductoUnidadDto {
	private String codigoUnidadAlterno;
	private Integer unidadId;
	private String unidadCodigo;
	private String unidadNombre;
	private Integer factorConversion;
	private BigDecimal largo;
	private BigDecimal ancho;
	private BigDecimal alto;
	private BigDecimal pesoBruto;

	public String getCodigoUnidadAlterno() {
		return codigoUnidadAlterno;
	}

	public void setCodigoUnidadAlterno(String codigoUnidadAlterno) {
		this.codigoUnidadAlterno = codigoUnidadAlterno;
	}

	public Integer getUnidadId() {
		return unidadId;
	}

	public void setUnidadId(Integer unidadId) {
		this.unidadId = unidadId;
	}

	public String getUnidadCodigo() {
		return unidadCodigo;
	}

	public void setUnidadCodigo(String unidadCodigo) {
		this.unidadCodigo = unidadCodigo;
	}

	public String getUnidadNombre() {
		return unidadNombre;
	}

	public void setUnidadNombre(String unidadNombre) {
		this.unidadNombre = unidadNombre;
	}

	public Integer getFactorConversion() {
		return factorConversion;
	}

	public void setFactorConversion(Integer factorConversion) {
		this.factorConversion = factorConversion;
	}

	public BigDecimal getLargo() {
		return largo;
	}

	public void setLargo(BigDecimal largo) {
		this.largo = largo;
	}

	public BigDecimal getAncho() {
		return ancho;
	}

	public void setAncho(BigDecimal ancho) {
		this.ancho = ancho;
	}

	public BigDecimal getAlto() {
		return alto;
	}

	public void setAlto(BigDecimal alto) {
		this.alto = alto;
	}

	public BigDecimal getPesoBruto() {
		return pesoBruto;
	}

	public void setPesoBruto(BigDecimal pesoBruto) {
		this.pesoBruto = pesoBruto;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Unidad [");
		if (codigoUnidadAlterno != null)
			builder.append("codigoUnidadAlterno=").append(codigoUnidadAlterno).append(", ");
		if (unidadId != null)
			builder.append("unidadId=").append(unidadId).append(", ");
		if (unidadCodigo != null)
			builder.append("unidadCodigo=").append(unidadCodigo).append(", ");
		if (unidadNombre != null)
			builder.append("unidadNombre=").append(unidadNombre).append(", ");
		if (factorConversion != null)
			builder.append("factorConversion=").append(factorConversion).append(", ");
		if (largo != null)
			builder.append("largo=").append(largo).append(", ");
		if (ancho != null)
			builder.append("ancho=").append(ancho).append(", ");
		if (alto != null)
			builder.append("alto=").append(alto).append(", ");
		if (pesoBruto != null)
			builder.append("pesoBruto=").append(pesoBruto);
		builder.append("]");
		return builder.toString();
	}
}