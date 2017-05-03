package spork.exceptions;

public class SporkRuntimeException extends RuntimeException {
	
	public SporkRuntimeException(Exception parent) {
		super(parent.getMessage(), parent);
	}

	public SporkRuntimeException(String s, Exception parent) {
		super(s, parent);
	}

	public SporkRuntimeException(String s, BindContext context) {
		super(s + context.toString());
	}

	public SporkRuntimeException(String s, Throwable throwable, BindContext context) {
		super(s + context.toString(), throwable);
	}
}