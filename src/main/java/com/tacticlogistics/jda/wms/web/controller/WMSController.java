package com.tacticlogistics.jda.wms.web.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.tacticlogistics.application.dto.common.MensajesDto;
import com.tacticlogistics.clientes.dicermex.compras.wms.alertas.AlertasWmsService;
import com.tacticlogistics.clientes.dicermex.compras.wms.recibos.AcuseReciboDto;
import com.tacticlogistics.clientes.dicermex.compras.wms.recibos.LineaResultadoReciboDto;
import com.tacticlogistics.clientes.dicermex.compras.wms.recibos.RecibosService;
import com.tacticlogistics.clientes.dicermex.compras.wms.recibos.ResultadoReciboDto;
import com.tacticlogistics.domain.model.common.SeveridadType;
import com.tacticlogistics.jda.wms.web.controller.entities.PurchaseOrder;
import com.tacticlogistics.jda.wms.web.controller.entities.PurchaseOrderContainer;
import com.tacticlogistics.jda.wms.web.controller.entities.PurchaseOrderLine;
import com.tacticlogistics.jda.wms.web.controller.entities.ShipmentOrder;
import com.tacticlogistics.jda.wms.web.controller.entities.ShipmentOrderContainer;
import com.tacticlogistics.jda.wms.web.controller.entities.ShipmentOrderLine;
import com.tacticlogistics.presentation.util.BadRequestException;

@RestController
@RequestMapping("/wms/jda")
public class WMSController {

	protected final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private RecibosService reciboService;

	@RequestMapping(value = "/purchaseOrder", method = RequestMethod.POST)
	public MensajesDto confirmPurchaseOrder(@RequestBody PurchaseOrderContainer purchaseOrder) {
		MensajesDto mensaje = new MensajesDto();
		try {
			// @formatter:off

			System.out.println(purchaseOrder.toString());
			List<ResultadoReciboDto> resultados = new ArrayList<>();
			for (PurchaseOrder po : purchaseOrder.getPurchaseOrders()) {
				ResultadoReciboDto dto = ResultadoReciboDto
						.builder()
						.bodegaCodigo(po.getWh_id())
						.clienteCodigoWms(po.getClient_id())
						.numeroOrdenWms(po.getPo_num())
						.build();
				
				List<LineaResultadoReciboDto> dtoLines = new ArrayList<>();
				for (PurchaseOrderLine line : po.getLines()) {
					
					LineaResultadoReciboDto dtoLine = LineaResultadoReciboDto
							.builder()
							.numeroItem(line.getInvlin())
							.productoCodigo(line.getPrtnum())
							.cantidadPlanificada(line.getExpqty())
							.cantidadReal(line.getRcvqty())
							.estadoInventarioId(line.getRcvsts())
							.build();
					
					dtoLines.add(dtoLine);
				}
				
				dto.setLineas(dtoLines);
				resultados.add(dto);
				
			}

			List<AcuseReciboDto> responses_ = reciboService.confirmarReciboDeOrdenesDeCompra(resultados);
			
			for (AcuseReciboDto response : responses_) {
				if (response.isError()) {
					mensaje.add(SeveridadType.ERROR, response.getMensaje());
					throw new BadRequestException(mensaje);
				}
			}
			mensaje.add(SeveridadType.INFO, "Orden confirmada exitosamente");
			return mensaje;
		} catch (Exception e) {
			mensaje.add(e);
			throw new BadRequestException(mensaje);
		}
		// @formatter:on
	}

	@RequestMapping(value = "/purchaseOrder/test", method = RequestMethod.GET)
	public PurchaseOrderContainer getPurchaseOrder() {

		PurchaseOrder purchaseOrder = new PurchaseOrder("TRK001", "SUPA", "WMD1", "CLIENTA");

		purchaseOrder.getLines().add(new PurchaseOrderLine(1, 0, "ANTACID", "A", 1000, 50));
		purchaseOrder.getLines().add(new PurchaseOrderLine(2, 0, "T001", "A", 2000, 80));

		PurchaseOrderContainer container = new PurchaseOrderContainer();
		container.getPurchaseOrders().add(purchaseOrder);

		return container;
	}

	@RequestMapping(value = "/shipmentOrder", method = RequestMethod.POST)
	public String confirmShipmentOrder(@RequestBody ShipmentOrderContainer shipmentOrder) {
		try {
			System.out.println(shipmentOrder.toString());

			return "Great :D";
		} catch (Exception e) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/shipmentOrder/test", method = RequestMethod.GET)
	public ShipmentOrderContainer getShipmentOrder() {

		ShipmentOrder shipmentOrder = new ShipmentOrder("ORD001", "WMD1", "CLIENTA");

		shipmentOrder.getLines().add(new ShipmentOrderLine(1, 0, "ANTACID", 100, 100, "A", "----"));
		shipmentOrder.getLines().add(new ShipmentOrderLine(2, 0, "T001", 200, 80, "A", "----"));

		ShipmentOrderContainer container = new ShipmentOrderContainer();
		container.getShipmentOrders().add(shipmentOrder);

		return container;
	}

}
