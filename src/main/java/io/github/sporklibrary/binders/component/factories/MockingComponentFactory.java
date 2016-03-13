package io.github.sporklibrary.binders.component.factories;

import io.github.sporklibrary.annotations.Nullable;

import java.util.HashMap;

/**
 * A component factory that allows the user to specify class overrides for existing components.
 * This allows the creation of mocks/stubs for testing purposes.
 */
public class MockingComponentFactory extends DefaultComponentFactory
{
	private HashMap<Class<?>, Class<?>> mClassMap = new HashMap<>();

	/**
	 * Register a new mocking type for the provided component type.
	 * @param componentClass the component class to mock
	 * @param mockClass the mocking class
	 * @return this instance so it can be changed with other register() calls
	 */
	public MockingComponentFactory register(Class<?> componentClass, Class<?> mockClass)
	{
		mClassMap.put(componentClass, mockClass);

		return this;
	}

	@Override
	public Object create(Class<?> classObject, @Nullable Object parent)
	{
		// First get the mocking class if there is one
		Class<?> mock_class = mClassMap.get(classObject);

		return super.create(mock_class != null ? mock_class : classObject, parent);
	}
}
