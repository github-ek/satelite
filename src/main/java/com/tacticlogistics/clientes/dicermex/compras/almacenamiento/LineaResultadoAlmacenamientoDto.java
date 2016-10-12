package com.tacticlogistics.clientes.dicermex.compras.almacenamiento;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class LineaResultadoAlmacenamientoDto {
	private int numeroItem;
	private int numeroSubLinea;
	private String productoCodigo;
	private String estadoInventarioId;
	private int cantidadPlanificada;
	private int cantidadReal;
}
