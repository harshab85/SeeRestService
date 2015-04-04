package com.see.service.response.impl;

import java.util.List;

import com.see.service.response.intf.AbstractResponse;

public class GetChannelsResponse extends AbstractResponse {

	private List<String> channels;
	
	public GetChannelsResponse(boolean success, String errorMessage, List<String> channels) {
		super(success, errorMessage);
		this.channels = channels;
	}

	public List<String> getChannels(){
		return channels;
	}
	
}
