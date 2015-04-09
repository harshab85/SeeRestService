package com.see.service.response.impl;

import com.see.service.response.intf.IResponse;

public class DeleteAllResponse implements IResponse {

	@Override
	public boolean isSuccess() {
		return true;
	}

	@Override
	public String getErrorMessage() {
		return null;
	}

}
