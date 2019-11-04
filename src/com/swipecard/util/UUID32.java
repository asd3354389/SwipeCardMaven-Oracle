package com.swipecard.util;

import java.util.UUID;

public class UUID32 {
	
	public static String GetUUID32(){
		String uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase();
	    return uuid;
	}
	
	public static void main(String[] args) {
		System.out.println(UUID32.GetUUID32());
	}
}
