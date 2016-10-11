package com.tacticlogistics.domain.model.clientes.dicermex;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.tacticlogistics.domain.model.ordenes.Orden;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "compras", catalog = "dicermex")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class OrdenDeCompra implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "id_orden")
	@NotNull
	private Orden orden;

	@Column(name = "CENTRO_OPERACION", nullable = false, length = 3)
	@NotNull
	private String centroOperacion;

	@Column(name = "CONSEC_DOCTO", nullable = false, length = 8)
	@NotNull
	private String consecutivoDocumento;

	@Column(name = "FECHA_DOCTO", nullable = false, length = 8)
	@NotNull
	private String fechaDocumento;

	@Column(name = "TERCERO_PROVEEDOR", nullable = false, length = 15)
	@NotNull
	private String terceroProveedor;

	@Column(name = "NOTAS_DOCTO", nullable = false, length = 255)
	@NotNull
	private String notasDocumento;

	@Column(name = "SUCURSAL_PROVEEDOR", nullable = false, length = 3)
	@NotNull
	private String sucursalProveedor;

	@Column(name = "ID_TERCERO_COMPRADOR", nullable = false, length = 15)
	@NotNull
	private String terceroCompradorId;

	@Column(name = "NUM_DOCTO_REF", nullable = false, length = 12)
	@NotNull
	private String numeroDocumentoReferencia;

	@Column(name = "MONEDA_DOCTO", nullable = false, length = 3)
	@NotNull
	private String monedaDocumento;

	@Column(name = "MONEDA_CONVERSION", nullable = false, length = 3)
	@NotNull
	private String monedaConversion;

	@Column(name = "CO_OC", nullable = false, length = 3)
	@NotNull
	private String centroOperacionOrdenCompra;

	@Column(name = "TIPO_DOCTO_OC", nullable = false, length = 3)
	@NotNull
	private String tipoDocumentoOrdenCompra;

	@Column(name = "CONSEC_DOCTO_OC", nullable = false, length = 8)
	@NotNull
	private String consecutivoDocumentoOrdenCompra;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "id_orden", nullable = false)
	private Set<LineaOrdenDeCompra> lineas;
}
