package com.tacticlogistics.domain.model.common.valueobjects;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tacticlogistics.common.ddd.AssertionConcern;

@Embeddable
public class IntervaloDeFechas extends AssertionConcern {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 1L;
	@Column(nullable = false, columnDefinition = "DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaMinima;
	
	@Column(nullable = false, columnDefinition = "DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaMaxima;
	
    // ---------------------------------------------------------------------------------------------------------
    public IntervaloDeFechas(final Date fechaMinima, final Date fechaMaxima) {
        super();
        this.setFechaMinima(fechaMinima);
        this.setFechaMaxima(fechaMaxima);
	}

    public Date getFechaMinima() {
        return fechaMinima;
	}

	public Date getFechaMaxima() {
		return fechaMaxima;
	}

    // ---------------------------------------------------------------------------------------------------------
    protected IntervaloDeFechas() {
        this(null, null);
    }
    //TODO
    public void setFechaMinima(final Date fechaMinima) {
        this.fechaMinima = fechaMinima;
    }
    //TODO
    public void setFechaMaxima(final Date fechaMaxima) {
		this.fechaMaxima = fechaMaxima;
	}

    // ---------------------------------------------------------------------------------------------------------
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((fechaMaxima == null) ? 0 : fechaMaxima.hashCode());
        result = prime * result + ((fechaMinima == null) ? 0 : fechaMinima.hashCode());
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
        final IntervaloDeFechas other = (IntervaloDeFechas) obj;
        if (fechaMaxima == null) {
            if (other.fechaMaxima != null) {
                return false;
            }
        } else if (!fechaMaxima.equals(other.fechaMaxima)) {
            return false;
        }
        if (fechaMinima == null) {
            if (other.fechaMinima != null) {
                return false;
            }
        } else if (!fechaMinima.equals(other.fechaMinima)) {
            return false;
        }
        return true;
    }

	@Override
	public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("IntervaloDeFechas [");
        if (fechaMinima != null) {
			builder.append("fechaMinima=").append(fechaMinima).append(", ");
        }
        if (fechaMaxima != null) {
			builder.append("fechaMaxima=").append(fechaMaxima);
        }
		builder.append("]");
		return builder.toString();
	}
}
