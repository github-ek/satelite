package com.dicermex.services.compras.wms.recibos;

import java.math.BigInteger;

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
	private BigInteger id;
	private boolean error;
	private String mensaje;
}
