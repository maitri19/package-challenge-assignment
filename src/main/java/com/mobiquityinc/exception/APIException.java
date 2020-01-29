package com.mobiquityinc.exception;

/**
 * The Class APIException.
 */
public class APIException extends Exception {

	/**
	 * Instantiates a new API exception.
	 *
	 * @param message the message
	 * @param e       the e
	 */
	public APIException(String message, Exception e) {
		super(message, e);
	}

	/**
	 * Instantiates a new API exception.
	 *
	 * @param message the message
	 */
	public APIException(String message) {
		super(message);
	}
}
