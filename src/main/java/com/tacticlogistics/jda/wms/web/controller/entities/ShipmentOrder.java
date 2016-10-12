package com.tacticlogistics.jda.wms.web.controller.entities;

import java.util.ArrayList;
import java.util.List;

public class ShipmentOrder {

	String ordnum;
	String wh_id;
	String client_id;

	List<ShipmentOrderLine> lines = new ArrayList<ShipmentOrderLine>();

	public ShipmentOrder() {
	}

	public ShipmentOrder(String ordnum, String wh_id, String client_id) {
		super();
		this.ordnum = ordnum;
		this.wh_id = wh_id;
		this.client_id = client_id;
	}

	public List<ShipmentOrderLine> getLines() {
		return lines;
	}

	public void setLines(List<ShipmentOrderLine> lines) {
		this.lines = lines;
	}

	public String getOrdnum() {
		return ordnum;
	}

	public void setOrdnum(String ordnum) {
		this.ordnum = ordnum;
	}

	public String getWh_id() {
		return wh_id;
	}

	public void setWh_id(String wh_id) {
		this.wh_id = wh_id;
	}

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	@Override
	public String toString() {
		return "ShipmentOrder [ordnum=" + ordnum + ", wh_id=" + wh_id + ", client_id=" + client_id + ", lines=" + lines
				+ "]";
	}

}
