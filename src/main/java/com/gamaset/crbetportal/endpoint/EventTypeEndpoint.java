package com.gamaset.crbetportal.endpoint;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gamaset.crbetportal.business.EventTypeBusiness;
import com.gamaset.crbetportal.integration.betfair.aping.entities.EventTypeResult;
import com.gamaset.crbetportal.schema.response.GenericResponse;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/v1/event-types")
public class EventTypeEndpoint {

	@Autowired
	private EventTypeBusiness eventTypeBusiness;
	

	@GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
	public GenericResponse<EventTypeResult> list() {
		return new GenericResponse<EventTypeResult>(eventTypeBusiness.list());
	}
	

}
