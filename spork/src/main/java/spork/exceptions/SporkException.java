package spork.exceptions;

public class SporkException extends Exception {

	public SporkException(String s) {
		super(s);
	}

	public SporkException(String s, BindContext context) {
		super(s + context.toString());
	}

	public SporkException(String s, Throwable throwable, BindContext context) {
		super(s + context.toString(), throwable);
	}
}
