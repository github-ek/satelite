package com.tacticlogistics.domain.model.wms;

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
@Table(name = "EstadosInventario", catalog = "wms")
public class EstadoInventario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_estado_inventario", unique = true, nullable = false, length = 4)
	private String id;

	@Column(nullable = false, length = 4, unique = true)
	private String codigoEstadoInvsts;

	@Column(nullable = false, length = 50, unique = true)
	private String nombre;

	@Column(nullable = false, length = 200)
	private String descripcion;
	private int ordinal;
	private boolean activo;

	@Column(nullable = false, columnDefinition = "DATETIME2(0)")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaActualizacion;

	@Column(nullable = false, length = 50)
	private String usuarioActualizacion;

	protected EstadoInventario() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCodigoEstadoInvsts() {
		return codigoEstadoInvsts;
	}

	public void setCodigoEstadoInvsts(String codigoEstadoInvsts) {
		this.codigoEstadoInvsts = codigoEstadoInvsts;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

	@Override
	public String toString() {
		return "EstadoInventario [" + (id != null ? "id=" + id + ", " : "")
				+ (codigoEstadoInvsts != null ? "codigoEstadoInvsts=" + codigoEstadoInvsts + ", " : "")
				+ (nombre != null ? "nombre=" + nombre + ", " : "")
				+ (descripcion != null ? "descripcion=" + descripcion + ", " : "") + "ordinal=" + ordinal + ", activo="
				+ activo + ", " + (fechaActualizacion != null ? "fechaActualizacion=" + fechaActualizacion + ", " : "")
				+ (usuarioActualizacion != null ? "usuarioActualizacion=" + usuarioActualizacion : "") + "]";
	}

}