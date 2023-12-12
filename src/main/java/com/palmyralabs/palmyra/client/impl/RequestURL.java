package com.palmyralabs.palmyra.client.impl;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RequestURL {
	private String baseUrl;
	private String context;
	private String mapping;

	public String getURL() {
		StringBuilder sb = new StringBuilder();
		sb.append(baseUrl).append(context).append(mapping);
		return sb.toString();
	}

	public String getURL(Object id) {
		StringBuilder sb = new StringBuilder();
		sb.append(baseUrl).append(context).append(mapping).append('/').append(id);
		return sb.toString();
	}

	public String getMultiURL() {
		StringBuilder sb = new StringBuilder();
		sb.append(baseUrl).append(context).append("/multi").append(mapping);
		return sb.toString();
	}
}
