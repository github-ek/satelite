package com.tacticlogistics.application.tasks.schedules.tms.rutas;

import static com.tacticlogistics.infrastructure.services.Basic.coalesce;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tacticlogistics.domain.model.tms.EstadoRutaType;
import com.tacticlogistics.domain.model.tms.EstadoSincronizacionRutaType;

public class RutaDto {
	@JsonIgnore
	private Integer rutaId;
	@JsonIgnore
	private EstadoRutaType estadoRuta;
	@JsonIgnore
	private EstadoSincronizacionRutaType estadoSincronizacionRuta;
	@JsonIgnore
	private String errorSincronizacion;
	@JsonIgnore
	private Integer intentosSincronizacion;
	@JsonIgnore
	private String placa;
	@JsonProperty("ruta")
	private String movil;
	@JsonProperty("primaria")
	private boolean distribucionPrimaria;

	@JsonProperty("clientes")
	private List<LineaRutaDto> lineas = new ArrayList<>();
	@JsonProperty("documentos")
	private List<DocumentoDto> documentos = new ArrayList<>();

	public RutaDto() {
		super();
		this.setRutaId(null);
		this.setEstadoRuta(null);
		this.setEstadoSincronizacionRuta(null);
		this.setIntentosSincronizacion(null);
		this.setPlaca("");
		this.setMovil("");
		this.setDistribucionPrimaria(false);
	}

	public Integer getRutaId() {
		return rutaId;
	}

	public EstadoRutaType getEstadoRuta() {
		return estadoRuta;
	}

	public EstadoSincronizacionRutaType getEstadoSincronizacionRuta() {
		return estadoSincronizacionRuta;
	}
	
	public String getErrorSincronizacion() {
		return errorSincronizacion;
	}

	public Integer getIntentosSincronizacion() {
		return intentosSincronizacion;
	}

	public String getPlaca() {
		return placa;
	}

	public String getMovil() {
		return movil;
	}

	public boolean isDistribucionPrimaria() {
		return distribucionPrimaria;
	}

	public List<LineaRutaDto> getLineas() {
		if (lineas == null) {
			lineas = new ArrayList<>();
		}
		return lineas;
	}

	public List<DocumentoDto> getDocumentos() {
		if (documentos == null) {
			documentos = new ArrayList<>();
		}
		return documentos;
	}

	public void setRutaId(Integer rutaId) {
		this.rutaId = rutaId;
	}

	public void setEstadoRuta(EstadoRutaType estadoRuta) {
		this.estadoRuta = estadoRuta;
	}

	public void setEstadoSincronizacionRuta(EstadoSincronizacionRutaType estadoSincronizacionRuta) {
		this.estadoSincronizacionRuta = estadoSincronizacionRuta;
	}

	protected void setErrorSincronizacion(String errorSincronizacion) {
		this.errorSincronizacion = errorSincronizacion;
	}

	public void setIntentosSincronizacion(Integer intentosSincronizacion) {
		this.intentosSincronizacion = intentosSincronizacion;
	}

	public void setPlaca(String value) {
		this.placa = coalesce(value, "").toLowerCase();
	}

	public void setMovil(String value) {
		this.movil = coalesce(value, "").toLowerCase();
	}

	public void setDistribucionPrimaria(boolean value) {
		this.distribucionPrimaria = value;
	}

	@Override
	public String toString() {
		return "RutaDto [rutaId=" + rutaId + ", " + (estadoRuta != null ? "estadoRuta=" + estadoRuta + ", " : "")
				+ (estadoSincronizacionRuta != null ? "estadoSincronizacionRuta=" + estadoSincronizacionRuta + ", "
						: "")
				+ "intentosSincronizacion=" + intentosSincronizacion + ", "
				+ (placa != null ? "placa=" + placa + ", " : "") + (movil != null ? "movil=" + movil + ", " : "")
				+ "distribucionPrimaria=" + distribucionPrimaria + ", "
				+ (lineas != null ? "lineas=" + lineas + ", " : "")
				+ (documentos != null ? "documentos=" + documentos : "") + "]";
	}
}