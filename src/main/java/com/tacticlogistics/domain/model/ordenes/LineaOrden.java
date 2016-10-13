package com.tacticlogistics.domain.model.ordenes;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.tacticlogistics.domain.model.common.TipoContenido;
import com.tacticlogistics.domain.model.common.valueobjects.Dimensiones;
import com.tacticlogistics.domain.model.wms.Bodega;
import com.tacticlogistics.domain.model.wms.Producto;
import com.tacticlogistics.domain.model.wms.Unidad;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "LineasOrden", catalog = "ordenes", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "id_orden", "numeroItem" }) })
@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class LineaOrden implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_linea_orden")
	private Integer id;

	private int numeroItem;

	@Column(nullable = false, length = 300)
	@NotNull
	private String descripcion;

	private int cantidadSolicitada;

	private int cantidadPlanificada;

	private Integer cantidadAlistada;

	private Integer cantidadEntregada;

	private Integer cantidadNoEntregada;

	private Integer cantidadNoEntregadaLegalizada;

	private Integer cantidadSobrante;

	private Integer cantidadSobranteLegalizada;

	// ---------------------------------------------------------------------------------------------------------
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "id_producto")
	private Producto producto;

	@Column(nullable = false, length = 50)
	@NotNull
	private String productoCodigo;

	// ---------------------------------------------------------------------------------------------------------
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "id_unidad")
	private Unidad unidad;

	@Column(nullable = false, length = 2)
	@NotNull
	private String unidadCodigo;

	// ---------------------------------------------------------------------------------------------------------
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "id_tipo_contenido")
	private TipoContenido tipoContenido;

	@Column(nullable = false, length = 20)
	@NotNull
	private String tipoContenidoCodigo;

	// ---------------------------------------------------------------------------------------------------------
	@Embedded
	private Dimensiones dimensiones;

	// ---------------------------------------------------------------------------------------------------------
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "id_bodega_origen")
	private Bodega bodegaOrigen;

	@Column(nullable = false, length = 32)
	@NotNull
	private String bodegaOrigenCodigo;

	@Column(nullable = false, length = 50)
	@NotNull
	private String bodegaOrigenCodigoAlterno;

	@Column(name = "id_estado_inventario_origen", nullable = false, length = 4)
	@NotNull
	private String estadoInventarioOrigenId;

	@Column(nullable = false, length = 35)
	@NotNull
	private String numeroOrdenWmsOrigen;

	// ---------------------------------------------------------------------------------------------------------
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "id_bodega_destino")
	private Bodega bodegaDestino;

	@Column(nullable = false, length = 32)
	@NotNull
	private String bodegaDestinoCodigo;

	@Column(nullable = false, length = 50)
	@NotNull
	private String bodegaDestinoCodigoAlterno;

	@Column(name = "id_estado_inventario_destino", nullable = false, length = 4)
	@NotNull
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

	@Column(nullable = false, length = 30)
	@NotNull
	private String requerimientoPinado;

	// ---------------------------------------------------------------------------------------------------------
	@Column(nullable = false, length = 100)
	@NotNull
	private String predistribucionDestinoFinal;

	@Column(nullable = false, length = 100)
	@NotNull
	private String predistribucionRotulo;

	// ---------------------------------------------------------------------------------------------------------
	@Column(nullable = true)
	private Integer valorDeclaradoPorUnidad;

	@Column(nullable = false, length = 200)
	@NotNull
	private String notas;

	// ---------------------------------------------------------------------------------------------------------
	@Column(nullable = false)
	@NotNull
	private LocalDateTime fechaCreacion;

	@Column(nullable = false, length = 50)
	@NotNull
	private String usuarioCreacion;

	@Column(nullable = false, length = 50)
	@NotNull
	private LocalDateTime fechaActualizacion;

	@Column(nullable = false, length = 50)
	@NotNull
	private String usuarioActualizacion;

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
		this.setUnidad(null);
		this.setUnidadCodigo("");
		this.setTipoContenido(null);
		this.setTipoContenidoCodigo("");
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
		this.setRequerimientoPinado("");
		this.setPredistribucionDestinoFinal("");
		this.setPredistribucionRotulo("");
		this.setValorDeclaradoPorUnidad(null);
		this.setNotas("");
		this.setFechaCreacion(null);
		this.setUsuarioCreacion("");
		this.setFechaActualizacion(null);
		this.setUsuarioActualizacion("");
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + numeroItem;
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
		return true;
	}
}
