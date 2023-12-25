package com.palmyralabs.palmyra.async;

public interface ErrorHandler {
	public void handle(String operation, int responseCode, FailedResponse response);
}
