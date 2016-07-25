package com.tacticlogistics.domain.model.requerimientos;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.tacticlogistics.domain.model.crm.TipoServicio;
import com.tacticlogistics.domain.model.requerimientos.valueobjects.OpcionesDeRequerimientoEmbeddable;
import com.tacticlogistics.domain.model.requerimientos.valueobjects.PermisosParaPersonalizarOpcionesDeRequerimientoEmbeddable;

@Entity
@Table(name = "requerimientos_tipos_servicios", catalog = "requerimientos")
public class RequerimientoTipoServicioAssociation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_requerimiento", nullable = false)
	private Requerimiento requerimiento;
	
	@Id
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_tipo_servicio", nullable = false)
	private TipoServicio tipoServicio;

	@Embedded
	private PermisosParaPersonalizarOpcionesDeRequerimientoEmbeddable permisos;
	
	@Embedded
	private OpcionesDeRequerimientoEmbeddable opciones;

	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((requerimiento == null) ? 0 : requerimiento.hashCode());
        result = prime * result + ((tipoServicio == null) ? 0 : tipoServicio.hashCode());
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
        RequerimientoTipoServicioAssociation other = (RequerimientoTipoServicioAssociation) obj;
        if (requerimiento == null) {
            if (other.requerimiento != null)
                return false;
        } else if (!requerimiento.equals(other.requerimiento))
            return false;
        if (tipoServicio == null) {
            if (other.tipoServicio != null)
                return false;
        } else if (!tipoServicio.equals(other.tipoServicio))
            return false;
        return true;
    }

    private int ordinal;
	
	protected RequerimientoTipoServicioAssociation(){
		
	}

	public Requerimiento getRequerimiento() {
		return requerimiento;
	}

	public void setRequerimiento(Requerimiento requerimiento) {
		this.requerimiento = requerimiento;
	}

	public TipoServicio getTipoServicio() {
		return tipoServicio;
	}

	public void setTipoServicio(TipoServicio tipoServicio) {
		this.tipoServicio = tipoServicio;
	}

	public PermisosParaPersonalizarOpcionesDeRequerimientoEmbeddable getPermisos() {
		return permisos;
	}

	public void setPermisos(PermisosParaPersonalizarOpcionesDeRequerimientoEmbeddable permisos) {
		this.permisos = permisos;
	}

	public OpcionesDeRequerimientoEmbeddable getOpciones() {
		return opciones;
	}

	public void setOpciones(OpcionesDeRequerimientoEmbeddable opciones) {
		this.opciones = opciones;
	}

	public int getOrdinal() {
		return ordinal;
	}

	public void setOrdinal(int ordinal) {
		this.ordinal = ordinal;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RequerimientoTipoServicioAssociation [");
		if (requerimiento != null)
			builder.append("requerimiento=").append(requerimiento).append(", ");
		if (tipoServicio != null)
			builder.append("tipoServicio=").append(tipoServicio).append(", ");
		if (permisos != null)
			builder.append("permisos=").append(permisos).append(", ");
		if (opciones != null)
			builder.append("opciones=").append(opciones).append(", ");
		builder.append("ordinal=").append(ordinal).append("]");
		return builder.toString();
	}
}
