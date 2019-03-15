package com.gamaset.crbetportal.business;

import static com.gamaset.crbetportal.infra.log.LogEvent.create;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gamaset.crbetportal.infra.configuration.ApiNgAccessKeysConfiguration;
import com.gamaset.crbetportal.infra.exception.BusinessException;
import com.gamaset.crbetportal.infra.exception.NotFoundException;
import com.gamaset.crbetportal.infra.log.LogEvent;
import com.gamaset.crbetportal.infra.utils.MarketFilterUtils;
import com.gamaset.crbetportal.integration.betfair.aping.entities.CompetitionResult;
import com.gamaset.crbetportal.integration.betfair.aping.entities.MarketFilter;
import com.gamaset.crbetportal.repository.CompetitionRepository;
import com.gamaset.crbetportal.repository.EventTypeRepository;
import com.gamaset.crbetportal.repository.entity.CompetitionModel;
import com.gamaset.crbetportal.schema.CompetitionSchema;
import com.gamaset.crbetportal.service.CompetitionService;

@Component
public class CompetitionBusiness {

	private static final Logger LOG_ACTION = LogEvent.logger("ACTION");
	private static final Logger LOG_ERROR = LogEvent.logger("ERROR");

	private EventTypeRepository eventTypeRepository;
	private CompetitionRepository competitionRepository;
	private CompetitionService competitionService;
	private ApiNgAccessKeysConfiguration accessKeysConfiguration;

	@Autowired
	public CompetitionBusiness(ApiNgAccessKeysConfiguration accessKeysConfiguration,
			CompetitionService competitionService, CompetitionRepository competitionRepository,
			EventTypeRepository eventTypeRepository) {
		this.accessKeysConfiguration = accessKeysConfiguration;
		this.competitionService = competitionService;
		this.competitionRepository = competitionRepository;
		this.eventTypeRepository = eventTypeRepository;
	}

	public List<CompetitionSchema> list(Long eventTypeId, boolean favorites) {

		List<CompetitionSchema> listCompetitions = new ArrayList<CompetitionSchema>();

		try {

			LOG_ACTION.info(create("Listando Competicoes por Tipo de Evento").add("eventTypeId", eventTypeId).build());

//			List<CompetitionModel> listCompetitionsResult = null;
//			if(favorites) {
//				listCompetitionsResult = competitionRepository.findByEventTypeIdAndActiveTrue(eventTypeId);
//			}else {
//				listCompetitionsResult = competitionRepository.findByEventTypeId(eventTypeId);
//			}
//
//			if (Objects.nonNull(listCompetitionsResult)) {
//				listCompetitionsResult.stream().forEach(c -> {
//					listCompetitions.add(new CompetitionSchema(c));
//				});
//			}

			listCompetitions = listByApiNg(eventTypeId, favorites);

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

	private List<CompetitionSchema> listByApiNg(Long eventTypeId, boolean favorites) {
		List<CompetitionSchema> listCompetitions = new ArrayList<CompetitionSchema>();

		MarketFilter marketFilter = new MarketFilter();

		Set<String> eventTypeIds = new HashSet<>();
		eventTypeIds.add(eventTypeId.toString());
		marketFilter.setEventTypeIds(eventTypeIds);
//		marketFilter.setMarketTypeCodes(MarketFilterUtils.getMarketTypeCodesDefault());
		marketFilter.setMarketStartTime(MarketFilterUtils.getTimeRangeDefault());

		if (favorites) {
			marketFilter.setMarketCountries(MarketFilterUtils.getCountriesDefault());
		}

		List<CompetitionResult> listCompetitionsResult = competitionService.listCompetitions(marketFilter,
				accessKeysConfiguration.getAppKey(), accessKeysConfiguration.getSsoToken());
		
		
		
		List<CompetitionModel> listCompetitionsModel = new ArrayList<CompetitionModel>();
		if (Objects.nonNull(listCompetitionsResult)) {
			listCompetitionsResult.forEach(c -> {
				CompetitionModel competitionModel = new CompetitionModel(Long.valueOf(c.getCompetition().getId()), c.getCompetition().getName(),
						c.getCompetitionRegion(), eventTypeRepository.findById(eventTypeId).get(), true);
				CompetitionModel competitionModel2 = competitionRepository.save(competitionModel);
				listCompetitionsModel.add(competitionModel2);
			});
		}

		if (Objects.nonNull(listCompetitionsModel)) {
			listCompetitionsModel.forEach(c -> {
				listCompetitions.add(new CompetitionSchema(c));
			});
		}
		
		return listCompetitions;
	}

}
