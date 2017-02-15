package spork.inject.internal.objectgraph;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.annotation.Nullable;

public final class Annotations {
	private Annotations() {
	}

	@Nullable
	public static <T extends Annotation> T findAnnotationAnnotatedWith(Class<T> annotationClass, Field field) {
		return findAnnotationAnnotatedWith(annotationClass, field.getAnnotations());

	}

	@Nullable
	public static <T extends Annotation> T findAnnotationAnnotatedWith(Class<T> annotationClass, Method method) {
		return findAnnotationAnnotatedWith(annotationClass, method.getAnnotations());
	}

	@SuppressWarnings("unchecked")
	@Nullable
	public static <T extends Annotation> T findAnnotationAnnotatedWith(Class<T> annotationClass, Annotation[] annotations) {
		for (Annotation annotation : annotations) {
			if (annotation.annotationType().getAnnotation(annotationClass) != null) {
				return (T) annotation;
			}
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	@Nullable
	public static <T extends Annotation> T findAnnotation(Class<T> annotationClass, Annotation[] annotations) {
		for (Annotation annotation : annotations) {
			if (annotation.annotationType() == annotationClass) {
				return (T) annotation;
			}
		}

		return null;
	}
}
