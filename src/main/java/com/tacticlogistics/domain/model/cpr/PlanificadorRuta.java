package com.tacticlogistics.domain.model.cpr;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tacticlogistics.domain.model.seguridad.Usuario;

@Entity
@Table(name = "PlanificadoresRutas", catalog = "cpr")
public class PlanificadorRuta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_planificador_ruta", unique = true, nullable = false)
	private Integer id;

	private boolean activo;
	
	@Column(nullable = false, columnDefinition = "DATETIME2(0)")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaActualizacion;

	@Column(nullable = false, length = 50)
	private String usuarioActualizacion;

	//---------------------------------------------------------------------------------------------------------
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario", nullable = false)
	private Usuario usuario;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_centro_planeacion_ruta", nullable = true)
	private CentroPlaneacionRuta centroPlaneacionRuta;

	protected PlanificadorRuta() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public CentroPlaneacionRuta getCentroPlaneacionRuta() {
		return centroPlaneacionRuta;
	}

	public void setCentroPlaneacionRuta(CentroPlaneacionRuta centroPlaneacionRuta) {
		this.centroPlaneacionRuta = centroPlaneacionRuta;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PlanificadorRuta [");
		if (id != null)
			builder.append("id=").append(id).append(", ");
		builder.append("activo=").append(activo).append(", ");
		if (fechaActualizacion != null)
			builder.append("fechaActualizacion=").append(fechaActualizacion).append(", ");
		if (usuarioActualizacion != null)
			builder.append("usuarioActualizacion=").append(usuarioActualizacion).append(", ");
		if (usuario != null)
			builder.append("usuario=").append(usuario).append(", ");
		if (centroPlaneacionRuta != null)
			builder.append("centroPlaneacionRuta=").append(centroPlaneacionRuta);
		builder.append("]");
		return builder.toString();
	}
	
}
