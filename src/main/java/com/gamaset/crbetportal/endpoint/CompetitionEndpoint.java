package com.gamaset.crbetportal.endpoint;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gamaset.crbetportal.business.CompetitionBusiness;
import com.gamaset.crbetportal.schema.CompetitionSchema;
import com.gamaset.crbetportal.schema.response.GenericResponse;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/v1/event-types/{eventTypeId}/competitions")
public class CompetitionEndpoint {

	@Autowired
	private CompetitionBusiness competitionBusiness;
	

	@GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
	public GenericResponse<CompetitionSchema> list(@PathVariable("eventTypeId") Long eventTypeId) {
		return new GenericResponse<CompetitionSchema>(competitionBusiness.list(eventTypeId, null));
	}
	

}
