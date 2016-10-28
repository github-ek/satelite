package com.tacticlogistics.domain.model.oms;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.OptionalInt;

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
import javax.validation.constraints.NotNull;

import com.tacticlogistics.domain.model.common.valueobjects.Contacto;
import com.tacticlogistics.domain.model.common.valueobjects.MensajeEmbeddable;
import com.tacticlogistics.domain.model.crm.Canal;
import com.tacticlogistics.domain.model.crm.Cliente;
import com.tacticlogistics.domain.model.crm.Destinatario;
import com.tacticlogistics.domain.model.crm.Destino;
import com.tacticlogistics.domain.model.crm.Servicio;
import com.tacticlogistics.domain.model.geo.Ciudad;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Ordenes", catalog = "ordenes")
@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Orden implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_orden")
	private Integer id;

	@Column(nullable = false, length = 20)
	@NotNull
	private String numeroOrden;

	@Column(nullable = true)
	private LocalDate fechaOrden;

	@Column(nullable = false, length = 20)
	@NotNull
	private String numeroOrdenCompra;

	// ---------------------------------------------------------------------------------------------------------
	@Enumerated(EnumType.STRING)
	@Column(name = "id_estado_orden", nullable = false, length = 50)
	@NotNull
	private EstadoOrdenType estadoOrden;

	@Enumerated(EnumType.STRING)
	@Column(name = "id_estado_almacenamiento", nullable = false, length = 50)
	@NotNull
	private EstadoAlmacenamientoType estadoAlmacenamiento;

	@Enumerated(EnumType.STRING)
	@Column(name = "id_estado_distribucion", nullable = false, length = 50)
	@NotNull
	private EstadoDistribucionType estadoDistribucion;

	@Enumerated(EnumType.STRING)
	@Column(name = "id_estado_cumplidos", nullable = false, length = 50)
	@NotNull
	private EstadoCumplidosType estadoCumplidos;

	@Enumerated(EnumType.STRING)
	@Column(name = "id_estado_notificacion", nullable = false, length = 50)
	@NotNull
	private EstadoNotificacionType estadoNotificacion;

	// ---------------------------------------------------------------------------------------------------------
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_cliente")
	@NotNull
	private Cliente cliente;

	@Column(nullable = false, length = 20)
	@NotNull
	private String clienteCodigo;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_servicio")
	@NotNull
	private Servicio servicio;

	@Column(nullable = false, length = 50)
	@NotNull
	private String servicioCodigoAlterno;

	// ---------------------------------------------------------------------------------------------------------
	private boolean requiereConfirmacionCita;

	@Column(nullable = true)
	private LocalDate fechaSugeridaMinima;

	@Column(nullable = true)
	private LocalDate fechaSugeridaMaxima;

	@Column(nullable = true, columnDefinition = "TIME(0)")
	private LocalTime horaSugeridaMinima;

	@Column(nullable = true, columnDefinition = "TIME(0)")
	private LocalTime horaSugeridaMaxima;

	// ---------------------------------------------------------------------------------------------------------
	@Column(nullable = true)
	private LocalDate fechaPlanificadaMinima;

	@Column(nullable = true)
	private LocalDate fechaPlanificadaMaxima;

	@Column(nullable = true, columnDefinition = "TIME(0)")
	private LocalTime horaPlanificadaMinima;

	@Column(nullable = true, columnDefinition = "TIME(0)")
	private LocalTime horaPlanificadaMaxima;

	// ---------------------------------------------------------------------------------------------------------
	private boolean requiereServicioDistribucion;

	// ---------------------------------------------------------------------------------------------------------
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "id_ciudad_destino")
	private Ciudad ciudadDestino;

	@Column(nullable = false, length = 100, name = "ciudad_destino_nombre")
	@NotNull
	private String ciudadDestinoNombre;

	@Column(nullable = false, length = 150)
	@NotNull
	private String destinoDireccion;

	@Column(nullable = false, length = 150)
	@NotNull
	private String destinoIndicaciones;

	// ---------------------------------------------------------------------------------------------------------
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "id_ciudad_origen")
	private Ciudad ciudadOrigen;

	@Column(nullable = false, length = 100)
	@NotNull
	private String ciudadOrigenNombre;

	@Column(nullable = false, length = 150)
	@NotNull
	private String origenDireccion;

	@Column(nullable = false, length = 150)
	@NotNull
	private String origenIndicaciones;

	// ---------------------------------------------------------------------------------------------------------
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "id_canal")
	private Canal canal;

	@Column(length = 50, nullable = false)
	@NotNull
	private String canalCodigoAlterno;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "id_destinatario", nullable = true)
	private Destinatario destinatario;

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
	@JoinColumn(name = "id_destino")
	private Destino destino;

	@Column(nullable = false, length = 20)
	@NotNull
	private String destinoCodigo;

	@Column(nullable = false, length = 100)
	@NotNull
	private String destinoNombre;

	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = "nombres", column = @Column(name = "destino_contacto_nombres", nullable = true, length = 100)),
			@AttributeOverride(name = "telefonos", column = @Column(name = "destino_contacto_telefonos", nullable = true, length = 50)),
			@AttributeOverride(name = "email", column = @Column(name = "destino_contacto_email", nullable = true, length = 100)) })
	@NotNull
	private Contacto destinoContacto;

	// ---------------------------------------------------------------------------------------------------------
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "id_origen")
	private Destino origen;

	@Column(nullable = false, length = 20)
	@NotNull
	private String origenCodigo;

	@Column(nullable = false, length = 100)
	@NotNull
	private String origenNombre;

	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = "nombres", column = @Column(name = "origen_contacto_nombres", nullable = true, length = 100)),
			@AttributeOverride(name = "telefonos", column = @Column(name = "origen_contacto_telefonos", nullable = true, length = 50)),
			@AttributeOverride(name = "email", column = @Column(name = "origen_contacto_email", nullable = true, length = 100)) })
	@NotNull
	private Contacto origenContacto;

	// ---------------------------------------------------------------------------------------------------------
	@Column(nullable = true)
	private Integer valorRecaudo;

	// ---------------------------------------------------------------------------------------------------------
	@Column(length = 200, nullable = false)
	@NotNull
	private String notasConfirmacion;

	@Column(nullable = true)
	private LocalDateTime fechaConfirmacion;

	@Column(nullable = false, length = 50)
	@NotNull
	private String usuarioConfirmacion;

	// ---------------------------------------------------------------------------------------------------------
	@Column(nullable = true)
	private LocalDate fechaAlistamiento;

	@Column(nullable = true)
	private LocalDate fechaCita;

	@Column(nullable = true, columnDefinition = "TIME(0)")
	private LocalTime horaCitaMinima;

	@Column(nullable = true, columnDefinition = "TIME(0)")
	private LocalTime horaCitaMaxima;

	@Column(nullable = true)
	private LocalDateTime fechaAsignacionCita;

	@Column(nullable = false, length = 50)
	@NotNull
	private String usuarioAsignacionCita;

	// ---------------------------------------------------------------------------------------------------------
	@Column(length = 200, nullable = false)
	@NotNull
	private String notasAceptacion;

	@Column(nullable = true)
	private LocalDateTime fechaAceptacion;

	@Column(nullable = false, length = 50)
	@NotNull
	private String usuarioAceptacion;

	// ---------------------------------------------------------------------------------------------------------
	@Column(nullable = true, name = "id_corte_ruta")
	private Integer corteRutaId;

	@Column(nullable = true)
	private LocalDateTime fechaCorteRuta;

	@Column(nullable = false, length = 50)
	@NotNull
	private String usuarioCorteRuta;

	// ---------------------------------------------------------------------------------------------------------
	@Column(nullable = true, name = "id_ruta")
	private Integer rutaId;

	@Column(nullable = true)
	private Integer secuenciaRuta;

	@Column(nullable = true)
	private LocalDateTime fechaAsignacionRuta;

	@Column(nullable = false, length = 50)
	@NotNull
	private String usuarioAsignacionRuta;

	// ---------------------------------------------------------------------------------------------------------
	@Column(nullable = true)
	private LocalDateTime fechaEstimadaEntrega;

	@Column(nullable = true)
	private LocalDateTime fechaEntrega;

	@Column(nullable = true)
	private LocalDateTime fechaInicioEntrega;

	@Column(nullable = true)
	private LocalDateTime fechaFinEntrega;

	// ---------------------------------------------------------------------------------------------------------
	@Column(nullable = false)
	private LocalDateTime fechaCreacion;

	@Column(nullable = false, length = 50)
	@NotNull
	private String usuarioCreacion;

	@Column(nullable = false)
	@NotNull
	private LocalDateTime fechaActualizacion;

	@Column(nullable = false, length = 50)
	@NotNull
	private String usuarioActualizacion;

	// ---------------------------------------------------------------------------------------------------------
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "id_causal_anulacion")
	private CausalAnulacion causalAnulacion;

	@Column(length = 200, nullable = false)
	@NotNull
	private String notasAnulacion;

	@Column(nullable = true)
	private LocalDateTime fechaAnulacion;

	@Column(nullable = false, length = 50)
	private String usuarioAnulacion;

	// ---------------------------------------------------------------------------------------------------------
	@Column(nullable = true, name = "id_orden_original")
	private Integer ordenOriginalId;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "id_causal_reprogramacion")
	private CausalReprogramacion causalReprogramacion;

	@Column(length = 200, nullable = false)
	@NotNull
	private String notasReprogramacion;

	@Column(nullable = true)
	private LocalDateTime fechaReprogramacion;

	@Column(nullable = false, length = 50)
	private String usuarioReprogramacion;

	// ---------------------------------------------------------------------------------------------------------
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "id_orden", nullable = false)
	@Setter(AccessLevel.NONE)
	@Getter(AccessLevel.NONE)
	private List<LineaOrden> lineas;

	@ElementCollection
	@CollectionTable(name = "ordenes_mensajes", catalog = "ordenes", joinColumns = @JoinColumn(name = "id_orden", referencedColumnName = "id_orden", nullable = false))
	private List<MensajeEmbeddable> mensajes;

	// ---------------------------------------------------------------------------------------------------------
	public void confirmar(String usuario, LocalDateTime fecha, String notas) {
		if (!this.isRequiereConfirmacionCita()) {
			if (this.getFechaSugeridaMinima() != null) {
				if (this.getFechaSugeridaMaxima() != null) {
					if (this.getFechaSugeridaMinima().equals(this.getFechaSugeridaMaxima())) {
						this.setFechaCita(this.getFechaSugeridaMaxima());
					}
				}
			}
		}

		this.setEstadoOrden(EstadoOrdenType.CONFIRMADA);
		this.setDatosConfirmacion(usuario, fecha, notas);
		this.setDatosActualizacion(fecha, usuario);
	}

	public void aceptar(LocalDateTime fecha, String usuario, String notas) {
		if (this.getEstadoOrden() == EstadoOrdenType.ANULADA) {
			this.revertirAnulacion(fecha, usuario);
		}
		this.setEstadoOrden(EstadoOrdenType.ACEPTADA);
		this.setDatosAceptacion(fecha, usuario, notas);
		this.setDatosActualizacion(fecha, usuario);
	}

	public void anular(LocalDateTime fecha, String usuario, String notas, CausalAnulacion causal) {
		this.setEstadoOrden(EstadoOrdenType.ANULADA);
		this.setDatosAnulacion(fecha, usuario, notas, causal);
		this.setDatosActualizacion(fecha, usuario);
	}

	public void revertirConfirmacion(String usuario, LocalDateTime fecha) {
		this.setEstadoOrden(EstadoOrdenType.NO_CONFIRMADA);
		this.setDatosConfirmacion("", null, this.getNotasConfirmacion());
		this.setDatosActualizacion(fecha, usuario);
	}

	public void revertirAceptacion(String usuario, LocalDateTime fecha) {
		this.setDatosAceptacion(null, "", this.getNotasAceptacion());
		this.confirmar(usuario, fecha, "");
	}

	private void revertirAnulacion(LocalDateTime fecha, String usuario) {
		this.setDatosAnulacion(null, "", "", null);
	}

	// ---------------------------------------------------------------------------------------------------------
	public Orden() {
		super();

		this.setEstadoOrden(EstadoOrdenType.NO_CONFIRMADA);
		this.setEstadoDistribucion(EstadoDistribucionType.NO_PLANIFICADA);
		this.setEstadoAlmacenamiento(EstadoAlmacenamientoType.NO_ALERTADA);
		this.setEstadoCumplidos(EstadoCumplidosType.NO_REPORTADOS);
		this.setEstadoNotificacion(EstadoNotificacionType.SIN_NOTIFICAR);

		// ---------------------------------------------------------------------------------------------------------
		this.setDatosOrden("", null, "", null, null, "", true);

		// ---------------------------------------------------------------------------------------------------------
		this.setDatosDireccionDestino(null, "", "");
		this.setRequiereConfirmacionCita(false);
		this.setDatosCitaSugerida(null, null, null, null);

		// ---------------------------------------------------------------------------------------------------------
		this.setDatosDireccionOrigen(null, "", "");

		// ---------------------------------------------------------------------------------------------------------
		this.setDatosDestinatario(null, "", null, null);
		this.setDatosPuntoDestino(null, null);
		this.setDatosPuntoOrigen(null, null);

		// ---------------------------------------------------------------------------------------------------------
		this.setValorRecaudo(null);

		this.setDatosConfirmacion("", null, "");

		// ---------------------------------------------------------------------------------------------------------
		this.setDatosAlistamiento(null);
		this.setDatosCita(null, null, null);

		this.setDatosAceptacion(null, "", "");

		this.setDatosEntrega(null, null, null);

		// ---------------------------------------------------------------------------------------------------------
		this.setDatosCorteRuta(null, "", null);
		this.setDatosRuta(null, null, "", null);

		// ---------------------------------------------------------------------------------------------------------
		this.setDatosCreacion("", null);
		this.setDatosActualizacion(null, "");
		this.setDatosAnulacion(null, "", "", null);
		this.setDatosReprogramacion("", null, "", null, null);
	}

	// ---------------------------------------------------------------------------------------------------------
	public void setDatosOrden(String numeroOrden, LocalDate fechaOrden, String numeroOrdenCompra, Cliente cliente,
			Servicio servicio, String servicioCodigoAlterno, boolean requiereServicioDistribucion) {
		this.setNumeroOrden(numeroOrden);
		this.setFechaOrden(fechaOrden);
		this.setNumeroOrdenCompra(numeroOrdenCompra);

		this.setCliente(cliente);
		this.setServicio(servicio);
		this.setServicioCodigoAlterno(servicioCodigoAlterno);
		this.setRequiereServicioDistribucion(requiereServicioDistribucion);
	}

	public void setDatosDireccionDestino(Ciudad ciudad, String direccion, String indicaciones) {
		this.setCiudadDestino(ciudad);
		this.setCiudadDestinoNombre((ciudad == null) ? "" : ciudad.getNombreAlterno());
		this.setDestinoDireccion(direccion);
		this.setDestinoIndicaciones(indicaciones);
	}

	public void setDatosCitaSugerida(LocalDate feMi, LocalDate feMa, LocalTime hoMi, LocalTime hoMa) {
		this.setFechaSugeridaMinima(feMi);
		this.setFechaSugeridaMaxima(feMa);
		this.setHoraSugeridaMinima(hoMi);
		this.setHoraSugeridaMaxima(hoMa);
	}

	public void setDatosDireccionOrigen(Ciudad ciudad, String direccion, String indicaciones) {
		this.setCiudadOrigen(ciudad);
		this.setCiudadOrigenNombre((ciudad == null) ? "" : ciudad.getNombreAlterno());
		this.setOrigenDireccion(direccion);
		this.setOrigenIndicaciones(indicaciones);
	}

	public void setDatosDestinatario(Canal canal, String canalCodigoAlterno, Destinatario destinatario,
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

	public void setDatosPuntoDestino(Destino punto, Contacto contacto) {
		if (contacto == null) {
			if (punto != null) {
				contacto = punto.getContacto();
			}
			if (contacto == null) {
				contacto = new Contacto();
			}
		}
		this.setDestino(punto);
		this.setDestinoCodigo((punto == null) ? "" : punto.getCodigo());
		this.setDestinoNombre((punto == null) ? "" : punto.getNombre());
		this.setDestinoContacto(contacto);
	}

	public void setDatosPuntoOrigen(Destino punto, Contacto contacto) {
		if (contacto == null) {
			if (punto != null) {
				contacto = punto.getContacto();
			}
			if (contacto == null) {
				contacto = new Contacto();
			}
		}
		this.setOrigen(punto);
		this.setOrigenCodigo((punto == null) ? "" : punto.getCodigo());
		this.setOrigenNombre((punto == null) ? "" : punto.getNombre());
		this.setOrigenContacto(contacto);
	}

	public void setDatosConfirmacion(String usuario, LocalDateTime fecha, String notas) {
		this.setNotasConfirmacion(notas);
		this.setFechaConfirmacion(fecha);
		this.setUsuarioConfirmacion(usuario);
	}

	public void setDatosCita(LocalDate fecha, LocalTime hoMi, LocalTime hoMa) {
		this.setFechaCita(fecha);
		this.setHoraCitaMinima(hoMi);
		this.setHoraCitaMaxima(hoMa);
		this.setFechaAsignacionCita(null);
		this.setUsuarioAsignacionCita("");
	}

	public void setDatosAlistamiento(LocalDate fecha) {
		this.setFechaAlistamiento(fecha);
	}

	public void setDatosAceptacion(LocalDateTime fecha, String usuario, String notas) {
		this.setNotasAceptacion(notas);
		this.setFechaAceptacion(fecha);
		this.setUsuarioAceptacion(usuario);
	}

	public void setDatosCorteRuta(Integer corteRutaId, String usuario, LocalDateTime fecha) {
		this.setCorteRutaId(corteRutaId);
		this.setFechaCorteRuta(fecha);
		this.setUsuarioCorteRuta(usuario);
	}

	public void setDatosRuta(Integer rutaId, Integer secuenciaRuta, String usuario, LocalDateTime fecha) {
		this.setRutaId(rutaId);
		this.setSecuenciaRuta(secuenciaRuta);
		this.setFechaAsignacionRuta(fecha);
		this.setUsuarioAsignacionRuta(usuario);
	}

	public void setDatosEntrega(LocalDateTime fechaEntrega, LocalDateTime fechaInicioEntrega,
			LocalDateTime fechaFinEntrega) {
		this.setFechaEntrega(fechaEntrega);
		this.setFechaInicioEntrega(fechaInicioEntrega);
		this.setFechaFinEntrega(fechaFinEntrega);
	}

	public void setDatosCreacion(String usuario, LocalDateTime fecha) {
		this.setUsuarioCreacion(usuario);
		this.setFechaCreacion(fecha);
	}

	public void setDatosActualizacion(LocalDateTime fecha, String usuario) {
		this.setUsuarioActualizacion(usuario);
		this.setFechaActualizacion(fecha);
	}

	protected void setDatosAnulacion(LocalDateTime fecha, String usuario, String notas, CausalAnulacion causal) {
		this.setUsuarioAnulacion(usuario);
		this.setFechaAnulacion(fecha);
		this.setNotasAnulacion(notas);
		this.setCausalAnulacion(causal);
	}

	// TODO ORDEN ORIGINAL
	protected void setDatosReprogramacion(String usuario, LocalDateTime fecha, String notas,
			CausalReprogramacion causal, Integer ordenOriginalId) {
		this.setUsuarioReprogramacion(usuario);
		this.setFechaReprogramacion(fecha);
		this.setNotasReprogramacion(notas);
		this.setCausalReprogramacion(causal);
		this.setOrdenOriginalId(ordenOriginalId);
	}

	// ---------------------------------------------------------------------------------------------------------
	protected List<LineaOrden> getInternalLineas() {
		if (this.lineas == null) {
			this.lineas = new ArrayList<>();
		}
		return lineas;
	}

	public List<LineaOrden> getLineas() {
		return Collections.unmodifiableList(getInternalLineas());
	}

	public int lineasSize() {
		return getInternalLineas().size();
	}

	public boolean isLineasEmpty() {
		return getInternalLineas().isEmpty();
	}

	public boolean addLinea(LineaOrden e) {
		e.setNumeroItem(this.getNumeroItemMaximo() + 1);
		return getInternalLineas().add(e);
	}

	public boolean removeLinea(Object o) {
		return getInternalLineas().remove(o);
	}

	public boolean lineasAddAll(Collection<? extends LineaOrden> c) {
		int i = this.getNumeroItemMaximo();
		for (LineaOrden e : c) {
			e.setNumeroItem(++i);
		}
		return getInternalLineas().addAll(c);
	}

	public void lineasReplaceAll(Collection<? extends LineaOrden> c) {
		lineasClear();
		lineasAddAll(c);
	}

	public void lineasClear() {
		getInternalLineas().clear();
	}

	public int getNumeroItemMaximo() {
		// @formatter:off
		OptionalInt max = this.getLineas()
				.stream()
				.mapToInt(a -> a.getNumeroItem())
				.max();
		// @formatter:on
		return max.isPresent() ? max.getAsInt() : 0;
	}

	// ---------------------------------------------------------------------------------------------------------
	protected List<MensajeEmbeddable> getInternalMensajes() {
		if (this.mensajes == null) {
			this.mensajes = new ArrayList<>();
		}
		return mensajes;
	}

	public List<MensajeEmbeddable> getMensajes() {
		return Collections.unmodifiableList(getInternalMensajes());
	}

	public int mensajesSize() {
		return getInternalMensajes().size();
	}

	public boolean isMensajesEmpty() {
		return getInternalMensajes().isEmpty();
	}

	public boolean addMensaje(MensajeEmbeddable e) {
		return getInternalMensajes().add(e);
	}

	public boolean removeMensaje(Object o) {
		return getInternalMensajes().remove(o);
	}

	public boolean mensajesAddAll(Collection<? extends MensajeEmbeddable> c) {
		return getInternalMensajes().addAll(c);
	}

	public void mensajesReplaceAll(Collection<? extends MensajeEmbeddable> c) {
		mensajesClear();
		mensajesAddAll(c);
	}

	public void mensajesClear() {
		getInternalMensajes().clear();
	}

	// ---------------------------------------------------------------------------------------------------------
	public static boolean transicionPermitida(EstadoOrdenType estadoOrigen, EstadoOrdenType estadoDestino) {
		switch (estadoOrigen) {
		case NO_CONFIRMADA:
			return transicionPermitidaDesde_NO_CONFIRMADA(estadoDestino);
		case CONFIRMADA:
			return transicionPermitidaDesde_CONFIRMADA(estadoDestino);
		case ACEPTADA:
			return transicionPermitidaDesde_ACEPTADA(estadoDestino);

		case EJECUCION:
			return transicionPermitidaDesde_EJECUCION(estadoDestino);
		case NO_ENTREGADA:
			return transicionPermitidaDesde_NO_ENTREGADA(estadoDestino);

		case ENTREGADA:
			return transicionPermitidaDesde_ENTREGADA(estadoDestino);
		case REPROGRAMADA:
			return transicionPermitidaDesde_REPROGRAMADA(estadoDestino);
		case NO_REPROGRAMADA:
			return transicionPermitidaDesde_NO_REPROGRAMADA(estadoDestino);
		case NOVEDADES:
			return transicionPermitidaDesde_NOVEDADES(estadoDestino);

		case ANULADA:
			return transicionPermitidaDesde_ANULADA(estadoDestino);
		default:
			break;
		}
		return false;
	}

	private static boolean transicionPermitidaDesde_NO_CONFIRMADA(EstadoOrdenType nuevoEstado) {
		switch (nuevoEstado) {
		case CONFIRMADA:
		case ACEPTADA:
			// Solo si es planner
		case ANULADA:
			return true;
		default:
			return false;
		}
	}

	private static boolean transicionPermitidaDesde_CONFIRMADA(EstadoOrdenType nuevoEstado) {
		switch (nuevoEstado) {
		case NO_CONFIRMADA:
		case ACEPTADA:
		case ANULADA:
			return true;
		default:
			return false;
		}
	}

	private static boolean transicionPermitidaDesde_ACEPTADA(EstadoOrdenType nuevoEstado) {
		switch (nuevoEstado) {
		case CONFIRMADA:
		case ANULADA:
			return true;
		default:
			return false;
		}
	}

	private static boolean transicionPermitidaDesde_EJECUCION(EstadoOrdenType nuevoEstado) {
		switch (nuevoEstado) {
		case ACEPTADA:
			// Depende de lo avanzado que vaya la operacion
		case NO_ENTREGADA:
		case ENTREGADA:
		case REPROGRAMADA:
		case NO_REPROGRAMADA:
		case NOVEDADES:
		case ANULADA:
			// Depende de lo avanzado que vaya la operacion
			return true;
		default:
			return false;
		}
	}

	private static boolean transicionPermitidaDesde_NO_ENTREGADA(EstadoOrdenType nuevoEstado) {
		switch (nuevoEstado) {
		case ENTREGADA:
		case REPROGRAMADA:
		case NO_REPROGRAMADA:
		case NOVEDADES:
			return true;
		default:
			return false;
		}
	}

	private static boolean transicionPermitidaDesde_ENTREGADA(EstadoOrdenType nuevoEstado) {
		switch (nuevoEstado) {
		case NO_ENTREGADA:
		case REPROGRAMADA:
		case NO_REPROGRAMADA:
		case NOVEDADES:
			return true;
		default:
			return false;
		}
	}

	private static boolean transicionPermitidaDesde_REPROGRAMADA(EstadoOrdenType nuevoEstado) {
		return false;
	}

	private static boolean transicionPermitidaDesde_NO_REPROGRAMADA(EstadoOrdenType nuevoEstado) {
		switch (nuevoEstado) {
		case REPROGRAMADA:
			return true;
		default:
			return false;
		}
	}

	private static boolean transicionPermitidaDesde_NOVEDADES(EstadoOrdenType nuevoEstado) {
		switch (nuevoEstado) {
		case ENTREGADA:
		case NO_ENTREGADA:
		case REPROGRAMADA:
		case NO_REPROGRAMADA:
			return true;
		default:
			return false;
		}
	}

	private static boolean transicionPermitidaDesde_ANULADA(EstadoOrdenType nuevoEstado) {
		return false;
	}

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
