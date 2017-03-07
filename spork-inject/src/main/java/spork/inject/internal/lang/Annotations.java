package spork.inject.internal.lang;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.annotation.Nullable;

public final class Annotations {
	private Annotations() {
	}

	@Nullable
	public static Annotation findAnnotationAnnotatedWith(Class<? extends Annotation> annotationClass, Field field) {
		return findAnnotationAnnotatedWith(annotationClass, field.getAnnotations());
	}

	@Nullable
	public static Annotation findAnnotationAnnotatedWith(Class<? extends Annotation> annotationClass, Method method) {
		return findAnnotationAnnotatedWith(annotationClass, method.getAnnotations());
	}

	@Nullable
	@SuppressWarnings("PMD.UseVarargs")
	public static Annotation findAnnotationAnnotatedWith(Class<? extends Annotation> annotationClass, Annotation[] annotations) {
		for (Annotation annotation : annotations) {
			if (annotation.annotationType().isAnnotationPresent(annotationClass)) {
				return annotation;
			}
		}

		return null;
	}
}
