package com.see.service.util;

public final class Util {

	private static String HOST = "52.10.144.216";  
	
	public static String CREATE_REMOTE_CHANNEL_URL = "http://" + HOST + ":8080/RestfulWowzaApi/rest/create/";
	
	public static String PLAY_REMOTE_STREAM_URL = "rtsp://" + HOST + ":1935/live/";
	
	public static void reset(String host){
		HOST = host;
		CREATE_REMOTE_CHANNEL_URL = "http://" + HOST + ":8080/RestfulWowzaApi/rest/create/";
		PLAY_REMOTE_STREAM_URL = "rtsp://" + HOST + ":1935/live/";
	}
	
}
