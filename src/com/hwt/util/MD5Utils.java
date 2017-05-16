package com.hwt.util;

import java.security.MessageDigest;

import org.apache.log4j.Logger;



public final class MD5Utils{

	public static String getMD5(String str) {
		Logger log = Logger.getLogger("MD5Encryption");
		String reStr = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte ss[] = md.digest();
			reStr = bytes2String(ss).toLowerCase();
		} catch (Exception e) {
			log.error("MD5加密失败", e);
		}
		return reStr;
	}
	
	public static String bytes2String(byte[] aa) {
		String hash = "";
		for(int i = 0; i < aa.length; i++) {
			int temp;
			temp = aa[i] < 0 ? aa[i]+256 : aa[i];
			if(temp < 16) hash += "0";
			hash += Integer.toString(temp, 16);
		}
		hash = hash.toUpperCase();
		return hash;
	}
}
