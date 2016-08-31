package com.tacticlogistics.application.dto.oms;

import static com.tacticlogistics.infrastructure.services.Basic.coalesce;

import java.sql.Time;
import java.util.Date;

public class ConsultaOrdenesDto {

    private int id;

    // ---------------------------------------------------------------------------------------------------------
    private String tipoServicioNombre;

    // ---------------------------------------------------------------------------------------------------------
    private String clienteCodigo;
    private String clienteNombre;

    // ---------------------------------------------------------------------------------------------------------
    private com.tacticlogistics.domain.model.oms.EstadoOrdenType estadoOrden;
    private String numeroOrden;

    // ---------------------------------------------------------------------------------------------------------
    private String numeroConsolidado;

    // ---------------------------------------------------------------------------------------------------------
    private String canalCodigo;
    private String canalNombre;

    // ---------------------------------------------------------------------------------------------------------
    private String destinatarioNombre;
    private String destinatarioNumeroIdentificacion;

    // ---------------------------------------------------------------------------------------------------------
    private String destinoNombre;

    // ---------------------------------------------------------------------------------------------------------
    private String bodegaDestinoCodigo;
    private String bodegaDestinoNombre;

    // ---------------------------------------------------------------------------------------------------------
    private String destinoCiudadNombre;
    private String destinoDireccion;

    // ---------------------------------------------------------------------------------------------------------
    private Date fechaSugeridaEntregaMinima;
    private Date fechaSugeridaEntregaMaxima;
    private Time horaSugeridaEntregaMinima;
    private Time horaSugeridaEntregaMaxima;
    private Time horaSugeridaEntregaMinimaAdicional;
    private Time horaSugeridaEntregaMaximaAdicional;

    // ---------------------------------------------------------------------------------------------------------
    private Date fechaPlaneadaAlistamiento;

    private Date fechaPlaneadaEntregaMinima;
    private Date fechaPlaneadaEntregaMaxima;
    private Time horaPlaneadaEntregaMinima;
    private Time horaPlaneadaEntregaMaxima;
    private Time horaPlaneadaEntregaMinimaAdicional;
    private Time horaPlaneadaEntregaMaximaAdicional;

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

    public String getTipoServicioNombre() {
        return tipoServicioNombre;
    }

