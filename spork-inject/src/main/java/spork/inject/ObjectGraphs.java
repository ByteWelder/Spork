package spork.inject;

import spork.inject.internal.ObjectGraphBuilderImpl;
import spork.inject.internal.ObjectGraphImpl;

/**
 * ObjectGraph utility class.
 */
public final class ObjectGraphs {

	private ObjectGraphs() {
	}

	/**
	 * Builder for {@link ObjectGraph}.
	 *
	 * @return an implementation of {@link ObjectGraphBuilder} to help create an {@link ObjectGraph}
	 */
	public static ObjectGraphBuilder builder() {
		return new ObjectGraphBuilderImpl();
	}

	/**
	 * Builder for {@link ObjectGraph} that is linked to the specified parent {@link ObjectGraph}.
	 *
	 * @return an implementation of {@link ObjectGraphBuilder} to help create an {@link ObjectGraph}
	 */
	public static ObjectGraphBuilder builder(ObjectGraph parentGraph) {
		if (!(parentGraph instanceof ObjectGraphImpl)) {
			throw new IllegalArgumentException("Provided ObjectGraph instance is not compatible");
		}

		return new ObjectGraphBuilderImpl((ObjectGraphImpl) parentGraph);
	}

	/**
	 * Cast the given {@link Object} to an {@link ObjectGraphProvider} and return the {@link ObjectGraph}.
	 * If the provided Object doesn't implement ObjectGraph, an IllegalArgumentException is thrown.
	 *
	 * This is a helper object for when one may assume that a given object is implementing ObjectGraphProvider.
	 *
	 * @return the ObjectGraph instance
	 */
	public static ObjectGraph objectGraphFrom(Object objectGraphProvider) {
		if (!(objectGraphProvider instanceof ObjectGraphProvider)) {
			throw new IllegalArgumentException(objectGraphProvider.getClass().getName() + " doesn't implement ObjectGraphProvider");
		}

		return ((ObjectGraphProvider) objectGraphProvider).getObjectGraph();
	}
}
