package com.tacticlogistics.application.dto.ingresos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "OrdenesIngreso", catalog = "ordenes")
public class OrdenIngreso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_orden_ingreso", unique = true, nullable = false)
	public Integer id;

	public String usuario;
	public String cliente;
	public String numeroDocumentoCliente;
	public String bodega;
	public String fechaInicio;
	public String fechaFinRegistro;
	public Integer hash;

	@Enumerated(EnumType.STRING)
	public EstadoOrdenIngresoType estado;
	public Date fechaRecepcion;
	public Date fechaIntegracionWMS;
	public Date fechaRealimentacionWMS;
	public boolean notificado;
	public Date fechaNotificacion;

	public String proveedores;
	public String ciudadOrigen;
	public String transportadora;
	public String vehiculo;
	public String placaVehiculo;
	public String placaRemolque;
	public String numeroPrecinto;
	public String documentosProductoNacional;
	public String documentosProductoImportado;
	public String plasticos;
	public String numeroTornaguia;
	public String fondoCuenta;

	@Lob
	public String imagenIngreso;
	@Lob
	public String imagenInicioDescarga;
	@Lob
	public String fotoPrecinto;
	@Lob
	public String fotoTornaguia;
	@Lob
	public String fotoPiso;
	@Lob
	public String fotoSalida;
	
	
	// @OneToMany(fetch = FetchType.LAZY, mappedBy = "orden")
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_orden_ingreso", referencedColumnName = "id_orden_ingreso")
	public List<LineaOrdenIngreso> productos = new ArrayList<LineaOrdenIngreso>(0);

	public OrdenIngreso Recepcionar() {
		if (this.estado != null) {
			throw new IllegalStateException("Estado actual de orden: " + this.estado.getCodigo());
		}
		this.hash = this.hashCode();
		this.estado = EstadoOrdenIngresoType.RECEPCIONADO;
		this.fechaRecepcion = new Date();
		this.notificado = false;
		this.fechaNotificacion = null;
		return this;
	}

	protected OrdenIngreso() {

	}

	public OrdenIngreso(Integer id, String usuario, String cliente, String numeroDocumentoCliente, String bodega,
			String fechaInicio, String fechaFinRegistro, Integer hash, EstadoOrdenIngresoType estado,
			Date fechaRecepcion, Date fechaIntegracionWMS, Date fechaRealimentacionWMS, boolean notificado,
			Date fechaNotificacion, String proveedores, String ciudadOrigen, String transportadora, String vehiculo,
			String placaVehiculo, String placaRemolque, String numeroPrecinto, String documentosProductoNacional,
			String documentosProductoImportado, String plasticos, String numeroTornaguia, String fondoCuenta,
			String imagenIngreso, String imagenInicioDescarga, String fotoPrecinto, String fotoTornaguia,
			String fotoSalida, List<LineaOrdenIngreso> productos) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.cliente = cliente;
		this.numeroDocumentoCliente = numeroDocumentoCliente;
		this.bodega = bodega;
		this.fechaInicio = fechaInicio;
		this.fechaFinRegistro = fechaFinRegistro;
		this.hash = hash;
		this.estado = estado;
		this.fechaRecepcion = fechaRecepcion;
		this.fechaIntegracionWMS = fechaIntegracionWMS;
		this.fechaRealimentacionWMS = fechaRealimentacionWMS;
		this.notificado = notificado;
		this.fechaNotificacion = fechaNotificacion;
		this.proveedores = proveedores;
		this.ciudadOrigen = ciudadOrigen;
		this.transportadora = transportadora;
		this.vehiculo = vehiculo;
		this.placaVehiculo = placaVehiculo;
		this.placaRemolque = placaRemolque;
		this.numeroPrecinto = numeroPrecinto;
		this.documentosProductoNacional = documentosProductoNacional;
		this.documentosProductoImportado = documentosProductoImportado;
		this.plasticos = plasticos;
		this.numeroTornaguia = numeroTornaguia;
		this.fondoCuenta = fondoCuenta;
		this.imagenIngreso = imagenIngreso;
		this.imagenInicioDescarga = imagenInicioDescarga;
		this.fotoPrecinto = fotoPrecinto;
		this.fotoTornaguia = fotoTornaguia;
		this.fotoSalida = fotoSalida;
		this.productos = productos;
	}

	@Override
	public String toString() {
		final int maxLen = 3;
		StringBuilder builder = new StringBuilder();
		builder.append("Orden [id=").append(id).append(", usuario=").append(usuario).append(", cliente=")
				.append(cliente).append(", numeroDocumentoCliente=").append(numeroDocumentoCliente).append(", bodega=")
				.append(bodega).append(", fechaInicio=").append(fechaInicio).append(", fechaFinRegistro=")
				.append(fechaFinRegistro).append(", hash=").append(hash).append(", estado=").append(estado)
				.append(", fechaRecepcion=").append(fechaRecepcion).append(", fechaIntegracionWMS=")
				.append(fechaIntegracionWMS).append(", fechaRealimentacionWMS=").append(fechaRealimentacionWMS)
				.append(", notificado=").append(notificado).append(", fechaNotificacion=").append(fechaNotificacion)
				.append(", proveedores=").append(proveedores).append(", ciudadOrigen=").append(ciudadOrigen)
				.append(", transportadora=").append(transportadora).append(", vehiculo=").append(vehiculo)
				.append(", placaVehiculo=").append(placaVehiculo).append(", placaRemolque=").append(placaRemolque)
				.append(", numeroPrecinto=").append(numeroPrecinto).append(", documentosProductoNacional=")
				.append(documentosProductoNacional).append(", documentosProductoImportado=")
				.append(documentosProductoImportado).append(", plasticos=").append(plasticos)
				.append(", numeroTornaguia=").append(numeroTornaguia).append(", fondoCuenta=").append(fondoCuenta)
				.append(", imagenIngreso=").append(imagenIngreso).append(", imagenInicioDescarga=")
				.append(imagenInicioDescarga).append(", fotoPrecinto=").append(fotoPrecinto).append(", fotoTornaguia=")
				.append(fotoTornaguia).append(", fotoSalida=").append(fotoSalida).append(", productos=")
				.append(productos != null ? productos.subList(0, Math.min(productos.size(), maxLen)) : null)
				.append("]");
		return builder.toString();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getNumeroDocumentoCliente() {
		return numeroDocumentoCliente;
	}

	public void setNumeroDocumentoCliente(String numeroDocumentoCliente) {
		this.numeroDocumentoCliente = numeroDocumentoCliente;
	}

	public String getBodega() {
		return bodega;
	}

	public void setBodega(String bodega) {
		this.bodega = bodega;
	}

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getFechaFinRegistro() {
		return fechaFinRegistro;
	}

	public void setFechaFinRegistro(String fechaFinRegistro) {
		this.fechaFinRegistro = fechaFinRegistro;
	}

	public Integer getHash() {
		return hash;
	}

	public void setHash(Integer hash) {
		this.hash = hash;
	}

	public EstadoOrdenIngresoType getEstado() {
		return estado;
	}

	public void setEstado(EstadoOrdenIngresoType estado) {
		this.estado = estado;
	}

	public Date getFechaRecepcion() {
		return fechaRecepcion;
	}

	public void setFechaRecepcion(Date fechaRecepcion) {
		this.fechaRecepcion = fechaRecepcion;
	}

	public Date getFechaIntegracionWMS() {
		return fechaIntegracionWMS;
	}

	public void setFechaIntegracionWMS(Date fechaIntegracionWMS) {
		this.fechaIntegracionWMS = fechaIntegracionWMS;
	}

	public Date getFechaRealimentacionWMS() {
		return fechaRealimentacionWMS;
	}

	public void setFechaRealimentacionWMS(Date fechaRealimentacionWMS) {
		this.fechaRealimentacionWMS = fechaRealimentacionWMS;
	}

	public boolean isNotificado() {
		return notificado;
	}

	public void setNotificado(boolean notificado) {
		this.notificado = notificado;
	}

	public Date getFechaNotificacion() {
		return fechaNotificacion;
	}

	public void setFechaNotificacion(Date fechaNotificacion) {
		this.fechaNotificacion = fechaNotificacion;
	}

	public String getProveedores() {
		return proveedores;
	}

	public void setProveedores(String proveedores) {
		this.proveedores = proveedores;
	}

	public String getCiudadOrigen() {
		return ciudadOrigen;
	}

	public void setCiudadOrigen(String ciudadOrigen) {
		this.ciudadOrigen = ciudadOrigen;
	}

	public String getTransportadora() {
		return transportadora;
	}

	public void setTransportadora(String transportadora) {
		this.transportadora = transportadora;
	}

	public String getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(String vehiculo) {
		this.vehiculo = vehiculo;
	}

	public String getPlacaVehiculo() {
		return placaVehiculo;
	}

	public void setPlacaVehiculo(String placaVehiculo) {
		this.placaVehiculo = placaVehiculo;
	}

	public String getPlacaRemolque() {
		return placaRemolque;
	}

	public void setPlacaRemolque(String placaRemolque) {
		this.placaRemolque = placaRemolque;
	}

	public String getNumeroPrecinto() {
		return numeroPrecinto;
	}

	public void setNumeroPrecinto(String numeroPrecinto) {
		this.numeroPrecinto = numeroPrecinto;
	}

	public String getDocumentosProductoNacional() {
		return documentosProductoNacional;
	}

	public void setDocumentosProductoNacional(String documentosProductoNacional) {
		this.documentosProductoNacional = documentosProductoNacional;
	}

	public String getDocumentosProductoImportado() {
		return documentosProductoImportado;
	}

	public void setDocumentosProductoImportado(String documentosProductoImportado) {
		this.documentosProductoImportado = documentosProductoImportado;
	}

	public String getPlasticos() {
		return plasticos;
	}

	public void setPlasticos(String plasticos) {
		this.plasticos = plasticos;
	}

	public String getNumeroTornaguia() {
		return numeroTornaguia;
	}

	public void setNumeroTornaguia(String numeroTornaguia) {
		this.numeroTornaguia = numeroTornaguia;
	}

	public String getFondoCuenta() {
		return fondoCuenta;
	}

	public void setFondoCuenta(String fondoCuenta) {
		this.fondoCuenta = fondoCuenta;
	}

	public String getImagenIngreso() {
		return imagenIngreso;
	}

	public void setImagenIngreso(String imagenIngreso) {
		this.imagenIngreso = imagenIngreso;
	}

	public String getImagenInicioDescarga() {
		return imagenInicioDescarga;
	}

	public void setImagenInicioDescarga(String imagenInicioDescarga) {
		this.imagenInicioDescarga = imagenInicioDescarga;
	}

	public String getFotoPrecinto() {
		return fotoPrecinto;
	}

	public void setFotoPrecinto(String fotoPrecinto) {
		this.fotoPrecinto = fotoPrecinto;
	}

	public String getFotoTornaguia() {
		return fotoTornaguia;
	}

	public void setFotoTornaguia(String fotoTornaguia) {
		this.fotoTornaguia = fotoTornaguia;
	}

	public String getFotoPiso() {
		return fotoPiso;
	}

	public void setFotoPiso(String fotoPiso) {
		this.fotoPiso = fotoPiso;
	}

	public String getFotoSalida() {
		return fotoSalida;
	}

	public void setFotoSalida(String fotoSalida) {
		this.fotoSalida = fotoSalida;
	}

	public List<LineaOrdenIngreso> getProductos() {
		return productos;
	}

	public void setProductos(List<LineaOrdenIngreso> productos) {
		this.productos = productos;
	}

}
