package spork.inject.internal.objectgraph;

/**
 * Thrown when ObjectGraph is broken or when ObjectGraph cannot be built.
 */
public class ObjectGraphException extends Exception {
	public ObjectGraphException(String s) {
		super(s);
	}
}
