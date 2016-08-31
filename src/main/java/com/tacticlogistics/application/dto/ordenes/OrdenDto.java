package com.tacticlogistics.application.dto.ordenes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tacticlogistics.domain.model.common.Mensaje;
import com.tacticlogistics.domain.model.oms.EstadoOrdenType;

public class OrdenDto {
    private Integer idOrden;

    // ---------------------------------------------------------------------------------------------------------
    private DestinatarioDto datosFacturacion;
    private DestinoOrigenDto destinoOrigen;
    private BodegaDestinoOrigenDto bodegaDestinoOrigen;
    private EntregaRecogidaDto datosEntregaRecogida;
    private OtrosDatosDto datosOtros;
    // ---------------------------------------------------------------------------------------------------------
    private List<LineaOrdenDto> lineas;
    private List<Mensaje> mensajes;
    // TODO CAMBIOS
    // TODO DOCUMENTOS
    // TODO REQUERIMIENTOS

    // ---------------------------------------------------------------------------------------------------------
    private Date fechaConfirmacion;
    private String usuarioConfirmacion;

    private Date fechaAceptacion;
    private String usuarioAceptacion;

    private Date fechaActualizacion;
    private String usuarioActualizacion;

    private EstadoOrdenType nuevoEstadoOrden;

    // ---------------------------------------------------------------------------------------------------------
    public OrdenDto() {
        super();
    }

    // ---------------------------------------------------------------------------------------------------------
    public Integer getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(Integer idOrden) {
        this.idOrden = idOrden;
    }

    public EstadoOrdenType getNuevoEstadoOrden() {
        return nuevoEstadoOrden;
    }

    public void setNuevoEstadoOrden(EstadoOrdenType nuevoEstadoOrden) {
        this.nuevoEstadoOrden = nuevoEstadoOrden;
    }

    public DestinatarioDto getDatosFacturacion() {
        if (datosFacturacion == null) {
            datosFacturacion = new DestinatarioDto();
        }
        return datosFacturacion;
    }

    public void setDatosFacturacion(DestinatarioDto datosFacturacion) {
        this.datosFacturacion = datosFacturacion;
    }

    public DestinoOrigenDto getDestinoOrigen() {
        if (destinoOrigen == null) {
            destinoOrigen = new DestinoOrigenDto();
        }
        return destinoOrigen;
    }

    public void setDestinoOrigen(DestinoOrigenDto destinoOrigen) {
        this.destinoOrigen = destinoOrigen;
    }

    public BodegaDestinoOrigenDto getBodegaDestinoOrigen() {
        if (bodegaDestinoOrigen == null) {
            bodegaDestinoOrigen = new BodegaDestinoOrigenDto();
        }
        return bodegaDestinoOrigen;
    }

    public void setBodegaDestinoOrigen(BodegaDestinoOrigenDto bodegaDestinoOrigen) {
        this.bodegaDestinoOrigen = bodegaDestinoOrigen;
    }

    public EntregaRecogidaDto getDatosEntregaRecogida() {
        if (datosEntregaRecogida == null) {
            datosEntregaRecogida = new EntregaRecogidaDto();
        }
        return datosEntregaRecogida;
    }

    public void setDatosEntregaRecogida(EntregaRecogidaDto datosEntregaRecogida) {
        this.datosEntregaRecogida = datosEntregaRecogida;
    }

    public OtrosDatosDto getDatosOtros() {
        if (datosOtros == null) {
            datosOtros = new OtrosDatosDto();
        }
        return datosOtros;
    }

    public void setDatosOtros(OtrosDatosDto datosOtros) {
        this.datosOtros = datosOtros;
    }

    public List<LineaOrdenDto> getLineas() {
        if (lineas == null) {
            lineas = new ArrayList<>();
        }
        return lineas;
    }

    public void setLineas(List<LineaOrdenDto> lineas) {
        this.lineas = lineas;
    }

    public List<LineaOrdenDto> getLineasProductos() {
        return getLineas();
    }

    public List<LineaOrdenDto> getLineasPaquetes() {
        return getLineas();
    }

    public List<Mensaje> getMensajes() {
        if (mensajes == null) {
            this.mensajes = new ArrayList<>();
        }
        return mensajes;
    }

    public void setMensajes(List<Mensaje> mensajes) {
        this.mensajes = mensajes;
    }

    public Date getFechaConfirmacion() {
        return fechaConfirmacion;
    }

    public void setFechaConfirmacion(Date fechaConfirmacion) {
        this.fechaConfirmacion = fechaConfirmacion;
    }

    public String getUsuarioConfirmacion() {
        return usuarioConfirmacion;
    }

    public void setUsuarioConfirmacion(String usuarioConfirmacion) {
        this.usuarioConfirmacion = usuarioConfirmacion;
    }

    public Date getFechaAceptacion() {
        return fechaAceptacion;
    }

    public void setFechaAceptacion(Date fechaAceptacion) {
        this.fechaAceptacion = fechaAceptacion;
    }

    public String getUsuarioAceptacion() {
        return usuarioAceptacion;
    }

    public void setUsuarioAceptacion(String usuarioAceptacion) {
        this.usuarioAceptacion = usuarioAceptacion;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public String getUsuarioActualizacion() {
        return usuarioActualizacion;
    }

    public void setUsuarioActualizacion(String usuarioActualizacion) {
        this.usuarioActualizacion = usuarioActualizacion;
    }

    @Override
    public String toString() {
        final int maxLen = 5;
        StringBuilder builder = new StringBuilder();
        builder.append("OrdenViewModel [");
        if (idOrden != null)
            builder.append("idOrden=").append(idOrden).append(", ");
        if (datosFacturacion != null)
            builder.append("datosFacturacion=").append(datosFacturacion).append(", ");
        if (destinoOrigen != null)
            builder.append("destinoOrigen=").append(destinoOrigen).append(", ");
        if (bodegaDestinoOrigen != null)
            builder.append("bodegaDestinoOrigen=").append(bodegaDestinoOrigen).append(", ");
        if (datosEntregaRecogida != null)
            builder.append("datosEntregaRecogida=").append(datosEntregaRecogida).append(", ");
        if (datosOtros != null)
            builder.append("datosOtros=").append(datosOtros).append(", ");
        if (lineas != null)
            builder.append("lineas=").append(lineas.subList(0, Math.min(lineas.size(), maxLen))).append(", ");
        if (mensajes != null)
            builder.append("mensajes=").append(mensajes.subList(0, Math.min(mensajes.size(), maxLen))).append(", ");
        if (fechaConfirmacion != null)
            builder.append("fechaConfirmacion=").append(fechaConfirmacion).append(", ");
        if (usuarioConfirmacion != null)
            builder.append("usuarioConfirmacion=").append(usuarioConfirmacion).append(", ");
        if (fechaAceptacion != null)
            builder.append("fechaAceptacion=").append(fechaAceptacion).append(", ");
        if (usuarioAceptacion != null)
            builder.append("usuarioAceptacion=").append(usuarioAceptacion).append(", ");
        if (fechaActualizacion != null)
            builder.append("fechaActualizacion=").append(fechaActualizacion).append(", ");
        if (usuarioActualizacion != null)
            builder.append("usuarioActualizacion=").append(usuarioActualizacion).append(", ");
        if (nuevoEstadoOrden != null)
            builder.append("nuevoEstadoOrden=").append(nuevoEstadoOrden);
        builder.append("]");
        return builder.toString();
    }
}
