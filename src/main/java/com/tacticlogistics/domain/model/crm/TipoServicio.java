package com.tacticlogistics.domain.model.crm;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
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

import com.tacticlogistics.domain.model.requerimientos.RequerimientoClienteAssociation;
import com.tacticlogistics.domain.model.requerimientos.RequerimientoTipoServicioAssociation;

@Entity
@Table(name = "TiposServicios", catalog = "crm")
public class TipoServicio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_tipo_servicio", nullable = false, unique = true)
	private Integer id;

	@Column(nullable = false, length = 20, unique = true)
	private String codigo;

	@Column(nullable = false, length = 100, unique = true)
	private String nombre;

	@Column(nullable = false, length = 200)
	private String descripcion;

	private boolean admiteBodegasComoDestino;

	private boolean admiteBodegasComoOrigen;

	private boolean registrarDestinoEnLaOrden;

	private boolean admiteProductosPorLinea;

	private boolean admitePaquetesPorLinea;

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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tipoServicio")
	private Set<RequerimientoTipoServicioAssociation> requerimientoTipoServicioAssociation = new HashSet<RequerimientoTipoServicioAssociation>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tipoServicio")
	private Set<RequerimientoClienteAssociation> requerimientoClienteAssociation = new HashSet<RequerimientoClienteAssociation>();

	public TipoServicio() {

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

	public boolean isAdmiteBodegasComoDestino() {
		return admiteBodegasComoDestino;
	}

	public void setAdmiteBodegasComoDestino(boolean admiteBodegasComoDestino) {
		this.admiteBodegasComoDestino = admiteBodegasComoDestino;
	}

	public boolean isAdmiteBodegasComoOrigen() {
		return admiteBodegasComoOrigen;
	}

	public void setAdmiteBodegasComoOrigen(boolean admiteBodegasComoOrigen) {
		this.admiteBodegasComoOrigen = admiteBodegasComoOrigen;
	}

	public boolean isRegistrarDestinoEnLaOrden() {
		return registrarDestinoEnLaOrden;
	}

	public void setRegistrarDestinoEnLaOrden(boolean registrarDestinoEnLaOrden) {
		this.registrarDestinoEnLaOrden = registrarDestinoEnLaOrden;
	}

	public boolean isAdmiteProductosPorLinea() {
		return admiteProductosPorLinea;
	}

	public void setAdmiteProductosPorLinea(boolean admiteProductosPorLinea) {
		this.admiteProductosPorLinea = admiteProductosPorLinea;
	}

	public boolean isAdmitePaquetesPorLinea() {
		return admitePaquetesPorLinea;
	}

	public void setAdmitePaquetesPorLinea(boolean admitePaquetesPorLinea) {
		this.admitePaquetesPorLinea = admitePaquetesPorLinea;
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

	public Set<RequerimientoTipoServicioAssociation> getRequerimientoTipoServicioAssociation() {
		return requerimientoTipoServicioAssociation;
	}

	public void setRequerimientoTipoServicioAssociation(
			Set<RequerimientoTipoServicioAssociation> requerimientoTipoServicioAssociation) {
		this.requerimientoTipoServicioAssociation = requerimientoTipoServicioAssociation;
	}

	public Set<RequerimientoClienteAssociation> getRequerimientoClienteAssociation() {
		return requerimientoClienteAssociation;
	}

	public void setRequerimientoClienteAssociation(
			Set<RequerimientoClienteAssociation> requerimientoClienteAssociation) {
		this.requerimientoClienteAssociation = requerimientoClienteAssociation;
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
		TipoServicio other = (TipoServicio) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TipoServicio [");
		if (id != null)
			builder.append("id=").append(id).append(", ");
		if (codigo != null)
			builder.append("codigo=").append(codigo).append(", ");
		if (nombre != null)
			builder.append("nombre=").append(nombre).append(", ");
		if (tipoDistribucion != null)
			builder.append("tipoDistribucion=").append(tipoDistribucion);
		builder.append("]");
		return builder.toString();
	}
}
