package com.gamaset.crbetportal.schema;

import java.io.Serializable;

import com.gamaset.crbetportal.integration.betfair.aping.entities.EventTypeResult;

public class SidebarItemSchema implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	private String icon;
	private String url;

	public SidebarItemSchema() {
	}
	
	public SidebarItemSchema(EventTypeResult et) {
		setId(Long.valueOf(et.getEventType().getId()));
		setName(et.getEventType().getName() + " (" + et.getMarketCount() + ")");
		this.url = "/event-types/" + getId();
		this.icon = "icon-puzzle";
	}

	public SidebarItemSchema(CompetitionSchema c) {
		setId(c.getId());
		setName(c.getCountryCode() + " - " + c.getDescription() + " (" + c.getCount() + ")");
		this.url = "/competitions/" + getId();
		this.icon = "icon-puzzle";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return this.icon;
	}

	public String getUrl() {
		return this.url;
	}

}
