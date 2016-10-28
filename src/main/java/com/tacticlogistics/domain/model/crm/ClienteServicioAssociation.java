package com.tacticlogistics.domain.model.crm;

import static com.tacticlogistics.infrastructure.services.Basic.coalesce;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "clientes_servicios", catalog = "crm")
public class ClienteServicioAssociation implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_cliente", nullable = false, insertable = true, updatable = false)
    private int clienteId;

    @Id
    @Column(nullable = false, length = 50)
    private String codigoAlterno;
    
    @Column(name = "id_servicio", nullable = false, insertable = true, updatable = false)
    private int servicioId;

    // ---------------------------------------------------------------------------------------------------------
    public ClienteServicioAssociation(int clienteId, String codigoAlterno, int servicioId) {
        super();
        this.setClienteId(clienteId);
        this.setCodigoAlterno(codigoAlterno);
        this.setServicioId(servicioId);
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public String getCodigoAlterno() {
        return codigoAlterno;
    }

    public Integer getServicioId() {
        return servicioId;
    }

    // ---------------------------------------------------------------------------------------------------------
    protected ClienteServicioAssociation() {
        super();
        setCodigoAlterno("");
    }

    protected void setClienteId(Integer value) {
        this.clienteId = value;
    }

    protected void setCodigoAlterno(String value) {
        this.codigoAlterno = coalesce(value, "");
    }

    public void setServicioId(Integer value) {
        this.servicioId = value;
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
        ClienteServicioAssociation other = (ClienteServicioAssociation) obj;
        if (clienteId != other.clienteId)
            return false;
        if (codigoAlterno == null) {
            if (other.codigoAlterno != null)
                return false;
        } else if (!codigoAlterno.equals(other.codigoAlterno))
            return false;
        return true;
    }

}
