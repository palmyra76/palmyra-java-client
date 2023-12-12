package com.palmyralabs.palmyra.client.exception;

import java.util.List;

@SuppressWarnings("rawtypes")
public class MultipleRecordsFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final List result;

	public MultipleRecordsFoundException(List result) {
		this.result = result;
	}

	public List getResult() {
		return result;
	}
}
