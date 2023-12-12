/**
 * <LICENSE/>
 */
package com.palmyralabs.palmyra.client.auth;

import java.util.Date;
import java.util.HashMap;

import org.apache.commons.codec.digest.DigestUtils;

import lombok.AllArgsConstructor;

/**
 * @author ksvraja
 *
 */
@AllArgsConstructor
public class PalmyraAuthClient implements AuthClient {
	private String username;
	private String password;
	private String context;
	private String deviceId;

	@Override
	public HashMap<String, String> getHeaders() {
		String random = getUniqueRef();
		StringBuilder auth = new StringBuilder(username).append("@").append(context).append(":")
				.append(DigestUtils.md5Hex(password)).append(random);

		String authHeader = DigestUtils.md5Hex(auth.toString());

		HashMap<String, String> authMap = new HashMap<String, String>();
		authMap.put(HEADER_SECRET, authHeader);
		authMap.put(HEADER_USER, username);
		authMap.put(HEADER_RANDOM, random);
		if (null != deviceId)
			authMap.put(HEADER_DEVICE, deviceId);

		return authMap;
	}

	private String getUniqueRef() {
		return Long.toString(new Date().getTime());
	}
}
