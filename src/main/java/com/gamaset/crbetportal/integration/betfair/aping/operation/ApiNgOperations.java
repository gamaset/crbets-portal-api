package com.gamaset.crbetportal.integration.betfair.aping.operation;

import java.util.Locale;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gamaset.crbetportal.integration.betfair.aping.exception.APINGException;

public interface ApiNgOperations {

	public String FILTER = "filter";
    public String LOCALE = "locale";
    public String SORT = "sort";
    public String MAX_RESULT = "maxResults";
    public String MARKET_IDS = "marketIds";
    public String MARKET_ID = "marketId";
    public String INSTRUCTIONS = "instructions";
    public String CUSTOMER_REF = "customerRef";
    public String MARKET_PROJECTION = "marketProjection";
    public String PRICE_PROJECTION = "priceProjection";
    public String MATCH_PROJECTION = "matchProjection";
    public String ORDER_PROJECTION = "orderProjection";
    public String locale = new Locale("pt", "BR").toString();
    
    public String makeRequest(String operation, Map<String, Object> params, String appKey, String ssoToken) 
    		throws  APINGException, JsonProcessingException;
    

}
