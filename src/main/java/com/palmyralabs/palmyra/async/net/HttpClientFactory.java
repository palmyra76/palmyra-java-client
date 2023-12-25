package com.palmyralabs.palmyra.async.net;

import java.net.ProxySelector;
import java.net.http.HttpClient;

public class HttpClientFactory {
	private static HttpClient httpClient;

	public static HttpClient getInstance() {
		if (null != httpClient)
			return httpClient;

		httpClient = HttpClient.newBuilder()
				.proxy(ProxySelector.getDefault())
				.followRedirects(HttpClient.Redirect.NORMAL)
				.build();

		return httpClient;
	}
}
