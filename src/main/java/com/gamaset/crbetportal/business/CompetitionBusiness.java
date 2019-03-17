package com.gamaset.crbetportal.business;

import static com.gamaset.crbetportal.infra.log.LogEvent.create;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gamaset.crbetportal.business.builder.MarketFilterBuilder;
import com.gamaset.crbetportal.infra.configuration.ApiNgAccessKeysConfiguration;
import com.gamaset.crbetportal.infra.exception.BusinessException;
import com.gamaset.crbetportal.infra.exception.NotFoundException;
import com.gamaset.crbetportal.infra.log.LogEvent;
import com.gamaset.crbetportal.integration.betfair.aping.entities.CompetitionResult;
import com.gamaset.crbetportal.integration.betfair.aping.entities.MarketFilter;
import com.gamaset.crbetportal.integration.betfair.aping.entities.TimeRange;
import com.gamaset.crbetportal.repository.CompetitionRepository;
import com.gamaset.crbetportal.repository.EventTypeRepository;
import com.gamaset.crbetportal.repository.entity.CompetitionModel;
import com.gamaset.crbetportal.repository.entity.EventTypeModel;
import com.gamaset.crbetportal.schema.CompetitionSchema;
import com.gamaset.crbetportal.service.CompetitionService;

@Component
public class CompetitionBusiness {

	private static final Logger LOG_ACTION = LogEvent.logger("ACTION");
	private static final Logger LOG_ERROR = LogEvent.logger("ERROR");

	private MarketFilterBuilder marketBuilder;
	private EventTypeRepository eventTypeRepository;
	private CompetitionRepository competitionRepository;
	private CompetitionService competitionService;
	private ApiNgAccessKeysConfiguration accessKeysConfiguration;

	@Autowired
	public CompetitionBusiness(ApiNgAccessKeysConfiguration accessKeysConfiguration,
			CompetitionService competitionService, CompetitionRepository competitionRepository,
			EventTypeRepository eventTypeRepository, MarketFilterBuilder marketBuilder) {
		this.accessKeysConfiguration = accessKeysConfiguration;
		this.competitionService = competitionService;
		this.competitionRepository = competitionRepository;
		this.eventTypeRepository = eventTypeRepository;
		this.marketBuilder = marketBuilder;
	}

	public List<CompetitionSchema> list(Long eventTypeId, TimeRange timeRange) {

		List<CompetitionSchema> listCompetitions = new ArrayList<CompetitionSchema>();

		try {

			LOG_ACTION.info(create("Listando Competicoes por Tipo de Evento").add("eventTypeId", eventTypeId).build());

			Optional<EventTypeModel> eventTypeOpt = eventTypeRepository.findById(eventTypeId);
			if(!eventTypeOpt.isPresent()) {
				LOG_ERROR.error(create("Tipo de Evento não encontrado").add("eventTypeId", eventTypeId).build());
				throw new NotFoundException("Tipo de Evento não encontrado");
			}
			
			EventTypeModel eventTypeModel = eventTypeOpt.get();
			
			List<CompetitionModel> listCompetitionsActive = competitionRepository.findByEventTypeIdAndActiveTrue(eventTypeId);
			MarketFilter marketFilter = marketBuilder.filterCompetitionByIds(listCompetitionsActive, timeRange);
			
			List<CompetitionResult> listCompetitionsResult = competitionService.listCompetitions(marketFilter,
					accessKeysConfiguration.getAppKey(), accessKeysConfiguration.getSsoToken());
			
			if (Objects.nonNull(listCompetitionsResult)) {
				listCompetitionsResult.stream().forEach(c -> {
					listCompetitions.add(new CompetitionSchema(c, eventTypeModel));
				});
			}

		} catch (BusinessException e) {
			LOG_ERROR.info(create("Erro ao Listar Competicoes por Tipo de Evento").build());
			throw e;
		}

		return listCompetitions;
	}

	public CompetitionSchema getById(Long competitionId) {
		CompetitionSchema response = null;
		Optional<CompetitionModel> compOpt = competitionRepository.findById(competitionId);

		if (!compOpt.isPresent()) {
			LOG_ERROR.info(create("Erro ao Buscar Competicao por ID").add("competitionId", competitionId).build());
			throw new NotFoundException("Competicao nao encontrada");
		}

		response = new CompetitionSchema(compOpt.get());

		return response;
	}

}
