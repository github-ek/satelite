package com.tacticlogistics.application.dto.wms;

public class EstadoInventarioDto {
	private final Integer id;
	private String codigo;
	private String texto;
	private int ordinal;
	private boolean activo;

	/**
	 * @param id
	 * @param codigo
	 * @param texto
	 * @param ordinal
	 * @param activo
	 */
	public EstadoInventarioDto(Integer id, String codigo, String texto, int ordinal, boolean activo) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.texto = texto;
		this.ordinal = ordinal;
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

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
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
}
