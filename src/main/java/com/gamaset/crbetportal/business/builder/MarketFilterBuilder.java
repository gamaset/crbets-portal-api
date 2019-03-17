package com.gamaset.crbetportal.business.builder;

import static com.gamaset.crbetportal.infra.utils.TimeRangeUtils.getTimeRangeDefault;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.gamaset.crbetportal.integration.betfair.aping.entities.EventResult;
import com.gamaset.crbetportal.integration.betfair.aping.entities.MarketCatalogue;
import com.gamaset.crbetportal.integration.betfair.aping.entities.MarketFilter;
import com.gamaset.crbetportal.integration.betfair.aping.entities.TimeRange;
import com.gamaset.crbetportal.integration.betfair.aping.enums.MarketBettingType;
import com.gamaset.crbetportal.repository.entity.CompetitionModel;
import com.gamaset.crbetportal.repository.entity.EventTypeModel;

@Component
public class MarketFilterBuilder {

	public MarketFilter filterEventsByEventTypeAndCompetition(Long eventTypeId, Long competitionId,
			boolean marketTypeDefault, TimeRange timeRange) {
		MarketFilter marketFilter = new MarketFilter();
		marketFilter.setEventTypeIds(buildSetIds(eventTypeId));
		marketFilter.setCompetitionIds(buildSetIds(competitionId));
		if (Objects.nonNull(timeRange)) {
			marketFilter.setMarketStartTime(timeRange);
		} else {
			marketFilter.setMarketStartTime(getTimeRangeDefault());
		}
		if (marketTypeDefault) {
			marketFilter.setMarketTypeCodes(getMarketTypeCodesDefault());
		}

		Set<MarketBettingType> marketBettingTypes = new HashSet<MarketBettingType>();
		marketBettingTypes.add(MarketBettingType.ODDS);
		marketFilter.setMarketBettingTypes(marketBettingTypes);

		return marketFilter;
	}

	public MarketFilter filterEventsByEventTypeAndCompetitionAndEvent(Long eventTypeId, Long competitionId,
			Long eventId, boolean marketTypeDefault, TimeRange timeRange) {
		MarketFilter marketFilter = new MarketFilter();
		marketFilter.setEventTypeIds(buildSetIds(eventTypeId));
		marketFilter.setCompetitionIds(buildSetIds(competitionId));
		marketFilter.setEventIds(buildSetIds(eventId));
		if (Objects.nonNull(timeRange)) {
			marketFilter.setMarketStartTime(timeRange);
		} else {
			marketFilter.setMarketStartTime(getTimeRangeDefault());
		}
		if (marketTypeDefault) {
			marketFilter.setMarketTypeCodes(getMarketTypeCodesDefault());
		}

		Set<MarketBettingType> marketBettingTypes = new HashSet<MarketBettingType>();
		marketBettingTypes.add(MarketBettingType.ODDS);
		marketFilter.setMarketBettingTypes(marketBettingTypes);

		return marketFilter;
	}

	public MarketFilter filterMarketCatalogueByEvents(List<EventResult> listEventsResult, boolean marketTypeDefault) {
		Set<String> eventIds = listEventsResult.stream().map(e -> e.getEvent().getId()).collect(Collectors.toSet());
		MarketFilter marketFilter = new MarketFilter();
		marketFilter.setEventIds(eventIds);
		if (marketTypeDefault) {
			marketFilter.setMarketTypeCodes(getMarketTypeCodesDefault());
		}

		Set<MarketBettingType> marketBettingTypes = new HashSet<MarketBettingType>();
		marketBettingTypes.add(MarketBettingType.ODDS);
		marketFilter.setMarketBettingTypes(marketBettingTypes);

		return marketFilter;
	}

	public MarketFilter filterMarketBookByMarkets(List<MarketCatalogue> listMarketCatalogue,
			boolean marketTypeDefault) {
		Set<String> marketIds = listMarketCatalogue.stream().map(e -> e.getMarketId()).collect(Collectors.toSet());
		MarketFilter marketFilter = new MarketFilter();
		marketFilter.setMarketIds(marketIds);
		if (marketTypeDefault) {
			marketFilter.setMarketTypeCodes(getMarketTypeCodesDefault());
		}

		Set<MarketBettingType> marketBettingTypes = new HashSet<MarketBettingType>();
		marketBettingTypes.add(MarketBettingType.ODDS);
		marketFilter.setMarketBettingTypes(marketBettingTypes);

		return marketFilter;

	}

	public MarketFilter filterEventTypeByIds(List<EventTypeModel> events) {
		Set<String> eventTypeIds = events.stream().map(e -> String.valueOf(e.getId())).collect(Collectors.toSet());
		MarketFilter marketFilter = new MarketFilter();
		marketFilter.setEventTypeIds(eventTypeIds);
		return marketFilter;
	}

	public MarketFilter filterCompetitionByIds(List<CompetitionModel> competitions, TimeRange timeRange) {
		Set<String> competitionIds = competitions.stream().map(c -> String.valueOf(c.getId()))
				.collect(Collectors.toSet());
		MarketFilter marketFilter = new MarketFilter();
		marketFilter.setCompetitionIds(competitionIds);
		if (Objects.nonNull(timeRange)) {
			marketFilter.setMarketStartTime(timeRange);
		} else {
			marketFilter.setMarketStartTime(getTimeRangeDefault());
		}
		return marketFilter;
	}

	private Set<String> buildSetIds(Long... ids) {
		Set<String> response = new HashSet<String>();
		for (Long id : ids) {
			response.add(id.toString());
		}
		return response;
	}

	/**
	 * Catalogo de Mercado Default = PROBABILIDADES
	 * 
	 * @return
	 */
	private Set<String> getMarketTypeCodesDefault() {
		Set<String> marketTypeCodes = new HashSet<>();
		marketTypeCodes.add("MATCH_ODDS");
		return marketTypeCodes;
	}

}
