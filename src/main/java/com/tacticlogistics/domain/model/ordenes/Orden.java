package com.tacticlogistics.domain.model.ordenes;

import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.OptionalInt;
import java.util.Set;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
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

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;

import com.tacticlogistics.domain.model.common.valueobjects.Contacto;
import com.tacticlogistics.domain.model.common.valueobjects.Direccion;
import com.tacticlogistics.domain.model.common.valueobjects.IntervaloDeFechas;
import com.tacticlogistics.domain.model.common.valueobjects.IntervaloDeHoras;
import com.tacticlogistics.domain.model.common.valueobjects.MensajeEmbeddable;
import com.tacticlogistics.domain.model.crm.Cliente;
import com.tacticlogistics.domain.model.crm.DestinatarioRemitente;
import com.tacticlogistics.domain.model.crm.DestinoOrigen;
import com.tacticlogistics.domain.model.crm.Canal;
import com.tacticlogistics.domain.model.crm.TipoFormaPago;
import com.tacticlogistics.domain.model.crm.TipoServicio;
import com.tacticlogistics.domain.model.wms.Bodega;

@Entity
@Table(name = "Ordenes", catalog = "ordenes")

public class Orden implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_orden", unique = true, nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_tipo_servicio", nullable = false)
    private TipoServicio tipoServicio;

