package com.palmyralabs.palmyra.client.json;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

public class ObjectMapperFactory {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	static {
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		SimpleModule dateTimeModule = new SimpleModule();
		dateTimeModule.addDeserializer(LocalDate.class,
				new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		dateTimeModule.addSerializer(LocalDate.class,
				new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

//		dateTimeModule.addDeserializer(LocalDateTime.class,
//				new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-ddThh:mm:ss")));
//		dateTimeModule.addSerializer(LocalDateTime.class,
//				new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-ddThh:mm:ss")));

		objectMapper.registerModule(dateTimeModule);
	}

	private ObjectMapperFactory() {

	}

	public static ObjectMapper getMapper() {
		return objectMapper;
	}
}
