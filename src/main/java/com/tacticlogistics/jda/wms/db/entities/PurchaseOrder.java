package com.tacticlogistics.jda.wms.db.entities;

import java.math.BigInteger;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import lombok.Data;

@DatabaseTable(tableName = "uc_purchase_order_tcc_wms")
public class PurchaseOrder {


	@DatabaseField
	private BigInteger transaction_id;
	@DatabaseField
	private String invnum;
	@DatabaseField
	private String supnum;
	@DatabaseField
	private String wh_id;
	@DatabaseField
	private String client_id;
	@DatabaseField
	private String rimsts;
	@DatabaseField
	private String invtyp;
	@DatabaseField
	private Date invdte;
	
	private List<PurchaseOrderLine> lines;
	
	public List<PurchaseOrderLine> getLines() {
		return lines;
	}
	
	public void addLine(PurchaseOrderLine line){
		line.setInvnum(this.invnum);
		line.setTransaction_id(this.transaction_id);
		line.setWh_id(this.wh_id);
		line.setClient_id(this.client_id);
		line.setSupnum(this.supnum);
		this.lines.add(line);
	}

	public void setLines(List<PurchaseOrderLine> lines) {
		this.lines = lines;
	}

	public PurchaseOrder() {
		lines = new ArrayList<>();
	}

	public BigInteger getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(BigInteger transaction_id) {
		this.transaction_id = transaction_id;
	}

	public String getInvnum() {
		return invnum;
	}

	public void setInvnum(String invnum) {
		this.invnum = invnum;
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

	public String getRimsts() {
		return rimsts;
	}

	public void setRimsts(String rimsts) {
		this.rimsts = rimsts;
	}

	public String getInvtyp() {
		return invtyp;
	}

	public void setInvtyp(String invtyp) {
		this.invtyp = invtyp;
	}

	public Date getInvdte() {
		return invdte;
	}

	public void setInvdte(Date invdte) {
		this.invdte = invdte;
	}

	@Override
	public String toString() {
		return "PurchaseOrder [transaction_id=" + transaction_id + ", invnum=" + invnum + ", supnum=" + supnum
				+ ", wh_id=" + wh_id + ", \n client_id=" + client_id + ", rimsts=" + rimsts + ", invtyp=" + invtyp
				+ ", invdte=" + invdte + ", lines: " + lines + " ]";
	}

}
