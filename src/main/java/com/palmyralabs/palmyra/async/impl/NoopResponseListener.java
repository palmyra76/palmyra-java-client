package com.palmyralabs.palmyra.async.impl;

import com.palmyralabs.palmyra.async.ResponseListener;

public final class NoopResponseListener implements ResponseListener{

	private static final ResponseListener instance = new NoopResponseListener();
	
	public static final ResponseListener getInstance() {
		return instance;
	}
	
	@Override
	public void onResponse() {
		
	}

	@Override
	public void onFailure() {
		
	}

}
