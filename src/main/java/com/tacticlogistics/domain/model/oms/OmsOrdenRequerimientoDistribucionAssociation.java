package com.tacticlogistics.domain.model.oms;

import static com.tacticlogistics.infrastructure.services.Basic.coalesce;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//@Entity
//@Table(name = "ordenes_requerimientos_distribucion", catalog = "oms")
@Embeddable
public class OmsOrdenRequerimientoDistribucionAssociation implements Serializable {
	private static final long serialVersionUID = 1L;

	//@Id
	@Column(name = "id_requerimiento_distribucion", nullable = false, insertable = true, updatable = false)
	private int requerimientoDistribucionId;

	//@Id
	@Column(name = "codigo_alterno", nullable = false, length = 50)
	private String codigoAlterno;

	@Column(name = "descripcion", nullable = false, length = 200)
	private String descripcion;

	// ---------------------------------------------------------------------------------------------------------
	public OmsOrdenRequerimientoDistribucionAssociation(int requerimientoDistribucionId, String codigoAlterno,
			String descripcion) {
		super();
		this.setRequerimientoDistribucionId(requerimientoDistribucionId);
		this.setCodigoAlterno(codigoAlterno);
		this.setDescripcion(descripcion);
	}

	public int getRequerimientoDistribucionId() {
		return requerimientoDistribucionId;
	}

	public String getCodigoAlterno() {
		return codigoAlterno;
	}

	public String getDescripcion() {
		return descripcion;
	}

	// ---------------------------------------------------------------------------------------------------------
	protected OmsOrdenRequerimientoDistribucionAssociation() {
		super();
		this.setCodigoAlterno("");
		this.setDescripcion("");
	}

	protected void setRequerimientoDistribucionId(int value) {
		this.requerimientoDistribucionId = value;
	}

	protected void setCodigoAlterno(String value) {
		this.codigoAlterno = coalesce(value, "");
	}

	protected void setDescripcion(String value) {
		this.descripcion = coalesce(value, "");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigoAlterno == null) ? 0 : codigoAlterno.hashCode());
		result = prime * result + requerimientoDistribucionId;
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
		OmsOrdenRequerimientoDistribucionAssociation other = (OmsOrdenRequerimientoDistribucionAssociation) obj;
		if (codigoAlterno == null) {
			if (other.codigoAlterno != null)
				return false;
		} else if (!codigoAlterno.equals(other.codigoAlterno))
			return false;
		if (requerimientoDistribucionId != other.requerimientoDistribucionId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OmsOrdenRequerimientoDistribucionAssociation [requerimientoDistribucionId="
				+ requerimientoDistribucionId + ", "
				+ (codigoAlterno != null ? "codigoAlterno=" + codigoAlterno + ", " : "")
				+ (descripcion != null ? "descripcion=" + descripcion : "") + "]";
	}
}
