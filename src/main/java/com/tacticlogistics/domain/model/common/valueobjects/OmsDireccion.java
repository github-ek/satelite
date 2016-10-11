package com.tacticlogistics.domain.model.common.valueobjects;

import static com.tacticlogistics.infrastructure.services.Basic.coalesce;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import com.tacticlogistics.common.ddd.AssertionConcern;

@Embeddable
public class OmsDireccion extends AssertionConcern implements Serializable{
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 1L;

    @Column(name = "id_ciudad")
    private Integer ciudadId;

    @Column(nullable = false, length = 150)
    @NotNull
    private String direccion;

    @Column(nullable = false, length = 150)
    @NotNull
    private String indicacionesDireccion;

    @Column(nullable = false, length = 150)
    private String direccionEstandarizada;

    @Column(nullable = true, precision = 18, scale = 15)
    private BigDecimal cx;

    @Column(nullable = true, precision = 18, scale = 15)
    private BigDecimal cy;

    // ---------------------------------------------------------------------------------------------------------
    public OmsDireccion() {
        this(null, "", "");
    }

    public OmsDireccion(final Integer ciudadId, final String direccion, final String indicacionesDireccion) {
        super();
        this.setCiudadId(ciudadId);
        this.setDireccion(direccion);
        this.setIndicacionesDireccion(indicacionesDireccion);
        this.setDireccionEstandarizada("");
        this.setCx(null);
        this.setCy(null);
    }

    public OmsDireccion(OmsDireccion model) {
        super();
        this.assertArgumentNotNull(model, "model no puede ser null");
        this.setCiudadId(model.getCiudadId());
        this.setDireccion(model.getDireccion());
        this.setIndicacionesDireccion(model.getIndicacionesDireccion());
        this.setDireccionEstandarizada(model.getDireccionEstandarizada());
        this.setCx(model.getCx());
        this.setCy(model.getCy());
    }

    // ---------------------------------------------------------------------------------------------------------
    public Integer getCiudadId() {
        return ciudadId;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getIndicacionesDireccion() {
        return indicacionesDireccion;
    }

    public String getDireccionEstandarizada() {
        return direccionEstandarizada;
    }

    public BigDecimal getCx() {
        return cx;
    }

    public BigDecimal getCy() {
        return cy;
    }

    // ---------------------------------------------------------------------------------------------------------
    protected void setCiudadId(final Integer value) {
        this.ciudadId = value;
    }

    protected void setDireccion(String value) {
        this.direccion = coalesce(value, "");
    }

    protected void setIndicacionesDireccion(String value) {
        this.indicacionesDireccion = coalesce(value, "");;
    }

    protected void setDireccionEstandarizada(String value) {
        this.direccionEstandarizada = coalesce(value, "");;
    }

    protected void setCx(BigDecimal cx) {
        this.cx = cx;
    }

    protected void setCy(BigDecimal cy) {
        this.cy = cy;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((ciudadId == null) ? 0 : ciudadId.hashCode());
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
        OmsDireccion other = (OmsDireccion) obj;
        if (ciudadId == null) {
            if (other.ciudadId != null)
                return false;
        } else if (!ciudadId.equals(other.ciudadId))
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
        final StringBuilder builder = new StringBuilder();
        builder.append("Direccion [");
        if (ciudadId != null) {
            builder.append("ciudadId=").append(ciudadId).append(", ");
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
}
