package com.tacticlogistics.application.dto.ordenes;

import com.tacticlogistics.domain.model.common.valueobjects.Contacto;

public class DestinoOrigenDto {
    private ReferenciaConDatosAlternos<Integer> destinoOrigen;
    private ReferenciaUbicacionDto ubicacion;
    private Contacto contacto;

    // ---------------------------------------------------------------------------------------------------------
    public DestinoOrigenDto() {
        this(new ReferenciaConDatosAlternos<>(), new ReferenciaUbicacionDto(), new Contacto());
    }

    public DestinoOrigenDto(ReferenciaConDatosAlternos<Integer> aDestinoOrigen, ReferenciaUbicacionDto aUbicacion,
            Contacto aContacto) {
        super();
        this.setDestinoOrigen(aDestinoOrigen);
        this.setUbicacion(aUbicacion);
        this.setContacto(aContacto);
    }

    // ---------------------------------------------------------------------------------------------------------
    // Value Objects
    // ---------------------------------------------------------------------------------------------------------
    protected ReferenciaConDatosAlternos<Integer> getDestinoOrigen() {
        return destinoOrigen;
    }

    protected ReferenciaUbicacionDto getUbicacion() {
        return ubicacion;
    }

    protected Contacto getContacto() {
        return contacto;
    }

    protected void setDestinoOrigen(ReferenciaConDatosAlternos<Integer> aDestinoOrigen) {
        if (aDestinoOrigen == null) {
            throw new NullPointerException();
        }
        destinoOrigen = aDestinoOrigen;
    }

    protected void setUbicacion(ReferenciaUbicacionDto aUbicacion) {
        if (aUbicacion == null) {
            throw new NullPointerException();
        }
        ubicacion = aUbicacion;
    }

    protected void setContacto(Contacto aContacto) {
        if (aContacto == null) {
            throw new NullPointerException();
        }
        contacto = aContacto;
    }

    // ---------------------------------------------------------------------------------------------------------
    // DestinoOrigen
    // ---------------------------------------------------------------------------------------------------------
    public Integer getDestinoId() {
        return getDestinoOrigen().getId();
    }

    public String getDestinoCodigo() {
        return getDestinoOrigen().getCodigo();
    }

    public String getDestinoNombre() {
        return getDestinoOrigen().getNombre();
    }

    public String getDestinoCodigoAlterno() {
        return getDestinoOrigen().getCodigoAlterno();
    }

    public String getDestinoNombreAlterno() {
        return getDestinoOrigen().getNombreAlterno();
    }

    public void setDestinoId(Integer aDestinoId) {
        this.getDestinoOrigen().setId(aDestinoId);
    }

    public void setDestinoCodigo(String aDestinoCodigo) {
        this.getDestinoOrigen().setCodigo(aDestinoCodigo);
    }

    public void setDestinoNombre(String aDestinoNombre) {
        this.getDestinoOrigen().setNombre(aDestinoNombre);
    }

    public void setDestinoCodigoAlterno(String aDestinoCodigoAlterno) {
        this.getDestinoOrigen().setCodigoAlterno(aDestinoCodigoAlterno);
    }

    public void setDestinoNombreAlterno(String aDestinoNombreAlterno) {
        this.getDestinoOrigen().setNombreAlterno(aDestinoNombreAlterno);
    }

    // ---------------------------------------------------------------------------------------------------------
    // Ubicacion
    // ---------------------------------------------------------------------------------------------------------
    public Integer getCiudadId() {
        return getUbicacion().getCiudadId();
    }

    public String getCiudadCodigo() {
        return getUbicacion().getCiudadCodigo();
    }

    public String getCiudadNombre() {
        return getUbicacion().getCiudadNombre();
    }

    public String getCiudadCodigoAlterno() {
        return getUbicacion().getCiudadCodigoAlterno();
    }

    public String getCiudadNombreAlterno() {
        return getUbicacion().getCiudadNombreAlterno();
    }

    public String getDireccion() {
        return getUbicacion().getDireccion();
    }

    public String getIndicacionesDireccion() {
        return getUbicacion().getIndicacionesDireccion();
    }

    public void setCiudadId(Integer aCiudadId) {
        getUbicacion().setCiudadId(aCiudadId);
    }

    public void setCiudadCodigo(String aCiudadCodigo) {
        getUbicacion().setCiudadCodigo(aCiudadCodigo);
    }

    public void setCiudadNombre(String aCiudadNombre) {
        getUbicacion().setCiudadNombre(aCiudadNombre);
    }

    public void setCiudadCodigoAlterno(String aCiudadCodigoAlterno) {
        getUbicacion().setCiudadCodigoAlterno(aCiudadCodigoAlterno);
    }

    public void setCiudadNombreAlterno(String aCiudadNombreAlterno) {
        getUbicacion().setCiudadNombreAlterno(aCiudadNombreAlterno);
    }

    public void setDireccion(String aDireccion) {
        getUbicacion().setDireccion(aDireccion);
    }

    public void setIndicacionesDireccion(String aIndicacionesDireccion) {
        getUbicacion().setIndicacionesDireccion(aIndicacionesDireccion);
    }

    // ---------------------------------------------------------------------------------------------------------
    // Contacto
    // ---------------------------------------------------------------------------------------------------------
    public String getContactoNombres() {
        return getContacto().getNombres();
    }

