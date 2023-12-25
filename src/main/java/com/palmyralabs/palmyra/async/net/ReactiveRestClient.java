package com.palmyralabs.palmyra.async.net;

import java.net.http.HttpRequest.Builder;

import com.palmyralabs.palmyra.client.auth.AuthClient;

public class ReactiveRestClient extends ReactiveHttpClient {

	public ReactiveRestClient(String baseUrl, AuthClient authClient) {
		super(baseUrl, authClient);
	}

	protected void assignHeaders(Builder builder) {
		super.assignHeaders(builder);
		builder.header("Content-Type", "application/json");
	}
}
