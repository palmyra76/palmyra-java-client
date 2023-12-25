package com.palmyralabs.palmyra.async;

public interface ReactiveClient {

	public <T, R> void post(String url, T body, ResponseHandler<R> handler, RequestConfig requestConfig);

	public <T, R> void put(String url, T body, ResponseHandler<R> handler, RequestConfig requestConfig);

	public <R> void delete(String url, ResponseHandler<R> handler, RequestConfig requestConfig);

	public <R> void get(String url, ResponseHandler<R> handler, RequestConfig requestConfig);
	
	public <R> void getAll(String url, ListResponseHandler<R> handler, RequestConfig requestConfig);

	public default <T, R> void post(String operation, String url, T body, ResponseHandler<R> handler) {
		post(url, body, handler, RequestConfig.of(operation));
	}

	public default <T, R> void put(String operation, String url, T body, ResponseHandler<R> handler) {
		put(url, body, handler, RequestConfig.of(operation));
	}

	public default <R> void delete(String operation, String url, ResponseHandler<R> handler) {
		delete(url, handler, RequestConfig.of(operation));
	}

	public default <R> void get(String operation, String url, ResponseHandler<R> handler) {
		get(url, handler, RequestConfig.of(operation));
	}
	
	public default <R> void getAll(String operation, String url, ListResponseHandler<R> handler) {
		getAll(url, handler, RequestConfig.of(operation));
	}
}
