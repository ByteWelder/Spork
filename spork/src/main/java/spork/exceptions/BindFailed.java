package spork.exceptions;

/**
 * High-level Exception that is thrown when binding fails.
 * e.g. SporkInstance.bind() might throw this.
 */
public class BindFailed extends SporkException {

	public BindFailed(String message, BindContext context) {
		super(message, context);
	}

	public BindFailed(String message, Throwable cause, BindContext context) {
		super(message, cause, context);
	}
}
