package com.tacticlogistics.domain.model.tms;

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
@Table(name = "TiposVehiculo", catalog = "tms")
public class TipoVehiculo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_tipo_vehiculo", unique = true, nullable = false)
	private Integer id;

	@Column(nullable = false, length = 20, unique = true)
	private String codigo;

	@Column(nullable = false, length = 100, unique = true)
	private String nombre;
	
	private boolean requiereRemolque;
	
	private boolean activo;

	@Column(nullable = false, columnDefinition = "DATETIME2(0)")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaActualizacion;

	@Column(nullable = false, length = 50)
	private String usuarioActualizacion;

	protected TipoVehiculo() {

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

	public boolean isRequiereRemolque() {
		return requiereRemolque;
	}

	public void setRequiereRemolque(boolean requiereRemolque) {
		this.requiereRemolque = requiereRemolque;
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
		StringBuilder builder = new StringBuilder();
		builder.append("TipoVehiculo [");
		if (id != null)
			builder.append("id=").append(id).append(", ");
		if (codigo != null)
			builder.append("codigo=").append(codigo).append(", ");
		if (nombre != null)
			builder.append("nombre=").append(nombre).append(", ");
		builder.append("requiereRemolque=").append(requiereRemolque).append(", activo=").append(activo).append(", ");
		if (fechaActualizacion != null)
			builder.append("fechaActualizacion=").append(fechaActualizacion).append(", ");
		if (usuarioActualizacion != null)
			builder.append("usuarioActualizacion=").append(usuarioActualizacion);
		builder.append("]");
		return builder.toString();
	}
}
