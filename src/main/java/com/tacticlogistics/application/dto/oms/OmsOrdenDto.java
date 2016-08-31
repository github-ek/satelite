package com.tacticlogistics.application.dto.oms;

import static com.tacticlogistics.infrastructure.services.Basic.coalesce;

import java.sql.Time;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tacticlogistics.application.dto.common.ItemGenerico;
import com.tacticlogistics.domain.model.oms.EstadoOrdenType;
import com.tacticlogistics.domain.model.oms.EstadoSolicitudActualizacionDatosMaestrosType;
import com.tacticlogistics.domain.model.oms.OrigenOrdenType;

public class OmsOrdenDto {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 1L;
    private static final String dateFormat = "yyyy-MM-dd";

    private Integer id;

    // ---------------------------------------------------------------------------------------------------------
    private Integer tipoServicioId;
    private String tipoServicioNombre;

    // ---------------------------------------------------------------------------------------------------------
    private Integer clienteId;
    private String clienteCodigo;
    private String clienteNombre;

    // ---------------------------------------------------------------------------------------------------------
    private EstadoOrdenType estadoOrden;
    private OrigenOrdenType origenOrden;
    private String numeroOrden;

    // ---------------------------------------------------------------------------------------------------------
    private Integer consolidadoId;
    private String numeroConsolidado;

    // ---------------------------------------------------------------------------------------------------------
    private Integer canalId;
    private String canalCodigo;
    private String canalNombre;
    private String canalCodigoAlterno;

    // ---------------------------------------------------------------------------------------------------------
    private Integer destinatarioId;
    private String destinatarioCodigo;
    private String destinatarioNombre;
    private String destinatarioNumeroIdentificacion;

    private String destinatarioCodigoAlterno;
    private String destinatarioNombreAlterno;
    private String destinatarioNumeroIdentificacionAlterno;
    private String destinatarioDigitoVerificacionAlterno;

    // ---------------------------------------------------------------------------------------------------------
    private Integer destinatarioCiudadId;
    private String destinatarioCiudadNombre;
    private String destinatarioDireccion;
    private String destinatarioIndicacionesDireccion;

    private String destinatarioContactoNombres;
    private String destinatarioContactoTelefonos;
    private String destinatarioContactoEmail;
    private EstadoSolicitudActualizacionDatosMaestrosType estadoSolicitudActualizacionDatosContactoDestinatario;

    private String destinatarioCiudadNombreAlterno;

    // ---------------------------------------------------------------------------------------------------------
    private Integer destinoId;
    private String destinoCodigo;
    private String destinoNombre;
    private String destinoCodigoAlterno;
    private String destinoNombreAlterno;

    // ---------------------------------------------------------------------------------------------------------
    private Integer bodegaDestinoId;
    private String bodegaDestinoCodigo;
    private String bodegaDestinoNombre;
    private String bodegaDestinoCodigoAlterno;
    private String bodegaDestinoNombreAlterno;

    // ---------------------------------------------------------------------------------------------------------
    private Integer destinoCiudadId;
    private String destinoCiudadNombre;
    private String destinoDireccion;
    private String destinoIndicacionesDireccion;

    private String destinoContactoNombres;
    private String destinoContactoTelefonos;
    private String destinoContactoEmail;

    private EstadoSolicitudActualizacionDatosMaestrosType estadoSolicitudActualizacionDatosContactoDestino;

    private String destinoCiudadNombreAlterno;

    private boolean requiereServicioDistribucion;

