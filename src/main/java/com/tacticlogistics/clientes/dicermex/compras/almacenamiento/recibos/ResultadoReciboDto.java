package com.tacticlogistics.clientes.dicermex.compras.almacenamiento.recibos;

import java.util.Set;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ResultadoReciboDto {
	private int id;
	private String numeroOrdenWms;
	private String clienteCodigoWms;
	private String bodegaCodigo;
	private Set<LineaResultadoReciboDto> lineas;
}
