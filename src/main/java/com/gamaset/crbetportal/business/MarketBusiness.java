package com.gamaset.crbetportal.business;

import static com.gamaset.crbetportal.infra.log.LogEvent.create;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gamaset.crbetportal.infra.configuration.ApiNgAccessKeysConfiguration;
import com.gamaset.crbetportal.infra.exception.BusinessException;
import com.gamaset.crbetportal.infra.log.LogEvent;
import com.gamaset.crbetportal.infra.utils.MarketFilterUtils;
import com.gamaset.crbetportal.integration.betfair.aping.entities.MarketBook;
import com.gamaset.crbetportal.integration.betfair.aping.entities.MarketCatalogue;
import com.gamaset.crbetportal.integration.betfair.aping.entities.MarketFilter;
import com.gamaset.crbetportal.service.MarketService;

@Component
public class MarketBusiness {

	private static final Logger LOG_ACTION = LogEvent.logger("ACTION");
	private static final Logger LOG_ERROR = LogEvent.logger("ERROR");

	private MarketService marketService;
	private ApiNgAccessKeysConfiguration accessKeysConfiguration;

	@Autowired
	public MarketBusiness(ApiNgAccessKeysConfiguration accessKeysConfiguration, MarketService marketService) {
		this.accessKeysConfiguration = accessKeysConfiguration;
		this.marketService = marketService;
	}

	public List<MarketCatalogue> listMarketCatalogue(Set<String> eventIds) {
		List<MarketCatalogue> listCatalogue = new ArrayList<>();

		try {

			LOG_ACTION.info(create("Listando Catalogo de Mercados").build());

			MarketFilter marketFilter = new MarketFilter();
			marketFilter.setEventIds(eventIds);
			
			listCatalogue = marketService.listMarketCatalogue(marketFilter, accessKeysConfiguration.getAppKey(), accessKeysConfiguration.getSsoToken());
			
		} catch (BusinessException e) {
			LOG_ERROR.info(create("Erro ao Listar Catalogo de Mercados").build());
			throw e;
		}

		return listCatalogue;
	}

	public List<MarketBook> listMarketBook(Set<String> marketIds) {

		List<MarketBook> listMarketBooks = new ArrayList<>();
		
		try {

			LOG_ACTION.info(create("Listando Caderneta de Mercados").build());

			MarketFilter marketFilter = new MarketFilter();
			marketFilter.setMarketIds(marketIds);
			
			listMarketBooks = marketService.listMarketBooks(marketFilter, accessKeysConfiguration.getAppKey(), accessKeysConfiguration.getSsoToken());
			
		} catch (BusinessException e) {
			LOG_ERROR.info(create("Erro ao Listar Caderneta de Mercados").build());
			throw e;
		}		
		
		return listMarketBooks;
	}
	
//	private void getOdds() {
//		// FILTRANDO ODDS POR MERCADO
//		Set<String> marketIds = listMarketCatalogue.stream().map(e -> e.getMarketId()).collect(Collectors.toSet());
//		List<MarketBook> listMarketBook = marketOperation.listMarketBook(marketIds, MarketFilterUtils.getPriceProjectionDefault(), appKey, ssoToken);
//		for (EventModel event : events) {
//			for (MarketBook marketBook : listMarketBook) {
//				if(marketBook.getMarketId().equalsIgnoreCase(event.getMarkets().get(0).getMarketId())){
//					event.getMarkets().get(0).withPrices(marketBook.getRunners(), listMarketCatalogue);
//					break;
//				}
//			}
//		}
//	}

}
