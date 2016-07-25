package com.tacticlogistics.domain.model.seguridad;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Usuarios", catalog = "seguridad")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario", unique = true, nullable = false)
	private Integer id;

	@Column(nullable = false, length = 20, unique = true)
	private String usuario;

	@Column(nullable = false, length = 20)
	private String password;

	@Column(nullable = false, length = 50)
	private String nombres;

	@Column(nullable = false, length = 50)
	private String apellidos;

	@Column(nullable = false, length = 50)
	private String email;
	private boolean activo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, columnDefinition = "DATETIME2(0)")
	private Date fechaActualizacion;

	@Column(nullable = false, length = 50)
	private String usuarioActualizacion;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
	private Set<UsuarioBodegaAssociation> usuarioBodegaAssociation;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
	private Set<UsuarioClienteAssociation> usuarioClienteAssociation;

	protected Usuario() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public Set<UsuarioBodegaAssociation> getUsuarioBodegaAssociation() {
		return usuarioBodegaAssociation;
	}

	public void setUsuarioBodegaAssociation(Set<UsuarioBodegaAssociation> usuarioBodegaAssociation) {
		this.usuarioBodegaAssociation = usuarioBodegaAssociation;
	}

	public Set<UsuarioClienteAssociation> getUsuarioClienteAssociation() {
		return usuarioClienteAssociation;
	}

	public void setUsuarioClienteAssociation(Set<UsuarioClienteAssociation> usuarioClienteAssociation) {
		this.usuarioClienteAssociation = usuarioClienteAssociation;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
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
		Usuario other = (Usuario) obj;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}
}
