package com.tacticlogistics.application.tasks.etl.components.tactic.tms.rutas.finalizacion;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.tacticlogistics.domain.model.oms.EstadoDistribucionType;

public class EntregaDto {
	private String clienteNumeroIdentificacion;
	private String numeroOrden;
	private String movil;
	private String placa;
	private LocalDate fechaRuta;
	private EstadoDistribucionType estadoDistribucion;
	private LocalDateTime fechaEntregaInicio;
	private LocalDateTime fechaEntregaFin;
	
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

	public LocalDate getFechaRuta() {
		return fechaRuta;
	}

	public void setFechaRuta(LocalDate fechaRuta) {
		this.fechaRuta = fechaRuta;
	}

	public EstadoDistribucionType getEstadoDistribucion() {
		return estadoDistribucion;
	}

	public void setEstadoDistribucion(EstadoDistribucionType estadoDistribucion) {
		this.estadoDistribucion = estadoDistribucion;
	}

	public LocalDateTime getFechaEntregaInicio() {
		return fechaEntregaInicio;
	}

	public void setFechaEntregaInicio(LocalDateTime fechaEntregaInicio) {
		this.fechaEntregaInicio = fechaEntregaInicio;
	}

	public LocalDateTime getFechaEntregaFin() {
		return fechaEntregaFin;
	}

	public void setFechaEntregaFin(LocalDateTime fechaEntregaFin) {
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
