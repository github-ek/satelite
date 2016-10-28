package com.dicermex.domain.model.compras;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Table(name = "lineas_compra", catalog = "dicermex")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class LineaOrdenDeCompra implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_linea_compra", unique = true, nullable = false)
	private Integer id;

	@Column(name = "CO_MOVTO", nullable = false, length = 3)
	@NotNull
	@NonNull
	private String centroOperacion;

	@Column(name = "CONSEC_DOCTO_MOV", nullable = false, length = 8)
	@NotNull
	@NonNull
	private String consecutivoDocumento;

	@Column(name = "NUM_REGISTRO", nullable = false)
	private int numeroRegistro;

	@Column(name = "ID_BODEGA", nullable = false, length = 5)
	@NotNull
	@NonNull
	private String bodegaId;

	@Column(name = "ID_UBICACION", nullable = false, length = 10)
	@NotNull
	@NonNull
	private String ubicacionId;

	@Column(name = "ID_LOTE", nullable = false, length = 15)
	@NotNull
	@NonNull
	private String loteId;

	@Column(name = "UNIDAD_MEDIDA", nullable = false, length = 4)
	@NotNull
	@NonNull
	private String unidadMedida;

	@Column(name = "FECHA_ENTREGA", nullable = false, length = 8)
	@NotNull
	@NonNull
	private String fechaEntrega;

	@Column(name = "CANTIDAD", nullable = false)
	private int cantidad;

	@Column(name = "NOTAS_MOVIMIENTO", nullable = false, length = 255)
	@NotNull
	@NonNull
	private String notasMovimiento;

	@Column(name = "ID_ITEM", nullable = false, length = 7)
	@NotNull
	@NonNull
	private String itemId;

	@Column(name = "EXT1", nullable = false, length = 20)
	@NotNull
	@NonNull
	private String talla;

	@Column(name = "EXT2", nullable = false, length = 20)
	@NotNull
	@NonNull
	private String color;

	@Column(name = "CCOSTO_MOVTO", nullable = false, length = 15)
	@NotNull
	@NonNull
	private String centroCosto;

	@Column(name = "ID_PROYECTO", nullable = false, length = 15)
	@NotNull
	@NonNull
	private String proyectoId;
}
