package com.tacticlogistics.domain.model.oms;

import static com.tacticlogistics.infrastructure.services.Basic.coalesce;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ordenes_requerimientos_maquila", catalog = "oms")
public class OmsOrdenRequerimientoAlistamientoAssociation implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_orden", nullable = false, insertable = true, updatable = false)
    private int ordenId;

    @Id
    @Column(name = "id_requerimiento_maquila", nullable = false, insertable = true, updatable = false)
    private int requerimientoMaquilaId;

    @Id
    @Column(nullable = false, length = 50)
    private String codigoAlterno;

    @Column(nullable = false, length = 200)
    private String descripcion;

    // ---------------------------------------------------------------------------------------------------------
    public OmsOrdenRequerimientoAlistamientoAssociation(int ordenId, int requerimientoMaquilaId, String codigoAlterno,
            String descripcion) {
        super();
        this.setOrdenId(ordenId);
        this.setRequerimientoMaquilaId(requerimientoMaquilaId);
        this.setCodigoAlterno(codigoAlterno);
        this.setDescripcion(descripcion);
    }

    public int getOrdenId() {
        return ordenId;
    }

    public int getRequerimientoMaquilaId() {
        return requerimientoMaquilaId;
    }

    public String getCodigoAlterno() {
        return codigoAlterno;
    }

    public String getDescripcion() {
        return descripcion;
    }

    // ---------------------------------------------------------------------------------------------------------
    protected OmsOrdenRequerimientoAlistamientoAssociation() {
        super();
        this.setCodigoAlterno("");
        this.setDescripcion("");
    }

    protected void setOrdenId(int value) {
        this.ordenId = value;
    }

    protected void setRequerimientoMaquilaId(int value) {
        this.requerimientoMaquilaId = value;
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
        result = prime * result + ordenId;
        result = prime * result + requerimientoMaquilaId;
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
        OmsOrdenRequerimientoAlistamientoAssociation other = (OmsOrdenRequerimientoAlistamientoAssociation) obj;
        if (codigoAlterno == null) {
            if (other.codigoAlterno != null)
                return false;
        } else if (!codigoAlterno.equals(other.codigoAlterno))
            return false;
        if (ordenId != other.ordenId)
            return false;
        if (requerimientoMaquilaId != other.requerimientoMaquilaId)
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("OmsOrdenRequerimientoMaquilaAssociation [ordenId=").append(ordenId)
                .append(", requerimientoMaquilaId=").append(requerimientoMaquilaId).append(", ");
        if (codigoAlterno != null) {
            builder.append("codigoAlterno=").append(codigoAlterno).append(", ");
        }
        if (descripcion != null) {
            builder.append("descripcion=").append(descripcion);
        }
        builder.append("]");
        return builder.toString();
    }
}
