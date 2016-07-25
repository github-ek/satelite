package com.tacticlogistics.domain.model.tms;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tacticlogistics.domain.model.common.IdentificacionType;

@Entity
@Table(name = "Conductores", catalog = "tms")
public class Conductor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_conductor", unique = true, nullable = false)
	private Integer id;

	@Column(nullable = false, length = 20, unique = true)
	private String codigo;

	@Column(nullable = false, length = 2)
	@Enumerated(EnumType.STRING)
	private IdentificacionType identificacionType;

	@Column(nullable = false, length = 20, unique = true)
	private String numeroIdentificacion;

	@Column(nullable = false, length = 100)
	private String nombres;

	@Column(nullable = false, length = 100)
	private String apellidos;

	private boolean activo;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_transportadora", nullable = false)
	private Transportadora transportadora;

	@Column(nullable = false, columnDefinition = "DATETIME2(0)")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaActualizacion;

	@Column(nullable = false, length = 50)
	private String usuarioActualizacion;

	protected Conductor() {

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

	public IdentificacionType getIdentificacionType() {
		return identificacionType;
	}

	public void setIdentificacionType(IdentificacionType identificacionType) {
		this.identificacionType = identificacionType;
	}

	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public Transportadora getTransportadora() {
		return transportadora;
	}

	public void setTransportadora(Transportadora transportadora) {
		this.transportadora = transportadora;
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
		builder.append("Conductor [");
		if (id != null)
			builder.append("id=").append(id).append(", ");
		if (codigo != null)
			builder.append("codigo=").append(codigo).append(", ");
		if (identificacionType != null)
			builder.append("identificacionType=").append(identificacionType).append(", ");
		if (numeroIdentificacion != null)
			builder.append("numeroIdentificacion=").append(numeroIdentificacion).append(", ");
		if (nombres != null)
			builder.append("nombres=").append(nombres).append(", ");
		if (apellidos != null)
			builder.append("apellidos=").append(apellidos).append(", ");
		builder.append("activo=").append(activo).append(", ");
		if (transportadora != null)
			builder.append("transportadora=").append(transportadora).append(", ");
		if (fechaActualizacion != null)
			builder.append("fechaActualizacion=").append(fechaActualizacion).append(", ");
		if (usuarioActualizacion != null)
			builder.append("usuarioActualizacion=").append(usuarioActualizacion);
		builder.append("]");
		return builder.toString();
	}
}
