package com.gamaset.crbetportal.service;

import static com.gamaset.crbetportal.infra.log.LogEvent.create;
import static com.gamaset.crbetportal.integration.betfair.aping.enums.ApiNgOperation.LISTCOMPETITIONS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamaset.crbetportal.infra.exception.BusinessException;
import com.gamaset.crbetportal.infra.log.LogEvent;
import com.gamaset.crbetportal.integration.betfair.aping.entities.CompetitionResult;
import com.gamaset.crbetportal.integration.betfair.aping.entities.MarketFilter;
import com.gamaset.crbetportal.integration.betfair.aping.exception.APINGException;
import com.gamaset.crbetportal.integration.betfair.aping.operation.ApiNgOperations;
import com.gamaset.crbetportal.integration.betfair.aping.operation.ApiNgSender;

@Service
public class CompetitionService implements ApiNgOperations {

	private static final Logger LOG_ACTION = LogEvent.logger("ACTION");
	private static final Logger LOG_ERROR = LogEvent.logger("ERROR");

	@Autowired
	private ObjectMapper mapper;
	@Autowired
	private ApiNgSender sender;

	public List<CompetitionResult> listCompetitions(MarketFilter marketFilter, String appKey, String ssoToken) {

		List<CompetitionResult> competitions = new ArrayList<CompetitionResult>();

		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put(FILTER, marketFilter);
			params.put(LOCALE, locale);

			String result = makeRequest(LISTCOMPETITIONS.getOperationName(), params, appKey, ssoToken);

			competitions = mapper.readValue(result, new TypeReference<List<CompetitionResult>>() {});
			
		} catch (Exception | APINGException e) {
			LOG_ERROR.info(create("erro ao enviar requisicao para o aping").add("message", e.getMessage()).build());
			throw new BusinessException(e);
		}
		
		return competitions;
	}

	@Override
	public String makeRequest(String operation, Map<String, Object> params, String appKey, String ssoToken)
			throws APINGException, JsonProcessingException {

		String requestString = mapper.writeValueAsString(params);

		LOG_ACTION.info(create("enviando requisicao para aping").add("params", requestString).build());

		String response = sender.sendPostRequest(requestString, operation, appKey, ssoToken);
		if (Objects.nonNull(response)) {
			return response;
		} else {
			throw new APINGException();
		}
	}
}
