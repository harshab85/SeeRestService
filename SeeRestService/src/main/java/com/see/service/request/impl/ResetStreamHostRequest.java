package com.see.service.request.impl;

import com.see.service.request.intf.BaseRequest;

public class ResetStreamHostRequest extends BaseRequest {

	private String newHost;
	
	public String getNewHost(){
		return this.newHost;
	}
}
