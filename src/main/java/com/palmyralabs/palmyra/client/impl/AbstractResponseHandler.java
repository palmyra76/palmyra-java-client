package com.palmyralabs.palmyra.client.impl;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.palmyralabs.palmyra.client.exception.ApplicationException;
import com.palmyralabs.palmyra.client.exception.BadRequestException;
import com.palmyralabs.palmyra.client.exception.ClientException;
import com.palmyralabs.palmyra.client.exception.ForbiddenException;
import com.palmyralabs.palmyra.client.exception.ServerErrorException;
import com.palmyralabs.palmyra.client.exception.UnAuthorizedException;
import com.palmyralabs.palmyra.client.json.ObjectMapperFactory;
import com.palmyralabs.palmyra.client.pojo.ResultSetImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractResponseHandler<D> implements HttpClientResponseHandler<D> {
	private final String url;
	protected final ObjectMapper objectMapper = ObjectMapperFactory.getMapper();

	@SuppressWarnings("unchecked")
	protected HttpEntity processHttpCode(ClassicHttpResponse response) throws IOException {		
		int code = response.getCode();
		if (log.isTraceEnabled())
			log.trace("Server Status Code : {}", code);

		HttpEntity entity = response.getEntity();
		if (code == HttpStatus.SC_OK)
			return entity;

		switch (code) {
		case HttpStatus.SC_UNAUTHORIZED: {
			log.info("Un Authorized error message from server");
			Map<String, Object> val = null;
			String message = toString(entity);
			
			try {
				val = deserialize(message, HashMap.class);
			} catch (Throwable e) {
				message = "Unauthorized";
			}
			throw new UnAuthorizedException(val, message);
		}
		case HttpStatus.SC_FORBIDDEN: {
			log.info("Forbidden error message from server");
			Map<String, Object> val = null;
			String message = toString(entity);
			try {
				val = deserialize(message, HashMap.class);
			} catch (Throwable e) {
				message="forbidden";
			}
			throw new ForbiddenException(val, message);
		}
		case HttpStatus.SC_INTERNAL_SERVER_ERROR: {
			log.info("Internal Server error message from server");
			String message = toString(entity);
			
			if (null == message || message.length() == 0) {
				message = response.getReasonPhrase();
			}
			throw new ServerErrorException(message);
		}
		case HttpStatus.SC_GATEWAY_TIMEOUT: {
			log.info("Gateway timeout -- {}", url);
			EntityUtils.consume(entity);
			throw new ClientException("Gateway timedout while accessing the url ");
		}
		case HttpStatus.SC_NOT_FOUND: {
			EntityUtils.consume(entity);
			log.info("Requested URL not found message from server url -- {}", url);
			throw new ClientException("Requested URL not found from the server -- " + url);
		}
		case HttpStatus.SC_BAD_REQUEST: {
			Map<String, Object> val = null;
			String message = toString(entity);
			try {
				val = deserialize(message, HashMap.class);
			} catch (Throwable e) {
			}
			throw new BadRequestException(val, message);
		}
		case HttpStatus.SC_NO_CONTENT: {
			EntityUtils.consume(entity);
			log.trace("Empty response received for the request");
			return null;
		}
		default: {
			Map<String, Object> val = null;
			String message = toString(entity);
			try {
				val = deserialize(message, HashMap.class);
			} catch (Throwable e) {
			}
			throw new ApplicationException(code, message, val);
		}
		}
	}

	private String toString(HttpEntity entity) {
		try {
			return EntityUtils.toString(entity);
		} catch (ParseException | IOException e) {
			log.error("Parse Error", e);
		}
		return null;
	}

	protected final <T> T deserialize(String content, Class<T> valueType) throws IOException {
		return objectMapper.readValue(content, valueType);
	}
	
	protected final <T> T deserializeValue(HttpEntity entity, Class<T> valueType) throws IOException {
		return objectMapper.readValue(entity.getContent(), valueType);
	}

	protected final <T> T deserialize(HttpEntity entity, TypeReference<T> valueTypeRef) throws IOException {
		return objectMapper.readValue(entity.getContent(), valueTypeRef);
	}

	protected final <T> ArrayList<T> deserializeList(HttpEntity entity, Class<T> valueType) throws IOException {
		return objectMapper.readValue(entity.getContent(),
				objectMapper.getTypeFactory().constructCollectionLikeType(ArrayList.class, valueType));
	}

	protected final <T> ResultSetImpl<T> deserializeResult(HttpEntity entity, Class<T> valueType) throws IOException {
		return objectMapper.readValue(entity.getContent(),
				objectMapper.constructType(getType(ResultSetImpl.class, valueType)));
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
