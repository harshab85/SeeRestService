package com.see.service.request.impl;

import com.see.service.request.intf.BaseRequest;

public class RemoveStreamRequest extends BaseRequest {

	private String channelName;
	
	public String getChannelName(){
		return this.channelName;
	}

}
