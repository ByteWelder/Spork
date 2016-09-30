package io.github.sporklibrary.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import io.github.sporklibrary.exceptions.BindException;

/**
 * A utility  class for {@link AnnotatedField}.
 */
public final class AnnotatedFields {

	private AnnotatedFields() {
	}

	/**
	 * Get an immutable set of annotated fields from the provided class
	 *
	 * @param annotationClass  the annotation class
	 * @param annotatedClass   the class to search for annotations
	 * @param <AnnotationType> the annotationClass type
	 * @return a set of AnnotatedField objects for the specified annotation type
	 */
	public static <AnnotationType extends Annotation> List<AnnotatedField<AnnotationType>> get(Class<AnnotationType> annotationClass, Class<?> annotatedClass) {
		ArrayList<AnnotatedField<AnnotationType>> annotatedFields = new ArrayList<>();

		for (Field field : annotatedClass.getDeclaredFields()) {
			AnnotationType annotation = field.getAnnotation(annotationClass);

			if (annotation != null) {
				annotatedFields.add(new AnnotatedField<>(annotation, field));
			}
		}

		annotatedFields.trimToSize();

		return annotatedFields;
	}

	/**
	 * Set a value for an AnnotatedField on an object
	 *
	 * @param annotation   the annotation to set a value for
	 * @param field        the field where the annotation was placed
	 * @param parentObject the parent object
	 * @param valueObject  the field value to bind
	 */
	public static void setValue(Annotation annotation, Field field, Object parentObject, Object valueObject) {
		boolean accessible = field.isAccessible();

		try {
			if (accessible) {
				field.set(parentObject, valueObject);
			} else {
				field.setAccessible(true);
				field.set(parentObject, valueObject);
				field.setAccessible(false);
			}
		} catch (IllegalAccessException e) {
			throw new BindException(annotation.getClass(), parentObject.getClass(), field, "field not accessible", e);
		} finally {
			// ensure the Field isn't accessible when it shouldn't be
			if (!accessible && field.isAccessible()) {
				field.setAccessible(false);
			}
		}
	}
}
