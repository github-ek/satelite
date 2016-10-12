package com.tacticlogistics.presentation.wms.compras;

import java.util.ArrayList;
import java.util.List;

public class OrdenCompra {

	private int idOrden;
	private String numeroOrden;
	private String proveedor;
	private String clienteId;
	private String tipoOrden;
	private String estadoOrden;
	private String bodega ;

	private List<LineaOrdenCompra> lineas;

	public OrdenCompra() {
		super();
		lineas = new ArrayList<LineaOrdenCompra>();
	}

	public int getIdOrden() {
		return idOrden;
	}

	public void setIdOrden(int idOrden) {
		this.idOrden = idOrden;
	}

	public String getNumeroOrden() {
		return numeroOrden;
	}

	public void setNumeroOrden(String numeroOrden) {
		this.numeroOrden = numeroOrden;
	}

	public String getProveedor() {
		return proveedor;
	}

	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}

	public String getClienteId() {
		return clienteId;
	}

	public void setClienteId(String clienteId) {
		this.clienteId = clienteId;
	}

	public String getTipoOrden() {
		return tipoOrden;
	}

	public void setTipoOrden(String tipoOrden) {
		this.tipoOrden = tipoOrden;
	}

	public String getEstadoOrden() {
		return estadoOrden;
	}

	public void setEstadoOrden(String estadoOrden) {
		this.estadoOrden = estadoOrden;
	}

	public String getBodega() {
		return bodega;
	}

	public void setBodega(String bodega) {
		this.bodega = bodega;
	}

	public void setLineas(List<LineaOrdenCompra> lineas) {
		this.lineas = lineas;
	}

	public void addLinea(LineaOrdenCompra linea) {
		lineas.add(linea);
	}

	public void appendLineas(List<LineaOrdenCompra> lineas) {
		this.lineas.addAll(lineas);
	}

	public List<LineaOrdenCompra> getLineas() {
		return lineas;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrdenCompra other = (OrdenCompra) obj;
		if (bodega == null) {
			if (other.bodega != null)
				return false;
		} else if (!bodega.equals(other.bodega))
			return false;
		if (clienteId == null) {
			if (other.clienteId != null)
				return false;
		} else if (!clienteId.equals(other.clienteId))
			return false;
		if (estadoOrden == null) {
			if (other.estadoOrden != null)
				return false;
		} else if (!estadoOrden.equals(other.estadoOrden))
			return false;
		if (numeroOrden == null) {
			if (other.numeroOrden != null)
				return false;
		} else if (!numeroOrden.equals(other.numeroOrden))
			return false;
		if (proveedor == null) {
			if (other.proveedor != null)
				return false;
		} else if (!proveedor.equals(other.proveedor))
			return false;
		if (tipoOrden == null) {
			if (other.tipoOrden != null)
				return false;
		} else if (!tipoOrden.equals(other.tipoOrden))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OrdenCompra [numeroOrden=" + numeroOrden + ", proveedor=" + proveedor + ", clienteId=" + clienteId
				+ ", tipoOrden=" + tipoOrden + ", estadoOrden=" + estadoOrden + ", bodega=" + bodega + ", lineas="
				+ lineas + "]";
	}

}
