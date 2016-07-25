package com.tacticlogistics.domain.model.crm;

import static com.tacticlogistics.infrastructure.services.Basic.coalesce;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "clientes_bodegas", catalog = "crm")
public class ClienteBodegaAssociation implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_cliente", nullable = false, insertable = true, updatable = false)
    private int clienteId;

    @Id
    @Column(nullable = false, length = 50)
    @NotNull
    private String codigoAlterno;

    @Column(name = "id_bodega", nullable = false, insertable = true, updatable = false)
    @NotNull
    private int bodegaId;

    @Column(name = "id_estado_inventario", nullable = false, length = 4)
    @NotNull
    private String estadoInventarioId;

    // ---------------------------------------------------------------------------------------------------------
    public ClienteBodegaAssociation(int clienteId, String codigoAlterno, int bodegaId) {
        super();
    }

    public ClienteBodegaAssociation(int clienteId, String codigoAlterno, int bodegaId, String estadoInventarioId) {
        super();
        this.setClienteId(clienteId);
        this.setCodigoAlterno(codigoAlterno);
        this.setBodegaId(bodegaId);
        this.setEstadoInventarioId(estadoInventarioId);
    }

    // ---------------------------------------------------------------------------------------------------------
    public int getClienteId() {
        return clienteId;
    }

    public String getCodigoAlterno() {
        return codigoAlterno;
    }

    public int getBodegaId() {
        return bodegaId;
    }

    public String getEstadoInventarioId() {
        return estadoInventarioId;
    }

    // ---------------------------------------------------------------------------------------------------------
    protected ClienteBodegaAssociation() {
        super();
        setCodigoAlterno("");
    }

    protected void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    protected void setCodigoAlterno(String value) {
        this.codigoAlterno = coalesce(value, "");
    }

    protected void setBodegaId(int bodegaId) {
        this.bodegaId = bodegaId;
    }

    protected void setEstadoInventarioId(String value) {
        this.estadoInventarioId = coalesce(value, "");
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + clienteId;
        result = prime * result + ((codigoAlterno == null) ? 0 : codigoAlterno.hashCode());
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
        ClienteBodegaAssociation other = (ClienteBodegaAssociation) obj;
        if (clienteId != other.clienteId)
            return false;
        if (codigoAlterno == null) {
            if (other.codigoAlterno != null)
                return false;
        } else if (!codigoAlterno.equals(other.codigoAlterno))
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ClienteBodegaAssociation [clienteId=").append(clienteId).append(", ");
        if (codigoAlterno != null) {
            builder.append("codigoAlterno=").append(codigoAlterno).append(", ");
        }
        builder.append("bodegaId=").append(bodegaId).append(", ");
        if (estadoInventarioId != null) {
            builder.append("estadoInventarioId=").append(estadoInventarioId);
        }
        builder.append("]");
        return builder.toString();
    }
}