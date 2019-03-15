package com.gamaset.crbetportal.service;

import static com.gamaset.crbetportal.infra.log.LogEvent.create;
import static com.gamaset.crbetportal.integration.betfair.aping.enums.ApiNgOperation.LISTMARKETBOOK;
import static com.gamaset.crbetportal.integration.betfair.aping.enums.ApiNgOperation.LISTMARKETCATALOGUE;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamaset.crbetportal.infra.exception.BusinessException;
import com.gamaset.crbetportal.infra.log.LogEvent;
import com.gamaset.crbetportal.integration.betfair.aping.entities.MarketBook;
import com.gamaset.crbetportal.integration.betfair.aping.entities.MarketCatalogue;
import com.gamaset.crbetportal.integration.betfair.aping.entities.MarketFilter;
import com.gamaset.crbetportal.integration.betfair.aping.enums.MarketProjection;
import com.gamaset.crbetportal.integration.betfair.aping.enums.MarketSort;
import com.gamaset.crbetportal.integration.betfair.aping.enums.MatchProjection;
import com.gamaset.crbetportal.integration.betfair.aping.enums.OrderProjection;
import com.gamaset.crbetportal.integration.betfair.aping.exception.APINGException;
import com.gamaset.crbetportal.integration.betfair.aping.operation.ApiNgOperations;
import com.gamaset.crbetportal.integration.betfair.aping.operation.ApiNgSender;

@Service
public class MarketService implements ApiNgOperations {

	private static final Logger LOG_ACTION = LogEvent.logger("ACTION");
	private static final Logger LOG_ERROR = LogEvent.logger("ERROR");

	@Autowired
	private ObjectMapper mapper;
	@Autowired
	private ApiNgSender sender;

	public List<MarketCatalogue> listMarketCatalogue(MarketFilter marketFilter, String appKey, String ssoToken) {
		
		LOG_ACTION.info(create("Enviando Chamada listMarketCatalogue").build());
		
		List<MarketCatalogue> results = null;
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put(FILTER, marketFilter);
			params.put(LOCALE, locale);

			Set<MarketProjection> marketProjection = new HashSet<MarketProjection>();
//			marketProjection.add(MarketProjection.COMPETITION);
			marketProjection.add(MarketProjection.EVENT);
//        marketProjection.add(MarketProjection.EVENT_TYPE);
//        marketProjection.add(MarketProjection.MARKET_DESCRIPTION);
			marketProjection.add(MarketProjection.RUNNER_DESCRIPTION);
			
			params.put(MARKET_PROJECTION, marketProjection);
			params.put(SORT, MarketSort.FIRST_TO_START);
			params.put(MAX_RESULT, 100);

			String result = makeRequest(LISTMARKETCATALOGUE.getOperationName(), params, appKey, ssoToken);

			results = mapper.readValue(result, new TypeReference<List<MarketCatalogue>>() {
			});

		} catch (Exception | APINGException e) {
			LOG_ERROR.info(create("erro ao enviar requisicao para o aping").add("message", e.getMessage()).build());
			throw new BusinessException(e);
		}
		return results;
	}

	public List<MarketBook> listMarketBooks(MarketFilter marketFilter, String appKey, String ssoToken) {

		List<MarketBook> results = null;

		try {

			Map<String, Object> params = new HashMap<String, Object>();
			params.put(MARKET_IDS, marketFilter.getMarketIds());// REQUIRED
			params.put(LOCALE, locale);

			params.put(PRICE_PROJECTION, MarketFilter.getPriceProjectionDefault());

			OrderProjection orderProjection = OrderProjection.EXECUTABLE;
			params.put(ORDER_PROJECTION, orderProjection);

			MatchProjection matchProjection = MatchProjection.ROLLED_UP_BY_AVG_PRICE;
			params.put(MATCH_PROJECTION, matchProjection);

//        String currencyCode = null;
//        params.put("currencyCode", currencyCode);

//		 boolean includeOverallPosition, 
//		 boolean partitionMatchedByStrategyRef, 
//		 Set<String> customerStrategyRefs, 
//		 Date matchedSince, 
//		 Set<BetId> betIds

			String result = makeRequest(LISTMARKETBOOK.getOperationName(), params, appKey, ssoToken);

			results = mapper.readValue(result, new TypeReference<List<MarketBook>>() {
			});
			
		} catch (Exception | APINGException e) {
			LOG_ERROR.info(create("erro ao enviar requisicao para o aping").add("message", e.getMessage()).build());
			throw new BusinessException(e);
		}

		return results;

	}

	@Override
	public String makeRequest(String operation, Map<String, Object> params, String appKey, String ssoToken)
			throws APINGException, JsonProcessingException {

		String requestString = mapper.writeValueAsString(params);

		LOG_ACTION.info(create("Requisicao para ApiNg").add("params", requestString).build());

		String response = sender.sendPostRequest(requestString, operation, appKey, ssoToken);
		if (Objects.nonNull(response)) {
			return response;
		} else {
			throw new APINGException();
		}
	}

}
