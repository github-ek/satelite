package com.tacticlogistics.domain.model.notificaciones;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.tacticlogistics.domain.model.common.valueobjects.Contacto;
import com.tacticlogistics.domain.model.crm.Cliente;

@Entity
@Table(name = "suscriptores", catalog = "notificaciones")
public class Suscriptor implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_suscriptor", unique = true, nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @Embedded
    private Contacto contacto;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_grupo_suscriptor", nullable = false)
    private GrupoSuscriptor grupoSuscriptor;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "suscriptor")
    private Set<SuscriptorNotificacionAssociation> suscriptorNotificacionAssociation;

    protected Suscriptor() {
        super();
    }

    public Suscriptor(Cliente aCliente, GrupoSuscriptor aGrupoSuscriptor, Contacto aContacto) {
        super();
        setCliente(aCliente);
        setGrupoSuscriptor(aGrupoSuscriptor);
        setContacto(aContacto);
    }

    public Integer getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public GrupoSuscriptor getGrupoSuscriptor() {
        return grupoSuscriptor;
    }

    public Contacto getContacto() {
        return contacto;
    }

    public Set<SuscriptorNotificacionAssociation> getSuscriptorNotificacionAssociation() {
        if (suscriptorNotificacionAssociation == null) {
            suscriptorNotificacionAssociation = new HashSet<>();
        }
        return suscriptorNotificacionAssociation;
    }

    protected void setCliente(final Cliente aCliente) {
        if (aCliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser null");
        }
        cliente = aCliente;
    }

    protected void setGrupoSuscriptor(final GrupoSuscriptor aGrupoSuscriptor) {
        if (aGrupoSuscriptor == null) {
            throw new IllegalArgumentException("El GrupoSuscriptor no puede ser null");
        }
        grupoSuscriptor = aGrupoSuscriptor;
    }

    protected void setContacto(final Contacto aContacto) {
        if (aContacto == null) {
            throw new IllegalArgumentException("El Contacto no puede ser null");
        }
        contacto = aContacto;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
        result = prime * result + ((contacto == null) ? 0 : contacto.hashCode());
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
        Suscriptor other = (Suscriptor) obj;
        if (cliente == null) {
            if (other.cliente != null)
                return false;
        } else if (!cliente.equals(other.cliente))
            return false;
        if (contacto == null) {
            if (other.contacto != null)
                return false;
        } else if (!contacto.equals(other.contacto))
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Suscriptor [");
        if (id != null) {
            builder.append("id=").append(id).append(", ");
        }
        if (cliente != null) {
            builder.append("cliente=").append(cliente).append(", ");
        }
        if (contacto != null) {
            builder.append("contacto=").append(contacto).append(", ");
        }
        if (grupoSuscriptor != null) {
            builder.append("grupoSuscriptor=").append(grupoSuscriptor);
        }
        builder.append("]");
        return builder.toString();
    }

}
