package com.palmyralabs.palmyra.client.json;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.palmyralabs.palmyra.client.PalmyraClientConfig;

public class ObjectMapperFactory {

	private static ObjectMapper objectMapper;

	private ObjectMapperFactory() {

	}

	private ObjectMapper create() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.findAndRegisterModules();
		PalmyraClientConfig config = PalmyraClientConfig.getInstance();

		String datePattern = config.getDatePattern();
		
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		SimpleModule dateTimeModule = new SimpleModule();
		dateTimeModule.addDeserializer(LocalDate.class,
				new LocalDateDeserializer(DateTimeFormatter.ofPattern(datePattern)));
		dateTimeModule.addSerializer(LocalDate.class,
				new LocalDateSerializer(DateTimeFormatter.ofPattern(datePattern)));

//			dateTimeModule.addDeserializer(LocalDateTime.class,
//					new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-ddThh:mm:ss")));
//			dateTimeModule.addSerializer(LocalDateTime.class,
//					new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-ddThh:mm:ss")));

		objectMapper.registerModule(dateTimeModule);
		return objectMapper;
	}

	public static ObjectMapper getMapper() {
		if (null != objectMapper)
			return objectMapper;
		else {
			objectMapper = new ObjectMapperFactory().create();
		}
		return objectMapper;
	}
}
