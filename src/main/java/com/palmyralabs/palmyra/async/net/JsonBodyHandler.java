package com.palmyralabs.palmyra.async.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodySubscriber;
import java.net.http.HttpResponse.ResponseInfo;
import java.util.function.Function;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.palmyralabs.palmyra.async.exception.HttpClientException;

public class JsonBodyHandler<R> implements HttpResponse.BodyHandler<R> {

	private final Class<R> wClass;

	public JsonBodyHandler(Class<R> wClass) {
		this.wClass = wClass;
	}

	@Override
	public BodySubscriber<R> apply(ResponseInfo responseInfo) {
		isValid(responseInfo);
		return asJSON(wClass);
	}

	private void isValid(ResponseInfo responseInfo) {
		int status = responseInfo.statusCode();
		if (status >= 500) {
			handlServerError(responseInfo);	
		} else if (status >= 400) {
			handleClientError(responseInfo);
		}
	}

	private void handleClientError(ResponseInfo responseInfo) {
		throw new HttpClientException("Url not found", responseInfo.statusCode());
	}

	private void handlServerError(ResponseInfo responseInfo) {

	}

	private static <W> HttpResponse.BodySubscriber<W> asJSON(Class<W> targetType) {
		HttpResponse.BodySubscriber<InputStream> upstream = HttpResponse.BodySubscribers.ofInputStream();
		return HttpResponse.BodySubscribers.mapping(upstream, new Converter<W>(targetType));
	}
}

class Converter<R> implements Function<InputStream, R> {
	private Class<R> cz;

	Converter(Class<R> clazz) {
		this.cz = clazz;
	}

	@Override
	public R apply(InputStream t) {
		try (InputStream stream = t) {
			ObjectMapper objectMapper = ObjectMapperFactory.getInstance();
			return objectMapper.readValue(stream, cz);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

}
