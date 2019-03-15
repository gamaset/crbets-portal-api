package com.gamaset.crbetportal.schema.response;

public class ErrorResponse {

	private String error;

	public ErrorResponse(String message) {
		this.error = message;
	}

	public String getError() {
		return error;
	}

	public void setError(String message) {
		this.error = message;
	}

}
