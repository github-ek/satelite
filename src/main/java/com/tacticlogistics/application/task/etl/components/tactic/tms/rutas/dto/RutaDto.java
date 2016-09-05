package com.tacticlogistics.application.task.etl.components.tactic.tms.rutas.dto;

import static com.tacticlogistics.infrastructure.services.Basic.coalesce;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RutaDto {
	@JsonProperty("ruta")
	private String identificadorMovil;
	@JsonProperty("primaria")
	private boolean distribucionPrimaria;
	@JsonIgnore
	private Time horaCitaCargue;
	@JsonProperty("clientes")
	private List<LineaRutaDto> lineas = new ArrayList<>();
	private List<DocumentoDto> documentos = new ArrayList<>();

	public RutaDto() {
		this("", false, null);
	}

	public RutaDto(String identificadorMovil, boolean distribucionPrimaria, Time horaCitaCargue) {
		this.setIdentificadorMovil(identificadorMovil);
		this.setDistribucionPrimaria(distribucionPrimaria);
		this.setHoraCitaCargue(horaCitaCargue);
	}

	public String getIdentificadorMovil() {
		return identificadorMovil;
	}

	public boolean isDistribucionPrimaria() {
		return distribucionPrimaria;
	}

	public Time getHoraCitaCargue() {
		return horaCitaCargue;
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

	protected void setIdentificadorMovil(String value) {
		this.identificadorMovil = coalesce(value, "").toLowerCase();
	}

	protected void setDistribucionPrimaria(boolean value) {
		this.distribucionPrimaria = value;
	}

	protected void setHoraCitaCargue(Time value) {
		this.horaCitaCargue = value;
	}

	@Override
	public String toString() {
		return "RutaDto [" + (identificadorMovil != null ? "identificadorMovil=" + identificadorMovil + ", " : "")
				+ "distribucionPrimaria=" + distribucionPrimaria + ", "
				+ (horaCitaCargue != null ? "horaCitaCargue=" + horaCitaCargue + ", " : "")
				+ (lineas != null ? "lineas=" + lineas + ", " : "")
				+ (documentos != null ? "documentos=" + documentos : "") + "]";
	}
}