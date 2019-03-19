package com.gamaset.crbetportal.endpoint;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gamaset.crbetportal.business.PortalBuilderBusiness;
import com.gamaset.crbetportal.schema.SidebarSchema;
import com.gamaset.crbetportal.schema.response.GenericResponse;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/v1/portal/shared")
public class SidebarEndpoint {
	
	@Autowired
	private PortalBuilderBusiness portalBusiness;
	
	@GetMapping(value = "/sidebar", produces = APPLICATION_JSON_UTF8_VALUE)
	public GenericResponse<SidebarSchema> listWithCompetitions(){
		return new GenericResponse<SidebarSchema>(portalBusiness.listWithCompetitions());
	}

}
