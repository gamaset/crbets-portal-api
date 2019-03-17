package com.gamaset.crbetportal.infra.configuration;

import org.springframework.stereotype.Component;

@Component
public class ApiNgAccessKeysConfiguration {

	private String appKey;
	private String ssoToken;
	
	public ApiNgAccessKeysConfiguration() {
		this.appKey = "Szx1FGThmaNmSz9U";
		this.ssoToken = "+cIorOJa5KKn1W3dlHGcleFJCWPHTY65umZKDjt6pqo=";
	}
	
	public String getAppKey() {
		return appKey;
	}
	public String getSsoToken() {
		return ssoToken;
	}
	
	
}
