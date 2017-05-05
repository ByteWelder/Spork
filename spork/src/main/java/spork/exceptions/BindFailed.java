package spork.exceptions;

/**
 * High-level Exception that is thrown when binding fails.
 * e.g. SporkInstance.bind() might throw this.
 */
public class BindFailed extends SporkException {

	public BindFailed(String message) {
		super(message);
	}

	public BindFailed(String message, Throwable cause) {
		super(message, cause);
	}
}
