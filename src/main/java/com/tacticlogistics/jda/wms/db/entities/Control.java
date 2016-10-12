package com.tacticlogistics.jda.wms.db.entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "uc_control_tcc_wms")
public class Control implements Serializable{

	private static final long serialVersionUID = 7556302255533735908L;
	
	@DatabaseField(generatedId = true)
	private BigInteger transaction_id;
	@DatabaseField
	private String wh_id;
	@DatabaseField
	private String event_id;
	@DatabaseField
	private Date create_date;
	@DatabaseField
	private Date start_date;
	@DatabaseField
	private Date end_date;
	@DatabaseField
	private int status;
	@DatabaseField
	private String msj_error;
	@DatabaseField
	private String trntyp;

	public Control(){
		
	}
	
	public BigInteger getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(BigInteger transaction_id) {
		this.transaction_id = transaction_id;
	}

	public String getTrntyp(){
		return trntyp;
	}
	
	public void setTrntyp(String trntyp){
		this.trntyp = trntyp;
	}
	
	public String getWh_id() {
		return wh_id;
	}

	public void setWh_id(String wh_id) {
		this.wh_id = wh_id;
	}

	public String getEvent_id() {
		return event_id;
	}

	public void setEvent_id(String event_id) {
		this.event_id = event_id;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMsj_error() {
		return msj_error;
	}

	public void setMsj_error(String msg_error) {
		this.msj_error = msg_error;
	}

	@Override
	public String toString() {
		return "Control [transaction_id=" + transaction_id + ", wh_id=" + wh_id + ", event_id=" + event_id
				+ ", create_date=" + create_date + ", start_date=" + start_date + ", end_date=" + end_date + ", status="
				+ status + ", msj_error=" + msj_error + "]";
	}
	
	

}
