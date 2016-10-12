package com.tacticlogistics.application.services.clientes.dicermex;

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
public class ResultadoAlmacenamientoOrdenDeCompraDto {
	private String numeroOrdenWms;
	private String clienteCodigoWms;
	private String bodegaCodigo;
	private String proveedorCodigo;
	private Set<LineaResultadoAlmacenamientoOrdenDeCompraDto> lineas;
}
