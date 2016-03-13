package io.github.sporklibrary.mockito;

import io.github.sporklibrary.binders.component.factories.DefaultComponentFactory;
import org.mockito.Mockito;

public class MockitoComponentFactory extends DefaultComponentFactory
{
	private final MockitoFilter mMockitoFilter;

	public MockitoComponentFactory(MockitoFilter filter)
	{
		mMockitoFilter = filter;
	}

	@Override
	public Object create(Class<?> classObject, Object parent)
	{
		if (mMockitoFilter.shouldMockWithMockito(classObject))
		{
			return Mockito.mock(classObject);
		}
		else // create regularly
		{
			return super.create(classObject, parent);
		}
	}
}
