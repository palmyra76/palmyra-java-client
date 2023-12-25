package com.palmyralabs.palmyra.async.exception;

public class HttpServerException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	private String responseBody;

	public HttpServerException(String message, String body) {
		super(message);
		this.responseBody = body;
	}
	
	public HttpServerException(String message, Throwable t) {
		super(message, t);
	}
	
	public String getBody() {
		return responseBody;
	}
}
