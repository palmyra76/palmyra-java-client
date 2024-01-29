/**
 * <LICENSE/>
 */
package com.palmyralabs.palmyra.client.exception;

import java.util.Map;

/**
 * @author ksvraja
 *
 */
public class ForbiddenException extends ApplicationException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5905001874835886596L;

	public ForbiddenException(final String message) {
		super(401, message, null);
	}
	
	public ForbiddenException(Map<String, Object> response) {
		super(401, null, response);
	}
	
	public ForbiddenException(Map<String, Object> response, String message) {
		super(401, message, response);
	}
	
	public ForbiddenException(final String s, final Throwable t) {
		super(401, s, null, t);
	}
	
	public ForbiddenException(Map<String, Object> response, final Throwable t) {
		super(401, null, response, t);
	}
}
