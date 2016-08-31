package com.tacticlogistics.application.dto.oms;

import java.sql.Time;
import java.util.Date;

import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tacticlogistics.domain.model.common.valueobjects.Contacto;
import com.tacticlogistics.domain.model.common.valueobjects.Dimensiones;
import com.tacticlogistics.domain.model.crm.Canal;
import com.tacticlogistics.domain.model.crm.DestinatarioRemitente;
import com.tacticlogistics.domain.model.geo.Ciudad;
import com.tacticlogistics.domain.model.oms.EstadoAlistamientoType;
import com.tacticlogistics.domain.model.oms.EstadoDistribucionType;
import com.tacticlogistics.domain.model.oms.EstadoOrdenType;

public class OrdenDetailDto extends OrdenPivotDto {
	private int numeroItem;
	private String descripcion;

	private int cantidadSolicitada;
	@JsonIgnore
	private int cantidadPlanificada;
	@JsonIgnore
	private int cantidadAlistada;
	@JsonIgnore
	private int cantidadEntregada;
	@JsonIgnore
	private int cantidadNoEntregada;
	@JsonIgnore
	private int cantidadNoEntregadaLegalizada;
	@JsonIgnore
	private int cantidadSobrante;
	@JsonIgnore
	private int cantidadSobranteLegalizada;

	// ---------------------------------------------------------------------------------------------------------
	@JsonIgnore
	private Integer productoId;
	private String productoCodigo;
	@JsonIgnore
	private String productoCodigoAlterno;

	// ---------------------------------------------------------------------------------------------------------
	@JsonIgnore
	private Integer unidadId;
	private String unidadCodigo;
	@JsonIgnore
	private String unidadCodigoAlterno;

	// ---------------------------------------------------------------------------------------------------------
	@JsonIgnore
	private Dimensiones dimensiones;

	// ---------------------------------------------------------------------------------------------------------
	@JsonIgnore
	private Integer bodegaOrigenId;
	private String bodegaOrigenCodigo;
	private String bodegaOrigenCodigoAlterno;
	private String estadoInventarioOrigenId;
	private String numeroOrdenWmsOrigen;

	// ---------------------------------------------------------------------------------------------------------
	@JsonIgnore
	private Integer bodegaDestinoId;
	private String bodegaDestinoCodigo;
	private String bodegaDestinoCodigoAlterno;
	private String estadoInventarioDestinoId;
	private String numeroOrdenWmsDestino;

	// ---------------------------------------------------------------------------------------------------------
	private String lote;
	@JsonIgnore
	private String serial;
	@JsonIgnore
	private String cosecha;
	@JsonIgnore
	private String requerimientoEstampillado;
	@JsonIgnore
	private String requerimientoSalud;
	@JsonIgnore
	private String requerimientoImporte;
	@JsonIgnore
	private String requerimientoDistribuido;
	@JsonIgnore
	private String requerimientoNutricional;
	@JsonIgnore
	private String requerimientoBl;
	@JsonIgnore
	private String requerimientoFondoCuenta;
	@JsonIgnore
	private String requerimientoOrigen;

	// ---------------------------------------------------------------------------------------------------------
	@JsonIgnore
	private String numeroOrdenTms;
	@JsonIgnore
	private Date fechaOrdenTms;
	private String predistribucionDestinoFinal;
	private String predistribucionRotulo;

	// ---------------------------------------------------------------------------------------------------------
	private Integer valorDeclaradoPorUnidad;
	private String notas;

	// ---------------------------------------------------------------------------------------------------------
	public int getNumeroItem() {
		return numeroItem;
	}

