package com.tacticlogistics.application.dto.crm;

public class ClienteDto {
	private final Integer id;
	private String codigo;
	private String numeroIdentificacion;
	private String digitoVerificacion;
	private String texto;
	private boolean activo;

	public ClienteDto(Integer id, String codigo, String numeroIdentificacion, String digitoVerificacion,
			String texto, boolean activo) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.numeroIdentificacion = numeroIdentificacion;
		this.digitoVerificacion = digitoVerificacion;
		this.texto = texto;
		this.activo = activo;
	}

	public Integer getId() {
		return id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}

	public String getDigitoVerificacion() {
		return digitoVerificacion;
	}

	public void setDigitoVerificacion(String digitoVerificacion) {
		this.digitoVerificacion = digitoVerificacion;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	};

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
}
