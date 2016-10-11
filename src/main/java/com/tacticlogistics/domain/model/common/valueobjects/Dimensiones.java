package com.tacticlogistics.domain.model.common.valueobjects;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Dimensiones implements Serializable{

    @Column(nullable = true, precision = 12, scale = 4)
    private BigDecimal largoPorUnidad;

    @Column(nullable = true, precision = 12, scale = 4)
    private BigDecimal anchoPorUnidad;

    @Column(nullable = true, precision = 12, scale = 4)
    private BigDecimal altoPorUnidad;

    @Column(nullable = true, precision = 12, scale = 4)
    private BigDecimal pesoBrutoPorUnidad;

    public Dimensiones() {
        this(new BigDecimal(0), new BigDecimal(0), new BigDecimal(0), new BigDecimal(0));
    }

    public Dimensiones(BigDecimal largoPorUnidad, BigDecimal anchoPorUnidad, BigDecimal altoPorUnidad,
            BigDecimal pesoBrutoPorUnidad) {
        super();
        this.largoPorUnidad = largoPorUnidad;
        this.anchoPorUnidad = anchoPorUnidad;
        this.altoPorUnidad = altoPorUnidad;
        this.pesoBrutoPorUnidad = pesoBrutoPorUnidad;
    }

    public Dimensiones(Dimensiones model) {
        this();
        if (model != null) {
            this.largoPorUnidad = model.getLargoPorUnidad();
            this.anchoPorUnidad = model.getAnchoPorUnidad();
            this.altoPorUnidad = model.getAltoPorUnidad();
            this.pesoBrutoPorUnidad = model.getPesoBrutoPorUnidad();
        }
    }

    // ---------------------------------------------------------------------------------------------------------
    public BigDecimal getLargoPorUnidad() {
        return largoPorUnidad;
    }

    public BigDecimal getAnchoPorUnidad() {
        return anchoPorUnidad;
    }

    public BigDecimal getAltoPorUnidad() {
        return altoPorUnidad;
    }

    public BigDecimal getPesoBrutoPorUnidad() {
        return pesoBrutoPorUnidad;
    }

    public BigDecimal getPesoBrutoVolumetricoPorUnidad() {
        if (pesoBrutoPorUnidad == null || largoPorUnidad == null || anchoPorUnidad == null || altoPorUnidad == null) {
            return null;
        }
        return pesoBrutoPorUnidad.multiply(largoPorUnidad).multiply(anchoPorUnidad).multiply(altoPorUnidad);
    }

    // ---------------------------------------------------------------------------------------------------------
    protected void setLargoPorUnidad(BigDecimal value) {
        if (value == null) {

        }
        this.largoPorUnidad = value;
    }

    protected void setAnchoPorUnidad(BigDecimal value) {
        if (value == null) {

        }
        this.anchoPorUnidad = value;
    }

    protected void setAltoPorUnidad(BigDecimal value) {
        if (value == null) {

        }
        this.altoPorUnidad = value;
    }

    protected void setPesoBrutoPorUnidad(BigDecimal value) {
        if (value == null) {

        }

        this.pesoBrutoPorUnidad = value;
    }

    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((altoPorUnidad == null) ? 0 : altoPorUnidad.hashCode());
        result = prime * result + ((anchoPorUnidad == null) ? 0 : anchoPorUnidad.hashCode());
        result = prime * result + ((largoPorUnidad == null) ? 0 : largoPorUnidad.hashCode());
        result = prime * result + ((pesoBrutoPorUnidad == null) ? 0 : pesoBrutoPorUnidad.hashCode());
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
        Dimensiones other = (Dimensiones) obj;
        if (altoPorUnidad == null) {
            if (other.altoPorUnidad != null)
                return false;
        } else if (!altoPorUnidad.equals(other.altoPorUnidad))
            return false;
        if (anchoPorUnidad == null) {
            if (other.anchoPorUnidad != null)
                return false;
        } else if (!anchoPorUnidad.equals(other.anchoPorUnidad))
            return false;
        if (largoPorUnidad == null) {
            if (other.largoPorUnidad != null)
                return false;
        } else if (!largoPorUnidad.equals(other.largoPorUnidad))
            return false;
        if (pesoBrutoPorUnidad == null) {
            if (other.pesoBrutoPorUnidad != null)
                return false;
        } else if (!pesoBrutoPorUnidad.equals(other.pesoBrutoPorUnidad))
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Dimensiones [");
        if (largoPorUnidad != null) {
            builder.append("largoPorUnidad=").append(largoPorUnidad).append(", ");
        }
        if (anchoPorUnidad != null) {
            builder.append("anchoPorUnidad=").append(anchoPorUnidad).append(", ");
        }
        if (altoPorUnidad != null) {
            builder.append("altoPorUnidad=").append(altoPorUnidad).append(", ");
        }
        if (pesoBrutoPorUnidad != null) {
            builder.append("pesoBrutoPorUnidad=").append(pesoBrutoPorUnidad);
        }
        builder.append("]");
        return builder.toString();
    }
}
