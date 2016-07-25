package com.tacticlogistics.domain.model.oms;

import static com.tacticlogistics.infrastructure.services.Basic.coalesce;

import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.OptionalInt;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;

import com.tacticlogistics.domain.model.common.valueobjects.Contacto;
import com.tacticlogistics.domain.model.common.valueobjects.IntervaloDeFechas;
import com.tacticlogistics.domain.model.common.valueobjects.IntervaloDeHoras;
import com.tacticlogistics.domain.model.common.valueobjects.MensajeEmbeddable;
import com.tacticlogistics.domain.model.crm.Canal;
import com.tacticlogistics.domain.model.crm.Cliente;
import com.tacticlogistics.domain.model.crm.DestinatarioRemitente;
import com.tacticlogistics.domain.model.crm.DestinoOrigen;
import com.tacticlogistics.domain.model.crm.TipoServicio;
import com.tacticlogistics.domain.model.geo.Ciudad;
import com.tacticlogistics.domain.model.tms.TipoVehiculo;

@Entity
@Table(name = "Ordenes", catalog = "oms")

// TODO DOCUMENTOS
// TODO MENSAJES POR LINEA
// TODO REQUERIMIENTOS DISTRIBUCION POR LINEA
// TODO REQUERIMIENTOS ALISTAMIENTO POR LINEA
public class OmsOrden implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_orden", unique = true, nullable = false)
    @NotNull
    private Integer id;

    @Column(nullable = false, length = 30)
    @NotNull
    private String numeroOrden;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "id_consolidado", nullable = true)
    private OmsConsolidado consolidado;

    // ---------------------------------------------------------------------------------------------------------
    @Enumerated(EnumType.STRING)
    @Column(name = "id_estado_orden", nullable = false, length = 50)
    @NotNull
    private EstadoOrdenType estadoOrden;

    @Enumerated(EnumType.STRING)
    @Column(name = "id_estado_distribucion", nullable = false, length = 50)
    @NotNull
    private EstadoDistribucionType estadoDistribucion;

    @Enumerated(EnumType.STRING)
    @Column(name = "id_estado_alistamiento", nullable = false, length = 50)
    @NotNull
    private EstadoAlistamientoType estadoAlistamiento;

    // ---------------------------------------------------------------------------------------------------------
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_cliente", nullable = false)
    @NotNull
    private Cliente cliente;

    @Column(name = "cliente_codigo", nullable = false, length = 20)
    @NotNull
    private String clienteCodigo;

    // ---------------------------------------------------------------------------------------------------------
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_tipo_servicio", nullable = false)
    @NotNull
    private TipoServicio tipoServicio;

    @Column(nullable = false, length = 50)
    @NotNull
    private String tipoServicioCodigoAlterno;

    private boolean requiereServicioDistribucion;

    private boolean requiereServicioAlistamiento;

    // ---------------------------------------------------------------------------------------------------------
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "id_ciudad_destino", nullable = true)
    private Ciudad ciudadDestino;

    @Column(nullable = false, length = 100)
    @NotNull
    private String destinoCiudadNombreAlterno;

    @Column(nullable = false, length = 150)
    @NotNull
    private String destinoDireccion;

    @Column(nullable = false, length = 150)
    @NotNull
    private String destinoIndicaciones;

    // ---------------------------------------------------------------------------------------------------------
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "id_ciudad_origen", nullable = true)
    private Ciudad ciudadOrigen;

    @Column(nullable = false, length = 100)
    @NotNull
    private String origenCiudadNombreAlterno;

    @Column(nullable = false, length = 150)
    @NotNull
    private String origenDireccion;

    @Column(nullable = false, length = 150)
    @NotNull
    private String origenIndicaciones;

    // ---------------------------------------------------------------------------------------------------------
    private boolean requiereConfirmacionCitaEntrega;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "fechaMinima", column = @Column(name = "fecha_entrega_sugerida_minima", nullable = true, columnDefinition = "DATE")),
            @AttributeOverride(name = "fechaMaxima", column = @Column(name = "fecha_entrega_sugerida_maxima", nullable = true, columnDefinition = "DATE")) })
    private IntervaloDeFechas intervaloFechaEntregaSugerido;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "horaMinima", column = @Column(name = "hora_entrega_sugerida_minima", nullable = true, columnDefinition = "TIME(0)")),
            @AttributeOverride(name = "horaMaxima", column = @Column(name = "hora_entrega_sugerida_maxima", nullable = true, columnDefinition = "TIME(0)")) })
    private IntervaloDeHoras intervaloHoraEntregaSugerido;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "horaMinima", column = @Column(name = "hora_entrega_sugerida_minima_adicional", nullable = true, columnDefinition = "TIME(0)")),
            @AttributeOverride(name = "horaMaxima", column = @Column(name = "hora_entrega_sugerida_maxima_adicional", nullable = true, columnDefinition = "TIME(0)")) })
    private IntervaloDeHoras intervaloHoraEntregaAdicionalSugerido;

    // ---------------------------------------------------------------------------------------------------------
    private boolean requiereConfirmacionCitaRecogida;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "fechaMinima", column = @Column(name = "fecha_recogida_sugerida_minima", nullable = true, columnDefinition = "DATE")),
            @AttributeOverride(name = "fechaMaxima", column = @Column(name = "fecha_recogida_sugerida_maxima", nullable = true, columnDefinition = "DATE")) })
    private IntervaloDeFechas intervaloFechaRecogidaSugerido;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "horaMinima", column = @Column(name = "hora_recogida_sugerida_minima", nullable = true, columnDefinition = "TIME(0)")),
            @AttributeOverride(name = "horaMaxima", column = @Column(name = "hora_recogida_sugerida_maxima", nullable = true, columnDefinition = "TIME(0)")) })
    private IntervaloDeHoras intervaloHoraRecogidaSugerido;

    // ---------------------------------------------------------------------------------------------------------
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "id_canal", nullable = true)
    private Canal canal;

    @Column(name = "canal_codigo_alterno", nullable = false, length = 50)
    @NotNull
    private String canalCodigoAlterno;

    // ---------------------------------------------------------------------------------------------------------
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "id_destinatario", nullable = true)
    private DestinatarioRemitente destinatario;

    @Column(nullable = false, length = 20)
    @NotNull
    private String destinatarioNumeroIdentificacion;

    @Column(nullable = false, length = 100)
    @NotNull
    private String destinatarioNombre;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "nombres", column = @Column(name = "destinatario_contacto_nombres", nullable = false, length = 100)),
            @AttributeOverride(name = "telefonos", column = @Column(name = "destinatario_contacto_telefonos", nullable = false, length = 50)),
            @AttributeOverride(name = "email", column = @Column(name = "destinatario_contacto_email", nullable = false, length = 100)) })
    @NotNull
    private Contacto destinatarioContacto;

    // ---------------------------------------------------------------------------------------------------------
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "id_destino", nullable = true)
    private DestinoOrigen destino;

    @Column(nullable = false, length = 100)
    @NotNull
    private String destinoNombre;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "nombres", column = @Column(name = "destino_contacto_nombres", nullable = false, length = 100)),
            @AttributeOverride(name = "telefonos", column = @Column(name = "destino_contacto_telefonos", nullable = false, length = 50)),
            @AttributeOverride(name = "email", column = @Column(name = "destino_contacto_email", nullable = false, length = 100)) })
    @NotNull
    private Contacto destinoContacto;

    // ---------------------------------------------------------------------------------------------------------
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "id_origen", nullable = true)
    private DestinoOrigen origen;

    @Column(nullable = false, length = 100)
    @NotNull
    private String origenNombre;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "nombres", column = @Column(name = "origen_contacto_nombres", nullable = false, length = 100)),
            @AttributeOverride(name = "telefonos", column = @Column(name = "origen_contacto_telefonos", nullable = false, length = 50)),
            @AttributeOverride(name = "email", column = @Column(name = "origen_contacto_email", nullable = false, length = 100)) })
    @NotNull
    private Contacto origenContacto;

    // ---------------------------------------------------------------------------------------------------------
    @Column(length = 200, nullable = false)
    @NotNull
    private String notasRequerimientosDistribucion;

    @Column(length = 200, nullable = false)
    @NotNull
    private String notasRequerimientosAlistamiento;

    @Column(nullable = true)
    private Integer valorRecaudo;

    // ---------------------------------------------------------------------------------------------------------
    @Column(length = 200, nullable = false)
    @NotNull
    private String notasConfirmacion;

    @Column(nullable = true, columnDefinition = "DATETIME2(0)")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaConfirmacion;

    @Column(nullable = false, length = 50)
    @NotNull
    private String usuarioConfirmacion;

    // ---------------------------------------------------------------------------------------------------------
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "fechaMinima", column = @Column(name = "fecha_entrega_planificada_minima", nullable = true, columnDefinition = "DATE")),
            @AttributeOverride(name = "fechaMaxima", column = @Column(name = "fecha_entrega_planificada_maxima", nullable = true, columnDefinition = "DATE")) })
    private IntervaloDeFechas intervaloFechaEntregaPlanificado;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "horaMinima", column = @Column(name = "hora_entrega_planificada_minima", nullable = true, columnDefinition = "TIME(0)")),
            @AttributeOverride(name = "horaMaxima", column = @Column(name = "hora_entrega_planificada_maxima", nullable = true, columnDefinition = "TIME(0)")) })
    private IntervaloDeHoras intervaloHoraEntregaPlanificado;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "horaMinima", column = @Column(name = "hora_entrega_planificada_minima_adicional", nullable = true, columnDefinition = "TIME(0)")),
            @AttributeOverride(name = "horaMaxima", column = @Column(name = "hora_entrega_planificada_maxima_adicional", nullable = true, columnDefinition = "TIME(0)")) })
    private IntervaloDeHoras intervaloHoraEntregaAdicionalPlanificado;

    // ---------------------------------------------------------------------------------------------------------
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "fechaMinima", column = @Column(name = "fecha_recogida_planificada_minima", nullable = true, columnDefinition = "DATE")),
            @AttributeOverride(name = "fechaMaxima", column = @Column(name = "fecha_recogida_planificada_maxima", nullable = true, columnDefinition = "DATE")) })
    private IntervaloDeFechas intervaloFechaRecogidaPlanificado;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "horaMinima", column = @Column(name = "hora_recogida_planificada_minima", nullable = true, columnDefinition = "TIME(0)")),
            @AttributeOverride(name = "horaMaxima", column = @Column(name = "hora_recogida_planificada_maxima", nullable = true, columnDefinition = "TIME(0)")) })
    private IntervaloDeHoras intervaloHoraRecogidaPlanificado;

    // ---------------------------------------------------------------------------------------------------------
    @Column(nullable = true, columnDefinition = "DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAlistamientoPlanificada;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "horaMinima", column = @Column(name = "hora_alistamiento_planificada_minima", nullable = true, columnDefinition = "TIME(0)")),
            @AttributeOverride(name = "horaMaxima", column = @Column(name = "hora_alistamiento_planificada_maxima", nullable = true, columnDefinition = "TIME(0)")) })
    private IntervaloDeHoras intervaloHoraAlistamientoPlanificado;

    // ---------------------------------------------------------------------------------------------------------
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "id_tipo_vehiculo_planificado", nullable = true)
    private TipoVehiculo tipoVehiculoPlanificado;

    @Column(nullable = true)
    private Integer valorFletePlanificado;

    // ---------------------------------------------------------------------------------------------------------
    @Column(length = 200, nullable = false)
    @NotNull
    private String notasAprobacionPlanificacion;

    @Column(nullable = true, columnDefinition = "DATETIME2(0)")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAprobacionPlanificacion;

    @Column(nullable = false, length = 50)
    @NotNull
    private String usuarioAprobacionPlanificacion;

    // ---------------------------------------------------------------------------------------------------------
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "estadoGeoReferenciacion", column = @Column(name = "id_estado_georeferenciacion_destino", nullable = false, length = 50)),
            @AttributeOverride(name = "tipoGeoReferenciacionId", column = @Column(name = "id_tipo_georeferenciacion_destino", nullable = true)),
            @AttributeOverride(name = "cx", column = @Column(name = "destino_cx", nullable = true, precision = 18, scale = 15)),
            @AttributeOverride(name = "cy", column = @Column(name = "destino_cy", nullable = true, precision = 18, scale = 15)),
            @AttributeOverride(name = "direccionEstandarizada", column = @Column(name = "destino_direccion_estandarizada", nullable = false, length = 150)),
            @AttributeOverride(name = "direccionSugerida", column = @Column(name = "destino_direccion_sugerida", nullable = false, length = 150)),
            @AttributeOverride(name = "zona", column = @Column(name = "destino_zona", nullable = false, length = 100)),
            @AttributeOverride(name = "localidad", column = @Column(name = "destino_localidad", nullable = false, length = 100)),
            @AttributeOverride(name = "barrio", column = @Column(name = "destino_barrio", nullable = false, length = 100)),
            @AttributeOverride(name = "fechaGeoreferenciacion", column = @Column(name = "fecha_georeferenciacion_destino", nullable = true, columnDefinition = "DATETIME2(0)")),
            @AttributeOverride(name = "usuarioGeoreferenciacion", column = @Column(name = "usuario_georeferenciacion_destino", nullable = false, length = 50)) })
    private OmsDireccionGeoreferenciada destinoDireccionGeoreferenciada;

    // ---------------------------------------------------------------------------------------------------------
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "estadoGeoReferenciacion", column = @Column(name = "id_estado_georeferenciacion_origen", nullable = false, length = 50)),
            @AttributeOverride(name = "tipoGeoReferenciacionId", column = @Column(name = "id_tipo_georeferenciacion_origen", nullable = true)),
            @AttributeOverride(name = "cx", column = @Column(name = "origen_cx", nullable = true, precision = 18, scale = 15)),
            @AttributeOverride(name = "cy", column = @Column(name = "origen_cy", nullable = true, precision = 18, scale = 15)),
            @AttributeOverride(name = "direccionEstandarizada", column = @Column(name = "origen_direccion_estandarizada", nullable = false, length = 150)),
            @AttributeOverride(name = "direccionSugerida", column = @Column(name = "origen_direccion_sugerida", nullable = false, length = 150)),
            @AttributeOverride(name = "zona", column = @Column(name = "origen_zona", nullable = false, length = 100)),
            @AttributeOverride(name = "localidad", column = @Column(name = "origen_localidad", nullable = false, length = 100)),
            @AttributeOverride(name = "barrio", column = @Column(name = "origen_barrio", nullable = false, length = 100)),
            @AttributeOverride(name = "fechaGeoreferenciacion", column = @Column(name = "fecha_georeferenciacion_origen", nullable = true, columnDefinition = "DATETIME2(0)")),
            @AttributeOverride(name = "usuarioGeoreferenciacion", column = @Column(name = "usuario_georeferenciacion_origen", nullable = false, length = 50)) })
    private OmsDireccionGeoreferenciada origenDireccionGeoreferenciada;
    // ---------------------------------------------------------------------------------------------------------
    @Enumerated(EnumType.STRING)
    @Column(name = "id_estado_planificacion_ruta", nullable = false, length = 50)
    @NotNull
    private EstadoPlanificacionRutaType estadoPlanificacionRuta;

    @Column(name = "id_corte_planificacion_ruta", nullable = true)
    private Integer cortePlanificacionRutaId;

    @Column(nullable = true, columnDefinition = "DATETIME2(0)")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCortePlanificacionRuta;

    @Column(nullable = false, length = 50)
    @NotNull
    private String usuarioCortePlanificacionRuta;

    // ---------------------------------------------------------------------------------------------------------
    @Column(name = "id_ruta", nullable = true)
    private Integer rutaId;

    @Column(nullable = true, columnDefinition = "DATETIME2(0)")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAsignacionRuta;

    @Column(nullable = false, length = 50)
    @NotNull
    private String usuarioAsignacionRuta;

    // ---------------------------------------------------------------------------------------------------------
    @Column(nullable = false, columnDefinition = "DATETIME2(0)")
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date fechaCreacion;

    @Column(nullable = false, length = 50)
    @NotNull
    private String usuarioCreacion;

    @Column(nullable = false, columnDefinition = "DATETIME2(0)")
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date fechaActualizacion;

    @Column(nullable = false, length = 50)
    @NotNull
    private String usuarioActualizacion;

    // ---------------------------------------------------------------------------------------------------------
    @Column(length = 200, nullable = false)
    @NotNull
    private String notasAnulacion;

    @Column(name = "id_causal_anulacion", nullable = true)
    private Integer causalAnulacionId;

    @Column(nullable = false, length = 50)
    @NotNull
    private String usuarioAnulacion;

    @Column(nullable = true, columnDefinition = "DATETIME2(0)")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAnulacion;

    // ---------------------------------------------------------------------------------------------------------
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ordenId", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OmsLineaOrden> lineas;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ordenId", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OmsOrdenRequerimientoDistribucionAssociation> requerimientosDistribucion;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ordenId", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OmsOrdenRequerimientoAlistamientoAssociation> requerimientosAlistamiento;

    @ElementCollection
    @CollectionTable(name = "ordenes_mensajes", catalog = "oms", joinColumns = @JoinColumn(name = "id_orden", referencedColumnName = "id_orden"))
    private Set<MensajeEmbeddable> mensajes;

    // ---------------------------------------------------------------------------------------------------------
    public OmsOrden() {
        super();
        this.setId(null);
        this.setNumeroOrden("");
        this.setConsolidado(null);
        this.setEstadoOrden(EstadoOrdenType.NO_CONFIRMADA);
        this.setEstadoDistribucion(EstadoDistribucionType.NO_PROGRAMADO);
        this.setEstadoAlistamiento(EstadoAlistamientoType.NO_PROGRAMADO);
        this.setCliente(null);
        this.setClienteCodigo("");
        this.setTipoServicio(null);
        this.setTipoServicioCodigo("");
        this.setRequiereServicioDistribucion(true);
        this.setRequiereServicioAlistamiento(true);
        this.setCiudadDestino(null);
        this.setDestinoCiudadNombreAlterno("");
        this.setDestinoDireccion("");
        this.setDestinoIndicaciones("");
        this.setCiudadOrigen(null);
        this.setOrigenCiudadNombreAlterno("");
        this.setOrigenDireccion("");
        this.setOrigenIndicaciones("");
        this.setRequiereConfirmacionCitaEntrega(false);
        this.setIntervaloFechaEntregaSugerido(null);
        this.setIntervaloHoraEntregaSugerido(null);
        this.setIntervaloHoraEntregaAdicionalSugerido(null);
        this.setRequiereConfirmacionCitaRecogida(false);
        this.setIntervaloFechaRecogidaSugerido(null);
        this.setIntervaloHoraRecogidaSugerido(null);
        this.setCanal(null);
        this.setCanalCodigoAlterno("");
        this.setDestinatario(null);
        this.setDestinatarioNumeroIdentificacion("");
        this.setDestinatarioNombre("");
        this.setDestinatarioContacto(null);
        this.setDestino(null);
        this.setDestinoNombre("");
        this.setDestinoContacto(null);
        this.setOrigen(null);
        this.setOrigenNombre("");
        this.setOrigenContacto(null);
        this.setNotasRequerimientosDistribucion("");
        this.setNotasRequerimientosAlistamiento("");
        this.setValorRecaudo(null);
        this.setNotasConfirmacion("");
        this.setFechaConfirmacion(null);
        this.setUsuarioConfirmacion("");
        this.setIntervaloFechaEntregaPlanificado(null);
        this.setIntervaloHoraEntregaPlanificado(null);
        this.setIntervaloHoraEntregaAdicionalPlanificado(null);
        this.setIntervaloFechaRecogidaPlanificado(null);
        this.setIntervaloHoraRecogidaPlanificado(null);
        this.setFechaAlistamientoPlanificada(null);
        this.setIntervaloHoraAlistamientoPlanificado(null);
        this.setTipoVehiculoPlanificado(null);
        this.setValorFletePlanificado(null);
        this.setNotasAprobacionPlanificacion("");
        this.setFechaAprobacionPlanificacion(null);
        this.setUsuarioAprobacionPlanificacion("");
        this.setDestinoDireccionGeoreferenciada(null);
        this.setOrigenDireccionGeoreferenciada(null);
        this.setEstadoPlanificacionRuta(EstadoPlanificacionRutaType.SIN_SOLICITUD);
        this.setCortePlanificacionRutaId(null);
        this.setFechaPlanificacionRuta(null);
        this.setUsuarioPlanificacionRuta("");
        this.setRutaId(null);
        this.setFechaAsignacionRuta(null);
        this.setUsuarioAsignacionRuta("");
        this.setFechaCreacion(null);
        this.setUsuarioCreacion("");
        this.setFechaActualizacion(null);
        this.setUsuarioActualizacion("");
        this.setNotasAnulacion("");
        this.setCausalAnulacionId(null);
        this.setUsuarioAnulacion("");
        this.setFechaAnulacion(null);

        this.lineas = new HashSet<>();
        this.mensajes = new HashSet<>();
        this.requerimientosDistribucion = new HashSet<>();
        this.requerimientosAlistamiento = new HashSet<>();
    }

    // ---------------------------------------------------------------------------------------------------------
    public Integer getId() {
        return id;
    }

    public String getNumeroOrden() {
        return numeroOrden;
    }

    public OmsConsolidado getConsolidado() {
        return consolidado;
    }

    // ---------------------------------------------------------------------------------------------------------
    public EstadoOrdenType getEstadoOrden() {
        return estadoOrden;
    }

    public EstadoDistribucionType getEstadoDistribucion() {
        return estadoDistribucion;
    }

    public EstadoAlistamientoType getEstadoAlistamiento() {
        return estadoAlistamiento;
    }

    // ---------------------------------------------------------------------------------------------------------
    public Cliente getCliente() {
        return cliente;
    }

    public String getClienteCodigo() {
        return clienteCodigo;
    }

    // ---------------------------------------------------------------------------------------------------------
    public TipoServicio getTipoServicio() {
        return tipoServicio;
    }

    public String getTipoServicioCodigo() {
        return tipoServicioCodigoAlterno;
    }

    public boolean isRequiereServicioDistribucion() {
        return requiereServicioDistribucion;
    }

    public boolean isRequiereServicioAlistamiento() {
        return requiereServicioAlistamiento;
    }

    // ---------------------------------------------------------------------------------------------------------
    public Ciudad getCiudadDestino() {
        return ciudadDestino;
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

    // ---------------------------------------------------------------------------------------------------------
    public Ciudad getCiudadOrigen() {
        return ciudadOrigen;
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

    // ---------------------------------------------------------------------------------------------------------
    public boolean isRequiereConfirmacionCitaEntrega() {
        return requiereConfirmacionCitaEntrega;
    }

    public Date getFechaEntregaMinimaSugerida() {
        return getIntervaloFechaEntregaSugerido().getFechaMinima();
    }

    public Date getFechaEntregaMaximaSugerida() {
        return getIntervaloFechaEntregaSugerido().getFechaMaxima();
    }

    public Time getHoraEntregaMinimaSugerida() {
        return getIntervaloHoraEntregaSugerido().getHoraMinima();
    }

    public Time getHoraEntregaMaximaSugerida() {
        return getIntervaloHoraEntregaSugerido().getHoraMaxima();
    }

    public Time getHoraEntregaMinimaAdicionalSugerida() {
        return getIntervaloHoraEntregaAdicionalSugerido().getHoraMinima();
    }

    public Time getHoraEntregaMaximaAdicionalSugerida() {
        return getIntervaloHoraEntregaAdicionalSugerido().getHoraMaxima();
    }

    protected IntervaloDeFechas getIntervaloFechaEntregaSugerido() {
        return intervaloFechaEntregaSugerido;
    }

    protected IntervaloDeHoras getIntervaloHoraEntregaSugerido() {
        return intervaloHoraEntregaSugerido;
    }

    protected IntervaloDeHoras getIntervaloHoraEntregaAdicionalSugerido() {
        return intervaloHoraEntregaAdicionalSugerido;
    }

    // ---------------------------------------------------------------------------------------------------------
    public boolean isRequiereConfirmacionCitaRecogida() {
        return requiereConfirmacionCitaRecogida;
    }

    public Date getFechaRecogidaMinimaSugerida() {
        return getIntervaloFechaRecogidaSugerido().getFechaMinima();
    }

    public Date getFechaRecogidaMaximaSugerida() {
        return getIntervaloFechaRecogidaSugerido().getFechaMaxima();
    }

    public Time getHoraRecogidaMinimaSugerida() {
        return getIntervaloHoraRecogidaSugerido().getHoraMinima();
    }

    public Time getHoraRecogidaMaximaSugerida() {
        return getIntervaloHoraRecogidaSugerido().getHoraMaxima();
    }

    protected IntervaloDeFechas getIntervaloFechaRecogidaSugerido() {
        return intervaloFechaRecogidaSugerido;
    }

    protected IntervaloDeHoras getIntervaloHoraRecogidaSugerido() {
        return intervaloHoraRecogidaSugerido;
    }

    // ---------------------------------------------------------------------------------------------------------
    public Canal getCanal() {
        return canal;
    }

    public String getCanalCodigoAlterno() {
        return canalCodigoAlterno;
    }

    // ---------------------------------------------------------------------------------------------------------
    public DestinatarioRemitente getDestinatario() {
        return destinatario;
    }

    public String getDestinatarioNumeroIdentificacion() {
        return destinatarioNumeroIdentificacion;
    }

    public String getDestinatarioNombre() {
        return destinatarioNombre;
    }

    public Contacto getDestinatarioContacto() {
        if (this.destinatarioContacto == null) {
            // this.setDestinatario
        }

        return destinatarioContacto;
    }

    // ---------------------------------------------------------------------------------------------------------
    public DestinoOrigen getDestino() {
        return destino;
    }

    public String getDestinoNombre() {
        return destinoNombre;
    }

    public Contacto getDestinoContacto() {
        if (this.destinoContacto == null) {

        }
        return destinoContacto;
    }

    // ---------------------------------------------------------------------------------------------------------
    public DestinoOrigen getOrigen() {
        return origen;
    }

    public String getOrigenNombre() {
        return origenNombre;
    }

    public Contacto getOrigenContacto() {
        return origenContacto;
    }

    // ---------------------------------------------------------------------------------------------------------
    public String getNotasRequerimientosDistribucion() {
        return notasRequerimientosDistribucion;
    }

    public String getNotasRequerimientosAlistamiento() {
        return notasRequerimientosAlistamiento;
    }

    public Integer getValorRecaudo() {
        return valorRecaudo;
    }

    // ---------------------------------------------------------------------------------------------------------
    public String getNotasConfirmacion() {
        return notasConfirmacion;
    }

    public Date getFechaConfirmacion() {
        return fechaConfirmacion;
    }

    public String getUsuarioConfirmacion() {
        return usuarioConfirmacion;
    }

    // ---------------------------------------------------------------------------------------------------------
    public Date getFechaEntregaMinimaPlanificada() {
        return getIntervaloFechaEntregaPlanificado().getFechaMinima();
    }

    public Date getFechaEntregaMaximaPlanificada() {
        return getIntervaloFechaEntregaPlanificado().getFechaMaxima();
    }

    public Time getHoraEntregaMinimaPlanificada() {
        return getIntervaloHoraEntregaPlanificado().getHoraMinima();
    }

    public Time getHoraEntregaMaximaPlanificada() {
        return getIntervaloHoraEntregaPlanificado().getHoraMaxima();
    }

    public Time getHoraEntregaMinimaAdicionalPlanificada() {
        return getIntervaloHoraEntregaAdicionalPlanificado().getHoraMinima();
    }

    public Time getHoraEntregaMaximaAdicionalPlanificada() {
        return getIntervaloHoraEntregaAdicionalPlanificado().getHoraMaxima();
    }

    protected IntervaloDeFechas getIntervaloFechaEntregaPlanificado() {
        return intervaloFechaEntregaPlanificado;
    }

    protected IntervaloDeHoras getIntervaloHoraEntregaPlanificado() {
        return intervaloHoraEntregaPlanificado;
    }

    protected IntervaloDeHoras getIntervaloHoraEntregaAdicionalPlanificado() {
        return intervaloHoraEntregaAdicionalPlanificado;
    }

    // ---------------------------------------------------------------------------------------------------------
    public Date getFechaRecogidaMinimaPlanificada() {
        return getIntervaloFechaRecogidaPlanificado().getFechaMinima();
    }

    public Date getFechaRecogidaMaximaPlanificada() {
        return getIntervaloFechaRecogidaPlanificado().getFechaMaxima();
    }

    public Time getHoraRecogidaMinimaPlanificada() {
        return getIntervaloHoraRecogidaPlanificado().getHoraMinima();
    }

    public Time getHoraRecogidaMaximaPlanificada() {
        return getIntervaloHoraRecogidaPlanificado().getHoraMaxima();
    }

    protected IntervaloDeFechas getIntervaloFechaRecogidaPlanificado() {
        return intervaloFechaRecogidaPlanificado;
    }

    protected IntervaloDeHoras getIntervaloHoraRecogidaPlanificado() {
        return intervaloHoraRecogidaPlanificado;
    }

    // ---------------------------------------------------------------------------------------------------------
    public Date getFechaAlistamientoPlanificada() {
        return fechaAlistamientoPlanificada;
    }

    public Time getHoraAlistamientoMinimaPlanificada() {
        return getIntervaloHoraAlistamientoPlanificado().getHoraMinima();
    }

    public Time getHoraAlistamientoMaximaPlanificada() {
        return getIntervaloHoraAlistamientoPlanificado().getHoraMaxima();
    }

    protected IntervaloDeHoras getIntervaloHoraAlistamientoPlanificado() {
        if (intervaloHoraAlistamientoPlanificado == null) {

        }
        return intervaloHoraAlistamientoPlanificado;
    }

    // ---------------------------------------------------------------------------------------------------------
    public TipoVehiculo getTipoVehiculoPlanificado() {
        return tipoVehiculoPlanificado;
    }

    public Integer getValorFletePlanificado() {
        return valorFletePlanificado;
    }

    // ---------------------------------------------------------------------------------------------------------
    public String getNotasAprobacionPlanificacion() {
        return notasAprobacionPlanificacion;
    }

    public Date getFechaAprobacionPlanificacion() {
        return fechaAprobacionPlanificacion;
    }

    public String getUsuarioAprobacionPlanificacion() {
        return usuarioAprobacionPlanificacion;
    }

    // ---------------------------------------------------------------------------------------------------------
    public OmsDireccionGeoreferenciada getDestinoDireccionGeoreferenciada() {
        if (destinoDireccionGeoreferenciada == null) {

        }
        return destinoDireccionGeoreferenciada;
    }

    public OmsDireccionGeoreferenciada getOrigenDireccionGeoreferenciada() {
        if (origenDireccionGeoreferenciada == null) {

        }
        return origenDireccionGeoreferenciada;
    }

    // ---------------------------------------------------------------------------------------------------------
    public EstadoPlanificacionRutaType getEstadoPlanificacionRuta() {
        return estadoPlanificacionRuta;
    }

    public Integer getCortePlanificacionRutaId() {
        return cortePlanificacionRutaId;
    }

    public Date getFechaPlanificacionRuta() {
        return fechaCortePlanificacionRuta;
    }

    public String getUsuarioPlanificacionRuta() {
        return usuarioCortePlanificacionRuta;
    }

    // ---------------------------------------------------------------------------------------------------------
    public Integer getRutaId() {
        return rutaId;
    }

    public Date getFechaAsignacionRuta() {
        return fechaAsignacionRuta;
    }

    public String getUsuarioAsignacionRuta() {
        return usuarioAsignacionRuta;
    }

    // ---------------------------------------------------------------------------------------------------------
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

    // ---------------------------------------------------------------------------------------------------------
    public String getNotasAnulacion() {
        return notasAnulacion;
    }

    public Integer getCausalAnulacionId() {
        return causalAnulacionId;
    }

    public String getUsuarioAnulacion() {
        return usuarioAnulacion;
    }

    public Date getFechaAnulacion() {
        return fechaAnulacion;
    }

    // ---------------------------------------------------------------------------------------------------------
    protected void setId(Integer id) {
        this.id = id;
    }

    protected void setNumeroOrden(String value) {
        this.numeroOrden = coalesce(value, "");
    }

    protected void setConsolidado(OmsConsolidado consolidado) {
        this.consolidado = consolidado;
    }

    // ---------------------------------------------------------------------------------------------------------
    protected void setEstadoOrden(EstadoOrdenType estadoOrden) {
        this.estadoOrden = estadoOrden;
    }

    protected void setEstadoDistribucion(EstadoDistribucionType estadoDistribucion) {
        this.estadoDistribucion = estadoDistribucion;
    }

    protected void setEstadoAlistamiento(EstadoAlistamientoType estadoAlistamiento) {
        this.estadoAlistamiento = estadoAlistamiento;
    }

    // ---------------------------------------------------------------------------------------------------------
    protected void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    protected void setClienteCodigo(String value) {
        this.clienteCodigo = coalesce(value, "");
    }

    protected void setTipoServicio(TipoServicio tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    protected void setTipoServicioCodigo(String value) {
        this.tipoServicioCodigoAlterno = coalesce(value, "");
    }

    protected void setRequiereServicioDistribucion(boolean requiereServicioDistribucion) {
        this.requiereServicioDistribucion = requiereServicioDistribucion;
    }

    protected void setRequiereServicioAlistamiento(boolean requiereServicioAlistamiento) {
        this.requiereServicioAlistamiento = requiereServicioAlistamiento;
    }

    // ---------------------------------------------------------------------------------------------------------
    protected void setCiudadDestino(Ciudad ciudadDestino) {
        this.ciudadDestino = ciudadDestino;
    }

    protected void setDestinoCiudadNombreAlterno(String value) {
        this.destinoCiudadNombreAlterno = coalesce(value, "");
    }

    protected void setDestinoDireccion(String value) {
        this.destinoDireccion = coalesce(value, "");
    }

    protected void setDestinoIndicaciones(String value) {
        this.destinoIndicaciones = coalesce(value, "");
    }

    // ---------------------------------------------------------------------------------------------------------
    protected void setCiudadOrigen(Ciudad ciudadOrigen) {
        this.ciudadOrigen = ciudadOrigen;
    }

    protected void setOrigenCiudadNombreAlterno(String value) {
        this.origenCiudadNombreAlterno = coalesce(value, "");
    }

    protected void setOrigenDireccion(String value) {
        this.origenDireccion = coalesce(value, "");
    }

    protected void setOrigenIndicaciones(String value) {
        this.origenIndicaciones = coalesce(value, "");
    }

    // ---------------------------------------------------------------------------------------------------------
    protected void setRequiereConfirmacionCitaEntrega(boolean requiereConfirmacionCitaEntrega) {
        this.requiereConfirmacionCitaEntrega = requiereConfirmacionCitaEntrega;
    }

    protected void setIntervaloFechaEntregaSugerido(IntervaloDeFechas value) {
        this.intervaloFechaEntregaSugerido = coalesce(value, new IntervaloDeFechas(null, null));
    }

    protected void setIntervaloHoraEntregaSugerido(IntervaloDeHoras value) {
        this.intervaloHoraEntregaSugerido = coalesce(value, new IntervaloDeHoras(null, null));
    }

    protected void setIntervaloHoraEntregaAdicionalSugerido(IntervaloDeHoras value) {
        this.intervaloHoraEntregaAdicionalSugerido = coalesce(value, new IntervaloDeHoras(null, null));
    }

    // ---------------------------------------------------------------------------------------------------------
    protected void setRequiereConfirmacionCitaRecogida(boolean requiereConfirmacionCitaRecogida) {
        this.requiereConfirmacionCitaRecogida = requiereConfirmacionCitaRecogida;
    }

    protected void setIntervaloFechaRecogidaSugerido(IntervaloDeFechas value) {
        this.intervaloFechaRecogidaSugerido = coalesce(value, new IntervaloDeFechas(null, null));
    }

    protected void setIntervaloHoraRecogidaSugerido(IntervaloDeHoras value) {
        this.intervaloHoraRecogidaSugerido = coalesce(value, new IntervaloDeHoras(null, null));
    }

    // ---------------------------------------------------------------------------------------------------------
    protected void setCanal(Canal canal) {
        this.canal = canal;
    }

    protected void setCanalCodigoAlterno(String value) {
        this.canalCodigoAlterno = coalesce(value, "");
    }

    protected void setDestinatario(DestinatarioRemitente destinatario) {
        this.destinatario = destinatario;
    }

    protected void setDestinatarioNumeroIdentificacion(String value) {
        this.destinatarioNumeroIdentificacion = coalesce(value, "");
    }

    protected void setDestinatarioNombre(String value) {
        this.destinatarioNombre = coalesce(value, "");
    }

    protected void setDestinatarioContacto(Contacto value) {
        this.destinatarioContacto = coalesce(value, new Contacto("", "", ""));
    }

    // ---------------------------------------------------------------------------------------------------------
    protected void setDestino(DestinoOrigen destino) {
        this.destino = destino;
    }

    protected void setDestinoNombre(String value) {
        this.destinoNombre = coalesce(value, "");
    }

    protected void setDestinoContacto(Contacto value) {
        this.destinoContacto = coalesce(value, new Contacto("", "", ""));
    }

    // ---------------------------------------------------------------------------------------------------------
    protected void setOrigen(DestinoOrigen origen) {
        this.origen = origen;
    }

    protected void setOrigenNombre(String value) {
        this.origenNombre = coalesce(value, "");
    }

    protected void setOrigenContacto(Contacto value) {
        this.origenContacto = coalesce(value, new Contacto("", "", ""));
    }

    // ---------------------------------------------------------------------------------------------------------
    protected void setNotasRequerimientosDistribucion(String value) {
        this.notasRequerimientosDistribucion = coalesce(value, "");
    }

    protected void setNotasRequerimientosAlistamiento(String value) {
        this.notasRequerimientosAlistamiento = coalesce(value, "");
    }

    protected void setValorRecaudo(Integer valorRecaudo) {
        this.valorRecaudo = valorRecaudo;
    }

    // ---------------------------------------------------------------------------------------------------------
    protected void setNotasConfirmacion(String value) {
        this.notasConfirmacion = coalesce(value, "");
    }

    protected void setFechaConfirmacion(Date fechaConfirmacion) {
        this.fechaConfirmacion = fechaConfirmacion;
    }

    protected void setUsuarioConfirmacion(String value) {
        this.usuarioConfirmacion = coalesce(value, "");
    }

    // ---------------------------------------------------------------------------------------------------------
    protected void setIntervaloFechaEntregaPlanificado(IntervaloDeFechas value) {
        this.intervaloFechaEntregaPlanificado = coalesce(value, new IntervaloDeFechas(null, null));
    }

    protected void setIntervaloHoraEntregaPlanificado(IntervaloDeHoras value) {
        this.intervaloHoraEntregaPlanificado = coalesce(value, new IntervaloDeHoras(null, null));
    }

    protected void setIntervaloHoraEntregaAdicionalPlanificado(IntervaloDeHoras value) {
        this.intervaloHoraEntregaAdicionalPlanificado = coalesce(value, new IntervaloDeHoras(null, null));
    }

    // ---------------------------------------------------------------------------------------------------------
    protected void setIntervaloFechaRecogidaPlanificado(IntervaloDeFechas value) {
        this.intervaloFechaRecogidaPlanificado = coalesce(value, new IntervaloDeFechas(null, null));
    }

    protected void setIntervaloHoraRecogidaPlanificado(IntervaloDeHoras value) {
        this.intervaloHoraRecogidaPlanificado = coalesce(value, new IntervaloDeHoras(null, null));
    }

    // ---------------------------------------------------------------------------------------------------------
    protected void setFechaAlistamientoPlanificada(Date fechaAlistamientoPlanificada) {
        this.fechaAlistamientoPlanificada = fechaAlistamientoPlanificada;
    }

    protected void setIntervaloHoraAlistamientoPlanificado(IntervaloDeHoras value) {
        this.intervaloHoraAlistamientoPlanificado = coalesce(value, new IntervaloDeHoras(null, null));
    }

    // ---------------------------------------------------------------------------------------------------------
    protected void setTipoVehiculoPlanificado(TipoVehiculo tipoVehiculoPlanificado) {
        this.tipoVehiculoPlanificado = tipoVehiculoPlanificado;
    }

    protected void setValorFletePlanificado(Integer valorFletePlanificado) {
        this.valorFletePlanificado = valorFletePlanificado;
    }

    // ---------------------------------------------------------------------------------------------------------
    protected void setNotasAprobacionPlanificacion(String value) {
        this.notasAprobacionPlanificacion = coalesce(value, "");
    }

    protected void setFechaAprobacionPlanificacion(Date fechaAprobacionPlanificacion) {
        this.fechaAprobacionPlanificacion = fechaAprobacionPlanificacion;
    }

    protected void setUsuarioAprobacionPlanificacion(String value) {
        this.usuarioAprobacionPlanificacion = coalesce(value, "");
    }

    // ---------------------------------------------------------------------------------------------------------
    protected void setDestinoDireccionGeoreferenciada(OmsDireccionGeoreferenciada value) {
        this.destinoDireccionGeoreferenciada = coalesce(value, new OmsDireccionGeoreferenciada());
    }

    protected void setOrigenDireccionGeoreferenciada(OmsDireccionGeoreferenciada value) {
        this.origenDireccionGeoreferenciada = coalesce(value, new OmsDireccionGeoreferenciada());
    }

    // ---------------------------------------------------------------------------------------------------------
    protected void setEstadoPlanificacionRuta(EstadoPlanificacionRutaType estadoPlanificacionRuta) {
        this.estadoPlanificacionRuta = estadoPlanificacionRuta;
    }

    protected void setCortePlanificacionRutaId(Integer cortePlanificacionRutaId) {
        this.cortePlanificacionRutaId = cortePlanificacionRutaId;
    }

    protected void setFechaPlanificacionRuta(Date fechaPlanificacionRuta) {
        this.fechaCortePlanificacionRuta = fechaPlanificacionRuta;
    }

    protected void setUsuarioPlanificacionRuta(String value) {
        this.usuarioCortePlanificacionRuta = coalesce(value, "");
    }

    // ---------------------------------------------------------------------------------------------------------
    protected void setRutaId(Integer rutaId) {
        this.rutaId = rutaId;
    }

    protected void setFechaAsignacionRuta(Date fechaAsignacionRuta) {
        this.fechaAsignacionRuta = fechaAsignacionRuta;
    }

    protected void setUsuarioAsignacionRuta(String value) {
        this.usuarioAsignacionRuta = coalesce(value, "");
    }

    // ---------------------------------------------------------------------------------------------------------
    protected void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    protected void setUsuarioCreacion(String value) {
        this.usuarioCreacion = coalesce(value, "");
    }

    protected void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    protected void setUsuarioActualizacion(String value) {
        this.usuarioActualizacion = coalesce(value, "");
    }

    // ---------------------------------------------------------------------------------------------------------
    protected void setNotasAnulacion(String value) {
        this.notasAnulacion = coalesce(value, "");
    }

    protected void setCausalAnulacionId(Integer causalAnulacionId) {
        this.causalAnulacionId = causalAnulacionId;
    }

    protected void setFechaAnulacion(Date fechaAnulacion) {
        this.fechaAnulacion = fechaAnulacion;
    }

    protected void setUsuarioAnulacion(String value) {
        this.usuarioAnulacion = coalesce(value, "");
    }

    // ---------------------------------------------------------------------------------------------------------
    public List<OmsLineaOrden> getLineas() {
        List<OmsLineaOrden> sorted = new ArrayList<>(getInternalLineas());
        PropertyComparator.sort(sorted, new MutableSortDefinition("numeroItem", true, true));
        return Collections.unmodifiableList(sorted);
    }

    public boolean addLinea(OmsLineaOrden e) {
        e.setOrdenId(this.getId());
        e.setNumeroItem(this.getMaximoNumeroItem() + 1);
        return this.getInternalLineas().add(e);
    }

    public OmsLineaOrden getLinea(Integer id) {

        if (id != null) {
            for (OmsLineaOrden linea : getInternalLineas()) {
                if (id.equals(linea.getId())) {
                    return linea;
                }
            }
        }
        return null;
    }

    public boolean removeLinea(OmsLineaOrden e) {
        boolean rc = this.getInternalLineas().remove(e);
        return rc;
    }

    public int getMaximoNumeroItem() {
        OptionalInt max = this.getInternalLineas().stream().mapToInt(a -> a.getNumeroItem()).max();
        return max.isPresent() ? max.getAsInt() : 0;
    }

    protected Set<OmsLineaOrden> getInternalLineas() {
        if (lineas == null) {
            this.lineas = new HashSet<>();
        }
        return lineas;
    }

    protected void setInternalLineas(Set<OmsLineaOrden> set) {
        if (this.lineas != null) {
            this.lineas.clear();
        }
        this.lineas = set;
    }

    // ---------------------------------------------------------------------------------------------------------
    public Set<OmsOrdenRequerimientoDistribucionAssociation> getRequerimientosDistribucion() {
        return requerimientosDistribucion;
    }

    protected void setRequerimientosDistribucion(
            Set<OmsOrdenRequerimientoDistribucionAssociation> requerimientosDistribucion) {
        this.requerimientosDistribucion = requerimientosDistribucion;
    }

    // ---------------------------------------------------------------------------------------------------------
    public Set<OmsOrdenRequerimientoAlistamientoAssociation> getRequerimientosAlistamiento() {
        return requerimientosAlistamiento;
    }

    protected void setRequerimientosAlistamiento(
            Set<OmsOrdenRequerimientoAlistamientoAssociation> requerimientosAlistamiento) {
        this.requerimientosAlistamiento = requerimientosAlistamiento;
    }

    // ---------------------------------------------------------------------------------------------------------
    public Set<MensajeEmbeddable> getMensajes() {
        return mensajes;
    }

    protected void setMensajes(Set<MensajeEmbeddable> mensajes) {
        this.mensajes = mensajes;
    }
}
