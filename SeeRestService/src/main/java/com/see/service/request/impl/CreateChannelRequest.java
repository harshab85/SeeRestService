package com.see.service.request.impl;

import com.see.service.request.intf.BaseRequest;

public class CreateChannelRequest extends BaseRequest {

	private String registrationId;
	
	private String channelName;
	
	public String getChannelName() {
		return channelName;
	}		
	
	public String getRegistrationId() {
		return registrationId;
	}
	
}
