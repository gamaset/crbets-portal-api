package com.gamaset.crbetportal.endpoint;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gamaset.crbetportal.business.EventBusiness;
import com.gamaset.crbetportal.schema.response.CompetitionEventsResponse;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/v1/event-types/{eventTypeId}/competitions/{competitionId}/events")
public class EventEndpoint {

	@Autowired
	private EventBusiness eventBusiness;
	

	@GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
	public CompetitionEventsResponse list(@PathVariable("eventTypeId") Long eventTypeId, 
			@PathVariable("competitionId") Long competitionId) {
		return eventBusiness.listByEventTypeAndCompetitionId(eventTypeId, competitionId);
	}

	@GetMapping(value = "/{eventId}", produces = APPLICATION_JSON_UTF8_VALUE)
	public CompetitionEventsResponse getByEventId(@PathVariable("eventTypeId") Long eventTypeId, 
			@PathVariable("competitionId") Long competitionId, @PathVariable("eventId") Long eventId) {
		return eventBusiness.getByEventId(eventTypeId, competitionId, eventId);
	}
	

}
