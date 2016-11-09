package com.tacticlogistics.application.etl.dicermex.despachos.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class DespachoDTO {
	@Size(max = 200)
	@NotNull
	public String notas;
	@Size(max = 3)
	@NotBlank
	public String prefijoOrden;
	@Size(max = 16)
	@NotBlank
	public String numeroOrden;
	
	@Size(max = 20)
	@NotBlank
	public String destinatarioIdentificacion;
	@Size(max = 100)
	@NotBlank
	public String destinatarioNombre;
	
	@Size(max = 20)
	public String destinoCodigo;
	@Size(max = 100)
	public String destinoNombre;
	@Size(max = 50)
	public String destinoContactoTelefonos;
	@Size(max = 100)
	public String destinoContactoEmails;
	@Size(max = 150)
	@NotBlank
	public String destinoDireccion;
	@Size(min = 5, max = 5)
	@NotBlank
	public String ciudadDestinoCodigoAlterno;
	
	@Digits(integer = 8, fraction = 0)
	public String valorRecaudo;
	
	@Size(max = 50)
	@NotBlank
	public String canalCodigoAlterno;

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
	
	public DespachoDTO() {
	}
}