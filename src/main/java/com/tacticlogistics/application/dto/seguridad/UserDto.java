package com.tacticlogistics.application.dto.seguridad;

public class UserDto {
	private final Integer id;
	private String usuario;
	private String pwd;
	private String nombres;
	private String apellidos;
	private String email;
	private boolean activo;

	public UserDto(Integer id, String usuario, String pwd, String nombres, String apellidos, String email, boolean activo) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.pwd = pwd;
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.email = email;
		this.activo = activo;
	}

	public Integer getId() {
		return id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
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
}
