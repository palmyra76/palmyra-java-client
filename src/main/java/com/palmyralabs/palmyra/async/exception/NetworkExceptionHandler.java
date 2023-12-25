package com.palmyralabs.palmyra.async.exception;

import java.util.concurrent.CompletionException;

import com.palmyralabs.palmyra.async.ExceptionHandler;
import com.palmyralabs.palmyra.async.FailedRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class NetworkExceptionHandler implements ExceptionHandler {
	private final String operation;
	private final FailedRequest request;

	@Override
	public void handle(Throwable t) {
		if(t instanceof CompletionException) {
			t = t.getCause();
		}
		log.error("Error while connecting to {}", request.url());
		log.error(operation + " failed:", t);
	}
}