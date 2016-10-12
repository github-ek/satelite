package com.tacticlogistics.jda.wms.tasks.despachos;

import java.util.ArrayList;
import java.util.List;

public class OrdenDespacho {

	private int idOrden;
	private String numeroOrden;
	private String tipoOrden;
	private String direccionDespacho;
	private String direccionDespachoId;
	private String direccionEnvio;
	private String direccionEnvioId;
	private String direccionFacturacion;
	private String direccionFacturacionId;
	private String codigoCliente;
	private String estadoOrden;
	private String bodega;

	private List<LineaOrdenDespacho> lineas;

	public OrdenDespacho() {
		super();
		lineas = new ArrayList<LineaOrdenDespacho>();
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

	public String getTipoOrden() {
		return tipoOrden;
	}

	public void setTipoOrden(String tipoOrden) {
		this.tipoOrden = tipoOrden;
	}

	public String getDireccionDespacho() {
		return direccionDespacho;
	}

	public void setDireccionDespacho(String direccionDespacho) {
		this.direccionDespacho = direccionDespacho;
	}

	public String getDireccionDespachoId() {
		return direccionDespachoId;
	}

	public void setDireccionDespachoId(String direccionDespachoId) {
		this.direccionDespachoId = direccionDespachoId;
	}

	public String getDireccionEnvio() {
		return direccionEnvio;
	}

	public void setDireccionEnvio(String direccionEnvio) {
		this.direccionEnvio = direccionEnvio;
	}

	public String getDireccionEnvioId() {
		return direccionEnvioId;
	}

	public void setDireccionEnvioId(String direccionEnvioId) {
		this.direccionEnvioId = direccionEnvioId;
	}

	public String getDireccionFacturacion() {
		return direccionFacturacion;
	}

	public void setDireccionFacturacion(String direccionFacturacion) {
		this.direccionFacturacion = direccionFacturacion;
	}

	public String getDireccionFacturacionId() {
		return direccionFacturacionId;
	}

	public void setDireccionFacturacionId(String direccionFacturacionId) {
		this.direccionFacturacionId = direccionFacturacionId;
	}

	public String getCodigoCliente() {
		return codigoCliente;
	}

	public void setCodigoCliente(String clienteId) {
		this.codigoCliente = clienteId;
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

	public List<LineaOrdenDespacho> getLineas() {
		return lineas;
	}
	
	public void appendLineas(List<LineaOrdenDespacho> lineas){
		this.lineas.addAll(lineas);
	}

	public void setLineas(List<LineaOrdenDespacho> lineas) {
		this.lineas = lineas;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrdenDespacho other = (OrdenDespacho) obj;
		if (bodega == null) {
			if (other.bodega != null)
				return false;
		} else if (!bodega.equals(other.bodega))
			return false;
		if (codigoCliente == null) {
			if (other.codigoCliente != null)
				return false;
		} else if (!codigoCliente.equals(other.codigoCliente))
			return false;
		if (direccionDespacho == null) {
			if (other.direccionDespacho != null)
				return false;
		} else if (!direccionDespacho.equals(other.direccionDespacho))
			return false;
		if (direccionDespachoId == null) {
			if (other.direccionDespachoId != null)
				return false;
		} else if (!direccionDespachoId.equals(other.direccionDespachoId))
			return false;
		if (direccionEnvio == null) {
			if (other.direccionEnvio != null)
				return false;
		} else if (!direccionEnvio.equals(other.direccionEnvio))
			return false;
		if (direccionEnvioId == null) {
			if (other.direccionEnvioId != null)
				return false;
		} else if (!direccionEnvioId.equals(other.direccionEnvioId))
			return false;
		if (direccionFacturacion == null) {
			if (other.direccionFacturacion != null)
				return false;
		} else if (!direccionFacturacion.equals(other.direccionFacturacion))
			return false;
		if (direccionFacturacionId == null) {
			if (other.direccionFacturacionId != null)
				return false;
		} else if (!direccionFacturacionId.equals(other.direccionFacturacionId))
			return false;
		if (estadoOrden == null) {
			if (other.estadoOrden != null)
				return false;
		} else if (!estadoOrden.equals(other.estadoOrden))
			return false;
		if (idOrden != other.idOrden)
			return false;
		if (numeroOrden == null) {
			if (other.numeroOrden != null)
				return false;
		} else if (!numeroOrden.equals(other.numeroOrden))
			return false;
		if (tipoOrden == null) {
			if (other.tipoOrden != null)
				return false;
		} else if (!tipoOrden.equals(other.tipoOrden))
			return false;
		return true;
	}

}
