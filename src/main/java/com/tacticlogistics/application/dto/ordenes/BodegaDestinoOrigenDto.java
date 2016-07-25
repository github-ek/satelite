package com.tacticlogistics.application.dto.ordenes;

public class BodegaDestinoOrigenDto {
    private ReferenciaConDatosAlternos<Integer> bodegaDestinoOrigen;
    private ReferenciaConDatosAlternos<Integer> ciudad;

    // ---------------------------------------------------------------------------------------------------------
    public BodegaDestinoOrigenDto() {
        this(new ReferenciaConDatosAlternos<>(), new ReferenciaConDatosAlternos<>());
    }

    public BodegaDestinoOrigenDto(ReferenciaConDatosAlternos<Integer> aBodega, ReferenciaConDatosAlternos<Integer> aCiudad) {
        super();
        setBodegaDestinoOrigen(aBodega);
        setCiudad(aCiudad);
    }

    // ---------------------------------------------------------------------------------------------------------
    // Value Objects
    // ---------------------------------------------------------------------------------------------------------
    protected ReferenciaConDatosAlternos<Integer> getBodegaDestinoOrigen() {
        return bodegaDestinoOrigen;
    }

    protected ReferenciaConDatosAlternos<Integer> getCiudad() {
        return ciudad;
    }

    protected void setBodegaDestinoOrigen(ReferenciaConDatosAlternos<Integer> aBodegaDestinoOrigen) {
        if (aBodegaDestinoOrigen == null) {
            throw new NullPointerException();
        }
        bodegaDestinoOrigen = aBodegaDestinoOrigen;
    }

    protected void setCiudad(ReferenciaConDatosAlternos<Integer> aCiudad) {
        if (aCiudad == null) {
            throw new NullPointerException();
        }
        ciudad = aCiudad;
    }

    // ---------------------------------------------------------------------------------------------------------
    // Bodega
    // ---------------------------------------------------------------------------------------------------------
    public Integer getBodegaId() {
        return getBodegaDestinoOrigen().getId();
    }

    public String getBodegaCodigo() {
        return getBodegaDestinoOrigen().getCodigo();
    }

    public String getBodegaNombre() {
        return getBodegaDestinoOrigen().getNombre();
    }

    public String getBodegaCodigoAlterno() {
        return getBodegaDestinoOrigen().getCodigoAlterno();
    }

    public String getBodegaNombreAlterno() {
        return getBodegaDestinoOrigen().getNombreAlterno();
    }

    public void setBodegaId(Integer aBodegaId) {
        this.getBodegaDestinoOrigen().setId(aBodegaId);
    }

    public void setBodegaCodigo(String aBodegaCodigo) {
        this.getBodegaDestinoOrigen().setCodigo(aBodegaCodigo);
    }

    public void setBodegaNombre(String aBodegaNombre) {
        this.getBodegaDestinoOrigen().setNombre(aBodegaNombre);
    }

    public void setBodegaCodigoAlterno(String aBodegaCodigoAlterno) {
        this.getBodegaDestinoOrigen().setCodigoAlterno(aBodegaCodigoAlterno);
    }

    public void setBodegaNombreAlterno(String aBodegaNombreAlterno) {
        this.getBodegaDestinoOrigen().setNombreAlterno(aBodegaNombreAlterno);
    }

    // ---------------------------------------------------------------------------------------------------------
    // Ubicacion
    // ---------------------------------------------------------------------------------------------------------
    public Integer getCiudadId() {
        return getCiudad().getId();
    }

    public String getCiudadCodigo() {
        return getCiudad().getCodigo();
    }

    public String getCiudadNombre() {
        return getCiudad().getNombre();
    }

    public String getCiudadCodigoAlterno() {
        return getCiudad().getCodigoAlterno();
    }

    public String getCiudadNombreAlterno() {
        return getCiudad().getNombreAlterno();
    }

    public void setCiudadId(Integer aCiudadId) {
        getCiudad().setId(aCiudadId);
    }

    public void setCiudadCodigo(String aCiudadCodigo) {
        getCiudad().setCodigo(aCiudadCodigo);
    }

    public void setCiudadNombre(String aCiudadNombre) {
        getCiudad().setNombre(aCiudadNombre);
    }

    public void setCiudadCodigoAlterno(String aCiudadCodigoAlterno) {
        getCiudad().setCodigoAlterno(aCiudadCodigoAlterno);
    }

    public void setCiudadNombreAlterno(String aCiudadNombreAlterno) {
        getCiudad().setNombreAlterno(aCiudadNombreAlterno);
    }

    // ---------------------------------------------------------------------------------------------------------
    // Bodega @Deprecated
    // ---------------------------------------------------------------------------------------------------------
    @Deprecated
    public Integer getBodega() {
        return getBodegaId();
    }

    @Deprecated
    public String getCodigoBodega() {
        return getBodegaCodigo();
    }

    @Deprecated
    public String getNombreBodega() {
        return getBodegaNombre();
    }

    @Deprecated
    public String getCodigoAlternoBodega() {
        return getBodegaCodigoAlterno();
    }

    @Deprecated
    public String getNombreAlternoBodega() {
        return getBodegaNombreAlterno();
    }

    @Deprecated
    public void setBodega(Integer aBodegaId) {
        setBodegaId(aBodegaId);
    }
    @Deprecated
    public void setCodigoBodega(String aBodegaCodigo) {
        setBodegaCodigo(aBodegaCodigo);
    }
    @Deprecated
    public void setNombreBodega(String aBodegaNombre) {
        setBodegaNombre(aBodegaNombre);
    }
    @Deprecated
    public void setCodigoAlternoBodega(String aBodegaCodigoAlterno) {
        setBodegaCodigoAlterno(aBodegaCodigoAlterno);
    }
    @Deprecated
    public void setNombreAlternoBodega(String aBodegaNombreAlterno) {
        setBodegaNombreAlterno(aBodegaNombreAlterno);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("BodegaDestinoOrigenViewModel [");
        if (getBodegaId() != null) {
            builder.append("getBodegaId()=").append(getBodegaId()).append(", ");
        }
        if (getBodegaCodigo() != null) {
            builder.append("getBodegaCodigo()=").append(getBodegaCodigo()).append(", ");
        }
        if (getBodegaNombre() != null) {
            builder.append("getBodegaNombre()=").append(getBodegaNombre()).append(", ");
        }
        if (getBodegaCodigoAlterno() != null) {
            builder.append("getBodegaCodigoAlterno()=").append(getBodegaCodigoAlterno()).append(", ");
        }
        if (getBodegaNombreAlterno() != null) {
            builder.append("getBodegaNombreAlterno()=").append(getBodegaNombreAlterno()).append(", ");
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
        builder.append("]");
        return builder.toString();
    }

}