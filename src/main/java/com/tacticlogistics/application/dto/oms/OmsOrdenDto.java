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
    private String numeroDocumentoOrdenCliente;

    // ---------------------------------------------------------------------------------------------------------
    private Integer consolidadoId;
    private String numeroDocumentoConsolidadoCliente;

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

    private Set<ItemGenerico<Integer>> requerimientosMaquila;
    private String notasRequerimientosMaquila;

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

    public String getNumeroDocumentoOrdenCliente() {
        return numeroDocumentoOrdenCliente;
    }

    public Integer getConsolidadoId() {
        return consolidadoId;
    }

    public String getNumeroDocumentoConsolidadoCliente() {
        return numeroDocumentoConsolidadoCliente;
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

    public Set<ItemGenerico<Integer>> getRequerimientosMaquila() {
        return requerimientosMaquila;
    }

    public String getNotasRequerimientosMaquila() {
        return notasRequerimientosMaquila;
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

    public void setNumeroDocumentoOrdenCliente(String value) {
        this.numeroDocumentoOrdenCliente = coalesce(value, "");
    }

    public void setConsolidadoId(Integer value) {
        this.consolidadoId = value;
    }

    public void setNumeroDocumentoConsolidadoCliente(String value) {
        this.numeroDocumentoConsolidadoCliente = coalesce(value, "");
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

    public void setRequerimientosMaquila(Set<ItemGenerico<Integer>> value) {
        this.requerimientosMaquila = value;
    }

    public void setNotasRequerimientosMaquila(String value) {
        this.notasRequerimientosMaquila = coalesce(value, "");
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

        this.setNumeroDocumentoOrdenCliente("");
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

        this.setRequerimientosMaquila(new HashSet<>());
        this.setNotasRequerimientosMaquila("");

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
        final int maxLen = 5;
        StringBuilder builder = new StringBuilder();
        builder.append("OmsOrdenDto [");
        if (id != null) {
            builder.append("id=").append(id).append(", ");
        }
        if (tipoServicioId != null) {
            builder.append("tipoServicioId=").append(tipoServicioId).append(", ");
        }
        if (tipoServicioNombre != null) {
            builder.append("tipoServicioNombre=").append(tipoServicioNombre).append(", ");
        }
        if (clienteId != null) {
            builder.append("clienteId=").append(clienteId).append(", ");
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
        if (origenOrden != null) {
            builder.append("origenOrden=").append(origenOrden).append(", ");
        }
        if (numeroDocumentoOrdenCliente != null) {
            builder.append("numeroDocumentoOrdenCliente=").append(numeroDocumentoOrdenCliente).append(", ");
        }
        if (consolidadoId != null) {
            builder.append("consolidadoId=").append(consolidadoId).append(", ");
        }
        if (numeroDocumentoConsolidadoCliente != null) {
            builder.append("numeroDocumentoConsolidadoCliente=").append(numeroDocumentoConsolidadoCliente).append(", ");
        }
        if (canalId != null) {
            builder.append("canalId=").append(canalId).append(", ");
        }
        if (canalCodigo != null) {
            builder.append("canalCodigo=").append(canalCodigo).append(", ");
        }
        if (canalNombre != null) {
            builder.append("canalNombre=").append(canalNombre).append(", ");
        }
        if (canalCodigoAlterno != null) {
            builder.append("canalCodigoAlterno=").append(canalCodigoAlterno).append(", ");
        }
        if (destinatarioId != null) {
            builder.append("destinatarioId=").append(destinatarioId).append(", ");
        }
        if (destinatarioCodigo != null) {
            builder.append("destinatarioCodigo=").append(destinatarioCodigo).append(", ");
        }
        if (destinatarioNombre != null) {
            builder.append("destinatarioNombre=").append(destinatarioNombre).append(", ");
        }
        if (destinatarioNumeroIdentificacion != null) {
            builder.append("destinatarioNumeroIdentificacion=").append(destinatarioNumeroIdentificacion).append(", ");
        }
        if (destinatarioCodigoAlterno != null) {
            builder.append("destinatarioCodigoAlterno=").append(destinatarioCodigoAlterno).append(", ");
        }
        if (destinatarioNombreAlterno != null) {
            builder.append("destinatarioNombreAlterno=").append(destinatarioNombreAlterno).append(", ");
        }
        if (destinatarioNumeroIdentificacionAlterno != null) {
            builder.append("destinatarioNumeroIdentificacionAlterno=").append(destinatarioNumeroIdentificacionAlterno)
                    .append(", ");
        }
        if (destinatarioDigitoVerificacionAlterno != null) {
            builder.append("destinatarioDigitoVerificacionAlterno=").append(destinatarioDigitoVerificacionAlterno)
                    .append(", ");
        }
        if (destinatarioCiudadId != null) {
            builder.append("destinatarioCiudadId=").append(destinatarioCiudadId).append(", ");
        }
        if (destinatarioCiudadNombre != null) {
            builder.append("destinatarioCiudadNombre=").append(destinatarioCiudadNombre).append(", ");
        }
        if (destinatarioDireccion != null) {
            builder.append("destinatarioDireccion=").append(destinatarioDireccion).append(", ");
        }
        if (destinatarioIndicacionesDireccion != null) {
            builder.append("destinatarioIndicacionesDireccion=").append(destinatarioIndicacionesDireccion).append(", ");
        }
        if (destinatarioContactoNombres != null) {
            builder.append("destinatarioContactoNombres=").append(destinatarioContactoNombres).append(", ");
        }
        if (destinatarioContactoTelefonos != null) {
            builder.append("destinatarioContactoTelefonos=").append(destinatarioContactoTelefonos).append(", ");
        }
        if (destinatarioContactoEmail != null) {
            builder.append("destinatarioContactoEmail=").append(destinatarioContactoEmail).append(", ");
        }
        if (estadoSolicitudActualizacionDatosContactoDestinatario != null) {
            builder.append("estadoSolicitudActualizacionDatosContactoDestinatario=")
                    .append(estadoSolicitudActualizacionDatosContactoDestinatario).append(", ");
        }
        if (destinatarioCiudadNombreAlterno != null) {
            builder.append("destinatarioCiudadNombreAlterno=").append(destinatarioCiudadNombreAlterno).append(", ");
        }
        if (destinoId != null) {
            builder.append("destinoId=").append(destinoId).append(", ");
        }
        if (destinoCodigo != null) {
            builder.append("destinoCodigo=").append(destinoCodigo).append(", ");
        }
        if (destinoNombre != null) {
            builder.append("destinoNombre=").append(destinoNombre).append(", ");
        }
        if (destinoCodigoAlterno != null) {
            builder.append("destinoCodigoAlterno=").append(destinoCodigoAlterno).append(", ");
        }
        if (destinoNombreAlterno != null) {
            builder.append("destinoNombreAlterno=").append(destinoNombreAlterno).append(", ");
        }
        if (bodegaDestinoId != null) {
            builder.append("bodegaDestinoId=").append(bodegaDestinoId).append(", ");
        }
        if (bodegaDestinoCodigo != null) {
            builder.append("bodegaDestinoCodigo=").append(bodegaDestinoCodigo).append(", ");
        }
        if (bodegaDestinoNombre != null) {
            builder.append("bodegaDestinoNombre=").append(bodegaDestinoNombre).append(", ");
        }
        if (bodegaDestinoCodigoAlterno != null) {
            builder.append("bodegaDestinoCodigoAlterno=").append(bodegaDestinoCodigoAlterno).append(", ");
        }
        if (bodegaDestinoNombreAlterno != null) {
            builder.append("bodegaDestinoNombreAlterno=").append(bodegaDestinoNombreAlterno).append(", ");
        }
        if (destinoCiudadId != null) {
            builder.append("destinoCiudadId=").append(destinoCiudadId).append(", ");
        }
        if (destinoCiudadNombre != null) {
            builder.append("destinoCiudadNombre=").append(destinoCiudadNombre).append(", ");
        }
        if (destinoDireccion != null) {
            builder.append("destinoDireccion=").append(destinoDireccion).append(", ");
        }
        if (destinoIndicacionesDireccion != null) {
            builder.append("destinoIndicacionesDireccion=").append(destinoIndicacionesDireccion).append(", ");
        }
        if (destinoContactoNombres != null) {
            builder.append("destinoContactoNombres=").append(destinoContactoNombres).append(", ");
        }
        if (destinoContactoTelefonos != null) {
            builder.append("destinoContactoTelefonos=").append(destinoContactoTelefonos).append(", ");
        }
        if (destinoContactoEmail != null) {
            builder.append("destinoContactoEmail=").append(destinoContactoEmail).append(", ");
        }
        if (estadoSolicitudActualizacionDatosContactoDestino != null) {
            builder.append("estadoSolicitudActualizacionDatosContactoDestino=")
                    .append(estadoSolicitudActualizacionDatosContactoDestino).append(", ");
        }
        if (destinoCiudadNombreAlterno != null) {
            builder.append("destinoCiudadNombreAlterno=").append(destinoCiudadNombreAlterno).append(", ");
        }
        builder.append("requiereServicioDistribucion=").append(requiereServicioDistribucion).append(", ");
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
        if (requerimientosDistribucion != null) {
            builder.append("requerimientosDistribucion=").append(toString(requerimientosDistribucion, maxLen))
                    .append(", ");
        }
        if (notasRequerimientosDistribucion != null) {
            builder.append("notasRequerimientosDistribucion=").append(notasRequerimientosDistribucion).append(", ");
        }
        if (requerimientosMaquila != null) {
            builder.append("requerimientosMaquila=").append(toString(requerimientosMaquila, maxLen)).append(", ");
        }
        if (notasRequerimientosMaquila != null) {
            builder.append("notasRequerimientosMaquila=").append(notasRequerimientosMaquila).append(", ");
        }
        if (fechaConfirmacion != null) {
            builder.append("fechaConfirmacion=").append(fechaConfirmacion).append(", ");
        }
        if (usuarioConfirmacion != null) {
            builder.append("usuarioConfirmacion=").append(usuarioConfirmacion).append(", ");
        }
        if (notasConfirmacion != null) {
            builder.append("notasConfirmacion=").append(notasConfirmacion).append(", ");
        }
        if (fechaAprobacionCliente != null) {
            builder.append("fechaAprobacionCliente=").append(fechaAprobacionCliente).append(", ");
        }
        if (usuarioAprobacionCliente != null) {
            builder.append("usuarioAprobacionCliente=").append(usuarioAprobacionCliente).append(", ");
        }
        if (notasAprobacionCliente != null) {
            builder.append("notasAprobacionCliente=").append(notasAprobacionCliente).append(", ");
        }
        if (fechaSolicitudRevisionCliente != null) {
            builder.append("fechaSolicitudRevisionCliente=").append(fechaSolicitudRevisionCliente).append(", ");
        }
        if (usuarioSolicitudRevisionCliente != null) {
            builder.append("usuarioSolicitudRevisionCliente=").append(usuarioSolicitudRevisionCliente).append(", ");
        }
        if (notasSolicitudRevisionCliente != null) {
            builder.append("notasSolicitudRevisionCliente=").append(notasSolicitudRevisionCliente).append(", ");
        }
        if (causalSolicitudRevisionClienteId != null) {
            builder.append("causalSolicitudRevisionClienteId=").append(causalSolicitudRevisionClienteId).append(", ");
        }
        if (causalSolicitudRevisionClienteNombre != null) {
            builder.append("causalSolicitudRevisionClienteNombre=").append(causalSolicitudRevisionClienteNombre)
                    .append(", ");
        }
        if (fechaInicioPlaneacionLogistica != null) {
            builder.append("fechaInicioPlaneacionLogistica=").append(fechaInicioPlaneacionLogistica).append(", ");
        }
        if (usuarioInicioPlaneacionLogistica != null) {
            builder.append("usuarioInicioPlaneacionLogistica=").append(usuarioInicioPlaneacionLogistica).append(", ");
        }
        if (fechaFinPlaneacionLogistica != null) {
            builder.append("fechaFinPlaneacionLogistica=").append(fechaFinPlaneacionLogistica).append(", ");
        }
        if (usuarioFinPlaneacionLogistica != null) {
            builder.append("usuarioFinPlaneacionLogistica=").append(usuarioFinPlaneacionLogistica).append(", ");
        }
        if (notasPlaneacionLogistica != null) {
            builder.append("notasPlaneacionLogistica=").append(notasPlaneacionLogistica).append(", ");
        }
        if (fechaSolicitudRevisionPlaneacionLogistica != null) {
            builder.append("fechaSolicitudRevisionPlaneacionLogistica=")
                    .append(fechaSolicitudRevisionPlaneacionLogistica).append(", ");
        }
        if (usuarioSolicitudRevisionPlaneacionLogistica != null) {
            builder.append("usuarioSolicitudRevisionPlaneacionLogistica=")
                    .append(usuarioSolicitudRevisionPlaneacionLogistica).append(", ");
        }
        if (notasSolicitudRevisionPlaneacionLogistica != null) {
            builder.append("notasSolicitudRevisionPlaneacionLogistica=")
                    .append(notasSolicitudRevisionPlaneacionLogistica).append(", ");
        }
        if (causalSolicitudRevisionPlaneacionLogisticaId != null) {
            builder.append("causalSolicitudRevisionPlaneacionLogisticaId=")
                    .append(causalSolicitudRevisionPlaneacionLogisticaId).append(", ");
        }
        if (causalSolicitudRevisionPlaneacionLogisticaNombre != null) {
            builder.append("causalSolicitudRevisionPlaneacionLogisticaNombre=")
                    .append(causalSolicitudRevisionPlaneacionLogisticaNombre).append(", ");
        }
        if (fechaInicioEjecucionLogistica != null) {
            builder.append("fechaInicioEjecucionLogistica=").append(fechaInicioEjecucionLogistica).append(", ");
        }
        if (fechaFinEjecucionLogistica != null) {
            builder.append("fechaFinEjecucionLogistica=").append(fechaFinEjecucionLogistica).append(", ");
        }
        if (fechaCreacion != null) {
            builder.append("fechaCreacion=").append(fechaCreacion).append(", ");
        }
        if (usuarioCreacion != null) {
            builder.append("usuarioCreacion=").append(usuarioCreacion).append(", ");
        }
        if (fechaActualizacion != null) {
            builder.append("fechaActualizacion=").append(fechaActualizacion).append(", ");
        }
        if (usuarioActualizacion != null) {
            builder.append("usuarioActualizacion=").append(usuarioActualizacion).append(", ");
        }
        if (fechaAnulacion != null) {
            builder.append("fechaAnulacion=").append(fechaAnulacion).append(", ");
        }
        if (usuarioAnulacion != null) {
            builder.append("usuarioAnulacion=").append(usuarioAnulacion).append(", ");
        }
        if (notasAnulacion != null) {
            builder.append("notasAnulacion=").append(notasAnulacion).append(", ");
        }
        if (causalAnulacionId != null) {
            builder.append("causalAnulacionId=").append(causalAnulacionId).append(", ");
        }
        if (causalAnulacionNombre != null) {
            builder.append("causalAnulacionNombre=").append(causalAnulacionNombre);
        }
        builder.append("]");
        return builder.toString();
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
