package com.gamaset.crbetportal.infra.configuration;

import org.springframework.stereotype.Component;

@Component
public class ApiNgAccessKeysConfiguration {

	private String appKey;
	private String ssoToken;
	
	public ApiNgAccessKeysConfiguration() {
		this.appKey = "Szx1FGThmaNmSz9U";
		this.ssoToken = "9KddiEkLe2lYiRISbxt+TbTajKgRiMvbuuJuHm/Z2v0=";
	}
	
	public String getAppKey() {
		return appKey;
	}
	public String getSsoToken() {
		return ssoToken;
	}
	
	
}
