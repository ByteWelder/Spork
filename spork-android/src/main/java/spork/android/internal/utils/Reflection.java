package spork.android.internal.utils;

import javax.annotation.Nullable;

public final class Reflection {

	private Reflection() {
	}

	@SuppressWarnings("unchecked")
	@Nullable
	public static <T> T tryCast(Class<T> objectClass, Object object) {
		if (!objectClass.isAssignableFrom(object.getClass())) {
			return null;
		}

		return (T) object;
	}
}
