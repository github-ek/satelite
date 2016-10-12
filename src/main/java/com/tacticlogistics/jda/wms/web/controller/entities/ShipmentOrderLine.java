package com.tacticlogistics.jda.wms.web.controller.entities;

public class ShipmentOrderLine {

	int ordlin;
	int ordsln;
	String prtnum;
	int ordqty;
	int shpqty;
	String invsts;
	String lotnum;

	public ShipmentOrderLine() {
	}

	public ShipmentOrderLine(int ordlin, int ordsln, String prtnum, int ordqty, int shpqty, String invsts,
			String lotnum) {
		super();
		this.ordlin = ordlin;
		this.ordsln = ordsln;
		this.prtnum = prtnum;
		this.ordqty = ordqty;
		this.shpqty = shpqty;
		this.invsts = invsts;
		this.lotnum = lotnum;
	}

	public int getOrdlin() {
		return ordlin;
	}

	public void setOrdlin(int ordlin) {
		this.ordlin = ordlin;
	}

	public int getOrdsln() {
		return ordsln;
	}

	public void setOrdsln(int ordsln) {
		this.ordsln = ordsln;
	}

	public String getPrtnum() {
		return prtnum;
	}

	public void setPrtnum(String prtnum) {
		this.prtnum = prtnum;
	}

	public int getOrdqty() {
		return ordqty;
	}

	public void setOrdqty(int ordqty) {
		this.ordqty = ordqty;
	}

	public int getShpqty() {
		return shpqty;
	}

	public void setShpqty(int shpqty) {
		this.shpqty = shpqty;
	}

	public String getInvsts() {
		return invsts;
	}

	public void setInvsts(String invsts) {
		this.invsts = invsts;
	}

	public String getLotnum() {
		return lotnum;
	}

	public void setLotnum(String lotnum) {
		this.lotnum = lotnum;
	}

	@Override
	public String toString() {
		return "ShipmentOrderLine [ordlin=" + ordlin + ", ordsln=" + ordsln + ", prtnum=" + prtnum + ", ordqty="
				+ ordqty + ", shpqty=" + shpqty + ", invsts=" + invsts + ", lotnum=" + lotnum + "]";
	}

}
