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

	/**
	 * Cast the given Object to an ObjectGraphProvider and return the ObjectGraph.
	 * If the provided Object doesn't implement ObjectGraph, an IllegalArgumentException is thrown.
	 */
	public static ObjectGraph objectGraphOf(Object object) {
		if (!(object instanceof ObjectGraphProvider)) {
			throw new IllegalArgumentException(object.getClass().getName() + " doesn't implement ObjectGraphProvider");
		}

		ObjectGraphProvider objectGraphProvider = (ObjectGraphProvider) object;

		return objectGraphProvider.getObjectGraph();
	}
}
