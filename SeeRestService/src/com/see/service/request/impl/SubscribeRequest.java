package com.see.service.request.impl;

import java.util.List;

import com.see.service.request.intf.BaseRequest;

public class SubscribeRequest extends BaseRequest {

	private List<String> channelNames;

	public SubscribeRequest() {
	}
	
	public List<String> getChannelNames() {
		return channelNames;
	}
	
	public void setChannelNames(List<String> channelNames){
		this.channelNames = channelNames;
	}
			
}
