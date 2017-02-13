package spork.inject.internal.objectgraph;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.annotation.Nullable;

public enum Nullability {
	NULLABLE,
	NONNULL;

	public static Nullability create(Field field) {
		if (field.getAnnotation(Nullable.class) != null) {
			return NULLABLE;
		} else { // If @Nullable is not defined, we assume the default @Nonnull
			return NONNULL;
		}
	}

	public static Nullability create(Method method) {
		if (method.getAnnotation(Nullable.class) != null) {
			return NULLABLE;
		} else { // If @Nullable is not defined, we assume the default @Nonnull
			return NONNULL;
		}
	}
}
