package com.palmyralabs.palmyra.client;

import lombok.Getter;
import lombok.Setter;

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
		return instance;
	}
}
