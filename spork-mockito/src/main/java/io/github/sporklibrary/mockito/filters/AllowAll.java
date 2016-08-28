package io.github.sporklibrary.mockito.filters;

import io.github.sporklibrary.mockito.MockitoFilter;

/**
 * A filter used to mock all (component) classes.
 */
public class AllowAll implements MockitoFilter {

	@Override
	public boolean shouldMockWithMockito(Class<?> classObject) {
		return true;
	}
}
