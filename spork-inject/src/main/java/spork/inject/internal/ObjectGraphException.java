package spork.inject.internal;

import spork.exceptions.BindContext;
import spork.exceptions.SporkException;

/**
 * Thrown when ObjectGraph is broken or when ObjectGraph cannot be built.
 */
public class ObjectGraphException extends SporkException {
	ObjectGraphException(String s, BindContext bindContext) {
		super(s, bindContext);
	}

	ObjectGraphException(String s, Exception parent, BindContext bindContext) {
		super(s, parent, bindContext);
	}
}
