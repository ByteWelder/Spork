package spork.internal;

/**
 * This is thrown when a situation occurs that should never happen.
 */
public class UnexpectedException extends RuntimeException {
	public UnexpectedException(String s) {
		super(s);
	}

	public UnexpectedException(String s, Throwable throwable) {
		super(s, throwable);
	}
}
