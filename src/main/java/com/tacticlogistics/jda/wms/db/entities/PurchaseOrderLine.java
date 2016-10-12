package com.tacticlogistics.jda.wms.db.entities;

import java.math.BigInteger;
import java.sql.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "uc_purchase_order_line_tcc_wms")
public class PurchaseOrderLine {
	
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
	private String invlin;
	@DatabaseField
	private int invsln;
	@DatabaseField
	private String prtnum;
	@DatabaseField
	private int expqty;
	@DatabaseField
	private String orgcod;
	@DatabaseField
	private String lotnum;
	@DatabaseField
	private Date expire_dte;
	@DatabaseField
	private Date mandte;
	@DatabaseField
	private String rcvsts;
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
	
	public PurchaseOrderLine(){
		
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

	public String getInvlin() {
		return invlin;
	}
	public void setInvlin(String invlin) {
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
	public int getExpqty() {
		return expqty;
	}
	public void setExpqty(int expqty) {
		this.expqty = expqty;
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
	public String getRcvsts() {
		return rcvsts;
	}
	public void setRcvsts(String rcvsts) {
		this.rcvsts = rcvsts;
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

	@Override
	public String toString() {
		return "PurchaseOrderLine [invlin=" + invlin + ", invsln=" + invsln + ", prtnum=" + prtnum + ", expqty=" + expqty
				+ ", orgcod=" + orgcod + ", lotnum=" + lotnum + ", expire_dte=" + expire_dte + ", mandte=" + mandte
				+ ", rcvsts=" + rcvsts + ", inv_attr_str1=" + inv_attr_str1 + ", inv_attr_str2=" + inv_attr_str2
				+ ", inv_attr_str3=" + inv_attr_str3 + ", inv_attr_str4=" + inv_attr_str4 + ", inv_attr_str5="
				+ inv_attr_str5 + ", inv_attr_str6=" + inv_attr_str6 + ", inv_attr_str7=" + inv_attr_str7
				+ ", inv_attr_str8=" + inv_attr_str8 + ", inv_attr_str9=" + inv_attr_str9 + ", inv_attr_str10="
				+ inv_attr_str10 + "]";
	}
	
	

}
