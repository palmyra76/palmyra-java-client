package com.palmyralabs.palmyra.async.net;

import java.net.http.HttpRequest;

import com.palmyralabs.palmyra.async.FailedRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NetRequest implements FailedRequest{
	private final HttpRequest request;
	
	public static NetRequest of(HttpRequest req) {
		return new NetRequest(req);
	}
	
	@Override
	public String url() {
		return request.uri().toString();
	}

}
