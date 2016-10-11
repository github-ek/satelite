package com.tacticlogistics.presentation.api.clientes.dicermex.compras;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class LineaOrdenDeCompraDto {
	private String centroOperacion;
	private String consecutivoDocumento;
	private int numeroRegistro;
	private String bodegaId;
	private String ubicacionId;
	private String loteId;
	private String unidadMedida;
	private String fechaEntrega;
	private int cantidad;
	private String notasMovimiento;
	private String itemId;
	private String talla;
	private String color;
	private String centroCosto;
	private String proyectoId;
}
