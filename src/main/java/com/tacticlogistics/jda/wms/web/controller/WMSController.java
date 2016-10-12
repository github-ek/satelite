package com.tacticlogistics.jda.wms.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.tacticlogistics.jda.wms.web.controller.entities.PurchaseOrder;
import com.tacticlogistics.jda.wms.web.controller.entities.PurchaseOrderContainer;
import com.tacticlogistics.jda.wms.web.controller.entities.PurchaseOrderLine;
import com.tacticlogistics.jda.wms.web.controller.entities.ShipmentOrder;
import com.tacticlogistics.jda.wms.web.controller.entities.ShipmentOrderContainer;
import com.tacticlogistics.jda.wms.web.controller.entities.ShipmentOrderLine;

@RestController
@RequestMapping("/wms/jda")
public class WMSController {

	protected final Logger log = LoggerFactory.getLogger(getClass()); 
	
	@RequestMapping(value = "/purchaseOrder", method = RequestMethod.POST)
	public String confirmPurchaseOrder(@RequestBody PurchaseOrderContainer purchaseOrder) {
		try {
			System.out.println(purchaseOrder.toString());
				
			return "Great :D";
		} catch (Exception e) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
		}	
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
