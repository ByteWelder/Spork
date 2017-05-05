package spork.exceptions;

public class SporkException extends Exception {

	public SporkException(String s) {
		super(s);
	}

	public SporkException(String s, Throwable throwable) {
		super(s, throwable);
	}
}
