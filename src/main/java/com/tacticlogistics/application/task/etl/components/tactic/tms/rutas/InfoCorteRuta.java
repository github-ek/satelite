package com.tacticlogistics.application.task.etl.components.tactic.tms.rutas;

import java.util.Date;

public class InfoCorteRuta {

	private int ordenId;
	private Date fechaRuta;
	private Integer corteRutaId;
	private String usuarioCorteRuta;
	private Integer bodegaOrigenId;
	private String bodegaOrigenCodigo;
	private Integer ciudadOrigenId;
	private String ciudadOrigenNombre;
	private String origenDireccion;

	public InfoCorteRuta(int ordenId, Date fechaRuta, Integer corteRutaId, String usuarioCorteRuta,
			Integer bodegaOrigenId, String bodegaOrigenCodigo, Integer ciudadOrigenId, String ciudadOrigenNombre,
			String origenDireccion) {
		super();
		this.ordenId = ordenId;
		this.fechaRuta = fechaRuta;
		this.corteRutaId = corteRutaId;
		this.usuarioCorteRuta = usuarioCorteRuta;
		this.bodegaOrigenId = bodegaOrigenId;
		this.bodegaOrigenCodigo = bodegaOrigenCodigo;
		this.ciudadOrigenId = ciudadOrigenId;
		this.ciudadOrigenNombre = ciudadOrigenNombre;
		this.origenDireccion = origenDireccion;
	}

	public int getOrdenId() {
		return ordenId;
	}

	public Date getFechaRuta() {
		return fechaRuta;
	}

	public Integer getCorteRutaId() {
		return corteRutaId;
	}

	public String getUsuarioCorteRuta() {
		return usuarioCorteRuta;
	}

	public Integer getBodegaOrigenId() {
		return bodegaOrigenId;
	}

	public String getBodegaOrigenCodigo() {
		return bodegaOrigenCodigo;
	}

	public Integer getCiudadOrigenId() {
		return ciudadOrigenId;
	}

	public String getCiudadOrigenNombre() {
		return ciudadOrigenNombre;
	}

	public String getOrigenDireccion() {
		return origenDireccion;
	}

}
