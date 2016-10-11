package com.tacticlogistics.application.dto.etl;

import static com.tacticlogistics.infrastructure.services.Basic.coalesce;
import static com.tacticlogistics.infrastructure.services.Basic.substringSafe;

import java.time.LocalDate;

public class ETLLineaOrdenDto {
	private Integer numeroItem;
	private String descripcion;

	private Integer cantidadSolicitada;
	private Integer cantidadPlanificada;

	private String productoCodigo;

	private String unidadCodigo;

	private String tipoContenidoCodigo;
	private String tipoContenidoCodigoAlterno;

	private String bodegaOrigenCodigo;
	private String estadoInventarioOrigen;
	private String bodegaOrigenCodigoAlterno;

	private String bodegaDestinoCodigo;
	private String estadoInventarioDestino;
	private String bodegaDestinoCodigoAlterno;

	private String lote;
	private String serial;
	private String cosecha;

	private String requerimientoEstampillado;
	private String requerimientoSalud;
	private String requerimientoImporte;
	private String requerimientoDistribuido;
	private String requerimientoNutricional;
	private String requerimientoBl;
	private String requerimientoFondoCuenta;
	private String requerimientoOrigen;
	private String requerimientoPinado;
	
	private String numeroOrdenTms;
	private LocalDate fechaOrdenTms;

	private String predistribucionDestinoFinal;
	private String predistribucionRotulo;

	private Integer valorDeclaradoPorUnidad;

	private String notas;

	public ETLLineaOrdenDto() {
		super();

		setNumeroItem(null);
		setDescripcion("");

		setCantidadSolicitada(null);
		setCantidadPlanificada(null);

		setProductoCodigo("");
		setUnidadCodigo("");
		setTipoContenidoCodigo("");
		setTipoContenidoCodigoAlterno("");

		setBodegaOrigenCodigo("");
		setBodegaOrigenCodigoAlterno("");
		setEstadoInventarioOrigen("");

		setBodegaDestinoCodigo("");
		setBodegaDestinoCodigoAlterno("");
		setEstadoInventarioDestino("");

		setLote("");
		setSerial("");
		setCosecha("");

		setRequerimientoEstampillado("");
		setRequerimientoSalud("");
		setRequerimientoImporte("");
		setRequerimientoDistribuido("");
		setRequerimientoNutricional("");
		setRequerimientoBl("");
		setRequerimientoFondoCuenta("");
		setRequerimientoOrigen("");
		setRequerimientoPinado("");

		setNumeroOrdenTms("");
		setFechaOrdenTms(null);

		setPredistribucionDestinoFinal("");
		setPredistribucionRotulo("");

		setValorDeclaradoPorUnidad(null);

		setNotas("");
	}