    public String getContactoEmail() {
        return getContacto().getEmail();
    }

    public String getContactoTelefonos() {
        return getContacto().getTelefonos();
    }

    public void setContactoNombres(String aNombres) {
        setContacto(new Contacto(aNombres, getContactoEmail(), getContactoTelefonos()));
    }

    public void setContactoEmail(String aEmail) {
        setContacto(new Contacto(getContactoNombres(), aEmail, getContactoTelefonos()));
    }

    public void setContactoTelefonos(String aTelefonos) {
        setContacto(new Contacto(getContactoNombres(), getContactoEmail(), aTelefonos));
    }

    // ---------------------------------------------------------------------------------------------------------
    // DestinoOrigen @Deprecated
    // ---------------------------------------------------------------------------------------------------------
    @Deprecated
    public Integer getDestino() {
        return getDestinoId();
    }

    @Deprecated
    public String getCodigoDestino() {
        return getDestinoCodigo();
    }

    @Deprecated
    public String getNombreDestino() {
        return getDestinoNombre();
    }

    @Deprecated
    public String getCodigoAlternoDestino() {
        return getDestinoCodigoAlterno();
    }

    @Deprecated
    public String getNombreAlternoDestino() {
        return getDestinoNombreAlterno();
    }

    @Deprecated
    public void setDestino(Integer aDestinoId) {
        setDestinoId(aDestinoId);
    }

    @Deprecated
    public void setCodigoDestino(String aDestinoCodigo) {
        setDestinoCodigo(aDestinoCodigo);
    }

    @Deprecated
    public void setNombreDestino(String aDestinoNombre) {
        setDestinoNombre(aDestinoNombre);
    }

    @Deprecated
    public void setCodigoAlternoDestino(String aDestinoCodigoAlterno) {
        setDestinoCodigoAlterno(aDestinoCodigoAlterno);
    }

    @Deprecated
    public void setNombreAlternoDestino(String aDestinoNombreAlterno) {
        setDestinoNombreAlterno(aDestinoNombreAlterno);
    }

    // ---------------------------------------------------------------------------------------------------------
    // Ubicacion @Deprecated
    // ---------------------------------------------------------------------------------------------------------
    @Deprecated
    public Integer getCiudad() {
        return getCiudadId();
    }

    @Deprecated
    public String getNombreAlternoCiudad() {
        return getCiudadNombre();
    }

    @Deprecated
    public void setCiudad(Integer aCiudadId) {
        setCiudadId(aCiudadId);
    }

    @Deprecated
    public void setNombreAlternoCiudad(String aCiudadNombreAlterno) {
        setCiudadNombreAlterno(aCiudadNombreAlterno);
    }

    // ---------------------------------------------------------------------------------------------------------
    // Conatco @Deprecated
    // ---------------------------------------------------------------------------------------------------------

    public String getNombre() {
        return getContactoNombres();
    }

    public String getEmail() {
        return getContactoEmail();
    }

    public String getTelefonos() {
        return getContactoTelefonos();
    }

    public void setNombre(String aNombres) {
        setContactoNombres(aNombres);
    }

    public void setEmail(String aEmail) {
        setContactoEmail(aEmail);
    }

    public void setTelefonos(String aTelefonos) {
        setContactoTelefonos(aTelefonos);
    }

    // ---------------------------------------------------------------------------------------------------------
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("DestinoOrigenViewModel [");
        if (destinoOrigen != null) {
            builder.append("destinoOrigen=").append(destinoOrigen).append(", ");
        }
        if (ubicacion != null) {
            builder.append("ubicacion=").append(ubicacion).append(", ");
        }
        if (contacto != null) {
            builder.append("contacto=").append(contacto).append(", ");
        }
        if (getDestinoId() != null) {
            builder.append("getDestinoId()=").append(getDestinoId()).append(", ");
        }
        if (getDestinoCodigo() != null) {
            builder.append("getDestinoCodigo()=").append(getDestinoCodigo()).append(", ");
        }
        if (getDestinoNombre() != null) {
            builder.append("getDestinoNombre()=").append(getDestinoNombre()).append(", ");
        }
        if (getDestinoCodigoAlterno() != null) {
            builder.append("getDestinoCodigoAlterno()=").append(getDestinoCodigoAlterno()).append(", ");
        }
        if (getDestinoNombreAlterno() != null) {
            builder.append("getDestinoNombreAlterno()=").append(getDestinoNombreAlterno()).append(", ");
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
        if (getDireccion() != null) {
            builder.append("getDireccion()=").append(getDireccion()).append(", ");
        }
        if (getIndicacionesDireccion() != null) {
            builder.append("getIndicacionesDireccion()=").append(getIndicacionesDireccion()).append(", ");
        }
        if (getContactoNombres() != null) {
            builder.append("getContactoNombres()=").append(getContactoNombres()).append(", ");
        }
        if (getContactoEmail() != null) {
            builder.append("getContactoEmail()=").append(getContactoEmail()).append(", ");
        }
        if (getContactoTelefonos() != null) {
            builder.append("getContactoTelefonos()=").append(getContactoTelefonos());
        }
        builder.append("]");
        return builder.toString();
    }
}