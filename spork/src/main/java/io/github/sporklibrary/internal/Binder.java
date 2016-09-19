package io.github.sporklibrary.internal;

/**
 * Used by the caching mechanism to bind one or more annotations for a specified object.
 */
public interface Binder {
	/**
	 * Bind all annotations for an object instance on all levels of inheritance.
	 *
	 * @param object the instance to bind annotations for
	 */
	void bind(Object object);

	/**
	 * Bind all annotations for an object instance on all levels of inheritance.
	 *
	 * @param object the instance to bind annotations for
	 * @param modules an array of non-null modules
	 */
	void bind(Object object, Object... modules);
}