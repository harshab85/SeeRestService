package com.see.service.util;

public final class Util {
	
	
	//GCM SERVICE
	public static String GCM_URL = "https://android.googleapis.com/gcm/send";
	

	private static String HOST = "52.10.144.216";  
	
	public static String CREATE_REMOTE_CHANNEL_URL = "http://" + HOST + ":8080/RestfulWowzaApi/rest/create/";
	
	public static String PLAY_REMOTE_STREAM_URL = "rtmp://" + HOST + ":1935/";
	
	public static String STREAM_NAME = "/myStream";
	
	public static void reset(String host){
		HOST = host;
		CREATE_REMOTE_CHANNEL_URL = "http://" + HOST + ":8080/RestfulWowzaApi/rest/create/";
		PLAY_REMOTE_STREAM_URL = "rtmp://" + HOST + ":1935/";
	}
	
}
