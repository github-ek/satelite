package com.dicermex.services.compras.wms.alertas;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ResultadoAlertaDto {
	private String numeroOrdenWms;
	private ResultadoAlertaType resultado;
	String mensaje;
}
