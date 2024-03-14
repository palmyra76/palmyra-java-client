/**
 * <LICENSE/>
 */
package com.palmyralabs.palmyra.client.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.hc.core5.http.HttpMessage;

import com.palmyralabs.palmyra.client.PalmyraClient;
import com.palmyralabs.palmyra.client.PalmyraType;
import com.palmyralabs.palmyra.client.auth.AuthClient;
import com.palmyralabs.palmyra.client.pojo.FilterCriteria;
import com.palmyralabs.palmyra.client.pojo.ResultSetImpl;

/**
 * @author ksvraja
 *
 */
public class PalmyraClientImpl<T, ID> extends BaseRestClient implements PalmyraClient<T, ID> {
	private Class<T> valueType;
	private RequestURL request;
	private String type;
	private final AuthClient authClient;

	public PalmyraClientImpl(AuthClient client, String baseURL, String context, Class<T> valueType) {
		this.type = getAnnotation(valueType);
		this.request = new RequestURL(baseURL, context, type);
		this.valueType = valueType;
		this.authClient = client;
	}

	@Override
	public T findById(ID id) throws IOException {
		String url = request.getURL(id);
		return get(url, new ItemResponseHandler<T>(url, valueType));
	}

	@Override
	public T findUnique(FilterCriteria filter) throws IOException {
		String url = request.getURL();
		return get(url, filter, new ItemResponseHandler<T>(url, valueType));
	}

	@Override
	public ResultSetImpl<T> query(FilterCriteria filter) throws IOException {
		String url = request.getURL();
		return get(url, filter, new ResultSetResponseHandler<T>(url, valueType));
	}

	@Override
	public List<T> list(FilterCriteria filter) throws IOException {
		String url = request.getURL();
		return get(url, filter, new ListResponseHandler<T>(url, valueType));
	}

	@Override
	public T save(T data) throws IOException {
		String url = request.getURL();
		return post(url, data, new ItemResponseHandler<T>(url, valueType));
	}

	@Override
	public List<T> save(List<T> objs) throws IOException {
		String url = request.getURL();
		return post(url, objs, new ListResponseHandler<T>(url, valueType));
	}

	private static String getAnnotation(Class<?> t) {
		if (t.isAnnotationPresent(PalmyraType.class)) {
			PalmyraType type = t.getAnnotation(PalmyraType.class);
			return type.value();
		} else {
			return t.getSimpleName();
		}
	}

	@Override
	public T save(T data, ID id) throws IOException {
		String url = request.getURL(id);
		return put(url, data, new ItemResponseHandler<T>(url, valueType));
	}

	@Override
	public T delete(ID id) throws IOException {
		String url = request.getURL(id);
		return delete(url, new ItemResponseHandler<T>(url, valueType));
	}

	@Override
	protected void setAuthentication(HttpMessage request) {
		Map<String, String> headers = authClient.getHeaders();
		for (Entry<String, String> entry : headers.entrySet()) {
			request.addHeader(entry.getKey(), entry.getValue());
		}
	}
}
