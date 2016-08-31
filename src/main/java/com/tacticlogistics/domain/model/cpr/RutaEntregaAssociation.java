package com.tacticlogistics.domain.model.cpr;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tacticlogistics.domain.model.entregas.Entrega;
import com.tacticlogistics.domain.model.entregas.EstadoEntregaType;
import com.tacticlogistics.domain.model.tms.Ruta;

@Entity
@Table(name = "rutas_entregas", catalog = "cpr")
public class RutaEntregaAssociation implements Serializable {
	private static final long serialVersionUID = 1L;

	private int consecutivoRuta;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((entrega == null) ? 0 : entrega.hashCode());
		result = prime * result + ((ruta == null) ? 0 : ruta.hashCode());
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
		RutaEntregaAssociation other = (RutaEntregaAssociation) obj;
		if (entrega == null) {
			if (other.entrega != null)
				return false;
		} else if (!entrega.equals(other.entrega))
			return false;
		if (ruta == null) {
			if (other.ruta != null)
				return false;
		} else if (!ruta.equals(other.ruta))
			return false;
		return true;
	}

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private EstadoEntregaType estadoEntregaType;

	@Column(nullable = false, columnDefinition = "DATETIME2(0)")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaProgramadaEntrega;

	@Column(nullable = false, columnDefinition = "TIME(0)")
	private Time horaProgramadaEntrega;

	@Column(nullable = false, columnDefinition = "DATETIME2(0)")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaRealEntrega;

	@Column(nullable = false, columnDefinition = "TIME(0)")
	private Time horaRealEntrega;

	private boolean activo;

	@Column(nullable = false, columnDefinition = "DATETIME2(0)")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaActualizacion;

	@Column(nullable = false, length = 50)
	private String usuarioActualizacion;

	//---------------------------------------------------------------------------------------------------------
	@Id
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_ruta", nullable = false)
	private Ruta ruta;

	@Id
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_entrega", nullable = false)
	private Entrega entrega;

	protected RutaEntregaAssociation() {
	}

	public int getConsecutivoRuta() {
		return consecutivoRuta;
	}

	public void setConsecutivoRuta(int consecutivoRuta) {
		this.consecutivoRuta = consecutivoRuta;
	}

	public EstadoEntregaType getEstadoEntregaType() {
		return estadoEntregaType;
	}

	public void setEstadoEntregaType(EstadoEntregaType estadoEntregaType) {
		this.estadoEntregaType = estadoEntregaType;
	}

	public Date getFechaProgramadaEntrega() {
		return fechaProgramadaEntrega;
	}

	public void setFechaProgramadaEntrega(Date fechaProgramadaEntrega) {
		this.fechaProgramadaEntrega = fechaProgramadaEntrega;
	}

	public Time getHoraProgramadaEntrega() {
		return horaProgramadaEntrega;
	}

	public void setHoraProgramadaEntrega(Time horaProgramadaEntrega) {
		this.horaProgramadaEntrega = horaProgramadaEntrega;
	}

	public Date getFechaRealEntrega() {
		return fechaRealEntrega;
	}

	public void setFechaRealEntrega(Date fechaRealEntrega) {
		this.fechaRealEntrega = fechaRealEntrega;
	}

	public Time getHoraRealEntrega() {
		return horaRealEntrega;
	}

	public void setHoraRealEntrega(Time horaRealEntrega) {
		this.horaRealEntrega = horaRealEntrega;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public String getUsuarioActualizacion() {
		return usuarioActualizacion;
	}

	public void setUsuarioActualizacion(String usuarioActualizacion) {
		this.usuarioActualizacion = usuarioActualizacion;
	}

	public Ruta getRuta() {
		return ruta;
	}

	public void setRuta(Ruta ruta) {
		this.ruta = ruta;
	}

	public Entrega getEntrega() {
		return entrega;
	}

	public void setEntrega(Entrega entrega) {
		this.entrega = entrega;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RutaEntregaAssociation [consecutivoRuta=").append(consecutivoRuta).append(", ");
		if (estadoEntregaType != null)
			builder.append("estadoEntregaType=").append(estadoEntregaType).append(", ");
		if (fechaProgramadaEntrega != null)
			builder.append("fechaProgramadaEntrega=").append(fechaProgramadaEntrega).append(", ");
		if (horaProgramadaEntrega != null)
			builder.append("horaProgramadaEntrega=").append(horaProgramadaEntrega).append(", ");
		if (fechaRealEntrega != null)
			builder.append("fechaRealEntrega=").append(fechaRealEntrega).append(", ");
		if (horaRealEntrega != null)
			builder.append("horaRealEntrega=").append(horaRealEntrega).append(", ");
		builder.append("activo=").append(activo).append(", ");
		if (fechaActualizacion != null)
			builder.append("fechaActualizacion=").append(fechaActualizacion).append(", ");
		if (usuarioActualizacion != null)
			builder.append("usuarioActualizacion=").append(usuarioActualizacion).append(", ");
		if (ruta != null)
			builder.append("ruta=").append(ruta).append(", ");
		if (entrega != null)
			builder.append("entrega=").append(entrega);
		builder.append("]");
		return builder.toString();
	}
}