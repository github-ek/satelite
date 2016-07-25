package com.tacticlogistics.domain.model.entregas;

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
@Table(name = "EstadosEntrega", catalog = "ordenes")
public class EstadoEntrega implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Enumerated(EnumType.STRING)
	@Column(name = "id_estado_entrega", nullable = false, length = 20, unique = true)
	private EstadoEntregaType EstadoEntrega;

	@Column(nullable = false, length = 200)
	private String descripcion;

	private int ordinal;

	private boolean activo;

	@Column(nullable = false, columnDefinition = "DATETIME2(0)")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaActualizacion;

	@Column(nullable = false, length = 50)
	private String usuarioActualizacion;

	protected EstadoEntrega() {

	}

	public EstadoEntregaType getEstadoEntrega() {
		return EstadoEntrega;
	}

	public void setEstadoEntrega(EstadoEntregaType estadoEntrega) {
		EstadoEntrega = estadoEntrega;
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
		result = prime * result + ((EstadoEntrega == null) ? 0 : EstadoEntrega.hashCode());
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
		EstadoEntrega other = (EstadoEntrega) obj;
		if (EstadoEntrega != other.EstadoEntrega)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EstadoEntrega [");
		if (EstadoEntrega != null)
			builder.append("EstadoEntrega=").append(EstadoEntrega).append(", ");
		if (descripcion != null)
			builder.append("descripcion=").append(descripcion).append(", ");
		builder.append("ordinal=").append(ordinal).append("]");
		return builder.toString();
	}

}
