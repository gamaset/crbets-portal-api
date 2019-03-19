package com.gamaset.crbetportal.schema;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.gamaset.crbetportal.integration.betfair.aping.entities.EventResult;
import com.gamaset.crbetportal.integration.betfair.aping.entities.MarketCatalogue;
import com.gamaset.crbetportal.integration.betfair.aping.entities.RunnerCatalog;

public class EventSchema implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime openDate;
	private List<MarketSchema> markets;

	public EventSchema() {
	}

	public EventSchema(EventResult e) {
		addEventInfo(e.getEvent().getId(), e.getEvent().getName(), e.getEvent().getOpenDate());
	}

	public EventSchema addEventInfo(String id, String name, Date openDate) {
		setId(id);
		setName(name);
		setOpenDate(openDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
		return this;

	}

	public void addMarket(MarketCatalogue marketCatalogue) {
		if (Objects.isNull(markets)) {
			markets = new ArrayList<MarketSchema>();
		}

		MarketSchema marketSchema = new MarketSchema(marketCatalogue.getMarketId(), marketCatalogue.getMarketName());
		marketSchema.setPrices(new ArrayList<PriceMarketSchema>());
		for (RunnerCatalog rc : marketCatalogue.getRunners()) {
			PriceMarketSchema price = new PriceMarketSchema();
			price.setSelectionId(rc.getSelectionId());
			price.setSelectionName(rc.getRunnerName());
			marketSchema.getPrices().add(price);
		}

		markets.add(marketSchema);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDateTime getOpenDate() {
		return openDate;
	}

	public void setOpenDate(LocalDateTime openDate) {
		this.openDate = openDate;
	}

	public List<MarketSchema> getMarkets() {
		return markets;
	}

	public void setMarkets(List<MarketSchema> markets) {
		this.markets = markets;
	}

}
