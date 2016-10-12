package com.tacticlogistics.application.services.clientes.dicermex;

import com.tacticlogistics.application.services.clientes.dicermex.ResultadoPreAlertaOrdenDeCompraDto.ResultadoPreAlertaOrdenDeCompraDtoBuilder;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class LineaResultadoAlmacenamientoOrdenDeCompraDto {
	private int numeroItem;
	private int numeroSubLinea;
	private String productoCodigo;
	private String estadoInventarioId;
	private int cantidadPlanificada;
	private int cantidadReal;
}
