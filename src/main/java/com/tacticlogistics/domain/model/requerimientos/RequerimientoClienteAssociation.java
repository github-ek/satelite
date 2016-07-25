package com.tacticlogistics.domain.model.requerimientos;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.tacticlogistics.domain.model.crm.Cliente;
import com.tacticlogistics.domain.model.crm.TipoServicio;
import com.tacticlogistics.domain.model.requerimientos.valueobjects.OpcionesDeRequerimientoEmbeddable;
import com.tacticlogistics.domain.model.requerimientos.valueobjects.PermisosParaPersonalizarOpcionesDeRequerimientoEmbeddable;

@Entity
@Table(name = "requerimientos_clientes", catalog = "requerimientos")
public class RequerimientoClienteAssociation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_requerimiento", nullable = false)
	private Requerimiento requerimiento;
	
	@Id
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_tipo_servicio", nullable = false)
	private TipoServicio tipoServicio;

	@Id
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_cliente", nullable = false)
	private Cliente cliente;

	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
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
        RequerimientoClienteAssociation other = (RequerimientoClienteAssociation) obj;
        if (cliente == null) {
            if (other.cliente != null)
                return false;
        } else if (!cliente.equals(other.cliente))
            return false;
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

    @Embedded
	private PermisosParaPersonalizarOpcionesDeRequerimientoEmbeddable permisos;
	
	@Embedded
	private OpcionesDeRequerimientoEmbeddable opciones;

	private boolean incluirSiempre;
	
	protected RequerimientoClienteAssociation(){
		
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

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
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

	public boolean isIncluirSiempre() {
		return incluirSiempre;
	}

	public void setIncluirSiempre(boolean incluirSiempre) {
		this.incluirSiempre = incluirSiempre;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RequerimientoClienteAssociation [");
		if (requerimiento != null)
			builder.append("requerimiento=").append(requerimiento).append(", ");
		if (tipoServicio != null)
			builder.append("tipoServicio=").append(tipoServicio).append(", ");
		if (cliente != null)
			builder.append("cliente=").append(cliente).append(", ");
		if (permisos != null)
			builder.append("permisos=").append(permisos).append(", ");
		if (opciones != null)
			builder.append("opciones=").append(opciones).append(", ");
		builder.append("incluirSiempre=").append(incluirSiempre).append("]");
		return builder.toString();
	}

	
}
