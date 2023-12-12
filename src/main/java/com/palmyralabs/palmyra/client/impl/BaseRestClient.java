/**
 * <LICENSE/>
 */
package com.palmyralabs.palmyra.client.impl;

import java.io.IOException;
import java.net.ConnectException;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.hc.client5.http.ClientProtocolException;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpMessage;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.io.support.ClassicRequestBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.palmyralabs.palmyra.client.exception.ClientException;
import com.palmyralabs.palmyra.client.pojo.FilterCriteria;

/**
 * @author ksvraja
 *
 */
public abstract class BaseRestClient {
	private static CloseableHttpClient httpclient = HttpClientProvider.getClient();
	private static final Logger logger = LoggerFactory.getLogger(BaseRestClient.class);
	private static final ObjectMapper objectMapper = new ObjectMapper();

	static {
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	protected abstract void setAuthentication(HttpMessage request);

	public BaseRestClient() {

	}

	protected <T> T post(String URL, String data, HttpClientResponseHandler<T> handler) throws IOException {
		HttpPost httpPost = new HttpPost(URL);
		StringEntity body = new StringEntity(data, ContentType.create("application/json", "UTF-8"));
		httpPost.setEntity(body);
		return execute(httpPost, handler);
	}

	protected <T> T put(String URL, String data, HttpClientResponseHandler<T> handler) throws IOException {
		HttpPut httpPost = new HttpPut(URL);
		StringEntity body = new StringEntity(data, ContentType.create("application/json", "UTF-8"));
		httpPost.setEntity(body);
		return execute(httpPost, handler);
	}

	protected <T> T get(String URL, FilterCriteria filter, HttpClientResponseHandler<T> responseHandler)
			throws IOException {

		ClassicRequestBuilder builder = ClassicRequestBuilder.get(URL);
		if (filter.isTotal())
			builder.addParameter("_total", "true");
		if (null != filter.getLimit())
			builder.addParameter("_limit", Integer.toString(filter.getLimit()));
		if (null != filter.getOffset() && filter.getOffset() > 0)
			builder.addParameter("_offset", Integer.toString(filter.getOffset()));
		if (null != filter.getOrderBy() && filter.getOrderBy().size() > 0) {
			String orderBy = filter.getOrderBy().stream().collect(Collectors.joining(","));
			builder.addParameter("_orderBy", orderBy);
		}
		if (null != filter.getCriteria() && filter.getCriteria().size() > 0) {
			for (Entry<String, String> entry : filter.getCriteria().entrySet()) {
				builder.addParameter(entry.getKey(), entry.getValue());
			}
		}		

		return execute(builder.build(), responseHandler);
	}

	protected <T> T get(String URL, HttpClientResponseHandler<T> handler) throws IOException {
		HttpGet httpGet = new HttpGet(URL);
		return execute(httpGet, handler);
	}

	protected <T> T delete(String URL, HttpClientResponseHandler<T> handler) throws IOException {
		HttpDelete httpDelete = new HttpDelete(URL);
		return execute(httpDelete, handler);
	}

	protected <T> T post(String URL, Object data, HttpClientResponseHandler<T> handler) throws IOException {
		return post(URL, objectMapper.writeValueAsString(data), handler);
	}

	protected <T> T put(String URL, Object data, HttpClientResponseHandler<T> handler) throws IOException {
		return put(URL, objectMapper.writeValueAsString(data), handler);
	}

	protected <T> T execute(ClassicHttpRequest request, HttpClientResponseHandler<T> responseHandler)
			throws IOException {
		request.addHeader("accept", "application/json");
		request.addHeader("content-type", "application/json");
		setAuthentication(request);
		try {
			return httpclient.execute(request, responseHandler);

		} catch (ConnectException ce) {
			logger.info("Server Connection refused !!");
			logger.info(ce.getMessage());
			throw new ClientException(HttpStatus.SC_SERVICE_UNAVAILABLE,
					"Server Connection refused !! Please check server reachability", ce);
		} catch (ClientProtocolException e1) {
			throw new ClientException(HttpStatus.SC_BAD_REQUEST, "Invalid protocol", e1);
		}
	}

	public void close() {
		try {
			httpclient.close();
		} catch (Throwable e) {
		}
	}
}
