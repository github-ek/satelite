package com.tacticlogistics.domain.model.common.valueobjects;

import static com.tacticlogistics.infrastructure.services.Basic.coalesce;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import com.tacticlogistics.common.ddd.AssertionConcern;

@Embeddable
public class Contacto extends AssertionConcern {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 1L;

    @Column(name = "contacto_nombres", nullable = false, length = 100)
    @NotNull
    private String nombres;

    @Column(name = "contacto_email", nullable = false, length = 100)
    @NotNull
    private String email;

    @Column(name = "contacto_telefonos", nullable = false, length = 50)
    @NotNull
    private String telefonos;

    // ---------------------------------------------------------------------------------------------------------
    public Contacto() {
        this("", "", "");
    }

    public Contacto(final Contacto contacto) {
        super();
        if (null == contacto) {
            throw new IllegalArgumentException("ContactoEmbeddable");
        }
        setNombres(contacto.getNombres());
        setEmail(contacto.getEmail());
        setTelefonos(contacto.getTelefonos());
    }

    public Contacto(final String nombres, final String email, final String telefonos) {
        super();
        setNombres(nombres);
        setEmail(email);
        setTelefonos(telefonos);
    }

    // ---------------------------------------------------------------------------------------------------------
    public String getNombres() {
        return nombres;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefonos() {
        return telefonos;
    }

    // ---------------------------------------------------------------------------------------------------------
    protected void setNombres(final String value) {
        this.nombres = coalesce(value, "");
        ;
    }

    protected void setEmail(final String value) {
        this.email = coalesce(value, "");
    }

    protected void setTelefonos(final String value) {
        telefonos = coalesce(value, "");
    }

    // ---------------------------------------------------------------------------------------------------------
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((nombres == null) ? 0 : nombres.hashCode());
        result = prime * result + ((telefonos == null) ? 0 : telefonos.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Contacto other = (Contacto) obj;
        if (email == null) {
            if (other.email != null) {
                return false;
            }
        } else if (!email.equals(other.email)) {
            return false;
        }
        if (nombres == null) {
            if (other.nombres != null) {
                return false;
            }
        } else if (!nombres.equals(other.nombres)) {
            return false;
        }
        if (telefonos == null) {
            if (other.telefonos != null) {
                return false;
            }
        } else if (!telefonos.equals(other.telefonos)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("ContactoEmbeddable [");
        if (nombres != null) {
            builder.append("nombres=").append(nombres).append(", ");
        }
        if (email != null) {
            builder.append("email=").append(email).append(", ");
        }
        if (telefonos != null) {
            builder.append("telefonos=").append(telefonos);
        }
        builder.append("]");
        return builder.toString();
    }
}
