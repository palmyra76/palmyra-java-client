package com.palmyralabs.palmyra.async.net;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.palmyralabs.palmyra.async.ReactiveClient;
import com.palmyralabs.palmyra.async.RequestConfig;
import com.palmyralabs.palmyra.async.ResponseHandler;
import com.palmyralabs.palmyra.client.auth.AuthClient;

import lombok.SneakyThrows;

public class ReactiveHttpClient implements ReactiveClient {

	private String baseUrl;
	private AuthClient authClient;
	private int timeoutInSeconds = 30;

	public ReactiveHttpClient(String baseUrl, AuthClient authClient) {
		if (baseUrl.endsWith("/"))
			this.baseUrl = baseUrl;
		else
			this.baseUrl = baseUrl + "/";
		this.authClient = authClient;
	}

	@SneakyThrows
	private <R> void asyncRequest(RequestConfig requestConfig, HttpRequest request, ResponseHandler<R> handler) {
		ResponseParser<R> parser = new ResponseParser<R>(requestConfig.getOperation(), handler);

		getClient().sendAsync(request, HttpResponse.BodyHandlers.ofInputStream())
				.thenAcceptAsync(parser, handler.getExecutor())
				.exceptionally(handler.getHandler(requestConfig, NetRequest.of(request)));
	}

	protected void assignHeaders(Builder builder) {
		Map<String, String> headers = authClient.getHeaders();
		addHeaders(builder, headers);
	}

	private void addHeaders(Builder builder, Map<String, String> headers) {
		for (Entry<String, String> entry : headers.entrySet()) {
			builder.header(entry.getKey(), entry.getValue());
		}
	}

	@SneakyThrows
	private <T> BodyPublisher getBody(T body) {
		ObjectMapper mapper = ObjectMapperFactory.getInstance();
		byte[] value = mapper.writeValueAsBytes(body);
		return BodyPublishers.ofByteArray(value);
	}

	private Builder getBuilder(String url, RequestConfig requestConfig) {
		Builder result = HttpRequest.newBuilder().uri(getUri(url)).timeout(getTimeout());
		assignHeaders(result);
		configureRequest(result, requestConfig);
		return result;
	}

	private void configureRequest(Builder result, RequestConfig requestConfig) {
		addHeaders(result, requestConfig.getHeaders());
	}

	private Duration getTimeout() {
		return Duration.ofSeconds(timeoutInSeconds);
	}

	@SneakyThrows
	private URI getUri(String url) {
		return new URI(baseUrl + url);
	}

	private HttpClient getClient() {
		return HttpClientFactory.getInstance();
	}

	@Override
	public <T, R> void post(String url, T body, ResponseHandler<R> handler, RequestConfig requestConfig) {
		HttpRequest request = getBuilder(url, requestConfig).POST(getBody(body)).build();
		asyncRequest(requestConfig, request, handler);
	}

	@Override
	public <T, R> void put(String url, T body, ResponseHandler<R> handler, RequestConfig requestConfig) {
		HttpRequest request = getBuilder(url, requestConfig).PUT(getBody(body)).build();
		asyncRequest(requestConfig, request, handler);
	}

	@Override
	public <R> void delete(String url, ResponseHandler<R> handler, RequestConfig requestConfig) {
		HttpRequest request = getBuilder(url, requestConfig).DELETE().build();
		asyncRequest(requestConfig, request, handler);
	}

	@Override
	public <R> void get(String url, ResponseHandler<R> handler, RequestConfig requestConfig) {
		HttpRequest request = getBuilder(url, requestConfig).GET().build();
		asyncRequest(requestConfig, request, handler);
	}
}