    // ---------------------------------------------------------------------------------------------------------
    @DateTimeFormat(pattern = dateFormat)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = dateFormat)
    private Date fechaSugeridaEntregaMinima;
    @DateTimeFormat(pattern = dateFormat)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = dateFormat)
    private Date fechaSugeridaEntregaMaxima;

    private Time horaSugeridaEntregaMinima;
    private Time horaSugeridaEntregaMaxima;
    private Time horaSugeridaEntregaMinimaAdicional;
    private Time horaSugeridaEntregaMaximaAdicional;

    @DateTimeFormat(pattern = dateFormat)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = dateFormat)
    private Date fechaPlaneadaAlistamiento;

    @DateTimeFormat(pattern = dateFormat)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = dateFormat)
    private Date fechaPlaneadaEntregaMinima;

    @DateTimeFormat(pattern = dateFormat)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = dateFormat)
    private Date fechaPlaneadaEntregaMaxima;

    private Time horaPlaneadaEntregaMinima;
    private Time horaPlaneadaEntregaMaxima;
    private Time horaPlaneadaEntregaMinimaAdicional;
    private Time horaPlaneadaEntregaMaximaAdicional;

    // ---------------------------------------------------------------------------------------------------------
    private Set<ItemGenerico<Integer>> requerimientosDistribucion;
    private String notasRequerimientosDistribucion;

    private Set<ItemGenerico<Integer>> requerimientosAlistamiento;
    private String notasRequerimientosAlistamiento;

    // ---------------------------------------------------------------------------------------------------------
    @DateTimeFormat(pattern = dateFormat)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = dateFormat)
    private Date fechaConfirmacion;
    private String usuarioConfirmacion;
    private String notasConfirmacion;

    // ---------------------------------------------------------------------------------------------------------
    @DateTimeFormat(pattern = dateFormat)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = dateFormat)
    private Date fechaAprobacionCliente;
    private String usuarioAprobacionCliente;
    private String notasAprobacionCliente;

    @DateTimeFormat(pattern = dateFormat)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = dateFormat)
    private Date fechaSolicitudRevisionCliente;
    private String usuarioSolicitudRevisionCliente;
    private String notasSolicitudRevisionCliente;
    private Integer causalSolicitudRevisionClienteId;
    private String causalSolicitudRevisionClienteNombre;

    // ---------------------------------------------------------------------------------------------------------
    @DateTimeFormat(pattern = dateFormat)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = dateFormat)
    private Date fechaInicioPlaneacionLogistica;
    private String usuarioInicioPlaneacionLogistica;
    private Date fechaFinPlaneacionLogistica;

    private String usuarioFinPlaneacionLogistica;
    private String notasPlaneacionLogistica;

    @DateTimeFormat(pattern = dateFormat)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = dateFormat)
    private Date fechaSolicitudRevisionPlaneacionLogistica;
    private String usuarioSolicitudRevisionPlaneacionLogistica;
    private String notasSolicitudRevisionPlaneacionLogistica;
    private Integer causalSolicitudRevisionPlaneacionLogisticaId;
    private String causalSolicitudRevisionPlaneacionLogisticaNombre;

    // ---------------------------------------------------------------------------------------------------------
    @DateTimeFormat(pattern = dateFormat)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = dateFormat)
    private Date fechaInicioEjecucionLogistica;
    @DateTimeFormat(pattern = dateFormat)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = dateFormat)
    private Date fechaFinEjecucionLogistica;

    // ---------------------------------------------------------------------------------------------------------
    @DateTimeFormat(pattern = dateFormat)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = dateFormat)
    private Date fechaCreacion;
    private String usuarioCreacion;

    @DateTimeFormat(pattern = dateFormat)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = dateFormat)
    private Date fechaActualizacion;
    private String usuarioActualizacion;

    // ---------------------------------------------------------------------------------------------------------
    @DateTimeFormat(pattern = dateFormat)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = dateFormat)
    private Date fechaAnulacion;
    private String usuarioAnulacion;
    private String notasAnulacion;
    private Integer causalAnulacionId;
    private String causalAnulacionNombre;

    public Integer getId() {
        return id;
    }

    public Integer getTipoServicioId() {
        return tipoServicioId;
    }

    public String getTipoServicioNombre() {
        return tipoServicioNombre;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public String getClienteCodigo() {
        return clienteCodigo;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public EstadoOrdenType getEstadoOrden() {
        return estadoOrden;
    }

    public OrigenOrdenType getOrigenOrden() {
        return origenOrden;
    }

    public String getNumeroOrden() {
        return numeroOrden;
    }

    public Integer getConsolidadoId() {
        return consolidadoId;
    }

    public String getNumeroConsolidado() {
        return numeroConsolidado;
    }

    public Integer getCanalId() {
        return canalId;
    }

    public String getCanalCodigo() {
        return canalCodigo;
    }

    public String getCanalNombre() {
        return canalNombre;
    }

    public String getCanalCodigoAlterno() {
        return canalCodigoAlterno;
    }

    public Integer getDestinatarioId() {
        return destinatarioId;
    }

    public String getDestinatarioCodigo() {
        return destinatarioCodigo;
    }

    public String getDestinatarioNombre() {
        return destinatarioNombre;
    }

    public String getDestinatarioNumeroIdentificacion() {
        return destinatarioNumeroIdentificacion;
    }

    public String getDestinatarioCodigoAlterno() {
        return destinatarioCodigoAlterno;
    }

    public String getDestinatarioNombreAlterno() {
        return destinatarioNombreAlterno;
    }

    public String getDestinatarioNumeroIdentificacionAlterno() {
        return destinatarioNumeroIdentificacionAlterno;
    }

    public String getDestinatarioDigitoVerificacionAlterno() {
        return destinatarioDigitoVerificacionAlterno;
    }

    public Integer getDestinatarioCiudadId() {
        return destinatarioCiudadId;
    }

    public String getDestinatarioCiudadNombre() {
        return destinatarioCiudadNombre;
    }

    public String getDestinatarioDireccion() {
        return destinatarioDireccion;
    }

    public String getDestinatarioIndicacionesDireccion() {
        return destinatarioIndicacionesDireccion;
    }

    public String getDestinatarioContactoNombres() {
        return destinatarioContactoNombres;
    }

    public String getDestinatarioContactoTelefonos() {
        return destinatarioContactoTelefonos;
    }

    public String getDestinatarioContactoEmail() {
        return destinatarioContactoEmail;
    }

    public EstadoSolicitudActualizacionDatosMaestrosType getEstadoSolicitudActualizacionDatosContactoDestinatario() {
        return estadoSolicitudActualizacionDatosContactoDestinatario;
    }

    public String getDestinatarioCiudadNombreAlterno() {
        return destinatarioCiudadNombreAlterno;
    }

    public Integer getDestinoId() {
        return destinoId;
    }

    public String getDestinoCodigo() {
        return destinoCodigo;
    }

    public String getDestinoNombre() {
        return destinoNombre;
    }

    public String getDestinoCodigoAlterno() {
        return destinoCodigoAlterno;
    }

    public String getDestinoNombreAlterno() {
        return destinoNombreAlterno;
    }

    public Integer getBodegaDestinoId() {
        return bodegaDestinoId;
    }

    public String getBodegaDestinoCodigo() {
        return bodegaDestinoCodigo;
    }

    public String getBodegaDestinoNombre() {
        return bodegaDestinoNombre;
    }

    public String getBodegaDestinoCodigoAlterno() {
        return bodegaDestinoCodigoAlterno;
    }

    public String getBodegaDestinoNombreAlterno() {
        return bodegaDestinoNombreAlterno;
    }

    public Integer getDestinoCiudadId() {
        return destinoCiudadId;
    }

    public String getDestinoCiudadNombre() {
        return destinoCiudadNombre;
    }

    public String getDestinoDireccion() {
        return destinoDireccion;
    }

    public String getDestinoIndicacionesDireccion() {
        return destinoIndicacionesDireccion;
    }

    public String getDestinoContactoNombres() {
        return destinoContactoNombres;
    }

    public String getDestinoContactoTelefonos() {
        return destinoContactoTelefonos;
    }

    public String getDestinoContactoEmail() {
        return destinoContactoEmail;
    }

    public EstadoSolicitudActualizacionDatosMaestrosType getEstadoSolicitudActualizacionDatosContactoDestino() {
        return estadoSolicitudActualizacionDatosContactoDestino;
    }

    public String getDestinoCiudadNombreAlterno() {
        return destinoCiudadNombreAlterno;
    }

    public boolean isRequiereServicioDistribucion() {
        return requiereServicioDistribucion;
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

    public Set<ItemGenerico<Integer>> getRequerimientosDistribucion() {
        return requerimientosDistribucion;
    }

    public String getNotasRequerimientosDistribucion() {
        return notasRequerimientosDistribucion;
    }

    public Set<ItemGenerico<Integer>> getRequerimientosAlistamiento() {
        return requerimientosAlistamiento;
    }

    public String getNotasRequerimientosAlistamiento() {
        return notasRequerimientosAlistamiento;
    }

    public Date getFechaConfirmacion() {
        return fechaConfirmacion;
    }

    public String getUsuarioConfirmacion() {
        return usuarioConfirmacion;
    }

    public String getNotasConfirmacion() {
        return notasConfirmacion;
    }

    public Date getFechaAprobacionCliente() {
        return fechaAprobacionCliente;
    }

    public String getUsuarioAprobacionCliente() {
        return usuarioAprobacionCliente;
    }

    public String getNotasAprobacionCliente() {
        return notasAprobacionCliente;
    }

    public Date getFechaSolicitudRevisionCliente() {
        return fechaSolicitudRevisionCliente;
    }

    public String getUsuarioSolicitudRevisionCliente() {
        return usuarioSolicitudRevisionCliente;
    }

    public String getNotasSolicitudRevisionCliente() {
        return notasSolicitudRevisionCliente;
    }

    public Integer getCausalSolicitudRevisionClienteId() {
        return causalSolicitudRevisionClienteId;
    }

    public String getCausalSolicitudRevisionClienteNombre() {
        return causalSolicitudRevisionClienteNombre;
    }

    public Date getFechaInicioPlaneacionLogistica() {
        return fechaInicioPlaneacionLogistica;
    }

    public String getUsuarioInicioPlaneacionLogistica() {
        return usuarioInicioPlaneacionLogistica;
    }

    public Date getFechaFinPlaneacionLogistica() {
        return fechaFinPlaneacionLogistica;
    }

    public String getUsuarioFinPlaneacionLogistica() {
        return usuarioFinPlaneacionLogistica;
    }

    public String getNotasPlaneacionLogistica() {
        return notasPlaneacionLogistica;
    }

    public Date getFechaSolicitudRevisionPlaneacionLogistica() {
        return fechaSolicitudRevisionPlaneacionLogistica;
    }

    public String getUsuarioSolicitudRevisionPlaneacionLogistica() {
        return usuarioSolicitudRevisionPlaneacionLogistica;
    }

    public String getNotasSolicitudRevisionPlaneacionLogistica() {
        return notasSolicitudRevisionPlaneacionLogistica;
    }

    public Integer getCausalSolicitudRevisionPlaneacionLogisticaId() {
        return causalSolicitudRevisionPlaneacionLogisticaId;
    }

    public String getCausalSolicitudRevisionPlaneacionLogisticaNombre() {
        return causalSolicitudRevisionPlaneacionLogisticaNombre;
    }

    public Date getFechaInicioEjecucionLogistica() {
        return fechaInicioEjecucionLogistica;
    }

    public Date getFechaFinEjecucionLogistica() {
        return fechaFinEjecucionLogistica;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public String getUsuarioActualizacion() {
        return usuarioActualizacion;
    }

    public Date getFechaAnulacion() {
        return fechaAnulacion;
    }

    public String getUsuarioAnulacion() {
        return usuarioAnulacion;
    }

    public String getNotasAnulacion() {
        return notasAnulacion;
    }

    public Integer getCausalAnulacionId() {
        return causalAnulacionId;
    }

    public String getCausalAnulacionNombre() {
        return causalAnulacionNombre;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    public void setTipoServicioId(Integer value) {
        this.tipoServicioId = value;
    }

    public void setTipoServicioNombre(String value) {
        this.tipoServicioNombre = coalesce(value, "");
    }

    public void setClienteId(Integer value) {
        this.clienteId = value;
    }

    public void setClienteCodigo(String value) {
        this.clienteCodigo = coalesce(value, "");
    }

    public void setClienteNombre(String value) {
        this.clienteNombre = coalesce(value, "");
    }

    public void setEstadoOrden(EstadoOrdenType value) {
        this.estadoOrden = value;
    }

    public void setOrigenOrden(OrigenOrdenType value) {
        this.origenOrden = value;
    }

    public void setNumeroOrden(String value) {
        this.numeroOrden = coalesce(value, "");
    }

    public void setConsolidadoId(Integer value) {
        this.consolidadoId = value;
    }

    public void setNumeroDocumentoConsolidadoCliente(String value) {
        this.numeroConsolidado = coalesce(value, "");
    }

    public void setCanalId(Integer value) {
        this.canalId = value;
    }

    public void setCanalCodigo(String value) {
        this.canalCodigo = coalesce(value, "");
    }

    public void setCanalNombre(String value) {
        this.canalNombre = coalesce(value, "");
    }

    public void setCanalCodigoAlterno(String value) {
        this.canalCodigoAlterno = coalesce(value, "");
    }

    public void setDestinatarioId(Integer value) {
        this.destinatarioId = value;
    }

    public void setDestinatarioCodigo(String value) {
        this.destinatarioCodigo = coalesce(value, "");
    }

    public void setDestinatarioNombre(String value) {
        this.destinatarioNombre = coalesce(value, "");
    }

    public void setDestinatarioNumeroIdentificacion(String value) {
        this.destinatarioNumeroIdentificacion = coalesce(value, "");
    }

    public void setDestinatarioCodigoAlterno(String value) {
        this.destinatarioCodigoAlterno = coalesce(value, "");
    }

    public void setDestinatarioNombreAlterno(String value) {
        this.destinatarioNombreAlterno = coalesce(value, "");
    }

    public void setDestinatarioNumeroIdentificacionAlterno(String value) {
        this.destinatarioNumeroIdentificacionAlterno = coalesce(value, "");
    }

    public void setDestinatarioDigitoVerificacionAlterno(String value) {
        this.destinatarioDigitoVerificacionAlterno = coalesce(value, "");
    }

    public void setDestinatarioCiudadId(Integer value) {
        this.destinatarioCiudadId = value;
    }

    public void setDestinatarioCiudadNombre(String value) {
        this.destinatarioCiudadNombre = coalesce(value, "");
    }

    public void setDestinatarioDireccion(String value) {
        this.destinatarioDireccion = coalesce(value, "");
    }

    public void setDestinatarioIndicacionesDireccion(String value) {
        this.destinatarioIndicacionesDireccion = coalesce(value, "");
    }

    public void setDestinatarioContactoNombres(String value) {
        this.destinatarioContactoNombres = coalesce(value, "");
    }

    public void setDestinatarioContactoTelefonos(String value) {
        this.destinatarioContactoTelefonos = coalesce(value, "");
    }

    public void setDestinatarioContactoEmail(String value) {
        this.destinatarioContactoEmail = coalesce(value, "");
    }

    public void setEstadoSolicitudActualizacionDatosContactoDestinatario(
            EstadoSolicitudActualizacionDatosMaestrosType value) {
        this.estadoSolicitudActualizacionDatosContactoDestinatario = value;
    }

    public void setDestinatarioCiudadNombreAlterno(String value) {
        this.destinatarioCiudadNombreAlterno = coalesce(value, "");
    }

    public void setDestinoId(Integer value) {
        this.destinoId = value;
    }

    public void setDestinoCodigo(String value) {
        this.destinoCodigo = coalesce(value, "");
    }

    public void setDestinoNombre(String value) {
        this.destinoNombre = coalesce(value, "");
    }

    public void setDestinoCodigoAlterno(String value) {
        this.destinoCodigoAlterno = coalesce(value, "");
    }

    public void setDestinoNombreAlterno(String value) {
        this.destinoNombreAlterno = coalesce(value, "");
    }

    public void setBodegaDestinoId(Integer value) {
        this.bodegaDestinoId = value;
    }

    public void setBodegaDestinoCodigo(String value) {
        this.bodegaDestinoCodigo = coalesce(value, "");
    }

    public void setBodegaDestinoNombre(String value) {
        this.bodegaDestinoNombre = coalesce(value, "");
    }

    public void setBodegaDestinoCodigoAlterno(String value) {
        this.bodegaDestinoCodigoAlterno = coalesce(value, "");
    }

    public void setBodegaDestinoNombreAlterno(String value) {
        this.bodegaDestinoNombreAlterno = coalesce(value, "");
    }

    public void setDestinoCiudadId(Integer value) {
        this.destinoCiudadId = value;
    }

    public void setDestinoCiudadNombre(String value) {
        this.destinoCiudadNombre = coalesce(value, "");
    }

    public void setDestinoDireccion(String value) {
        this.destinoDireccion = coalesce(value, "");
    }

    public void setDestinoIndicacionesDireccion(String value) {
        this.destinoIndicacionesDireccion = coalesce(value, "");
    }

    public void setDestinoContactoNombres(String value) {
        this.destinoContactoNombres = coalesce(value, "");
    }

    public void setDestinoContactoTelefonos(String value) {
        this.destinoContactoTelefonos = coalesce(value, "");
    }

    public void setDestinoContactoEmail(String value) {
        this.destinoContactoEmail = coalesce(value, "");
    }

    public void setEstadoSolicitudActualizacionDatosContactoDestino(
            EstadoSolicitudActualizacionDatosMaestrosType value) {
        this.estadoSolicitudActualizacionDatosContactoDestino = value;
    }

    public void setDestinoCiudadNombreAlterno(String value) {
        this.destinoCiudadNombreAlterno = coalesce(value, "");
    }

    public void setRequiereServicioDistribucion(boolean value) {
        this.requiereServicioDistribucion = value;
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

    public void setRequerimientosDistribucion(Set<ItemGenerico<Integer>> value) {
        this.requerimientosDistribucion = value;
    }

    public void setNotasRequerimientosDistribucion(String value) {
        this.notasRequerimientosDistribucion = coalesce(value, "");
    }

    public void setRequerimientosAlistamiento(Set<ItemGenerico<Integer>> value) {
        this.requerimientosAlistamiento = value;
    }

    public void setNotasRequerimientosAlistamiento(String value) {
        this.notasRequerimientosAlistamiento = coalesce(value, "");
    }

    public void setFechaConfirmacion(Date value) {
        this.fechaConfirmacion = value;
    }

    public void setUsuarioConfirmacion(String value) {
        this.usuarioConfirmacion = coalesce(value, "");
    }

    public void setNotasConfirmacion(String value) {
        this.notasConfirmacion = coalesce(value, "");
    }

    public void setFechaAprobacionCliente(Date value) {
        this.fechaAprobacionCliente = value;
    }

    public void setUsuarioAprobacionCliente(String value) {
        this.usuarioAprobacionCliente = coalesce(value, "");
    }

    public void setNotasAprobacionCliente(String value) {
        this.notasAprobacionCliente = coalesce(value, "");
    }

    public void setFechaSolicitudRevisionCliente(Date value) {
        this.fechaSolicitudRevisionCliente = value;
    }

    public void setUsuarioSolicitudRevisionCliente(String value) {
        this.usuarioSolicitudRevisionCliente = coalesce(value, "");
    }

    public void setNotasSolicitudRevisionCliente(String value) {
        this.notasSolicitudRevisionCliente = coalesce(value, "");
    }

    public void setCausalSolicitudRevisionClienteId(Integer value) {
        this.causalSolicitudRevisionClienteId = value;
    }

    public void setCausalSolicitudRevisionClienteNombre(String value) {
        this.causalSolicitudRevisionClienteNombre = coalesce(value, "");
    }

    public void setFechaInicioPlaneacionLogistica(Date value) {
        this.fechaInicioPlaneacionLogistica = value;
    }

    public void setUsuarioInicioPlaneacionLogistica(String value) {
        this.usuarioInicioPlaneacionLogistica = coalesce(value, "");
    }

    public void setFechaFinPlaneacionLogistica(Date value) {
        this.fechaFinPlaneacionLogistica = value;
    }

    public void setUsuarioFinPlaneacionLogistica(String value) {
        this.usuarioFinPlaneacionLogistica = coalesce(value, "");
    }

    public void setNotasPlaneacionLogistica(String value) {
        this.notasPlaneacionLogistica = coalesce(value, "");
    }

    public void setFechaSolicitudRevisionPlaneacionLogistica(Date value) {
        this.fechaSolicitudRevisionPlaneacionLogistica = value;
    }

    public void setUsuarioSolicitudRevisionPlaneacionLogistica(String value) {
        this.usuarioSolicitudRevisionPlaneacionLogistica = coalesce(value, "");
    }

    public void setNotasSolicitudRevisionPlaneacionLogistica(String value) {
        this.notasSolicitudRevisionPlaneacionLogistica = coalesce(value, "");
    }

    public void setCausalSolicitudRevisionPlaneacionLogisticaId(Integer value) {
        this.causalSolicitudRevisionPlaneacionLogisticaId = value;
    }

    public void setCausalSolicitudRevisionPlaneacionLogisticaNombre(String value) {
        this.causalSolicitudRevisionPlaneacionLogisticaNombre = coalesce(value, "");
    }

    public void setFechaInicioEjecucionLogistica(Date value) {
        this.fechaInicioEjecucionLogistica = value;
    }

    public void setFechaFinEjecucionLogistica(Date value) {
        this.fechaFinEjecucionLogistica = value;
    }

    public void setFechaCreacion(Date value) {
        this.fechaCreacion = value;
    }

    public void setUsuarioCreacion(String value) {
        this.usuarioCreacion = coalesce(value, "");
    }

    public void setFechaActualizacion(Date value) {
        this.fechaActualizacion = value;
    }

    public void setUsuarioActualizacion(String value) {
        this.usuarioActualizacion = coalesce(value, "");
    }

    public void setFechaAnulacion(Date value) {
        this.fechaAnulacion = value;
    }

    public void setUsuarioAnulacion(String value) {
        this.usuarioAnulacion = coalesce(value, "");
    }

    public void setNotasAnulacion(String value) {
        this.notasAnulacion = coalesce(value, "");
    }

    public void setCausalAnulacionId(Integer value) {
        this.causalAnulacionId = value;
    }

    public void setCausalAnulacionNombre(String value) {
        this.causalAnulacionNombre = coalesce(value, "");
    }

    public OmsOrdenDto() {
        super();
        this.setTipoServicioNombre("");

        this.setClienteCodigo("");
        this.setClienteNombre("");

        this.setNumeroOrden("");
        this.setNumeroDocumentoConsolidadoCliente("");

        this.setCanalCodigo("");
        this.setCanalNombre("");
        this.setCanalCodigoAlterno("");

        this.setDestinatarioCodigo("");
        this.setDestinatarioNombre("");
        this.setDestinatarioNumeroIdentificacion("");
        this.setDestinatarioCodigoAlterno("");
        this.setDestinatarioNombreAlterno("");
        this.setDestinatarioNumeroIdentificacionAlterno("");
        this.setDestinatarioDigitoVerificacionAlterno("");

        this.setDestinatarioCiudadNombre("");
        this.setDestinatarioDireccion("");
        this.setDestinatarioIndicacionesDireccion("");
        this.setDestinatarioContactoNombres("");
        this.setDestinatarioContactoTelefonos("");
        this.setDestinatarioContactoEmail("");
        this.setDestinatarioCiudadNombreAlterno("");

        this.setDestinoCodigo("");
        this.setDestinoNombre("");
        this.setDestinoCodigoAlterno("");
        this.setDestinoNombreAlterno("");

        this.setBodegaDestinoCodigo("");
        this.setBodegaDestinoNombre("");
        this.setBodegaDestinoCodigoAlterno("");
        this.setBodegaDestinoNombreAlterno("");

        this.setDestinoCiudadNombre("");
        this.setDestinoDireccion("");
        this.setDestinoIndicacionesDireccion("");
        this.setDestinoContactoNombres("");
        this.setDestinoContactoTelefonos("");
        this.setDestinoContactoEmail("");
        this.setDestinoCiudadNombreAlterno("");

        this.setRequiereServicioDistribucion(true);

        this.setRequerimientosDistribucion(new HashSet<>());
        this.setNotasRequerimientosDistribucion("");

        this.setRequerimientosAlistamiento(new HashSet<>());
        this.setNotasRequerimientosAlistamiento("");

        this.setUsuarioConfirmacion("");
        this.setNotasConfirmacion("");

        this.setUsuarioAprobacionCliente("");
        this.setNotasAprobacionCliente("");

        this.setUsuarioSolicitudRevisionCliente("");
        this.setNotasSolicitudRevisionCliente("");
        this.setCausalSolicitudRevisionClienteNombre("");

        this.setUsuarioInicioPlaneacionLogistica("");

        this.setUsuarioFinPlaneacionLogistica("");
        this.setNotasPlaneacionLogistica("");

        this.setUsuarioSolicitudRevisionPlaneacionLogistica("");
        this.setNotasSolicitudRevisionPlaneacionLogistica("");
        this.setCausalSolicitudRevisionPlaneacionLogisticaNombre("");

        this.setUsuarioCreacion("");

        this.setUsuarioActualizacion("");

        this.setUsuarioAnulacion("");
        this.setNotasAnulacion("");
        this.setCausalAnulacionNombre("");
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        OmsOrdenDto other = (OmsOrdenDto) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
	public String toString() {
		return "OmsOrdenDto [" + (id != null ? "id=" + id + ", " : "")
				+ (tipoServicioId != null ? "tipoServicioId=" + tipoServicioId + ", " : "")
				+ (tipoServicioNombre != null ? "tipoServicioNombre=" + tipoServicioNombre + ", " : "")
				+ (clienteId != null ? "clienteId=" + clienteId + ", " : "")
				+ (clienteCodigo != null ? "clienteCodigo=" + clienteCodigo + ", " : "")
				+ (clienteNombre != null ? "clienteNombre=" + clienteNombre + ", " : "")
				+ (estadoOrden != null ? "estadoOrden=" + estadoOrden + ", " : "")
				+ (origenOrden != null ? "origenOrden=" + origenOrden + ", " : "")
				+ (numeroOrden != null
						? "numeroOrden=" + numeroOrden + ", " : "")
				+ (consolidadoId != null ? "consolidadoId=" + consolidadoId + ", " : "")
				+ (numeroConsolidado != null
						? "numeroDocumentoConsolidadoCliente=" + numeroConsolidado + ", " : "")
				+ (canalId != null ? "canalId=" + canalId + ", " : "")
				+ (canalCodigo != null ? "canalCodigo=" + canalCodigo + ", " : "")
				+ (canalNombre != null ? "canalNombre=" + canalNombre + ", " : "")
				+ (canalCodigoAlterno != null ? "canalCodigoAlterno=" + canalCodigoAlterno + ", " : "")
				+ (destinatarioId != null ? "destinatarioId=" + destinatarioId + ", " : "")
				+ (destinatarioCodigo != null ? "destinatarioCodigo=" + destinatarioCodigo + ", " : "")
				+ (destinatarioNombre != null ? "destinatarioNombre=" + destinatarioNombre + ", " : "")
				+ (destinatarioNumeroIdentificacion != null
						? "destinatarioNumeroIdentificacion=" + destinatarioNumeroIdentificacion + ", " : "")
				+ (destinatarioCodigoAlterno != null ? "destinatarioCodigoAlterno=" + destinatarioCodigoAlterno + ", "
						: "")
				+ (destinatarioNombreAlterno != null ? "destinatarioNombreAlterno=" + destinatarioNombreAlterno + ", "
						: "")
				+ (destinatarioNumeroIdentificacionAlterno != null
						? "destinatarioNumeroIdentificacionAlterno=" + destinatarioNumeroIdentificacionAlterno + ", "
						: "")
				+ (destinatarioDigitoVerificacionAlterno != null
						? "destinatarioDigitoVerificacionAlterno=" + destinatarioDigitoVerificacionAlterno + ", " : "")
				+ (destinatarioCiudadId != null ? "destinatarioCiudadId=" + destinatarioCiudadId + ", " : "")
				+ (destinatarioCiudadNombre != null ? "destinatarioCiudadNombre=" + destinatarioCiudadNombre + ", "
						: "")
				+ (destinatarioDireccion != null ? "destinatarioDireccion=" + destinatarioDireccion + ", " : "")
				+ (destinatarioIndicacionesDireccion != null
						? "destinatarioIndicacionesDireccion=" + destinatarioIndicacionesDireccion + ", " : "")
				+ (destinatarioContactoNombres != null
						? "destinatarioContactoNombres=" + destinatarioContactoNombres + ", " : "")
				+ (destinatarioContactoTelefonos != null
						? "destinatarioContactoTelefonos=" + destinatarioContactoTelefonos + ", " : "")
				+ (destinatarioContactoEmail != null ? "destinatarioContactoEmail=" + destinatarioContactoEmail + ", "
						: "")
				+ (estadoSolicitudActualizacionDatosContactoDestinatario != null
						? "estadoSolicitudActualizacionDatosContactoDestinatario="
								+ estadoSolicitudActualizacionDatosContactoDestinatario + ", "
						: "")
				+ (destinatarioCiudadNombreAlterno != null
						? "destinatarioCiudadNombreAlterno=" + destinatarioCiudadNombreAlterno + ", " : "")
				+ (destinoId != null ? "destinoId=" + destinoId + ", " : "")
				+ (destinoCodigo != null ? "destinoCodigo=" + destinoCodigo + ", " : "")
				+ (destinoNombre != null ? "destinoNombre=" + destinoNombre + ", " : "")
				+ (destinoCodigoAlterno != null ? "destinoCodigoAlterno=" + destinoCodigoAlterno + ", " : "")
				+ (destinoNombreAlterno != null ? "destinoNombreAlterno=" + destinoNombreAlterno + ", " : "")
				+ (bodegaDestinoId != null ? "bodegaDestinoId=" + bodegaDestinoId + ", " : "")
				+ (bodegaDestinoCodigo != null ? "bodegaDestinoCodigo=" + bodegaDestinoCodigo + ", " : "")
				+ (bodegaDestinoNombre != null ? "bodegaDestinoNombre=" + bodegaDestinoNombre + ", " : "")
				+ (bodegaDestinoCodigoAlterno != null
						? "bodegaDestinoCodigoAlterno=" + bodegaDestinoCodigoAlterno + ", " : "")
				+ (bodegaDestinoNombreAlterno != null
						? "bodegaDestinoNombreAlterno=" + bodegaDestinoNombreAlterno + ", " : "")
				+ (destinoCiudadId != null ? "destinoCiudadId=" + destinoCiudadId + ", " : "")
				+ (destinoCiudadNombre != null ? "destinoCiudadNombre=" + destinoCiudadNombre + ", " : "")
				+ (destinoDireccion != null ? "destinoDireccion=" + destinoDireccion + ", " : "")
				+ (destinoIndicacionesDireccion != null
						? "destinoIndicacionesDireccion=" + destinoIndicacionesDireccion + ", " : "")
				+ (destinoContactoNombres != null ? "destinoContactoNombres=" + destinoContactoNombres + ", " : "")
				+ (destinoContactoTelefonos != null ? "destinoContactoTelefonos=" + destinoContactoTelefonos + ", "
						: "")
				+ (destinoContactoEmail != null ? "destinoContactoEmail=" + destinoContactoEmail + ", " : "")
				+ (estadoSolicitudActualizacionDatosContactoDestino != null
						? "estadoSolicitudActualizacionDatosContactoDestino="
								+ estadoSolicitudActualizacionDatosContactoDestino + ", "
						: "")
				+ (destinoCiudadNombreAlterno != null
						? "destinoCiudadNombreAlterno=" + destinoCiudadNombreAlterno + ", " : "")
				+ "requiereServicioDistribucion=" + requiereServicioDistribucion + ", "
				+ (fechaSugeridaEntregaMinima != null
						? "fechaSugeridaEntregaMinima=" + fechaSugeridaEntregaMinima + ", " : "")
				+ (fechaSugeridaEntregaMaxima != null
						? "fechaSugeridaEntregaMaxima=" + fechaSugeridaEntregaMaxima + ", " : "")
				+ (horaSugeridaEntregaMinima != null ? "horaSugeridaEntregaMinima=" + horaSugeridaEntregaMinima + ", "
						: "")
				+ (horaSugeridaEntregaMaxima != null ? "horaSugeridaEntregaMaxima=" + horaSugeridaEntregaMaxima + ", "
						: "")
				+ (horaSugeridaEntregaMinimaAdicional != null
						? "horaSugeridaEntregaMinimaAdicional=" + horaSugeridaEntregaMinimaAdicional + ", " : "")
				+ (horaSugeridaEntregaMaximaAdicional != null
						? "horaSugeridaEntregaMaximaAdicional=" + horaSugeridaEntregaMaximaAdicional + ", " : "")
				+ (fechaPlaneadaAlistamiento != null ? "fechaPlaneadaAlistamiento=" + fechaPlaneadaAlistamiento + ", "
						: "")
				+ (fechaPlaneadaEntregaMinima != null
						? "fechaPlaneadaEntregaMinima=" + fechaPlaneadaEntregaMinima + ", " : "")
				+ (fechaPlaneadaEntregaMaxima != null
						? "fechaPlaneadaEntregaMaxima=" + fechaPlaneadaEntregaMaxima + ", " : "")
				+ (horaPlaneadaEntregaMinima != null ? "horaPlaneadaEntregaMinima=" + horaPlaneadaEntregaMinima + ", "
						: "")
				+ (horaPlaneadaEntregaMaxima != null ? "horaPlaneadaEntregaMaxima=" + horaPlaneadaEntregaMaxima + ", "
						: "")
				+ (horaPlaneadaEntregaMinimaAdicional != null
						? "horaPlaneadaEntregaMinimaAdicional=" + horaPlaneadaEntregaMinimaAdicional + ", " : "")
				+ (horaPlaneadaEntregaMaximaAdicional != null
						? "horaPlaneadaEntregaMaximaAdicional=" + horaPlaneadaEntregaMaximaAdicional + ", " : "")
				+ (requerimientosDistribucion != null
						? "requerimientosDistribucion=" + requerimientosDistribucion + ", " : "")
				+ (notasRequerimientosDistribucion != null
						? "notasRequerimientosDistribucion=" + notasRequerimientosDistribucion + ", " : "")
				+ (requerimientosAlistamiento != null
						? "requerimientosAlistamiento=" + requerimientosAlistamiento + ", " : "")
				+ (notasRequerimientosAlistamiento != null
						? "notasRequerimientosAlistamiento=" + notasRequerimientosAlistamiento + ", " : "")
				+ (fechaConfirmacion != null ? "fechaConfirmacion=" + fechaConfirmacion + ", " : "")
				+ (usuarioConfirmacion != null ? "usuarioConfirmacion=" + usuarioConfirmacion + ", " : "")
				+ (notasConfirmacion != null ? "notasConfirmacion=" + notasConfirmacion + ", " : "")
				+ (fechaAprobacionCliente != null ? "fechaAprobacionCliente=" + fechaAprobacionCliente + ", " : "")
				+ (usuarioAprobacionCliente != null ? "usuarioAprobacionCliente=" + usuarioAprobacionCliente + ", "
						: "")
				+ (notasAprobacionCliente != null ? "notasAprobacionCliente=" + notasAprobacionCliente + ", " : "")
				+ (fechaSolicitudRevisionCliente != null
						? "fechaSolicitudRevisionCliente=" + fechaSolicitudRevisionCliente + ", " : "")
				+ (usuarioSolicitudRevisionCliente != null
						? "usuarioSolicitudRevisionCliente=" + usuarioSolicitudRevisionCliente + ", " : "")
				+ (notasSolicitudRevisionCliente != null
						? "notasSolicitudRevisionCliente=" + notasSolicitudRevisionCliente + ", " : "")
				+ (causalSolicitudRevisionClienteId != null
						? "causalSolicitudRevisionClienteId=" + causalSolicitudRevisionClienteId + ", " : "")
				+ (causalSolicitudRevisionClienteNombre != null
						? "causalSolicitudRevisionClienteNombre=" + causalSolicitudRevisionClienteNombre + ", " : "")
				+ (fechaInicioPlaneacionLogistica != null
						? "fechaInicioPlaneacionLogistica=" + fechaInicioPlaneacionLogistica + ", " : "")
				+ (usuarioInicioPlaneacionLogistica != null
						? "usuarioInicioPlaneacionLogistica=" + usuarioInicioPlaneacionLogistica + ", " : "")
				+ (fechaFinPlaneacionLogistica != null
						? "fechaFinPlaneacionLogistica=" + fechaFinPlaneacionLogistica + ", " : "")
				+ (usuarioFinPlaneacionLogistica != null
						? "usuarioFinPlaneacionLogistica=" + usuarioFinPlaneacionLogistica + ", " : "")
				+ (notasPlaneacionLogistica != null ? "notasPlaneacionLogistica=" + notasPlaneacionLogistica + ", "
						: "")
				+ (fechaSolicitudRevisionPlaneacionLogistica != null ? "fechaSolicitudRevisionPlaneacionLogistica="
						+ fechaSolicitudRevisionPlaneacionLogistica + ", " : "")
				+ (usuarioSolicitudRevisionPlaneacionLogistica != null ? "usuarioSolicitudRevisionPlaneacionLogistica="
						+ usuarioSolicitudRevisionPlaneacionLogistica + ", " : "")
				+ (notasSolicitudRevisionPlaneacionLogistica != null ? "notasSolicitudRevisionPlaneacionLogistica="
						+ notasSolicitudRevisionPlaneacionLogistica + ", " : "")
				+ (causalSolicitudRevisionPlaneacionLogisticaId != null
						? "causalSolicitudRevisionPlaneacionLogisticaId=" + causalSolicitudRevisionPlaneacionLogisticaId
								+ ", "
						: "")
				+ (causalSolicitudRevisionPlaneacionLogisticaNombre != null
						? "causalSolicitudRevisionPlaneacionLogisticaNombre="
								+ causalSolicitudRevisionPlaneacionLogisticaNombre + ", "
						: "")
				+ (fechaInicioEjecucionLogistica != null
						? "fechaInicioEjecucionLogistica=" + fechaInicioEjecucionLogistica + ", " : "")
				+ (fechaFinEjecucionLogistica != null
						? "fechaFinEjecucionLogistica=" + fechaFinEjecucionLogistica + ", " : "")
				+ (fechaCreacion != null ? "fechaCreacion=" + fechaCreacion + ", " : "")
				+ (usuarioCreacion != null ? "usuarioCreacion=" + usuarioCreacion + ", " : "")
				+ (fechaActualizacion != null ? "fechaActualizacion=" + fechaActualizacion + ", " : "")
				+ (usuarioActualizacion != null ? "usuarioActualizacion=" + usuarioActualizacion + ", " : "")
				+ (fechaAnulacion != null ? "fechaAnulacion=" + fechaAnulacion + ", " : "")
				+ (usuarioAnulacion != null ? "usuarioAnulacion=" + usuarioAnulacion + ", " : "")
				+ (notasAnulacion != null ? "notasAnulacion=" + notasAnulacion + ", " : "")
				+ (causalAnulacionId != null ? "causalAnulacionId=" + causalAnulacionId + ", " : "")
				+ (causalAnulacionNombre != null ? "causalAnulacionNombre=" + causalAnulacionNombre : "") + "]";
	}

    private String toString(Collection<?> collection, int maxLen) {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        int i = 0;
        for (Iterator<?> iterator = collection.iterator(); iterator.hasNext() && i < maxLen; i++) {
            if (i > 0) {
                builder.append(", ");
            }
            builder.append(iterator.next());
        }
        builder.append("]");
        return builder.toString();
    }

}
