package com.tacticlogistics.domain.model.cpr;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tacticlogistics.domain.model.wms.Bodega;

@Entity
@Table(name = "CentrosPlaneacionRuta", catalog = "cpr")
public class CentroPlaneacionRuta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_centro_planeacion_ruta", unique = true, nullable = false)
	private Integer id;

	@Column(nullable = false, length = 20, unique = true)
	private String codigo;

	@Column(nullable = false, length = 100, unique = true)
	private String nombre;

	private boolean activo;

	@Column(nullable = false, columnDefinition = "DATETIME2(0)")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaActualizacion;

	@Column(nullable = false, length = 50)
	private String usuarioActualizacion;

	// ---------------------------------------------------------------------------------------------------------
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_centro_planeacion_ruta", referencedColumnName = "id_centro_planeacion_ruta")
	private Set<PlanificadorRuta> planificadoresRutas = new HashSet<PlanificadorRuta>(0);

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_centro_planeacion_ruta", referencedColumnName = "id_centro_planeacion_ruta")
	private Set<Bodega> bodegas = new HashSet<Bodega>(0);

	@ElementCollection
	@CollectionTable(name = "centros_planeacion_ruta_codigos_ciudades", catalog = "cpr"
		, joinColumns = @JoinColumn(name = "id_centro_planeacion_ruta", referencedColumnName = "id_centro_planeacion_ruta") )
	@Column(name = "codigo_ciudad", nullable = false, length = 20)
	private Set<String> codigosCiudades = new HashSet<String>(0);

	protected CentroPlaneacionRuta() {

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

	public Set<PlanificadorRuta> getPlanificadoresRutas() {
		return planificadoresRutas;
	}

	public void setPlanificadoresRutas(Set<PlanificadorRuta> planificadoresRutas) {
		this.planificadoresRutas = planificadoresRutas;
	}

	public Set<Bodega> getBodegas() {
		return bodegas;
	}

	public void setBodegas(Set<Bodega> bodegas) {
		this.bodegas = bodegas;
	}

	public Set<String> getCodigosCiudades() {
		return codigosCiudades;
	}

	public void setCodigosCiudades(Set<String> codigosCiudades) {
		this.codigosCiudades = codigosCiudades;
	}

	@Override
	public String toString() {
		final int maxLen = 5;
		StringBuilder builder = new StringBuilder();
		builder.append("CentroPlaneacionRuta [");
		if (id != null)
			builder.append("id=").append(id).append(", ");
		if (codigo != null)
			builder.append("codigo=").append(codigo).append(", ");
		if (nombre != null)
			builder.append("nombre=").append(nombre).append(", ");
		builder.append("activo=").append(activo).append(", ");
		if (fechaActualizacion != null)
			builder.append("fechaActualizacion=").append(fechaActualizacion).append(", ");
		if (usuarioActualizacion != null)
			builder.append("usuarioActualizacion=").append(usuarioActualizacion).append(", ");
		if (planificadoresRutas != null)
			builder.append("planificadoresRutas=").append(toString(planificadoresRutas, maxLen)).append(", ");
		if (bodegas != null)
			builder.append("bodegas=").append(toString(bodegas, maxLen)).append(", ");
		if (codigosCiudades != null)
			builder.append("codigosCiudades=").append(toString(codigosCiudades, maxLen));
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

}
