package io.github.sporklibrary.internal.interfaces;

import io.github.sporklibrary.annotations.Nullable;

/**
 * Used by the caching mechanism to bind one or more annotations for a specified object.
 */
public interface ObjectBinder {
    /**
     * Bind one or more annotations for the specified object
     *
     * @param object the object to bind annotations for
	 * @param modules either null or an array of non-null modules
     */
    void bind(Object object, @Nullable Object[] modules);
}
