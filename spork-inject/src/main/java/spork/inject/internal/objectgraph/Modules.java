package spork.inject.internal.objectgraph;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import spork.inject.Provides;

public final class Modules {
	private Modules() {
	}

	/**
	 * Find all methods for a specific module class
	 * @param moduleClass the module class to look for methods
	 * @return a non-mutable list which contains 0 or more methods
	 */
	public static List<Method> getProvidesMethods(Class<?> moduleClass) {
		// It's safe to assume most or all methods are relevant.
		// The overhead of this will be smaller than the overhead of eager ArrayList allocation
		List<Method> methods = new ArrayList<>(moduleClass.getMethods().length);

		for (Method method : moduleClass.getMethods()) {
			if (method.getAnnotation(Provides.class) != null) {
				methods.add(method);
			}
		}

		return methods.isEmpty() ? Collections.<Method>emptyList() : methods;
	}
}
