package com.swipecard.model;

import java.util.Date;

public class RepairSwipecard {
	private String Id;
	private String WorkshopNo;
	private String LineNo;
	private Date Swipe_In;
	private Date Swipe_Out;
	private String Reason_No;
	private String Recv_id;
	private String SwipeType;
	private String PrivilegeLevel;
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getWorkshopNo() {
		return WorkshopNo;
	}
	public void setWorkshopNo(String workshopNo) {
		WorkshopNo = workshopNo;
	}
	public String getLineNo() {
		return LineNo;
	}
	public void setLineNo(String lineNo) {
		LineNo = lineNo;
	}
	public Date getSwipe_In() {
		return Swipe_In;
	}
	public void setSwipe_In(Date swipe_In) {
		Swipe_In = swipe_In;
	}
	public Date getSwipe_Out() {
		return Swipe_Out;
	}
	public void setSwipe_Out(Date swipe_Out) {
		Swipe_Out = swipe_Out;
	}
	public String getReason_No() {
		return Reason_No;
	}
	public void setReason_No(String reason_No) {
		Reason_No = reason_No;
	}
	public String getRecv_id() {
		return Recv_id;
	}
	public void setRecv_id(String recv_id) {
		Recv_id = recv_id;
	}
	public String getSwipeType() {
		return SwipeType;
	}
	public void setSwipeType(String swipeType) {
		SwipeType = swipeType;
	}
	public String getPrivilegeLevel() {
		return PrivilegeLevel;
	}
	public void setPrivilegeLevel(String privilegeLevel) {
		PrivilegeLevel = privilegeLevel;
	}
	@Override
	public String toString() {
		return "RepairSwipecard [Id=" + Id + ", WorkshopNo=" + WorkshopNo + ", LineNo=" + LineNo + ", Swipe_In="
				+ Swipe_In + ", Swipe_Out=" + Swipe_Out + ", Reason_No=" + Reason_No + ", Recv_id=" + Recv_id
				+ ", SwipeType=" + SwipeType + ", PrivilegeLevel=" + PrivilegeLevel + "]";
	}
	
	
}
