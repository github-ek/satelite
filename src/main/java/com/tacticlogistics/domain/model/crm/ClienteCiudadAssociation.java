package com.tacticlogistics.domain.model.crm;

import static com.tacticlogistics.infrastructure.services.Basic.coalesce;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "clientes_ciudades", catalog = "crm")
public class ClienteCiudadAssociation implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_cliente", nullable = false, insertable = true, updatable = false)
    private int clienteId;

    @Id
    @Column(nullable = false, length = 100)
    private String nombreAlterno;

    @Column(name = "id_ciudad", nullable = false, insertable = true, updatable = false)
    private int ciudadId;

    // ---------------------------------------------------------------------------------------------------------
    public ClienteCiudadAssociation(int clienteId, String nombreAlterno, int ciudadId) {
        super();
        this.setClienteId(clienteId);
        this.setNombreAlterno(nombreAlterno);
        this.setCiudadId(ciudadId);
    }

    public int getClienteId() {
        return clienteId;
    }

    public String getNombreAlterno() {
        return nombreAlterno;
    }

    public int getCiudadId() {
        return ciudadId;
    }

    // ---------------------------------------------------------------------------------------------------------
    protected ClienteCiudadAssociation() {
        super();
        setNombreAlterno("");
    }

    protected void setClienteId(int value) {
        this.clienteId = value;
    }

    protected void setNombreAlterno(String value) {
        this.nombreAlterno = coalesce(value, "");
    }

    protected void setCiudadId(int value) {
        this.ciudadId = value;
    }

    // ---------------------------------------------------------------------------------------------------------
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ciudadId;
        result = prime * result + clienteId;
        result = prime * result + ((nombreAlterno == null) ? 0 : nombreAlterno.hashCode());
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
        ClienteCiudadAssociation other = (ClienteCiudadAssociation) obj;
        if (ciudadId != other.ciudadId)
            return false;
        if (clienteId != other.clienteId)
            return false;
        if (nombreAlterno == null) {
            if (other.nombreAlterno != null)
                return false;
        } else if (!nombreAlterno.equals(other.nombreAlterno))
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ClienteCiudadAssociation [clienteId=").append(clienteId).append(", ");
        if (nombreAlterno != null) {
            builder.append("nombreAlterno=").append(nombreAlterno).append(", ");
        }
        builder.append("ciudadId=").append(ciudadId).append("]");
        return builder.toString();
    }

}
