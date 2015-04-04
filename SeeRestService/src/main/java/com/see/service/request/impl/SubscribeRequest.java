package com.see.service.request.impl;

import com.see.service.request.intf.BaseRequest;

public class SubscribeRequest extends BaseRequest {

	private String requestorChannelName;
	private String newSubscription;

	public SubscribeRequest() {
	}

	public String getRequestorChannelName() {
		return requestorChannelName;
	}

	public String getNewSubscription() {
		return newSubscription;
	}
	
				
}
