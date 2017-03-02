package spork.inject.internal;

/**
 * Thrown when ObjectGraph is broken or when ObjectGraph cannot be built.
 */
class ObjectGraphException extends Exception {
	ObjectGraphException(String s) {
		super(s);
	}
}
