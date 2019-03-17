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
import com.gamaset.crbetportal.schema.response.GenericResponse;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/v1/event-types/{eventTypeId}/events")
public class EventFavoritesEndpoint {

	@Autowired
	private EventBusiness eventBusiness;
	

	@GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
	public GenericResponse<CompetitionEventsResponse> list(@PathVariable("eventTypeId") Long eventTypeId) {
		return new GenericResponse<CompetitionEventsResponse>(eventBusiness.listFavoritesByEventType(eventTypeId));
	}

}
