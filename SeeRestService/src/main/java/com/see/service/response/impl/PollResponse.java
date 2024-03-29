package com.see.service.response.impl;

import com.see.service.response.intf.AbstractResponse;

public class PollResponse extends AbstractResponse {

	private String videoUrl = null;
	
	public PollResponse() {
		super(false, null);		
	}
	
	public PollResponse(String videoUrl) {
		super(true, null);
		this.videoUrl = videoUrl;
	}
	
	public PollResponse(boolean success, String errorMessage, String videoUrl) {
		super(success, errorMessage);
		this.videoUrl = videoUrl;
	}

		
	public String getVideoUrl(){
		return this.videoUrl;
	}
}
