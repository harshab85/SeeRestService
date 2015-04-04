package com.see.service.response.impl;

import com.see.service.response.intf.IResponse;

public class RemoveStreamResponse implements IResponse {

	private String error;
	private boolean success;
	
	public RemoveStreamResponse(boolean success, String error) {
		this.error = error;
		this.success = success;
	}

	@Override
	public boolean isSuccess() {
		return this.success;
	}

	@Override
	public String getErrorMessage() {
		return this.error;
	}

}
