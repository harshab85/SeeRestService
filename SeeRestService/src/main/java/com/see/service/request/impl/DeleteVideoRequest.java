package com.see.service.request.impl;

import com.see.service.request.intf.BaseRequest;

public class DeleteVideoRequest extends BaseRequest {

	private String videoURL;
	private String channelName;
	
	public String getVideoUrl(){
		return this.videoURL;
	}
	
	public String getChannelName(){
		return this.channelName;
	}
}
