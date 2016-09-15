package com.tacticlogistics.presentation.api.clientes.dicermex;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tacticlogistics.application.dto.common.MensajesDto;
import com.tacticlogistics.application.dto.etl.ETLLineaOrdenDto;
import com.tacticlogistics.application.dto.etl.ETLOrdenDto;
import com.tacticlogistics.application.services.ordenes.OrdenesApplicationService;
import com.tacticlogistics.domain.model.common.SeveridadType;
import com.tacticlogistics.presentation.api.clientes.dicermex.compras.LineaOrdenCompraDto;
import com.tacticlogistics.presentation.api.clientes.dicermex.compras.OrdenCompraDto;
import com.tacticlogistics.presentation.util.BadRequestException;

@CrossOrigin
@RestController
@RequestMapping("/oms/dicermex")
public class DicermexController {

	@Autowired
	private OrdenesApplicationService ordenesService;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@RequestMapping(value = "/ordenes/test", method = RequestMethod.GET)
	public OrdenCompraDto test() {
		List<LineaOrdenCompraDto> lineas = new ArrayList<>();
		lineas.add(new LineaOrdenCompraDto(new Date(), 10, "XXXXX", "UN", ".... .....  ..... ", "LARGE", "ROJO",
				"XXXX YYY", "PUENTE ARANDA", "UBICACION"));
		lineas.add(new LineaOrdenCompraDto(new Date(), 5, "ZZZZ", "UN", ".... .....  ..... ", "LARGE", "ROJO",
				"XXXX YYY", "PUENTE ARANDA", "UBICACION"));

		return new OrdenCompraDto("100", "OCN", "1111111", "XXX", lineas);
	}

	@RequestMapping(value = "/ordenes/compras", method = RequestMethod.POST)
	public MensajesDto crearOrdenDeCompra(@RequestBody OrdenCompraDto dto) {
		MensajesDto mensajes = new MensajesDto();
		try {
			System.out.println(dto.toString());
			
			String folderPath = "C:\\Users\\cristianb\\Downloads\\test\\";
						
			//ordenesService.saveOrdenReciboPrimaria(parseOrden(dto));

			mensajes.addMensaje(SeveridadType.INFO, "OK");
			
			return mensajes;
		} catch (Exception e) {
			mensajes.addMensaje(e, dto);
			System.out.println(e);
			throw new BadRequestException(mensajes);
		}
		

	}

	private ETLOrdenDto parseOrden(OrdenCompraDto dto) {
		ETLOrdenDto orden = new ETLOrdenDto();
		
		// Convertir dto a Orden
		orden.setNumeroOrden(dto.getCentroOperacion() + dto.getTipoDocumento() + dto.getNumeroOrden());
		
		// Fecha entrega
		if (!dto.getLineas().isEmpty()) {
			orden.setFechaEntregaSugeridaMaxima(dto.getLineas().get(0).getFechaEntrega());
		}

		// Parse Lines data
		for(LineaOrdenCompraDto lineaDto: dto.getLineas()){
			orden.getLineas().add(parseLineaOrden(lineaDto));
		}

		return orden;
	}

	private ETLLineaOrdenDto parseLineaOrden(LineaOrdenCompraDto dto) {
		ETLLineaOrdenDto lineaOrden = new ETLLineaOrdenDto();
		lineaOrden.setUnidadCodigo(dto.getUnidadCodigo());
		lineaOrden.setCantidadSolicitada(dto.getCantidad());
		lineaOrden.setNotas(dto.getNotas());
		lineaOrden.setProductoCodigo(dto.getProductoCodigo());
		lineaOrden.setLote(dto.getLote());
		lineaOrden.setBodegaDestinoCodigo(dto.getBodegaDestinoCodigo());
		// TODO ubicación ID

		return lineaOrden;
	}
}
