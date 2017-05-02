package spork.exceptions;

public class SporkException extends Exception {
	private final BindContext context;

	public SporkException(String s, BindContext context) {
		super(s + context.toString());
		this.context = context;
	}

	public SporkException(String s, Throwable throwable, BindContext context) {
		super(s + context.toString(), throwable);
		this.context = context;
	}

	public BindContext getBindContext() {
		return context;
	}
}
