package com.gamaset.crbetportal.infra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.gamaset.crbetportal.schema.response.ErrorResponse;

@RestControllerAdvice
public class ExceptionHandler {
    
	@RequestMapping(produces = "application/json")
    @org.springframework.web.bind.annotation.ExceptionHandler({BusinessException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResponse handleAuthorizationException(RuntimeException ex) {
    	return new ErrorResponse(ex.getMessage());
    }

	@RequestMapping(produces = "application/json")
	@org.springframework.web.bind.annotation.ExceptionHandler(NotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ErrorResponse handleAuthorizationException(NotFoundException ex) {
		return new ErrorResponse(ex.getMessage());
	}
}
