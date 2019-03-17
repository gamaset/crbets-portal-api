package com.gamaset.crbetportal.business;

import static com.gamaset.crbetportal.infra.log.LogEvent.create;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gamaset.crbetportal.business.builder.MarketFilterBuilder;
import com.gamaset.crbetportal.infra.configuration.ApiNgAccessKeysConfiguration;
import com.gamaset.crbetportal.infra.exception.BusinessException;
import com.gamaset.crbetportal.infra.log.LogEvent;
import com.gamaset.crbetportal.integration.betfair.aping.entities.EventTypeResult;
import com.gamaset.crbetportal.integration.betfair.aping.entities.MarketFilter;
import com.gamaset.crbetportal.repository.EventTypeRepository;
import com.gamaset.crbetportal.repository.entity.EventTypeModel;
import com.gamaset.crbetportal.service.EventTypeService;

@Component
public class EventTypeBusiness {

	private static final Logger LOG_ACTION = LogEvent.logger("ACTION");
	private static final Logger LOG_ERROR = LogEvent.logger("ERROR");

	private MarketFilterBuilder marketBuilder;
	private EventTypeRepository eventTypeRepository;
	private EventTypeService eventTypeService;
	private ApiNgAccessKeysConfiguration accessKeysConfiguration;

	@Autowired
	public EventTypeBusiness(ApiNgAccessKeysConfiguration accessKeysConfiguration, EventTypeService eventTypeService, 
			EventTypeRepository eventTypeRepository, MarketFilterBuilder marketBuilder) {
		this.accessKeysConfiguration = accessKeysConfiguration;
		this.eventTypeService = eventTypeService;
		this.eventTypeRepository = eventTypeRepository;
		this.marketBuilder = marketBuilder;
	}

	public List<EventTypeResult> list() {
		List<EventTypeResult> listEventTypes = new ArrayList<EventTypeResult>();

		try {

			LOG_ACTION.info(create("Listando Tipos de Evento").build());

			List<EventTypeModel> events = eventTypeRepository.findByActiveTrue();

			MarketFilter marketFilter = marketBuilder.filterEventTypeByIds(events);
			listEventTypes = eventTypeService.listEventTypesApiNg(marketFilter, accessKeysConfiguration.getAppKey(),
					accessKeysConfiguration.getSsoToken());

		} catch (BusinessException e) {
			LOG_ERROR.info(create("Erro ao Listar Tipos de Evento").build());
			throw e;
		}

		return listEventTypes;
	}

}
