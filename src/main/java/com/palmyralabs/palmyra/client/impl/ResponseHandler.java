package com.palmyralabs.palmyra.client.impl;

import java.io.IOException;

import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpException;

class ResponseHandler<T> extends AbstractResponseHandler<T> {
	private final Class<T> valueType;
	
	public ResponseHandler(String url, Class<T> valueType) {
		super(url);
		this.valueType = valueType;
	}

	@Override
	public T handleResponse(ClassicHttpResponse response) throws HttpException, IOException {
		HttpEntity entity = processHttpCode(response);
		if (null != entity) {
			return deserializeValue(entity, valueType);
		}
		return null;
	}

}
