package com.tacticlogistics.application.tasks.etl.components.tactic.tms.rutas.finalizacion;

import java.util.Date;

import com.tacticlogistics.domain.model.oms.EstadoDistribucionType;

public class EntregaDto {
	private String clienteNumeroIdentificacion;
	private String numeroOrden;
	private String movil;
	private String placa;
	private Date fechaRuta;
	private EstadoDistribucionType estadoDistribucion;
	private Date fechaEntregaInicio;
	private Date fechaEntregaFin;
	
	public EntregaDto() {
		super();
	}

	public String getClienteNumeroIdentificacion() {
		return clienteNumeroIdentificacion;
	}

	public void setClienteNumeroIdentificacion(String clienteNumeroIdentificacion) {
		this.clienteNumeroIdentificacion = clienteNumeroIdentificacion;
	}

	public String getNumeroOrden() {
		return numeroOrden;
	}

	public void setNumeroOrden(String numeroOrden) {
		this.numeroOrden = numeroOrden;
	}

	public String getMovil() {
		return movil;
	}

	public void setMovil(String movil) {
		this.movil = movil;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public Date getFechaRuta() {
		return fechaRuta;
	}

	public void setFechaRuta(Date fechaRuta) {
		this.fechaRuta = fechaRuta;
	}

	public EstadoDistribucionType getEstadoDistribucion() {
		return estadoDistribucion;
	}

	public void setEstadoDistribucion(EstadoDistribucionType estadoDistribucion) {
		this.estadoDistribucion = estadoDistribucion;
	}

	public Date getFechaEntregaInicio() {
		return fechaEntregaInicio;
	}

	public void setFechaEntregaInicio(Date fechaEntregaInicio) {
		this.fechaEntregaInicio = fechaEntregaInicio;
	}

	public Date getFechaEntregaFin() {
		return fechaEntregaFin;
	}

	public void setFechaEntregaFin(Date fechaEntregaFin) {
		this.fechaEntregaFin = fechaEntregaFin;
	}

	@Override
	public String toString() {
		return "EntregaDto ["
				+ (clienteNumeroIdentificacion != null
						? "clienteNumeroIdentificacion=" + clienteNumeroIdentificacion + ", " : "")
				+ (numeroOrden != null ? "numeroOrden=" + numeroOrden + ", " : "")
				+ (movil != null ? "movil=" + movil + ", " : "") + (placa != null ? "placa=" + placa + ", " : "")
				+ (fechaRuta != null ? "fechaRuta=" + fechaRuta + ", " : "")
				+ (estadoDistribucion != null ? "estadoDistribucion=" + estadoDistribucion + ", " : "")
				+ (fechaEntregaInicio != null ? "fechaEntregaInicio=" + fechaEntregaInicio + ", " : "")
				+ (fechaEntregaFin != null ? "fechaEntregaFin=" + fechaEntregaFin : "") + "]";
	}
}
