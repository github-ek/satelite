package com.tacticlogistics.domain.model.crm;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "servicios", catalog = "crm")
public class Servicio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_servicio", nullable = false, unique = true)
	private Integer id;

	@Column(nullable = false, length = 20, unique = true)
	private String codigo;

	@Column(nullable = false, length = 100, unique = true)
	private String nombre;

	@Column(nullable = false, length = 200)
	private String descripcion;

	private boolean admiteBodegasComoOrigen;

	private boolean admiteBodegasComoDestino;

	private boolean admiteDireccionesComoOrigen;

	private boolean admiteDireccionesComoDestino;

	private int ordinal;

	private boolean activo;

	@Column(nullable = false, columnDefinition = "DATETIME2(0)")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaActualizacion;

	@Column(nullable = false, length = 50)
	private String usuarioActualizacion;

	// ---------------------------------------------------------------------------------------------------------
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "id_tipo_distribucion", nullable = true)
	private TipoDistribucion tipoDistribucion;

	public Servicio() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public boolean isAdmiteBodegasComoOrigen() {
		return admiteBodegasComoOrigen;
	}

	protected void setAdmiteBodegasComoOrigen(boolean admiteBodegasComoOrigen) {
		this.admiteBodegasComoOrigen = admiteBodegasComoOrigen;
	}

	public boolean isAdmiteBodegasComoDestino() {
		return admiteBodegasComoDestino;
	}

	protected void setAdmiteBodegasComoDestino(boolean admiteBodegasComoDestino) {
		this.admiteBodegasComoDestino = admiteBodegasComoDestino;
	}

	public boolean isAdmiteDireccionesComoOrigen() {
		return admiteDireccionesComoOrigen;
	}

	protected void setAdmiteDireccionesComoOrigen(boolean admiteDireccionesComoOrigen) {
		this.admiteDireccionesComoOrigen = admiteDireccionesComoOrigen;
	}

	public boolean isAdmiteDireccionesComoDestino() {
		return admiteDireccionesComoDestino;
	}

	protected void setAdmiteDireccionesComoDestino(boolean admiteDireccionesComoDestino) {
		this.admiteDireccionesComoDestino = admiteDireccionesComoDestino;
	}

	public int getOrdinal() {
		return ordinal;
	}

	public void setOrdinal(int ordinal) {
		this.ordinal = ordinal;
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

	public TipoDistribucion getTipoDistribucion() {
		return tipoDistribucion;
	}

	public void setTipoDistribucion(TipoDistribucion tipoDistribucion) {
		this.tipoDistribucion = tipoDistribucion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
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
		Servicio other = (Servicio) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Servicio [id=" + id + ", codigo=" + codigo + ", nombre=" + nombre + ", descripcion=" + descripcion
				+ ", admiteBodegasComoOrigen=" + admiteBodegasComoOrigen + ", admiteBodegasComoDestino="
				+ admiteBodegasComoDestino + ", admiteDireccionesComoOrigen=" + admiteDireccionesComoOrigen
				+ ", admiteDireccionesComoDestino=" + admiteDireccionesComoDestino + ", ordinal=" + ordinal
				+ ", activo=" + activo + ", fechaActualizacion=" + fechaActualizacion + ", usuarioActualizacion="
				+ usuarioActualizacion + ", tipoDistribucion=" + tipoDistribucion + "]";
	}
}
