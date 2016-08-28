package io.github.sporklibrary.mockito;

import org.mockito.Mockito;

import io.github.sporklibrary.binders.component.factories.DefaultComponentFactory;

/**
 * Creates mocked instances for the provided MockitoFilter or otherwise
 * creates the normal instance as defined by @BindComponent
 */
public class MockitoComponentFactory extends DefaultComponentFactory {
	private final MockitoFilter mockitoFilter;

	public MockitoComponentFactory(MockitoFilter filter) {
		mockitoFilter = filter;
	}

	@Override
	public Object create(Class<?> classObject, Object parent) {
		if (mockitoFilter.shouldMockWithMockito(classObject)) {
			return Mockito.mock(classObject);
		} else { // create regularly
			return super.create(classObject, parent);
		}
	}
}
