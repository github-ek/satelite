package com.tacticlogistics.presentation.api.clientes.dicermex.compras;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tacticlogistics.application.dto.common.MensajesDto;
import com.tacticlogistics.clientes.dicermex.compras.prealertas.PreAlertasService;
import com.tacticlogistics.presentation.util.BadRequestException;

@CrossOrigin	
@RestController
@RequestMapping("/oms/dicermex")
public class DicermexController {

	@Autowired
	private PreAlertasService comprasService;
	
	@RequestMapping(value = "/ordenes/test", method = RequestMethod.GET)
	public OrdenDeCompraDto test() {
		return OrdenDeCompraDto
			.builder()
			.centroOperacion("100")
			.consecutivoDocumento("1111111")
			.fechaDocumento("29991010")
			.terceroProveedor("PROVIDER")
			.notasDocumento("NOTA")
			.sucursalProveedor("100")
			.terceroCompradorId("EL QUE DIGITÓ")
			.numeroDocumentoReferencia("123456789012")
			.monedaDocumento("COP")
			.monedaConversion("COP")
			.centroOperacionOrdenCompra("100")
			.tipoDocumentoOrdenCompra("OC")
			.consecutivoDocumentoOrdenCompra("1111111")
			.linea(
				LineaOrdenDeCompraDto
				.builder()
				.centroOperacion("100")
				.consecutivoDocumento("1111111")
				.numeroRegistro(1)
				.bodegaId("DBOG")
				.ubicacionId("UBICACION")
				.loteId("LOTE")
				.unidadMedida("UND")
				.fechaEntrega("20161006")
				.cantidad(10)
				.notasMovimiento("NOTAS")
				.itemId("555")
				.talla("NPI")
				.color("NPI")
				.centroCosto("EL CENTRO")
				.proyectoId("EL PROYECTO")
				.build()
					)
			.linea(
				LineaOrdenDeCompraDto
				.builder()
				.centroOperacion("100")
				.consecutivoDocumento("1111111")
				.numeroRegistro(2)
				.bodegaId("DBOG")
				.ubicacionId("UBICACION")
				.loteId("LOTE")
				.unidadMedida("UND")
				.fechaEntrega("20161006")
				.cantidad(3)
				.notasMovimiento("NOTAS")
				.itemId("544")
				.talla("NPI")
				.color("NPI")
				.centroCosto("EL CENTRO")
				.proyectoId("EL PROYECTO")
				.build()
					)
			.build();
	}
	
	@RequestMapping(value = "/ordenes/compras", method = RequestMethod.POST)
	public MensajesDto preAlertarOrConfirmarOrdenDeCompra(@RequestBody OrdenDeCompraDto dto) {
		MensajesDto mensajes = new MensajesDto();
		try {
			return comprasService.alertarOrdenDeCompra(dto);
		} catch (Exception e) {
			mensajes.addMensaje(e, dto);
			throw new BadRequestException(mensajes);
		}
	}
}
