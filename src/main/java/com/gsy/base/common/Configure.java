package com.gsy.base.common;

public class Configure {
	private static String key = "17JUWdcTzCN7MphU2e3AnC20QhTWV1OE";

	//小程序ID	
	private static String appID = "wx8ef1d7f4f0591754";
	//商户号
	private static String mch_id = "1482678932";
	//
	private static String secret = "daab992ed773dfcd8c3023d06e04b1f6";

	public static String getSecret() {
		return secret;
	}

	public static void setSecret(String secret) {
		Configure.secret = secret;
	}

	public static String getKey() {
		return key;
	}

	public static void setKey(String key) {
		Configure.key = key;
	}

	public static String getAppID() {
		return appID;
	}

	public static void setAppID(String appID) {
		Configure.appID = appID;
	}

	public static String getMch_id() {
		return mch_id;
	}

	public static void setMch_id(String mch_id) {
		Configure.mch_id = mch_id;
	}

}
