package com.gamaset.crbetportal.schema;

import java.util.Arrays;
import java.util.Optional;

public enum CountryEnum {

	FRANCE("FR"), ITALY("IT"), ARGENTINA("AR"), ARGENTINA_2("AG"), BRAZIL("BR"), GRAN_BRETAIN("GB"),
	MEXICO("MX"), NEDERLANDS("NL"), SPAIN("ES"), PORTUGAL("PT");
	
	private String code;
	
	private CountryEnum(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
	
//	    {
//	        "countryCode": "GR",
//	        "marketCount": 11
//	    },
//	    {
//	        "countryCode": "DE",
//	        "marketCount": 33
//	    },
