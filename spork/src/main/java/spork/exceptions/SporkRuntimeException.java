package spork.exceptions;

public class SporkRuntimeException extends RuntimeException {
	/**
	 * Constructor to wrap a regular {@link Exception}.
	 */
	public SporkRuntimeException(Exception parent) {
		super(parent);
	}

	public SporkRuntimeException(String s, BindContext context) {
		super(s + context.toString());
	}

	public SporkRuntimeException(String s, Throwable throwable, BindContext context) {
		super(s + context.toString(), throwable);
	}
}