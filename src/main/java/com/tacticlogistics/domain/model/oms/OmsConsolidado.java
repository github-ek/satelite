package com.tacticlogistics.domain.model.oms;

import java.io.Serializable;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tacticlogistics.domain.model.crm.Cliente;

@Entity
@Table(name = "Consolidados", catalog = "oms")
public class OmsConsolidado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_consolidado", unique = true, nullable = false)
	private Integer id;

	// TODO HACER UNICO
	@Column(nullable = false, length = 30)
	private String numeroDocumentoConsolidadoCliente;

	@Column(nullable = false, columnDefinition = "DATETIME2(0)")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaActualizacion;

	@Column(nullable = false, length = 50)
	private String usuarioActualizacion;

	// ---------------------------------------------------------------------------------------------------------
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_cliente", nullable = false)
	private Cliente cliente;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_consolidado", referencedColumnName = "id_consolidado", nullable = true)
	private Set<OmsOrden> ordenes = new HashSet<OmsOrden>(0);

	protected OmsConsolidado() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNumeroDocumentoConsolidadoCliente() {
		return numeroDocumentoConsolidadoCliente;
	}

	public void setNumeroDocumentoConsolidadoCliente(String numeroDocumentoConsolidadoCliente) {
		this.numeroDocumentoConsolidadoCliente = numeroDocumentoConsolidadoCliente;
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

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Set<OmsOrden> getOrdenes() {
		return ordenes;
	}

	public void setOrdenes(Set<OmsOrden> ordenes) {
		this.ordenes = ordenes;
	}

	@Override
	public String toString() {
		final int maxLen = 5;
		StringBuilder builder = new StringBuilder();
		builder.append("Consolidado [");
		if (id != null)
			builder.append("id=").append(id).append(", ");
		if (numeroDocumentoConsolidadoCliente != null)
			builder.append("numeroDocumentoConsolidadoCliente=").append(numeroDocumentoConsolidadoCliente).append(", ");
		if (fechaActualizacion != null)
			builder.append("fechaActualizacion=").append(fechaActualizacion).append(", ");
		if (usuarioActualizacion != null)
			builder.append("usuarioActualizacion=").append(usuarioActualizacion).append(", ");
		if (cliente != null)
			builder.append("cliente=").append(cliente).append(", ");
		if (ordenes != null)
			builder.append("ordenes=").append(toString(ordenes, maxLen));
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
