package com.gamaset.crbetportal.schema;

import java.io.Serializable;

import com.gamaset.crbetportal.integration.betfair.aping.entities.CompetitionResult;
import com.gamaset.crbetportal.repository.entity.CompetitionModel;
import com.gamaset.crbetportal.repository.entity.EventTypeModel;

public class CompetitionSchema implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String description;
	private String countryCode;
	private EventTypeModel eventType;
	private boolean active;
	private int count;

	public CompetitionSchema() {
	}

	public CompetitionSchema(CompetitionModel model) {
		setId(model.getId());
		setDescription(model.getDescription());
		setCountryCode(model.getCountryCode());
		setEventType(model.getEventType());
		setActive(model.isActive());

	}

	public CompetitionSchema(CompetitionResult c, EventTypeModel eventType) {
		setId(Long.valueOf(c.getCompetition().getId()));
		setDescription(c.getCompetition().getName());
		setCountryCode(c.getCompetitionRegion());
		setEventType(eventType);
		setActive(true);
		setCount(c.getMarketCount());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public EventTypeModel getEventType() {
		return eventType;
	}

	public void setEventType(EventTypeModel eventType) {
		this.eventType = eventType;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
