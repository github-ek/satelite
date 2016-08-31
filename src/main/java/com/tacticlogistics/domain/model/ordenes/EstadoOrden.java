package com.tacticlogistics.domain.model.ordenes;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "EstadosOrden", catalog = "ordenes")
public class EstadoOrden implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Enumerated(EnumType.STRING)
	@Column(name = "id_estado_orden", nullable = false, length = 20, unique = true)
	private com.tacticlogistics.domain.model.oms.EstadoOrdenType EstadoOrden;

	@Column(nullable = false, length = 200)
	private String descripcion;

	private int ordinal;

	private boolean activo;

	@Column(nullable = false, columnDefinition = "DATETIME2(0)")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaActualizacion;

	@Column(nullable = false, length = 50)
	private String usuarioActualizacion;

	public EstadoOrden() {

	}

	public com.tacticlogistics.domain.model.oms.EstadoOrdenType getEstadoOrden() {
		return EstadoOrden;
	}

	public void setEstadoOrden(com.tacticlogistics.domain.model.oms.EstadoOrdenType estadoOrden) {
		EstadoOrden = estadoOrden;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((EstadoOrden == null) ? 0 : EstadoOrden.hashCode());
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
		EstadoOrden other = (EstadoOrden) obj;
		if (EstadoOrden != other.EstadoOrden)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EstadoOrden [");
		if (EstadoOrden != null)
			builder.append("EstadoOrden=").append(EstadoOrden).append(", ");
		if (descripcion != null)
			builder.append("descripcion=").append(descripcion).append(", ");
		builder.append("ordinal=").append(ordinal).append("]");
		return builder.toString();
	}
}
