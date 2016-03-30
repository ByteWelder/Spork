package io.github.sporklibrary.binders.component;

import io.github.sporklibrary.annotations.Nullable;

/**
 * A component factory creates component instances.
 */
public interface ComponentFactory {
    /**
     * Create a component instance.
     *
     * @param classObject the component type to create an instance for
     * @param parent      the parent that the component belongs to
     * @return the created component instance
     */
    Object create(Class<?> classObject, @Nullable Object parent);
}
