package io.github.sporklibrary.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * A model that contains an {@link Annotation} and a {@link Field}
 *
 * @param <AnnotationType> the annotation type to store
 */
public class AnnotatedField<AnnotationType extends Annotation> {
	private final AnnotationType annotation;
	private final Field field;

	public AnnotatedField(AnnotationType annotation, Field field) {
		this.annotation = annotation;
		this.field = field;
	}

	public AnnotationType getAnnotation() {
		return annotation;
	}

	public Field getField() {
		return field;
	}
}
