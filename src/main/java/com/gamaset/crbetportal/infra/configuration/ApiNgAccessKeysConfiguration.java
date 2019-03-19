package com.gamaset.crbetportal.infra.configuration;

import org.springframework.stereotype.Component;

@Component
public class ApiNgAccessKeysConfiguration {

	private String appKey;
	private String ssoToken;
	
	public ApiNgAccessKeysConfiguration() {
		this.appKey = "Szx1FGThmaNmSz9U";
		this.ssoToken = "PCF5/wiFEb+1u0hbzI6KivmW/UfPGtbWKfWpbdphWOI=";
	}
	
	public String getAppKey() {
		return appKey;
	}
	public String getSsoToken() {
		return ssoToken;
	}
	
	
}
