package com.gamaset.crbetportal.schema;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.gamaset.crbetportal.integration.betfair.aping.entities.EventTypeResult;

public class SidebarSchema implements Serializable {

	private static final long serialVersionUID = 1L;

	private SidebarItemSchema eventType;
	private List<SidebarItemSchema> children = new ArrayList<>();

	public SidebarSchema() {
	}

	public SidebarSchema(EventTypeResult et, List<CompetitionSchema> list) {
		this.eventType = new SidebarItemSchema(et);
		if (Objects.nonNull(list) && list.size() > 0) {
			list.stream().forEach(c -> {
				children.add(new SidebarItemSchema(c));
			});
		}
	}

	public SidebarItemSchema getEventType() {
		return eventType;
	}

	public void setEventType(SidebarItemSchema eventType) {
		this.eventType = eventType;
	}

	public List<SidebarItemSchema> getChildren() {
		return children;
	}

	public void setChildren(List<SidebarItemSchema> children) {
		this.children = children;
	}

}
