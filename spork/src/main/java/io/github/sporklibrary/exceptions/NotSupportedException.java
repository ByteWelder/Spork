package io.github.sporklibrary.exceptions;

/**
 * Exception that is thrown when a feature or operation is not supported.
 */
public class NotSupportedException extends RuntimeException {

	public NotSupportedException(String message) {
		super(message);
	}

	public NotSupportedException(String message, Exception e) {
		super(message, e);
	}
}
