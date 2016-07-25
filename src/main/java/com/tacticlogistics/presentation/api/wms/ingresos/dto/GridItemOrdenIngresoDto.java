package com.tacticlogistics.presentation.api.wms.ingresos.dto;

import static com.tacticlogistics.infrastructure.services.Basic.coalesce;

import java.sql.Time;
import java.util.Date;

import com.tacticlogistics.domain.model.ordenes.EstadoOrdenType;

public class GridItemOrdenIngresoDto {
    private Integer id;

    // ---------------------------------------------------------------------------------------------------------
    private String clienteCodigo;
    // ---------------------------------------------------------------------------------------------------------
    private com.tacticlogistics.domain.model.ordenes.EstadoOrdenType estadoOrden;
    private String numeroDocumentoOrdenCliente;

    // ---------------------------------------------------------------------------------------------------------
    private Date fechaPlaneadaEntregaMinima;
    private Date fechaPlaneadaEntregaMaxima;
    private Time horaPlaneadaEntregaMinima;
    private Time horaPlaneadaEntregaMaxima;

    // ---------------------------------------------------------------------------------------------------------
    private Date fechaActualizacion;
    private String usuarioActualizacion;

    private Date fechaConfirmacion;
    private String usuarioConfirmacion;

    private Date fechaAprobacionCliente;
    private String usuarioAprobacionCliente;

    public int getId() {
        return id;
    }

    public String getClienteCodigo() {
        return clienteCodigo;
    }

    public com.tacticlogistics.domain.model.ordenes.EstadoOrdenType getEstadoOrden() {
        return estadoOrden;
    }

    public String getNumeroDocumentoOrdenCliente() {
        return numeroDocumentoOrdenCliente;
    }

    public Date getFechaPlaneadaEntregaMinima() {
        return fechaPlaneadaEntregaMinima;
    }

    public Date getFechaPlaneadaEntregaMaxima() {
        return fechaPlaneadaEntregaMaxima;
    }

    public Time getHoraPlaneadaEntregaMinima() {
        return horaPlaneadaEntregaMinima;
    }

