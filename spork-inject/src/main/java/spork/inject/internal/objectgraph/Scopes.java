package spork.inject.internal.objectgraph;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.annotation.Nullable;
import javax.inject.Scope;
import javax.inject.Singleton;

public final class Scopes {
	private Scopes() {
	}

	@Nullable
	public static Annotation findScopeAnnotation(Field field) {
		return findScopeAnnotation(field.getAnnotations());

	}

	@Nullable
	public static Annotation findScopeAnnotation(Method method) {
		return findScopeAnnotation(method.getAnnotations());
	}

	@Nullable
	public static Annotation findScopeAnnotation(Annotation[] annotations) {
		for (Annotation annotation : annotations) {
			if (annotation.getClass().getAnnotation(Scope.class) != null) {
				return annotation;
			}
		}

		return null;
	}
}
