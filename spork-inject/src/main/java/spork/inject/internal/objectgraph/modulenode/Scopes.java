package spork.inject.internal.objectgraph.modulenode;

import java.lang.annotation.Annotation;

public final class Scopes {
	private Scopes() {
	}

	public static String getName(Annotation scopeAnnotation) {
		return scopeAnnotation.annotationType().getName();
	}
}
