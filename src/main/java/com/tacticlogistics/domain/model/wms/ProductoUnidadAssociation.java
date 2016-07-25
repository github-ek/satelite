package com.tacticlogistics.domain.model.wms;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "productos_unidades", catalog = "wms")
public class ProductoUnidadAssociation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_producto", nullable = false)
	private Producto producto;

	@Id
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_unidad", nullable = false)
	private Unidad unidad;

	@Id
	private int nivel;

	private boolean unidadBase;

	private int factorConversion;

	@Column(nullable = false, columnDefinition = "DECIMAL(12,4)")
	private BigDecimal largo;

	@Column(nullable = false, columnDefinition = "DECIMAL(12,4)")
	private BigDecimal ancho;

	@Column(nullable = false, columnDefinition = "DECIMAL(12,4)")
	private BigDecimal alto;

	@Column(nullable = false, columnDefinition = "DECIMAL(12,4)")
	private BigDecimal pesoBruto;

	private boolean habilitadaEnOrdenesDeIngreso;

	private boolean predeterminadaEnOrdenesDeIngreso;

	private boolean habilitadaEnOrdenesDeSalida;

	private boolean predeterminadaEnOrdenesDeSalida;

	@Column(nullable = true)
	private Integer valorAproximado;

	public ProductoUnidadAssociation() {
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

	public boolean isUnidadBase() {
		return unidadBase;
	}

	public void setUnidadBase(boolean unidadBase) {
		this.unidadBase = unidadBase;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public int getFactorConversion() {
		return factorConversion;
	}

	public void setFactorConversion(int factorConversion) {
		this.factorConversion = factorConversion;
	}

	public Integer getValorAproximado() {
		return valorAproximado;
	}

	public void setValorAproximado(Integer valorAproximado) {
		this.valorAproximado = valorAproximado;
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

	public boolean isHabilitadaEnOrdenesDeIngreso() {
		return habilitadaEnOrdenesDeIngreso;
	}

	public void setHabilitadaEnOrdenesDeIngreso(boolean habilitadaEnOrdenesDeIngreso) {
		this.habilitadaEnOrdenesDeIngreso = habilitadaEnOrdenesDeIngreso;
	}

	public boolean isPredeterminadaEnOrdenesDeIngreso() {
		return predeterminadaEnOrdenesDeIngreso;
	}

	public void setPredeterminadaEnOrdenesDeIngreso(boolean predeterminadaEnOrdenesDeIngreso) {
		this.predeterminadaEnOrdenesDeIngreso = predeterminadaEnOrdenesDeIngreso;
	}

	public boolean isHabilitadaEnOrdenesDeSalida() {
		return habilitadaEnOrdenesDeSalida;
	}

	public void setHabilitadaEnOrdenesDeSalida(boolean habilitadaEnOrdenesDeSalida) {
		this.habilitadaEnOrdenesDeSalida = habilitadaEnOrdenesDeSalida;
	}

	public boolean isPredeterminadaEnOrdenesDeSalida() {
		return predeterminadaEnOrdenesDeSalida;
	}

	public void setPredeterminadaEnOrdenesDeSalida(boolean predeterminadaEnOrdenesDeSalida) {
		this.predeterminadaEnOrdenesDeSalida = predeterminadaEnOrdenesDeSalida;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProductoUnidadAssociation [");
		if (producto != null)
			builder.append("producto=").append(producto).append(", ");
		if (unidad != null)
			builder.append("unidad=").append(unidad).append(", ");
		builder.append("unidadBase=").append(unidadBase).append(", nivel=").append(nivel).append(", factorConversion=")
				.append(factorConversion).append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((producto == null) ? 0 : producto.hashCode());
		result = prime * result + ((unidad == null) ? 0 : unidad.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductoUnidadAssociation other = (ProductoUnidadAssociation) obj;
		if (producto == null) {
			if (other.producto != null)
				return false;
		} else if (!producto.equals(other.producto))
			return false;
		if (unidad == null) {
			if (other.unidad != null)
				return false;
		} else if (!unidad.equals(other.unidad))
			return false;
		return true;
	}
}