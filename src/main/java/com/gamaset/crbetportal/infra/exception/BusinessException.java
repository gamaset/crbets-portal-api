package com.gamaset.crbetportal.infra.exception;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = -4885177945079359790L;

	public BusinessException() {
		super();
	}

	public BusinessException(final String message) {
		super(message);
	}

	public BusinessException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public BusinessException(final Throwable cause) {
		super(cause);
	}

}