    public String getClienteCodigo() {
        return clienteCodigo;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public com.tacticlogistics.domain.model.oms.EstadoOrdenType getEstadoOrden() {
        return estadoOrden;
    }

    public String getNumeroOrden() {
        return numeroOrden;
    }

    public String getNumeroConsolidado() {
        return numeroConsolidado;
    }

    public String getCanalCodigo() {
        return canalCodigo;
    }

    public String getCanalNombre() {
        return canalNombre;
    }

    public String getDestinatarioNombre() {
        return destinatarioNombre;
    }

    public String getDestinatarioNumeroIdentificacion() {
        return destinatarioNumeroIdentificacion;
    }

    public String getDestinoNombre() {
        return destinoNombre;
    }

    public String getBodegaDestinoCodigo() {
        return bodegaDestinoCodigo;
    }

    public String getBodegaDestinoNombre() {
        return bodegaDestinoNombre;
    }

    public String getDestinoCiudadNombre() {
        return destinoCiudadNombre;
    }

    public String getDestinoDireccion() {
        return destinoDireccion;
    }

    public Date getFechaSugeridaEntregaMinima() {
        return fechaSugeridaEntregaMinima;
    }

    public Date getFechaSugeridaEntregaMaxima() {
        return fechaSugeridaEntregaMaxima;
    }

    public Time getHoraSugeridaEntregaMinima() {
        return horaSugeridaEntregaMinima;
    }

    public Time getHoraSugeridaEntregaMaxima() {
        return horaSugeridaEntregaMaxima;
    }

    public Time getHoraSugeridaEntregaMinimaAdicional() {
        return horaSugeridaEntregaMinimaAdicional;
    }

    public Time getHoraSugeridaEntregaMaximaAdicional() {
        return horaSugeridaEntregaMaximaAdicional;
    }

    public Date getFechaPlaneadaAlistamiento() {
        return fechaPlaneadaAlistamiento;
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

    public Time getHoraPlaneadaEntregaMinimaAdicional() {
        return horaPlaneadaEntregaMinimaAdicional;
    }

    public Time getHoraPlaneadaEntregaMaximaAdicional() {
        return horaPlaneadaEntregaMaximaAdicional;
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

    public void setTipoServicioNombre(String value) {
        this.tipoServicioNombre = coalesce(value, "");
    }

    public void setClienteCodigo(String value) {
        this.clienteCodigo = coalesce(value, "");
    }

    public void setClienteNombre(String value) {
        this.clienteNombre = coalesce(value, "");
    }

    public void setEstadoOrden(com.tacticlogistics.domain.model.oms.EstadoOrdenType value) {
        this.estadoOrden = value;
    }

    public void setNumeroOrden(String value) {
        this.numeroOrden = coalesce(value, "");
    }

    public void setNumeroConsolidado(String value) {
        this.numeroConsolidado = coalesce(value, "");
    }

    public void setCanalCodigo(String value) {
        this.canalCodigo = coalesce(value, "");
    }

    public void setCanalNombre(String value) {
        this.canalNombre = coalesce(value, "");
    }

    public void setDestinatarioNombre(String value) {
        this.destinatarioNombre = coalesce(value, "");
    }

    public void setDestinatarioNumeroIdentificacion(String value) {
        this.destinatarioNumeroIdentificacion = coalesce(value, "");
    }

    public void setDestinoNombre(String value) {
        this.destinoNombre = coalesce(value, "");
    }

    public void setBodegaDestinoCodigo(String value) {
        this.bodegaDestinoCodigo = coalesce(value, "");
    }

    public void setBodegaDestinoNombre(String value) {
        this.bodegaDestinoNombre = coalesce(value, "");
    }

    public void setDestinoCiudadNombre(String value) {
        this.destinoCiudadNombre = coalesce(value, "");
    }

    public void setDestinoDireccion(String value) {
        this.destinoDireccion = coalesce(value, "");
    }

    public void setFechaSugeridaEntregaMinima(Date value) {
        this.fechaSugeridaEntregaMinima = value;
    }

    public void setFechaSugeridaEntregaMaxima(Date value) {
        this.fechaSugeridaEntregaMaxima = value;
    }

    public void setHoraSugeridaEntregaMinima(Time value) {
        this.horaSugeridaEntregaMinima = value;
    }

    public void setHoraSugeridaEntregaMaxima(Time value) {
        this.horaSugeridaEntregaMaxima = value;
    }

    public void setHoraSugeridaEntregaMinimaAdicional(Time value) {
        this.horaSugeridaEntregaMinimaAdicional = value;
    }

    public void setHoraSugeridaEntregaMaximaAdicional(Time value) {
        this.horaSugeridaEntregaMaximaAdicional = value;
    }

    public void setFechaPlaneadaAlistamiento(Date value) {
        this.fechaPlaneadaAlistamiento = value;
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

    public void setHoraPlaneadaEntregaMinimaAdicional(Time value) {
        this.horaPlaneadaEntregaMinimaAdicional = value;
    }

    public void setHoraPlaneadaEntregaMaximaAdicional(Time value) {
        this.horaPlaneadaEntregaMaximaAdicional = value;
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

    public ConsultaOrdenesDto() {
        super();
        this.setTipoServicioNombre("");
        this.setClienteCodigo("");
        this.setClienteNombre("");
        this.setNumeroOrden("");
        this.setNumeroConsolidado("");
        this.setCanalCodigo("");
        this.setCanalNombre("");
        this.setDestinatarioNombre("");
        this.setDestinatarioNumeroIdentificacion("");
        this.setDestinoNombre("");
        this.setBodegaDestinoCodigo("");
        this.setBodegaDestinoNombre("");
        this.setDestinoCiudadNombre("");
        this.setDestinoDireccion("");
        this.setUsuarioActualizacion("");
        this.setUsuarioConfirmacion("");
        this.setUsuarioAprobacionCliente("");
    }

    public ConsultaOrdenesDto(
            Integer id, 
            String tipoServicioNombre, 
            String clienteCodigo, String clienteNombre,
            com.tacticlogistics.domain.model.oms.EstadoOrdenType estadoOrden, 
            String numeroOrden, 
            String numeroConsolidado,

            String canalCodigo, 
            String canalNombre, 
            String destinatarioNombre, 
            String destinatarioNumeroIdentificacion,
            String destinoNombre, 
            String bodegaDestinoCodigo, 
            String bodegaDestinoNombre, 

            String destinoCiudadNombre,
            String destinoDireccion,
            
            Date fechaSugeridaEntregaMinima, Date fechaSugeridaEntregaMaxima,
            Date horaSugeridaEntregaMinima, Date horaSugeridaEntregaMaxima,
            
            Date fechaActualizacion,
            String usuarioActualizacion, 
            Date fechaConfirmacion, 
            String usuarioConfirmacion,
            Date fechaAprobacionCliente, 
            String usuarioAprobacionCliente
            ) {
        this();
        this.setId(id);
        this.setTipoServicioNombre(tipoServicioNombre);
        this.setClienteCodigo(clienteCodigo);
        this.setClienteNombre(clienteNombre);
        this.setEstadoOrden(estadoOrden);
        this.setNumeroOrden(numeroOrden);
        this.setNumeroConsolidado(numeroConsolidado);
        
        this.setCanalCodigo(canalCodigo);
        this.setCanalNombre(canalNombre);
        this.setDestinatarioNombre(destinatarioNombre);
        this.setDestinatarioNumeroIdentificacion(destinatarioNumeroIdentificacion);
        this.setDestinoNombre(destinoNombre);
        this.setBodegaDestinoCodigo(bodegaDestinoCodigo);
        this.setBodegaDestinoNombre(bodegaDestinoNombre);
        
        this.setDestinoCiudadNombre(destinoCiudadNombre);
        this.setDestinoDireccion(destinoDireccion);
        
        this.setFechaSugeridaEntregaMinima(fechaSugeridaEntregaMinima);
        this.setFechaSugeridaEntregaMaxima(fechaSugeridaEntregaMaxima);
        this.setHoraSugeridaEntregaMinima((horaSugeridaEntregaMinima==null)?null:new Time(horaSugeridaEntregaMinima.getTime()));
        this.setHoraSugeridaEntregaMaxima((horaSugeridaEntregaMaxima==null)?null:new Time(horaSugeridaEntregaMaxima.getTime()));
        this.setHoraSugeridaEntregaMinimaAdicional(null);
        this.setHoraSugeridaEntregaMaximaAdicional(null);
        
        this.setFechaPlaneadaAlistamiento(null);
        this.setFechaPlaneadaEntregaMinima(null);
        this.setFechaPlaneadaEntregaMaxima(null);
        this.setHoraPlaneadaEntregaMinima(null);
        this.setHoraPlaneadaEntregaMaxima(null);
        this.setHoraPlaneadaEntregaMinimaAdicional(null);
        this.setHoraPlaneadaEntregaMaximaAdicional(null);
        
        this.setFechaActualizacion(fechaActualizacion);
        this.setUsuarioActualizacion(usuarioActualizacion);
        this.setFechaConfirmacion(fechaConfirmacion);
        this.setUsuarioConfirmacion(usuarioConfirmacion);
        this.setFechaAprobacionCliente(fechaAprobacionCliente);
        this.setUsuarioAprobacionCliente(usuarioAprobacionCliente);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ConsultaOrdenesDto [id=").append(id).append(", ");
        if (tipoServicioNombre != null) {
            builder.append("tipoServicioNombre=").append(tipoServicioNombre).append(", ");
        }
        if (clienteCodigo != null) {
            builder.append("clienteCodigo=").append(clienteCodigo).append(", ");
        }
        if (clienteNombre != null) {
            builder.append("clienteNombre=").append(clienteNombre).append(", ");
        }
        if (estadoOrden != null) {
            builder.append("estadoOrden=").append(estadoOrden).append(", ");
        }
        if (numeroOrden != null) {
            builder.append("numeroOrden=").append(numeroOrden).append(", ");
        }
        if (numeroConsolidado != null) {
            builder.append("numeroDocumentoConsolidadoCliente=").append(numeroConsolidado).append(", ");
        }
        if (canalCodigo != null) {
            builder.append("canalCodigo=").append(canalCodigo).append(", ");
        }
        if (canalNombre != null) {
            builder.append("canalNombre=").append(canalNombre).append(", ");
        }
        if (destinatarioNombre != null) {
            builder.append("destinatarioNombre=").append(destinatarioNombre).append(", ");
        }
        if (destinatarioNumeroIdentificacion != null) {
            builder.append("destinatarioNumeroIdentificacion=").append(destinatarioNumeroIdentificacion).append(", ");
        }
        if (destinoNombre != null) {
            builder.append("destinoNombre=").append(destinoNombre).append(", ");
        }
        if (bodegaDestinoCodigo != null) {
            builder.append("bodegaDestinoCodigo=").append(bodegaDestinoCodigo).append(", ");
        }
        if (bodegaDestinoNombre != null) {
            builder.append("bodegaDestinoNombre=").append(bodegaDestinoNombre).append(", ");
        }
        if (destinoCiudadNombre != null) {
            builder.append("destinoCiudadNombre=").append(destinoCiudadNombre).append(", ");
        }
        if (destinoDireccion != null) {
            builder.append("destinoDireccion=").append(destinoDireccion).append(", ");
        }
        if (fechaSugeridaEntregaMinima != null) {
            builder.append("fechaSugeridaEntregaMinima=").append(fechaSugeridaEntregaMinima).append(", ");
        }
        if (fechaSugeridaEntregaMaxima != null) {
            builder.append("fechaSugeridaEntregaMaxima=").append(fechaSugeridaEntregaMaxima).append(", ");
        }
        if (horaSugeridaEntregaMinima != null) {
            builder.append("horaSugeridaEntregaMinima=").append(horaSugeridaEntregaMinima).append(", ");
        }
        if (horaSugeridaEntregaMaxima != null) {
            builder.append("horaSugeridaEntregaMaxima=").append(horaSugeridaEntregaMaxima).append(", ");
        }
        if (horaSugeridaEntregaMinimaAdicional != null) {
            builder.append("horaSugeridaEntregaMinimaAdicional=").append(horaSugeridaEntregaMinimaAdicional)
                    .append(", ");
        }
        if (horaSugeridaEntregaMaximaAdicional != null) {
            builder.append("horaSugeridaEntregaMaximaAdicional=").append(horaSugeridaEntregaMaximaAdicional)
                    .append(", ");
        }
        if (fechaPlaneadaAlistamiento != null) {
            builder.append("fechaPlaneadaAlistamiento=").append(fechaPlaneadaAlistamiento).append(", ");
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
        if (horaPlaneadaEntregaMinimaAdicional != null) {
            builder.append("horaPlaneadaEntregaMinimaAdicional=").append(horaPlaneadaEntregaMinimaAdicional)
                    .append(", ");
        }
        if (horaPlaneadaEntregaMaximaAdicional != null) {
            builder.append("horaPlaneadaEntregaMaximaAdicional=").append(horaPlaneadaEntregaMaximaAdicional)
                    .append(", ");
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
