package com.gamaset.crbetportal.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.gamaset.crbetportal.repository.entity.EventTypeModel;

@Repository
public interface EventTypeRepository extends PagingAndSortingRepository<EventTypeModel, Long> {

	List<EventTypeModel> findByActiveTrue();


}
