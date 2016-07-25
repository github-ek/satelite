package com.tacticlogistics.domain.model.tms;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tacticlogistics.domain.model.wms.Bodega;

@Entity
@Table(name = "Vehiculos", catalog = "tms")
public class Vehiculo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_vehiculo", unique = true, nullable = false)
	private Integer id;

	@Column(nullable = false, length = 20)
	private String placa;

	private boolean activo;
	
	@Column(nullable = false, columnDefinition = "DATETIME2(0)")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaActualizacion;

	@Column(nullable = false, length = 50)
	private String usuarioActualizacion;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_dispositivo_movil_seguimiento", nullable = false)
	private DispositivoMovilSeguimiento dispositivoMovilSeguimiento;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_conductor", nullable = false)
	private Conductor conductor;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_tipo_vehiculo", nullable = false)
	private TipoVehiculo tipoVehiculo;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_transportadora", nullable = false)
	private Transportadora transportadora;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_bodega", nullable = false)
	private Bodega bodega;

	protected Vehiculo() {

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

	public DispositivoMovilSeguimiento getDispositivoMovilSeguimiento() {
		return dispositivoMovilSeguimiento;
	}

	public void setDispositivoMovilSeguimiento(DispositivoMovilSeguimiento dispositivoMovilSeguimiento) {
		this.dispositivoMovilSeguimiento = dispositivoMovilSeguimiento;
	}

	public Conductor getConductor() {
		return conductor;
	}

	public void setConductor(Conductor conductor) {
		this.conductor = conductor;
	}

	public TipoVehiculo getTipoVehiculo() {
		return tipoVehiculo;
	}

	public void setTipoVehiculo(TipoVehiculo tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
	}

	public Transportadora getTransportadora() {
		return transportadora;
	}

	public void setTransportadora(Transportadora transportadora) {
		this.transportadora = transportadora;
	}

	public Bodega getBodega() {
		return bodega;
	}

	public void setBodega(Bodega bodega) {
		this.bodega = bodega;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Vehiculo [");
		if (id != null)
			builder.append("id=").append(id).append(", ");
		if (placa != null)
			builder.append("placa=").append(placa).append(", ");
		builder.append("activo=").append(activo).append(", ");
		if (fechaActualizacion != null)
			builder.append("fechaActualizacion=").append(fechaActualizacion).append(", ");
		if (usuarioActualizacion != null)
			builder.append("usuarioActualizacion=").append(usuarioActualizacion).append(", ");
		if (dispositivoMovilSeguimiento != null)
			builder.append("dispositivoMovilSeguimiento=").append(dispositivoMovilSeguimiento).append(", ");
		if (conductor != null)
			builder.append("conductor=").append(conductor).append(", ");
		if (tipoVehiculo != null)
			builder.append("tipoVehiculo=").append(tipoVehiculo).append(", ");
		if (transportadora != null)
			builder.append("transportadora=").append(transportadora).append(", ");
		if (bodega != null)
			builder.append("bodega=").append(bodega);
		builder.append("]");
		return builder.toString();
	}
}
