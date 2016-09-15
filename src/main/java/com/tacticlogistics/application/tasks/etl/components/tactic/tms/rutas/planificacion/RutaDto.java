package com.tacticlogistics.application.tasks.etl.components.tactic.tms.rutas.planificacion;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class RutaDto {
	private String placa;
	private LocalTime horaCitaCargue;
	private List<LineaRutaDto> lineas;

	public RutaDto(String placa, LocalTime timeValue) {
		super();
		this.setPlaca(placa);
		this.setHoraCitaCargue(timeValue);
		this.lineas = new ArrayList<>();
	}

	public String getPlaca() {
		return placa;
	}

	public LocalTime getHoraCitaCargue() {
		return horaCitaCargue;
	}

	protected void setPlaca(String placa) {
		this.placa = placa;
	}

	protected void setHoraCitaCargue(LocalTime timeValue) {
		this.horaCitaCargue = timeValue;
	}

	protected List<LineaRutaDto> getLineas() {
		return lineas;
	}

	@Override
	public String toString() {
		return "RutaDto [" + (placa != null ? "placa=" + placa + ", " : "")
				+ (horaCitaCargue != null ? "horaCitaCargue=" + horaCitaCargue : "") + "]";
	}
	
	
}
