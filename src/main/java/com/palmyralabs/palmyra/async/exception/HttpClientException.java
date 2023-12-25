package com.palmyralabs.palmyra.async.exception;

public class HttpClientException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	private int statusCode = 0;
	
	public HttpClientException(String message, Throwable t) {
		super(message, t);
	}
	
	public HttpClientException(String message, int statusCode) {
		super(message);
		this.statusCode = statusCode;
	}
	
	public int getStatusCode() {
		return statusCode;
	}
}
