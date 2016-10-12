package com.tacticlogistics.jda.wms.tasks.despachos;

public class LineaOrdenDespacho {
	
	private String numeroOrden;
	private int numeroLinea;
	private int numeroSublinea = 0000;
	private String codigoProducto;
	private String codigoCliente;
	private String lote;
	private String estadoInventario;
	private int cantidadSolicitad;
	private boolean dividirEnvio = true;
	private String bodega;
	private String estampillado;
	private String transportista;
	private String nivelServicio;
	
	public LineaOrdenDespacho(){
		
	}
	
	public String getBodega() {
		return bodega;
	}
	
	public void setBodega(String bodega) {
		this.bodega = bodega;
	}

	public String getNumeroOrden() {
		return numeroOrden;
	}

	public void setNumeroOrden(String numeroOrden) {
		this.numeroOrden = numeroOrden;
	}

	public int getNumeroLinea() {
		return numeroLinea;
	}

	public void setNumeroLinea(int numeroLinea) {
		this.numeroLinea = numeroLinea;
	}

	public int getNumeroSublinea() {
		return numeroSublinea;
	}

	public void setNumeroSublinea(int numeroSublinea) {
		this.numeroSublinea = numeroSublinea;
	}

	public String getCodigoProducto() {
		return codigoProducto;
	}

	public void setCodigoProducto(String codigoProducto) {
		this.codigoProducto = codigoProducto;
	}

	public String getCodigoCliente() {
		return codigoCliente;
	}

	public void setCodigoCliente(String codigoCliente) {
		this.codigoCliente = codigoCliente;
	}

	public String getLote() {
		return lote;
	}

	public void setLote(String lote) {
		this.lote = lote;
	}

	public String getEstadoInventario() {
		return estadoInventario;
	}

	public void setEstadoInventario(String estadoInventario) {
		this.estadoInventario = estadoInventario;
	}

	public int getCantidadSolicitad() {
		return cantidadSolicitad;
	}

	public void setCantidadSolicitad(int cantidadSolicitad) {
		this.cantidadSolicitad = cantidadSolicitad;
	}

	public boolean isDividirEnvio() {
		return dividirEnvio;
	}

	public void setDividirEnvio(boolean dividirEnvio) {
		this.dividirEnvio = dividirEnvio;
	}

	public String getEstampillado() {
		return estampillado;
	}

	public void setEstampillado(String estampillado) {
		this.estampillado = estampillado;
	}

	public String getTransportista() {
		return transportista;
	}

	public void setTransportista(String transportista) {
		this.transportista = transportista;
	}

	public String getNivelServicio() {
		return nivelServicio;
	}

	public void setNivelServicio(String nivelServicio) {
		this.nivelServicio = nivelServicio;
	}
	
	
}
