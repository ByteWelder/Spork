package io.github.sporklibrary.mockito;

/**
 * A filter that decides which (component) classes to mock.
 */
public interface MockitoFilter {
	boolean shouldMockWithMockito(Class<?> classObject);
}
