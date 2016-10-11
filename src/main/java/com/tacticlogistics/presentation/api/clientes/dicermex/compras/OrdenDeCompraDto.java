package com.tacticlogistics.presentation.api.clientes.dicermex.compras;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.Singular;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class OrdenDeCompraDto {
	@NonNull
	private String centroOperacion;
	@NonNull
	private String consecutivoDocumento;
	@NonNull
	private String fechaDocumento;
	@NonNull
	private String terceroProveedor;
	@NonNull
	private String notasDocumento;
	@NonNull
	private String sucursalProveedor;
	@NonNull
	private String terceroCompradorId;
	@NonNull
	private String numeroDocumentoReferencia;
	@NonNull
	private String monedaDocumento;
	@NonNull
	private String monedaConversion;
	@NonNull
	private String centroOperacionOrdenCompra;
	@NonNull
	private String tipoDocumentoOrdenCompra;
	@NonNull
	private String consecutivoDocumentoOrdenCompra;
	
	@Singular
	@Setter(AccessLevel.NONE)
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
