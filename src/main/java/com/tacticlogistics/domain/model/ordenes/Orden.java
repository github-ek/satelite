package com.tacticlogistics.domain.model.ordenes;

import static com.tacticlogistics.infrastructure.services.Basic.coalesce;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
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
import javax.validation.constraints.NotNull;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;

import com.tacticlogistics.domain.model.common.valueobjects.Contacto;
import com.tacticlogistics.domain.model.common.valueobjects.MensajeEmbeddable;
import com.tacticlogistics.domain.model.crm.Canal;
import com.tacticlogistics.domain.model.crm.Cliente;
import com.tacticlogistics.domain.model.crm.Destinatario;
import com.tacticlogistics.domain.model.crm.Destino;
import com.tacticlogistics.domain.model.crm.TipoServicio;
import com.tacticlogistics.domain.model.geo.Ciudad;
import com.tacticlogistics.domain.model.oms.CausalAnulacion;
import com.tacticlogistics.domain.model.oms.CausalReprogramacion;
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
	@Column(name = "id_estado_alistamiento", nullable = false, length = 50)
	@NotNull
	private EstadoAlistamientoType estadoAlistamiento;

	@Enumerated(EnumType.STRING)
	@Column(name = "id_estado_distribucion", nullable = false, length = 50)
	@NotNull
	private EstadoDistribucionType estadoDistribucion;

	@Enumerated(EnumType.STRING)
	@Column(name = "id_estado_cumplidos", nullable = false, length = 50)
	@NotNull
	private EstadoCumplidosType estadoCumplidos;

	// ---------------------------------------------------------------------------------------------------------
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_cliente", nullable = false)
	@NotNull
	private Cliente cliente;

	@Column(nullable = false, length = 20)
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
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "id_ciudad_destino", nullable = true)
	private Ciudad ciudadDestino;

	@Column(nullable = false, length = 100, name = "destino_ciudad_nombre_alterno")
	@NotNull
	private String destinoCiudadNombre;

	@Column(nullable = false, length = 150)
	@NotNull
	private String destinoDireccion;

	@Column(nullable = false, length = 150)
	@NotNull
	private String destinoIndicaciones;

	// ---------------------------------------------------------------------------------------------------------
	private boolean requiereConfirmacionCitaEntrega;

	@Column(nullable = true)
	private LocalDate fechaEntregaSugeridaMinima;

	@Column(nullable = true)
	private LocalDate fechaEntregaSugeridaMaxima;

	@Column(nullable = true, columnDefinition = "TIME(0)")
	private LocalTime horaEntregaSugeridaMinima;

	@Column(nullable = true, columnDefinition = "TIME(0)")
	private LocalTime horaEntregaSugeridaMaxima;

	// ---------------------------------------------------------------------------------------------------------
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "id_ciudad_origen", nullable = true)
	private Ciudad ciudadOrigen;

	@Column(nullable = false, length = 100, name = "origen_ciudad_nombre_alterno")
	@NotNull
	private String origenCiudadNombre;

	@Column(nullable = false, length = 150)
	@NotNull
	private String origenDireccion;

	@Column(nullable = false, length = 150)
	@NotNull
	private String origenIndicaciones;

	// ---------------------------------------------------------------------------------------------------------
	private boolean requiereConfirmacionCitaRecogida;

	@Column(nullable = true)
	private LocalDate fechaRecogidaSugeridaMinima;

	@Column(nullable = true)
	private LocalDate fechaRecogidaSugeridaMaxima;

	@Column(nullable = true, columnDefinition = "TIME(0)")
	private LocalTime horaRecogidaSugeridaMinima;

	@Column(nullable = true, columnDefinition = "TIME(0)")
	private LocalTime horaRecogidaSugeridaMaxima;

	// ---------------------------------------------------------------------------------------------------------
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "id_canal", nullable = true)
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
	@JoinColumn(name = "id_destino", nullable = true)
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
	private Contacto destinoContacto;

	// ---------------------------------------------------------------------------------------------------------
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "id_origen", nullable = true)
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
	private Contacto origenContacto;

	// ---------------------------------------------------------------------------------------------------------
	// @Column(length = 200, nullable = false)
	// @NotNull
	// private String notasRequerimientosDistribucion;
	//
	// @Column(length = 200, nullable = false)
	// @NotNull
	// private String notasRequerimientosAlistamiento;

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
	// ---------------------------------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------------------------------
	@Column(nullable = true)
	private LocalDate fechaCitaEntrega;

	@Column(nullable = true, columnDefinition = "TIME(0)")
	private LocalTime horaCitaEntregaMinima;

	@Column(nullable = true, columnDefinition = "TIME(0)")
	private LocalTime horaCitaEntregaMaxima;

	// ---------------------------------------------------------------------------------------------------------
	@Column(nullable = true)

	private LocalDate fechaCitaRecogida;

	@Column(nullable = true, columnDefinition = "TIME(0)")
	private LocalTime horaCitaRecogidaMinima;

	@Column(nullable = true, columnDefinition = "TIME(0)")
	private LocalTime horaCitaRecogidaMaxima;

	// ---------------------------------------------------------------------------------------------------------
	@Column(nullable = true)

	private LocalDate fechaAlistamiento;

	@Column(nullable = true, columnDefinition = "TIME(0)")
	private LocalTime horaAlistamientoMinima;

	@Column(nullable = true, columnDefinition = "TIME(0)")
	private LocalTime horaAlistamientoMaxima;

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
	private Integer numeroCajas;

	@Column(nullable = true)
	private LocalDateTime fechaAsignacionRuta;

	@Column(nullable = false, length = 50)
	@NotNull
	private String usuarioAsignacionRuta;

	// ---------------------------------------------------------------------------------------------------------
	@Column(nullable = true)
	private LocalDateTime fechaEntregaEstimada;

	@Column(nullable = true)
	private LocalDateTime fechaEntrega;

	@Column(nullable = true)
	private LocalDateTime fechaEntregaInicio;

	@Column(nullable = true)
	private LocalDateTime fechaEntregaFin;

	// ---------------------------------------------------------------------------------------------------------
	@Column(nullable = true)
	private LocalDateTime fechaRecogidaEstimada;

	@Column(nullable = true)
	private LocalDateTime fechaRecogida;

	@Column(nullable = true)
	private LocalDateTime fechaRecogidaInicio;

	@Column(nullable = true)
	private LocalDateTime fechaRecogidaFin;

	// ---------------------------------------------------------------------------------------------------------
	@Column(nullable = true)
	private LocalDateTime fechaCreacion;

	@Column(nullable = false, length = 50)
	private String usuarioCreacion;

	@Column(nullable = true)
	private LocalDateTime fechaActualizacion;

	@Column(nullable = false, length = 50)
	private String usuarioActualizacion;

	// ---------------------------------------------------------------------------------------------------------
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "id_causal_anulacion", nullable = true)
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
	@JoinColumn(name = "id_causal_reprogramacion", nullable = true)
	private CausalReprogramacion causalReprogramacion;

	@Column(length = 200, nullable = false)
	@NotNull
	private String notasReprogramacion;

	@Column(nullable = true)
	private LocalDateTime fechaReprogramacion;

	@Column(nullable = false, length = 50)
	private String usuarioReprogramacion;

	// ---------------------------------------------------------------------------------------------------------
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "orden", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<LineaOrden> lineas;

	// TODO AGREGAR LA LINEA ORDEN Y ELIMINAR DE LINEAS
	@ElementCollection
	@CollectionTable(name = "ordenes_mensajes", catalog = "ordenes", joinColumns = @JoinColumn(name = "id_orden", referencedColumnName = "id_orden"))
	private Set<MensajeEmbeddable> mensajes;

	// TODO DOCUMENTOS

	// TODO REQUERIMIENTOS
	// @ElementCollection
	// @CollectionTable(name = "ordenes_requerimientos_distribucion", catalog =
	// "oms", joinColumns = @JoinColumn(name = "id_orden", referencedColumnName
	// = "id_orden"))
	// private Set<OmsOrdenRequerimientoDistribucionAssociation>
	// requerimientosDistribucion;
	//
	// @ElementCollection
	// @CollectionTable(name = "ordenes_requerimientos_alistamiento", catalog =
	// "oms", joinColumns = @JoinColumn(name = "id_orden", referencedColumnName
	// = "id_orden"))
	// private Set<OmsOrdenRequerimientoAlistamientoAssociation>
	// requerimientosAlistamiento;

	// ---------------------------------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------------------------------
	public void confirmar(String usuario, LocalDateTime fecha, String notas) {
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

		this.setEstadoOrden(EstadoOrdenType.CONFIRMADA);
		this.setDatosConfirmacion(usuario, fecha, notas);
		this.setDatosActualizacion(fecha, usuario);
	}

	public void aceptar(LocalDateTime fecha,String usuario, String notas) {
		if (this.getEstadoOrden() == EstadoOrdenType.ANULADA) {
			this.revertirAnulacion(fecha,usuario);
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

	private void revertirConfirmacion(String usuario, LocalDateTime fecha) {
		this.setEstadoOrden(EstadoOrdenType.NO_CONFIRMADA);
		this.setDatosConfirmacion("", null, this.getNotasConfirmacion());
		this.setDatosActualizacion(fecha, usuario);
	}

	private void revertirAceptacion(String usuario, LocalDateTime fecha) {
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
		this.setEstadoAlistamiento(EstadoAlistamientoType.NO_ALERTADA);
		this.setEstadoCumplidos(EstadoCumplidosType.NO_REPORTADOS);

		// ---------------------------------------------------------------------------------------------------------
		this.setDatosOrden("", null, "", null, null, "", true);

		// ---------------------------------------------------------------------------------------------------------
		this.setDatosDireccionDestino(null, "", "");
		this.setRequiereConfirmacionCitaEntrega(false);
		this.setDatosCitaEntregaSugerida(null, null, null, null);

		// ---------------------------------------------------------------------------------------------------------
		this.setDatosDireccionOrigen(null, "", "");
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
		this.setDatosAceptacion(null, "", "");

		this.setDatosEntrega(null, null, null);
		this.setDatosRecogida(null, null, null);

		// ---------------------------------------------------------------------------------------------------------
		this.setDatosCorteRuta(null, "", null);
		this.setDatosRuta(null, null, null, "", null);

		// ---------------------------------------------------------------------------------------------------------
		this.setDatosCreacion("", null);
		this.setDatosActualizacion(null, "");
		this.setDatosAnulacion(null, "", "", null);
		this.setDatosReprogramacion("", null, "", null, null);

		// ---------------------------------------------------------------------------------------------------------
		this.lineas = new HashSet<>();
		this.mensajes = new HashSet<>();
	}

	// ---------------------------------------------------------------------------------------------------------
	public void setDatosOrden(String numeroOrden, LocalDate fechaOrden, String numeroOrdenCompra, Cliente cliente,
			TipoServicio tipoServicio, String tipoServicioCodigoAlterno, boolean requiereServicioDistribucion) {
		this.setNumeroOrden(numeroOrden);
		this.setFechaOrden(fechaOrden);
		this.setNumeroOrdenCompra(numeroOrdenCompra);

		this.setCliente(cliente);
		this.setTipoServicio(tipoServicio);
		this.setTipoServicioCodigoAlterno(tipoServicioCodigoAlterno);
		this.setRequiereServicioDistribucion(requiereServicioDistribucion);
	}

	public void setDatosDireccionDestino(Ciudad ciudad, String direccion, String indicaciones) {
		this.setCiudadDestino(ciudad);
		this.setDestinoCiudadNombre((ciudad == null) ? "" : ciudad.getNombreAlterno());
		this.setDestinoDireccion(direccion);
		this.setDestinoIndicaciones(indicaciones);
	}

	public void setDatosCitaEntregaSugerida(LocalDate feMi, LocalDate feMa, LocalTime hoMi, LocalTime hoMa) {
		this.setFechaEntregaSugeridaMinima(feMi);
		this.setFechaEntregaSugeridaMaxima(feMa);
		this.setHoraEntregaSugeridaMinima(hoMi);
		this.setHoraEntregaSugeridaMaxima(hoMa);
	}

	public void setDatosDireccionOrigen(Ciudad ciudad, String direccion, String indicaciones) {
		this.setCiudadOrigen(ciudad);
		this.setOrigenCiudadNombre((ciudad == null) ? "" : ciudad.getNombreAlterno());
		this.setOrigenDireccion(direccion);
		this.setOrigenIndicaciones(indicaciones);
	}

	public void setDatosCitaRecogidaSugerida(LocalDate feMi, LocalDate feMa, LocalTime hoMi, LocalTime hoMa) {
		this.setFechaRecogidaSugeridaMinima(feMi);
		this.setFechaRecogidaSugeridaMaxima(feMa);
		this.setHoraRecogidaSugeridaMinima(hoMi);
		this.setHoraRecogidaSugeridaMaxima(hoMa);
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

	public void setDatosCitaEntrega(LocalDate fecha, LocalTime hoMi, LocalTime hoMa) {
		this.setFechaCitaEntrega(fecha);
		this.setHoraCitaEntregaMinima(hoMi);
		this.setHoraCitaEntregaMaxima(hoMa);
	}

	public void setDatosCitaRecogida(LocalDate fecha, LocalTime hoMi, LocalTime hoMa) {
		this.setFechaCitaRecogida(fecha);
		this.setHoraCitaRecogidaMinima(hoMi);
		this.setHoraCitaRecogidaMaxima(hoMa);
	}

	public void setDatosCitaAlistamiento(LocalDate fecha, LocalTime hoMi, LocalTime hoMa) {
		this.setFechaAlistamiento(fecha);
		this.setHoraAlistamientoMinima(hoMi);
		this.setHoraAlistamientoMaxima(hoMa);
	}

	public void setDatosPlanificacionPrimaria(TipoVehiculo tipoVehiculo, Integer valorFletePlanificado) {
		this.setTipoVehiculoPlanificado(tipoVehiculo);
		this.setValorFletePlanificado(valorFletePlanificado);
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

	public void setDatosRuta(Integer rutaId, Integer secuenciaRuta, Integer numeroCajas, String usuario,
			LocalDateTime fecha) {
		this.setRutaId(rutaId);
		this.setSecuenciaRuta(secuenciaRuta);
		this.setNumeroCajas(numeroCajas);
		this.setFechaAsignacionRuta(fecha);
		this.setUsuarioAsignacionRuta(usuario);
	}

	public void setDatosEntrega(LocalDateTime fechaEntrega, LocalDateTime fechaEntregaInicio,
			LocalDateTime fechaEntregaFin) {
		this.setFechaEntrega(fechaEntrega);
		this.setFechaEntregaInicio(fechaEntregaInicio);
		this.setFechaEntregaFin(fechaEntregaFin);
	}

	public void setDatosRecogida(LocalDateTime fechaRecogida, LocalDateTime fechaRecogidaInicio,
			LocalDateTime fechaRecogidaFin) {
		this.setFechaRecogida(fechaRecogida);
		this.setFechaRecogidaInicio(fechaRecogidaInicio);
		this.setFechaRecogidaFin(fechaRecogidaFin);
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

	// TODO DATOS REPROGRAMACION

	// ---------------------------------------------------------------------------------------------------------
	public Integer getId() {
		return id;
	}

	public String getNumeroOrden() {
		return numeroOrden;
	}

	public LocalDate getFechaOrden() {
		return fechaOrden;
	}

	public String getNumeroOrdenCompra() {
		return numeroOrdenCompra;
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
	public Ciudad getCiudadDestino() {
		return ciudadDestino;
	}

	public String getDestinoCiudadNombre() {
		return destinoCiudadNombre;
	}

	public String getDestinoDireccion() {
		return destinoDireccion;
	}

	public String getDestinoIndicaciones() {
		return destinoIndicaciones;
	}

	// ---------------------------------------------------------------------------------------------------------
	public boolean isRequiereConfirmacionCitaEntrega() {
		return requiereConfirmacionCitaEntrega;
	}

	public LocalDate getFechaEntregaSugeridaMinima() {
		return fechaEntregaSugeridaMinima;
	}

	public LocalDate getFechaEntregaSugeridaMaxima() {
		return fechaEntregaSugeridaMaxima;
	}

	public LocalTime getHoraEntregaSugeridaMinima() {
		return horaEntregaSugeridaMinima;
	}

	public LocalTime getHoraEntregaSugeridaMaxima() {
		return horaEntregaSugeridaMaxima;
	}

	// ---------------------------------------------------------------------------------------------------------
	public Ciudad getCiudadOrigen() {
		return ciudadOrigen;
	}

	public String getOrigenCiudadNombre() {
		return origenCiudadNombre;
	}

	public String getOrigenDireccion() {
		return origenDireccion;
	}

	public String getOrigenIndicaciones() {
		return origenIndicaciones;
	}

	// ---------------------------------------------------------------------------------------------------------
	public boolean isRequiereConfirmacionCitaRecogida() {
		return requiereConfirmacionCitaRecogida;
	}

	public LocalDate getFechaRecogidaSugeridaMinima() {
		return fechaRecogidaSugeridaMinima;
	}

	public LocalDate getFechaRecogidaSugeridaMaxima() {
		return fechaRecogidaSugeridaMaxima;
	}

	public LocalTime getHoraRecogidaSugeridaMinima() {
		return horaRecogidaSugeridaMinima;
	}

	public LocalTime getHoraRecogidaSugeridaMaxima() {
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
	public Destinatario getDestinatario() {
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
	public Destino getDestino() {
		return destino;
	}

	public String getDestinoCodigo() {
		return destinoCodigo;
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
	public Destino getOrigen() {
		return origen;
	}

	public String getOrigenCodigo() {
		return origenCodigo;
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

	public LocalDateTime getFechaConfirmacion() {
		return fechaConfirmacion;
	}

	public String getUsuarioConfirmacion() {
		return usuarioConfirmacion;
	}

	// ---------------------------------------------------------------------------------------------------------
	public LocalDate getFechaCitaEntrega() {
		return fechaCitaEntrega;
	}

	public LocalTime getHoraCitaEntregaMinima() {
		return horaCitaEntregaMinima;
	}

	public LocalTime getHoraCitaEntregaMaxima() {
		return horaCitaEntregaMaxima;
	}

	// ---------------------------------------------------------------------------------------------------------
	public LocalDate getFechaCitaRecogida() {
		return fechaCitaRecogida;
	}

	public LocalTime getHoraCitaRecogidaMinima() {
		return horaCitaRecogidaMinima;
	}

	public LocalTime getHoraCitaRecogidaMaxima() {
		return horaCitaRecogidaMaxima;
	}

	// ---------------------------------------------------------------------------------------------------------
	public LocalDate getFechaAlistamiento() {
		return fechaAlistamiento;
	}

	public LocalTime getHoraAlistamientoMinima() {
		return horaAlistamientoMinima;
	}

	public LocalTime getHoraAlistamientoMaxima() {
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

	public LocalDateTime getFechaAceptacion() {
		return fechaAceptacion;
	}

	public String getUsuarioAceptacion() {
		return usuarioAceptacion;
	}

	// ---------------------------------------------------------------------------------------------------------
	public Integer getCorteRutaId() {
		return corteRutaId;
	}

	public LocalDateTime getFechaCorteRuta() {
		return fechaCorteRuta;
	}

	public String getUsuarioCorteRuta() {
		return usuarioCorteRuta;
	}

	// ---------------------------------------------------------------------------------------------------------
	public Integer getRutaId() {
		return rutaId;
	}

	public Integer getSecuenciaRuta() {
		return secuenciaRuta;
	}

	public Integer getNumeroCajas() {
		return numeroCajas;
	}

	public LocalDateTime getFechaAsignacionRuta() {
		return fechaAsignacionRuta;
	}

	public String getUsuarioAsignacionRuta() {
		return usuarioAsignacionRuta;
	}

	// ---------------------------------------------------------------------------------------------------------
	public LocalDateTime getFechaEntregaEstimada() {
		return fechaEntregaEstimada;
	}

	public LocalDateTime getFechaEntrega() {
		return fechaEntrega;
	}

	public LocalDateTime getFechaEntregaInicio() {
		return fechaEntregaInicio;
	}

	public LocalDateTime getFechaEntregaFin() {
		return fechaEntregaFin;
	}

	// ---------------------------------------------------------------------------------------------------------
	public LocalDateTime getFechaRecogidaEstimada() {
		return fechaRecogidaEstimada;
	}

	public LocalDateTime getFechaRecogida() {
		return fechaRecogida;
	}

	public LocalDateTime getFechaRecogidaInicio() {
		return fechaRecogidaInicio;
	}

	public LocalDateTime getFechaRecogidaFin() {
		return fechaRecogidaFin;
	}

	// ---------------------------------------------------------------------------------------------------------
	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public String getUsuarioCreacion() {
		return usuarioCreacion;
	}

	public LocalDateTime getFechaActualizacion() {
		return fechaActualizacion;
	}

	public String getUsuarioActualizacion() {
		return usuarioActualizacion;
	}

	// ---------------------------------------------------------------------------------------------------------
	public CausalAnulacion getCausalAnulacion() {
		return causalAnulacion;
	}

	public String getNotasAnulacion() {
		return notasAnulacion;
	}

	public LocalDateTime getFechaAnulacion() {
		return fechaAnulacion;
	}

	public String getUsuarioAnulacion() {
		return usuarioAnulacion;
	}

	// ---------------------------------------------------------------------------------------------------------
	public Integer getOrdenOriginalId() {
		return ordenOriginalId;
	}

	public CausalReprogramacion getCausalReprogramacion() {
		return causalReprogramacion;
	}

	public String getNotasReprogramacion() {
		return notasReprogramacion;
	}

	public LocalDateTime getFechaReprogramacion() {
		return fechaReprogramacion;
	}

	public String getUsuarioReprogramacion() {
		return usuarioReprogramacion;
	}

	// ---------------------------------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------------------------------
	protected void setNumeroOrden(String value) {
		this.numeroOrden = coalesce(value, "");
	}

	protected void setFechaOrden(LocalDate fechaOrden) {
		this.fechaOrden = fechaOrden;
	}

	protected void setNumeroOrdenCompra(String value) {
		this.numeroOrdenCompra = coalesce(value, "");
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
	protected void setCiudadDestino(Ciudad ciudadDestino) {
		this.ciudadDestino = ciudadDestino;
	}

	protected void setDestinoCiudadNombre(String value) {
		this.destinoCiudadNombre = coalesce(value, "");
	}

	protected void setDestinoDireccion(String value) {
		this.destinoDireccion = coalesce(value, "");
	}

	protected void setDestinoIndicaciones(String value) {
		this.destinoIndicaciones = coalesce(value, "");
	}

	// ---------------------------------------------------------------------------------------------------------
	public void setRequiereConfirmacionCitaEntrega(boolean requiereConfirmacionCitaEntrega) {
		this.requiereConfirmacionCitaEntrega = requiereConfirmacionCitaEntrega;
	}

	protected void setFechaEntregaSugeridaMinima(LocalDate fechaEntregaSugeridaMinima) {
		this.fechaEntregaSugeridaMinima = fechaEntregaSugeridaMinima;
	}

	protected void setFechaEntregaSugeridaMaxima(LocalDate fechaEntregaSugeridaMaxima) {
		this.fechaEntregaSugeridaMaxima = fechaEntregaSugeridaMaxima;
	}

	protected void setHoraEntregaSugeridaMinima(LocalTime horaEntregaSugeridaMinima) {
		this.horaEntregaSugeridaMinima = horaEntregaSugeridaMinima;
	}

	protected void setHoraEntregaSugeridaMaxima(LocalTime horaEntregaSugeridaMaxima) {
		this.horaEntregaSugeridaMaxima = horaEntregaSugeridaMaxima;
	}

	// ---------------------------------------------------------------------------------------------------------
	protected void setCiudadOrigen(Ciudad ciudadOrigen) {
		this.ciudadOrigen = ciudadOrigen;
	}

	protected void setOrigenCiudadNombre(String value) {
		this.origenCiudadNombre = coalesce(value, "");
	}

	protected void setOrigenDireccion(String value) {
		this.origenDireccion = coalesce(value, "");
	}

	protected void setOrigenIndicaciones(String value) {
		this.origenIndicaciones = coalesce(value, "");
	}

	// ---------------------------------------------------------------------------------------------------------
	public void setRequiereConfirmacionCitaRecogida(boolean requiereConfirmacionCitaRecogida) {
		this.requiereConfirmacionCitaRecogida = requiereConfirmacionCitaRecogida;
	}

	protected void setFechaRecogidaSugeridaMinima(LocalDate fechaRecogidaSugeridaMinima) {
		this.fechaRecogidaSugeridaMinima = fechaRecogidaSugeridaMinima;
	}

	protected void setFechaRecogidaSugeridaMaxima(LocalDate fechaRecogidaSugeridaMaxima) {
		this.fechaRecogidaSugeridaMaxima = fechaRecogidaSugeridaMaxima;
	}

	protected void setHoraRecogidaSugeridaMinima(LocalTime horaRecogidaSugeridaMinima) {
		this.horaRecogidaSugeridaMinima = horaRecogidaSugeridaMinima;
	}

	protected void setHoraRecogidaSugeridaMaxima(LocalTime horaRecogidaSugeridaMaxima) {
		this.horaRecogidaSugeridaMaxima = horaRecogidaSugeridaMaxima;
	}

	// ---------------------------------------------------------------------------------------------------------
	protected void setCanal(Canal canal) {
		this.canal = canal;
	}

	protected void setCanalCodigoAlterno(String value) {
		this.canalCodigoAlterno = coalesce(value, "");
	}

	// ---------------------------------------------------------------------------------------------------------
	protected void setDestinatario(Destinatario destinatario) {
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
	protected void setDestino(Destino destino) {
		this.destino = destino;
	}

	protected void setDestinoCodigo(String value) {
		this.destinoCodigo = coalesce(value, "");
	}

	protected void setDestinoNombre(String value) {
		this.destinoNombre = coalesce(value, "");
	}

	protected void setDestinoContacto(Contacto value) {
		this.destinoContacto = coalesce(value, new Contacto("", "", ""));
	}

	// ---------------------------------------------------------------------------------------------------------
	protected void setOrigen(Destino origen) {
		this.origen = origen;
	}

	protected void setOrigenCodigo(String value) {
		this.origenCodigo = coalesce(value, "");
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
	protected void setNotasConfirmacion(String value) {
		this.notasConfirmacion = coalesce(value, "");
	}

	protected void setFechaConfirmacion(LocalDateTime fechaConfirmacion) {
		this.fechaConfirmacion = fechaConfirmacion;
	}

	protected void setUsuarioConfirmacion(String value) {
		this.usuarioConfirmacion = coalesce(value, "");
	}

	// ---------------------------------------------------------------------------------------------------------
	protected void setFechaCitaEntrega(LocalDate fechaCitaEntrega) {
		this.fechaCitaEntrega = fechaCitaEntrega;
	}

	protected void setHoraCitaEntregaMinima(LocalTime horaCitaEntregaMinima) {
		this.horaCitaEntregaMinima = horaCitaEntregaMinima;
	}

	protected void setHoraCitaEntregaMaxima(LocalTime horaCitaEntregaMaxima) {
		this.horaCitaEntregaMaxima = horaCitaEntregaMaxima;
	}

	// ---------------------------------------------------------------------------------------------------------
	protected void setFechaCitaRecogida(LocalDate fechaCitaRecogida) {
		this.fechaCitaRecogida = fechaCitaRecogida;
	}

	protected void setHoraCitaRecogidaMinima(LocalTime horaCitaRecogidaMinima) {
		this.horaCitaRecogidaMinima = horaCitaRecogidaMinima;
	}

	protected void setHoraCitaRecogidaMaxima(LocalTime horaCitaRecogidaMaxima) {
		this.horaCitaRecogidaMaxima = horaCitaRecogidaMaxima;
	}

	// ---------------------------------------------------------------------------------------------------------
	protected void setFechaAlistamiento(LocalDate fechaAlistamiento) {
		this.fechaAlistamiento = fechaAlistamiento;
	}

	protected void setHoraAlistamientoMinima(LocalTime horaAlistamientoMinima) {
		this.horaAlistamientoMinima = horaAlistamientoMinima;
	}

	protected void setHoraAlistamientoMaxima(LocalTime horaAlistamientoMaxima) {
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
	protected void setNotasAceptacion(String value) {
		this.notasAceptacion = coalesce(value, "");
	}

	protected void setFechaAceptacion(LocalDateTime fechaAceptacion) {
		this.fechaAceptacion = fechaAceptacion;
	}

	protected void setUsuarioAceptacion(String value) {
		this.usuarioAceptacion = coalesce(value, "");
	}

	// ---------------------------------------------------------------------------------------------------------
	protected void setCorteRutaId(Integer corteRutaId) {
		this.corteRutaId = corteRutaId;
	}

	protected void setFechaCorteRuta(LocalDateTime fechaCorteRuta) {
		this.fechaCorteRuta = fechaCorteRuta;
	}

	protected void setUsuarioCorteRuta(String value) {
		this.usuarioCorteRuta = coalesce(value, "");
	}

	// ---------------------------------------------------------------------------------------------------------
	protected void setRutaId(Integer rutaId) {
		this.rutaId = rutaId;
	}

	protected void setSecuenciaRuta(Integer secuenciaRuta) {
		this.secuenciaRuta = secuenciaRuta;
	}

	protected void setNumeroCajas(Integer numeroCajas) {
		this.numeroCajas = numeroCajas;
	}

	protected void setFechaAsignacionRuta(LocalDateTime fechaAsignacionRuta) {
		this.fechaAsignacionRuta = fechaAsignacionRuta;
	}

	protected void setUsuarioAsignacionRuta(String value) {
		this.usuarioAsignacionRuta = coalesce(value, "");
	}

	// ---------------------------------------------------------------------------------------------------------
	protected void setFechaEntregaEstimada(LocalDateTime fechaEntregaEstimada) {
		this.fechaEntregaEstimada = fechaEntregaEstimada;
	}

	protected void setFechaEntrega(LocalDateTime fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	protected void setFechaEntregaInicio(LocalDateTime fechaEntregaInicio) {
		this.fechaEntregaInicio = fechaEntregaInicio;
	}

	protected void setFechaEntregaFin(LocalDateTime fechaEntregaFin) {
		this.fechaEntregaFin = fechaEntregaFin;
	}

	// ---------------------------------------------------------------------------------------------------------
	protected void setFechaRecogidaEstimada(LocalDateTime fechaRecogidaEstimada) {
		this.fechaRecogidaEstimada = fechaRecogidaEstimada;
	}

	protected void setFechaRecogida(LocalDateTime fechaRecogida) {
		this.fechaRecogida = fechaRecogida;
	}

	protected void setFechaRecogidaInicio(LocalDateTime fechaRecogidaInicio) {
		this.fechaRecogidaInicio = fechaRecogidaInicio;
	}

	protected void setFechaRecogidaFin(LocalDateTime fechaRecogidaFin) {
		this.fechaRecogidaFin = fechaRecogidaFin;
	}

	// ---------------------------------------------------------------------------------------------------------
	protected void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	protected void setUsuarioCreacion(String value) {
		this.usuarioCreacion = coalesce(value, "");
	}

	protected void setFechaActualizacion(LocalDateTime fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	protected void setUsuarioActualizacion(String value) {
		this.usuarioActualizacion = coalesce(value, "");
	}

	// ---------------------------------------------------------------------------------------------------------
	protected void setCausalAnulacion(CausalAnulacion causalAnulacion) {
		this.causalAnulacion = causalAnulacion;
	}

	protected void setNotasAnulacion(String value) {
		this.notasAnulacion = coalesce(value, "");
	}

	protected void setFechaAnulacion(LocalDateTime fechaAnulacion) {
		this.fechaAnulacion = fechaAnulacion;
	}

	protected void setUsuarioAnulacion(String value) {
		this.usuarioAnulacion = coalesce(value, "");
	}

	// ---------------------------------------------------------------------------------------------------------
	protected void setOrdenOriginalId(Integer ordenOriginalId) {
		this.ordenOriginalId = ordenOriginalId;
	}

	protected void setCausalReprogramacion(CausalReprogramacion causalReprogramacion) {
		this.causalReprogramacion = causalReprogramacion;
	}

	protected void setNotasReprogramacion(String notasReprogramacion) {
		this.notasReprogramacion = notasReprogramacion;
	}

	protected void setFechaReprogramacion(LocalDateTime fechaReprogramacion) {
		this.fechaReprogramacion = fechaReprogramacion;
	}

	protected void setUsuarioReprogramacion(String usuarioReprogramacion) {
		this.usuarioReprogramacion = usuarioReprogramacion;
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
