package io.github.sporklibrary.mockito;

import io.github.sporklibrary.binders.component.ComponentInstanceManager;
import io.github.sporklibrary.mockito.filters.AllowAll;
import io.github.sporklibrary.mockito.filters.AllowSet;

import java.util.Arrays;
import java.util.HashSet;

public final class SporkMockito
{
	/**
	 * Registers the MockitoComponentFactory that will mock all Spork components.
	 */
	public static void initialize()
	{
		MockitoFilter filter = new AllowAll();

		initialize(filter);
	}

	/**
	 * Registers the MockitoComponentFactory that will mock all Spork components.
	 * @param classes the classes to mock
	 */
	public static void initialize(Class<?>... classes)
	{
		HashSet<Class<?>> class_set = new HashSet<>(Arrays.asList(classes));
		MockitoFilter filter = new AllowSet(class_set);

		initialize(filter);
	}

	/**
	 * Register the MockitoComponentFactory with a specified class filter.
	 * @param filter the class filter
	 */
	public static void initialize(MockitoFilter filter)
	{
		MockitoComponentFactory mocking_factory = new MockitoComponentFactory(filter);
		ComponentInstanceManager.setComponentFactory(mocking_factory);
	}
}
