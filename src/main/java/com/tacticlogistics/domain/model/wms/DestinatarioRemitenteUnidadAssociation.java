package com.tacticlogistics.domain.model.wms;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.tacticlogistics.domain.model.crm.DestinatarioRemitente;

@Entity
@Table(name = "destinatarios_remitentes_unidades", catalog = "wms")
public class DestinatarioRemitenteUnidadAssociation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @ManyToOne(targetEntity = DestinatarioRemitente.class, optional = false)
    @JoinColumn(name = "id_destinatario_remitente", nullable = false, insertable = true, updatable = false)
    private int destinatarioRemitenteId;

	@Id
	@Column(nullable = false, length = 20)
	private String codigoAlterno;

    @ManyToOne(targetEntity = Unidad.class, optional = false)
    @JoinColumn(name = "id_unidad", nullable = false, insertable = true, updatable = false)
    private int unidadId;

    public DestinatarioRemitenteUnidadAssociation(int destinatarioRemitenteId, String codigoAlterno, int unidadId) {
        super();
        this.destinatarioRemitenteId = destinatarioRemitenteId;
        this.codigoAlterno = codigoAlterno;
        this.unidadId = unidadId;
    }

	protected DestinatarioRemitenteUnidadAssociation() {

	}

    public int getDestinatarioRemitenteId() {
        return destinatarioRemitenteId;
	}

	public String getCodigoAlterno() {
		return codigoAlterno;
	}

    public int getUnidadId() {
        return unidadId;
	}

    protected void setDestinatarioRemitenteId(int destinatarioRemitenteId) {
        this.destinatarioRemitenteId = destinatarioRemitenteId;
	}

    protected void setCodigoAlterno(String codigoAlterno) {
        this.codigoAlterno = codigoAlterno;
	}

    protected void setUnidadId(int unidadId) {
        this.unidadId = unidadId;
	}

	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((codigoAlterno == null) ? 0 : codigoAlterno.hashCode());
        result = prime * result + destinatarioRemitenteId;
        result = prime * result + unidadId;
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
        DestinatarioRemitenteUnidadAssociation other = (DestinatarioRemitenteUnidadAssociation) obj;
        if (codigoAlterno == null) {
            if (other.codigoAlterno != null)
                return false;
        } else if (!codigoAlterno.equals(other.codigoAlterno))
            return false;
        if (destinatarioRemitenteId != other.destinatarioRemitenteId)
            return false;
        if (unidadId != other.unidadId)
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("DestinatarioRemitenteUnidadAssociation [destinatarioRemitenteId=")
                .append(destinatarioRemitenteId).append(", ");
        if (codigoAlterno != null) {
            builder.append("codigoAlterno=").append(codigoAlterno).append(", ");
        }
        builder.append("unidadId=").append(unidadId).append("]");
        return builder.toString();
    }
}