package com.tacticlogistics.domain.model.crm;

import static com.tacticlogistics.infrastructure.services.Basic.coalesce;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "clientes_requerimientos_maquila", catalog = "crm")
public class ClienteRequerimientoMaquilaAssociation implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_cliente", nullable = false, insertable = true, updatable = false)
    private int clienteId;

    @Id
    @Column(name = "id_tipo_servicio", nullable = false, insertable = true, updatable = false)
    private int tipoServicioId;

    @Id
    @Column(nullable = false, length = 50)
    private String codigoAlterno;

    @Column(name = "id_requerimiento_maquila", nullable = false, insertable = true, updatable = false)
    private int requerimientoMaquilaId;

    @Column(nullable = false, length = 200)
    private String descripcion;

    // ---------------------------------------------------------------------------------------------------------
    public ClienteRequerimientoMaquilaAssociation(int clienteId, int tipoServicioId, String codigoAlterno,
            int requerimientoMaquilaId, String descripcion) {
        super();
        this.setClienteId(clienteId);
        this.setTipoServicioId(tipoServicioId);
        this.setCodigoAlterno(codigoAlterno);
        this.setRequerimientoMaquilaId(requerimientoMaquilaId);
        this.setDescripcion(descripcion);
    }

    public int getClienteId() {
        return clienteId;
    }

    public int getTipoServicioId() {
        return tipoServicioId;
    }

    public String getCodigoAlterno() {
        return codigoAlterno;
    }

    public int getRequerimientoMaquilaId() {
        return requerimientoMaquilaId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    // ---------------------------------------------------------------------------------------------------------
    protected ClienteRequerimientoMaquilaAssociation() {
        super();
        this.setCodigoAlterno("");
        this.setDescripcion("");
    }

    protected void setClienteId(int value) {
        this.clienteId = value;
    }

    protected void setTipoServicioId(int value) {
        this.tipoServicioId = value;
    }

    protected void setCodigoAlterno(String value) {
        this.codigoAlterno = coalesce(value, "");
    }

    protected void setRequerimientoMaquilaId(int value) {
        this.requerimientoMaquilaId = value;
    }

    protected void setDescripcion(String value) {
        this.descripcion = coalesce(value, "");
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + clienteId;
        result = prime * result + ((codigoAlterno == null) ? 0 : codigoAlterno.hashCode());
        result = prime * result + tipoServicioId;
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
        ClienteRequerimientoMaquilaAssociation other = (ClienteRequerimientoMaquilaAssociation) obj;
        if (clienteId != other.clienteId)
            return false;
        if (codigoAlterno == null) {
            if (other.codigoAlterno != null)
                return false;
        } else if (!codigoAlterno.equals(other.codigoAlterno))
            return false;
        if (tipoServicioId != other.tipoServicioId)
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ClienteRequerimientoMaquilaAssociation [clienteId=").append(clienteId)
                .append(", tipoServicioId=").append(tipoServicioId).append(", ");
        if (codigoAlterno != null) {
            builder.append("codigoAlterno=").append(codigoAlterno).append(", ");
        }
        builder.append("requerimientoMaquilaId=").append(requerimientoMaquilaId).append(", ");
        if (descripcion != null) {
            builder.append("descripcion=").append(descripcion);
        }
        builder.append("]");
        return builder.toString();
    }
}
