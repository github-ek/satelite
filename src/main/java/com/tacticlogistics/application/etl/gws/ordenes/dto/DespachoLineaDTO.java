package com.tacticlogistics.application.etl.gws.ordenes.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class DespachoLineaDTO {
	@Size(max = 3)
	@NotBlank
	public String prefijoOrden;
	@Size(max = 16)
	@NotBlank
	public String numeroOrden;
	@Size(max = 50)
	@NotBlank
	public String productoCodigo;
	@Digits(integer = 8, fraction = 0)
	public String cantidadSolicitada;	
	@Size(max = 20)
	public String bodegaOrigenCodigoAlterno;
	@Size(max = 20)
	public String bodegaDestinoCodigoAlterno;

	public DespachoLineaDTO() {
	}
}