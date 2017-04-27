package spork.android.example;

import spork.inject.ObjectGraph;

/**
 * Utility class for ObjectGraph.
 */
public final class ObjectGraphs {

	private ObjectGraphs() {
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
