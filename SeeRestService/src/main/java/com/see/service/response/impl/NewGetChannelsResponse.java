package com.see.service.response.impl;

import java.util.Set;

import com.see.service.response.intf.AbstractResponse;

public class NewGetChannelsResponse extends AbstractResponse {

	private Set<String> channels;
	
	public NewGetChannelsResponse(boolean success, String errorMessage, Set<String> channels) {
		super(success, errorMessage);
		this.channels = channels;
	}

	public Set<String> getChannels(){
		return channels;
	}
	
}
