package com.see.service.request.intf;

public abstract class BaseRequest implements IRequest {

	private String userId;
	private long timeStamp;
	
	@Override
	public String getUserId() {		
		return this.userId;
	}
			
	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public long getTimeStamp() {		
		return this.timeStamp;
	}

}
