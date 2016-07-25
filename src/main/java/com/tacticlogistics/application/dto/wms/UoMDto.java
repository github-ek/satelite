package com.tacticlogistics.application.dto.wms;

import java.util.Map;
import java.util.TreeMap;

public class UoMDto {
	private final Long id;
	private String codigo;
	private String texto;
	private int ordinal;
	private boolean activo;
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
	public Long getId() {
		return id;
	}
	/**
	 * @param id
	 * @param codigo
	 * @param texto
	 * @param ordinal
	 * @param activo
	 */
	public UoMDto(Long id, String codigo, String texto, int ordinal, boolean activo) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.texto = texto;
		this.ordinal = ordinal;
		this.activo = activo;
	}

	public static Map<String,UoMDto> getUoM(){
		Map<String,UoMDto> map = new TreeMap<String,UoMDto>();
		long id=0;
		UoMDto o;
		
		o = new UoMDto(id++, "UN", "UNIDAD", 1,true) ;map.put(o.getCodigo(), o);
		o = new UoMDto(id++, "CJ", "CAJA", 2,true) ;map.put(o.getCodigo(), o);
		o = new UoMDto(id++, "PA", "PALLET", 3,true) ;map.put(o.getCodigo(), o);
		o = new UoMDto(id++, "KL", "KILO", 11,true) ;map.put(o.getCodigo(), o);
		o = new UoMDto(id++, "BU", "BULTO", 12,true) ;map.put(o.getCodigo(), o);
		o = new UoMDto(id++, "BG", "BOLSA GRANDE", 13,true) ;map.put(o.getCodigo(), o);
		
		return map;
	};
}
