package com.palmyralabs.palmyra.client.impl;

import com.palmyralabs.palmyra.client.PalmyraClient;
import com.palmyralabs.palmyra.client.PalmyraClientFactory;
import com.palmyralabs.palmyra.client.TupleRestClient;
import com.palmyralabs.palmyra.client.auth.AuthClient;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@RequiredArgsConstructor
public class PalmyraClientFactoryImpl implements PalmyraClientFactory{
	private @NonNull final String baseURL;
	private @NonNull final String context;
	private AuthClient authClient = AuthClient.NoopAuthClient;

	public <T> PalmyraClient<T, Integer> getClient(String mapping, Class<T> valueType) {
		return new PalmyraClientImpl<T, Integer>(authClient, baseURL, context, valueType);
	}

	public <T> PalmyraClient<T, Integer> getClient(Class<T> valueType) {
		return new PalmyraClientImpl<T, Integer>(authClient, baseURL, context, valueType);
	}

	public <T, ID> PalmyraClient<T, ID> getClient(Class<T> valueType, Class<ID> IDType) {
		return new PalmyraClientImpl<T, ID>(authClient, baseURL, context, valueType);
	}

	public TupleRestClient getTupleClient(String mapping) {
		return new TupleRestClientImpl(authClient, baseURL, context, mapping);
	}
}
