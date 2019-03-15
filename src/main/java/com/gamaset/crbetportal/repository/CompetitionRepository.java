package com.gamaset.crbetportal.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.gamaset.crbetportal.repository.entity.CompetitionModel;

@Repository
public interface CompetitionRepository extends PagingAndSortingRepository<CompetitionModel, Long> {

	List<CompetitionModel> findByEventTypeId(Long eventTypeId);

	List<CompetitionModel> findByEventTypeIdAndActiveTrue(Long eventTypeId);

}
