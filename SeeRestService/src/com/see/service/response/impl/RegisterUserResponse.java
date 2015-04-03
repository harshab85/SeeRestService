package com.see.service.response.impl;

import com.see.service.response.intf.AbstractResponse;

public class RegisterUserResponse extends AbstractResponse {

	public RegisterUserResponse(boolean success, String errorMessage) {
		super(success, errorMessage);
	}

}
