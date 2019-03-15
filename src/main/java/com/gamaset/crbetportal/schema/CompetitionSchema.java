package com.gamaset.crbetportal.schema;

import com.gamaset.crbetportal.repository.entity.CompetitionModel;
import com.gamaset.crbetportal.repository.entity.EventTypeModel;

public class CompetitionSchema {

	private Long id;
	private String description;
	private String countryCode;
	private EventTypeModel eventType;
	private boolean active;

	public CompetitionSchema() {
	}
	
	public CompetitionSchema(CompetitionModel model) {
		setId(model.getId());
		setDescription(model.getDescription());
		setCountryCode(model.getCountryCode());
		setEventType(model.getEventType());
		setActive(model.isActive());
		
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
	
}
