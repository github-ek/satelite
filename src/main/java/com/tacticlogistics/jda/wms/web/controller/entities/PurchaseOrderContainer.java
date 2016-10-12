package com.tacticlogistics.jda.wms.web.controller.entities;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "PURCHASE_ORDERS")
@XmlAccessorType(XmlAccessType.FIELD)
public class PurchaseOrderContainer {

	
	@XmlElement(name = "purchase_order")
	List<PurchaseOrder> purchaseOrders = new ArrayList<PurchaseOrder>();
	
	public List<PurchaseOrder> getPurchaseOrders() {
		return purchaseOrders;
	}

	public void setPurchaseOrders(List<PurchaseOrder> purchaseOrders) {
		this.purchaseOrders = purchaseOrders;
	}

	public PurchaseOrderContainer(){
		
	}

	@Override
	public String toString() {
		return "PurchaseOrderContainer [purchaseOrders=" + purchaseOrders + "]";
	}
	
	
	
}
