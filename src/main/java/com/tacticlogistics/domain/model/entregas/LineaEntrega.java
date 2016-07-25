package com.tacticlogistics.domain.model.entregas;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tacticlogistics.domain.model.ordenes.LineaOrden;
import com.tacticlogistics.domain.model.wms.Producto;
import com.tacticlogistics.domain.model.wms.Unidad;

@Entity
@Table(name = "LineasEntrega", catalog = "ordenes")
public class LineaEntrega implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_linea_entrega", unique = true, nullable = false)
	private Integer id;

	// TODO hacer unico
	private int numeroItem;

	private int cantidadOriginal;

	private int cantidadConforme;

	private int cantidadNoConforme;

	private int cantidadSobrante;

	private int cantidadFaltante;

	@Column(nullable = false, length = 30)
	private String lote;
	
	@Column(nullable = false, length = 200)
	private String notas;

	@Column(nullable = true, columnDefinition = "DECIMAL(12,4)")
	private BigDecimal volumen;

	@Column(nullable = true, columnDefinition = "DECIMAL(12,4)")
	private BigDecimal pesoBruto;

	@Column(nullable = true, columnDefinition = "DECIMAL(12,4)")
	private BigDecimal pesoBrutoVolumetrico;

	@Column(nullable = false, columnDefinition = "DATETIME2(0)")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaActualizacion;

	@Column(nullable = false, length = 50)
	private String usuarioActualizacion;

	//---------------------------------------------------------------------------------------------------------
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_entrega", nullable = true)
	private Entrega entrega;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_linea_orden", nullable = false)
	private LineaOrden lineaOrden;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_producto", nullable = false)
	private Producto producto;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_unidad", nullable = false)
	private Unidad unidad;

	protected LineaEntrega() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getNumeroItem() {
		return numeroItem;
	}

	public void setNumeroItem(int numeroItem) {
		this.numeroItem = numeroItem;
	}

	public int getCantidadOriginal() {
		return cantidadOriginal;
	}

	public void setCantidadOriginal(int cantidadOriginal) {
		this.cantidadOriginal = cantidadOriginal;
	}

	public int getCantidadConforme() {
		return cantidadConforme;
	}

	public void setCantidadConforme(int cantidadConforme) {
		this.cantidadConforme = cantidadConforme;
	}

	public int getCantidadNoConforme() {
		return cantidadNoConforme;
	}

	public void setCantidadNoConforme(int cantidadNoConforme) {
		this.cantidadNoConforme = cantidadNoConforme;
	}

	public int getCantidadSobrante() {
		return cantidadSobrante;
	}

	public void setCantidadSobrante(int cantidadSobrante) {
		this.cantidadSobrante = cantidadSobrante;
	}

	public int getCantidadFaltante() {
		return cantidadFaltante;
	}

	public void setCantidadFaltante(int cantidadFaltante) {
		this.cantidadFaltante = cantidadFaltante;
	}

	public String getLote() {
		return lote;
	}

	public void setLote(String lote) {
		this.lote = lote;
	}

	public String getNotas() {
		return notas;
	}

	public void setNotas(String notas) {
		this.notas = notas;
	}

	public BigDecimal getVolumen() {
		return volumen;
	}

	public void setVolumen(BigDecimal volumen) {
		this.volumen = volumen;
	}

	public BigDecimal getPesoBruto() {
		return pesoBruto;
	}

	public void setPesoBruto(BigDecimal pesoBruto) {
		this.pesoBruto = pesoBruto;
	}

	public BigDecimal getPesoBrutoVolumetrico() {
		return pesoBrutoVolumetrico;
	}

	public void setPesoBrutoVolumetrico(BigDecimal pesoBrutoVolumetrico) {
		this.pesoBrutoVolumetrico = pesoBrutoVolumetrico;
	}

	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public String getUsuarioActualizacion() {
		return usuarioActualizacion;
	}

	public void setUsuarioActualizacion(String usuarioActualizacion) {
		this.usuarioActualizacion = usuarioActualizacion;
	}

	public Entrega getEntrega() {
		return entrega;
	}

	public void setEntrega(Entrega entrega) {
		this.entrega = entrega;
	}

	public LineaOrden getLineaOrden() {
		return lineaOrden;
	}

	public void setLineaOrden(LineaOrden lineaOrden) {
		this.lineaOrden = lineaOrden;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Unidad getUnidad() {
		return unidad;
	}

	public void setUnidad(Unidad unidad) {
		this.unidad = unidad;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LineaEntrega [");
		if (id != null)
			builder.append("id=").append(id).append(", ");
		builder.append("numeroItem=").append(numeroItem).append(", cantidadOriginal=").append(cantidadOriginal)
				.append(", cantidadConforme=").append(cantidadConforme).append(", cantidadNoConforme=")
				.append(cantidadNoConforme).append(", cantidadSobrante=").append(cantidadSobrante)
				.append(", cantidadFaltante=").append(cantidadFaltante).append(", ");
		if (lote != null)
			builder.append("lote=").append(lote).append(", ");
		if (notas != null)
			builder.append("notas=").append(notas).append(", ");
		if (volumen != null)
			builder.append("volumen=").append(volumen).append(", ");
		if (pesoBruto != null)
			builder.append("pesoBruto=").append(pesoBruto).append(", ");
		if (pesoBrutoVolumetrico != null)
			builder.append("pesoBrutoVolumetrico=").append(pesoBrutoVolumetrico).append(", ");
		if (fechaActualizacion != null)
			builder.append("fechaActualizacion=").append(fechaActualizacion).append(", ");
		if (usuarioActualizacion != null)
			builder.append("usuarioActualizacion=").append(usuarioActualizacion).append(", ");
		if (entrega != null)
			builder.append("entrega=").append(entrega).append(", ");
		if (lineaOrden != null)
			builder.append("lineaOrden=").append(lineaOrden).append(", ");
		if (producto != null)
			builder.append("producto=").append(producto).append(", ");
		if (unidad != null)
			builder.append("unidad=").append(unidad);
		builder.append("]");
		return builder.toString();
	}
	
	
}
