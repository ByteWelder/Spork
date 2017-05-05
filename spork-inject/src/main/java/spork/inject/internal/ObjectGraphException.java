package spork.inject.internal;

import spork.exceptions.SporkException;

/**
 * Thrown when ObjectGraph is broken or when ObjectGraph cannot be built.
 */
public class ObjectGraphException extends SporkException {
	ObjectGraphException(String s) {
		super(s);
	}

	ObjectGraphException(String s, Exception parent) {
		super(s, parent);
	}
}
