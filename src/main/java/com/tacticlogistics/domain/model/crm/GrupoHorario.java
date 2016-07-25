package com.tacticlogistics.domain.model.crm;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
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

import com.tacticlogistics.domain.model.common.valueobjects.IntervaloDeHoras;

@Entity
@Table(name = "GruposHorario", catalog = "crm")
public class GrupoHorario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_grupo_horario", unique = true, nullable = false)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_cliente", nullable = false)
	private Cliente cliente;

	@Column(nullable = false, length = 100, unique = true)
	private String nombre;

	@Column(nullable = false, length = 200)
	private String descripcion;

	@Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "horaMinima", column = @Column(name = "hora_minima", nullable = false, columnDefinition = "TIME(0)")),
        @AttributeOverride(name = "horaMaxima", column = @Column(name = "hora_maxima", nullable = false, columnDefinition = "TIME(0)")) })
	private IntervaloDeHoras intervalo;

	private boolean aplicaLunes;
	private boolean aplicaMartes;
	private boolean aplicaMiercoles;
	private boolean aplicaJueves;
	private boolean aplicaViernes;
	private boolean aplicaSabado;
	private boolean aplicaDomingo;
	private boolean aplicaFestivos;

	private int ordinal;

	private boolean activo;

	@Column(nullable = false, columnDefinition = "DATETIME2(0)")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaActualizacion;

	@Column(nullable = false, length = 50)
	private String usuarioActualizacion;

	protected GrupoHorario() {
		super();
	}

	// ---------------------------------------------------------------------------------------------------------
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
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

	public Time getHoraMinima() {
		return intervalo.getHoraMinima();
	}

	public void setHoraMinima(Time horaMinima) {
		//intervalo.setHoraMinima(horaMinima);
	}

	public Time getHoraMaxima() {
		return intervalo.getHoraMaxima();
	}

	public void setHoraMaxima(Time horaMaxima) {
	    //intervalo.setHoraMaxima(horaMaxima);
	}

	public boolean isAplicaLunes() {
		return aplicaLunes;
	}

	public void setAplicaLunes(boolean aplicaLunes) {
		this.aplicaLunes = aplicaLunes;
	}

	public boolean isAplicaMartes() {
		return aplicaMartes;
	}

	public void setAplicaMartes(boolean aplicaMartes) {
		this.aplicaMartes = aplicaMartes;
	}

	public boolean isAplicaMiercoles() {
		return aplicaMiercoles;
	}

	public void setAplicaMiercoles(boolean aplicaMiercoles) {
		this.aplicaMiercoles = aplicaMiercoles;
	}

	public boolean isAplicaJueves() {
		return aplicaJueves;
	}

	public void setAplicaJueves(boolean aplicaJueves) {
		this.aplicaJueves = aplicaJueves;
	}

	public boolean isAplicaViernes() {
		return aplicaViernes;
	}

	public void setAplicaViernes(boolean aplicaViernes) {
		this.aplicaViernes = aplicaViernes;
	}

	public boolean isAplicaSabado() {
		return aplicaSabado;
	}

	public void setAplicaSabado(boolean aplicaSabado) {
		this.aplicaSabado = aplicaSabado;
	}

	public boolean isAplicaDomingo() {
		return aplicaDomingo;
	}

	public void setAplicaDomingo(boolean aplicaDomingo) {
		this.aplicaDomingo = aplicaDomingo;
	}

	public boolean isAplicaFestivos() {
		return aplicaFestivos;
	}

	public void setAplicaFestivos(boolean aplicaFestivos) {
		this.aplicaFestivos = aplicaFestivos;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
		result = prime * result + ((intervalo == null) ? 0 : intervalo.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
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
		GrupoHorario other = (GrupoHorario) obj;
		if (cliente == null) {
			if (other.cliente != null)
				return false;
		} else if (!cliente.equals(other.cliente))
			return false;
		if (intervalo == null) {
			if (other.intervalo != null)
				return false;
		} else if (!intervalo.equals(other.intervalo))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}

	// ---------------------------------------------------------------------------------------------------------
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GrupoHorario [");
		if (id != null)
			builder.append("id=").append(id).append(", ");
		if (cliente != null)
			builder.append("cliente=").append(cliente).append(", ");
		if (nombre != null)
			builder.append("nombre=").append(nombre).append(", ");
		if (intervalo != null)
			builder.append("intervalo=").append(intervalo).append(", ");
		builder.append("aplicaLunes=").append(aplicaLunes).append(", aplicaMartes=").append(aplicaMartes)
				.append(", aplicaMiercoles=").append(aplicaMiercoles).append(", aplicaJueves=").append(aplicaJueves)
				.append(", aplicaViernes=").append(aplicaViernes).append(", aplicaSabado=").append(aplicaSabado)
				.append(", aplicaDomingo=").append(aplicaDomingo).append(", aplicaFestivos=").append(aplicaFestivos)
				.append("]");
		return builder.toString();
	}
}
