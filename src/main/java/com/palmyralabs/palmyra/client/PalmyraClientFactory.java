package com.palmyralabs.palmyra.client;

public interface PalmyraClientFactory {

	<T> PalmyraClient<T, Integer> getClient(String mapping, Class<T> valueType);

	<T> PalmyraClient<T, Integer> getClient(Class<T> valueType);

	<T, ID> PalmyraClient<T, ID> getClient(Class<T> valueType, Class<ID> IDType);

	TupleRestClient getTupleClient(String mapping);
}
