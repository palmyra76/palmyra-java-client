package com.palmyralabs.palmyra.async.handler;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import com.palmyralabs.palmyra.async.FailedResponse;
import com.palmyralabs.palmyra.async.ListResponseHandler;
import com.palmyralabs.palmyra.async.exception.HttpClientException;

import lombok.SneakyThrows;

public class SyncListResponseHandler<R>  extends AbstractSyncResponseHandler<R>
		implements ListResponseHandler<R> {

	private List<R> data;	

	public SyncListResponseHandler(Class<R> type) {
		super(type);
	}

	@Override
	public Consumer<List<R>> getHandler() {
		return new Consumer<List<R>>() {
			@Override
			public void accept(List<R> t) {
				data = t;
				latchDown();
			}
		};
	}

	public List<R> getData() {
		return this.getData(90, TimeUnit.SECONDS);
	}

	@SneakyThrows
	public List<R> getData(long timeout, TimeUnit unit) {
		awaitLatch(timeout, unit);
		Throwable e = getException();
		if (null != e) {
			throw e;
		}
		FailedResponse fr = getFailedResponse();
		if (null != fr) {
			throw new HttpClientException(fr.body(), fr.statusCode());
		}
		return data;
	}
}