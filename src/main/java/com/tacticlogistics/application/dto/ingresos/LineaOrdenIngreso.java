package com.tacticlogistics.application.dto.ingresos;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "LineasOrdenesIngreso", catalog = "ordenes")
public class LineaOrdenIngreso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_linea_orden_ingreso", unique = true, nullable = false)
	public Integer id;
	@Column(name = "id_orden_ingreso", nullable = false)
	public Integer idOrden;
	public String producto;
	@javax.persistence.Transient
	public String nombreProducto;
	public Integer cantidadEsperada;
	public Integer cantidadRecibida;
	public Integer cantidadFaltantes;
	public Integer cantidadSobrante;
	public Integer cantidadAverias;
	public String unidadMedida;
	public String loteSugerido;
	public String novedades;
	public String descripcionNovedad;

	@Lob
	public String fotoNovedad;

	protected LineaOrdenIngreso() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdOrden() {
		return idOrden;
	}

	public void setIdOrden(Integer idOrden) {
		this.idOrden = idOrden;
	}

	public String getProducto() {
		return producto;
	}

	public void setProducto(String producto) {
		this.producto = producto;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public Integer getCantidadEsperada() {
		return cantidadEsperada;
	}

	public void setCantidadEsperada(Integer cantidadEsperada) {
		this.cantidadEsperada = cantidadEsperada;
	}

	public Integer getCantidadRecibida() {
		return cantidadRecibida;
	}

	public void setCantidadRecibida(Integer cantidadRecibida) {
		this.cantidadRecibida = cantidadRecibida;
	}

	public Integer getCantidadFaltantes() {
		return cantidadFaltantes;
	}

	public void setCantidadFaltantes(Integer cantidadFaltantes) {
		this.cantidadFaltantes = cantidadFaltantes;
	}

	public Integer getCantidadSobrante() {
		return cantidadSobrante;
	}

	public void setCantidadSobrante(Integer cantidadSobrante) {
		this.cantidadSobrante = cantidadSobrante;
	}

	public Integer getCantidadAverias() {
		return cantidadAverias;
	}
	
	@javax.persistence.Transient
	public Integer getCantidadDisponible() {
		return  (cantidadRecibida==null?0:cantidadRecibida) - (cantidadAverias==null?0:cantidadAverias);
	}

	public void setCantidadAverias(Integer cantidadAverias) {
		this.cantidadAverias = cantidadAverias;
	}

	public String getUnidadMedida() {
		return unidadMedida;
	}

	public void setUnidadMedida(String unidadMedida) {
		this.unidadMedida = unidadMedida;
	}

	public String getLoteSugerido() {
		return loteSugerido;
	}

	public void setLoteSugerido(String loteSugerido) {
		this.loteSugerido = loteSugerido;
	}

	public String getNovedades() {
		return novedades;
	}

	public void setNovedades(String novedades) {
		this.novedades = novedades;
	}

	public String getDescripcionNovedad() {
		return descripcionNovedad;
	}

	public void setDescripcionNovedad(String descripcionNovedad) {
		this.descripcionNovedad = descripcionNovedad;
	}

	public String getFotoNovedad() {
		return fotoNovedad;
	}

	public void setFotoNovedad(String fotoNovedad) {
		this.fotoNovedad = fotoNovedad;
	}


}
