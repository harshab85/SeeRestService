package com.see.service.response.impl;

import com.see.service.response.intf.AbstractResponse;

public class SubscribeResponse extends AbstractResponse {

	public SubscribeResponse(boolean success, String errorMessage) {
		super(success, errorMessage);
	}

}
