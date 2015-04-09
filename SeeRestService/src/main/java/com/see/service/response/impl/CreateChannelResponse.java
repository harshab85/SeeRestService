package com.see.service.response.impl;

import com.see.service.response.intf.AbstractResponse;

public class CreateChannelResponse extends AbstractResponse {
	public CreateChannelResponse(boolean success, String errorMessage) {
		super(success, errorMessage);
	}
}
