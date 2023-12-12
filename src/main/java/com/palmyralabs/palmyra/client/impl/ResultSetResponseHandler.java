package com.palmyralabs.palmyra.client.impl;

import java.io.IOException;

import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpException;

import com.palmyralabs.palmyra.client.pojo.ResultSetImpl;

public class ResultSetResponseHandler<T> extends AbstractResponseHandler<ResultSetImpl<T>> {
	private final Class<T> valueType;
	
	public ResultSetResponseHandler(String url, Class<T> valueType) {
		super(url);
		this.valueType = valueType;
	}

	@Override
	public ResultSetImpl<T> handleResponse(ClassicHttpResponse response) throws HttpException, IOException {
		HttpEntity entity = processHttpCode(response);
		if (null != entity) {
			return deserializeResult(entity, valueType);
		}
		return null;
	}

}
