package com.palmyralabs.palmyra.async.net;

import java.net.http.HttpResponse;

import com.palmyralabs.palmyra.async.FailedResponse;

public class NetResponse implements FailedResponse{
	private HttpResponse<?> response;
	private String body;
	
	public NetResponse(HttpResponse<?> response, String body) {
		this.response = response;
		this.body = body;
	}

	@Override
	public int statusCode() {		
		return response.statusCode();
	}

	@Override
	public String body() {
		return body;
	}

	@Override
	public String url() {
		return response.uri().toString();
	}
	
	
}
