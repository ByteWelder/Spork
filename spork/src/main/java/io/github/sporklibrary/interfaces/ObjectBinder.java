package io.github.sporklibrary.interfaces;

/**
 * Used by the caching mechanism to bind one or more annotations for a specified object.
 */
public interface ObjectBinder {
    /**
     * Bind one or more annotations for the specified object
     *
     * @param object the object to bind annotations for
     */
    void bind(Object object);
}
