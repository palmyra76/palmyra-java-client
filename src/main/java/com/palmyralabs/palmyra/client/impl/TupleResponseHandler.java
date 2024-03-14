package com.palmyralabs.palmyra.client.impl;

import java.io.IOException;

import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpException;

import com.palmyralabs.palmyra.client.Tuple;
import com.palmyralabs.palmyra.client.pojo.ItemResult;
import com.palmyralabs.palmyra.client.pojo.TupleImpl;

public class TupleResponseHandler extends AbstractResponseHandler<Tuple> {

	public TupleResponseHandler(String url) {
		super(url);
	}

	@Override
	public Tuple handleResponse(ClassicHttpResponse response) throws HttpException, IOException {
		HttpEntity entity = processHttpCode(response);
		if (null != entity) {
			ItemResult<TupleImpl> r = deserializeItem(entity, TupleImpl.class);
			return r.getResult();
		}
		return null;
	}

}
