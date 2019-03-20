package com.gamaset.crbetportal.business;

import static com.gamaset.crbetportal.infra.log.LogEvent.create;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gamaset.crbetportal.business.builder.MarketFilterBuilder;
import com.gamaset.crbetportal.cache.component.CacheKeeper;
import com.gamaset.crbetportal.endpoint.PeriodFilter;
import com.gamaset.crbetportal.infra.configuration.ApiNgAccessKeysConfiguration;
import com.gamaset.crbetportal.infra.exception.BusinessException;
import com.gamaset.crbetportal.infra.log.LogEvent;
import com.gamaset.crbetportal.infra.utils.TimeRangeUtils;
import com.gamaset.crbetportal.integration.betfair.aping.entities.EventTypeResult;
import com.gamaset.crbetportal.integration.betfair.aping.entities.MarketFilter;
import com.gamaset.crbetportal.repository.EventTypeRepository;
import com.gamaset.crbetportal.repository.entity.EventTypeModel;
import com.gamaset.crbetportal.schema.CompetitionSchema;
import com.gamaset.crbetportal.schema.SidebarSchema;
import com.gamaset.crbetportal.service.EventTypeService;

@Component
public class PortalBuilderBusiness {

	private static final Logger LOG_ACTION = LogEvent.logger("ACTION");
	private static final Logger LOG_ERROR = LogEvent.logger("ERROR");

	private CacheKeeper cacheKeeper;
	private EventTypeService eventTypeService;
	private CompetitionBusiness competitionBusiness;
	private EventTypeRepository eventTypeRepository;
	private ApiNgAccessKeysConfiguration accessKeysConfiguration;
	private MarketFilterBuilder marketBuilder;

	@Autowired
	public PortalBuilderBusiness(CacheKeeper cacheKeeper, EventTypeService eventTypeService,
			CompetitionBusiness competitionBusiness, EventTypeRepository eventTypeRepository,
			ApiNgAccessKeysConfiguration accessKeysConfiguration, MarketFilterBuilder marketBuilder) {
		this.cacheKeeper = cacheKeeper;
		this.eventTypeService = eventTypeService;
		this.competitionBusiness = competitionBusiness;
		this.eventTypeRepository = eventTypeRepository;
		this.accessKeysConfiguration = accessKeysConfiguration;
		this.marketBuilder = marketBuilder;
	}

	public List<SidebarSchema> listWithCompetitions() {

		PeriodFilter period = PeriodFilter.TODAY;
		List<SidebarSchema> response = null;

		try {

			LOG_ACTION.info(create("Montando Conteudo do Sidebar").build());

			response = cacheKeeper.getSidebarItems(period);

			if (Objects.isNull(response)) {
				response = new ArrayList<>();

				List<EventTypeModel> events = eventTypeRepository.findByActiveTrue();

				MarketFilter marketFilter = marketBuilder.filterEventTypeByIds(events);
				List<EventTypeResult> eventTypes = eventTypeService.listEventTypesApiNg(marketFilter,
						accessKeysConfiguration.getAppKey(), accessKeysConfiguration.getSsoToken());

				for (EventTypeResult et : eventTypes) {
					List<CompetitionSchema> competitions = competitionBusiness.list(Long.valueOf(et.getEventType().getId()), TimeRangeUtils.getByPeriod(period));
					response.add(new SidebarSchema(et, competitions));
				}

				cacheKeeper.putSidebarItems(period, response);
			}

		} catch (BusinessException e) {
			LOG_ERROR.info(create("Erro ao Montar Sidebar").build());
			throw e;
		}

		return response;
	}

}
