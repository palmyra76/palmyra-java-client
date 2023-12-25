package com.palmyralabs.palmyra.async.handler;

import java.util.concurrent.CompletionException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import com.palmyralabs.palmyra.async.ErrorHandler;
import com.palmyralabs.palmyra.async.ExceptionHandler;
import com.palmyralabs.palmyra.async.FailedRequest;
import com.palmyralabs.palmyra.async.FailedResponse;
import com.palmyralabs.palmyra.async.RequestConfig;
import com.palmyralabs.palmyra.async.exception.DefaultErrorHandler;

public abstract class AbstractSyncResponseHandler<R> {

	private Class<R> type;
	private CountDownLatch latch = new CountDownLatch(1);
	private Throwable e;
	private FailedResponse fr;

	public AbstractSyncResponseHandler(Class<R> type) {
		this.type = type;
	}

	public final Class<R> getType() {
		return type;
	}

	public final ErrorHandler getErrorHandler() {
		return new SyncErrorHandler();
	}

	public final ExceptionHandler getExceptionHandler(RequestConfig requestConfig, FailedRequest req) {
		return new SyncExceptionHandler();
	}

	protected final Throwable getException() {
		return e;
	}

	protected final FailedResponse getFailedResponse() {
		return fr;
	}
	
	protected final void latchDown() {
		latch.countDown();		
	}
	
	protected final void awaitLatch(long timeout, TimeUnit unit) {
		try {
			latch.await(timeout, unit);
		} catch (InterruptedException e) {
		
		}
	}


	class SyncErrorHandler extends DefaultErrorHandler {
		@Override
		public void handle(String operation, int responseCode, FailedResponse responseInfo) {			
			fr = responseInfo;
			latchDown();
			super.handle(operation, responseCode, responseInfo);
		}
	}

	class SyncExceptionHandler implements ExceptionHandler {
		@Override
		public void handle(Throwable t) {
			if(t instanceof CompletionException) {
				e = t.getCause();
			}
			else
				e = t;
			latchDown();
		}
	}
}