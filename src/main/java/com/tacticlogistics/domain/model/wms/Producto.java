package com.tacticlogistics.domain.model.wms;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.CascadeType;
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
import javax.persistence.UniqueConstraint;

import com.tacticlogistics.domain.model.crm.Cliente;

@Entity
@Table(name = "Productos", catalog = "wms"
, uniqueConstraints = {@UniqueConstraint(columnNames = { "id_cliente","codigo" }),@UniqueConstraint(columnNames = { "id_cliente","codigo_alterno" })} )

public class Producto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_producto", unique = true, nullable = false)
	private Integer id;

	@Column(nullable = false, length = 50)
	private String codigo;

	@Column(name = "codigo_alterno", nullable = false, length = 50)
	private String codigoAlterno;

	@Column(nullable = false, length = 20)
	private String nombre;

	@Column(nullable = false, length = 250)
	private String nombreLargo;

	private boolean existeEnWms;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cliente", nullable = false)
	private Cliente cliente;

	@OneToMany(fetch = FetchType.LAZY, mappedBy="producto", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<ProductoBodegaAssociation> productoBodegaAssociation;

	@OneToMany(fetch = FetchType.LAZY, mappedBy="producto", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<ProductoUnidadAssociation> productoUnidadAssociation;

	private boolean activo;

	@Column(nullable = false, columnDefinition = "DATETIME2(0)")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaActualizacion;

	@Column(nullable = false, length = 50)
	private String usuarioActualizacion;

	public Producto() {

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

	public String getCodigoAlterno() {
		return codigoAlterno;
	}

	public void setCodigoAlterno(String codigoAlterno) {
		this.codigoAlterno = codigoAlterno;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombreLargo() {
		return nombreLargo;
	}

	public void setNombreLargo(String nombreLargo) {
		this.nombreLargo = nombreLargo;
	}

	public boolean isExisteEnWms() {
		return existeEnWms;
	}

	public void setExisteEnWms(boolean existeWms) {
		this.existeEnWms = existeWms;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Set<ProductoBodegaAssociation> getProductoBodegaAssociation() {
		return productoBodegaAssociation;
	}

	public void setProductoBodegaAssociation(Set<ProductoBodegaAssociation> productoBodegaAssociation) {
		this.productoBodegaAssociation = productoBodegaAssociation;
	}

	public Set<ProductoUnidadAssociation> getProductoUnidadAssociation() {
		if(productoUnidadAssociation == null){
			productoUnidadAssociation = new HashSet<>();
		}
		return productoUnidadAssociation;
	}

	public void setProductoUnidadAssociation(Set<ProductoUnidadAssociation> productoUnidadAssociation) {
		this.productoUnidadAssociation = productoUnidadAssociation;
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

	@Override
	public String toString() {
		final int maxLen = 5;
		StringBuilder builder = new StringBuilder();
		builder.append("Producto [");
		if (id != null)
			builder.append("id=").append(id).append(", ");
		if (codigo != null)
			builder.append("codigo=").append(codigo).append(", ");
		if (codigoAlterno != null)
			builder.append("codigoAlterno=").append(codigoAlterno).append(", ");
		if (nombre != null)
			builder.append("nombre=").append(nombre).append(", ");
		if (nombreLargo != null)
			builder.append("nombreLargo=").append(nombreLargo).append(", ");
		builder.append("existeWms=").append(existeEnWms).append(", ");
		if (cliente != null)
			builder.append("cliente=").append(cliente).append(", ");
		if (productoBodegaAssociation != null)
			builder.append("productoBodegaAssociation=").append(toString(productoBodegaAssociation, maxLen))
					.append(", ");
		if (productoUnidadAssociation != null)
			builder.append("productoUnidadAssociation=").append(toString(productoUnidadAssociation, maxLen))
					.append(", ");
		builder.append("activo=").append(activo).append(", ");
		if (fechaActualizacion != null)
			builder.append("fechaActualizacion=").append(fechaActualizacion).append(", ");
		if (usuarioActualizacion != null)
			builder.append("usuarioActualizacion=").append(usuarioActualizacion);
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

	public void setUsuarioActualizacion(String usuarioActualizacion) {
		this.usuarioActualizacion = usuarioActualizacion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
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
		Producto other = (Producto) obj;
		if (cliente == null) {
			if (other.cliente != null)
				return false;
		} else if (!cliente.equals(other.cliente))
			return false;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}


}
