package com.palmyralabs.palmyra.client;

import java.io.InputStream;
import java.util.Properties;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

@Getter
@Setter
public class PalmyraClientConfig {
	private String datePattern = "dd-MM-yyyy";
	private String timePattern = "HH-mm-ss";
	private String dateTimePattern = "dd-MM-yyyy-HH-mm-ss";

	private static final PalmyraClientConfig instance = new PalmyraClientConfig();
	
	private PalmyraClientConfig() {
		
	}
	
	public static final PalmyraClientConfig getInstance() {
		Properties props = getMapping();
		if(null != props) {
			PalmyraClientConfig cfg = new PalmyraClientConfig();
			cfg.datePattern = props.getProperty("date-pattern", "dd-MM-yyyy");
			return cfg;
		}
		
		return instance;
	}
	
	private static Properties getMapping() {
		String fileName = "palmyra-client.properties";
		InputStream inStream = PalmyraClientConfig.class.getClassLoader().getResourceAsStream(fileName);
		if (null == inStream) {
			return null;
		}
		return getMapping(inStream);
	}

	@SneakyThrows
	private static Properties getMapping(InputStream is) {
		Properties props = new Properties();
		props.load(is);
		return props;
	}

}
