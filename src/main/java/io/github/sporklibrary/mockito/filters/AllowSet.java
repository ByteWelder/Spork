package io.github.sporklibrary.mockito.filters;

import io.github.sporklibrary.mockito.MockitoFilter;

import java.util.Set;

public class AllowSet implements MockitoFilter
{
	private final Set<Class<?>> mClassSet;

	public AllowSet(Set<Class<?>> classSet)
	{
		mClassSet = classSet;
	}

	@Override
	public boolean shouldMockWithMockito(Class<?> classObject)
	{
		return mClassSet.contains(classObject);
	}
}
