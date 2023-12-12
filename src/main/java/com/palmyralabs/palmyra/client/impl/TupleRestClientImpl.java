/**
 * <LICENSE/>
 */
package com.palmyralabs.palmyra.client.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.hc.core5.http.HttpMessage;

import com.palmyralabs.palmyra.client.ResultSet;
import com.palmyralabs.palmyra.client.Tuple;
import com.palmyralabs.palmyra.client.TupleRestClient;
import com.palmyralabs.palmyra.client.auth.AuthClient;
import com.palmyralabs.palmyra.client.pojo.FilterCriteria;

/**
 * @author ksvraja
 *
 */
public class TupleRestClientImpl extends BaseRestClient implements TupleRestClient {
	private final RequestURL requestURL;
	private final AuthClient authClient;

	public TupleRestClientImpl(AuthClient authClient, String baseUrl, String context, String mapping) {
		requestURL = new RequestURL(baseUrl, context, mapping);
		this.authClient = authClient;
	}

	public TupleRestClientImpl(AuthClient authClient, RequestURL requestURL) {
		this.requestURL = requestURL;
		this.authClient = authClient;
	}

	public Tuple findById(Object id) throws IOException {
		String url = requestURL.getURL(id);
		return get(url, new TupleResponseHandler(url));
	}

	public ResultSet<Tuple> query(FilterCriteria filter) throws IOException {
		String url = requestURL.getURL();
		return get(url, filter, new ResultSetResponseHandler<Tuple>(url, Tuple.class));
	}

	public Tuple save(Tuple tuple) throws IOException {
		String url = requestURL.getURL();
		return post(url, tuple, new TupleResponseHandler(url));
	}

	public List<Tuple> save(List<Tuple> objs) throws IOException {
		String url = requestURL.getURL();
		return post(url, objs, new ListResponseHandler<Tuple>(url, Tuple.class));
	}

	@Override
	protected final void setAuthentication(HttpMessage request) {
		Map<String, String> headers = authClient.getHeaders();
		for (Entry<String, String> entry : headers.entrySet()) {
			request.addHeader(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public Tuple delete(Object id) throws IOException {
		String url = requestURL.getURL(id);
		return delete(url, new TupleResponseHandler(url));
	}

}
