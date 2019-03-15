package com.gamaset.crbetportal.infra.configuration;

import org.springframework.stereotype.Component;

@Component
public class ApiNgAccessKeysConfiguration {

	private String appKey;
	private String ssoToken;
	
	public ApiNgAccessKeysConfiguration() {
		this.appKey = "Szx1FGThmaNmSz9U";
		this.ssoToken = "n9eiBVCkVE0k/+XFtt3sL6ECLMsM6diCII9UcQNhg6k=";
	}
	
	public String getAppKey() {
		return appKey;
	}
	public String getSsoToken() {
		return ssoToken;
	}
	
	
}
