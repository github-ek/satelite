package com.tacticlogistics.domain.model.cpr;

import java.io.Serializable;
import java.sql.Time;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "CortesPlaneacionRuta", catalog = "cpr")
public class CortePlaneacionRuta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_corte_planeacion_ruta", unique = true, nullable = false)
	private Integer id;

	@Column(nullable = false, length = 200, unique = true)
	private String nombre;

	@Column(nullable = false, columnDefinition = "DATETIME2(0)")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaProgramadaSalidaRutas;

	@Column(nullable = false, columnDefinition = "TIME(0)")
	private Time horaInicio;

	@Column(nullable = false, columnDefinition = "TIME(0)")
	private Time horaFin;

	private boolean activo;

	@Column(nullable = false, columnDefinition = "DATETIME2(0)")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaActualizacion;

	@Column(nullable = false, length = 50)
	private String usuarioActualizacion;

	//---------------------------------------------------------------------------------------------------------
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_centro_planeacion_ruta", nullable = false)
	private CentroPlaneacionRuta centroPlaneacionRuta;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_planificador_ruta", nullable = false)
	private PlanificadorRuta planificadorRuta;
	
	protected CortePlaneacionRuta() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getFechaProgramadaSalidaRutas() {
		return fechaProgramadaSalidaRutas;
	}

	public void setFechaProgramadaSalidaRutas(Date fechaProgramadaSalidaRutas) {
		this.fechaProgramadaSalidaRutas = fechaProgramadaSalidaRutas;
	}

	public Time getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(Time horaInicio) {
		this.horaInicio = horaInicio;
	}

	public Time getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(Time horaFin) {
		this.horaFin = horaFin;
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

	public CentroPlaneacionRuta getCentroPlaneacionRuta() {
		return centroPlaneacionRuta;
	}

	public void setCentroPlaneacionRuta(CentroPlaneacionRuta centroPlaneacionRuta) {
		this.centroPlaneacionRuta = centroPlaneacionRuta;
	}

	public PlanificadorRuta getPlanificadorRuta() {
		return planificadorRuta;
	}

	public void setPlanificadorRuta(PlanificadorRuta planificadorRuta) {
		this.planificadorRuta = planificadorRuta;
	}

	@Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("CortePlaneacionRuta [");
        if (id != null) {
            builder.append("id=").append(id).append(", ");
        }
        if (nombre != null) {
            builder.append("nombre=").append(nombre).append(", ");
        }
        if (fechaProgramadaSalidaRutas != null) {
            builder.append("fechaProgramadaSalidaRutas=").append(fechaProgramadaSalidaRutas).append(", ");
        }
        if (horaInicio != null) {
            builder.append("horaInicio=").append(horaInicio).append(", ");
        }
        if (horaFin != null) {
            builder.append("horaFin=").append(horaFin).append(", ");
        }
        builder.append("activo=").append(activo).append(", ");
        if (fechaActualizacion != null) {
            builder.append("fechaActualizacion=").append(fechaActualizacion).append(", ");
        }
        if (usuarioActualizacion != null) {
            builder.append("usuarioActualizacion=").append(usuarioActualizacion).append(", ");
        }
        if (centroPlaneacionRuta != null) {
            builder.append("centroPlaneacionRuta=").append(centroPlaneacionRuta).append(", ");
        }
        if (planificadorRuta != null) {
            builder.append("planificadorRuta=").append(planificadorRuta);
        }
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
