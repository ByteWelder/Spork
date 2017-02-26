package spork.inject.internal.objectgraph;

import javax.annotation.Nullable;

public final class ObjectGraphs {
	private ObjectGraphs() {
	}

	@Nullable
	public static ObjectGraph findObjectGraph(Object[] objects) {
		for (Object object : objects) {
			if (object.getClass() == ObjectGraph.class) {
				return (ObjectGraph) object;
			}
		}

		return null;
	}
}
