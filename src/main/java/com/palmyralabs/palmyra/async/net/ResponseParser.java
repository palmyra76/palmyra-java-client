package com.palmyralabs.palmyra.async.net;

import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.http.HttpHeaders;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.palmyralabs.palmyra.async.FailedResponse;
import com.palmyralabs.palmyra.async.ResponseHandler;
import com.palmyralabs.palmyra.async.ResponseListener;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class ResponseParser<R> implements Consumer<HttpResponse<InputStream>> {
	private final String operation;
	private final ResponseHandler<R> handler;

	@Override
	public void accept(HttpResponse<InputStream> t) {
		int statusCode = t.statusCode();
		if (success(statusCode)) {
			onResponse();
			handler.accept(parseResponse(t));
		} else if (error(statusCode)) {
			onFailure();
			handler.getErrorHandler().handle(operation, statusCode, getResponse(t));
		}
	}

	private void onResponse() {
		try {
			ResponseListener listener = handler.getListener();
			if (null != listener)
				listener.onResponse();
		} catch (Throwable t) {

		}
	}

	private void onFailure() {
		try {
			ResponseListener listener = handler.getListener();
			if (null != listener)
				listener.onFailure();
		} catch (Throwable t) {

		}
	}

	@SneakyThrows
	private FailedResponse getResponse(HttpResponse<InputStream> t) {

		InputStream is = t.body();
		String body = new String(is.readAllBytes(), charsetFrom(t.headers()));

		return new NetResponse(t, body);
	}

	public static Charset charsetFrom(HttpHeaders headers) {
		return StandardCharsets.UTF_8;
	}

	private boolean error(int statusCode) {
		return true;
	}

	@SuppressWarnings("unchecked")
	@SneakyThrows
	private R parseResponse(HttpResponse<InputStream> t) {
		ObjectMapper objectMapper = ObjectMapperFactory.getInstance();

		return (R) objectMapper.readValue(t.body(), getType());
	}

	protected boolean success(int statusCode) {
		return statusCode == 200 || statusCode == 201;
	}

	@SuppressWarnings("rawtypes")
	public JavaType getType() {
		Class<?> clazz = handler.getClass();
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

	protected Type getType(Type rawClass, Type parameter) {
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
