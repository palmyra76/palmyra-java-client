package com.palmyralabs.palmyra.async.exception;

import com.palmyralabs.palmyra.async.ErrorHandler;
import com.palmyralabs.palmyra.async.FailedResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultErrorHandler implements ErrorHandler {

	@Override
	public void handle(String operation, int responseCode, FailedResponse responseInfo) {
		if (responseCode >= 500) {
			handlServerError(operation, responseInfo);
		} else if (responseCode >= 400) {
			handleClientError(operation, responseInfo);
		} else if (responseCode >= 300) {
			log.error("error:{} code received from {}, responsebody: {}", responseInfo.statusCode(), responseInfo.url(),
					responseInfo.body());
		}
	}

	protected void handleClientError(String operation, FailedResponse responseInfo) {
		log.error("{}--client error:{} code received from {}, responsebody: {}", operation, responseInfo.statusCode(),
				responseInfo.url(), responseInfo.body());
	}

	protected void handlServerError(String operation, FailedResponse responseInfo) {
		log.error("{}--server error:{} code received from {}, responsebody: {}", operation, responseInfo.statusCode(),
				responseInfo.url(), responseInfo.body());
	}
}