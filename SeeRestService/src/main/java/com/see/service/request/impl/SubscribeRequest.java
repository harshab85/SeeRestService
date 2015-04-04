package com.see.service.request.impl;

import java.util.List;

import com.see.service.request.intf.BaseRequest;

public class SubscribeRequest extends BaseRequest {

	private String requestorChannelName;
	private List<String> newSubscriptions;

	public SubscribeRequest() {
	}
	
	public String getRequestorChannelName() {
		return requestorChannelName;
	}

	public List<String> getSubscriptions() {
		return newSubscriptions;
	}			
}
