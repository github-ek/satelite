package com.tacticlogistics.domain.model.crm;

import static com.tacticlogistics.infrastructure.services.Basic.coalesce;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "clientes_canales", catalog = "crm")
public class ClienteCanalAssociation implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_cliente", nullable = false, insertable = true, updatable = false)
    private int clienteId;

    @Id
    @Column(nullable = false, length = 50)
    private String codigoAlterno;

    @Column(name = "id_canal", nullable = false, insertable = true, updatable = false)
    private int canalId;

    // ---------------------------------------------------------------------------------------------------------
    protected ClienteCanalAssociation(int clienteId, String codigoAlterno, int canalId) {
        super();
        this.setClienteId(clienteId);
        this.setCodigoAlterno(codigoAlterno);
        this.setCanalId(canalId);
    }

    public int getClienteId() {
        return clienteId;
    }

    public String getCodigoAlterno() {
        return codigoAlterno;
    }

    public int getCanalId() {
        return canalId;
    }

    // ---------------------------------------------------------------------------------------------------------
    protected ClienteCanalAssociation() {
        super();
        setCodigoAlterno("");
    }

    protected void setClienteId(int value) {
        this.clienteId = value;
    }

    protected void setCodigoAlterno(String value) {
        this.codigoAlterno = coalesce(value, "");
    }

    protected void setCanalId(int value) {
        this.canalId = value;
    }

    // ---------------------------------------------------------------------------------------------------------
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + clienteId;
        result = prime * result + ((codigoAlterno == null) ? 0 : codigoAlterno.hashCode());
        result = prime * result + canalId;
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
        ClienteCanalAssociation other = (ClienteCanalAssociation) obj;
        if (clienteId != other.clienteId)
            return false;
        if (codigoAlterno == null) {
            if (other.codigoAlterno != null)
                return false;
        } else if (!codigoAlterno.equals(other.codigoAlterno))
            return false;
        if (canalId != other.canalId)
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ClienteCanalAssociation [clienteId=").append(clienteId).append(", ");
        if (codigoAlterno != null) {
            builder.append("codigoAlterno=").append(codigoAlterno).append(", ");
        }
        builder.append("canalId=").append(canalId).append("]");
        return builder.toString();
    }
}