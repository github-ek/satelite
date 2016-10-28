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

import com.tacticlogistics.domain.model.common.valueobjects.Dimensiones;

import lombok.Data;

@Entity
@Table(name = "productos_unidades", catalog = "wms")
@Data
public class ProductoUnidadAssociation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_producto", nullable = false)
	private Producto producto;

	@Id
	private int nivel;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_unidad", nullable = false)
	private Unidad unidad;

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

	@Column(nullable = true)
	private Integer valorAproximado;
	
	public Dimensiones getDimensiones(){
		return new Dimensiones(this.getLargo(), this.getAncho(), this.getAlto(), this.getPesoBruto()); 
	} 

}