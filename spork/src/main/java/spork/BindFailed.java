package spork;

/**
 * Exception that is thrown when binding fails.
 */
public class BindFailed extends RuntimeException {

	public BindFailed(String message) {
		super(message);
	}

	public BindFailed(String message, Throwable cause) {
		super(message, cause);
	}
}
