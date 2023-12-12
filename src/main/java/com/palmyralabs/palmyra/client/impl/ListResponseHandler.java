package com.palmyralabs.palmyra.client.impl;

import java.io.IOException;
import java.util.List;

import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpException;

public class ListResponseHandler<T> extends AbstractResponseHandler<List<T>> {
	private final Class<T> valueType;
	
	public ListResponseHandler(String url, Class<T> valueType) {
		super(url);
		this.valueType = valueType;
	}

	@Override
	public List<T> handleResponse(ClassicHttpResponse response) throws HttpException, IOException {
		HttpEntity entity = processHttpCode(response);
		if (null != entity) {
			return deserializeList(entity, valueType);
		}
		return null;
	}

}
