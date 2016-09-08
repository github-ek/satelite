package com.tacticlogistics.presentation.api.clientes.dicermex.compras;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tacticlogistics.application.dto.common.MensajesDto;
import com.tacticlogistics.application.services.ordenes.OrdenesApplicationService;
import com.tacticlogistics.domain.model.common.SeveridadType;
import com.tacticlogistics.presentation.util.BadRequestException;

@CrossOrigin	
@RestController
@RequestMapping("/oms/dicermex")
public class DicermexController {

	@Autowired
	private OrdenesApplicationService ordenesService;
	
	@RequestMapping(value = "/ordenes/test", method = RequestMethod.GET)
	public OrdenCompraDto test() {
		List<LineaOrdenCompraDto> lineas = new ArrayList<>();
		lineas.add(new LineaOrdenCompraDto(new Date(), 10, "XXXXX", "UN", ".... .....  ..... ", "LARGE", "ROJO", "XXXX YYY", "PUENTE ARANDA", "UBICACION"));
		lineas.add(new LineaOrdenCompraDto(new Date(), 5, "ZZZZ", "UN", ".... .....  ..... ", "LARGE", "ROJO", "XXXX YYY", "PUENTE ARANDA", "UBICACION"));
		
		return new OrdenCompraDto("100", "OCN", "1111111", "XXX", lineas);
	}
	
	@RequestMapping(value = "/ordenes/compras", method = RequestMethod.POST)
	public MensajesDto crearOrdenDeCompra(@RequestBody OrdenCompraDto dto) {
		MensajesDto mensajes = new MensajesDto();
		try {
			System.out.println(dto.toString());
			//ETLOrdenDto orden = new ETLOrdenDto();
			//ordenesService.CrearOrdenCompra(dto);	
			mensajes.addMensaje(SeveridadType.INFO, "OK");
			return mensajes; 
		} catch (Exception e) {
			mensajes.addMensaje(e, dto);
			throw new BadRequestException(mensajes);
		}
	}
}
