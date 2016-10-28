package com.dicermex.presentation.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dicermex.services.compras.erp.alertas.AlertasService;
import com.dicermex.services.compras.erp.alertas.AlertasService.ResultadoAlertaDTO;
import com.dicermex.services.compras.erp.alertas.AlertasService.ResultadoAlertaOrdenDeCompraType;
import com.dicermex.services.compras.erp.alertas.dto.OrdenDeCompraDTO;

@CrossOrigin
@RestController
@RequestMapping("/oms/dicermex/ordenes/compras")
public class ComprasController {
	@Autowired
	private AlertasService comprasService;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<ResultadoAlertaDTO> alertarOrdenDeCompra(@Validated @RequestBody OrdenDeCompraDTO dto) {
		ResultadoAlertaDTO mensajes;
		
		try {
			mensajes = comprasService.alertarOrdenDeCompra(dto);
		} catch (RuntimeException e) {
			mensajes = processException(e);
		}

		return processResult(mensajes);
	}

	private ResponseEntity<ResultadoAlertaDTO> processResult(ResultadoAlertaDTO mensajes) {
		HttpStatus status;
		switch (mensajes.getData()) {
		case CREADA:
			status = HttpStatus.CREATED;
			break;
		case CONFIRMADA:
			status = HttpStatus.OK;
			break;
		default:
			status = HttpStatus.BAD_REQUEST;
		}

		return new ResponseEntity<>(mensajes, status);
	}

	private ResultadoAlertaDTO processException(RuntimeException e) {
		ResultadoAlertaDTO mensajes = new ResultadoAlertaDTO(ResultadoAlertaOrdenDeCompraType.ERROR);
		mensajes.add(e);
		return mensajes;
	}
}
