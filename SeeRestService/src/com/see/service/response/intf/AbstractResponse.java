package com.see.service.response.intf;

public class AbstractResponse implements IResponse {

	private boolean success;
	private String errorMessage;
	
	public AbstractResponse(boolean success, String errorMessage) {
		this.success = success;
		this.errorMessage = errorMessage;
	}
	
	@Override
	public boolean isSuccess() {
		return this.success;
	}

	@Override
	public String getErrorMessage() {
		return this.errorMessage;
	}

}
