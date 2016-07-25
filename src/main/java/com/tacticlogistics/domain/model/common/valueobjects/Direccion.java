package com.tacticlogistics.domain.model.common.valueobjects;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.tacticlogistics.domain.model.geo.Ciudad;

@Embeddable
public class Direccion {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_ciudad", nullable = false)
    @NotNull
    private Ciudad ciudad;

    @Column(nullable = false, length = 150)
    @NotNull
    private String direccion;

    @Column(nullable = false, length = 150)
    @NotNull
    private String indicacionesDireccion;

    @Column(nullable = true, precision = 18, scale = 15)
    private BigDecimal longitud;

    @Column(nullable = true, precision = 18, scale = 15)
    private BigDecimal latitud;

    @Column(nullable = false, length = 150)
    @NotNull
    private String direccionEstandarizada;

    // ---------------------------------------------------------------------------------------------------------
    public Direccion() {
        this(null, "", "");
    }

    public Direccion(Ciudad aCiudad, String aDireccion, String aIndicacionesDireccion) {
        super();
        setCiudad(aCiudad);
        setDireccion(aDireccion);
        setIndicacionesDireccion(aIndicacionesDireccion);
        setLongitud(null);
        setLatitud(null);
        setDireccionEstandarizada("");
    }

    public Direccion(Direccion model) {
        this();
        if (model != null) {
            this.ciudad = model.getCiudad();
            this.direccion = model.getDireccion();
            this.indicacionesDireccion = model.getIndicacionesDireccion();
        }
    }

    // ---------------------------------------------------------------------------------------------------------
    public Ciudad getCiudad() {
        return ciudad;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getIndicacionesDireccion() {
        return indicacionesDireccion;
    }

    public BigDecimal getLongitud() {
        return longitud;
    }

    public BigDecimal getLatitud() {
        return latitud;
    }

    public String getDireccionEstandarizada() {
        return direccionEstandarizada;
    }

    // ---------------------------------------------------------------------------------------------------------
    public void setCiudad(Ciudad aCiudad) {
        ciudad = aCiudad;
    }

    public void setDireccion(String aDireccion) {
        direccion = (aDireccion == null) ? "" : aDireccion;
    }

    public void setIndicacionesDireccion(String aIndicacionesDireccion) {
        indicacionesDireccion = (aIndicacionesDireccion == null) ? "" : aIndicacionesDireccion;
    }

    public void setLongitud(BigDecimal aLongitud) {
        longitud = aLongitud;
    }

    public void setLatitud(BigDecimal aLatitud) {
        latitud = aLatitud;
    }

    public void setDireccionEstandarizada(String aDireccionEstandarizada) {
        direccionEstandarizada = (aDireccionEstandarizada == null) ? "" : aDireccionEstandarizada;
    }
    // ---------------------------------------------------------------------------------------------------------

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((ciudad == null) ? 0 : ciudad.hashCode());
        result = prime * result + ((direccion == null) ? 0 : direccion.hashCode());
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
        Direccion other = (Direccion) obj;
        if (ciudad == null) {
            if (other.ciudad != null)
                return false;
        } else if (!ciudad.equals(other.ciudad))
            return false;
        if (direccion == null) {
            if (other.direccion != null)
                return false;
        } else if (!direccion.equals(other.direccion))
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("DireccionEmbeddable [");
        if (ciudad != null) {
            builder.append("ciudad=").append(ciudad).append(", ");
        }
        if (direccion != null) {
            builder.append("direccion=").append(direccion).append(", ");
        }
        if (indicacionesDireccion != null) {
            builder.append("indicacionesDireccion=").append(indicacionesDireccion);
        }
        builder.append("]");
        return builder.toString();
    }
    // TODO
    // Barrio
    // Localidad
    // Zona
    // Tipo Georeferenciacio
    // Fecha GeoReferenciacion
    // Usuario Georefere
}
