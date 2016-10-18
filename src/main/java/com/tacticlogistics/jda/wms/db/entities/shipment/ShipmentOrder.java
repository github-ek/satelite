package com.tacticlogistics.jda.wms.db.entities.shipment;

import java.math.BigInteger;
import java.sql.Date;
import java.util.List;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "uc_shipment_order_tcc_wms")
public class ShipmentOrder {

	public int orderId;
	@DatabaseField
	private BigInteger transaction_id;
	@DatabaseField
	private String ordnum;
	@DatabaseField
	private String stcust;
	@DatabaseField
	private String btcust;
	@DatabaseField
	private String rtcust;
	@DatabaseField
	private String wh_id;
	@DatabaseField
	private String client_id;
	@DatabaseField
	private String ordtyp;
	@DatabaseField
	private Date entdte;
	
	private List<ShipmentOrderLine> lines;
	
	public List<ShipmentOrderLine> getLines() {
		return lines;
	}
	
	public void addLine(ShipmentOrderLine line){
		line.setOrdnum(this.ordnum);
		line.setTransaction_id(this.transaction_id);
		line.setWh_id(this.wh_id);
		line.setClient_id(this.client_id);
		this.lines.add(line);
	}

	public BigInteger getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(BigInteger transaction_id) {
		this.transaction_id = transaction_id;
	}

	public String getOrdnum() {
		return ordnum;
	}

	public void setOrdnum(String ordnum) {
		this.ordnum = ordnum;
	}

	public String getStcust() {
		return stcust;
	}

	public void setStcust(String stcust) {
		this.stcust = stcust;
	}

	public String getBtcust() {
		return btcust;
	}

	public void setBtcust(String btcust) {
		this.btcust = btcust;
	}

	public String getRtcust() {
		return rtcust;
	}

	public void setRtcust(String rtcust) {
		this.rtcust = rtcust;
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

	public String getOrdtyp() {
		return ordtyp;
	}

	public void setOrdtyp(String ordtyp) {
		this.ordtyp = ordtyp;
	}

	public Date getEntdte() {
		return entdte;
	}

	public void setEntdte(Date entdte) {
		this.entdte = entdte;
	}

	public void setLines(List<ShipmentOrderLine> lines) {
		this.lines = lines;
	}

	

}
