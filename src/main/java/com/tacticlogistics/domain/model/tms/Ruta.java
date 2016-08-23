package com.tacticlogistics.domain.model.cpr;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tacticlogistics.domain.model.common.valueobjects.UbicacionEmbeddable;
import com.tacticlogistics.domain.model.tms.Conductor;
import com.tacticlogistics.domain.model.tms.Transportadora;
import com.tacticlogistics.domain.model.tms.Vehiculo;

@Entity
@Table(name = "Rutas", catalog = "cpr")
public class Ruta implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_ruta", unique = true, nullable = false)
	private Integer id;

	@Column(nullable = false, length = 20)
	private String placa;
	
	@Column(nullable = false, length = 20)
	private String placaRemolque;
	
	@Column(nullable = false, length = 20)
	private String numeroContenedor;
	
	@Column(nullable = false, length = 20)
	private String codigoDispositivoMovilSeguimiento;
	
	@Column(nullable = false, columnDefinition = "DATETIME2(0)")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaAlistamiento;

	private boolean activo;

	@Column(nullable = false, columnDefinition = "DATETIME2(0)")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaActualizacion;

	@Column(nullable = false, length = 50)
	private String usuarioActualizacion;

	//---------------------------------------------------------------------------------------------------------
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_corte_planeacion_ruta", nullable = true)
	private CortePlaneacionRuta corte;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_transportadora", nullable = true)
	private Transportadora transportadora;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_vehiculo", nullable = true)
	private Vehiculo vehiculo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_conductor", nullable = true)
	private Conductor conductor;

	@Embedded
	private UbicacionEmbeddable origen;

	protected Ruta() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getPlacaRemolque() {
		return placaRemolque;
	}

	public void setPlacaRemolque(String placaRemolque) {
		this.placaRemolque = placaRemolque;
	}

	public String getNumeroContenedor() {
		return numeroContenedor;
	}

	public void setNumeroContenedor(String numeroContenedor) {
		this.numeroContenedor = numeroContenedor;
	}

	public String getCodigoDispositivoMovilSeguimiento() {
		return codigoDispositivoMovilSeguimiento;
	}

	public void setCodigoDispositivoMovilSeguimiento(String codigoDispositivoMovilSeguimiento) {
		this.codigoDispositivoMovilSeguimiento = codigoDispositivoMovilSeguimiento;
	}

	public Date getFechaAlistamiento() {
		return fechaAlistamiento;
	}

	public void setFechaAlistamiento(Date fechaAlistamiento) {
		this.fechaAlistamiento = fechaAlistamiento;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
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

	public CortePlaneacionRuta getCorte() {
		return corte;
	}

	public void setCorte(CortePlaneacionRuta corte) {
		this.corte = corte;
	}

	public Transportadora getTransportadora() {
		return transportadora;
	}

	public void setTransportadora(Transportadora transportadora) {
		this.transportadora = transportadora;
	}

	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}

	public Conductor getConductor() {
		return conductor;
	}

	public void setConductor(Conductor conductor) {
		this.conductor = conductor;
	}

	public UbicacionEmbeddable getOrigen() {
		return origen;
	}

	public void setOrigen(UbicacionEmbeddable origen) {
		this.origen = origen;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Ruta [");
		if (id != null)
			builder.append("id=").append(id).append(", ");
		if (placa != null)
			builder.append("placa=").append(placa).append(", ");
		if (placaRemolque != null)
			builder.append("placaRemolque=").append(placaRemolque).append(", ");
		if (numeroContenedor != null)
			builder.append("numeroContenedor=").append(numeroContenedor).append(", ");
		if (codigoDispositivoMovilSeguimiento != null)
			builder.append("codigoDispositivoMovilSeguimiento=").append(codigoDispositivoMovilSeguimiento).append(", ");
		if (fechaAlistamiento != null)
			builder.append("fechaAlistamiento=").append(fechaAlistamiento).append(", ");
		builder.append("activo=").append(activo).append(", ");
		if (fechaActualizacion != null)
			builder.append("fechaActualizacion=").append(fechaActualizacion).append(", ");
		if (usuarioActualizacion != null)
			builder.append("usuarioActualizacion=").append(usuarioActualizacion).append(", ");
		if (corte != null)
			builder.append("corte=").append(corte).append(", ");
		if (transportadora != null)
			builder.append("transportadora=").append(transportadora).append(", ");
		if (vehiculo != null)
			builder.append("vehiculo=").append(vehiculo).append(", ");
		if (conductor != null)
			builder.append("conductor=").append(conductor).append(", ");
		if (origen != null)
			builder.append("origen=").append(origen);
		builder.append("]");
		return builder.toString();
	}
	
	
}