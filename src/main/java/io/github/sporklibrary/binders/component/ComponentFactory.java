package io.github.sporklibrary.binders.component;

import io.github.sporklibrary.annotations.Nullable;

public interface ComponentFactory
{
	Object create(Class<?> classObject, @Nullable Object parent);
}
