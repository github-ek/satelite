package com.dicermex.services.compras.wms.recibos;

import java.math.BigInteger;
import java.util.List;

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
	private BigInteger id;
	private String numeroOrdenWms;
	private String clienteCodigoWms;
	private String bodegaCodigo;
	private List<LineaResultadoReciboDto> lineas;
}
