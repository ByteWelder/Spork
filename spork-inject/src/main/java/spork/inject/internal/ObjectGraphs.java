package spork.inject.internal;

import javax.annotation.Nullable;

public final class ObjectGraphs {
	private ObjectGraphs() {
	}

	@Nullable
	static ObjectGraph findObjectGraph(Object[] objects) {
		for (Object object : objects) {
			if (object.getClass() == ObjectGraph.class) {
				return (ObjectGraph) object;
			}
		}

		return null;
	}
}
