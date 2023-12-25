package com.palmyralabs.palmyra.async.handler;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.function.Consumer;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.palmyralabs.palmyra.async.net.ObjectMapperFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReponseConsumerHandler<R> extends AbstractResponseHandler<R>{
	private final Consumer<R> consumer;
	
	public void accept(R arg0) {
		this.consumer.accept(arg0);		
	}

	@Override	
	@SuppressWarnings("rawtypes")
	public JavaType getType() {
		Class<?> clazz = consumer.getClass();
		ParameterizedType parameterizedType = (ParameterizedType) clazz.getGenericSuperclass();

		ObjectMapper objectMapper = ObjectMapperFactory.getInstance();

		Type type = parameterizedType.getActualTypeArguments()[0];
		if (type instanceof Class)
			return objectMapper.constructType(type);

		else if (type instanceof ParameterizedType) {
			ParameterizedType paramType = (ParameterizedType) type;

			return objectMapper.getTypeFactory().constructParametricType((Class) paramType.getRawType(),
					(Class) paramType.getActualTypeArguments()[0]);
		}

		return null;
	}
}
