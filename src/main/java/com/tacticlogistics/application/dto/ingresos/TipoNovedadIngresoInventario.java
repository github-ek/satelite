package com.tacticlogistics.application.dto.ingresos;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TiposNovedadIngresoInventario", catalog = "wms")
public class TipoNovedadIngresoInventario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_tipo_novedad_ingreso_inventario", unique = true, nullable = false)
	private Integer id;

	@Column(nullable = false, length = 50, unique = true)
	private String codigo;
	
	@Column(nullable = false, length = 50, unique = true)
	private String nombre;
	
	private int ordinal;
	
	private boolean activo;
	
	@Column(nullable = false, columnDefinition = "DATETIME2(0)")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaActualizacion;
	
	@Column(nullable = false, length = 50)
	private String usuarioActualizacion;

	protected TipoNovedadIngresoInventario() {

	}

	public TipoNovedadIngresoInventario(Integer id, String codigo, String nombre, int ordinal, boolean activo,
			Date fechaActualizacion, String usuarioActualizacion) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.nombre = nombre;
		this.ordinal = ordinal;
		this.activo = activo;
		this.fechaActualizacion = fechaActualizacion;
		this.usuarioActualizacion = usuarioActualizacion;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getOrdinal() {
		return ordinal;
	}

	public void setOrdinal(int ordinal) {
		this.ordinal = ordinal;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
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

}