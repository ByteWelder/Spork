package io.github.sporklibrary.internal.reflection;

import java.lang.annotation.Annotation;

/**
 * A model that contains an {@link Annotation} and a {@link Class} type
 *
 * @param <AnnotationType> the annotation type to store
 */
public class AnnotatedType<AnnotationType extends Annotation> {
	private final AnnotationType annotation;
	private final Class<?> annotatedClass;

	public AnnotatedType(AnnotationType annotation, Class<?> annotatedClass) {
		this.annotation = annotation;
		this.annotatedClass = annotatedClass;
	}

	public AnnotationType getAnnotation() {
		return annotation;
	}

	public Class<?> getAnnotatedClass() {
		return annotatedClass;
	}
}
