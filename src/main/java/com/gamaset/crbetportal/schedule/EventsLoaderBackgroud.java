package com.gamaset.crbetportal.schedule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gamaset.crbetportal.business.EventBusiness;
import com.gamaset.crbetportal.cache.component.CacheKeeper;
import com.gamaset.crbetportal.endpoint.PeriodFilter;
import com.gamaset.crbetportal.schema.response.CompetitionEventsResponse;

//@Component
public class EventsLoaderBackgroud {
	
	@Autowired
	private EventBusiness eventBusiness;
	@Autowired
	private CacheKeeper cacheKeeper;
	
	@Scheduled(fixedDelay = 120000)
	public void loadFavoriteEvents() {
		listFavoriteEvents(1L, PeriodFilter.TODAY);
		listFavoriteEvents(1L, PeriodFilter.TOMORROW);
	}

	public List<CompetitionEventsResponse> listFavoriteEvents(Long eventTypeId, PeriodFilter period){
		List<CompetitionEventsResponse> events = eventBusiness.listFavoritesByEventType(eventTypeId, period);
		
		cacheKeeper.saveFavoriteEvents(eventTypeId, period, events);
		
		return events;
	}
	
}
