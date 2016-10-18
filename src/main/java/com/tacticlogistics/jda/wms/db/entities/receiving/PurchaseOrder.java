package com.tacticlogistics.jda.wms.db.entities.receiving;

import java.math.BigInteger;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "uc_purchase_order_tcc_wms")
public class PurchaseOrder {


	public int orderId;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((client_id == null) ? 0 : client_id.hashCode());
		result = prime * result + ((invdte == null) ? 0 : invdte.hashCode());
		result = prime * result + ((invnum == null) ? 0 : invnum.hashCode());
		result = prime * result + ((invtyp == null) ? 0 : invtyp.hashCode());
		result = prime * result + ((rimsts == null) ? 0 : rimsts.hashCode());
		result = prime * result + ((supnum == null) ? 0 : supnum.hashCode());
		result = prime * result + ((transaction_id == null) ? 0 : transaction_id.hashCode());
		result = prime * result + ((wh_id == null) ? 0 : wh_id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PurchaseOrder other = (PurchaseOrder) obj;
		if (client_id == null) {
			if (other.client_id != null)
				return false;
		} else if (!client_id.equals(other.client_id))
			return false;
		if (invdte == null) {
			if (other.invdte != null)
				return false;
		} else if (!invdte.equals(other.invdte))
			return false;
		if (invnum == null) {
			if (other.invnum != null)
				return false;
		} else if (!invnum.equals(other.invnum))
			return false;
		if (invtyp == null) {
			if (other.invtyp != null)
				return false;
		} else if (!invtyp.equals(other.invtyp))
			return false;
		if (rimsts == null) {
			if (other.rimsts != null)
				return false;
		} else if (!rimsts.equals(other.rimsts))
			return false;
		if (supnum == null) {
			if (other.supnum != null)
				return false;
		} else if (!supnum.equals(other.supnum))
			return false;
		if (transaction_id == null) {
			if (other.transaction_id != null)
				return false;
		} else if (!transaction_id.equals(other.transaction_id))
			return false;
		if (wh_id == null) {
			if (other.wh_id != null)
				return false;
		} else if (!wh_id.equals(other.wh_id))
			return false;
		return true;
	}

	public void appendLines(List<PurchaseOrderLine> lines) {
		this.lines.addAll(lines);
	}

}
