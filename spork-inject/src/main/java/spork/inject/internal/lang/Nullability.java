package spork.inject.internal.lang;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.annotation.Nullable;

public enum Nullability {
	NULLABLE,
	NONNULL;

	public static Nullability create(Field field) {
		if (field.isAnnotationPresent(Nullable.class)) {
			return NULLABLE;
		} else { // If @Nullable is not defined, we assume the default @Nonnull
			return NONNULL;
		}
	}

	public static Nullability create(Method method) {
		if (method.isAnnotationPresent(Nullable.class)) {
			return NULLABLE;
		} else { // If @Nullable is not defined, we assume the default @Nonnull
			return NONNULL;
		}
	}

	@SuppressWarnings("PMD.UseVarargs")
	public static Nullability create(Annotation[] annotations) {
		for (Annotation annotation : annotations) {
			if (annotation.annotationType() == Nullable.class) {
				return NULLABLE;
			}
		}

		return NONNULL;
	}
}
