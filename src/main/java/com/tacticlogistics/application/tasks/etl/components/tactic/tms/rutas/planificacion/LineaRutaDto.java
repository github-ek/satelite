package com.tacticlogistics.application.tasks.etl.components.tactic.tms.rutas.planificacion;

import java.time.LocalTime;

public class LineaRutaDto {
	private Integer ordenId;
	private Integer secuencia;
	private LocalTime horaEstimada;

	
	public LineaRutaDto() {
		this(null,null,null);
	}

	public LineaRutaDto(Integer ordenId, Integer secuencia, LocalTime horaEstimada) {
		super();
		this.setOrdenId(ordenId);
		this.setSecuencia(secuencia);
		this.setHoraEstimada(horaEstimada);
	}

	public Integer getOrdenId() {
		return ordenId;
	}

	public Integer getSecuencia() {
		return secuencia;
	}

	public LocalTime getHoraEstimada() {
		return horaEstimada;
	}

	protected void setOrdenId(Integer ordenId) {
		this.ordenId = ordenId;
	}

	protected void setSecuencia(Integer secuencia) {
		this.secuencia = secuencia;
	}

	protected void setHoraEstimada(LocalTime timeValue) {
		this.horaEstimada = timeValue;
	}

	@Override
	public String toString() {
		return "LineaRutaDto [" + (ordenId != null ? "ordenId=" + ordenId + ", " : "")
				+ (secuencia != null ? "secuencia=" + secuencia + ", " : "")
				+ (horaEstimada != null ? "horaEstimada=" + horaEstimada : "") + "]";
	}
}