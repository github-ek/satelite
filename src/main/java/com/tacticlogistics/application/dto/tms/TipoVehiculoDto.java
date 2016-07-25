package com.tacticlogistics.application.dto.tms;

public class TipoVehiculoDto {
	private final Integer id;
	private String codigo;
	private String texto;
	private boolean requiereRemolque;
	private boolean activo;

	public Integer getId() {
		return id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
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

	public TipoVehiculoDto(Integer id, String codigo, String texto, boolean requiereRemolque, boolean activo) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.texto = texto;
		this.requiereRemolque = requiereRemolque;
		this.activo = activo;
	}

	
}
