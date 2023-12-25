package com.palmyralabs.palmyra.async;

public interface FailedResponse {
	public int statusCode();

	public String body();

	public String url();
}
