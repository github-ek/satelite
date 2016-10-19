package com.tacticlogistics.clientes.dicermex.compras.erp.prealertas.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class OrdenDeCompraDTO {
	@NotBlank
	@Size(min = 1, max = 3)
	private String centroOperacion;

	@NotBlank
	@Digits(integer = 8, fraction = 0)
	private String consecutivoDocumento;

	@NotBlank
	@Digits(integer = 8, fraction = 0)
	@Pattern(regexp="(20[1-9][0-9])((0[1-9])|(1[0-2]))((0[1-9])|(1[0-9])|(2[0-9])|(3[0-1]))")
	private String fechaDocumento;

	@NotBlank
	@Size(min = 1, max = 15)
	private String terceroProveedor;

	@NotNull
	@Size(min = 0, max = 255)
	private String notasDocumento;

	@NotBlank
	@Size(min = 1, max = 3)
	private String sucursalProveedor;

	@NotBlank
	@Size(min = 1, max = 15)
	private String terceroCompradorId;

	@NotNull
	@Size(min = 0, max = 0	)
	private String numeroDocumentoReferencia;

	@NotBlank
	@Size(min = 1, max = 3)
	private String monedaDocumento;

	@NotBlank
	@Size(min = 1, max = 3)
	private String monedaConversion;

	@NotBlank
	@Size(min = 1, max = 3)
	private String centroOperacionOrdenCompra;

	@NotBlank
	@Size(min = 1, max = 3)
	private String tipoDocumentoOrdenCompra;

	@NotBlank
	@Digits(integer = 8, fraction = 0)
	private String consecutivoDocumentoOrdenCompra;

	@Singular
	@Setter(AccessLevel.NONE)
	@NotEmpty
	private List<LineaOrdenDeCompraDto> lineas;

	@Setter(AccessLevel.NONE)
	@JsonIgnore
	private String numeroOrden;

	@JsonIgnore
	public String getNumeroOrden() {
		if (this.numeroOrden == null) {
			this.numeroOrden = String.format("%s-%s-%s", this.centroOperacion, this.tipoDocumentoOrdenCompra,
					this.consecutivoDocumento);
		}
		return this.numeroOrden;
	}

	@JsonIgnore
	public LocalDate getFechaOrdenFromFechaDocumento() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		return LocalDate.parse(getFechaDocumento(), formatter);
	}
}
