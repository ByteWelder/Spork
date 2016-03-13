package io.github.sporklibrary.mockito;

import io.github.sporklibrary.binders.component.ComponentFactory;
import org.mockito.Mockito;

public class MockitoComponentFactory implements ComponentFactory
{
	@Override
	public Object create(Class<?> classObject, Object parent)
	{
		return Mockito.mock(classObject);
	}
}
