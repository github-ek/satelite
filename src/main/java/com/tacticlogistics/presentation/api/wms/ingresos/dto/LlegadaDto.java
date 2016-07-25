package com.tacticlogistics.presentation.api.wms.ingresos.dto;

import static com.tacticlogistics.infrastructure.services.Basic.coalesce;

import java.util.Date;

public class LlegadaDto {
    private Integer id;

    private Integer transportadoraId;
    private Integer tipoVehiculoId;

    private String numeroPlacaVehiculo;
    private String numeroPlacaRemolque;

    private Date fechaNotificacionDeLlegada;
    private Date fechaRegistroDeLlegada;

    private String conductorNumeroIdentificacion;
    private String conductorNombres;
    private String conductorApellidos;
    private String conductorTelefono;

    private String usuarioActualizacion;

    public LlegadaDto() {
        super();
        this.setId(null);
        this.setTransportadoraId(null);
        this.setTipoVehiculoId(null);
        this.setNumeroPlacaVehiculo("");
        this.setNumeroPlacaRemolque("");
        this.setFechaNotificacionDeLlegada(null);
        this.setFechaRegistroDeLlegada(null);
        this.setConductorNumeroIdentificacion("");
        this.setConductorNombres("");
        this.setConductorApellidos("");
        this.setConductorTelefono("");
        this.setUsuarioActualizacion("");
    }

    public Integer getId() {
        return id;
    }

    public Integer getTransportadoraId() {
        return transportadoraId;
    }

    public Integer getTipoVehiculoId() {
        return tipoVehiculoId;
    }

    public String getNumeroPlacaVehiculo() {
        return numeroPlacaVehiculo;
    }

    public String getNumeroPlacaRemolque() {
        return numeroPlacaRemolque;
    }

    public Date getFechaNotificacionDeLlegada() {
        return fechaNotificacionDeLlegada;
    }

    public Date getFechaRegistroDeLlegada() {
        return fechaRegistroDeLlegada;
    }

    public String getConductorNumeroIdentificacion() {
        return conductorNumeroIdentificacion;
    }

    public String getConductorNombres() {
        return conductorNombres;
    }

    public String getConductorApellidos() {
        return conductorApellidos;
    }

    public String getConductorTelefono() {
        return conductorTelefono;
    }

    public String getUsuarioActualizacion() {
        return usuarioActualizacion;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTransportadoraId(Integer transportadoraId) {
        this.transportadoraId = transportadoraId;
    }

    public void setTipoVehiculoId(Integer tipoVehiculoId) {
        this.tipoVehiculoId = tipoVehiculoId;
    }

    public void setNumeroPlacaVehiculo(String value) {
        this.numeroPlacaVehiculo = coalesce(value, "");
    }

    public void setNumeroPlacaRemolque(String value) {
        this.numeroPlacaRemolque = coalesce(value, "");
    }

    public void setFechaNotificacionDeLlegada(Date fechaNotificacionDeLlegada) {
        this.fechaNotificacionDeLlegada = fechaNotificacionDeLlegada;
    }

    public void setFechaRegistroDeLlegada(Date fechaRegistroDeLlegada) {
        this.fechaRegistroDeLlegada = fechaRegistroDeLlegada;
    }

    public void setConductorNumeroIdentificacion(String value) {
        this.conductorNumeroIdentificacion = coalesce(value, "");
    }

    public void setConductorNombres(String value) {
        this.conductorNombres = coalesce(value, "");
    }

    public void setConductorApellidos(String value) {
        this.conductorApellidos = coalesce(value, "");
    }

    public void setConductorTelefono(String value) {
        this.conductorTelefono = coalesce(value, "");
    }

    public void setUsuarioActualizacion(String value) {
        this.usuarioActualizacion = coalesce(value, "");
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("RegistrarLlegadaDto [");
        if (id != null) {
            builder.append("id=").append(id).append(", ");
        }
        if (transportadoraId != null) {
            builder.append("transportadoraId=").append(transportadoraId).append(", ");
        }
        if (tipoVehiculoId != null) {
            builder.append("tipoVehiculoId=").append(tipoVehiculoId).append(", ");
        }
        if (numeroPlacaVehiculo != null) {
            builder.append("numeroPlacaVehiculo=").append(numeroPlacaVehiculo).append(", ");
        }
        if (numeroPlacaRemolque != null) {
            builder.append("numeroPlacaRemolque=").append(numeroPlacaRemolque).append(", ");
        }
        if (fechaNotificacionDeLlegada != null) {
            builder.append("fechaNotificacionDeLlegada=").append(fechaNotificacionDeLlegada).append(", ");
        }
        if (fechaRegistroDeLlegada != null) {
            builder.append("fechaRegistroDeLlegada=").append(fechaRegistroDeLlegada).append(", ");
        }
        if (conductorNumeroIdentificacion != null) {
            builder.append("conductorNumeroIdentificacion=").append(conductorNumeroIdentificacion).append(", ");
        }
        if (conductorNombres != null) {
            builder.append("conductorNombres=").append(conductorNombres).append(", ");
        }
        if (conductorApellidos != null) {
            builder.append("conductorApellidos=").append(conductorApellidos).append(", ");
        }
        if (conductorTelefono != null) {
            builder.append("conductorTelefono=").append(conductorTelefono).append(", ");
        }
        if (usuarioActualizacion != null) {
            builder.append("usuarioActualizacion=").append(usuarioActualizacion);
        }
        builder.append("]");
        return builder.toString();
    }
}
