package com.palmyralabs.palmyra.async.net;

import java.io.InputStream;
import java.net.http.HttpHeaders;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Consumer;

import com.palmyralabs.palmyra.async.FailedResponse;
import com.palmyralabs.palmyra.async.ListResponseHandler;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class ListResponseParser<R> implements Consumer<HttpResponse<InputStream>>{
	private final String operation;
	private final ListResponseHandler<R> handler;	
	
	@Override
	public void accept(HttpResponse<InputStream> t) {
		int statusCode = t.statusCode();
		if(success(statusCode)) {
			onResponse();
			handler.getHandler().accept(parseResponse(t));
		}else if(error(statusCode)){
			onFailure();
			handler.getErrorHandler().handle(operation, statusCode, getResponse(t));
		}
	}
	
	private void onResponse() {
		try {
			handler.getListener().onResponse();			
		} catch (Throwable t) {

		}
	}

	private void onFailure() {
		try {
			handler.getListener().onFailure();
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

	private List<R> parseResponse(HttpResponse<InputStream> t) {
		return null;
//		ObjectMapper objectMapper = ObjectMapperFactory.getInstance();
//		return objectMapper.readerForListOf(handler.getType()).readValue(t.body());
	}

	protected boolean success(int statusCode) {
		return statusCode == 200 || statusCode == 201;
	}

}
