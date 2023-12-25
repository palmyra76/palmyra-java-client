package com.palmyralabs.palmyra.async.exception;

public class HttpNetworkException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public HttpNetworkException(String message, Throwable t) {
		super(message, t);
	}
}
