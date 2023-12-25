package com.palmyralabs.palmyra.async;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class HttpUtils {
	private static Executor asyncExecutor = Executors.newFixedThreadPool(1);
	
	static Executor getAsyncExecutor() {		
		return asyncExecutor;
	}

}
