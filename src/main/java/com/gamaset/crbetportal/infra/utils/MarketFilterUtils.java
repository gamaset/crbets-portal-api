package com.gamaset.crbetportal.infra.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.gamaset.crbetportal.integration.betfair.aping.entities.PriceProjection;
import com.gamaset.crbetportal.integration.betfair.aping.entities.TimeRange;
import com.gamaset.crbetportal.integration.betfair.aping.enums.PriceData;
import com.gamaset.crbetportal.schema.CountryEnum;

public class MarketFilterUtils {

	public static TimeRange getTimeRangeDefault() {
		TimeRange timeRange = new TimeRange();
		timeRange.setFrom(Date.from(Instant.now()));
		timeRange.setTo(Date.from(LocalDate.now().plusDays(3).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		return timeRange;
	}

	public static Set<String> getCountriesDefault() {
		Set<String> marketCountries = new HashSet<>();
		marketCountries.add(CountryEnum.GRAN_BRETAIN.getCode());
		marketCountries.add(CountryEnum.BRAZIL.getCode());
		marketCountries.add(CountryEnum.FRANCE.getCode());
		marketCountries.add(CountryEnum.ARGENTINA.getCode());
		marketCountries.add(CountryEnum.ARGENTINA_2.getCode());
		marketCountries.add(CountryEnum.ITALY.getCode());
		marketCountries.add(CountryEnum.MEXICO.getCode());
		marketCountries.add(CountryEnum.NEDERLANDS.getCode());
		marketCountries.add(CountryEnum.PORTUGAL.getCode());
		marketCountries.add(CountryEnum.SPAIN.getCode());
		return marketCountries;
	}

	public static Set<String> getMarketTypeCodesDefault() {
		Set<String> marketTypeCodes = new HashSet<>();
		marketTypeCodes.add("MATCH_ODDS");
		return marketTypeCodes;
	}

//	public static Set<String> getAvailableCompetitions() {
//		Set<String> competitionIds = new HashSet<>();
//		CompetitionModel.getCompetitionsAvailable().forEach(comp -> {
//			competitionIds.add(String.valueOf(comp.getId()));
//		});
//		return competitionIds;
//	}
//
//	public static Set<String> getFavoritesCompetitions() {
//		Set<String> competitionIds = new HashSet<>();
//		CompetitionModel.getCompetitionsAvailable().stream().filter(comp -> comp.isFavorite()).forEach(comp -> {
//			competitionIds.add(String.valueOf(comp.getId()));
//		});
//		return competitionIds;
//	}

	public static Set<String> buildWithEventTypes(Long ...eventTypeIds) {
		Set<String> eventTypeIdsRetzz = new HashSet<>();
		for (Long id : eventTypeIds) {
			eventTypeIdsRetzz.add(id.toString());
			
		}
		return eventTypeIdsRetzz;
	}

	public static PriceProjection getPriceProjectionDefault() {
		PriceProjection priceProjection = new PriceProjection();
        Set<PriceData> priceData = new HashSet<PriceData>();
//        priceData.add(PriceData.EX_ALL_OFFERS);
        priceData.add(PriceData.EX_BEST_OFFERS);
//        priceData.add(PriceData.EX_TRADED);
//        priceData.add(PriceData.SP_AVAILABLE);
//        priceData.add(PriceData.SP_TRADED);
        priceProjection.setPriceData(priceData);
        
        return priceProjection;
	}

}
