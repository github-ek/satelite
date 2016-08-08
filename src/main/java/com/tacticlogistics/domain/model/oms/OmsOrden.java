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
import com.tacticlogistics.domain.model.common.valueobjects.MensajeEmbeddable;
import com.tacticlogistics.domain.model.crm.Canal;
import com.tacticlogistics.domain.model.crm.Cliente;
import com.tacticlogistics.domain.model.crm.DestinatarioRemitente;
import com.tacticlogistics.domain.model.crm.DestinoOrigen;
import com.tacticlogistics.domain.model.crm.TipoServicio;
import com.tacticlogistics.domain.model.geo.Ciudad;
import com.tacticlogistics.domain.model.ordenes.LineaOrden;
import com.tacticlogistics.domain.model.tms.TipoVehiculo;

@Entity
@Table(name = "Ordenes", catalog = "oms")

// TODO DOCUMENTOS
// TODO REQUERIMIENTOS DISTRIBUCION POR LINEA
// TODO REQUERIMIENTOS ALISTAMIENTO POR LINEA
// TODO COPIA ORIGINAL DE LAS LINEAS
public class OmsOrden implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_orden", unique = true, nullable = false)
	@NotNull
	private Integer id;

	@Column(nullable = false, length = 20)
	@NotNull
	private String numeroOrden;

	@Column(nullable = false, length = 20)
	@NotNull
	private String numeroConsolidado;

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

	@Column(nullable = true, columnDefinition = "DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaEntregaSugeridaMinima;

	@Column(nullable = true, columnDefinition = "DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaEntregaSugeridaMaxima;

	@Column(nullable = true, columnDefinition = "TIME(0)")
	private Time horaEntregaSugeridaMinima;

	@Column(nullable = true, columnDefinition = "TIME(0)")
	private Time horaEntregaSugeridaMaxima;

	@Column(nullable = true, columnDefinition = "TIME(0)")
	private Time horaEntregaSugeridaMinimaAdicional;

	@Column(nullable = true, columnDefinition = "TIME(0)")
	private Time horaEntregaSugeridaMaximaAdicional;

	// ---------------------------------------------------------------------------------------------------------
	private boolean requiereConfirmacionCitaRecogida;

	@Column(nullable = true, columnDefinition = "DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaRecogidaSugeridaMinima;

	@Column(nullable = true, columnDefinition = "DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaRecogidaSugeridaMaxima;

	@Column(nullable = true, columnDefinition = "TIME(0)")
	private Time horaRecogidaSugeridaMinima;

	@Column(nullable = true, columnDefinition = "TIME(0)")
	private Time horaRecogidaSugeridaMaxima;

	@Column(nullable = true, columnDefinition = "TIME(0)")
	private Time horaRecogidaSugeridaMinimaAdicional;

	@Column(nullable = true, columnDefinition = "TIME(0)")
	private Time horaRecogidaSugeridaMaximaAdicional;

	// ---------------------------------------------------------------------------------------------------------
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "id_canal", nullable = true)
	private Canal canal;

	@Column(nullable = false, length = 50)
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
	@Column(nullable = true, columnDefinition = "DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaEntregaPlanificadaMinima;

	@Column(nullable = true, columnDefinition = "DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaEntregaPlanificadaMaxima;

	@Column(nullable = true, columnDefinition = "TIME(0)")
	private Time horaEntregaPlanificadaMinima;

	@Column(nullable = true, columnDefinition = "TIME(0)")
	private Time horaEntregaPlanificadaMaxima;

	@Column(nullable = true, columnDefinition = "TIME(0)")
	private Time horaEntregaPlanificadaMinimaAdicional;

	@Column(nullable = true, columnDefinition = "TIME(0)")
	private Time horaEntregaPlanificadaMaximaAdicional;

	// ---------------------------------------------------------------------------------------------------------
	@Column(nullable = true, columnDefinition = "DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaRecogidaPlanificadaMinima;

	@Column(nullable = true, columnDefinition = "DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaRecogidaPlanificadaMaxima;

	@Column(nullable = true, columnDefinition = "TIME(0)")
	private Time horaRecogidaPlanificadaMinima;

	@Column(nullable = true, columnDefinition = "TIME(0)")
	private Time horaRecogidaPlanificadaMaxima;

	@Column(nullable = true, columnDefinition = "TIME(0)")
	private Time horaRecogidaPlanificadaMinimaAdicional;

	@Column(nullable = true, columnDefinition = "TIME(0)")
	private Time horaRecogidaPlanificadaMaximaAdicional;

	// ---------------------------------------------------------------------------------------------------------
	@Column(nullable = true, columnDefinition = "DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaAlistamientoPlanificada;

	@Column(nullable = true, columnDefinition = "TIME(0)")
	private Time horaAlistamientoPlanificadaMinima;

	@Column(nullable = true, columnDefinition = "TIME(0)")
	private Time horaAlistamientoPlanificadaMaxima;

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
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "orden", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OmsLineaOrden> lineas;

	@ElementCollection
	@CollectionTable(name = "ordenes_requerimientos_distribucion", catalog = "oms", joinColumns = @JoinColumn(name = "id_orden", referencedColumnName = "id_orden"))
	private Set<OmsOrdenRequerimientoDistribucionAssociation> requerimientosDistribucion;

	@ElementCollection
	@CollectionTable(name = "ordenes_requerimientos_alistamiento", catalog = "oms", joinColumns = @JoinColumn(name = "id_orden", referencedColumnName = "id_orden"))
	private Set<OmsOrdenRequerimientoAlistamientoAssociation> requerimientosAlistamiento;

	@ElementCollection
	@CollectionTable(name = "ordenes_mensajes", catalog = "oms", joinColumns = @JoinColumn(name = "id_orden", referencedColumnName = "id_orden"))
	private Set<MensajeEmbeddable> mensajes;

	// ---------------------------------------------------------------------------------------------------------
	public OmsOrden() {
		super();
		this.setId(null);
		this.setNumeroOrden("");
		this.setNumeroConsolidado("");
		this.setEstadoOrden(EstadoOrdenType.NO_CONFIRMADA);
		this.setEstadoDistribucion(EstadoDistribucionType.NO_PROGRAMADO);
		this.setEstadoAlistamiento(EstadoAlistamientoType.NO_PROGRAMADO);
		this.setCliente(null);

		this.setTipoServicio(null);
		this.setTipoServicioCodigoAlterno("");
		this.setRequiereServicioDistribucion(true);
		this.setRequiereServicioAlistamiento(true);

		this.setDireccionDestino();

		this.setDireccionOrigen();

		this.setRequiereConfirmacionCitaEntrega(false);
		this.setCitaEntregaSugerida();

		this.setRequiereConfirmacionCitaRecogida(false);
		this.setCitaRecogidaSugerida();

		this.setDestinatario();
		this.setUbicacionDestino();
		this.setUbicacionOrigen();

		this.setNotasRequerimientosDistribucion("");
		this.setNotasRequerimientosAlistamiento("");
		this.setValorRecaudo(null);

		this.setDatosConfirmacion();

		this.setCitaEntregaPlanificada();
		this.setCitaRecogidaPlanificada();
		this.setCitaAlistamientoPlanificada();

		this.setDatosPlanificacionTipoVehiculo();

		this.setDatosAprobacionPlanificacion();

		this.setDestinoDireccionGeoreferenciada(null);

		this.setOrigenDireccionGeoreferenciada(null);

		this.setEstadoPlanificacionRuta(EstadoPlanificacionRutaType.SIN_SOLICITUD);
		setDatosCortePlanificacionRuta();

		this.setDatosRuta();

		this.setDatosCreacion();

		this.setDatosActualizacion();

		this.setDatosAnulacion();

		this.lineas = new HashSet<>();
		this.mensajes = new HashSet<>();
		this.requerimientosDistribucion = new HashSet<>();
		this.requerimientosAlistamiento = new HashSet<>();
	}

	public void setDireccionDestino() {
		this.setCiudadDestino(null);
		this.setDestinoCiudadNombreAlterno("");
		this.setDestinoDireccion("");
		this.setDestinoIndicaciones("");
	}

	public void setDireccionOrigen() {
		this.setCiudadOrigen(null);
		this.setOrigenCiudadNombreAlterno("");
		this.setOrigenDireccion("");
		this.setOrigenIndicaciones("");
	}

	public void setDestinatario() {
		this.setCanal(null);
		this.setCanalCodigoAlterno("");
		this.setDestinatario(null);
		this.setDestinatarioNumeroIdentificacion("");
		this.setDestinatarioNombre("");
		this.setDestinatarioContacto(null);
	}

	public void setUbicacionDestino() {
		this.setDestino(null);
		this.setDestinoNombre("");
		this.setDestinoContacto(null);
	}

	public void setUbicacionOrigen() {
		this.setOrigen(null);
		this.setOrigenNombre("");
		this.setOrigenContacto(null);
	}

	public void setCitaEntregaSugerida() {
		this.setFechaEntregaSugeridaMinima(null);
		this.setFechaEntregaSugeridaMaxima(null);
		this.setHoraEntregaSugeridaMinima(null);
		this.setHoraEntregaSugeridaMaxima(null);
		this.setHoraEntregaSugeridaMinimaAdicional(null);
		this.setHoraEntregaSugeridaMaximaAdicional(null);
	}

	public void setCitaRecogidaSugerida() {
		this.setFechaRecogidaSugeridaMinima(null);
		this.setFechaRecogidaSugeridaMaxima(null);
		this.setHoraRecogidaSugeridaMinima(null);
		this.setHoraRecogidaSugeridaMaxima(null);
		this.setHoraRecogidaSugeridaMinimaAdicional(null);
		this.setHoraRecogidaSugeridaMaximaAdicional(null);
	}

	public void setCitaEntregaPlanificada() {
		this.setFechaEntregaPlanificadaMinima(null);
		this.setFechaEntregaPlanificadaMaxima(null);
		this.setHoraEntregaPlanificadaMinima(null);
		this.setHoraEntregaPlanificadaMaxima(null);
		this.setHoraEntregaPlanificadaMinimaAdicional(null);
		this.setHoraEntregaPlanificadaMaximaAdicional(null);
	}

	public void setCitaRecogidaPlanificada() {
		this.setFechaRecogidaPlanificadaMinima(null);
		this.setFechaRecogidaPlanificadaMaxima(null);
		this.setHoraRecogidaPlanificadaMinima(null);
		this.setHoraRecogidaPlanificadaMaxima(null);
		this.setHoraRecogidaPlanificadaMinimaAdicional(null);
		this.setHoraRecogidaPlanificadaMaximaAdicional(null);
	}

	public void setCitaAlistamientoPlanificada() {
		this.setFechaAlistamientoPlanificada(null);
		this.setHoraAlistamientoPlanificadaMinima(null);
		this.setHoraAlistamientoPlanificadaMaxima(null);
	}

	public void setDatosPlanificacionTipoVehiculo() {
		this.setTipoVehiculoPlanificado(null);
		this.setValorFletePlanificado(null);
	}

	public void setDatosAprobacionPlanificacion() {
		this.setNotasAprobacionPlanificacion("");
		this.setFechaAprobacionPlanificacion(null);
		this.setUsuarioAprobacionPlanificacion("");
	}

	public void setDatosCortePlanificacionRuta() {
		this.setCortePlanificacionRutaId(null);
		this.setFechaCortePlanificacionRuta(null);
		this.setUsuarioCortePlanificacionRuta("");
	}

	public void setDatosRuta() {
		this.setRutaId(null);
		this.setFechaAsignacionRuta(null);
		this.setUsuarioAsignacionRuta("");
	}

	public void setDatosConfirmacion() {
		this.setNotasConfirmacion("");
		this.setFechaConfirmacion(null);
		this.setUsuarioConfirmacion("");
	}

	public void setDatosCreacion() {
		this.setFechaCreacion(null);
		this.setUsuarioCreacion("");
	}

	public void setDatosActualizacion() {
		this.setFechaActualizacion(null);
		this.setUsuarioActualizacion("");
	}

	public void setDatosAnulacion() {
		this.setNotasAnulacion("");
		this.setCausalAnulacionId(null);
		this.setUsuarioAnulacion("");
		this.setFechaAnulacion(null);
	}

	// ---------------------------------------------------------------------------------------------------------
	public Integer getId() {
		return id;
	}

	public String getNumeroOrden() {
		return numeroOrden;
	}

	public String getNumeroConsolidado() {
		return numeroConsolidado;
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

	public String getTipoServicioCodigoAlterno() {
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

	public Date getFechaEntregaSugeridaMinima() {
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

	// ---------------------------------------------------------------------------------------------------------
	public boolean isRequiereConfirmacionCitaRecogida() {
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

	public Time getHoraRecogidaSugeridaMinimaAdicional() {
		return horaRecogidaSugeridaMinimaAdicional;
	}

	public Time getHoraRecogidaSugeridaMaximaAdicional() {
		return horaRecogidaSugeridaMaximaAdicional;
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
			// TODO
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
			// TODO
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
		if (this.origenContacto == null) {
			// TODO
		}
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
	public Date getFechaEntregaPlanificadaMinima() {
		return fechaEntregaPlanificadaMinima;
	}

	public Date getFechaEntregaPlanificadaMaxima() {
		return fechaEntregaPlanificadaMaxima;
	}

	public Time getHoraEntregaPlanificadaMinima() {
		return horaEntregaPlanificadaMinima;
	}

	public Time getHoraEntregaPlanificadaMaxima() {
		return horaEntregaPlanificadaMaxima;
	}

	public Time getHoraEntregaPlanificadaMinimaAdicional() {
		return horaEntregaPlanificadaMinimaAdicional;
	}

	public Time getHoraEntregaPlanificadaMaximaAdicional() {
		return horaEntregaPlanificadaMaximaAdicional;
	}

	// ---------------------------------------------------------------------------------------------------------
	public Date getFechaRecogidaPlanificadaMinima() {
		return fechaRecogidaPlanificadaMinima;
	}

	public Date getFechaRecogidaPlanificadaMaxima() {
		return fechaRecogidaPlanificadaMaxima;
	}

	public Time getHoraRecogidaPlanificadaMinima() {
		return horaRecogidaPlanificadaMinima;
	}

	public Time getHoraRecogidaPlanificadaMaxima() {
		return horaRecogidaPlanificadaMaxima;
	}

	public Time getHoraRecogidaPlanificadaMinimaAdicional() {
		return horaRecogidaPlanificadaMinimaAdicional;
	}

	public Time getHoraRecogidaPlanificadaMaximaAdicional() {
		return horaRecogidaPlanificadaMaximaAdicional;
	}

	// ---------------------------------------------------------------------------------------------------------
	public Date getFechaAlistamientoPlanificada() {
		return fechaAlistamientoPlanificada;
	}

	public Time getHoraAlistamientoPlanificadaMinima() {
		return horaAlistamientoPlanificadaMinima;
	}

	public Time getHoraAlistamientoPlanificadaMaxima() {
		return horaAlistamientoPlanificadaMaxima;
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
			// TODO
		}
		return destinoDireccionGeoreferenciada;
	}

	public OmsDireccionGeoreferenciada getOrigenDireccionGeoreferenciada() {
		if (origenDireccionGeoreferenciada == null) {
			// TODO
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

	public Date getFechaCortePlanificacionRuta() {
		return fechaCortePlanificacionRuta;
	}

	public String getUsuarioCortePlanificacionRuta() {
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
	public void setId(Integer id) {
		this.id = id;
	}

	public void setNumeroOrden(String value) {
		this.numeroOrden = coalesce(value, "");
	}

	public void setNumeroConsolidado(String value) {
		this.numeroConsolidado = coalesce(value, "");
	}

	// ---------------------------------------------------------------------------------------------------------
	public void setEstadoOrden(EstadoOrdenType estadoOrden) {
		this.estadoOrden = estadoOrden;
	}

	public void setEstadoDistribucion(EstadoDistribucionType estadoDistribucion) {
		this.estadoDistribucion = estadoDistribucion;
	}

	public void setEstadoAlistamiento(EstadoAlistamientoType estadoAlistamiento) {
		this.estadoAlistamiento = estadoAlistamiento;
	}

	// ---------------------------------------------------------------------------------------------------------
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
		this.setClienteCodigo((this.cliente != null) ? this.cliente.getCodigo() : "");
	}

	public void setClienteCodigo(String value) {
		this.clienteCodigo = coalesce(value, "");
	}

	// ---------------------------------------------------------------------------------------------------------
	public void setTipoServicio(TipoServicio tipoServicio) {
		this.tipoServicio = tipoServicio;
	}

	public void setTipoServicioCodigoAlterno(String value) {
		this.tipoServicioCodigoAlterno = coalesce(value, "");
	}

	public void setRequiereServicioDistribucion(boolean requiereServicioDistribucion) {
		this.requiereServicioDistribucion = requiereServicioDistribucion;
	}

	public void setRequiereServicioAlistamiento(boolean requiereServicioAlistamiento) {
		this.requiereServicioAlistamiento = requiereServicioAlistamiento;
	}

	// ---------------------------------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------------------------------
	public void setCiudadDestino(Ciudad ciudadDestino) {
		this.ciudadDestino = ciudadDestino;
	}

	public void setDestinoCiudadNombreAlterno(String value) {
		this.destinoCiudadNombreAlterno = coalesce(value, "");
	}

	public void setDestinoDireccion(String value) {
		this.destinoDireccion = coalesce(value, "");
	}

	public void setDestinoIndicaciones(String value) {
		this.destinoIndicaciones = coalesce(value, "");
	}

	// ---------------------------------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------------------------------
	public void setCiudadOrigen(Ciudad ciudadOrigen) {
		this.ciudadOrigen = ciudadOrigen;
	}

	public void setOrigenCiudadNombreAlterno(String value) {
		this.origenCiudadNombreAlterno = coalesce(value, "");
	}

	public void setOrigenDireccion(String value) {
		this.origenDireccion = coalesce(value, "");
	}

	public void setOrigenIndicaciones(String value) {
		this.origenIndicaciones = coalesce(value, "");
	}

	// ---------------------------------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------------------------------
	public void setRequiereConfirmacionCitaEntrega(boolean requiereConfirmacionCitaEntrega) {
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

	// ---------------------------------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------------------------------
	public void setRequiereConfirmacionCitaRecogida(boolean requiereConfirmacionCitaRecogida) {
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

	public void setHoraRecogidaSugeridaMinimaAdicional(Time horaRecogidaSugeridaMinimaAdicional) {
		this.horaRecogidaSugeridaMinimaAdicional = horaRecogidaSugeridaMinimaAdicional;
	}

	public void setHoraRecogidaSugeridaMaximaAdicional(Time horaRecogidaSugeridaMaximaAdicional) {
		this.horaRecogidaSugeridaMaximaAdicional = horaRecogidaSugeridaMaximaAdicional;
	}

	// ---------------------------------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------------------------------
	public void setCanal(Canal canal) {
		this.canal = canal;
	}

	public void setCanalCodigoAlterno(String value) {
		this.canalCodigoAlterno = coalesce(value, "");
	}

	// ---------------------------------------------------------------------------------------------------------

	public void setDestinatario(DestinatarioRemitente destinatario) {
		this.destinatario = destinatario;
	}

	public void setDestinatarioNumeroIdentificacion(String value) {
		this.destinatarioNumeroIdentificacion = coalesce(value, "");
	}

	public void setDestinatarioNombre(String value) {
		this.destinatarioNombre = coalesce(value, "");
	}

	public void setDestinatarioContacto(Contacto value) {
		this.destinatarioContacto = coalesce(value, new Contacto("", "", ""));
	}

	// ---------------------------------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------------------------------
	public void setDestino(DestinoOrigen destino) {
		this.destino = destino;
	}

	public void setDestinoNombre(String value) {
		this.destinoNombre = coalesce(value, "");
	}

	public void setDestinoContacto(Contacto value) {
		this.destinoContacto = coalesce(value, new Contacto("", "", ""));
	}

	// ---------------------------------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------------------------------
	public void setOrigen(DestinoOrigen origen) {
		this.origen = origen;
	}

	public void setOrigenNombre(String value) {
		this.origenNombre = coalesce(value, "");
	}

	public void setOrigenContacto(Contacto value) {
		this.origenContacto = coalesce(value, new Contacto("", "", ""));
	}

	// ---------------------------------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------------------------------
	public void setNotasRequerimientosDistribucion(String value) {
		this.notasRequerimientosDistribucion = coalesce(value, "");
	}

	public void setNotasRequerimientosAlistamiento(String value) {
		this.notasRequerimientosAlistamiento = coalesce(value, "");
	}

	public void setValorRecaudo(Integer valorRecaudo) {
		this.valorRecaudo = valorRecaudo;
	}

	// ---------------------------------------------------------------------------------------------------------

	// ---------------------------------------------------------------------------------------------------------
	public void setNotasConfirmacion(String value) {
		this.notasConfirmacion = coalesce(value, "");
	}

	public void setFechaConfirmacion(Date fechaConfirmacion) {
		this.fechaConfirmacion = fechaConfirmacion;
	}

	public void setUsuarioConfirmacion(String value) {
		this.usuarioConfirmacion = coalesce(value, "");
	}

	// ---------------------------------------------------------------------------------------------------------
	public void setFechaEntregaPlanificadaMinima(Date fechaEntregaPlanificadaMinima) {
		this.fechaEntregaPlanificadaMinima = fechaEntregaPlanificadaMinima;
	}

	public void setFechaEntregaPlanificadaMaxima(Date fechaEntregaPlanificadaMaxima) {
		this.fechaEntregaPlanificadaMaxima = fechaEntregaPlanificadaMaxima;
	}

	public void setHoraEntregaPlanificadaMinima(Time horaEntregaPlanificadaMinima) {
		this.horaEntregaPlanificadaMinima = horaEntregaPlanificadaMinima;
	}

	public void setHoraEntregaPlanificadaMaxima(Time horaEntregaPlanificadaMaxima) {
		this.horaEntregaPlanificadaMaxima = horaEntregaPlanificadaMaxima;
	}

	public void setHoraEntregaPlanificadaMinimaAdicional(Time horaEntregaPlanificadaMinimaAdicional) {
		this.horaEntregaPlanificadaMinimaAdicional = horaEntregaPlanificadaMinimaAdicional;
	}

	public void setHoraEntregaPlanificadaMaximaAdicional(Time horaEntregaPlanificadaMaximaAdicional) {
		this.horaEntregaPlanificadaMaximaAdicional = horaEntregaPlanificadaMaximaAdicional;
	}

	// ---------------------------------------------------------------------------------------------------------
	public void setFechaRecogidaPlanificadaMinima(Date fechaRecogidaPlanificadaMinima) {
		this.fechaRecogidaPlanificadaMinima = fechaRecogidaPlanificadaMinima;
	}

	public void setFechaRecogidaPlanificadaMaxima(Date fechaRecogidaPlanificadaMaxima) {
		this.fechaRecogidaPlanificadaMaxima = fechaRecogidaPlanificadaMaxima;
	}

	public void setHoraRecogidaPlanificadaMinima(Time horaRecogidaPlanificadaMinima) {
		this.horaRecogidaPlanificadaMinima = horaRecogidaPlanificadaMinima;
	}

	public void setHoraRecogidaPlanificadaMaxima(Time horaRecogidaPlanificadaMaxima) {
		this.horaRecogidaPlanificadaMaxima = horaRecogidaPlanificadaMaxima;
	}

	public void setHoraRecogidaPlanificadaMinimaAdicional(Time horaRecogidaPlanificadaMinimaAdicional) {
		this.horaRecogidaPlanificadaMinimaAdicional = horaRecogidaPlanificadaMinimaAdicional;
	}

	public void setHoraRecogidaPlanificadaMaximaAdicional(Time horaRecogidaPlanificadaMaximaAdicional) {
		this.horaRecogidaPlanificadaMaximaAdicional = horaRecogidaPlanificadaMaximaAdicional;
	}

	// ---------------------------------------------------------------------------------------------------------

	public void setFechaAlistamientoPlanificada(Date fechaAlistamientoPlanificada) {
		this.fechaAlistamientoPlanificada = fechaAlistamientoPlanificada;
	}

	public void setHoraAlistamientoPlanificadaMinima(Time horaAlistamientoPlanificadaMinima) {
		this.horaAlistamientoPlanificadaMinima = horaAlistamientoPlanificadaMinima;
	}

	public void setHoraAlistamientoPlanificadaMaxima(Time horaAlistamientoPlanificadaMaxima) {
		this.horaAlistamientoPlanificadaMaxima = horaAlistamientoPlanificadaMaxima;
	}

	// ---------------------------------------------------------------------------------------------------------
	public void setTipoVehiculoPlanificado(TipoVehiculo tipoVehiculoPlanificado) {
		this.tipoVehiculoPlanificado = tipoVehiculoPlanificado;
	}

	public void setValorFletePlanificado(Integer valorFletePlanificado) {
		this.valorFletePlanificado = valorFletePlanificado;
	}

	// ---------------------------------------------------------------------------------------------------------
	public void setNotasAprobacionPlanificacion(String value) {
		this.notasAprobacionPlanificacion = coalesce(value, "");
	}

	public void setFechaAprobacionPlanificacion(Date fechaAprobacionPlanificacion) {
		this.fechaAprobacionPlanificacion = fechaAprobacionPlanificacion;
	}

	public void setUsuarioAprobacionPlanificacion(String value) {
		this.usuarioAprobacionPlanificacion = coalesce(value, "");
	}

	// ---------------------------------------------------------------------------------------------------------
	public void setDestinoDireccionGeoreferenciada(OmsDireccionGeoreferenciada value) {
		this.destinoDireccionGeoreferenciada = coalesce(value, new OmsDireccionGeoreferenciada());
	}

	public void setOrigenDireccionGeoreferenciada(OmsDireccionGeoreferenciada value) {
		this.origenDireccionGeoreferenciada = coalesce(value, new OmsDireccionGeoreferenciada());
	}

	// ---------------------------------------------------------------------------------------------------------
	public void setEstadoPlanificacionRuta(EstadoPlanificacionRutaType estadoPlanificacionRuta) {
		this.estadoPlanificacionRuta = estadoPlanificacionRuta;
	}

	public void setCortePlanificacionRutaId(Integer cortePlanificacionRutaId) {
		this.cortePlanificacionRutaId = cortePlanificacionRutaId;
	}

	public void setFechaCortePlanificacionRuta(Date fechaPlanificacionRuta) {
		this.fechaCortePlanificacionRuta = fechaPlanificacionRuta;
	}

	public void setUsuarioCortePlanificacionRuta(String value) {
		this.usuarioCortePlanificacionRuta = coalesce(value, "");
	}

	// ---------------------------------------------------------------------------------------------------------
	public void setRutaId(Integer rutaId) {
		this.rutaId = rutaId;
	}

	public void setFechaAsignacionRuta(Date fechaAsignacionRuta) {
		this.fechaAsignacionRuta = fechaAsignacionRuta;
	}

	public void setUsuarioAsignacionRuta(String value) {
		this.usuarioAsignacionRuta = coalesce(value, "");
	}

	// ---------------------------------------------------------------------------------------------------------
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public void setUsuarioCreacion(String value) {
		this.usuarioCreacion = coalesce(value, "");
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public void setUsuarioActualizacion(String value) {
		this.usuarioActualizacion = coalesce(value, "");
	}

	// ---------------------------------------------------------------------------------------------------------
	public void setNotasAnulacion(String value) {
		this.notasAnulacion = coalesce(value, "");
	}

	public void setCausalAnulacionId(Integer causalAnulacionId) {
		this.causalAnulacionId = causalAnulacionId;
	}

	public void setFechaAnulacion(Date fechaAnulacion) {
		this.fechaAnulacion = fechaAnulacion;
	}

	public void setUsuarioAnulacion(String value) {
		this.usuarioAnulacion = coalesce(value, "");
	}

	// ---------------------------------------------------------------------------------------------------------
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

	public void setRequerimientosDistribucion(
			Set<OmsOrdenRequerimientoDistribucionAssociation> requerimientosDistribucion) {
		this.requerimientosDistribucion = requerimientosDistribucion;
	}

	public void setRequerimientosAlistamiento(
			Set<OmsOrdenRequerimientoAlistamientoAssociation> requerimientosAlistamiento) {
		this.requerimientosAlistamiento = requerimientosAlistamiento;
	}

	public void setMensajes(Set<MensajeEmbeddable> mensajes) {
		this.mensajes = mensajes;
	}

	// ---------------------------------------------------------------------------------------------------------
	public List<OmsLineaOrden> getLineas() {
		List<OmsLineaOrden> sorted = new ArrayList<>(getInternalLineas());
		PropertyComparator.sort(sorted, new MutableSortDefinition("numeroItem", true, true));
		return Collections.unmodifiableList(sorted);
	}

	public boolean addLinea(OmsLineaOrden e) {
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

	// ---------------------------------------------------------------------------------------------------------
	public Set<OmsOrdenRequerimientoDistribucionAssociation> getRequerimientosDistribucion() {
		return requerimientosDistribucion;
	}

	// ---------------------------------------------------------------------------------------------------------
	public Set<OmsOrdenRequerimientoAlistamientoAssociation> getRequerimientosAlistamiento() {
		return requerimientosAlistamiento;
	}

	// ---------------------------------------------------------------------------------------------------------
	public Set<MensajeEmbeddable> getMensajes() {
		return mensajes;
	}
}