	public Integer getNumeroItem() {
		return numeroItem;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public Integer getCantidadSolicitada() {
		return cantidadSolicitada;
	}

	public Integer getCantidadPlanificada() {
		return cantidadPlanificada;
	}

	public String getProductoCodigo() {
		return productoCodigo;
	}

	public String getUnidadCodigo() {
		return unidadCodigo;
	}

	public String getTipoContenidoCodigo() {
		return tipoContenidoCodigo;
	}

	public String getTipoContenidoCodigoAlterno() {
		return tipoContenidoCodigoAlterno;
	}

	public String getBodegaOrigenCodigo() {
		return bodegaOrigenCodigo;
	}

	public String getBodegaOrigenCodigoAlterno() {
		return bodegaOrigenCodigoAlterno;
	}

	public String getEstadoInventarioOrigen() {
		return estadoInventarioOrigen;
	}

	public String getBodegaDestinoCodigo() {
		return bodegaDestinoCodigo;
	}

	public String getBodegaDestinoCodigoAlterno() {
		return bodegaDestinoCodigoAlterno;
	}

	public String getEstadoInventarioDestino() {
		return estadoInventarioDestino;
	}

	public String getLote() {
		return lote;
	}

	public String getSerial() {
		return serial;
	}

	public String getCosecha() {
		return cosecha;
	}

	public String getRequerimientoEstampillado() {
		return requerimientoEstampillado;
	}

	public String getRequerimientoSalud() {
		return requerimientoSalud;
	}

	public String getRequerimientoImporte() {
		return requerimientoImporte;
	}

	public String getRequerimientoDistribuido() {
		return requerimientoDistribuido;
	}

	public String getRequerimientoNutricional() {
		return requerimientoNutricional;
	}

	public String getRequerimientoBl() {
		return requerimientoBl;
	}

	public String getRequerimientoFondoCuenta() {
		return requerimientoFondoCuenta;
	}

	public String getRequerimientoOrigen() {
		return requerimientoOrigen;
	}
	
	public String getRequerimientoPinado() {
		return requerimientoPinado;
	}

	public String getNumeroOrdenTms() {
		return numeroOrdenTms;
	}

	public LocalDate getFechaOrdenTms() {
		return fechaOrdenTms;
	}

	public String getPredistribucionDestinoFinal() {
		return predistribucionDestinoFinal;
	}

	public String getPredistribucionRotulo() {
		return predistribucionRotulo;
	}

	public Integer getValorDeclaradoPorUnidad() {
		return valorDeclaradoPorUnidad;
	}

	public String getNotas() {
		return notas;
	}

	public void setNumeroItem(Integer numeroItem) {
		this.numeroItem = numeroItem;
	}

	public void setDescripcion(String value) {
		this.descripcion = substringSafe(coalesce(value, "").trim(), 0, 300);
	}

	public void setCantidadSolicitada(Integer cantidadSolicitada) {
		this.cantidadSolicitada = cantidadSolicitada;
	}

	public void setCantidadPlanificada(Integer cantidadPlanificada) {
		this.cantidadPlanificada = cantidadPlanificada;
	}

	public void setProductoCodigo(String value) {
		this.productoCodigo = substringSafe(coalesce(value, "").trim(), 0, 50);
	}

	public void setUnidadCodigo(String value) {
		this.unidadCodigo = substringSafe(coalesce(value, "").trim(), 0, 2);
	}

	public void setTipoContenidoCodigo(String value) {
		this.tipoContenidoCodigo = substringSafe(coalesce(value, "").trim(), 0, 20);
	}

	public void setTipoContenidoCodigoAlterno(String value) {
		this.tipoContenidoCodigoAlterno = substringSafe(coalesce(value, "").trim(), 0, 50);
	}

	public void setBodegaOrigenCodigo(String value) {
		this.bodegaOrigenCodigo = substringSafe(coalesce(value, "").trim(), 0, 32);
	}

	public void setBodegaOrigenCodigoAlterno(String value) {
		this.bodegaOrigenCodigoAlterno = substringSafe(coalesce(value, "").trim(), 0, 50);
	}

	public void setEstadoInventarioOrigen(String value) {
		this.estadoInventarioOrigen = substringSafe(coalesce(value, "").trim(), 0, 4);
	}

	public void setBodegaDestinoCodigo(String value) {
		this.bodegaDestinoCodigo = substringSafe(coalesce(value, "").trim(), 0, 32);
	}

	public void setBodegaDestinoCodigoAlterno(String value) {
		this.bodegaDestinoCodigoAlterno = substringSafe(coalesce(value, "").trim(), 0, 50);
	}

	public void setEstadoInventarioDestino(String value) {
		this.estadoInventarioDestino = substringSafe(coalesce(value, "").trim(), 0, 4);
	}

	public void setLote(String value) {
		this.lote = substringSafe(coalesce(value, "").trim(), 0, 30);
	}

	public void setSerial(String value) {
		this.serial = substringSafe(coalesce(value, "").trim(), 0, 30);
	}

	public void setCosecha(String value) {
		this.cosecha = substringSafe(coalesce(value, "").trim(), 0, 30);
	}

	public void setRequerimientoEstampillado(String value) {
		this.requerimientoEstampillado = normalizarRequerimientoSN(value);
	}

	public void setRequerimientoSalud(String value) {
		this.requerimientoSalud = normalizarRequerimientoSN(value);
	}

	public void setRequerimientoImporte(String value) {
		this.requerimientoImporte = normalizarRequerimientoSN(value);
	}

	public void setRequerimientoDistribuido(String value) {
		this.requerimientoDistribuido = normalizarRequerimientoSN(value);
	}

	public void setRequerimientoNutricional(String value) {
		this.requerimientoNutricional = normalizarRequerimientoSN(value);
	}

	public void setRequerimientoBl(String value) {
		this.requerimientoBl = substringSafe(coalesce(value, "").trim(), 0, 30);
	}

	public void setRequerimientoFondoCuenta(String value) {
		this.requerimientoFondoCuenta = substringSafe(coalesce(value, "").trim(), 0, 30);
	}

	public void setRequerimientoOrigen(String value) {
		this.requerimientoOrigen = substringSafe(coalesce(value, "").trim(), 0, 30);
	}

	public void setRequerimientoPinado(String value) {
		this.requerimientoPinado = normalizarRequerimientoSN(value);
	}

	public void setNumeroOrdenTms(String value) {
		this.numeroOrdenTms = substringSafe(coalesce(value, "").trim(), 0, 30);
	}

	public void setFechaOrdenTms(LocalDate fechaOrdenTms) {
		this.fechaOrdenTms = fechaOrdenTms;
	}

	public void setPredistribucionDestinoFinal(String value) {
		this.predistribucionDestinoFinal = substringSafe(coalesce(value, "").trim(), 0, 100).trim();
	}

	public void setPredistribucionRotulo(String value) {
		this.predistribucionRotulo = substringSafe(coalesce(value, "").trim(), 0, 100).trim();
	}

	public void setValorDeclaradoPorUnidad(Integer value) {
		if (value != null) {
			if (value < 0) {
				value = null;
			}
		}
		this.valorDeclaradoPorUnidad = value;
	}

	public void setNotas(String value) {
		this.notas = substringSafe(coalesce(value, "").trim(), 0, 200);
	}

	private String normalizarRequerimientoSN(String value) {
		value = substringSafe(coalesce(value, "").trim(), 0, 2).toUpperCase();
		switch (value) {
		case "SI":
		case "NO":
		case "S":
		case "N":
			return value.substring(0, 1);
		default:
			return "";
		}
	}

	@Override
	public String toString() {
		return "ETLLineaOrdenDto [numeroItem=" + numeroItem + ", descripcion=" + descripcion + ", cantidadSolicitada="
				+ cantidadSolicitada + ", cantidadPlanificada=" + cantidadPlanificada + ", productoCodigo="
				+ productoCodigo + ", unidadCodigo=" + unidadCodigo + ", tipoContenidoCodigo=" + tipoContenidoCodigo
				+ ", tipoContenidoCodigoAlterno=" + tipoContenidoCodigoAlterno + ", bodegaOrigenCodigo="
				+ bodegaOrigenCodigo + ", estadoInventarioOrigen=" + estadoInventarioOrigen
				+ ", bodegaOrigenCodigoAlterno=" + bodegaOrigenCodigoAlterno + ", bodegaDestinoCodigo="
				+ bodegaDestinoCodigo + ", estadoInventarioDestino=" + estadoInventarioDestino
				+ ", bodegaDestinoCodigoAlterno=" + bodegaDestinoCodigoAlterno + ", lote=" + lote + ", serial=" + serial
				+ ", cosecha=" + cosecha + ", requerimientoEstampillado=" + requerimientoEstampillado
				+ ", requerimientoSalud=" + requerimientoSalud + ", requerimientoImporte=" + requerimientoImporte
				+ ", requerimientoDistribuido=" + requerimientoDistribuido + ", requerimientoNutricional="
				+ requerimientoNutricional + ", requerimientoBl=" + requerimientoBl + ", requerimientoFondoCuenta="
				+ requerimientoFondoCuenta + ", requerimientoOrigen=" + requerimientoOrigen + ", requerimientoPinado="
				+ requerimientoPinado + ", numeroOrdenTms=" + numeroOrdenTms + ", fechaOrdenTms=" + fechaOrdenTms
				+ ", predistribucionDestinoFinal=" + predistribucionDestinoFinal + ", predistribucionRotulo="
				+ predistribucionRotulo + ", valorDeclaradoPorUnidad=" + valorDeclaradoPorUnidad + ", notas=" + notas
				+ "]";
	}
}