package com.palmyralabs.palmyra.async;

import java.lang.reflect.Type;
import java.util.concurrent.Executor;
import java.util.function.Function;

import com.palmyralabs.palmyra.async.impl.NoopResponseListener;

public interface AbstractHandler<R> {
	
	public ErrorHandler getErrorHandler();
	
	public Type getType();

	public ExceptionHandler getExceptionHandler(RequestConfig requestConfig, FailedRequest req);

	public default Executor getExecutor() {
		return HttpUtils.getAsyncExecutor();
	}
	
	/**
	 * Do not override this method.
	 * @param requestConfig
	 * @param req
	 * @return
	 */
	public default Function<Throwable, Void> getHandler(RequestConfig requestConfig, FailedRequest req){
		return new Function<Throwable, Void>() {
			
			@Override
			public Void apply(Throwable t) {
				ResponseListener listener = getListener();
				if(null != listener) {
					listener.onFailure();
				}
				getExceptionHandler(requestConfig, req).apply(t);
				return null;
			}
		};
	}
	
	public default ResponseListener getListener() {
		return NoopResponseListener.getInstance();
	}
}