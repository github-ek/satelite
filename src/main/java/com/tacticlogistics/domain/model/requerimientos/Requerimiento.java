package com.tacticlogistics.domain.model.requerimientos;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.Column;
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

import com.tacticlogistics.domain.model.requerimientos.valueobjects.OpcionesDeRequerimientoEmbeddable;

@Entity
@Table(name = "requerimientos", catalog = "requerimientos")
public class Requerimiento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_requerimiento", unique = true, nullable = false)
	private Integer id;

	@Column(nullable = false, length = 20, unique = true)
	private String codigo;

	@Column(nullable = false, length = 100, unique = true)
	private String nombre;

	@Column(nullable = false, length = 200)
	private String descripcion;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_grupo_requerimiento", nullable = false)
	private GrupoRequerimiento grupoRequerimiento;

	@Enumerated(EnumType.STRING)
	@Column(name = "id_tipo_requerimiento", nullable = false, length = 20)
	private RequerimientoType tipoRequerimiento;

	@Column(nullable = false, length = 200)
	private String descripcionDeRequisitos; 

	//--------------------------------------------------------------------------------------------------------------------------------------
	private boolean aplicaSoloAlNIvelDeOrden;

	private boolean soloPermitirUnaUnicaOcurrenciaPorOrdenOPorLinea;

	//--------------------------------------------------------------------------------------------------------------------------------------
	@Embedded
	private OpcionesDeRequerimientoEmbeddable opciones;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "requerimiento")
	private Set<RequerimientoTipoServicioAssociation> requerimientoTipoServicioAssociation = new HashSet<RequerimientoTipoServicioAssociation>(0);

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "requerimiento")
	private Set<RequerimientoClienteAssociation> requerimientoClienteAssociation = new HashSet<RequerimientoClienteAssociation>(0);

	//--------------------------------------------------------------------------------------------------------------------------------------
	private boolean activo;

	@Column(nullable = false, columnDefinition = "DATETIME2(0)")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaActualizacion;

	@Column(nullable = false, length = 50)
	private String usuarioActualizacion;

	protected Requerimiento() {

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

	public GrupoRequerimiento getGrupoRequerimiento() {
		return grupoRequerimiento;
	}

	public void setGrupoRequerimiento(GrupoRequerimiento grupoRequerimiento) {
		this.grupoRequerimiento = grupoRequerimiento;
	}

	public RequerimientoType getTipoRequerimiento() {
		return tipoRequerimiento;
	}

	public void setTipoRequerimiento(RequerimientoType tipoRequerimiento) {
		this.tipoRequerimiento = tipoRequerimiento;
	}

	public String getDescripcionDeRequisitos() {
		return descripcionDeRequisitos;
	}

	public void setDescripcionDeRequisitos(String descripcionDeRequisitos) {
		this.descripcionDeRequisitos = descripcionDeRequisitos;
	}

	public boolean isAplicaSoloAlNIvelDeOrden() {
		return aplicaSoloAlNIvelDeOrden;
	}

	public void setAplicaSoloAlNIvelDeOrden(boolean aplicaSoloAlNIvelDeOrden) {
		this.aplicaSoloAlNIvelDeOrden = aplicaSoloAlNIvelDeOrden;
	}

	public boolean isSoloPermitirUnaUnicaOcurrenciaPorOrdenOPorLinea() {
		return soloPermitirUnaUnicaOcurrenciaPorOrdenOPorLinea;
	}

	public void setSoloPermitirUnaUnicaOcurrenciaPorOrdenOPorLinea(
			boolean soloPermitirUnaUnicaOcurrenciaPorOrdenOPorLinea) {
		this.soloPermitirUnaUnicaOcurrenciaPorOrdenOPorLinea = soloPermitirUnaUnicaOcurrenciaPorOrdenOPorLinea;
	}

	public OpcionesDeRequerimientoEmbeddable getOpciones() {
		return opciones;
	}

	public void setOpciones(OpcionesDeRequerimientoEmbeddable opciones) {
		this.opciones = opciones;
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

	public void setRequerimientoClienteAssociation(Set<RequerimientoClienteAssociation> requerimientoClienteAssociation) {
		this.requerimientoClienteAssociation = requerimientoClienteAssociation;
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

	@Override
	public String toString() {
		final int maxLen = 5;
		StringBuilder builder = new StringBuilder();
		builder.append("Requerimiento [");
		if (id != null)
			builder.append("id=").append(id).append(", ");
		if (codigo != null)
			builder.append("codigo=").append(codigo).append(", ");
		if (nombre != null)
			builder.append("nombre=").append(nombre).append(", ");
		if (descripcion != null)
			builder.append("descripcion=").append(descripcion).append(", ");
		if (grupoRequerimiento != null)
			builder.append("grupoRequerimiento=").append(grupoRequerimiento).append(", ");
		if (tipoRequerimiento != null)
			builder.append("tipoRequerimiento=").append(tipoRequerimiento).append(", ");
		if (descripcionDeRequisitos != null)
			builder.append("descripcionDeRequisitos=").append(descripcionDeRequisitos).append(", ");
		builder.append("aplicaSoloAlNIvelDeOrden=").append(aplicaSoloAlNIvelDeOrden)
				.append(", soloPermitirUnaUnicaOcurrenciaPorOrdenOPorLinea=")
				.append(soloPermitirUnaUnicaOcurrenciaPorOrdenOPorLinea).append(", ");
		if (opciones != null)
			builder.append("opciones=").append(opciones).append(", ");
		if (requerimientoTipoServicioAssociation != null)
			builder.append("requerimientoTipoServicioAssociation=")
					.append(toString(requerimientoTipoServicioAssociation, maxLen)).append(", ");
		if (requerimientoClienteAssociation != null)
			builder.append("requerimientoClienteAssociation=").append(toString(requerimientoClienteAssociation, maxLen))
					.append(", ");
		builder.append("activo=").append(activo).append(", ");
		if (fechaActualizacion != null)
			builder.append("fechaActualizacion=").append(fechaActualizacion).append(", ");
		if (usuarioActualizacion != null)
			builder.append("usuarioActualizacion=").append(usuarioActualizacion);
		builder.append("]");
		return builder.toString();
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
		Requerimiento other = (Requerimiento) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
}
