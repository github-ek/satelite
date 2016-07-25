package com.tacticlogistics.application.dto.wms;

import com.tacticlogistics.application.dto.geo.CiudadDto;

public class BodegaDto {
	private final Integer id;
	private String codigo;
	private String texto;
	private CiudadDto ciudad;
	private String direccion;
	private boolean activo;
	private boolean predeterminada;
	
	public boolean isPredeterminada() {
		return predeterminada;
	}
	public void setPredeterminada(boolean predeterminada) {
		this.predeterminada = predeterminada;
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
	public CiudadDto getCiudad() {
		return ciudad;
	}
	public void setCiudad(CiudadDto ciudad) {
		this.ciudad = ciudad;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	public Integer getId() {
		return id;
	}
	public BodegaDto(Integer id, String codigo, String texto, CiudadDto ciudad, String direccion,
			boolean activo, boolean predeterminada) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.texto = texto;
		this.ciudad = ciudad;
		this.direccion = direccion;
		this.activo = activo;
		this.predeterminada = predeterminada;
	}


}
