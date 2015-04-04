package com.see.service.request.impl;

import com.see.service.request.intf.BaseRequest;

public class NewSubscribeRequest extends BaseRequest {

	private String requestorChannelName;
	private String newSubscription;

	public NewSubscribeRequest() {
	}

	public String getRequestorChannelName() {
		return requestorChannelName;
	}

	public String getNewSubscription() {
		return newSubscription;
	}
	
				
}
