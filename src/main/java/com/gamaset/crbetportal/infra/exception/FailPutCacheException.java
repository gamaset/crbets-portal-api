package com.gamaset.crbetportal.infra.exception;

public class FailPutCacheException extends RuntimeException{
	
	private static final long serialVersionUID = -4119457690057006775L;

	public FailPutCacheException() {
		super();
	}

	public FailPutCacheException(String message, Throwable cause) {
		super(message, cause);
	}

	public FailPutCacheException(String message) {
		super(message);
	}

	public FailPutCacheException(Throwable cause) {
		super(cause);
	}

}
