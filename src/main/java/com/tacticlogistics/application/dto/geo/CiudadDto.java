package com.tacticlogistics.application.dto.geo;

public class CiudadDto {
	private final Integer id;
	private String codigo;
	private String texto;
	private String nombreAlterno;
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

	public String getNombreAlterno() {
		return nombreAlterno;
	}

	public void setNombreAlterno(String nombreAlterno) {
		this.nombreAlterno = nombreAlterno;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public CiudadDto(Integer id, String codigo, String texto, String nombreAlterno, boolean activo) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.texto = texto;
		this.nombreAlterno = nombreAlterno;
		this.activo = activo;
	}
}
