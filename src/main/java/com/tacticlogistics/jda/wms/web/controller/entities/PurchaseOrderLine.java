package com.tacticlogistics.jda.wms.web.controller.entities;

public class PurchaseOrderLine {

	int invlin;
	int invsln;
	String prtnum;
	String rcvsts;
	int expqty;
	int rcvqty;

	public PurchaseOrderLine() {
	}

	public PurchaseOrderLine(int invlin, int invsln, String prtnum, String rcvsts, int expqty, int rcvqty) {
		super();
		this.invlin = invlin;
		this.invsln = invsln;
		this.prtnum = prtnum;
		this.rcvsts = rcvsts;
		this.expqty = expqty;
		this.rcvqty = rcvqty;
	}

	public int getInvlin() {
		return invlin;
	}

	public void setInvlin(int invlin) {
		this.invlin = invlin;
	}

	public int getInvsln() {
		return invsln;
	}

	public void setInvsln(int invsln) {
		this.invsln = invsln;
	}

	public String getPrtnum() {
		return prtnum;
	}

	public void setPrtnum(String prtnum) {
		this.prtnum = prtnum;
	}

	public String getRcvsts() {
		return rcvsts;
	}

	public void setRcvsts(String rcvsts) {
		this.rcvsts = rcvsts;
	}

	public int getExpqty() {
		return expqty;
	}

	public void setExpqty(int expqty) {
		this.expqty = expqty;
	}

	public int getRcvqty() {
		return rcvqty;
	}

	public void setRcvqty(int rcvqty) {
		this.rcvqty = rcvqty;
	}

	@Override
	public String toString() {
		return "PurchaseOrderLine [invlin=" + invlin + ", invsln=" + invsln + ", prtnum=" + prtnum + ", rcvsts="
				+ rcvsts + ", expqty=" + expqty + ", rcvqty=" + rcvqty +"]";
	}

}
