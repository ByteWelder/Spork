package spork.inject.internal.reflection;

import javax.annotation.Nullable;

public final class Classes {

	private Classes() {
	}

	@Nullable
	@SuppressWarnings("unchecked")
	public static <T> T findFirstInstanceOfType(Class<T> type, Object... objects) {
		for (Object object : objects) {
			if (object.getClass() == type) {
				return (T) object;
			}
		}

		return null;
	}
}
