package spork.inject;

import spork.inject.internal.ObjectGraphBuilderImpl;
import spork.inject.internal.ObjectGraphImpl;

/**
 * ObjectGraph utility class.
 */
public final class ObjectGraphs {

	private ObjectGraphs() {
	}

	public static ObjectGraphBuilder builder() {
		return new ObjectGraphBuilderImpl();
	}

	public static ObjectGraphBuilder builder(ObjectGraph objectGraph) {
		if (!(objectGraph instanceof ObjectGraphImpl)) {
			throw new IllegalArgumentException("provided ObjectGraph instance is not compatible");
		}

		return new ObjectGraphBuilderImpl((ObjectGraphImpl) objectGraph);
	}
}
