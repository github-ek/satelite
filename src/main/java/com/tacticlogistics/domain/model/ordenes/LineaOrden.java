package com.tacticlogistics.domain.model.ordenes;

import static com.tacticlogistics.infrastructure.services.Basic.coalesce;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.tacticlogistics.domain.model.common.TipoContenido;
import com.tacticlogistics.domain.model.common.valueobjects.Dimensiones;
import com.tacticlogistics.domain.model.common.valueobjects.MensajeEmbeddable;
import com.tacticlogistics.domain.model.wms.Bodega;
import com.tacticlogistics.domain.model.wms.Producto;
import com.tacticlogistics.domain.model.wms.Unidad;

@Entity
@Table(name = "LineasOrden", catalog = "ordenes", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "id_orden", "numeroItem" }) })

public class LineaOrden implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_linea_orden", unique = true, nullable = false)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_orden", nullable = false)
	private Orden orden;

	private int numeroItem;

	@Column(nullable = false, length = 300)
	@NotNull
	private String descripcion;

	@Column(nullable = false, name = "cantidad")
	@NotNull
	private int cantidadSolicitada;
	private int cantidadPlanificada;
	private int cantidadAlistada;
	private int cantidadEntregada;
	private int cantidadNoEntregada;
	private int cantidadNoEntregadaLegalizada;
	private int cantidadSobrante;
	private int cantidadSobranteLegalizada;

	// ---------------------------------------------------------------------------------------------------------
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_producto", nullable = true)
	private Producto producto;

	@Column(nullable = false, length = 50, name = "codigo_producto")
	private String productoCodigo;

	@Column(nullable = false, length = 50, name = "codigo_alterno_producto")
	private String productoCodigoAlterno;

	// ---------------------------------------------------------------------------------------------------------
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_unidad", nullable = true)
	private Unidad unidad;

	@Column(nullable = false, length = 2, name = "codigo_unidad")
	@NotNull
	private String unidadCodigo;

	@Column(nullable = false, length = 50, name = "codigo_alterno_unidad")
	private String unidadCodigoAlterno;

	// ---------------------------------------------------------------------------------------------------------
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "id_tipo_contenido", nullable = true)
	private TipoContenido tipoContenido;

	@Column(nullable = false, length = 20)
	@NotNull
	private String tipoContenidoCodigo;

	@Column(nullable = false, length = 50)
	@NotNull
	private String tipoContenidoCodigoAlterno;

	// ---------------------------------------------------------------------------------------------------------
	@Embedded
	private Dimensiones dimensiones;

	// ---------------------------------------------------------------------------------------------------------
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "id_bodega_origen", nullable = true)
	private Bodega bodegaOrigen;

	@Column(nullable = false, length = 32)
	@NotNull
	private String bodegaOrigenCodigo;

	@Column(nullable = false, length = 50)
	@NotNull
	private String bodegaOrigenCodigoAlterno;

	@Column(name = "id_estado_inventario", nullable = false, length = 4)
	@NotNull
	private String estadoInventarioOrigenId;

	@Column(nullable = false, length = 35)
	@NotNull
	private String numeroOrdenWmsOrigen;

	// ---------------------------------------------------------------------------------------------------------
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "id_bodega_destino", nullable = true)
	private Bodega bodegaDestino;

	@Column(nullable = false, length = 32)
	@NotNull
	private String bodegaDestinoCodigo;

	@Column(nullable = false, length = 50)
	@NotNull
	private String bodegaDestinoCodigoAlterno;

	@Column(name = "id_estado_inventario_destino", nullable = false, length = 4)
	private String estadoInventarioDestinoId;

	@Column(nullable = false, length = 35)
	@NotNull
	private String numeroOrdenWmsDestino;

	// ---------------------------------------------------------------------------------------------------------
	@Column(nullable = false, length = 30)
	@NotNull
	private String lote;

	@Column(nullable = false, length = 30)
	@NotNull
	private String serial;

	@Column(nullable = false, length = 30)
	@NotNull
	private String cosecha;

	// ---------------------------------------------------------------------------------------------------------
	@Column(nullable = false, length = 30)
	@NotNull
	private String requerimientoEstampillado;

	@Column(nullable = false, length = 30)
	@NotNull
	private String requerimientoSalud;

	@Column(nullable = false, length = 30)
	@NotNull
	private String requerimientoImporte;

	@Column(nullable = false, length = 30)
	@NotNull
	private String requerimientoDistribuido;

	@Column(nullable = false, length = 30)
	@NotNull
	private String requerimientoNutricional;

	@Column(nullable = false, length = 30)
	@NotNull
	private String requerimientoBl;

	@Column(nullable = false, length = 30)
	@NotNull
	private String requerimientoFondoCuenta;

	@Column(nullable = false, length = 30)
	@NotNull
	private String requerimientoOrigen;

	// ---------------------------------------------------------------------------------------------------------
	@Column(nullable = true, length = 100)
	private String predistribucionDestinoFinal;

	@Column(nullable = true, length = 100)
	private String predistribucionRotulo;

	// ---------------------------------------------------------------------------------------------------------
	@Column(nullable = true)
	private Integer valorDeclaradoPorUnidad;

	@Column(nullable = false, length = 200)
	private String notas;

	// ---------------------------------------------------------------------------------------------------------
	@Column(nullable = false, columnDefinition = "DATETIME2(0)")
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date fechaCreacion;

	@Column(nullable = false, length = 50)
	@NotNull
	private String usuarioCreacion;

	@Column(nullable = false, columnDefinition = "DATETIME2(0)")
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date fechaActualizacion;

	@Column(nullable = false, length = 50)
	@NotNull
	private String usuarioActualizacion;

	// ---------------------------------------------------------------------------------------------------------
	@ElementCollection
	@CollectionTable(name = "lineas_ordenes_mensajes", catalog = "ordenes", joinColumns = @JoinColumn(name = "id_linea_orden", referencedColumnName = "id_linea_orden"))
	private Set<MensajeEmbeddable> mensajes = new HashSet<MensajeEmbeddable>();

	// ---------------------------------------------------------------------------------------------------------
	public LineaOrden() {
		super();
		this.setId(null);
		this.setNumeroItem(0);
		this.setDescripcion("");
		this.setCantidadSolicitada(0);
		this.setCantidadPlanificada(0);
		this.setCantidadAlistada(0);
		this.setCantidadEntregada(0);
		this.setCantidadNoEntregada(0);
		this.setCantidadNoEntregadaLegalizada(0);
		this.setCantidadSobrante(0);
		this.setCantidadSobranteLegalizada(0);
		this.setProducto(null);
		this.setProductoCodigo("");
		this.setProductoCodigoAlterno("");
		this.setUnidad(null);
		this.setUnidadCodigo("");
		this.setUnidadCodigoAlterno("");
		this.setTipoContenido(null);
		this.setTipoContenidoCodigo("");
		this.setTipoContenidoCodigoAlterno("");
		this.setDimensiones(null);
		this.setBodegaOrigen(null);
		this.setBodegaOrigenCodigo("");
		this.setBodegaOrigenCodigoAlterno("");
		this.setEstadoInventarioOrigenId("");
		this.setNumeroOrdenWmsOrigen("");
		this.setBodegaDestino(null);
		this.setBodegaDestinoCodigo("");
		this.setBodegaDestinoCodigoAlterno("");
		this.setEstadoInventarioDestinoId("");
		this.setNumeroOrdenWmsDestino("");
		this.setLote("");
		this.setSerial("");
		this.setCosecha("");
		this.setRequerimientoEstampillado("");
		this.setRequerimientoSalud("");
		this.setRequerimientoImporte("");
		this.setRequerimientoDistribuido("");
		this.setRequerimientoNutricional("");
		this.setRequerimientoBl("");
		this.setRequerimientoFondoCuenta("");
		this.setRequerimientoOrigen("");
		this.setPredistribucionDestinoFinal("");
		this.setPredistribucionRotulo("");
		this.setValorDeclaradoPorUnidad(null);
		this.setNotas("");
		this.setFechaCreacion(null);
		this.setUsuarioCreacion("");
		this.setFechaActualizacion(null);
		this.setUsuarioActualizacion("");

		this.mensajes = new HashSet<>();
	}

	// ---------------------------------------------------------------------------------------------------------
	public Integer getId() {
		return id;
	}

	public Orden getOrden() {
		return orden;
	}

	public int getNumeroItem() {
		return numeroItem;
	}

	// ---------------------------------------------------------------------------------------------------------
	public String getDescripcion() {
		return descripcion;
	}

	public int getCantidadSolicitada() {
		return cantidadSolicitada;
	}

	public int getCantidadPlanificada() {
		return cantidadPlanificada;
	}

	public int getCantidadAlistada() {
		return cantidadAlistada;
	}

	public int getCantidadEntregada() {
		return cantidadEntregada;
	}

	public int getCantidadNoEntregada() {
		return cantidadNoEntregada;
	}

	public int getCantidadNoEntregadaLegalizada() {
		return cantidadNoEntregadaLegalizada;
	}

	public int getCantidadSobrante() {
		return cantidadSobrante;
	}

	public int getCantidadSobranteLegalizada() {
		return cantidadSobranteLegalizada;
	}

	// ---------------------------------------------------------------------------------------------------------
	public Producto getProducto() {
		return producto;
	}

	public String getProductoCodigo() {
		return productoCodigo;
	}

	public String getProductoCodigoAlterno() {
		return productoCodigoAlterno;
	}

	// ---------------------------------------------------------------------------------------------------------
	public Unidad getUnidad() {
		return unidad;
	}

	public String getUnidadCodigo() {
		return unidadCodigo;
	}

	public String getUnidadCodigoAlterno() {
		return unidadCodigoAlterno;
	}

	// ---------------------------------------------------------------------------------------------------------
	public TipoContenido getTipoContenido() {
		return tipoContenido;
	}

	public String getTipoContenidoCodigo() {
		return tipoContenidoCodigo;
	}

	public String getTipoContenidoCodigoAlterno() {
		return tipoContenidoCodigoAlterno;
	}

	// ---------------------------------------------------------------------------------------------------------
	public BigDecimal getLargoPorUnidad() {
		return getDimensiones().getLargoPorUnidad();
	}

	public BigDecimal getAnchoPorUnidad() {
		return getDimensiones().getAnchoPorUnidad();
	}

	public BigDecimal getAltoPorUnidad() {
		return getDimensiones().getAltoPorUnidad();
	}

	public BigDecimal getPesoBrutoPorUnidad() {
		return getDimensiones().getPesoBrutoPorUnidad();
	}

	public BigDecimal getPesoBrutoVolumetricoPorUnidad() {
		return getDimensiones().getPesoBrutoVolumetricoPorUnidad();
	}

	protected Dimensiones getDimensiones() {
		if (this.dimensiones == null) {
			this.setDimensiones(new Dimensiones());
		}
		return dimensiones;
	}

	// ---------------------------------------------------------------------------------------------------------
	public Bodega getBodegaOrigen() {
		return bodegaOrigen;
	}

	public String getBodegaOrigenCodigo() {
		return bodegaOrigenCodigo;
	}

	public String getBodegaOrigenCodigoAlterno() {
		return bodegaOrigenCodigoAlterno;
	}

	public String getEstadoInventarioOrigenId() {
		return estadoInventarioOrigenId;
	}

	public String getNumeroOrdenWmsOrigen() {
		return numeroOrdenWmsOrigen;
	}

	// ---------------------------------------------------------------------------------------------------------
	public Bodega getBodegaDestino() {
		return bodegaDestino;
	}

	public String getBodegaDestinoCodigo() {
		return bodegaDestinoCodigo;
	}

	public String getBodegaDestinoCodigoAlterno() {
		return bodegaDestinoCodigoAlterno;
	}

	public String getEstadoInventarioDestinoId() {
		return estadoInventarioDestinoId;
	}

	public String getNumeroOrdenWmsDestino() {
		return numeroOrdenWmsDestino;
	}

	// ---------------------------------------------------------------------------------------------------------
	public String getLote() {
		return lote;
	}

	public String getSerial() {
		return serial;
	}

	public String getCosecha() {
		return cosecha;
	}

	// ---------------------------------------------------------------------------------------------------------
	public String getRequerimientoEstampillado() {
		return requerimientoEstampillado;
	}

	public String getRequerimientoSalud() {
		return requerimientoSalud;
	}

	public String getRequerimientoImporte() {
		return requerimientoImporte;
	}

	public String getRequerimientoDistribuido() {
		return requerimientoDistribuido;
	}

	public String getRequerimientoNutricional() {
		return requerimientoNutricional;
	}

	public String getRequerimientoBl() {
		return requerimientoBl;
	}

	public String getRequerimientoFondoCuenta() {
		return requerimientoFondoCuenta;
	}

	public String getRequerimientoOrigen() {
		return requerimientoOrigen;
	}

	// ---------------------------------------------------------------------------------------------------------
	public String getPredistribucionDestinoFinal() {
		return predistribucionDestinoFinal;
	}

	public String getPredistribucionRotulo() {
		return predistribucionRotulo;
	}

	// ---------------------------------------------------------------------------------------------------------
	public Integer getValorDeclaradoPorUnidad() {
		return valorDeclaradoPorUnidad;
	}

	public String getNotas() {
		return notas;
	}

	// ---------------------------------------------------------------------------------------------------------
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public String getUsuarioCreacion() {
		return usuarioCreacion;
	}

	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	public String getUsuarioActualizacion() {
		return usuarioActualizacion;
	}

	public Set<MensajeEmbeddable> getMensajes() {
		return mensajes;
	}

	// ---------------------------------------------------------------------------------------------------------
	public void setId(Integer id) {
		this.id = id;
	}

	public void setOrden(Orden orden) {
		this.orden = orden;
	}

	// ---------------------------------------------------------------------------------------------------------
	public void setNumeroItem(int numeroItem) {
		this.numeroItem = numeroItem;
	}

	public void setDescripcion(String value) {
		this.descripcion = coalesce(value, "");
	}

	// ---------------------------------------------------------------------------------------------------------
	public void setCantidadSolicitada(int cantidadSolicitada) {
		this.cantidadSolicitada = cantidadSolicitada;
	}

	public void setCantidadPlanificada(int cantidadPlanificada) {
		this.cantidadPlanificada = cantidadPlanificada;
	}

	public void setCantidadAlistada(int cantidadAlistada) {
		this.cantidadAlistada = cantidadAlistada;
	}

	public void setCantidadEntregada(int cantidadEntregada) {
		this.cantidadEntregada = cantidadEntregada;
	}

	public void setCantidadNoEntregada(int cantidadNoEntregada) {
		this.cantidadNoEntregada = cantidadNoEntregada;
	}

	public void setCantidadNoEntregadaLegalizada(int cantidadNoEntregadaLegalizada) {
		this.cantidadNoEntregadaLegalizada = cantidadNoEntregadaLegalizada;
	}

	public void setCantidadSobrante(int cantidadSobrante) {
		this.cantidadSobrante = cantidadSobrante;
	}

	public void setCantidadSobranteLegalizada(int cantidadSobranteLegalizada) {
		this.cantidadSobranteLegalizada = cantidadSobranteLegalizada;
	}

	// ---------------------------------------------------------------------------------------------------------
	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public void setProductoCodigo(String value) {
		this.productoCodigo = coalesce(value, "");
	}

	public void setProductoCodigoAlterno(String value) {
		this.productoCodigoAlterno = coalesce(value, "");
	}

	// ---------------------------------------------------------------------------------------------------------
	public void setUnidad(Unidad unidad) {
		this.unidad = unidad;
	}

	public void setUnidadCodigo(String value) {
		this.unidadCodigo = coalesce(value, "");
	}

	public void setUnidadCodigoAlterno(String value) {
		this.unidadCodigoAlterno = coalesce(value, "");
	}

	// ---------------------------------------------------------------------------------------------------------
	public void setTipoContenido(TipoContenido tipoContenido) {
		this.tipoContenido = tipoContenido;
	}

	public void setTipoContenidoCodigo(String value) {
		this.tipoContenidoCodigo = coalesce(value, "");
	}

	public void setTipoContenidoCodigoAlterno(String value) {
		this.tipoContenidoCodigoAlterno = coalesce(value, "");
	}

	// ---------------------------------------------------------------------------------------------------------
	public void setDimensiones(Dimensiones dimensiones) {
		this.dimensiones = coalesce(dimensiones, new Dimensiones());
	}

	// ---------------------------------------------------------------------------------------------------------
	public void setBodegaOrigen(Bodega bodegaOrigen) {
		this.bodegaOrigen = bodegaOrigen;
	}

	public void setBodegaOrigenCodigo(String value) {
		this.bodegaOrigenCodigo = coalesce(value, "");
	}

	public void setBodegaOrigenCodigoAlterno(String value) {
		this.bodegaOrigenCodigoAlterno = coalesce(value, "");
	}

	public void setEstadoInventarioOrigenId(String value) {
		this.estadoInventarioOrigenId = coalesce(value, "");
	}

	public void setNumeroOrdenWmsOrigen(String value) {
		this.numeroOrdenWmsOrigen = coalesce(value, "");
	}

	// ---------------------------------------------------------------------------------------------------------
	public void setBodegaDestino(Bodega bodegaDestino) {
		this.bodegaDestino = bodegaDestino;
	}

	public void setBodegaDestinoCodigo(String value) {
		this.bodegaDestinoCodigo = coalesce(value, "");
	}

	public void setBodegaDestinoCodigoAlterno(String value) {
		this.bodegaDestinoCodigoAlterno = coalesce(value, "");
	}

	public void setEstadoInventarioDestinoId(String value) {
		this.estadoInventarioDestinoId = coalesce(value, "");
	}

	public void setNumeroOrdenWmsDestino(String value) {
		this.numeroOrdenWmsDestino = coalesce(value, "");
	}

	// ---------------------------------------------------------------------------------------------------------
	public void setLote(String value) {
		this.lote = coalesce(value, "");
	}

	public void setSerial(String value) {
		this.serial = coalesce(value, "");
	}

	public void setCosecha(String value) {
		this.cosecha = coalesce(value, "");
	}

	// ---------------------------------------------------------------------------------------------------------
	public void setRequerimientoEstampillado(String value) {
		this.requerimientoEstampillado = coalesce(value, "");
	}

	public void setRequerimientoSalud(String value) {
		this.requerimientoSalud = coalesce(value, "");
	}

	public void setRequerimientoImporte(String value) {
		this.requerimientoImporte = coalesce(value, "");
	}

	public void setRequerimientoDistribuido(String value) {
		this.requerimientoDistribuido = coalesce(value, "");
	}

	public void setRequerimientoNutricional(String value) {
		this.requerimientoNutricional = coalesce(value, "");
	}

	public void setRequerimientoBl(String value) {
		this.requerimientoBl = coalesce(value, "");
	}

	public void setRequerimientoFondoCuenta(String value) {
		this.requerimientoFondoCuenta = coalesce(value, "");
	}

	public void setRequerimientoOrigen(String value) {
		this.requerimientoOrigen = coalesce(value, "");
	}

	// ---------------------------------------------------------------------------------------------------------
	public void setPredistribucionDestinoFinal(String value) {
		this.predistribucionDestinoFinal = coalesce(value, "");
	}

	public void setPredistribucionRotulo(String value) {
		this.predistribucionRotulo = coalesce(value, "");
	}

	// ---------------------------------------------------------------------------------------------------------
	public void setValorDeclaradoPorUnidad(Integer valorDeclaradoPorUnidad) {
		this.valorDeclaradoPorUnidad = valorDeclaradoPorUnidad;
	}

	public void setNotas(String value) {
		this.notas = coalesce(value, "");
	}

	// ---------------------------------------------------------------------------------------------------------
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public void setUsuarioCreacion(String value) {
		this.usuarioCreacion = coalesce(value, "");
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public void setUsuarioActualizacion(String value) {
		this.usuarioActualizacion = coalesce(value, "");
	}

	// ---------------------------------------------------------------------------------------------------------
	public void setMensajes(Set<MensajeEmbeddable> mensajes) {
		this.mensajes = mensajes;
	}

	// ---------------------------------------------------------------------------------------------------------
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + numeroItem;
		result = prime * result + ((orden == null) ? 0 : orden.hashCode());
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
		LineaOrden other = (LineaOrden) obj;
		if (numeroItem != other.numeroItem)
			return false;
		if (orden == null) {
			if (other.orden != null)
				return false;
		} else if (!orden.equals(other.orden))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LineaOrden [" + (id != null ? "id=" + id + ", " : "") + "numeroItem=" + numeroItem + ", "
				+ (descripcion != null ? "descripcion=" + descripcion + ", " : "") + "cantidadSolicitada="
				+ cantidadSolicitada + ", cantidadEntregada=" + cantidadEntregada + ", "
				+ (productoCodigo != null ? "productoCodigo=" + productoCodigo + ", " : "")
				+ (productoCodigoAlterno != null ? "productoCodigoAlterno=" + productoCodigoAlterno + ", " : "")
				+ (unidadCodigo != null ? "unidadCodigo=" + unidadCodigo + ", " : "")
				+ (unidadCodigoAlterno != null ? "unidadCodigoAlterno=" + unidadCodigoAlterno + ", " : "")
				+ (tipoContenidoCodigo != null ? "tipoContenidoCodigo=" + tipoContenidoCodigo + ", " : "")
				+ (tipoContenidoCodigoAlterno != null
						? "tipoContenidoCodigoAlterno=" + tipoContenidoCodigoAlterno + ", " : "")
				+ (dimensiones != null ? "dimensiones=" + dimensiones + ", " : "")
				+ (bodegaOrigenCodigo != null ? "bodegaOrigenCodigo=" + bodegaOrigenCodigo + ", " : "")
				+ (bodegaOrigenCodigoAlterno != null ? "bodegaOrigenCodigoAlterno=" + bodegaOrigenCodigoAlterno + ", "
						: "")
				+ (estadoInventarioOrigenId != null ? "estadoInventarioOrigenId=" + estadoInventarioOrigenId + ", "
						: "")
				+ (numeroOrdenWmsOrigen != null ? "numeroOrdenWmsOrigen=" + numeroOrdenWmsOrigen + ", " : "")
				+ (bodegaDestinoCodigo != null ? "bodegaDestinoCodigo=" + bodegaDestinoCodigo + ", " : "")
				+ (bodegaDestinoCodigoAlterno != null
						? "bodegaDestinoCodigoAlterno=" + bodegaDestinoCodigoAlterno + ", " : "")
				+ (estadoInventarioDestinoId != null ? "estadoInventarioDestinoId=" + estadoInventarioDestinoId + ", "
						: "")
				+ (numeroOrdenWmsDestino != null ? "numeroOrdenWmsDestino=" + numeroOrdenWmsDestino + ", " : "")
				+ (predistribucionDestinoFinal != null
						? "predistribucionDestinoFinal=" + predistribucionDestinoFinal + ", " : "")
				+ (predistribucionRotulo != null ? "predistribucionRotulo=" + predistribucionRotulo + ", " : "")
				+ (valorDeclaradoPorUnidad != null ? "valorDeclaradoPorUnidad=" + valorDeclaradoPorUnidad + ", " : "")
				+ (notas != null ? "notas=" + notas + ", " : "")
				+ (fechaCreacion != null ? "fechaCreacion=" + fechaCreacion + ", " : "")
				+ (usuarioCreacion != null ? "usuarioCreacion=" + usuarioCreacion + ", " : "")
				+ (fechaActualizacion != null ? "fechaActualizacion=" + fechaActualizacion + ", " : "")
				+ (usuarioActualizacion != null ? "usuarioActualizacion=" + usuarioActualizacion : "") + "]";
	}
}
