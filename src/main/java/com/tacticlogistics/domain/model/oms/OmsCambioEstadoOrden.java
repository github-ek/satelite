package com.tacticlogistics.domain.model.oms;

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
@Table(name = "CambiosEstadoOrden", catalog = "oms")
public class OmsCambioEstadoOrden implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_cambio_estado_orden", unique = true, nullable = false)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_orden", nullable = false)
	private OmsOrden orden;

	@Enumerated(EnumType.STRING)
	@Column(name = "id_estado_orden", nullable = false, length = 20)
	private EstadoOrdenType estadoOrden;
	
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
	protected OmsCambioEstadoOrden(){
		
	}

	//---------------------------------------------------------------------------------------------------------
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public OmsOrden getOrden() {
		return orden;
	}

	public void setOrden(OmsOrden orden) {
		this.orden = orden;
	}

	public EstadoOrdenType getEstadoOrden() {
		return estadoOrden;
	}

	public void setEstadoOrden(EstadoOrdenType estadoOrden) {
		this.estadoOrden = estadoOrden;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CambioEstadoOrden [");
		if (id != null)
			builder.append("id=").append(id).append(", ");
		if (orden != null)
			builder.append("orden=").append(orden).append(", ");
		if (estadoOrden != null)
			builder.append("estadoOrden=").append(estadoOrden).append(", ");
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
		result = prime * result + ((estadoOrden == null) ? 0 : estadoOrden.hashCode());
		result = prime * result + ((fechaDesde == null) ? 0 : fechaDesde.hashCode());
		result = prime * result + ((fechaHasta == null) ? 0 : fechaHasta.hashCode());
		result = prime * result + ((orden == null) ? 0 : orden.hashCode());
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
		OmsCambioEstadoOrden other = (OmsCambioEstadoOrden) obj;
		if (estadoOrden != other.estadoOrden)
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
		if (orden == null) {
			if (other.orden != null)
				return false;
		} else if (!orden.equals(other.orden))
			return false;
		return true;
	}

	//---------------------------------------------------------------------------------------------------------

	
}

