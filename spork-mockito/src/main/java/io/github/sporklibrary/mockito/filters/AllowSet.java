package io.github.sporklibrary.mockito.filters;

import java.util.Set;

import io.github.sporklibrary.mockito.MockitoFilter;

/**
 * A filter used to mock specified (component) classes.
 */
public class AllowSet implements MockitoFilter {
	private final Set<Class<?>> classSet;

	public AllowSet(Set<Class<?>> classSet) {
		this.classSet = classSet;
	}

	@Override
	public boolean shouldMockWithMockito(Class<?> classObject) {
		return classSet.contains(classObject);
	}
}
