package io.github.sporklibrary.mockito;

public interface MockitoFilter
{
	boolean shouldMockWithMockito(Class<?> classObject);
}
