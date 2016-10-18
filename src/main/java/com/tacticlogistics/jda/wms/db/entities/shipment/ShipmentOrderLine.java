package com.tacticlogistics.jda.wms.db.entities.shipment;

import java.math.BigInteger;
import java.sql.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "uc_shipment_line_order_tcc_wms")
public class ShipmentOrderLine {
	
	@DatabaseField
	private BigInteger transaction_id;
	@DatabaseField
	private String ordnum;
	@DatabaseField
	private String wh_id;
	@DatabaseField
	private String client_id;
	@DatabaseField
	private String ordlin;
	@DatabaseField
	private int ordsln;
	@DatabaseField
	private String prtnum;
	@DatabaseField
	private int ordqty;
	@DatabaseField
	private String orgcod;
	@DatabaseField
	private String lotnum;
	@DatabaseField
	private Date expire_dte;
	@DatabaseField
	private Date mandte;
	@DatabaseField
	private boolean splflg;
	@DatabaseField
	private boolean bckflg;
	@DatabaseField
	private String invsts;
	@DatabaseField
	private String inv_attr_str1;
	@DatabaseField
	private String inv_attr_str2;
	@DatabaseField
	private String inv_attr_str3;
	@DatabaseField
	private String inv_attr_str4;
	@DatabaseField
	private String inv_attr_str5;
	@DatabaseField
	private String inv_attr_str6;
	@DatabaseField
	private String inv_attr_str7;
	@DatabaseField
	private String inv_attr_str8;
	@DatabaseField
	private String inv_attr_str9;
	@DatabaseField
	private String inv_attr_str10;
	
	public ShipmentOrderLine(){
		
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

	public String getOrdlin() {
		return ordlin;
	}

	public void setOrdlin(String ordlin) {
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

	public String getOrgcod() {
		return orgcod;
	}

	public void setOrgcod(String orgcod) {
		this.orgcod = orgcod;
	}

	public String getLotnum() {
		return lotnum;
	}

	public void setLotnum(String lotnum) {
		this.lotnum = lotnum;
	}

	public Date getExpire_dte() {
		return expire_dte;
	}

	public void setExpire_dte(Date expire_dte) {
		this.expire_dte = expire_dte;
	}

	public Date getMandte() {
		return mandte;
	}

	public void setMandte(Date mandte) {
		this.mandte = mandte;
	}

	public boolean isSplflg() {
		return splflg;
	}

	public void setSplflg(boolean splflg) {
		this.splflg = splflg;
	}

	public boolean isBckflg() {
		return bckflg;
	}

	public void setBckflg(boolean bckflg) {
		this.bckflg = bckflg;
	}

	public String getInvsts() {
		return invsts;
	}

	public void setInvsts(String invsts) {
		this.invsts = invsts;
	}

	public String getInv_attr_str1() {
		return inv_attr_str1;
	}

	public void setInv_attr_str1(String inv_attr_str1) {
		this.inv_attr_str1 = inv_attr_str1;
	}

	public String getInv_attr_str2() {
		return inv_attr_str2;
	}

	public void setInv_attr_str2(String inv_attr_str2) {
		this.inv_attr_str2 = inv_attr_str2;
	}

	public String getInv_attr_str3() {
		return inv_attr_str3;
	}

	public void setInv_attr_str3(String inv_attr_str3) {
		this.inv_attr_str3 = inv_attr_str3;
	}

	public String getInv_attr_str4() {
		return inv_attr_str4;
	}

	public void setInv_attr_str4(String inv_attr_str4) {
		this.inv_attr_str4 = inv_attr_str4;
	}

	public String getInv_attr_str5() {
		return inv_attr_str5;
	}

	public void setInv_attr_str5(String inv_attr_str5) {
		this.inv_attr_str5 = inv_attr_str5;
	}

	public String getInv_attr_str6() {
		return inv_attr_str6;
	}

	public void setInv_attr_str6(String inv_attr_str6) {
		this.inv_attr_str6 = inv_attr_str6;
	}

	public String getInv_attr_str7() {
		return inv_attr_str7;
	}

	public void setInv_attr_str7(String inv_attr_str7) {
		this.inv_attr_str7 = inv_attr_str7;
	}

	public String getInv_attr_str8() {
		return inv_attr_str8;
	}

	public void setInv_attr_str8(String inv_attr_str8) {
		this.inv_attr_str8 = inv_attr_str8;
	}

	public String getInv_attr_str9() {
		return inv_attr_str9;
	}

	public void setInv_attr_str9(String inv_attr_str9) {
		this.inv_attr_str9 = inv_attr_str9;
	}

	public String getInv_attr_str10() {
		return inv_attr_str10;
	}

	public void setInv_attr_str10(String inv_attr_str10) {
		this.inv_attr_str10 = inv_attr_str10;
	}

	
}
