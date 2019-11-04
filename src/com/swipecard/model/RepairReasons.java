package com.swipecard.model;

public class RepairReasons {
	private String Reason_No;
	private String Reason_Class;
	private String Reason_Desc;
	public String getReason_No() {
		return Reason_No;
	}
	public void setReason_No(String reason_No) {
		Reason_No = reason_No;
	}
	public String getReason_Class() {
		return Reason_Class;
	}
	public void setReason_Class(String reason_Class) {
		Reason_Class = reason_Class;
	}
	public String getReason_Desc() {
		return Reason_Desc;
	}
	public void setReason_Desc(String reason_Desc) {
		Reason_Desc = reason_Desc;
	}
	@Override
	public String toString() {
		return "RepairReasons [Reason_No=" + Reason_No + ", Reason_Class=" + Reason_Class + ", Reason_Desc="
				+ Reason_Desc + "]";
	}
	
	
}
