package io.github.sporklibrary.mockito;

import io.github.sporklibrary.binders.component.ComponentInstanceManager;

public final class SporkMockito
{
	/**
	 * Registers the MockitoComponentFactory that will mock all Spork components.
	 */
	public static void register()
	{
		MockitoComponentFactory mocking_factory = new MockitoComponentFactory();
		ComponentInstanceManager.setComponentFactory(mocking_factory);
	}
}
