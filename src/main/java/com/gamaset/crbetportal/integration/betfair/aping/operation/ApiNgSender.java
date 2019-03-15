package com.gamaset.crbetportal.integration.betfair.aping.operation;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ApiNgSender {

	private final String HTTP_HEADER_X_APPLICATION = "X-Application";
	private final String HTTP_HEADER_X_AUTHENTICATION = "X-Authentication";
	private final String HTTP_HEADER_CONTENT_TYPE = "Content-Type";
	private final String HTTP_HEADER_ACCEPT = "Accept";
	private final String HTTP_HEADER_ACCEPT_CHARSET = "Accept-Charset";

	@Value("${betfair.api.aping_url}")
	private String APING_URL;
	@Value("${betfair.api.rest_suffix}")
	private String RESCRIPT_SUFFIX;
	
	private final RestTemplate restTemplate;

	public ApiNgSender(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	public String sendPostRequest(String param, String operation, String appKey, String ssoToken) {
		String apiNgURL = APING_URL + RESCRIPT_SUFFIX + operation + "/";
		
//		System.out.println(">>> HttpUtil.sendPostRequest.apiNgURL: " + apiNgURL);
		
		String jsonRequest = param;
		
		HttpHeaders headers = new HttpHeaders();
		headers.set(HTTP_HEADER_CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE);
		headers.set(HTTP_HEADER_ACCEPT, APPLICATION_JSON_UTF8_VALUE);
		headers.set(HTTP_HEADER_ACCEPT_CHARSET, "UTF-8");
		headers.set(HTTP_HEADER_X_APPLICATION, appKey);
		headers.set(HTTP_HEADER_X_AUTHENTICATION, ssoToken);

		HttpEntity<String> request = new HttpEntity<String>(jsonRequest, headers);
		
		ResponseEntity<String> postForEntity = restTemplate.postForEntity(apiNgURL, request, String.class);
		
//		System.out.println(">>> HttpUtil.sendPostRequest.postForEntity: " + postForEntity);
		
		return postForEntity.getBody();
	}
}
