package com.see.service.response.impl;

import com.see.service.response.intf.AbstractResponse;

public class DeleteVideoResponse extends AbstractResponse {

	public DeleteVideoResponse(boolean success, String errorMessage) {
		super(success, errorMessage);
	}

	
}