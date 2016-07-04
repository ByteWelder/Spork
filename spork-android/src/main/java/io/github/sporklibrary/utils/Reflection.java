package io.github.sporklibrary.utils;

import io.github.sporklibrary.annotations.Nullable;

public final class Reflection {

	private Reflection() {
	}

	@SuppressWarnings("unchecked")
	public static @Nullable <T> T tryCast(Class<T> objectClass, Object object) {
		if (!objectClass.isAssignableFrom(object.getClass())) {
			return null;
		}

		return (T)object;
	}
}
