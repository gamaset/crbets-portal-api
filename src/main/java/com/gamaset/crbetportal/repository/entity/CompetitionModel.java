package com.gamaset.crbetportal.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@Table(name = "competicao")
public class CompetitionModel {

	@Id
	@Column
	private Long id;
	@Column(name = "descricao")
	private String description;
	@Column(name = "pais_codigo")
	private String countryCode;
	@ManyToOne
	@JoinColumn(name = "tipo_evento_fk")
	private EventTypeModel eventType;
	@Column(name = "ativo")
	private boolean active;
	
	public CompetitionModel() {
	}

	public CompetitionModel(Long id, String description, String countryCode, EventTypeModel eventType, 
			boolean active) {
		this.id = id;
		this.description = description;
		this.countryCode = countryCode;
		this.eventType = eventType;
		this.active = active;
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

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
