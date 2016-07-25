package com.tacticlogistics.application.dto.ordenes;

public class ReferenciaConDatosAlternos<T> extends ReferenciaDto<T> {
    private String codigoAlterno;
    private String nombreAlterno;

    // ---------------------------------------------------------------------------------------------------------
    public ReferenciaConDatosAlternos() {
        this(null,"","","","");
    }

    @SuppressWarnings("unused")
    private ReferenciaConDatosAlternos(T aId, String aCodigo, String aNombre) {
        throw new UnsupportedOperationException();
    }

    public ReferenciaConDatosAlternos(T aId, String aCodigo, String aNombre, String aCodigoAlterno,
            String aNombreAlterno) {
        super(aId, aCodigo, aNombre);
        setCodigoAlterno(aCodigoAlterno);
        setNombreAlterno(aNombreAlterno);
    }

    // ---------------------------------------------------------------------------------------------------------
    public String getCodigoAlterno() {
        return codigoAlterno;
    }

    public String getNombreAlterno() {
        return nombreAlterno;
    }

    // ---------------------------------------------------------------------------------------------------------
    public void setCodigoAlterno(String aCodigoAlterno) {
        if (aCodigoAlterno == null) {
            throw new NullPointerException();
        }
        codigoAlterno = aCodigoAlterno;
    }

    public void setNombreAlterno(String aNombreAlterno) {
        if (aNombreAlterno == null) {
            throw new NullPointerException();
        }
        nombreAlterno = aNombreAlterno;
    }

    // ---------------------------------------------------------------------------------------------------------
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((codigoAlterno == null) ? 0 : codigoAlterno.hashCode());
        result = prime * result + ((nombreAlterno == null) ? 0 : nombreAlterno.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        ReferenciaConDatosAlternos<?> other = (ReferenciaConDatosAlternos<?>) obj;
        if (codigoAlterno == null) {
            if (other.codigoAlterno != null)
                return false;
        } else if (!codigoAlterno.equals(other.codigoAlterno))
            return false;
        if (nombreAlterno == null) {
            if (other.nombreAlterno != null)
                return false;
        } else if (!nombreAlterno.equals(other.nombreAlterno))
            return false;
        return true;
    }

    // ---------------------------------------------------------------------------------------------------------
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ReferenciaConDatosAlternos [");
        if (getId() != null) {
            builder.append("getId()=").append(getId()).append(", ");
        }
        if (getCodigo() != null) {
            builder.append("getCodigo()=").append(getCodigo()).append(", ");
        }
        if (getNombre() != null) {
            builder.append("getNombre()=").append(getNombre()).append(", ");
        }
        if (codigoAlterno != null) {
            builder.append("codigoAlterno=").append(codigoAlterno).append(", ");
        }
        if (nombreAlterno != null) {
            builder.append("nombreAlterno=").append(nombreAlterno);
        }
        builder.append("]");
        return builder.toString();
    }
}