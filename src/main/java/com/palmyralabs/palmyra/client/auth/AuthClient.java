/**
 * <LICENSE/>
 */
package com.palmyralabs.palmyra.client.auth;

import java.util.Collections;
import java.util.Map;

/**
 * @author ksvraja
 *
 */
public interface AuthClient {
	public static final String HEADER_USER = "X-Palmyra-user";
	public static final String HEADER_DEVICE = "X-Palmyra-device";
	public static final String HEADER_TOKEN = "X-Palmyra-token";
	public static final String HEADER_RANDOM = "X-Palmyra-random";
	public static final String HEADER_SECRET = "X-Palmyra-Authorization";
	public static final String HEADER_SYSTEM = "X-Palmyra-system";
	public static final String HEADER_BASIC_AUTH = "Authorization";
	
	public Map<String, String> getHeaders();
	
	public static final AuthClient NoopAuthClient = new AuthClient() {
		@Override
		public Map<String, String> getHeaders() {
			return Collections.emptyMap();
		}
	};
}
