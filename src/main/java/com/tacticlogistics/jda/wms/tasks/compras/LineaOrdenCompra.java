package com.tacticlogistics.presentation.wms.compras;

public class LineaOrdenCompra {
	
	private int numeroLinea;
	private int numeroSublinea = 0000;
	private String codigoProducto;
	private int cantidadEsperada;
	private String estadoInventarioEsperado;
	private String lote;
	private String estampillado;
	
	public LineaOrdenCompra(){
		
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
	public void setNumeroSublinea(int invsln) {
		this.numeroSublinea = invsln;
	}
	public String getCodigoProducto() {
		return codigoProducto;
	}
	public void setCodigoProducto(String codigoProducto) {
		this.codigoProducto = codigoProducto;
	}
	public int getCantidadEsperada() {
		return cantidadEsperada;
	}
	public void setCantidadEsperada(int cantidadEsperada) {
		this.cantidadEsperada = cantidadEsperada;
	}
	public String getEstadoInventarioEsperado() {
		return estadoInventarioEsperado;
	}
	public void setEstadoInventarioEsperado(String estadoInventarioEsperado) {
		this.estadoInventarioEsperado = estadoInventarioEsperado;
	}
	public String getLote() {
		return lote;
	}
	public void setLote(String lote) {
		this.lote = lote;
	}
	public String getEstampillado() {
		return estampillado;
	}
	public void setEstampillado(String estampillado) {
		this.estampillado = estampillado;
	}

	@Override
	public String toString() {
		return "LineaOrdenCompra [numeroLinea=" + numeroLinea + ", invsln=" + numeroSublinea + ", codigoProducto="
				+ codigoProducto + ", cantidadEsperada=" + cantidadEsperada + ", estadoInventarioEsperado="
				+ estadoInventarioEsperado + ", lote=" + lote + ", estampillado=" + estampillado + "]";
	}
	
	
}
