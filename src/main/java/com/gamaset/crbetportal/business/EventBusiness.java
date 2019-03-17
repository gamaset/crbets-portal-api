package com.gamaset.crbetportal.business;

import static com.gamaset.crbetportal.infra.log.LogEvent.create;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gamaset.crbetportal.business.builder.MarketFilterBuilder;
import com.gamaset.crbetportal.infra.configuration.ApiNgAccessKeysConfiguration;
import com.gamaset.crbetportal.infra.exception.BusinessException;
import com.gamaset.crbetportal.infra.log.LogEvent;
import com.gamaset.crbetportal.infra.utils.TimeRangeUtils;
import com.gamaset.crbetportal.integration.betfair.aping.entities.EventResult;
import com.gamaset.crbetportal.integration.betfair.aping.entities.MarketBook;
import com.gamaset.crbetportal.integration.betfair.aping.entities.MarketCatalogue;
import com.gamaset.crbetportal.integration.betfair.aping.entities.MarketFilter;
import com.gamaset.crbetportal.integration.betfair.aping.entities.TimeRange;
import com.gamaset.crbetportal.schema.CompetitionSchema;
import com.gamaset.crbetportal.schema.EventSchema;
import com.gamaset.crbetportal.schema.response.CompetitionEventsResponse;
import com.gamaset.crbetportal.service.EventService;
import com.gamaset.crbetportal.service.MarketService;

@Component
public class EventBusiness {

	private static final Logger LOG_ACTION = LogEvent.logger("ACTION");
	private static final Logger LOG_ERROR = LogEvent.logger("ERROR");

	private MarketFilterBuilder marketBuilder;
	private MarketService marketService;
	private CompetitionBusiness competitionBusiness;
	private EventService eventService;
	private ApiNgAccessKeysConfiguration accessKeysConfiguration;

	@Autowired
	public EventBusiness(MarketFilterBuilder marketBuilder, CompetitionBusiness competitionBusiness, EventService eventService,
			MarketService marketService, ApiNgAccessKeysConfiguration accessKeysConfiguration) {
		this.competitionBusiness = competitionBusiness;
		this.eventService = eventService;
		this.accessKeysConfiguration = accessKeysConfiguration;
		this.marketService = marketService;
		this.marketBuilder = marketBuilder;
	}

	public CompetitionEventsResponse listByEventTypeAndCompetitionId(Long eventTypeId, Long competitionId) {

		CompetitionEventsResponse response = new CompetitionEventsResponse();
		boolean marketTypeDefault = true;
		TimeRange timeRange = null;
		
		try {

			LOG_ACTION.info(create("Listando Eventos").add("eventTypeId", eventTypeId).add("competitionId", competitionId).build());
			
			response.setCompetition(competitionBusiness.getById(competitionId));

			// BUSCANDO OS EVENTOS
			MarketFilter filterEventsByEventTypeAndCompetition = marketBuilder.filterEventsByEventTypeAndCompetition(eventTypeId, competitionId, marketTypeDefault, timeRange);
			List<EventResult> listEventsResult = eventService.listEvents(filterEventsByEventTypeAndCompetition,
					accessKeysConfiguration.getAppKey(), accessKeysConfiguration.getSsoToken());
			response.setEvents(convertToEventSchema(listEventsResult));
			
			// BUSCANDO O CATALOGO DE MERCADOS
			MarketFilter filterMarketCatalogueByEvents = marketBuilder.filterMarketCatalogueByEvents(listEventsResult, marketTypeDefault);
			List<MarketCatalogue> listMarketCatalogue = marketService.listMarketCatalogue(filterMarketCatalogueByEvents, accessKeysConfiguration.getAppKey(), accessKeysConfiguration.getSsoToken());
			response.buildEventMarketCatalogue(listMarketCatalogue);
			
			//BUSCANDO ODDS PARA OS MERCADOS
			MarketFilter filterMarketBookByMarkets = marketBuilder.filterMarketBookByMarkets(listMarketCatalogue, marketTypeDefault);
			List<MarketBook> listMarketBook = marketService.listMarketBooks(filterMarketBookByMarkets, accessKeysConfiguration.getAppKey(), accessKeysConfiguration.getSsoToken());
			response.buildEventMarketPrices(listMarketBook);
			
		} catch (BusinessException e) {
			LOG_ERROR.info(create("Erro ao Listar Eventos").build());
			throw e;
		}

		return response;
	}
	
