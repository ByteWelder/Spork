package io.github.sporklibrary.mockito;

import java.util.Arrays;
import java.util.HashSet;

import io.github.sporklibrary.binders.component.ComponentInstanceManager;
import io.github.sporklibrary.mockito.filters.AllowAll;
import io.github.sporklibrary.mockito.filters.AllowSet;

/**
 * The main entry point for Spork Mockito functionality.
 */
public final class SporkMockito {

	private SporkMockito() {
	}

	/**
	 * Registers the MockitoComponentFactory that will mock all Spork components.
	 */
	public static void initialize() {
		MockitoFilter filter = new AllowAll();
		initialize(filter);
	}

	/**
	 * Registers the MockitoComponentFactory that will mock all Spork components.
	 *
	 * @param classes the classes to mock
	 */
	public static void initialize(Class<?>... classes) {
		HashSet<Class<?>> classSet = new HashSet<>(Arrays.asList(classes));
		MockitoFilter filter = new AllowSet(classSet);

		initialize(filter);
	}

	/**
	 * Register the MockitoComponentFactory with a specified class filter.
	 *
	 * @param filter the class filter
	 */
	public static void initialize(MockitoFilter filter) {
		MockitoComponentFactory mockingComponentFactory = new MockitoComponentFactory(filter);
		ComponentInstanceManager.setComponentFactory(mockingComponentFactory);
	}
}
