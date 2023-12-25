package com.palmyralabs.palmyra.async.handler;

import java.util.List;
import java.util.function.Consumer;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.palmyralabs.palmyra.async.net.ObjectMapperFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ListConsumerHandler<R> extends AbstractResponseHandler<List<R>> {
	private final Consumer<List<R>> consumer;
	private final Class<R> type;

	public void accept(List<R> arg0) {
		this.consumer.accept(arg0);
	}

	@Override
	public JavaType getType() {
		ObjectMapper objectMapper = ObjectMapperFactory.getInstance();
		return objectMapper.getTypeFactory().constructCollectionType(List.class, type);
	}
}
