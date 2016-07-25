    package com.tacticlogistics.application.dto.etl;

import static com.tacticlogistics.infrastructure.services.Basic.coalesce;
import static com.tacticlogistics.infrastructure.services.Basic.substringSafe;

import java.sql.Time;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ETLOrdenDto {
    private String numeroOrden;
    private String numeroConsolidado;

    private String clienteCodigo;

    private String tipoServicioCodigo;
    private String tipoServicioCodigoAlterno;
    private boolean requiereServicioDistribucion;
    private boolean requiereServicioAlistamiento;

    private String destinoCiudadNombreAlterno;
    private String destinoDireccion;
    private String destinoIndicaciones;

    private String origenCiudadNombreAlterno;
    private String origenDireccion;
    private String origenIndicaciones;

    private boolean requiereConfirmacionCitaEntrega;
    private Date fechaEntregaSugeridaMinima;
    private Date fechaEntregaSugeridaMaxima;
    private Time horaEntregaSugeridaMinima;
    private Time horaEntregaSugeridaMaxima;
    private Time horaEntregaSugeridaMinimaAdicional;
    private Time horaEntregaSugeridaMaximaAdicional;

    private boolean requiereConfirmacionCitaRecogida;
    private Date fechaRecogidaSugeridaMinima;
    private Date fechaRecogidaSugeridaMaxima;
    private Time horaRecogidaSugeridaMinima;
    private Time horaRecogidaSugeridaMaxima;

    private String canalCodigoAlterno;

    private String destinatarioNumeroIdentificacion;
    private String destinatarioNombre;
    private String destinatarioContactoNombres;
    private String destinatarioContactoEmail;
    private String destinatarioContactoTelefonos;

    private String destinoNombre;
    private String destinoContactoNombres;
    private String destinoContactoEmail;
    private String destinoContactoTelefonos;

    private String origenNombre;
    private String origenContactoNombres;
    private String origenContactoEmail;
    private String origenContactoTelefonos;

    private Set<String> requerimientosDistribucion;
    private String notasRequerimientosDistribucion;

    private Set<String> requerimientosAlistamiento;
    private String notasRequerimientosAlistamiento;

    private Integer valorRecaudo;

    private String notasConfirmacion;
    private String usuarioConfirmacion;
    private Date fechaConfirmacion;

    private Set<ETLLineaOrdenDto> lineas;

    private String bodegaDestinoCodigo;
    private String bodegaDestinoCodigoAlterno;

    public ETLOrdenDto() {
        super();

        setNumeroOrden("");
        setNumeroConsolidado("");

        setClienteCodigo("");

        setTipoServicioCodigo("");
        setTipoServicioCodigoAlterno("");

        setRequiereServicioDistribucion(true);
        setRequiereServicioAlistamiento(true);

        setDestinoCiudadNombreAlterno("");
        setDestinoDireccion("");
        setDestinoIndicaciones("");

        setOrigenCiudadNombreAlterno("");
        setOrigenDireccion("");
        setOrigenIndicaciones("");

        setRequiereConfirmacionCitaEntrega(false);
        setFechaEntregaSugeridaMinima(null);
        setFechaEntregaSugeridaMaxima(null);
        setHoraEntregaSugeridaMinima(null);
        setHoraEntregaSugeridaMaxima(null);
        setHoraEntregaSugeridaMinimaAdicional(null);
        setHoraEntregaSugeridaMaximaAdicional(null);

        setRequiereConfirmacionCitaRecogida(false);
        setFechaRecogidaSugeridaMinima(null);
        setFechaRecogidaSugeridaMaxima(null);
        setHoraRecogidaSugeridaMinima(null);
        setHoraRecogidaSugeridaMaxima(null);

        setCanalCodigoAlterno("");

        setDestinatarioNumeroIdentificacion("");
        setDestinatarioNombre("");
        setDestinatarioContactoNombres("");
        setDestinatarioContactoEmail("");
        setDestinatarioContactoTelefonos("");

        setDestinoNombre("");
        setDestinoContactoNombres("");
        setDestinoContactoEmail("");
        setDestinoContactoTelefonos("");

        setOrigenNombre("");
        setOrigenContactoNombres("");
        setOrigenContactoEmail("");
        setOrigenContactoTelefonos("");

        setRequerimientosDistribucion(new HashSet<>());
        setCodigosAlternosRequerimientosDistribucion("");
        setNotasRequerimientosDistribucion("");

        setRequerimientosMaquila(new HashSet<>());
        setCodigosAlternosRequerimientosMaquila("");
        setNotasRequerimientosAlistamiento("");

        setValorRecaudo(null);

        setNotasConfirmacion("");
        setUsuarioConfirmacion("");
        setFechaConfirmacion(null);

        setLineas(new HashSet<>());

        setBodegaDestinoCodigo("");
        setBodegaDestinoCodigoAlterno("");
    }

    public String getNumeroOrden() {
        return numeroOrden;
    }

    public String getNumeroConsolidado() {
        return numeroConsolidado;
    }

    public String getClienteCodigo() {
        return clienteCodigo;
    }

    public String getTipoServicioCodigo() {
        return tipoServicioCodigo;
    }

    public String getTipoServicioCodigoAlterno() {
        return tipoServicioCodigoAlterno;
    }

    protected boolean isRequiereServicioDistribucion() {
        return requiereServicioDistribucion;
    }

    protected boolean isRequiereServicioAlistamiento() {
        return requiereServicioAlistamiento;
    }

    public String getDestinoCiudadNombreAlterno() {
        return destinoCiudadNombreAlterno;
    }

    public String getDestinoDireccion() {
        return destinoDireccion;
    }

    public String getDestinoIndicaciones() {
        return destinoIndicaciones;
    }

    public String getOrigenCiudadNombreAlterno() {
        return origenCiudadNombreAlterno;
    }

    public String getOrigenDireccion() {
        return origenDireccion;
    }

    public String getOrigenIndicaciones() {
        return origenIndicaciones;
    }

    protected boolean isRequiereConfirmacionCitaEntrega() {
        return requiereConfirmacionCitaEntrega;
    }

    public Date getEntregaSugeridaMinima() {
        return fechaEntregaSugeridaMinima;
    }

    public Date getFechaEntregaSugeridaMaxima() {
        return fechaEntregaSugeridaMaxima;
    }

    public Time getHoraEntregaSugeridaMinima() {
        return horaEntregaSugeridaMinima;
    }

    public Time getHoraEntregaSugeridaMaxima() {
        return horaEntregaSugeridaMaxima;
    }

    public Time getHoraEntregaSugeridaMinimaAdicional() {
        return horaEntregaSugeridaMinimaAdicional;
    }

    public Time getHoraEntregaSugeridaMaximaAdicional() {
        return horaEntregaSugeridaMaximaAdicional;
    }

    protected boolean isRequiereConfirmacionCitaRecogida() {
        return requiereConfirmacionCitaRecogida;
    }

    public Date getFechaRecogidaSugeridaMinima() {
        return fechaRecogidaSugeridaMinima;
    }

    public Date getFechaRecogidaSugeridaMaxima() {
        return fechaRecogidaSugeridaMaxima;
    }

    public Time getHoraRecogidaSugeridaMinima() {
        return horaRecogidaSugeridaMinima;
    }

    public Time getHoraRecogidaSugeridaMaxima() {
        return horaRecogidaSugeridaMaxima;
    }

    public String getCanalCodigoAlterno() {
        return canalCodigoAlterno;
    }

    public String getDestinatarioNumeroIdentificacion() {
        return destinatarioNumeroIdentificacion;
    }

    public String getDestinatarioNombre() {
        return destinatarioNombre;
    }

    public String getDestinatarioContactoNombres() {
        return destinatarioContactoNombres;
    }

    public String getDestinatarioContactoEmail() {
        return destinatarioContactoEmail;
    }

    public String getDestinatarioContactoTelefonos() {
        return destinatarioContactoTelefonos;
    }

    public String getDestinoNombre() {
        return destinoNombre;
    }

    public String getDestinoContactoNombres() {
        return destinoContactoNombres;
    }

    public String getDestinoContactoEmail() {
        return destinoContactoEmail;
    }

    public String getDestinoContactoTelefonos() {
        return destinoContactoTelefonos;
    }

    public String getOrigenNombre() {
        return origenNombre;
    }

    public String getOrigenContactoNombres() {
        return origenContactoNombres;
    }

    public String getOrigenContactoEmail() {
        return origenContactoEmail;
    }

    public String getOrigenContactoTelefonos() {
        return origenContactoTelefonos;
    }

    protected String getNotasRequerimientosDistribucion() {
        return notasRequerimientosDistribucion;
    }

    public Set<String> getRequerimientosDistribucion() {
        return requerimientosDistribucion;
    }

    protected String getNotasRequerimientosAlistamiento() {
        return notasRequerimientosAlistamiento;
    }

    public Set<String> getRequerimientosAlistamiento() {
        return requerimientosAlistamiento;
    }

    public Integer getValorRecaudo() {
        return valorRecaudo;
    }

    public String getNotasConfirmacion() {
        return notasConfirmacion;
    }

    public String getUsuarioConfirmacion() {
        return usuarioConfirmacion;
    }

    public Date getFechaConfirmacion() {
        return fechaConfirmacion;
    }

    public Set<ETLLineaOrdenDto> getLineas() {
        return lineas;
    }

    public String getBodegaDestinoCodigo() {
        return bodegaDestinoCodigo;
    }

    public String getBodegaDestinoCodigoAlterno() {
        return bodegaDestinoCodigoAlterno;
    }

    public void setNumeroOrden(String value) {
        this.numeroOrden = substringSafe(coalesce(value, "").trim(), 0, 30);
    }

    public void setNumeroConsolidado(String value) {
        this.numeroConsolidado = substringSafe(coalesce(value, "").trim(), 0, 30);
    }

    public void setClienteCodigo(String value) {
        this.clienteCodigo = substringSafe(coalesce(value, "").trim(), 0, 20);
    }

    public void setTipoServicioCodigo(String value) {
        this.tipoServicioCodigo = substringSafe(coalesce(value, "").trim(), 0, 20);
    }

    public void setTipoServicioCodigoAlterno(String value) {
        this.tipoServicioCodigoAlterno = substringSafe(coalesce(value, ""), 0, 50);
    }

    protected void setRequiereServicioDistribucion(boolean requiereServicioDistribucion) {
        this.requiereServicioDistribucion = requiereServicioDistribucion;
    }

    protected void setRequiereServicioAlistamiento(boolean requiereServicioAlistamiento) {
        this.requiereServicioAlistamiento = requiereServicioAlistamiento;
    }

    public void setDestinoCiudadNombreAlterno(String value) {
        this.destinoCiudadNombreAlterno = substringSafe(coalesce(value, "").trim(), 0, 100);
    }

    public void setDestinoDireccion(String value) {
        this.destinoDireccion = substringSafe(coalesce(value, "").trim(), 0, 150);
    }

    public void setDestinoIndicaciones(String value) {
        this.destinoIndicaciones = substringSafe(coalesce(value, "").trim(), 0, 150);
    }

    public void setOrigenCiudadNombreAlterno(String value) {
        this.origenCiudadNombreAlterno = substringSafe(coalesce(value, "").trim(), 0, 100);
    }

    public void setOrigenDireccion(String value) {
        this.origenDireccion = substringSafe(coalesce(value, "").trim(), 0, 150);
    }

    public void setOrigenIndicaciones(String value) {
        this.origenIndicaciones = substringSafe(coalesce(value, "").trim(), 0, 150);
    }

    protected void setRequiereConfirmacionCitaEntrega(boolean requiereConfirmacionCitaEntrega) {
        this.requiereConfirmacionCitaEntrega = requiereConfirmacionCitaEntrega;
    }

    public void setFechaEntregaSugeridaMinima(Date fechaEntregaSugeridaMinima) {
        this.fechaEntregaSugeridaMinima = fechaEntregaSugeridaMinima;
    }

    public void setFechaEntregaSugeridaMaxima(Date fechaEntregaSugeridaMaxima) {
        this.fechaEntregaSugeridaMaxima = fechaEntregaSugeridaMaxima;
    }

    public void setHoraEntregaSugeridaMinima(Time horaEntregaSugeridaMinima) {
        this.horaEntregaSugeridaMinima = horaEntregaSugeridaMinima;
    }

    public void setHoraEntregaSugeridaMaxima(Time horaEntregaSugeridaMaxima) {
        this.horaEntregaSugeridaMaxima = horaEntregaSugeridaMaxima;
    }

    public void setHoraEntregaSugeridaMinimaAdicional(Time horaEntregaSugeridaMinimaAdicional) {
        this.horaEntregaSugeridaMinimaAdicional = horaEntregaSugeridaMinimaAdicional;
    }

    public void setHoraEntregaSugeridaMaximaAdicional(Time horaEntregaSugeridaMaximaAdicional) {
        this.horaEntregaSugeridaMaximaAdicional = horaEntregaSugeridaMaximaAdicional;
    }

    protected void setRequiereConfirmacionCitaRecogida(boolean requiereConfirmacionCitaRecogida) {
        this.requiereConfirmacionCitaRecogida = requiereConfirmacionCitaRecogida;
    }

    public void setFechaRecogidaSugeridaMinima(Date fechaRecogidaSugeridaMinima) {
        this.fechaRecogidaSugeridaMinima = fechaRecogidaSugeridaMinima;
    }

    public void setFechaRecogidaSugeridaMaxima(Date fechaRecogidaSugeridaMaxima) {
        this.fechaRecogidaSugeridaMaxima = fechaRecogidaSugeridaMaxima;
    }

    public void setHoraRecogidaSugeridaMinima(Time horaRecogidaSugeridaMinima) {
        this.horaRecogidaSugeridaMinima = horaRecogidaSugeridaMinima;
    }

    public void setHoraRecogidaSugeridaMaxima(Time horaRecogidaSugeridaMaxima) {
        this.horaRecogidaSugeridaMaxima = horaRecogidaSugeridaMaxima;
    }

    public void setCanalCodigoAlterno(String value) {
        this.canalCodigoAlterno = substringSafe(coalesce(value, "").trim(), 0, 50);
    }

    public void setDestinatarioNumeroIdentificacion(String value) {
        this.destinatarioNumeroIdentificacion = substringSafe(coalesce(value, "").trim(), 0, 20);
    }

    public void setDestinatarioNombre(String value) {
        this.destinatarioNombre = substringSafe(coalesce(value, "").trim(), 0, 100);
    }

    public void setDestinatarioContactoNombres(String value) {
        this.destinatarioContactoNombres = substringSafe(coalesce(value, "").trim(), 0, 100);
    }

    public void setDestinatarioContactoEmail(String value) {
        this.destinatarioContactoEmail = substringSafe(coalesce(value, "").trim(), 0, 100);
    }

    public void setDestinatarioContactoTelefonos(String value) {
        this.destinatarioContactoTelefonos = substringSafe(coalesce(value, "").trim(), 0, 50);
    }

    public void setDestinoNombre(String value) {
        this.destinoNombre = substringSafe(coalesce(value, "").trim(), 0, 100);
    }

    public void setDestinoContactoNombres(String value) {
        this.destinoContactoNombres = substringSafe(coalesce(value, "").trim(), 0, 100);
    }

    public void setDestinoContactoEmail(String value) {
        this.destinoContactoEmail = substringSafe(coalesce(value, "").trim(), 0, 100);
    }

    public void setDestinoContactoTelefonos(String value) {
        this.destinoContactoTelefonos = substringSafe(coalesce(value, "").trim(), 0, 50);
    }

    public void setOrigenNombre(String value) {
        this.origenNombre = substringSafe(coalesce(value, "").trim(), 0, 100);
    }

    public void setOrigenContactoNombres(String value) {
        this.origenContactoNombres = substringSafe(coalesce(value, "").trim(), 0, 100);
    }

    public void setOrigenContactoEmail(String value) {
        this.origenContactoEmail = substringSafe(coalesce(value, "").trim(), 0, 100);
    }

    public void setOrigenContactoTelefonos(String value) {
        this.origenContactoTelefonos = substringSafe(coalesce(value, "").trim(), 0, 50);
    }

    public void setCodigosAlternosRequerimientosDistribucion(String codigos) {
        this.requerimientosDistribucion.clear();
        for (String codigo : coalesce(codigos, "").split(",")) {
            this.requerimientosDistribucion.add(codigo);
        }
    }

    public void setCodigosAlternosRequerimientosMaquila(String codigos) {
        this.requerimientosAlistamiento.clear();
        for (String codigo : coalesce(codigos, "").split(",")) {
            this.requerimientosAlistamiento.add(codigo);
        }
    }

    protected void setRequerimientosDistribucion(Set<String> requerimientosDistribucion) {
        this.requerimientosDistribucion = requerimientosDistribucion;
    }

    protected void setNotasRequerimientosDistribucion(String notasRequerimientosDistribucion) {
        this.notasRequerimientosDistribucion = notasRequerimientosDistribucion;
    }

    protected void setRequerimientosMaquila(Set<String> requerimientosMaquila) {
        this.requerimientosAlistamiento = requerimientosMaquila;
    }

    protected void setNotasRequerimientosAlistamiento(String notasRequerimientosAlistamiento) {
        this.notasRequerimientosAlistamiento = notasRequerimientosAlistamiento;
    }

    public void setValorRecaudo(Integer value) {
        if (value != null) {
            if (value <= 0) {
                value = null;
            }
        }
        this.valorRecaudo = value;
    }

    public void setNotasConfirmacion(String value) {
        this.notasConfirmacion = substringSafe(coalesce(value, "").trim(), 0, 200);
    }

    public void setUsuarioConfirmacion(String value) {
        this.usuarioConfirmacion = substringSafe(coalesce(value, "").trim(), 0, 50);
    }

    public void setFechaConfirmacion(Date fechaAprobacionCliente) {
        this.fechaConfirmacion = fechaAprobacionCliente;
    }

    protected void setLineas(Set<ETLLineaOrdenDto> lineas) {
        this.lineas = lineas;
    }

    public void setBodegaDestinoCodigo(String value) {
        this.bodegaDestinoCodigo = substringSafe(coalesce(value, ""), 0, 20);
    }

    public void setBodegaDestinoCodigoAlterno(String value) {
        this.bodegaDestinoCodigoAlterno = substringSafe(coalesce(value, ""), 0, 50);
    }

    @Override
    public String toString() {
        final int maxLen = 3;
        StringBuilder builder = new StringBuilder();
        builder.append("ETLOrdenDto [");
        if (clienteCodigo != null) {
            builder.append("clienteCodigo=").append(clienteCodigo).append(", ");
        }
        if (numeroOrden != null) {
            builder.append("numeroDocumentoOrdenCliente=").append(numeroOrden).append(", ");
        }
        if (numeroConsolidado != null) {
            builder.append("numeroDocumentoConsolidadoCliente=").append(numeroConsolidado).append(", ");
        }
        if (canalCodigoAlterno != null) {
            builder.append("canalCodigoAlterno=").append(canalCodigoAlterno).append(", ");
        }
        if (destinatarioNumeroIdentificacion != null) {
            builder.append("destinatarioNumeroIdentificacion=").append(destinatarioNumeroIdentificacion).append(", ");
        }
        if (destinatarioNombre != null) {
            builder.append("destinatarioNombre=").append(destinatarioNombre).append(", ");
        }
        if (destinoNombre != null) {
            builder.append("destinoNombre=").append(destinoNombre).append(", ");
        }
        if (destinoCiudadNombreAlterno != null) {
            builder.append("destinoCiudadNombreAlterno=").append(destinoCiudadNombreAlterno).append(", ");
        }
        if (destinoDireccion != null) {
            builder.append("destinoDireccion=").append(destinoDireccion).append(", ");
        }
        if (fechaEntregaSugeridaMinima != null) {
            builder.append("fechaSugeridaEntregaMinima=").append(fechaEntregaSugeridaMinima).append(", ");
        }
        if (fechaEntregaSugeridaMaxima != null) {
            builder.append("fechaSugeridaEntregaMaxima=").append(fechaEntregaSugeridaMaxima).append(", ");
        }
        if (horaEntregaSugeridaMinima != null) {
            builder.append("horaSugeridaEntregaMinima=").append(horaEntregaSugeridaMinima).append(", ");
        }
        if (horaEntregaSugeridaMaxima != null) {
            builder.append("horaSugeridaEntregaMaxima=").append(horaEntregaSugeridaMaxima).append(", ");
        }
        if (notasConfirmacion != null) {
            builder.append("notasAprobacionCliente=").append(notasConfirmacion).append(", ");
        }
        if (usuarioConfirmacion != null) {
            builder.append("usuarioAprobacionCliente=").append(usuarioConfirmacion).append(", ");
        }
        if (fechaConfirmacion != null) {
            builder.append("fechaAprobacionCliente=").append(fechaConfirmacion).append(", ");
        }
        if (valorRecaudo != null) {
            builder.append("valorTotalRecaudo=").append(valorRecaudo).append(", ");
        }
        if (lineas != null) {
            builder.append("lineas=").append(toString(lineas, maxLen));
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