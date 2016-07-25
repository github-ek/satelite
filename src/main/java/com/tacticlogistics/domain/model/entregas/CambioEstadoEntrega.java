package com.tacticlogistics.domain.model.entregas;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "CambiosEstadoEntrega", catalog = "ordenes")
public class CambioEstadoEntrega implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_cambio_estado_entrega", unique = true, nullable = false)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_entrega", nullable = false)
	private Entrega entrega;

	@Enumerated(EnumType.STRING)
	@Column(name = "id_estado_entrega", nullable = false, length = 20)
	private EstadoEntregaType estadoEntrega;
	
	@Column(nullable = false, columnDefinition = "DATETIME2(0)")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaDesde;

	@Column(nullable = false, length = 50)
	private String usuarioDesde;

	@Column(nullable = false, columnDefinition = "DATETIME2(0)")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaHasta;

	@Column(nullable = false, length = 50)
	private String usuarioHasta;

	@Column(nullable = false, columnDefinition = "DATETIME2(0)")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaActualizacion;

	@Column(nullable = false, length = 50)
	private String usuarioActualizacion;

	//---------------------------------------------------------------------------------------------------------
	protected CambioEstadoEntrega(){
		
	}
	
	//---------------------------------------------------------------------------------------------------------
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Entrega getEntrega() {
		return entrega;
	}

	public void setEntrega(Entrega entrega) {
		this.entrega = entrega;
	}

	public EstadoEntregaType getEstadoEntrega() {
		return estadoEntrega;
	}

	public void setEstadoEntrega(EstadoEntregaType estadoEntrega) {
		this.estadoEntrega = estadoEntrega;
	}

	public Date getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public String getUsuarioDesde() {
		return usuarioDesde;
	}

	public void setUsuarioDesde(String usuarioDesde) {
		this.usuarioDesde = usuarioDesde;
	}

	public Date getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public String getUsuarioHasta() {
		return usuarioHasta;
	}

	public void setUsuarioHasta(String usuarioHasta) {
		this.usuarioHasta = usuarioHasta;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CambioEstadoEntrega [");
		if (id != null)
			builder.append("id=").append(id).append(", ");
		if (entrega != null)
			builder.append("entrega=").append(entrega).append(", ");
		if (estadoEntrega != null)
			builder.append("estadoEntrega=").append(estadoEntrega).append(", ");
		if (fechaDesde != null)
			builder.append("fechaDesde=").append(fechaDesde).append(", ");
		if (usuarioDesde != null)
			builder.append("usuarioDesde=").append(usuarioDesde).append(", ");
		if (fechaHasta != null)
			builder.append("fechaHasta=").append(fechaHasta).append(", ");
		if (usuarioHasta != null)
			builder.append("usuarioHasta=").append(usuarioHasta).append(", ");
		if (fechaActualizacion != null)
			builder.append("fechaActualizacion=").append(fechaActualizacion).append(", ");
		if (usuarioActualizacion != null)
			builder.append("usuarioActualizacion=").append(usuarioActualizacion);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((entrega == null) ? 0 : entrega.hashCode());
		result = prime * result + ((estadoEntrega == null) ? 0 : estadoEntrega.hashCode());
		result = prime * result + ((fechaDesde == null) ? 0 : fechaDesde.hashCode());
		result = prime * result + ((fechaHasta == null) ? 0 : fechaHasta.hashCode());
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
		CambioEstadoEntrega other = (CambioEstadoEntrega) obj;
		if (entrega == null) {
			if (other.entrega != null)
				return false;
		} else if (!entrega.equals(other.entrega))
			return false;
		if (estadoEntrega != other.estadoEntrega)
			return false;
		if (fechaDesde == null) {
			if (other.fechaDesde != null)
				return false;
		} else if (!fechaDesde.equals(other.fechaDesde))
			return false;
		if (fechaHasta == null) {
			if (other.fechaHasta != null)
				return false;
		} else if (!fechaHasta.equals(other.fechaHasta))
			return false;
		return true;
	}

	//---------------------------------------------------------------------------------------------------------

	
	
}