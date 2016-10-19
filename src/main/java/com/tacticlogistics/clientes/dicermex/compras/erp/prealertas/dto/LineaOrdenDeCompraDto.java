package com.tacticlogistics.clientes.dicermex.compras.erp.prealertas.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

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
	@NotBlank
	@Size(min = 1, max = 3)
	private String centroOperacion;
	
	@NotBlank
	@Digits(integer = 8, fraction = 0)
	private String consecutivoDocumento;
	
	@Null
	private Integer numeroRegistro;
	
	@NotBlank
	@Size(min = 1, max = 5)
	private String bodegaId;
	
	@NotBlank
	@Size(min = 1, max = 10)
	private String ubicacionId;

	@NotBlank
	@Size(min = 1, max = 15)
	private String loteId;

	@NotBlank
	@Size(min = 1, max = 4)
	private String unidadMedida;
	
	@NotBlank
	@Digits(integer = 8, fraction = 0)
	@Pattern(regexp="(20[1-9][0-9])((0[1-9])|(1[0-2]))((0[1-9])|(1[0-9])|(2[0-9])|(3[0-1]))")
	private String fechaEntrega;
	
	private int cantidad;
	
	@NotNull
	@Size(min = 0, max = 255)
	private String notasMovimiento;
	
	@NotBlank
	@Size(min = 1, max = 7)
	@Pattern(regexp="[0-9]{1,7}")
	private String itemId;
	
	@NotBlank
	@Size(min = 1, max = 20)	
	private String talla;
	@NotBlank
	@Size(min = 1, max = 20)	
	private String color;

	@NotBlank
	@Size(min = 1, max = 15)	
	private String centroCosto;
	
	@NotBlank
	@Size(min = 1, max = 15)	
	private String proyectoId;
}
