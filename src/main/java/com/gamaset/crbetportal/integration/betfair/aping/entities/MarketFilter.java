package com.gamaset.crbetportal.integration.betfair.aping.entities;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.gamaset.crbetportal.integration.betfair.aping.enums.MarketBettingType;
import com.gamaset.crbetportal.integration.betfair.aping.enums.OrderStatus;
import com.gamaset.crbetportal.integration.betfair.aping.enums.PriceData;

public class MarketFilter {

	private String textQuery;
	private Set<String> exchangeIds;
	private Set<String> eventTypeIds;
	private Set<String> marketIds;
	private Boolean inPlayOnly;
	private Set<String> eventIds;
	private Set<String> competitionIds;
	private Set<String> venues;
	private Boolean bspOnly;
	private Boolean turnInPlayEnabled;
	private Set<MarketBettingType> marketBettingTypes;
	private Set<String> marketCountries;
	private Set<String> marketTypeCodes;
	private TimeRange marketStartTime;
	private Set<OrderStatus> withOrders;

	public String getTextQuery() {
		return textQuery;
	}

	public void setTextQuery(String textQuery) {
		this.textQuery = textQuery;
	}

	public Set<String> getExchangeIds() {
		return exchangeIds;
	}

	public void setExchangeIds(Set<String> exchangeIds) {
		this.exchangeIds = exchangeIds;
	}

	public Set<String> getEventTypeIds() {
		return eventTypeIds;
	}

	public void setEventTypeIds(Set<String> eventTypeIds) {
		this.eventTypeIds = eventTypeIds;
	}

	public Set<String> getMarketIds() {
		return marketIds;
	}

	public void setMarketIds(Set<String> marketIds) {
		this.marketIds = marketIds;
	}

	public Boolean getInPlayOnly() {
		return inPlayOnly;
	}

	public void setInPlayOnly(Boolean inPlayOnly) {
		this.inPlayOnly = inPlayOnly;
	}

	public Set<String> getEventIds() {
		return eventIds;
	}

	public void setEventIds(Set<String> eventIds) {
		this.eventIds = eventIds;
	}

	public Set<String> getCompetitionIds() {
		return competitionIds;
	}

	public void setCompetitionIds(Set<String> competitionIds) {
		this.competitionIds = competitionIds;
	}

	public Set<String> getVenues() {
		return venues;
	}

	public void setVenues(Set<String> venues) {
		this.venues = venues;
	}

	public Boolean getBspOnly() {
		return bspOnly;
	}

	public void setBspOnly(Boolean bspOnly) {
		this.bspOnly = bspOnly;
	}

	public Boolean getTurnInPlayEnabled() {
		return turnInPlayEnabled;
	}

	public void setTurnInPlayEnabled(Boolean turnInPlayEnabled) {
		this.turnInPlayEnabled = turnInPlayEnabled;
	}

	public Set<MarketBettingType> getMarketBettingTypes() {
		return marketBettingTypes;
	}

	public void setMarketBettingTypes(Set<MarketBettingType> marketBettingTypes) {
		this.marketBettingTypes = marketBettingTypes;
	}

	public Set<String> getMarketCountries() {
		return marketCountries;
	}

	public void setMarketCountries(Set<String> marketCountries) {
		this.marketCountries = marketCountries;
	}

	public Set<String> getMarketTypeCodes() {
		return marketTypeCodes;
	}

	public void setMarketTypeCodes(Set<String> marketTypeCodes) {
		this.marketTypeCodes = marketTypeCodes;
	}

	public TimeRange getMarketStartTime() {
		return marketStartTime;
	}

	public void setMarketStartTime(TimeRange marketStartTime) {
		this.marketStartTime = marketStartTime;
	}

	public Set<OrderStatus> getWithOrders() {
		return withOrders;
	}

	public void setWithOrders(Set<OrderStatus> withOrders) {
		this.withOrders = withOrders;
	}

	public static Set<String> buildSetIds(Long... ids) {
		Set<String> response = new HashSet<String>();
		for (Long id : ids) {
			response.add(id.toString());
		}
		return response;
	}

	public static TimeRange getTimeRangeDefault() {
		ZoneId zoneId = ZoneId.of("America/Sao_Paulo");
		TimeRange timeRange = new TimeRange();
		timeRange.setFrom(Date.from(Instant.now().atZone(zoneId).toInstant()));
		timeRange.setTo(
				Date.from(LocalDate.now().plusDays(3).atStartOfDay(zoneId).toInstant().atZone(zoneId).toInstant()));
		return timeRange;
	}

	/**
	 * Catalogo de Mercado Default = PROBABILIDADES
	 * @return
	 */
	public static Set<String> getMarketTypeCodesDefault() {
		Set<String> marketTypeCodes = new HashSet<>();
		marketTypeCodes.add("MATCH_ODDS");
		return marketTypeCodes;
	}

	public String toString() {
		return "{" + "" + "textQuery=" + getTextQuery() + "," + "exchangeIds=" + getExchangeIds() + ","
				+ "eventTypeIds=" + getEventTypeIds() + "," + "eventIds=" + getEventIds() + "," + "competitionIds="
				+ getCompetitionIds() + "," + "marketIds=" + getMarketIds() + "," + "venues=" + getVenues() + ","
				+ "bspOnly=" + getBspOnly() + "," + "turnInPlayEnabled=" + getTurnInPlayEnabled() + "," + "inPlayOnly="
				+ getInPlayOnly() + "," + "marketBettingTypes=" + getMarketBettingTypes() + "," + "marketCountries="
				+ getMarketCountries() + "," + "marketTypeCodes=" + getMarketTypeCodes() + "," + "marketStartTime="
				+ getMarketStartTime() + "," + "withOrders=" + getWithOrders() + "," + "}";
	}

	public static PriceProjection getPriceProjectionDefault() {
		PriceProjection priceProjection = new PriceProjection();
		Set<PriceData> priceData = new HashSet<PriceData>();
//	        priceData.add(PriceData.EX_ALL_OFFERS);
		priceData.add(PriceData.EX_BEST_OFFERS);
//	        priceData.add(PriceData.EX_TRADED);
//	        priceData.add(PriceData.SP_AVAILABLE);
//	        priceData.add(PriceData.SP_TRADED);
		priceProjection.setPriceData(priceData);

		return priceProjection;
	}

}
