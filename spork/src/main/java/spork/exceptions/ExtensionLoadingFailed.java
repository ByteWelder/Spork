package spork.exceptions;

import spork.SporkExtension;

/**
 * Exception that is thrown when creating/initialize a {@link SporkExtension} fails.
 */
public class ExtensionLoadingFailed extends RuntimeException {
	public ExtensionLoadingFailed(String s) {
		super(s);
	}

	public ExtensionLoadingFailed(String s, Throwable throwable) {
		super(s, throwable);
	}
}