	public void setNumeroItem(int numeroItem) {
		this.numeroItem = numeroItem;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getCantidadSolicitada() {
		return cantidadSolicitada;
	}

	public void setCantidadSolicitada(int cantidadSolicitada) {
		this.cantidadSolicitada = cantidadSolicitada;
	}

	public int getCantidadPlanificada() {
		return cantidadPlanificada;
	}

	public void setCantidadPlanificada(int cantidadPlanificada) {
		this.cantidadPlanificada = cantidadPlanificada;
	}

	public int getCantidadAlistada() {
		return cantidadAlistada;
	}

	public void setCantidadAlistada(int cantidadAlistada) {
		this.cantidadAlistada = cantidadAlistada;
	}

	public int getCantidadEntregada() {
		return cantidadEntregada;
	}

	public void setCantidadEntregada(int cantidadEntregada) {
		this.cantidadEntregada = cantidadEntregada;
	}

	public int getCantidadNoEntregada() {
		return cantidadNoEntregada;
	}

	public void setCantidadNoEntregada(int cantidadNoEntregada) {
		this.cantidadNoEntregada = cantidadNoEntregada;
	}

	public int getCantidadNoEntregadaLegalizada() {
		return cantidadNoEntregadaLegalizada;
	}

	public void setCantidadNoEntregadaLegalizada(int cantidadNoEntregadaLegalizada) {
		this.cantidadNoEntregadaLegalizada = cantidadNoEntregadaLegalizada;
	}

	public int getCantidadSobrante() {
		return cantidadSobrante;
	}

	public void setCantidadSobrante(int cantidadSobrante) {
		this.cantidadSobrante = cantidadSobrante;
	}

	public int getCantidadSobranteLegalizada() {
		return cantidadSobranteLegalizada;
	}

	public void setCantidadSobranteLegalizada(int cantidadSobranteLegalizada) {
		this.cantidadSobranteLegalizada = cantidadSobranteLegalizada;
	}

	public Integer getProductoId() {
		return productoId;
	}

	public void setProductoId(Integer productoId) {
		this.productoId = productoId;
	}

	public String getProductoCodigo() {
		return productoCodigo;
	}

	public void setProductoCodigo(String productoCodigo) {
		this.productoCodigo = productoCodigo;
	}

	public String getProductoCodigoAlterno() {
		return productoCodigoAlterno;
	}

	public void setProductoCodigoAlterno(String productoCodigoAlterno) {
		this.productoCodigoAlterno = productoCodigoAlterno;
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

	public String getUnidadCodigoAlterno() {
		return unidadCodigoAlterno;
	}

	public void setUnidadCodigoAlterno(String unidadCodigoAlterno) {
		this.unidadCodigoAlterno = unidadCodigoAlterno;
	}

	public Dimensiones getDimensiones() {
		return dimensiones;
	}

	public void setDimensiones(Dimensiones dimensiones) {
		this.dimensiones = dimensiones;
	}

	public Integer getBodegaOrigenId() {
		return bodegaOrigenId;
	}

	public void setBodegaOrigenId(Integer bodegaOrigenId) {
		this.bodegaOrigenId = bodegaOrigenId;
	}

	public String getBodegaOrigenCodigo() {
		return bodegaOrigenCodigo;
	}

	public void setBodegaOrigenCodigo(String bodegaOrigenCodigo) {
		this.bodegaOrigenCodigo = bodegaOrigenCodigo;
	}

	public String getBodegaOrigenCodigoAlterno() {
		return bodegaOrigenCodigoAlterno;
	}

	public void setBodegaOrigenCodigoAlterno(String bodegaOrigenCodigoAlterno) {
		this.bodegaOrigenCodigoAlterno = bodegaOrigenCodigoAlterno;
	}

	public String getEstadoInventarioOrigenId() {
		return estadoInventarioOrigenId;
	}

	public void setEstadoInventarioOrigenId(String estadoInventarioOrigenId) {
		this.estadoInventarioOrigenId = estadoInventarioOrigenId;
	}

	public String getNumeroOrdenWmsOrigen() {
		return numeroOrdenWmsOrigen;
	}

	public void setNumeroOrdenWmsOrigen(String numeroOrdenWmsOrigen) {
		this.numeroOrdenWmsOrigen = numeroOrdenWmsOrigen;
	}

	public Integer getBodegaDestinoId() {
		return bodegaDestinoId;
	}

	public void setBodegaDestinoId(Integer bodegaDestinoId) {
		this.bodegaDestinoId = bodegaDestinoId;
	}

	public String getBodegaDestinoCodigo() {
		return bodegaDestinoCodigo;
	}

	public void setBodegaDestinoCodigo(String bodegaDestinoCodigo) {
		this.bodegaDestinoCodigo = bodegaDestinoCodigo;
	}

	public String getBodegaDestinoCodigoAlterno() {
		return bodegaDestinoCodigoAlterno;
	}

	public void setBodegaDestinoCodigoAlterno(String bodegaDestinoCodigoAlterno) {
		this.bodegaDestinoCodigoAlterno = bodegaDestinoCodigoAlterno;
	}

	public String getEstadoInventarioDestinoId() {
		return estadoInventarioDestinoId;
	}

	public void setEstadoInventarioDestinoId(String estadoInventarioDestinoId) {
		this.estadoInventarioDestinoId = estadoInventarioDestinoId;
	}

	public String getNumeroOrdenWmsDestino() {
		return numeroOrdenWmsDestino;
	}

	public void setNumeroOrdenWmsDestino(String numeroOrdenWmsDestino) {
		this.numeroOrdenWmsDestino = numeroOrdenWmsDestino;
	}

	public String getLote() {
		return lote;
	}

	public void setLote(String lote) {
		this.lote = lote;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getCosecha() {
		return cosecha;
	}

	public void setCosecha(String cosecha) {
		this.cosecha = cosecha;
	}

	public String getRequerimientoEstampillado() {
		return requerimientoEstampillado;
	}

	public void setRequerimientoEstampillado(String requerimientoEstampillado) {
		this.requerimientoEstampillado = requerimientoEstampillado;
	}

	public String getRequerimientoSalud() {
		return requerimientoSalud;
	}

	public void setRequerimientoSalud(String requerimientoSalud) {
		this.requerimientoSalud = requerimientoSalud;
	}

	public String getRequerimientoImporte() {
		return requerimientoImporte;
	}

	public void setRequerimientoImporte(String requerimientoImporte) {
		this.requerimientoImporte = requerimientoImporte;
	}

	public String getRequerimientoDistribuido() {
		return requerimientoDistribuido;
	}

	public void setRequerimientoDistribuido(String requerimientoDistribuido) {
		this.requerimientoDistribuido = requerimientoDistribuido;
	}

	public String getRequerimientoNutricional() {
		return requerimientoNutricional;
	}

	public void setRequerimientoNutricional(String requerimientoNutricional) {
		this.requerimientoNutricional = requerimientoNutricional;
	}

	public String getRequerimientoBl() {
		return requerimientoBl;
	}

	public void setRequerimientoBl(String requerimientoBl) {
		this.requerimientoBl = requerimientoBl;
	}

	public String getRequerimientoFondoCuenta() {
		return requerimientoFondoCuenta;
	}

	public void setRequerimientoFondoCuenta(String requerimientoFondoCuenta) {
		this.requerimientoFondoCuenta = requerimientoFondoCuenta;
	}

	public String getRequerimientoOrigen() {
		return requerimientoOrigen;
	}

	public void setRequerimientoOrigen(String requerimientoOrigen) {
		this.requerimientoOrigen = requerimientoOrigen;
	}

	public String getNumeroOrdenTms() {
		return numeroOrdenTms;
	}

	public void setNumeroOrdenTms(String numeroOrdenTms) {
		this.numeroOrdenTms = numeroOrdenTms;
	}

	public Date getFechaOrdenTms() {
		return fechaOrdenTms;
	}

	public void setFechaOrdenTms(Date fechaOrdenTms) {
		this.fechaOrdenTms = fechaOrdenTms;
	}

	public String getPredistribucionDestinoFinal() {
		return predistribucionDestinoFinal;
	}

	public void setPredistribucionDestinoFinal(String predistribucionDestinoFinal) {
		this.predistribucionDestinoFinal = predistribucionDestinoFinal;
	}

	public String getPredistribucionRotulo() {
		return predistribucionRotulo;
	}

	public void setPredistribucionRotulo(String predistribucionRotulo) {
		this.predistribucionRotulo = predistribucionRotulo;
	}

	public Integer getValorDeclaradoPorUnidad() {
		return valorDeclaradoPorUnidad;
	}

	public void setValorDeclaradoPorUnidad(Integer valorDeclaradoPorUnidad) {
		this.valorDeclaradoPorUnidad = valorDeclaradoPorUnidad;
	}

	public String getNotas() {
		return notas;
	}

	public void setNotas(String notas) {
		this.notas = notas;
	}
}
