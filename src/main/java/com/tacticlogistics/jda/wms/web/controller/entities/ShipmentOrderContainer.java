package com.tacticlogistics.jda.wms.web.controller.entities;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "SHIPMENT_ORDERS")
@XmlAccessorType(XmlAccessType.FIELD)
public class ShipmentOrderContainer {

	
	@XmlElement(name = "shipment_order")
	List<ShipmentOrder> shipmentOrders = new ArrayList<ShipmentOrder>();
	

	public ShipmentOrderContainer(){
		
	}

	public List<ShipmentOrder> getShipmentOrders() {
		return shipmentOrders;
	}

	public void setShipmentOrders(List<ShipmentOrder> shipmentOrders) {
		this.shipmentOrders = shipmentOrders;
	}

	@Override
	public String toString() {
		return "ShipmentOrderContainer [shipmentOrders=" + shipmentOrders + "]";
	}
	
	
	
}