//    @Column(nullable = false, length = 30)
//    private String tipoServicioCodigoAlterno;

    // ---------------------------------------------------------------------------------------------------------
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @Column(nullable = false, length = 30)
    private String numeroDocumentoOrdenCliente;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "id_consolidado", nullable = true)
    private Consolidado consolidado;

    @Enumerated(EnumType.STRING)
    @Column(name = "id_estado_orden", nullable = false, length = 20)
    private EstadoOrdenType estadoOrden;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "id_causal_anulacion_orden", nullable = true)
    private CausalAnulacionOrden causalAnulacion;

    // ---------------------------------------------------------------------------------------------------------
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "id_segmento", nullable = true)
    private Canal canal;

    @Column(name = "segmento_codigo_alterno", length = 20, nullable = false)
    private String canalCodigoAlterno;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "id_destinatario_remitente_destinatario", nullable = true)
    private DestinatarioRemitente destinatario;

    @Column(length = 20, nullable = true)
    private String destinatarioNumeroIdentificacionAlterno;

    @Column(length = 20, nullable = true)
    private String destinatarioCodigoAlterno;

    @Column(length = 100, nullable = true)
    private String destinatarioNombreAlterno;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "nombres", column = @Column(name = "destinatario_contacto_nombres", nullable = false, length = 100)),
            @AttributeOverride(name = "telefonos", column = @Column(name = "destinatario_contacto_telefonos", nullable = false, length = 50)),
            @AttributeOverride(name = "email", column = @Column(name = "destinatario_contacto_email", nullable = false, length = 100)) })
    private Contacto destinatarioContacto;

    // ---------------------------------------------------------------------------------------------------------
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "id_destino_origen_destino", nullable = true)
    private DestinoOrigen destino;

    @Column(length = 20, nullable = true)
    private String destinoCodigoAlterno;

    @Column(length = 100, nullable = true)
    private String destinoNombreAlterno;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "direccion", column = @Column(name = "destino_direccion", nullable = true, length = 150)),
            @AttributeOverride(name = "indicacionesDireccion", column = @Column(name = "destino_indicaciones_direccion", nullable = true, length = 150)),
            @AttributeOverride(name = "longitud", column = @Column(name = "destino_longitud", nullable = true, precision = 18, scale = 15)),
            @AttributeOverride(name = "latitud", column = @Column(name = "destino_latitud", nullable = true, precision = 18, scale = 15)),
            @AttributeOverride(name = "direccionEstandarizada", column = @Column(name = "destino_direccion_estandarizada", nullable = true, length = 150)) })
    @AssociationOverrides({
            @AssociationOverride(name = "ciudad", joinColumns = @JoinColumn(name = "id_ciudad_destino", nullable = true)) })
    private Direccion destinoDireccion;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "nombres", column = @Column(name = "destino_contacto_nombres", nullable = true, length = 100)),
            @AttributeOverride(name = "telefonos", column = @Column(name = "destino_contacto_telefonos", nullable = true, length = 50)),
            @AttributeOverride(name = "email", column = @Column(name = "destino_contacto_email", nullable = true, length = 100)) })
    private Contacto destinoContacto;

    // ---------------------------------------------------------------------------------------------------------
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "id_bodega_destino", nullable = true)
    private Bodega bodegaDestino;

    @Column(length = 20, nullable = true)
    private String bodegaDestinoCodigoAlterno;

    @Column(length = 100, nullable = true)
    private String bodegaDestinoNombreAlterno;

    // ---------------------------------------------------------------------------------------------------------
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "fechaMinima", column = @Column(name = "fecha_entrega_sugerida_minima", nullable = true, columnDefinition = "DATE")),
            @AttributeOverride(name = "fechaMaxima", column = @Column(name = "fecha_entrega_sugerida_maxima", nullable = true, columnDefinition = "DATE")) })
    private IntervaloDeFechas intervaloDeFechasSugerido = new IntervaloDeFechas(null,null);

    @Column(nullable = true, columnDefinition = "DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEntregaMaximaSegunPromesaServicio;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "horaMinima", column = @Column(name = "hora_entrega_sugerida_minima", nullable = true, columnDefinition = "TIME(0)")),
            @AttributeOverride(name = "horaMaxima", column = @Column(name = "hora_entrega_sugerida_maxima", nullable = true, columnDefinition = "TIME(0)")) })
    private IntervaloDeHoras intervaloDeHorasSugerido = new IntervaloDeHoras(null,null);

    // ---------------------------------------------------------------------------------------------------------
    @Column(nullable = true)
    private Integer valorDeclarado;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "id_tipo_forma_pago", nullable = true)
    private TipoFormaPago tipoFormaPago;

    private boolean requiereMaquila;

    @Column(length = 200, nullable = true)
    private String notas;

    // ---------------------------------------------------------------------------------------------------------
    @Column(nullable = true, columnDefinition = "DATETIME2(0)")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaConfirmacion;

    @Column(nullable = false, length = 50)
    private String usuarioConfirmacion;

    @Column(nullable = true, columnDefinition = "DATETIME2(0)")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAceptacion;

    @Column(nullable = false, length = 50)
    private String usuarioAceptacion;

    @Column(nullable = true, columnDefinition = "DATETIME2(0)")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaActualizacion;

    @Column(nullable = false, length = 50)
    private String usuarioActualizacion;

    // ---------------------------------------------------------------------------------------------------------
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "orden", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<LineaOrden> lineas;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "orden", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CambioEstadoOrden> cambios;
    // TODO DOCUMENTOS
    // TODO REQUERIMIENTOS
    @ElementCollection
    @CollectionTable(name = "ordenes_mensajes", catalog = "ordenes", joinColumns = @JoinColumn(name = "id_orden", referencedColumnName = "id_orden"))
    private Set<MensajeEmbeddable> mensajes;

    // ---------------------------------------------------------------------------------------------------------
    public Orden() {
        super();
        this.destinatarioContacto = new Contacto();
        this.destinoDireccion = new Direccion();
        this.destinoContacto = new Contacto();
        this.intervaloDeFechasSugerido = new IntervaloDeFechas(null,null);
        this.intervaloDeHorasSugerido = new IntervaloDeHoras(null,null);

        this.lineas = new HashSet<>();
        this.cambios = new HashSet<>();
        this.mensajes = new HashSet<>();
    }

    // ---------------------------------------------------------------------------------------------------------
    public Integer getId() {
        return id;
    }

    public TipoServicio getTipoServicio() {
        return tipoServicio;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public String getNumeroDocumentoOrdenCliente() {
        return numeroDocumentoOrdenCliente;
    }

    public Consolidado getConsolidado() {
        return consolidado;
    }

    public EstadoOrdenType getEstadoOrden() {
        return estadoOrden;
    }

    public CausalAnulacionOrden getCausalAnulacion() {
        return causalAnulacion;
    }

    public Canal getSegmento() {
        return canal;
    }

    public String getSegmentoCodigoAlterno() {
        return canalCodigoAlterno;
    }

    public DestinatarioRemitente getDestinatario() {
        return destinatario;
    }

    public String getDestinatarioNumeroIdentificacionAlterno() {
        return destinatarioNumeroIdentificacionAlterno;
    }

    public String getDestinatarioCodigoAlterno() {
        return destinatarioCodigoAlterno;
    }

    public String getDestinatarioNombreAlterno() {
        return destinatarioNombreAlterno;
    }

    public Contacto getDestinatarioContacto() {
        if (this.destinatarioContacto == null) {
            this.destinatarioContacto = new Contacto();
        }
        return destinatarioContacto;
    }

    public DestinoOrigen getDestino() {
        return destino;
    }

    public String getDestinoCodigoAlterno() {
        return destinoCodigoAlterno;
    }

    public String getDestinoNombreAlterno() {
        return destinoNombreAlterno;
    }

    public Direccion getDestinoDireccion() {
        if (this.destinoDireccion == null) {
            this.destinoDireccion = new Direccion();
        }

        return destinoDireccion;
    }

    public Contacto getDestinoContacto() {
        if (this.destinoContacto == null) {
            this.destinoContacto = new Contacto();
        }

        return destinoContacto;
    }

    public Bodega getBodegaDestino() {
        return bodegaDestino;
    }

    public String getBodegaDestinoCodigoAlterno() {
        return bodegaDestinoCodigoAlterno;
    }

    public String getBodegaDestinoNombreAlterno() {
        return bodegaDestinoNombreAlterno;
    }

    public Date getFechaEntregaMaximaSegunPromesaServicio() {
        return fechaEntregaMaximaSegunPromesaServicio;
    }

    public Integer getValorDeclarado() {
        return valorDeclarado;
    }

    public TipoFormaPago getTipoFormaPago() {
        return tipoFormaPago;
    }

    public boolean isRequiereMaquila() {
        return requiereMaquila;
    }

    public String getNotas() {
        return notas;
    }

    public Date getFechaConfirmacion() {
        return fechaConfirmacion;
    }

    public String getUsuarioConfirmacion() {
        return usuarioConfirmacion;
    }

    public Date getFechaAceptacion() {
        return fechaAceptacion;
    }

    public String getUsuarioAceptacion() {
        return usuarioAceptacion;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public String getUsuarioActualizacion() {
        return usuarioActualizacion;
    }

    // ---------------------------------------------------------------------------------------------------------
    public void setTipoServicio(TipoServicio tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public void setConsolidado(Consolidado consolidado) {
        this.consolidado = consolidado;
    }

    public void setCanal(Canal canal) {
        this.canal = canal;
    }

    public void setDestinatario(DestinatarioRemitente destinatario) {
        this.destinatario = destinatario;
    }

    public void setDestino(DestinoOrigen destino) {
        this.destino = destino;
    }

    public void setBodegaDestino(Bodega bodegaDestino) {
        this.bodegaDestino = bodegaDestino;
    }

    public void setTipoFormaPago(TipoFormaPago tipoFormaPago) {
        this.tipoFormaPago = tipoFormaPago;
    }

    public void setDestinoDireccion(Direccion destinoDireccion) {
        this.destinoDireccion = new Direccion(destinoDireccion);
    }

    public void setDestinatarioContacto(Contacto destinatarioContacto) {
        this.destinatarioContacto = new Contacto(destinatarioContacto);
    }

    public void setDestinoContacto(Contacto destinoContacto) {
        this.destinoContacto = new Contacto(destinoContacto);
    }

    // ---------------------------------------------------------------------------------------------------------
    public void setNumeroDocumentoOrdenCliente(String numeroDocumentoOrdenCliente) {
        if (numeroDocumentoOrdenCliente == null) {
            numeroDocumentoOrdenCliente = "";
        }

        this.numeroDocumentoOrdenCliente = numeroDocumentoOrdenCliente;
    }

    // ---------------------------------------------------------------------------------------------------------
    public void cambiarDatosAlternosDestinatarioRemitente(String canalCodigoAlterno,
            String destinatarioNumeroIdentificacionAlterno, String destinatarioCodigoAlterno,
            String destinatarioNombreAlterno) {

        this.setCanalCodigoAlterno(canalCodigoAlterno);
        this.setDestinatarioNumeroIdentificacionAlterno(destinatarioNumeroIdentificacionAlterno);
        this.setDestinatarioCodigoAlterno(destinatarioCodigoAlterno);
        this.setDestinatarioNombreAlterno(destinatarioNombreAlterno);
    }

    protected void setCanalCodigoAlterno(String value) {
        if (value == null) {
            value = "";
        }
        this.canalCodigoAlterno = value;
    }

    protected void setDestinatarioNumeroIdentificacionAlterno(String value) {
        if (value == null) {
            value = "";
        }
        this.destinatarioNumeroIdentificacionAlterno = value;
    }

    protected void setDestinatarioCodigoAlterno(String value) {
        if (value == null) {
            value = "";
        }
        this.destinatarioCodigoAlterno = value;
    }

    protected void setDestinatarioNombreAlterno(String value) {
        if (value == null) {
            value = "";
        }
        this.destinatarioNombreAlterno = value;
    }

    // ---------------------------------------------------------------------------------------------------------
    public void cambiarDatosAlternosDestino(String destinoCodigoAlterno, String destinoNombreAlterno) {
        this.setDestinoCodigoAlterno(destinoCodigoAlterno);
        this.setDestinoNombreAlterno(destinoNombreAlterno);
    }

    protected void setDestinoCodigoAlterno(String value) {
        if (value == null) {
            value = "";
        }
        this.destinoCodigoAlterno = value;
    }

    protected void setDestinoNombreAlterno(String value) {
        if (value == null) {
            value = "";
        }
        this.destinoNombreAlterno = value;
    }

    // ---------------------------------------------------------------------------------------------------------
    public void cambiarDatosAlternosBodegaDestino(String destinoCodigoAlterno, String destinoNombreAlterno) {
        this.setBodegaDestinoCodigoAlterno(destinoCodigoAlterno);
        this.setBodegaDestinoNombreAlterno(destinoNombreAlterno);
    }

    protected void setBodegaDestinoCodigoAlterno(String value) {
        if (value == null) {
            value = "";
        }
        this.bodegaDestinoCodigoAlterno = value;
    }

    protected void setBodegaDestinoNombreAlterno(String value) {
        if (value == null) {
            value = "";
        }
        this.bodegaDestinoNombreAlterno = value;
    }

    // ---------------------------------------------------------------------------------------------------------
    public void cambiarCitaSugerida(Date fechaMinima, Date fechaMaxima, Time horaMinima, Time horaMaxima) {
        this.setFechaEntregaSugeridaMinima(fechaMinima);
        this.setFechaEntregaSugeridaMaxima(fechaMaxima);
        this.setHoraEntregaSugeridaMinima(horaMinima);
        this.setHoraEntregaSugeridaMaxima(horaMaxima);
    }

    public void cambiarCitaPlanificada(Date fechaMinima, Date fechaMaxima, Time horaMinima, Time horaMaxima) {
        //throw new UnsupportedOperationException("cambiarCitaPlanificada");
    }

    protected IntervaloDeFechas getIntervaloDeFechasSugerido() {
        if (this.intervaloDeFechasSugerido == null) {
            this.intervaloDeFechasSugerido = new IntervaloDeFechas(null,null);
        }
        return intervaloDeFechasSugerido;
    }

    protected IntervaloDeHoras getIntervaloDeHorasSugerido() {
        if (this.intervaloDeHorasSugerido == null) {
            this.intervaloDeHorasSugerido = new IntervaloDeHoras(null,null);
        }
        return intervaloDeHorasSugerido;
    }

    public Date getFechaEntregaSugeridaMinima() {
        if (getIntervaloDeFechasSugerido() == null) {
            return null;
        }
        return getIntervaloDeFechasSugerido().getFechaMinima();
    }

    public Date getFechaEntregaSugeridaMaxima() {
        if (getIntervaloDeFechasSugerido() == null) {
            return null;
        }
        return getIntervaloDeFechasSugerido().getFechaMaxima();
    }

    public Time getHoraEntregaSugeridaMinima() {
        if (getIntervaloDeHorasSugerido() == null) {
            return null;
        }
        return getIntervaloDeHorasSugerido().getHoraMinima();
    }

    public Time getHoraEntregaSugeridaMaxima() {
        if (getIntervaloDeHorasSugerido() == null) {
            return null;
        }
        return getIntervaloDeHorasSugerido().getHoraMaxima();
    }

    protected void setFechaEntregaSugeridaMinima(Date fechaMinima) {
        if (getIntervaloDeFechasSugerido() == null) {
            throw new IllegalStateException("setFechaEntregaSugeridaMinima");
        }
        getIntervaloDeFechasSugerido().setFechaMinima(fechaMinima);
    }

    protected void setFechaEntregaSugeridaMaxima(Date fechaMaxima) {
        if (getIntervaloDeFechasSugerido() == null) {
            throw new IllegalStateException("setFechaEntregaSugeridaMaxima");
        }
        getIntervaloDeFechasSugerido().setFechaMaxima(fechaMaxima);
    }

    protected void setHoraEntregaSugeridaMinima(Time horaMinima) {
        if (getIntervaloDeHorasSugerido() == null) {
            throw new IllegalStateException("setHoraEntregaSugeridaMinima");
        }
        getIntervaloDeHorasSugerido().setHoraMinima(horaMinima);
    }

    protected void setHoraEntregaSugeridaMaxima(Time horaMaxima) {
        if (getIntervaloDeHorasSugerido() == null) {
            throw new IllegalStateException("setHoraEntregaSugeridaMaxima");
        }
        getIntervaloDeHorasSugerido().setHoraMaxima(horaMaxima);
    }

    // ---------------------------------------------------------------------------------------------------------
    public void cambiarFechaEntregaMaximaSegunPromesaServicio(Date fechaEntregaMaximaSegunPromesaServicio) {
        setFechaEntregaMaximaSegunPromesaServicio(fechaEntregaMaximaSegunPromesaServicio);
    }

    protected void setFechaEntregaMaximaSegunPromesaServicio(Date fechaEntregaMaximaSegunPromesaServicio) {
        this.fechaEntregaMaximaSegunPromesaServicio = fechaEntregaMaximaSegunPromesaServicio;
    }

    // ---------------------------------------------------------------------------------------------------------
    public void setValorDeclarado(Integer valorDeclarado) {
        this.valorDeclarado = valorDeclarado;
    }

    public void setRequiereMaquila(boolean requiereMaquila) {
        this.requiereMaquila = requiereMaquila;
    }

    public void setNotas(String value) {
        if (value == null){
            value = "";
        }
        this.notas = value;
    }

    // ---------------------------------------------------------------------------------------------------------
    protected void setEstadoOrden(EstadoOrdenType estadoOrden) {
        this.estadoOrden = estadoOrden;
    }

    protected void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    protected void setUsuarioActualizacion(String usuario) {
        if (usuario == null || "".equals(usuario)) {
            throw new IllegalArgumentException("setUsuarioActualizacion");
        }
        this.usuarioActualizacion = usuario;
    }

    protected void setFechaConfirmacion(Date fechaConfirmacion) {
        this.fechaConfirmacion = fechaConfirmacion;
    }

    protected void setUsuarioConfirmacion(String usuario) {
        if (usuario == null) {
            usuario = "";
        }
        this.usuarioConfirmacion = usuario;
    }

    protected void setFechaAceptacion(Date fechaAceptacion) {
        this.fechaAceptacion = fechaAceptacion;
    }

    protected void setUsuarioAceptacion(String usuario) {
        if (usuario == null) {
            usuario = "";
        }
        this.usuarioAceptacion = usuario;
    }

    // ---------------------------------------------------------------------------------------------------------
    // TODO cambiarEstado(EstadoOrdenType estadoOrden)
    public void cambiarEstado(EstadoOrdenType estadoOrden, String usuarioActualizacion) {
        if (this.getEstadoOrden() == null && this.getId() != null) {
            throw new IllegalStateException("cambiarEstado");
        }

        if (estadoOrden == null) {
            throw new IllegalArgumentException("cambiarEstado");
        }

        if (!estadoOrden.equals(this.getEstadoOrden())) {
            switch (estadoOrden) {
            case NO_CONFIRMADA:
                iniciar(usuarioActualizacion);
                break;
            case CONFIRMADA:
                confirmar(usuarioActualizacion);
                break;
            case ACEPTADA:
                aceptar(usuarioActualizacion);
                break;
            default:
                break;
            }
        }
        modificado(usuarioActualizacion);
    }

    protected void iniciar(String usuarioActualizacion) {
        setEstadoOrden(EstadoOrdenType.NO_CONFIRMADA);
        setFechaConfirmacion(null);
        setUsuarioConfirmacion("");
        setFechaAceptacion(null);
        setUsuarioAceptacion("");
    }

    protected void confirmar(String usuarioActualizacion) {
        setEstadoOrden(EstadoOrdenType.CONFIRMADA);
        setFechaConfirmacion(new Date());
        setUsuarioConfirmacion(usuarioActualizacion);
        setFechaAceptacion(null);
        setUsuarioAceptacion("");
    }

    protected void aceptar(String usuarioActualizacion) {
        setEstadoOrden(EstadoOrdenType.ACEPTADA);
        setFechaAceptacion(new Date());
        setUsuarioAceptacion(usuarioActualizacion);
    }

    public void modificado(String usuarioActualizacion) {
        setFechaActualizacion(new Date());
        setUsuarioActualizacion(usuarioActualizacion);
    }

    // ---------------------------------------------------------------------------------------------------------
    protected Set<LineaOrden> getInternalLineas() {
        return lineas;
    }

    protected void setInternalLineas(Set<LineaOrden> set) {
        if (this.lineas != null) {
            this.lineas.clear();
        }
        this.lineas = set;
    }

    public List<LineaOrden> getLineas() {
        List<LineaOrden> sorted = new ArrayList<>(getInternalLineas());
        PropertyComparator.sort(sorted, new MutableSortDefinition("numeroItem", true, true));
        return Collections.unmodifiableList(sorted);
    }

    public boolean addLinea(LineaOrden e) {
        e.setOrden(this);
        e.setNumeroItem(this.getMaximoNumeroItem() + 1);
        return this.getInternalLineas().add(e);
    }

    public LineaOrden getLinea(Integer id) {
        if (id != null) {
            for (LineaOrden linea : getInternalLineas()) {
                if (id.equals(linea.getId())) {
                    return linea;
                }
            }
        }
        return null;
    }

    public boolean removeLinea(LineaOrden e) {
        //e.setOrden(null);
        boolean rc = this.getInternalLineas().remove(e);
        return rc;
    }

    public int getMaximoNumeroItem() {
        OptionalInt max = this.getInternalLineas().stream().mapToInt(a -> a.getNumeroItem()).max();
        return max.isPresent() ? max.getAsInt() : 0;
    }

    // ---------------------------------------------------------------------------------------------------------
    protected Set<CambioEstadoOrden> getInternalCambios() {
        return cambios;
    }
    
    public void setCambios(Set<CambioEstadoOrden> set) {
        if (this.cambios != null) {
            this.cambios.clear();
        }
        this.cambios = set;
    }

    public List<CambioEstadoOrden> getCambios() {
        List<CambioEstadoOrden> sorted = new ArrayList<>(getInternalCambios());
        PropertyComparator.sort(sorted, new MutableSortDefinition("fechaDesde", true, true));
        return Collections.unmodifiableList(sorted);
    }
    // ---------------------------------------------------------------------------------------------------------
    protected Set<MensajeEmbeddable> getInternalMensajes() {
        return mensajes;
    }

    public List<MensajeEmbeddable> getMensajes() {
        List<MensajeEmbeddable> sorted = new ArrayList<>(getInternalMensajes());
        PropertyComparator.sort(sorted, new MutableSortDefinition("severidad", true, true));
        return Collections.unmodifiableList(sorted);
    }

    public void setMensajes(Set<MensajeEmbeddable> set) {
        if (this.mensajes != null) {
            this.mensajes.clear();
        }
        this.mensajes = set;
    }


    // ---------------------------------------------------------------------------------------------------------
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
        result = prime * result + ((numeroDocumentoOrdenCliente == null) ? 0 : numeroDocumentoOrdenCliente.hashCode());
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
        Orden other = (Orden) obj;
        if (cliente == null) {
            if (other.cliente != null)
                return false;
        } else if (!cliente.equals(other.cliente))
            return false;
        if (numeroDocumentoOrdenCliente == null) {
            if (other.numeroDocumentoOrdenCliente != null)
                return false;
        } else if (!numeroDocumentoOrdenCliente.equals(other.numeroDocumentoOrdenCliente))
            return false;
        return true;
    }

    @Override
    public String toString() {
        final int maxLen = 5;
        StringBuilder builder = new StringBuilder();
        builder.append("Orden [");
        if (id != null)
            builder.append("id=").append(id).append(", ");
        if (tipoServicio != null)
            builder.append("tipoServicio=").append(tipoServicio).append(", ");
        if (cliente != null)
            builder.append("cliente=").append(cliente).append(", ");
        if (numeroDocumentoOrdenCliente != null)
            builder.append("numeroDocumentoOrdenCliente=").append(numeroDocumentoOrdenCliente).append(", ");
        if (consolidado != null)
            builder.append("consolidado=").append(consolidado).append(", ");
        if (estadoOrden != null)
            builder.append("estadoOrden=").append(estadoOrden).append(", ");
        if (causalAnulacion != null)
            builder.append("causalAnulacion=").append(causalAnulacion).append(", ");
        if (canal != null)
            builder.append("canal=").append(canal).append(", ");
        if (destinatario != null)
            builder.append("destinatario=").append(destinatario).append(", ");
        if (destino != null)
            builder.append("destino=").append(destino).append(", ");
        if (bodegaDestino != null)
            builder.append("bodegaDestino=").append(bodegaDestino).append(", ");
        if (destinoDireccion != null)
            builder.append("destinoDireccion=").append(destinoDireccion).append(", ");
        if (intervaloDeFechasSugerido != null)
            builder.append("intervaloDeFechasSugerido=").append(intervaloDeFechasSugerido).append(", ");
        if (fechaEntregaMaximaSegunPromesaServicio != null)
            builder.append("fechaEntregaMaximaSegunPromesaServicio=").append(fechaEntregaMaximaSegunPromesaServicio)
                    .append(", ");
        if (intervaloDeHorasSugerido != null)
            builder.append("intervaloDeHorasSugerido=").append(intervaloDeHorasSugerido).append(", ");
        if (notas != null)
            builder.append("nota=").append(notas).append(", ");
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
        if (lineas != null)
            builder.append("lineas=").append(toString(lineas, maxLen));
        builder.append("]");
        return builder.toString();
    }

    private String toString(Collection<?> collection, int maxLen) {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        int i = 0;
        for (Iterator<?> iterator = collection.iterator(); iterator.hasNext() && i < maxLen; i++) {
            if (i > 0)
                builder.append(", ");
            builder.append(iterator.next());
        }
        builder.append("]");
        return builder.toString();
    }

    // protected void setConsolidado(Consolidado consolidado) {
    // this.consolidado = consolidado;
    // }
    //
    // protected void setCausalAnulacion(CausalAnulacionOrden causalAnulacion) {
    // this.causalAnulacion = causalAnulacion;
    // }
    //
}
