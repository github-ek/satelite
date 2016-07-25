package com.tacticlogistics.application.dto.etl;

import static com.tacticlogistics.infrastructure.services.Basic.coalesce;

public class ETLDestinoOrigenDto {
    private String clienteCodigo;
    private String numeroIdentificacion;
    private String codigo;
    private String nombre;

    private String ciudadCodigoAlterno;
    private String ciudadNombreAlterno;
    private String direccion;
    private String direccionIndicaciones;
    
    private String contactoNombres;
    private String contactoEmail;
    private String contactoTelefonos;
    
    private boolean activo;
    
    public ETLDestinoOrigenDto() {
        super();
        
        setClienteCodigo("");

        setNumeroIdentificacion("");
        setCodigo("");
        setNombre("");

        setCiudadCodigoAlterno("");
        setCiudadNombreAlterno("");
        setDireccion("");
        setDireccionIndicaciones("");

        setContactoNombres("");
        setContactoEmail("");
        setContactoTelefonos("");

        setActivo(true);

    }

    public String getClienteCodigo() {
        return clienteCodigo;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCiudadCodigoAlterno() {
        return ciudadCodigoAlterno;
    }

    public String getCiudadNombreAlterno() {
        return ciudadNombreAlterno;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getDireccionIndicaciones() {
        return direccionIndicaciones;
    }

    public String getContactoNombres() {
        return contactoNombres;
    }

    public String getContactoEmail() {
        return contactoEmail;
    }

    public String getContactoTelefonos() {
        return contactoTelefonos;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setClienteCodigo(String clienteCodigo) {
        this.clienteCodigo = clienteCodigo;
    }

    public void setNumeroIdentificacion(String value) {
        this.numeroIdentificacion = coalesce(value, "");
    }

    public void setCodigo(String value) {
        this.codigo = coalesce(value, "");
    }

    public void setNombre(String value) {
        this.nombre = coalesce(value, "");
    }

    public void setCiudadCodigoAlterno(String value) {
        this.ciudadCodigoAlterno = coalesce(value, "");
    }

    public void setCiudadNombreAlterno(String value) {
        this.ciudadNombreAlterno = coalesce(value, "");
    }

    public void setDireccion(String value) {
        this.direccion = coalesce(value, "");
    }

    public void setDireccionIndicaciones(String value) {
        this.direccionIndicaciones = coalesce(value, "");
    }

    public void setContactoNombres(String value) {
        this.contactoNombres = coalesce(value, "");
    }

    public void setContactoEmail(String value) {
        this.contactoEmail = coalesce(value, "");
    }

    public void setContactoTelefonos(String value) {
        this.contactoTelefonos = coalesce(value, "");
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ETLDestinoOrigenDto [");
        if (clienteCodigo != null) {
            builder.append("clienteCodigo=").append(clienteCodigo).append(", ");
        }
        if (numeroIdentificacion != null) {
            builder.append("numeroIdentificacion=").append(numeroIdentificacion).append(", ");
        }
        if (codigo != null) {
            builder.append("codigo=").append(codigo).append(", ");
        }
        if (nombre != null) {
            builder.append("nombre=").append(nombre).append(", ");
        }
        if (ciudadCodigoAlterno != null) {
            builder.append("ciudadCodigoAlterno=").append(ciudadCodigoAlterno).append(", ");
        }
        if (ciudadNombreAlterno != null) {
            builder.append("ciudadNombreAlterno=").append(ciudadNombreAlterno).append(", ");
        }
        if (direccion != null) {
            builder.append("direccion=").append(direccion).append(", ");
        }
        if (direccionIndicaciones != null) {
            builder.append("direccionIndicaciones=").append(direccionIndicaciones).append(", ");
        }
        if (contactoNombres != null) {
            builder.append("contactoNombres=").append(contactoNombres).append(", ");
        }
        if (contactoEmail != null) {
            builder.append("contactoEmail=").append(contactoEmail).append(", ");
        }
        if (contactoTelefonos != null) {
            builder.append("contactoTelefonos=").append(contactoTelefonos).append(", ");
        }
        builder.append("activo=").append(activo).append("]");
        return builder.toString();
    }


    

}