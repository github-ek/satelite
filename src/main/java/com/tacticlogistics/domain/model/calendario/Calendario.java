package com.tacticlogistics.domain.model.calendario;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Calendario", catalog = "core")
public class Calendario implements Comparable<Calendario> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_calendario", unique = true, nullable = false)
	private Integer id;

	@Column(nullable = false, columnDefinition = "DATE", unique = true)
	private LocalDate fecha;

	@Enumerated(EnumType.STRING)
	@Column(name = "id_dia_semana", nullable = false, length = 20)
	private DiaDeSemanaType dia;

	private boolean diaHabil;

	@Column(nullable = false, columnDefinition = "DATETIME2(0)")
	private LocalDateTime fechaActualizacion;

	@Column(nullable = false, length = 50)
	private String usuarioActualizacion;

	protected Calendario() {
		super();
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public DiaDeSemanaType getDia() {
		return dia;
	}

	public void setDia(DiaDeSemanaType dia) {
		this.dia = dia;
	}

	public boolean isDiaHabil() {
		return diaHabil;
	}

	public void setDiaHabil(boolean diaHabil) {
		this.diaHabil = diaHabil;
	}

	public LocalDateTime getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public String getUsuarioActualizacion() {
		return usuarioActualizacion;
	}

	public void setUsuarioActualizacion(String usuarioActualizacion) {
		this.usuarioActualizacion = usuarioActualizacion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dia == null) ? 0 : dia.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Calendario other = (Calendario) obj;
		if (dia != other.dia)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Calendario [");
		if (fecha != null)
			builder.append("fecha=").append(fecha).append(", ");
		if (dia != null)
			builder.append("dia=").append(dia).append(", ");
		builder.append("diaHabil=").append(diaHabil).append("]");
		return builder.toString();
	}

	@Override
	public int compareTo(Calendario o) {
		return this.dia.compareTo(getDia());
	}
}