	public CompetitionEventsResponse getByEventId(Long eventTypeId, Long competitionId, Long eventId) {
		CompetitionEventsResponse response = new CompetitionEventsResponse();
		boolean marketTypeDefault = false;
		TimeRange timeRange = null;
		
		try {

			LOG_ACTION.info(create("Detalhando Evento").
					add("eventTypeId", eventTypeId).
					add("competitionId", competitionId).
					add("eventId", eventId).
					build());
			
			// BUSCANDO OS EVENTOS
			MarketFilter filterEventsByEventTypeAndCompetition = marketBuilder.filterEventsByEventTypeAndCompetitionAndEvent(eventTypeId, competitionId, eventId, marketTypeDefault, timeRange);
			List<EventResult> listEventsResult = eventService.listEvents(filterEventsByEventTypeAndCompetition,
					accessKeysConfiguration.getAppKey(), accessKeysConfiguration.getSsoToken());
			response.setEvents(convertToEventSchema(listEventsResult));
			
			// BUSCANDO O CATALOGO DE MERCADOS
			MarketFilter filterMarketCatalogueByEvents = marketBuilder.filterMarketCatalogueByEvents(listEventsResult, marketTypeDefault);
			List<MarketCatalogue> listMarketCatalogue = marketService.listMarketCatalogue(filterMarketCatalogueByEvents, accessKeysConfiguration.getAppKey(), accessKeysConfiguration.getSsoToken());
			response.buildEventMarketCatalogue(listMarketCatalogue);
			
			//BUSCANDO ODDS PARA OS MERCADOS
			MarketFilter filterMarketBookByMarkets = marketBuilder.filterMarketBookByMarkets(listMarketCatalogue, marketTypeDefault);
			List<MarketBook> listMarketBook = marketService.listMarketBooks(filterMarketBookByMarkets, accessKeysConfiguration.getAppKey(), accessKeysConfiguration.getSsoToken());
			response.buildEventMarketPrices(listMarketBook);
			
		} catch (BusinessException e) {
			LOG_ERROR.info(create("Erro ao Listar Eventos").build());
			throw e;
		}

		return response;
	}
	
	public List<CompetitionEventsResponse> listFavoritesByEventType(Long eventTypeId) {
		
		List<CompetitionEventsResponse> response = new ArrayList<CompetitionEventsResponse>();
		boolean marketTypeDefault = true;
		TimeRange timeRange = TimeRangeUtils.getToday();

		try {
			LOG_ACTION.info(create("Listando Eventos Favoritos").add("eventTypeId", eventTypeId).build());
			
			List<CompetitionSchema> competitions = competitionBusiness.list(eventTypeId, timeRange);
			
			competitions.stream().forEach(c -> {
				CompetitionEventsResponse ce = new CompetitionEventsResponse();
				ce.setCompetition(c);
				
				// BUSCANDO OS EVENTOS
				MarketFilter filterEventsByEventTypeAndCompetition = marketBuilder.filterEventsByEventTypeAndCompetition(eventTypeId, c.getId(), marketTypeDefault, timeRange);
				List<EventResult> listEventsResult = eventService.listEvents(filterEventsByEventTypeAndCompetition,
						accessKeysConfiguration.getAppKey(), accessKeysConfiguration.getSsoToken());
				ce.setEvents(convertToEventSchema(listEventsResult));
				
				// BUSCANDO O CATALOGO DE MERCADOS
				MarketFilter filterMarketCatalogueByEvents = marketBuilder.filterMarketCatalogueByEvents(listEventsResult, marketTypeDefault);
				List<MarketCatalogue> listMarketCatalogue = marketService.listMarketCatalogue(filterMarketCatalogueByEvents, accessKeysConfiguration.getAppKey(), accessKeysConfiguration.getSsoToken());
				ce.buildEventMarketCatalogue(listMarketCatalogue);
				
				//BUSCANDO ODDS PARA OS MERCADOS
				MarketFilter filterMarketBookByMarkets = marketBuilder.filterMarketBookByMarkets(listMarketCatalogue, marketTypeDefault);
				List<MarketBook> listMarketBook = marketService.listMarketBooks(filterMarketBookByMarkets, accessKeysConfiguration.getAppKey(), accessKeysConfiguration.getSsoToken());
				ce.buildEventMarketPrices(listMarketBook);

				response.add(ce);
			});
			
		} catch (BusinessException e) {
			LOG_ERROR.info(create("Erro ao Listar Eventos Favoritos").build());
			throw e;
		}
		
		return response;
	}
	
	private List<EventSchema> convertToEventSchema(List<EventResult> listEventsResult) {
		List<EventSchema> events = new ArrayList<EventSchema>();

		if (Objects.nonNull(listEventsResult)) {
			listEventsResult.stream().forEach(e -> {
				events.add(new EventSchema(e));
			});
		}

		return events;
	}


}
