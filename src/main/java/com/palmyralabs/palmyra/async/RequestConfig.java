package com.palmyralabs.palmyra.async;

import java.util.Collections;
import java.util.Map;

import lombok.RequiredArgsConstructor;

public interface RequestConfig {
	public static final Map<String, String> emptyMap = Collections.unmodifiableMap(Collections.emptyMap());
	
	String getOperation();
	
	default Map<String, String> getParams() {
		return emptyMap;
	}
	
	default Map<String, String> getHeaders() {
		return emptyMap;
	}
	
	static RequestConfig of(String operation) {
		return new SimpleRequestConfig(operation);
	}
}

@RequiredArgsConstructor
final class SimpleRequestConfig implements RequestConfig{
	private final String operation;
	
	@Override
	public String getOperation() {		
		return operation;
	}
	
}
