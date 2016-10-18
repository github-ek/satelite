package com.tacticlogistics.jda.wms.web.controller.entities;

import java.util.ArrayList;
import java.util.List;

public class PurchaseOrder {

	String po_num;
	String trknum;
	String supnum;
	String wh_id;
	String client_id;

	List<PurchaseOrderLine> lines = new ArrayList<PurchaseOrderLine>();

	public PurchaseOrder() {
	}

	public PurchaseOrder(String trknum, String supnum, String wh_id, String client_id) {
		super();
		this.trknum = trknum;
		this.supnum = supnum;
		this.wh_id = wh_id;
		this.client_id = client_id;
	}

	public List<PurchaseOrderLine> getLines() {
		return lines;
	}

	public void setLines(List<PurchaseOrderLine> lines) {
		this.lines = lines;
	}

	public String getPo_num() {
		return po_num;
	}

	public void setPo_num(String po_num) {
		this.po_num = po_num;
	}

	public String getTrknum() {
		return trknum;
	}

	public void setTrknum(String trknum) {
		this.trknum = trknum;
	}

	public String getSupnum() {
		return supnum;
	}

	public void setSupnum(String supnum) {
		this.supnum = supnum;
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
		return "PurchaseOrder [trknum=" + trknum + ", supnum=" + supnum + ", wh_id=" + wh_id + ", client_id="
				+ client_id + " Lines: " + lines.toString() + "]";
	}

}
