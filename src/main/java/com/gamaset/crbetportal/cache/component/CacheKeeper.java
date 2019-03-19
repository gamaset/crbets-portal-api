package com.gamaset.crbetportal.cache.component;

import static org.springframework.util.Assert.notNull;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gamaset.crbetportal.endpoint.PeriodFilter;
import com.gamaset.crbetportal.infra.exception.FailPutCacheException;
import com.gamaset.crbetportal.schema.SidebarSchema;
import com.gamaset.crbetportal.schema.response.CompetitionEventsResponse;

@Component
public class CacheKeeper {
	
	private final PortalCache portalCache;
	private final EventCache eventCache;
	
	@Autowired
	public CacheKeeper(EventCache eventCache, PortalCache portalCache) {
		this.eventCache = eventCache;
		this.portalCache = portalCache;
	}
	
	/*
	 * --- GET
	 */
	/**
	 * 
	 * @param categoryId
	 * @return 
	 * @return {@link EditorialBrandsResponse}
	 * @throws llegalArgumentException
	 */
	public List<CompetitionEventsResponse> getFavoriteEvents(Long eventTypeId, PeriodFilter period) {
		notNull(eventTypeId, "eventTypeId nao pode ser nulo");
		notNull(period, "period nao pode ser nulo");
		
		return eventCache.getFavoriteEvents(eventTypeId, period);
	}
	
	public void saveFavoriteEvents(Long eventTypeId, PeriodFilter period, List<CompetitionEventsResponse> favoriteEvents) {
		notNull(eventTypeId, "eventTypeId nao pode ser nulo");
		notNull(period, "period nao pode ser nulo");
		
		boolean process = eventCache.putFavoriteEvents(eventTypeId, period, favoriteEvents);
		
		if (!process) {
			throw new FailPutCacheException("Erro ao salvar eventos favoritos no cache");
		}
	}

	public List<SidebarSchema> getSidebarItems(PeriodFilter period) {
		notNull(period, "period nao pode ser nulo");
		
		return portalCache.getSidebarItems(period);
		
	}
	public void putSidebarItems(PeriodFilter period, List<SidebarSchema> items) {
		notNull(period, "period nao pode ser nulo");
		
		boolean process = portalCache.putSidebarItems(period, items);
		
		if (!process) {
			throw new FailPutCacheException("Erro ao salvar eventos favoritos no cache");
		}
		
	}
	

}
