package com.tacticlogistics.application.etl.gws.ordenes.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class OrdenDTO {
	@Size(max = 50)
	@NotBlank
	public String servicioCodigoAlterno;
	@Size(max = 20)
	@NotBlank
	public String numeroOrdenDeCompra;
	@Size(max = 10)
	@NotBlank
	public String prefijoOrden;
	@Size(max = 10)
	@NotBlank
	public String numeroOrden;

	@Size(max = 20)
	@NotBlank
	public String destinatarioIdentificacion;
	@Size(max = 50)
	@NotBlank
	public String canalCodigoAlterno;

	@Size(max = 100)
	public String destinoNombre;
	@Size(max = 150)
	@NotBlank
	public String destinoDireccion;
	@Size(min = 5, max = 5)
	@NotBlank
	public String ciudadDestinoCodigoAlterno;

	@Pattern(regexp = "\\d{1,2}/\\d{1,2}/\\d{4}")
	@NotBlank
	public String fechaMinima;
	@Pattern(regexp = "\\d{1,2}/\\d{1,2}/\\d{4}")
	@NotBlank
	public String fechaMaxima;
	@Pattern(regexp = "\\{1,2}:\\{1,2}")
	public String horaMinima;
	@Pattern(regexp = "\\{1,2}:\\{1,2}")
	public String horaMaxima;

	@Size(max = 200)
	@NotNull
	public String notas;

	@Size(max = 50)
	@NotBlank
	public String productoCodigo;
	@Size(max = 200)
	@NotBlank
	public String descripcion;
	@Digits(integer = 8, fraction = 0)
	public String cantidadSolicitada;
	@Size(max = 20)
	public String bodegaOrigenCodigoAlterno;
	@Size(max = 20)
	public String bodegaDestinoCodigoAlterno;
	@Digits(integer = 8, fraction = 0)
	public String valorDeclaradoPorUnidad;

	@Size(max = 100)
	public String predistribucionDestinoFinal;
	@Size(max = 100)
	public String predistribucionRotulo;

	@Digits(integer = 8, fraction = 0)
	public String valorRecaudo;

	public OrdenDTO() {
	}
}