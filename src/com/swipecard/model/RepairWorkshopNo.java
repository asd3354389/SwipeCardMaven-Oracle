package com.swipecard.model;

import java.util.Date;

public class RepairWorkshopNo {
	private String WorkshopNo;
	private String Lineno;
	private String Status;
	private Date Update_time;
	private String Update_userid;
	private String Recv_id;
	public String getWorkshopNo() {
		return WorkshopNo;
	}
	public void setWorkshopNo(String workshopNo) {
		WorkshopNo = workshopNo;
	}
	public String getLineno() {
		return Lineno;
	}
	public void setLineno(String lineno) {
		Lineno = lineno;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public Date getUpdate_time() {
		return Update_time;
	}
	public void setUpdate_time(Date update_time) {
		Update_time = update_time;
	}
	public String getUpdate_userid() {
		return Update_userid;
	}
	public void setUpdate_userid(String update_userid) {
		Update_userid = update_userid;
	}
	public String getRecv_id() {
		return Recv_id;
	}
	public void setRecv_id(String recv_id) {
		Recv_id = recv_id;
	}
	@Override
	public String toString() {
		return "RepairWorkshopNo [WorkshopNo=" + WorkshopNo + ", Lineno=" + Lineno + ", Status=" + Status
				+ ", Update_time=" + Update_time + ", Update_userid=" + Update_userid + ", Recv_id=" + Recv_id + "]";
	}
	
	
}
