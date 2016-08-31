package com.tacticlogistics.domain.model.ordenes;

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
import javax.validation.constraints.NotNull;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;

import com.tacticlogistics.domain.model.common.valueobjects.Contacto;
import com.tacticlogistics.domain.model.common.valueobjects.Direccion;
import com.tacticlogistics.domain.model.common.valueobjects.MensajeEmbeddable;
import com.tacticlogistics.domain.model.crm.Canal;
import com.tacticlogistics.domain.model.crm.Cliente;
import com.tacticlogistics.domain.model.crm.DestinatarioRemitente;
import com.tacticlogistics.domain.model.crm.DestinoOrigen;
import com.tacticlogistics.domain.model.crm.TipoServicio;
import com.tacticlogistics.domain.model.geo.Ciudad;
import com.tacticlogistics.domain.model.oms.EstadoAlistamientoType;
import com.tacticlogistics.domain.model.oms.EstadoCumplidosType;
import com.tacticlogistics.domain.model.oms.EstadoDistribucionType;
import com.tacticlogistics.domain.model.oms.EstadoOrdenType;
import com.tacticlogistics.domain.model.tms.TipoVehiculo;

@Entity
@Table(name = "Ordenes", catalog = "ordenes")

public class Orden implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_orden", unique = true, nullable = false)
	private Integer id;

	@Column(nullable = false, length = 20, name = "numero_documento_orden_cliente")
	@NotNull
	private String numeroOrden;

	@Column(nullable = true, columnDefinition = "DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaOrden;

	@Column(nullable = false, length = 20)
	@NotNull
	private String numeroOrdenCompra;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "id_consolidado", nullable = true)
	private Consolidado consolidado;

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

	@Enumerated(EnumType.STRING)
	@Column(name = "id_estado_cumplidos", nullable = false, length = 50)
	@NotNull
	private EstadoCumplidosType estadoCumplidos;

	// ---------------------------------------------------------------------------------------------------------
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_cliente", nullable = false)
	@NotNull
	private Cliente cliente;

	@Column(name = "cliente_codigo", nullable = false, length = 20)
	@NotNull
	private String clienteCodigo;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_tipo_servicio", nullable = false)
	@NotNull
	private TipoServicio tipoServicio;

	@Column(nullable = false, length = 50)
	@NotNull
	private String tipoServicioCodigoAlterno;

	private boolean requiereServicioDistribucion;

	// ---------------------------------------------------------------------------------------------------------
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

	@Column(nullable = false, length = 100, name = "destino_ciudad_nombre_alterno")
	@NotNull
	private String destinoCiudadNombre;

	// ---------------------------------------------------------------------------------------------------------
	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = "direccion", column = @Column(name = "origen_direccion", nullable = true, length = 150)),
			@AttributeOverride(name = "indicacionesDireccion", column = @Column(name = "origen_indicaciones_direccion", nullable = true, length = 150)),
			@AttributeOverride(name = "longitud", column = @Column(name = "origen_longitud", nullable = true, precision = 18, scale = 15)),
			@AttributeOverride(name = "latitud", column = @Column(name = "origen_latitud", nullable = true, precision = 18, scale = 15)),
			@AttributeOverride(name = "direccionEstandarizada", column = @Column(name = "origen_direccion_estandarizada", nullable = true, length = 150)) })
	@AssociationOverrides({
			@AssociationOverride(name = "ciudad", joinColumns = @JoinColumn(name = "id_ciudad_origen", nullable = true)) })
	private Direccion origenDireccion;

	@Column(nullable = false, length = 100, name = "origen_ciudad_nombre_alterno")
	@NotNull
	private String origenCiudadNombre;

	// ---------------------------------------------------------------------------------------------------------
	@Column(name = "requiere_confirmacion_cita")
	@NotNull
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

	// ---------------------------------------------------------------------------------------------------------
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "id_segmento", nullable = true)
	private Canal canal;

	@Column(name = "segmento_codigo_alterno", length = 50, nullable = false)
	@NotNull
	private String canalCodigoAlterno;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "id_destinatario_remitente_destinatario", nullable = true)
	private DestinatarioRemitente destinatario;

	@Column(nullable = false, length = 20, name = "destinatario_numeroIdentificacion_alterno")
	@NotNull
	private String destinatarioNumeroIdentificacion;

	@Column(nullable = false, length = 100, name = "destinatario_nombre_alterno")
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
	@JoinColumn(name = "id_destino_origen_destino", nullable = true)
	private DestinoOrigen destino;

	@Column(nullable = false, length = 100, name = "destino_nombre_alterno")
	@NotNull
	private String destinoNombre;

	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = "nombres", column = @Column(name = "destino_contacto_nombres", nullable = true, length = 100)),
			@AttributeOverride(name = "telefonos", column = @Column(name = "destino_contacto_telefonos", nullable = true, length = 50)),
			@AttributeOverride(name = "email", column = @Column(name = "destino_contacto_email", nullable = true, length = 100)) })
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
			@AttributeOverride(name = "nombres", column = @Column(name = "origen_contacto_nombres", nullable = true, length = 100)),
			@AttributeOverride(name = "telefonos", column = @Column(name = "origen_contacto_telefonos", nullable = true, length = 50)),
			@AttributeOverride(name = "email", column = @Column(name = "origen_contacto_email", nullable = true, length = 100)) })
	private Contacto origenContacto;

	// ---------------------------------------------------------------------------------------------------------
	@Column(nullable = true, name = "valor_declarado")
	private Integer valorRecaudo;

	// ---------------------------------------------------------------------------------------------------------
	@Column(length = 200, nullable = false, name = "notas")
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
	private Date fechaCitaEntrega;

	@Column(nullable = true, columnDefinition = "TIME(0)")
	private Time horaCitaEntregaMinima;

	@Column(nullable = true, columnDefinition = "TIME(0)")
	private Time horaCitaEntregaMaxima;

	// ---------------------------------------------------------------------------------------------------------
	@Column(nullable = true, columnDefinition = "DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaCitaRecogida;

	@Column(nullable = true, columnDefinition = "TIME(0)")
	private Time horaCitaRecogidaMinima;

	@Column(nullable = true, columnDefinition = "TIME(0)")
	private Time horaCitaRecogidaMaxima;

	// ---------------------------------------------------------------------------------------------------------
	@Column(nullable = true, columnDefinition = "DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaAlistamiento;

	@Column(nullable = true, columnDefinition = "TIME(0)")
	private Time horaAlistamientoMinima;

	@Column(nullable = true, columnDefinition = "TIME(0)")
	private Time horaAlistamientoMaxima;

	// ---------------------------------------------------------------------------------------------------------
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "id_tipo_vehiculo_planificado", nullable = true)
	private TipoVehiculo tipoVehiculoPlanificado;

	@Column(nullable = true)
	private Integer valorFletePlanificado;

	// ---------------------------------------------------------------------------------------------------------
	@Column(length = 200, nullable = false)
	@NotNull
	private String notasAceptacion;

	@Column(nullable = true, columnDefinition = "DATETIME2(0)")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaAceptacion;

	@Column(nullable = false, length = 50)
	@NotNull
	private String usuarioAceptacion;

	// ---------------------------------------------------------------------------------------------------------
	@Column(nullable = true, columnDefinition = "DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaEntrega;

	@Column(nullable = true, columnDefinition = "TIME(0)")
	private Time horaEntregaInicio;

	@Column(nullable = true, columnDefinition = "TIME(0)")
	private Time horaEntregaFin;

	// ---------------------------------------------------------------------------------------------------------
	@Column(nullable = true, columnDefinition = "DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaRecogida;

	@Column(nullable = true, columnDefinition = "TIME(0)")
	private Time horaRecogidaInicio;

	@Column(nullable = true, columnDefinition = "TIME(0)")
	private Time horaRecogidaFin;

	// ---------------------------------------------------------------------------------------------------------
	@Column(nullable = true, columnDefinition = "DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaCargue;

	@Column(nullable = true, columnDefinition = "TIME(0)")
	private Time horaCargueInicio;

	@Column(nullable = true, columnDefinition = "TIME(0)")
	private Time horaCargueFin;

	// ---------------------------------------------------------------------------------------------------------
	@Column(nullable = true, columnDefinition = "DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaDescargue;

	@Column(nullable = true, columnDefinition = "TIME(0)")
	private Time horaDescargueInicio;

	@Column(nullable = true, columnDefinition = "TIME(0)")
	private Time horaDescargueFin;

	// ---------------------------------------------------------------------------------------------------------
	@Column(nullable = true, columnDefinition = "DATETIME2(0)")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaCreacion;

	@Column(nullable = false, length = 50)
	private String usuarioCreacion;

	@Column(nullable = true, columnDefinition = "DATETIME2(0)")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaActualizacion;

	@Column(nullable = false, length = 50)
	private String usuarioActualizacion;

	// ---------------------------------------------------------------------------------------------------------
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "id_causal_anulacion_orden", nullable = true)
	private CausalAnulacionOrden causalAnulacion;

	@Column(length = 200, nullable = false)
	@NotNull
	private String notasAnulacion;

	@Column(nullable = true, columnDefinition = "DATETIME2(0)")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaAnulacion;

	@Column(nullable = false, length = 50)
	private String usuarioAnulacion;

	// ---------------------------------------------------------------------------------------------------------
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "orden", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<LineaOrden> lineas;

	// TODO DOCUMENTOS

	// TODO REQUERIMIENTOS

	@ElementCollection
	@CollectionTable(name = "ordenes_mensajes", catalog = "ordenes", joinColumns = @JoinColumn(name = "id_orden", referencedColumnName = "id_orden"))
	private Set<MensajeEmbeddable> mensajes;

	// ---------------------------------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------------------------------
	public void confirmar(String usuario, Date fecha, String notas) {
		if (!this.isRequiereConfirmacionCitaEntrega()) {
			if (this.getFechaEntregaSugeridaMinima() != null) {
				if (this.getFechaEntregaSugeridaMaxima() != null) {
					if (this.getFechaEntregaSugeridaMinima().equals(this.getFechaEntregaSugeridaMaxima())) {
						this.setFechaCitaEntrega(this.getFechaEntregaSugeridaMaxima());
					}
				}
			}
		}

		if (!this.isRequiereConfirmacionCitaRecogida()) {
			if (this.getFechaRecogidaSugeridaMinima() != null) {
				if (this.getFechaRecogidaSugeridaMaxima() != null) {
					if (this.getFechaRecogidaSugeridaMinima().equals(this.getFechaRecogidaSugeridaMaxima())) {
						this.setFechaCitaRecogida(this.getFechaRecogidaSugeridaMaxima());
					}
				}
			}
		}

		this.setDatosConfirmacion(usuario, fecha, notas);
		this.setEstadoOrden(EstadoOrdenType.CONFIRMADA);
		this.setDatosActualizacion(usuario, fecha);
	}

	public void aceptar(String usuario, Date fecha, String notas) {
		this.setDatosAceptacion(usuario, fecha, notas);
		this.setEstadoOrden(EstadoOrdenType.ACEPTADA);
		this.setDatosActualizacion(usuario, fecha);
	}

	public void anular(String usuario, Date fecha, String notas, CausalAnulacionOrden causal) {
		this.setDatosAnulacion(usuario, fecha, notas, causal);
		this.setEstadoOrden(EstadoOrdenType.ANULADA);
		this.setDatosActualizacion(usuario, fecha);
	}

	public void revertirConfirmacion(String usuario, Date fecha) {
		this.setDatosConfirmacion("", null, this.getNotasConfirmacion());
		this.setEstadoOrden(EstadoOrdenType.NO_CONFIRMADA);
		this.setDatosActualizacion(usuario, fecha);
	}

	public void revertirAceptacion(String usuario, Date fecha) {
		this.setDatosAceptacion("", null, this.getNotasAceptacion());
		this.confirmar(usuario, fecha, "");
	}

	public void revertirAnulacion(String usuario, Date fecha, EstadoOrdenType nuevoEstado) {
		this.setDatosAnulacion("", null, "", null);
	}

	// ---------------------------------------------------------------------------------------------------------
	public Orden() {
		super();

		this.setEstadoOrden(EstadoOrdenType.NO_CONFIRMADA);
		this.setEstadoDistribucion(EstadoDistribucionType.NO_PLANIFICADA);
		this.setEstadoAlistamiento(EstadoAlistamientoType.NO_ALERTADA);
		this.setEstadoCumplidos(EstadoCumplidosType.NO_REPORTADOS);

		// ---------------------------------------------------------------------------------------------------------
		this.setDatosOrden("", null, "", null, null, null, "", true);

		// ---------------------------------------------------------------------------------------------------------
		this.setDatosDireccionDestino(null, "", "");
		this.setDatosDireccionOrigen(null, "", "");

		// ---------------------------------------------------------------------------------------------------------
		this.setRequiereConfirmacionCitaEntrega(false);
		this.setDatosCitaEntregaSugerida(null, null, null, null);

		this.setRequiereConfirmacionCitaRecogida(false);
		this.setDatosCitaRecogidaSugerida(null, null, null, null);

		// ---------------------------------------------------------------------------------------------------------
		this.setDatosDestinatario(null, "", null, null);
		this.setDatosPuntoDestino(null, null);
		this.setDatosPuntoOrigen(null, null);

		// ---------------------------------------------------------------------------------------------------------
		this.setValorRecaudo(null);

		this.setDatosConfirmacion("", null, "");

		// ---------------------------------------------------------------------------------------------------------
		this.setDatosCitaEntrega(null, null, null);
		this.setDatosCitaRecogida(null, null, null);
		this.setDatosCitaAlistamiento(null, null, null);

		this.setDatosPlanificacionPrimaria(null, null);
		this.setDatosAceptacion("", null, "");

		this.setDatosEntrega(null, null, null);
		this.setDatosRecogida(null, null, null);
		this.setDatosCargue(null, null, null);
		this.setDatosDescargue(null, null, null);

		// ---------------------------------------------------------------------------------------------------------
		this.setDatosCreacion("", null);
		this.setDatosActualizacion("", null);
		this.setDatosAnulacion("", null, "", null);

		// ---------------------------------------------------------------------------------------------------------
		this.lineas = new HashSet<>();
		this.mensajes = new HashSet<>();
	}

	// ---------------------------------------------------------------------------------------------------------
	public void setDatosOrden(String numeroOrden, Date fechaOrden, String numeroOrdenCompra, Consolidado consolidado,
			Cliente cliente, TipoServicio tipoServicio, String tipoServicioCodigoAlterno,
			boolean requiereServicioDistribucion) {
		this.setNumeroOrden(numeroOrden);
		this.setFechaOrden(fechaOrden);
		this.setNumeroOrdenCompra(numeroOrdenCompra);
		this.setConsolidado(consolidado);
		this.setCliente(cliente);
		this.setTipoServicio(tipoServicio);
		this.setTipoServicioCodigoAlterno(tipoServicioCodigoAlterno);
		this.setRequiereServicioDistribucion(requiereServicioDistribucion);
	}

	public void setDatosDireccionDestino(Ciudad ciudad, String direccion, String indicaciones) {
		this.setDestinoDireccion(new Direccion(ciudad, direccion, indicaciones));
		this.setDestinoCiudadNombre((ciudad == null) ? "" : ciudad.getNombreAlterno());
	}

	public void setDatosDireccionOrigen(Ciudad ciudad, String direccion, String indicaciones) {
		this.setOrigenDireccion(new Direccion(ciudad, direccion, indicaciones));
		this.setOrigenCiudadNombre((ciudad == null) ? "" : ciudad.getNombreAlterno());
	}

	public void setDatosCitaEntregaSugerida(Date feMi, Date feMa, Time hoMi, Time hoMa) {
		this.setFechaEntregaSugeridaMinima(feMi);
		this.setFechaEntregaSugeridaMaxima(feMa);
		this.setHoraEntregaSugeridaMinima(hoMi);
		this.setHoraEntregaSugeridaMaxima(hoMa);
	}

	public void setDatosCitaRecogidaSugerida(Date feMi, Date feMa, Time hoMi, Time hoMa) {
		this.setFechaRecogidaSugeridaMinima(feMi);
		this.setFechaRecogidaSugeridaMaxima(feMa);
		this.setHoraRecogidaSugeridaMinima(hoMi);
		this.setHoraRecogidaSugeridaMaxima(hoMa);
	}

	public void setDatosDestinatario(Canal canal, String canalCodigoAlterno, DestinatarioRemitente destinatario,
			Contacto contacto) {
		if (contacto == null) {
			if (destinatario != null) {
				contacto = destinatario.getContacto();
			}
			if (contacto == null) {
				contacto = new Contacto();
			}
		}
		this.setCanal(canal);
		this.setCanalCodigoAlterno(canalCodigoAlterno);
		this.setDestinatario(destinatario);
		this.setDestinatarioNumeroIdentificacion((destinatario == null) ? "" : destinatario.getNumeroIdentificacion());
		this.setDestinatarioNombre((destinatario == null) ? "" : destinatario.getNombre());
		this.setDestinatarioContacto(contacto);
	}

	public void setDatosPuntoDestino(DestinoOrigen ubicacion, Contacto contacto) {
		if (contacto == null) {
			if (ubicacion != null) {
				contacto = ubicacion.getContacto();
			}
			if (contacto == null) {
				contacto = new Contacto();
			}
		}
		this.setDestino(ubicacion);
		this.setDestinoNombre((ubicacion == null) ? "" : ubicacion.getNombre());
		this.setDestinoContacto(contacto);
	}

	public void setDatosPuntoOrigen(DestinoOrigen ubicacion, Contacto contacto) {
		if (contacto == null) {
			if (ubicacion != null) {
				contacto = ubicacion.getContacto();
			}
			if (contacto == null) {
				contacto = new Contacto();
			}
		}
		this.setOrigen(ubicacion);
		this.setOrigenNombre((ubicacion == null) ? "" : ubicacion.getNombre());
		this.setOrigenContacto(contacto);
	}

	public void setDatosConfirmacion(String usuario, Date fecha, String notas) {
		this.setUsuarioConfirmacion(usuario);
		this.setFechaConfirmacion(fecha);
		this.setNotasConfirmacion(notas);
	}

	public void setDatosCitaEntrega(Date fecha, Time hoMi, Time hoMa) {
		this.setFechaCitaEntrega(fecha);
		this.setHoraCitaEntregaMinima(hoMi);
		this.setHoraCitaEntregaMaxima(hoMa);
	}

	public void setDatosCitaRecogida(Date fecha, Time hoMi, Time hoMa) {
		this.setFechaCitaRecogida(fecha);
		this.setHoraCitaRecogidaMinima(hoMi);
		this.setHoraCitaRecogidaMaxima(hoMa);
	}

	public void setDatosCitaAlistamiento(Date fecha, Time hoMi, Time hoMa) {
		this.setFechaAlistamiento(fecha);
		this.setHoraAlistamientoMinima(hoMi);
		this.setHoraAlistamientoMaxima(hoMa);
	}

	public void setDatosPlanificacionPrimaria(TipoVehiculo tipoVehiculo, Integer valorFletePlanificado) {
		this.setTipoVehiculoPlanificado(tipoVehiculo);
		this.setValorFletePlanificado(valorFletePlanificado);
	}

	public void setDatosAceptacion(String usuario, Date fecha, String notas) {
		this.setUsuarioAceptacion(usuario);
		this.setFechaAceptacion(fecha);
		this.setNotasAceptacion(notas);
	}

	public void setDatosEntrega(Date fecha, Time hoMi, Time hoMa) {
		this.setFechaEntrega(fecha);
		this.setHoraEntregaInicio(hoMi);
		this.setHoraEntregaFin(hoMa);
	}

	public void setDatosRecogida(Date fecha, Time hoMi, Time hoMa) {
		this.setFechaRecogida(fecha);
		this.setHoraRecogidaInicio(hoMi);
		this.setHoraRecogidaFin(hoMa);
	}

	public void setDatosCargue(Date fecha, Time hoMi, Time hoMa) {
		this.setFechaCargue(fecha);
		this.setHoraCargueInicio(hoMi);
		this.setHoraCargueFin(hoMa);
	}

	public void setDatosDescargue(Date fecha, Time hoMi, Time hoMa) {
		this.setFechaDescargue(fecha);
		this.setHoraDescargueInicio(hoMi);
		this.setHoraDescargueFin(hoMa);
	}

	public void setDatosCreacion(String usuario, Date fecha) {
		this.setUsuarioCreacion(usuario);
		this.setFechaCreacion(fecha);
	}

	public void setDatosActualizacion(String usuario, Date fecha) {
		this.setUsuarioActualizacion(usuario);
		this.setFechaActualizacion(fecha);
	}

	public void setDatosAnulacion(String usuario, Date fecha, String notas, CausalAnulacionOrden causal) {
		this.setUsuarioAnulacion(usuario);
		this.setFechaAnulacion(fecha);
		this.setNotasAnulacion(notas);
		this.setCausalAnulacion(causal);
	}

	// ---------------------------------------------------------------------------------------------------------
	public Integer getId() {
		return id;
	}

	public String getNumeroOrden() {
		return numeroOrden;
	}

	public Date getFechaOrden() {
		return fechaOrden;
	}

	public String getNumeroOrdenCompra() {
		return numeroOrdenCompra;
	}

	public Consolidado getConsolidado() {
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

	public EstadoCumplidosType getEstadoCumplidos() {
		return estadoCumplidos;
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

	// ---------------------------------------------------------------------------------------------------------
	public Direccion getDestinoDireccion() {
		if (this.destinoDireccion == null) {
			this.destinoDireccion = new Direccion();
		}

		return destinoDireccion;
	}

	public String getDestinoCiudadNombre() {
		return destinoCiudadNombre;
	}

	// ---------------------------------------------------------------------------------------------------------
	public Direccion getOrigenDireccion() {
		if (this.origenDireccion == null) {
			this.origenDireccion = new Direccion();
		}

		return origenDireccion;
	}

	public String getOrigenCiudadNombre() {
		return origenCiudadNombre;
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
			this.destinatarioContacto = new Contacto();
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
			this.destinoContacto = new Contacto();
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
			this.origenContacto = new Contacto();
		}

		return origenContacto;
	}

	// ---------------------------------------------------------------------------------------------------------
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
	public Date getFechaCitaEntrega() {
		return fechaCitaEntrega;
	}

	public Time getHoraCitaEntregaMinima() {
		return horaCitaEntregaMinima;
	}

	public Time getHoraCitaEntregaMaxima() {
		return horaCitaEntregaMaxima;
	}

	// ---------------------------------------------------------------------------------------------------------
	public Date getFechaCitaRecogida() {
		return fechaCitaRecogida;
	}

	public Time getHoraCitaRecogidaMinima() {
		return horaCitaRecogidaMinima;
	}

	public Time getHoraCitaRecogidaMaxima() {
		return horaCitaRecogidaMaxima;
	}

	// ---------------------------------------------------------------------------------------------------------
	public Date getFechaAlistamiento() {
		return fechaAlistamiento;
	}

	public Time getHoraAlistamientoMinima() {
		return horaAlistamientoMinima;
	}

	public Time getHoraAlistamientoMaxima() {
		return horaAlistamientoMaxima;
	}

	// ---------------------------------------------------------------------------------------------------------
	public TipoVehiculo getTipoVehiculoPlanificado() {
		return tipoVehiculoPlanificado;
	}

	public Integer getValorFletePlanificado() {
		return valorFletePlanificado;
	}

	// ---------------------------------------------------------------------------------------------------------
	public String getNotasAceptacion() {
		return notasAceptacion;
	}

	public Date getFechaAceptacion() {
		return fechaAceptacion;
	}

	public String getUsuarioAceptacion() {
		return usuarioAceptacion;
	}

	// ---------------------------------------------------------------------------------------------------------
	public Date getFechaEntrega() {
		return fechaEntrega;
	}

	public Time getHoraEntregaInicio() {
		return horaEntregaInicio;
	}

	public Time getHoraEntregaFin() {
		return horaEntregaFin;
	}

	// ---------------------------------------------------------------------------------------------------------
	public Date getFechaRecogida() {
		return fechaRecogida;
	}

	public Time getHoraRecogidaInicio() {
		return horaRecogidaInicio;
	}

	public Time getHoraRecogidaFin() {
		return horaRecogidaFin;
	}

	// ---------------------------------------------------------------------------------------------------------
	public Date getFechaCargue() {
		return fechaCargue;
	}

	public Time getHoraCargueInicio() {
		return horaCargueInicio;
	}

	public Time getHoraCargueFin() {
		return horaCargueFin;
	}

	// ---------------------------------------------------------------------------------------------------------
	public Date getFechaDescargue() {
		return fechaDescargue;
	}

	public Time getHoraDescargueInicio() {
		return horaDescargueInicio;
	}

	public Time getHoraDescargueFin() {
		return horaDescargueFin;
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
	public CausalAnulacionOrden getCausalAnulacion() {
		return causalAnulacion;
	}

	public String getNotasAnulacion() {
		return notasAnulacion;
	}

	public Date getFechaAnulacion() {
		return fechaAnulacion;
	}

	public String getUsuarioAnulacion() {
		return usuarioAnulacion;
	}

	// ---------------------------------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------------------------------
	protected void setNumeroOrden(String value) {
		this.numeroOrden = coalesce(value, "");
	}

	protected void setFechaOrden(Date fechaOrden) {
		this.fechaOrden = fechaOrden;
	}

	protected void setNumeroOrdenCompra(String value) {
		this.numeroOrdenCompra = coalesce(value, "");
	}

	protected void setConsolidado(Consolidado consolidado) {
		this.consolidado = consolidado;
	}

	// ---------------------------------------------------------------------------------------------------------
	protected void setEstadoOrden(EstadoOrdenType estadoOrden) {
		this.estadoOrden = estadoOrden;
	}

	public void setEstadoDistribucion(EstadoDistribucionType estadoDistribucion) {
		this.estadoDistribucion = estadoDistribucion;
	}

	public void setEstadoAlistamiento(EstadoAlistamientoType estadoAlistamiento) {
		this.estadoAlistamiento = estadoAlistamiento;
	}

	protected void setEstadoCumplidos(EstadoCumplidosType estadoCumplidos) {
		this.estadoCumplidos = estadoCumplidos;
	}

	// ---------------------------------------------------------------------------------------------------------
	protected void setCliente(Cliente cliente) {
		this.cliente = cliente;
		this.setClienteCodigo((this.cliente != null) ? this.cliente.getCodigo() : "");
	}

	protected void setClienteCodigo(String clienteCodigo) {
		this.clienteCodigo = clienteCodigo;
	}

	// ---------------------------------------------------------------------------------------------------------
	protected void setTipoServicio(TipoServicio tipoServicio) {
		this.tipoServicio = tipoServicio;
	}

	protected void setTipoServicioCodigoAlterno(String value) {
		this.tipoServicioCodigoAlterno = coalesce(value, "");
	}

	protected void setRequiereServicioDistribucion(boolean requiereServicioDistribucion) {
		this.requiereServicioDistribucion = requiereServicioDistribucion;
	}

	// ---------------------------------------------------------------------------------------------------------
	protected void setDestinoDireccion(Direccion destinoDireccion) {
		this.destinoDireccion = new Direccion(destinoDireccion);
	}

	protected void setDestinoCiudadNombre(String destinoCiudadNombre) {
		this.destinoCiudadNombre = destinoCiudadNombre;
	}

	// ---------------------------------------------------------------------------------------------------------
	protected void setOrigenDireccion(Direccion origenDireccion) {
		this.origenDireccion = new Direccion(origenDireccion);
	}

	protected void setOrigenCiudadNombre(String origenCiudadNombre) {
		this.origenCiudadNombre = origenCiudadNombre;
	}

	// ---------------------------------------------------------------------------------------------------------
	public void setRequiereConfirmacionCitaEntrega(boolean requiereConfirmacionCitaEntrega) {
		this.requiereConfirmacionCitaEntrega = requiereConfirmacionCitaEntrega;
	}

	protected void setFechaEntregaSugeridaMinima(Date fechaEntregaSugeridaMinima) {
		this.fechaEntregaSugeridaMinima = fechaEntregaSugeridaMinima;
	}

	protected void setFechaEntregaSugeridaMaxima(Date fechaEntregaSugeridaMaxima) {
		this.fechaEntregaSugeridaMaxima = fechaEntregaSugeridaMaxima;
	}

	protected void setHoraEntregaSugeridaMinima(Time horaEntregaSugeridaMinima) {
		this.horaEntregaSugeridaMinima = horaEntregaSugeridaMinima;
	}

	protected void setHoraEntregaSugeridaMaxima(Time horaEntregaSugeridaMaxima) {
		this.horaEntregaSugeridaMaxima = horaEntregaSugeridaMaxima;
	}

	// ---------------------------------------------------------------------------------------------------------
	public void setRequiereConfirmacionCitaRecogida(boolean requiereConfirmacionCitaRecogida) {
		this.requiereConfirmacionCitaRecogida = requiereConfirmacionCitaRecogida;
	}

	protected void setFechaRecogidaSugeridaMinima(Date fechaRecogidaSugeridaMinima) {
		this.fechaRecogidaSugeridaMinima = fechaRecogidaSugeridaMinima;
	}

	protected void setFechaRecogidaSugeridaMaxima(Date fechaRecogidaSugeridaMaxima) {
		this.fechaRecogidaSugeridaMaxima = fechaRecogidaSugeridaMaxima;
	}

	protected void setHoraRecogidaSugeridaMinima(Time horaRecogidaSugeridaMinima) {
		this.horaRecogidaSugeridaMinima = horaRecogidaSugeridaMinima;
	}

	protected void setHoraRecogidaSugeridaMaxima(Time horaRecogidaSugeridaMaxima) {
		this.horaRecogidaSugeridaMaxima = horaRecogidaSugeridaMaxima;
	}

	// ---------------------------------------------------------------------------------------------------------
	public void setCanal(Canal canal) {
		this.canal = canal;
	}

	protected void setCanalCodigoAlterno(String value) {
		this.canalCodigoAlterno = coalesce(value, "");
	}

	// ---------------------------------------------------------------------------------------------------------
	public void setDestinatario(DestinatarioRemitente destinatario) {
		this.destinatario = destinatario;
	}

	protected void setDestinatarioNumeroIdentificacion(String value) {
		this.destinatarioNumeroIdentificacion = coalesce(value, "");
	}

	protected void setDestinatarioNombre(String value) {
		this.destinatarioNombre = coalesce(value, "");
	}

	public void setDestinatarioContacto(Contacto value) {
		this.destinatarioContacto = coalesce(value, new Contacto("", "", ""));
	}

	// ---------------------------------------------------------------------------------------------------------
	public void setDestino(DestinoOrigen destino) {
		this.destino = destino;
	}

	protected void setDestinoNombre(String value) {
		this.destinoNombre = coalesce(value, "");
	}

	public void setDestinoContacto(Contacto value) {
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
	public void setValorRecaudo(Integer valorRecaudo) {
		this.valorRecaudo = valorRecaudo;
	}

	// ---------------------------------------------------------------------------------------------------------
	public void setNotasConfirmacion(String value) {
		this.notasConfirmacion = coalesce(value, "");
	}

	protected void setFechaConfirmacion(Date fechaConfirmacion) {
		this.fechaConfirmacion = fechaConfirmacion;
	}

	protected void setUsuarioConfirmacion(String value) {
		this.usuarioConfirmacion = coalesce(value, "");
	}

	// ---------------------------------------------------------------------------------------------------------
	protected void setFechaCitaEntrega(Date fechaCitaEntrega) {
		this.fechaCitaEntrega = fechaCitaEntrega;
	}

	protected void setHoraCitaEntregaMinima(Time horaCitaEntregaMinima) {
		this.horaCitaEntregaMinima = horaCitaEntregaMinima;
	}

	protected void setHoraCitaEntregaMaxima(Time horaCitaEntregaMaxima) {
		this.horaCitaEntregaMaxima = horaCitaEntregaMaxima;
	}

	// ---------------------------------------------------------------------------------------------------------
	protected void setFechaCitaRecogida(Date fechaCitaRecogida) {
		this.fechaCitaRecogida = fechaCitaRecogida;
	}

	protected void setHoraCitaRecogidaMinima(Time horaCitaRecogidaMinima) {
		this.horaCitaRecogidaMinima = horaCitaRecogidaMinima;
	}

	protected void setHoraCitaRecogidaMaxima(Time horaCitaRecogidaMaxima) {
		this.horaCitaRecogidaMaxima = horaCitaRecogidaMaxima;
	}

	// ---------------------------------------------------------------------------------------------------------
	protected void setFechaAlistamiento(Date fechaAlistamiento) {
		this.fechaAlistamiento = fechaAlistamiento;
	}

	protected void setHoraAlistamientoMinima(Time horaAlistamientoMinima) {
		this.horaAlistamientoMinima = horaAlistamientoMinima;
	}

	protected void setHoraAlistamientoMaxima(Time horaAlistamientoMaxima) {
		this.horaAlistamientoMaxima = horaAlistamientoMaxima;
	}

	// ---------------------------------------------------------------------------------------------------------
	protected void setTipoVehiculoPlanificado(TipoVehiculo tipoVehiculoPlanificado) {
		this.tipoVehiculoPlanificado = tipoVehiculoPlanificado;
	}

	protected void setValorFletePlanificado(Integer valorFletePlanificado) {
		this.valorFletePlanificado = valorFletePlanificado;
	}

	// ---------------------------------------------------------------------------------------------------------
	protected void setNotasAceptacion(String notasAceptacion) {
		this.notasAceptacion = notasAceptacion;
	}

	protected void setFechaAceptacion(Date fechaAceptacion) {
		this.fechaAceptacion = fechaAceptacion;
	}

	protected void setUsuarioAceptacion(String value) {
		this.usuarioAceptacion = coalesce(value, "");
	}

	// ---------------------------------------------------------------------------------------------------------
	protected void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	protected void setHoraEntregaInicio(Time horaEntregaInicio) {
		this.horaEntregaInicio = horaEntregaInicio;
	}

	protected void setHoraEntregaFin(Time horaEntregaFin) {
		this.horaEntregaFin = horaEntregaFin;
	}

	// ---------------------------------------------------------------------------------------------------------
	protected void setFechaRecogida(Date fechaRecogida) {
		this.fechaRecogida = fechaRecogida;
	}

	protected void setHoraRecogidaInicio(Time horaRecogidaInicio) {
		this.horaRecogidaInicio = horaRecogidaInicio;
	}

	protected void setHoraRecogidaFin(Time horaRecogidaFin) {
		this.horaRecogidaFin = horaRecogidaFin;
	}

	// ---------------------------------------------------------------------------------------------------------
	protected void setFechaCargue(Date fechaCargue) {
		this.fechaCargue = fechaCargue;
	}

	protected void setHoraCargueInicio(Time horaCargueInicio) {
		this.horaCargueInicio = horaCargueInicio;
	}

	protected void setHoraCargueFin(Time horaCargueFin) {
		this.horaCargueFin = horaCargueFin;
	}

	// ---------------------------------------------------------------------------------------------------------
	protected void setFechaDescargue(Date fechaDescargue) {
		this.fechaDescargue = fechaDescargue;
	}

	protected void setHoraDescargueInicio(Time horaDescargueInicio) {
		this.horaDescargueInicio = horaDescargueInicio;
	}

	protected void setHoraDescargueFin(Time horaDescargueFin) {
		this.horaDescargueFin = horaDescargueFin;
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
	protected void setCausalAnulacion(CausalAnulacionOrden causalAnulacion) {
		this.causalAnulacion = causalAnulacion;
	}

	protected void setNotasAnulacion(String value) {
		this.notasAnulacion = coalesce(value, "");
	}

	protected void setFechaAnulacion(Date fechaAnulacion) {
		this.fechaAnulacion = fechaAnulacion;
	}

	protected void setUsuarioAnulacion(String value) {
		this.usuarioAnulacion = coalesce(value, "");
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
		// e.setOrden(null);
		boolean rc = this.getInternalLineas().remove(e);
		return rc;
	}

	public int getMaximoNumeroItem() {
		OptionalInt max = this.getInternalLineas().stream().mapToInt(a -> a.getNumeroItem()).max();
		return max.isPresent() ? max.getAsInt() : 0;
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
	// ---------------------------------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------------------------------
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
		result = prime * result + ((numeroOrden == null) ? 0 : numeroOrden.hashCode());
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
		if (numeroOrden == null) {
			if (other.numeroOrden != null)
				return false;
		} else if (!numeroOrden.equals(other.numeroOrden))
			return false;
		return true;
	}
}
