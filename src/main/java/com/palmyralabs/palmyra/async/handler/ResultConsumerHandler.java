package com.palmyralabs.palmyra.async.handler;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.function.Consumer;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.palmyralabs.palmyra.async.net.ObjectMapperFactory;
import com.palmyralabs.palmyra.client.ResultSet;
import com.palmyralabs.palmyra.client.pojo.ResultSetImpl;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ResultConsumerHandler<R> extends AbstractResponseHandler<List<R>> {
	private final Consumer<List<R>> consumer;
	private final Class<R> type;

	public void accept(List<R> arg0) {
		this.consumer.accept(arg0);
	}

	@Override
	public JavaType getType() {
		ObjectMapper objectMapper = ObjectMapperFactory.getInstance();
		return objectMapper.constructType(getType(ResultSetImpl.class, type));
	}

	protected Type getType(Class<?> rawClass, Class<?> parameter) {
		return new ParameterizedType() {
			@Override
			public Type[] getActualTypeArguments() {
				return new Type[] { parameter };
			}

			@Override
			public Type getRawType() {
				return rawClass;
			}

			@Override
			public Type getOwnerType() {
				return null;
			}
		};
	}
}
