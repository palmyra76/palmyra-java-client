/**
 * <LICENSE/>
 */
package com.palmyralabs.palmyra.client.auth;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;

import lombok.AllArgsConstructor;

/**
 * @author ksvraja
 *
 */
@AllArgsConstructor
public class BasicAuthClient implements AuthClient{

	private String username;
	private String password;
	private String deviceId;
	
	@Override
	public HashMap<String, String> getHeaders() {
		String auth = username + ":" + password;
		byte[] encodedAuth = Base64.getEncoder().encode(
		  auth.getBytes(StandardCharsets.ISO_8859_1));
		String authHeader = "Basic " + new String(encodedAuth);
		HashMap<String, String> result = new HashMap<String, String>();
		result.put(HEADER_BASIC_AUTH, authHeader);
		if(null != deviceId)
			result.put(HEADER_DEVICE, deviceId);
		return result;
	}

}
