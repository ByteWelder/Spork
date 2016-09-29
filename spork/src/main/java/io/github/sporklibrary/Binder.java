package io.github.sporklibrary;

import io.github.sporklibrary.annotations.Nullable;

/**
 * Used by the caching mechanism to bind one or more annotations for a specified object.
 */
public interface Binder {
	/**
	 * Bind all annotations for an object instance on all levels of inheritance.
	 *
	 * @param object the instance to bind annotations for
	 * @param modules either null or an array of non-null modules
	 */
	void bind(Object object, @Nullable Object... modules);
}