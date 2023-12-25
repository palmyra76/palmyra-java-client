package com.palmyralabs.palmyra.async.handler;

import java.util.concurrent.CompletionException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import com.palmyralabs.palmyra.async.ErrorHandler;
import com.palmyralabs.palmyra.async.ExceptionHandler;
import com.palmyralabs.palmyra.async.FailedRequest;
import com.palmyralabs.palmyra.async.FailedResponse;
import com.palmyralabs.palmyra.async.RequestConfig;
import com.palmyralabs.palmyra.async.ResponseHandler;
import com.palmyralabs.palmyra.async.exception.DefaultErrorHandler;
import com.palmyralabs.palmyra.async.exception.HttpClientException;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

public abstract class SyncResponseHandler<R> implements ResponseHandler<R> {

	private R data;
	private CountDownLatch latch = new CountDownLatch(1);
	private Throwable e;
	private FailedResponse fr;

	@Override
	public void accept(R t) {
		data = t;
		latch.countDown();
	}

	@Override
	public ErrorHandler getErrorHandler() {
		return new SyncErrorHandler();
	}

	@Override
	public ExceptionHandler getExceptionHandler(RequestConfig requestConfig, FailedRequest req) {
		return new SyncExceptionHandler();
	}

	public R getData() {
		return this.getData(90, TimeUnit.SECONDS);
	}

	@SneakyThrows
	public R getData(long timeout, TimeUnit unit) {
		latch.await(timeout, unit);
		if (null != e) {
			throw e;
		}
		if (null != fr) {
			throw new HttpClientException(fr.body(), fr.statusCode());
		}
		return data;
	}

	@RequiredArgsConstructor
	class SyncErrorHandler extends DefaultErrorHandler {
		@Override
		public void handle(String operation, int responseCode, FailedResponse responseInfo) {
			fr = responseInfo;
			latch.countDown();
			super.handle(operation, responseCode, responseInfo);
		}
	}

	class SyncExceptionHandler implements ExceptionHandler {

		@Override
		public void handle(Throwable t) {
			if (t instanceof CompletionException) {
				e = t.getCause();
			} else
				e = t;
			latch.countDown();
		}
	}
}