    public Time getHoraPlaneadaEntregaMaxima() {
        return horaPlaneadaEntregaMaxima;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public String getUsuarioActualizacion() {
        return usuarioActualizacion;
    }

    public Date getFechaConfirmacion() {
        return fechaConfirmacion;
    }

    public String getUsuarioConfirmacion() {
        return usuarioConfirmacion;
    }

    public Date getFechaAprobacionCliente() {
        return fechaAprobacionCliente;
    }

    public String getUsuarioAprobacionCliente() {
        return usuarioAprobacionCliente;
    }

    // ---------------------------------------------------------------------------------------------------------
    public void setId(int id) {
        this.id = id;
    }

    public void setClienteCodigo(String value) {
        this.clienteCodigo = coalesce(value, "");
    }

    public void setEstadoOrden(com.tacticlogistics.domain.model.ordenes.EstadoOrdenType value) {
        this.estadoOrden = value;
    }

    public void setNumeroDocumentoOrdenCliente(String value) {
        this.numeroDocumentoOrdenCliente = coalesce(value, "");
    }


    public void setFechaPlaneadaEntregaMinima(Date value) {
        this.fechaPlaneadaEntregaMinima = value;
    }

    public void setFechaPlaneadaEntregaMaxima(Date value) {
        this.fechaPlaneadaEntregaMaxima = value;
    }

    public void setHoraPlaneadaEntregaMinima(Time value) {
        this.horaPlaneadaEntregaMinima = value;
    }

    public void setHoraPlaneadaEntregaMaxima(Time value) {
        this.horaPlaneadaEntregaMaxima = value;
    }


    public void setFechaActualizacion(Date value) {
        this.fechaActualizacion = value;
    }

    public void setUsuarioActualizacion(String value) {
        this.usuarioActualizacion = coalesce(value, "");
    }

    public void setFechaConfirmacion(Date value) {
        this.fechaConfirmacion = value;
    }

    public void setUsuarioConfirmacion(String value) {
        this.usuarioConfirmacion = coalesce(value, "");
    }

    public void setFechaAprobacionCliente(Date value) {
        this.fechaAprobacionCliente = value;
    }

    public void setUsuarioAprobacionCliente(String value) {
        this.usuarioAprobacionCliente = coalesce(value, "");
    }
    
    public GridItemOrdenIngresoDto(
            int id, 
            String clienteCodigo, 
            EstadoOrdenType estadoOrden,
            String numeroDocumentoOrdenCliente, 
            Date fechaPlaneadaEntregaMinima, 
            Date fechaPlaneadaEntregaMaxima,
            Date horaPlaneadaEntregaMinima, 
            Date horaPlaneadaEntregaMaxima, 
            
            Date fechaActualizacion,
            String usuarioActualizacion, 
            
            Date fechaConfirmacion, 
            String usuarioConfirmacion,
            
            Date fechaAprobacionCliente, 
            String usuarioAprobacionCliente) {
        super();
        this.id = id;
        this.setClienteCodigo (clienteCodigo);
        this.setEstadoOrden  (estadoOrden);
        this.setNumeroDocumentoOrdenCliente  (numeroDocumentoOrdenCliente);
        this.setFechaPlaneadaEntregaMinima  (fechaPlaneadaEntregaMinima);
        this.setFechaPlaneadaEntregaMaxima  (fechaPlaneadaEntregaMaxima);
        this.setHoraPlaneadaEntregaMinima  ((horaPlaneadaEntregaMinima==null)?null:new Time(horaPlaneadaEntregaMinima.getTime()));
        this.setHoraPlaneadaEntregaMaxima  ((horaPlaneadaEntregaMaxima==null)?null:new Time(horaPlaneadaEntregaMaxima.getTime()));
        this.setFechaActualizacion  (fechaActualizacion);
        this.setUsuarioActualizacion  (usuarioActualizacion);
        this.setFechaConfirmacion  (fechaConfirmacion);
        this.setUsuarioConfirmacion  (usuarioConfirmacion);
        this.setFechaAprobacionCliente  (fechaAprobacionCliente);
        this.setUsuarioAprobacionCliente  (usuarioAprobacionCliente);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("GridItemOrdenIngresoDto [id=").append(id).append(", ");
        if (clienteCodigo != null) {
            builder.append("clienteCodigo=").append(clienteCodigo).append(", ");
        }
        if (estadoOrden != null) {
            builder.append("estadoOrden=").append(estadoOrden).append(", ");
        }
        if (numeroDocumentoOrdenCliente != null) {
            builder.append("numeroDocumentoOrdenCliente=").append(numeroDocumentoOrdenCliente).append(", ");
        }
        if (fechaPlaneadaEntregaMinima != null) {
            builder.append("fechaPlaneadaEntregaMinima=").append(fechaPlaneadaEntregaMinima).append(", ");
        }
        if (fechaPlaneadaEntregaMaxima != null) {
            builder.append("fechaPlaneadaEntregaMaxima=").append(fechaPlaneadaEntregaMaxima).append(", ");
        }
        if (horaPlaneadaEntregaMinima != null) {
            builder.append("horaPlaneadaEntregaMinima=").append(horaPlaneadaEntregaMinima).append(", ");
        }
        if (horaPlaneadaEntregaMaxima != null) {
            builder.append("horaPlaneadaEntregaMaxima=").append(horaPlaneadaEntregaMaxima).append(", ");
        }
        if (fechaActualizacion != null) {
            builder.append("fechaActualizacion=").append(fechaActualizacion).append(", ");
        }
        if (usuarioActualizacion != null) {
            builder.append("usuarioActualizacion=").append(usuarioActualizacion).append(", ");
        }
        if (fechaConfirmacion != null) {
            builder.append("fechaConfirmacion=").append(fechaConfirmacion).append(", ");
        }
        if (usuarioConfirmacion != null) {
            builder.append("usuarioConfirmacion=").append(usuarioConfirmacion).append(", ");
        }
        if (fechaAprobacionCliente != null) {
            builder.append("fechaAprobacionCliente=").append(fechaAprobacionCliente).append(", ");
        }
        if (usuarioAprobacionCliente != null) {
            builder.append("usuarioAprobacionCliente=").append(usuarioAprobacionCliente);
        }
        builder.append("]");
        return builder.toString();
    }

    
}
