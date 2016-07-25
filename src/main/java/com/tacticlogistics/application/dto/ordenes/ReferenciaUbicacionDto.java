package com.tacticlogistics.application.dto.ordenes;

public class ReferenciaUbicacionDto {
    private ReferenciaConDatosAlternos<Integer> ciudad;
    private String direccion;
    private String indicacionesDireccion;

    // ---------------------------------------------------------------------------------------------------------
    public ReferenciaUbicacionDto() {
        this(null, "", "", "", "", "", "");
    }

    public ReferenciaUbicacionDto(Integer aId, String aCodigo, String aNombre, String aCodigoAlterno,
            String aNombreAlterno, String aDireccion, String aIndicacionesDireccion) {
        setCiudad(new ReferenciaConDatosAlternos<Integer>(aId, aCodigo, aNombre, aCodigoAlterno, aNombreAlterno));
        setDireccion(aDireccion);
        setIndicacionesDireccion(aIndicacionesDireccion);
    }

    // ---------------------------------------------------------------------------------------------------------
    protected ReferenciaConDatosAlternos<Integer> getCiudad() {
        return ciudad;
    }

    public Integer getCiudadId() {
        return ciudad.getId();
    }

    public String getCiudadCodigo() {
        return ciudad.getCodigo();
    }

    public String getCiudadNombre() {
        return ciudad.getNombre();
    }

    public String getCiudadCodigoAlterno() {
        return ciudad.getCodigoAlterno();
    }

    public String getCiudadNombreAlterno() {
        return ciudad.getNombreAlterno();
    }

    public String getDireccion() {
        return direccion;
    }

    public String getIndicacionesDireccion() {
        return indicacionesDireccion;
    }

    // ---------------------------------------------------------------------------------------------------------
    protected void setCiudad(ReferenciaConDatosAlternos<Integer> aCiudad) {
        if (aCiudad == null) {
            throw new NullPointerException();
        }
        ciudad = aCiudad;
    }

    public void setCiudadId(Integer aCiudadId) {
        ciudad.setId(aCiudadId);
    }

    public void setCiudadCodigo(String aCiudadCodigo) {
        ciudad.setCodigo(aCiudadCodigo);
    }

    public void setCiudadNombre(String aCiudadNombre) {
        ciudad.setNombre(aCiudadNombre);
    }

    public void setCiudadCodigoAlterno(String aCiudadCodigoAlterno) {
        ciudad.setCodigoAlterno(aCiudadCodigoAlterno);
    }

    public void setCiudadNombreAlterno(String aCiudadNombreAlterno) {
        ciudad.setNombreAlterno(aCiudadNombreAlterno);
    }

    public void setDireccion(String aDireccion) {
        if (aDireccion == null) {
            throw new NullPointerException();
        }
        direccion = aDireccion;
    }

    public void setIndicacionesDireccion(String aIndicacionesDireccion) {
        if (aIndicacionesDireccion == null) {
            throw new NullPointerException();
        }
        indicacionesDireccion = aIndicacionesDireccion;
    }

    // ---------------------------------------------------------------------------------------------------------
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((direccion == null) ? 0 : direccion.hashCode());
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
        ReferenciaUbicacionDto other = (ReferenciaUbicacionDto) obj;
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
        builder.append("ReferenciaUbicacion [");
        if (getCiudad() != null) {
            builder.append("getCiudad()=").append(getCiudad()).append(", ");
        }
        if (getCiudadId() != null) {
            builder.append("getCiudadId()=").append(getCiudadId()).append(", ");
        }
        if (getCiudadCodigo() != null) {
            builder.append("getCiudadCodigo()=").append(getCiudadCodigo()).append(", ");
        }
        if (getCiudadNombre() != null) {
            builder.append("getCiudadNombre()=").append(getCiudadNombre()).append(", ");
        }
        if (getCiudadCodigoAlterno() != null) {
            builder.append("getCiudadCodigoAlterno()=").append(getCiudadCodigoAlterno()).append(", ");
        }
        if (getCiudadNombreAlterno() != null) {
            builder.append("getCiudadNombreAlterno()=").append(getCiudadNombreAlterno()).append(", ");
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
