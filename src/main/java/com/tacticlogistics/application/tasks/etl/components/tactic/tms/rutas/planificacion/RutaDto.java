package com.tacticlogistics.application.tasks.etl.components.tactic.tms.rutas.planificacion;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class RutaDto {
	private String placa;
	private Time horaCitaCargue;
	private List<LineaRutaDto> lineas;

	public RutaDto(String placa, Time horaCitaCargue) {
		super();
		this.setPlaca(placa);
		this.setHoraCitaCargue(horaCitaCargue);
		this.lineas = new ArrayList<>();
	}

	public String getPlaca() {
		return placa;
	}

	public Time getHoraCitaCargue() {
		return horaCitaCargue;
	}

	protected void setPlaca(String placa) {
		this.placa = placa;
	}

	protected void setHoraCitaCargue(Time horaCitaCargue) {
		this.horaCitaCargue = horaCitaCargue;
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
