package com.palmyralabs.palmyra.async.handler;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.function.Consumer;

import com.palmyralabs.palmyra.async.ErrorHandler;
import com.palmyralabs.palmyra.async.ExceptionHandler;
import com.palmyralabs.palmyra.async.FailedRequest;
import com.palmyralabs.palmyra.async.ListResponseHandler;
import com.palmyralabs.palmyra.async.RequestConfig;
import com.palmyralabs.palmyra.async.ResponseListener;
import com.palmyralabs.palmyra.async.exception.DefaultErrorHandler;
import com.palmyralabs.palmyra.async.exception.NetworkExceptionHandler;
import com.palmyralabs.palmyra.async.impl.NoopResponseListener;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractListResponseHandler<R> implements ListResponseHandler<R>{

	private final Consumer<List<R>> consumer;	
	private ResponseListener listener = NoopResponseListener.getInstance();
		
	@Override
	public Consumer<List<R>> getHandler() {
		return consumer;
	}

	@Override
	public ErrorHandler getErrorHandler() {
		return new DefaultErrorHandler();
	}

	@Override
	public ExceptionHandler getExceptionHandler(RequestConfig requestConfig, FailedRequest req) {
		return new NetworkExceptionHandler(requestConfig.getOperation(), req);
	}

	public ResponseListener getListener() {
		return listener;
	}

	public void setListener(ResponseListener listener) {
		this.listener = listener;
	}
	
	protected Type getType(Class<?> rawClass, Class<?> parameter) {
		return new ParameterizedType() {
			@Override
			public Type[] getActualTypeArguments() {
				return new Type[] { parameter };
			}

			@Override
			public Type getRawType() {
				return rawClass;
			}

			@Override
			public Type getOwnerType() {
				return null;
			}
		};
	}
}
