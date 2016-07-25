package com.tacticlogistics.domain.model.common.valueobjects;

import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.tacticlogistics.common.ddd.AssertionConcern;

@Embeddable
public class IntervaloDeHoras extends AssertionConcern {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 1L;

    @Column(nullable = true, columnDefinition = "TIME(0)")
	private Time horaMinima;
	
    @Column(nullable = true, columnDefinition = "TIME(0)")
	private Time horaMaxima;

    // ---------------------------------------------------------------------------------------------------------
    public IntervaloDeHoras(final Time horaMinima, final Time horaMaxima) {
        super();
        this.setHoraMinima(horaMinima);
        this.setHoraMaxima(horaMaxima);
	}

    public Time getHoraMinima() {
        return horaMinima;
	}

	public Time getHoraMaxima() {
		return horaMaxima;
	}

    // ---------------------------------------------------------------------------------------------------------
    protected IntervaloDeHoras() {
        this(null, null);
	}
    //TODO
    public void setHoraMinima(final Time horaMinima) {
        this.horaMinima = horaMinima;
	}

    public void setHoraMaxima(final Time horaMaxima) {
        this.horaMaxima = horaMaxima;
    }

    // ---------------------------------------------------------------------------------------------------------
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((horaMaxima == null) ? 0 : horaMaxima.hashCode());
		result = prime * result + ((horaMinima == null) ? 0 : horaMinima.hashCode());
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
        final IntervaloDeHoras other = (IntervaloDeHoras) obj;
		if (horaMaxima == null) {
            if (other.horaMaxima != null) {
				return false;
            }
        } else if (!horaMaxima.equals(other.horaMaxima)) {
			return false;
        }
		if (horaMinima == null) {
            if (other.horaMinima != null) {
				return false;
            }
        } else if (!horaMinima.equals(other.horaMinima)) {
			return false;
        }
		return true;
	}

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("IntervaloDeHoras [");
        if (horaMinima != null) {
            builder.append("horaMinima=").append(horaMinima).append(", ");
        }
        if (horaMaxima != null) {
            builder.append("horaMaxima=").append(horaMaxima);
        }
        builder.append("]");
        return builder.toString();
    }

}
