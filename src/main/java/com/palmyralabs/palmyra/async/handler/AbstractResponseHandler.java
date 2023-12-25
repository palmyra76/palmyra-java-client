package com.palmyralabs.palmyra.async.handler;

import com.palmyralabs.palmyra.async.ErrorHandler;
import com.palmyralabs.palmyra.async.ExceptionHandler;
import com.palmyralabs.palmyra.async.FailedRequest;
import com.palmyralabs.palmyra.async.RequestConfig;
import com.palmyralabs.palmyra.async.ResponseHandler;
import com.palmyralabs.palmyra.async.exception.DefaultErrorHandler;
import com.palmyralabs.palmyra.async.exception.NetworkExceptionHandler;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractResponseHandler<R> implements ResponseHandler<R>{

	@Override
	public ErrorHandler getErrorHandler() {
		return new DefaultErrorHandler();
	}

	@Override
	public ExceptionHandler getExceptionHandler(RequestConfig requestConfig, FailedRequest req) {
		return new NetworkExceptionHandler(requestConfig.getOperation(), req);
	}
	
	
}
