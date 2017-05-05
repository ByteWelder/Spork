package spork.exceptions;

public class SporkRuntimeException extends RuntimeException {

	public SporkRuntimeException(String message) {
		super(message);
	}

	public SporkRuntimeException(Exception parent) {
		super(parent.getMessage(), parent);
	}

	public SporkRuntimeException(String message, Exception parent) {
		super(message, parent);
	}
}