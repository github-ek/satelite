package com.tacticlogistics.clientes.dicermex.compras.almacenamiento.recibos;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class AcuseReciboDto {
	private int id;
	private boolean error;
	private String mensaje;
}
