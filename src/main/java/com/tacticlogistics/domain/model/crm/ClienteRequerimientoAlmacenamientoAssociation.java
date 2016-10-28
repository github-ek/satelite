package com.tacticlogistics.domain.model.crm;

import static com.tacticlogistics.infrastructure.services.Basic.coalesce;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "clientes_requerimientos_almacenamiento", catalog = "crm")
public class ClienteRequerimientoAlmacenamientoAssociation implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_cliente", nullable = false, insertable = true, updatable = false)
    private int clienteId;

    @Id
    @Column(name = "id_servicio", nullable = false, insertable = true, updatable = false)
    private int servicioId;

    @Id
    @Column(nullable = false, length = 50)
    private String codigoAlterno;

    @Column(name = "id_requerimiento_almacenamiento", nullable = false, insertable = true, updatable = false)
    private int requerimientoAlmacenamientoId;

    @Column(nullable = false, length = 200)
    private String descripcion;

    // ---------------------------------------------------------------------------------------------------------
    public ClienteRequerimientoAlmacenamientoAssociation(int clienteId, int servicioId, String codigoAlterno,
            int requerimientoAlmacenamientoId, String descripcion) {
        super();
        this.setClienteId(clienteId);
        this.setServicioId(servicioId);
        this.setCodigoAlterno(codigoAlterno);
        this.setRequerimientoAlmacenamientoId(requerimientoAlmacenamientoId);        
        this.setDescripcion(descripcion);
    }

    public int getClienteId() {
        return clienteId;
    }

    public int getServicioId() {
        return servicioId;
    }

    public String getCodigoAlterno() {
        return codigoAlterno;
    }

    public int getRequerimientoAlmacenamientoId() {
        return requerimientoAlmacenamientoId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    // ---------------------------------------------------------------------------------------------------------
    protected ClienteRequerimientoAlmacenamientoAssociation() {
        super();
        this.setCodigoAlterno("");
        this.setDescripcion("");
    }

    protected void setClienteId(int value) {
        this.clienteId = value;
    }

    protected void setServicioId(int value) {
        this.servicioId = value;
    }

    protected void setCodigoAlterno(String value) {
        this.codigoAlterno = coalesce(value, "");
    }

    protected void setRequerimientoAlmacenamientoId(int value) {
        this.requerimientoAlmacenamientoId = value;
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
        result = prime * result + servicioId;
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
        ClienteRequerimientoAlmacenamientoAssociation other = (ClienteRequerimientoAlmacenamientoAssociation) obj;
        if (clienteId != other.clienteId)
            return false;
        if (codigoAlterno == null) {
            if (other.codigoAlterno != null)
                return false;
        } else if (!codigoAlterno.equals(other.codigoAlterno))
            return false;
        if (servicioId != other.servicioId)
            return false;
        return true;
    }

    @Override
	public String toString() {
		return "ClienteRequerimientoAlmacenamientoAssociation [clienteId=" + clienteId + ", servicioId=" + servicioId
				+ ", codigoAlterno=" + codigoAlterno + ", requerimientoAlmacenamientoId="
				+ requerimientoAlmacenamientoId + ", descripcion=" + descripcion + "]";
	}
}
