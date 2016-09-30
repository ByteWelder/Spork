package io.github.sporklibrary.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import io.github.sporklibrary.annotations.Nullable;
import io.github.sporklibrary.exceptions.BindException;

/**
 * A utility class for {@link AnnotatedMethod}.
 */
public final class AnnotatedMethods {

	private AnnotatedMethods() {
	}

	/**
	 * Get an immutable set of annotated methods from the provided class
	 *
	 * @param annotationClass  the annotation class
	 * @param annotatedClass   the class to search for annotations
	 * @param <AnnotationType> the annotationClass type
	 * @return a set of AnnotatedMethod objects for the specified annotation type
	 */
	public static <AnnotationType extends Annotation> List<AnnotatedMethod<AnnotationType>> get(Class<AnnotationType> annotationClass, Class<?> annotatedClass) {
		ArrayList<AnnotatedMethod<AnnotationType>> annotatedMethods = new ArrayList<>();

		for (Method method : annotatedClass.getDeclaredMethods()) {
			AnnotationType annotation = method.getAnnotation(annotationClass);

			if (annotation != null) {
				annotatedMethods.add(new AnnotatedMethod<>(annotation, method));
			}
		}

		annotatedMethods.trimToSize();

		return annotatedMethods;
	}

	/**
	 * Invoke an AnnotatedMethod
	 *
	 * @param annotatedMethod the AnnotatedMethod
	 * @param object          the parent object
	 * @param args            the field value to bind
	 * @return the result of the invoked method
	 */
	@Nullable
	public static Object invoke(AnnotatedMethod<?> annotatedMethod, Object object, Object... args) {
		Method method = annotatedMethod.getMethod();

		boolean accessible = method.isAccessible();

		try {
			if (accessible) {
				return method.invoke(object, args);
			} else {
				method.setAccessible(true);
				Object result = method.invoke(object, args);
				method.setAccessible(false);
				return result;
			}
		} catch (IllegalAccessException e) {
			throw new BindException(annotatedMethod.getAnnotation().getClass(), object.getClass(), method, "method not accessible", e);
		} catch (InvocationTargetException e) {
			throw new BindException(annotatedMethod.getAnnotation().getClass(), object.getClass(), method, "method calling failed because of an invocation issue", e);
		} finally {
			// ensure the Field isn't accessible when it shouldn't be
			if (!accessible && method.isAccessible()) {
				method.setAccessible(false);
			}
		}
	}
}
