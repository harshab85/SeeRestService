package com.see.service.response.impl;

import com.see.service.response.intf.AbstractResponse;

public class GetStatusResponse extends AbstractResponse {

	public GetStatusResponse(boolean success, String errorMessage) {
		super(success, errorMessage);
	}

	
}
