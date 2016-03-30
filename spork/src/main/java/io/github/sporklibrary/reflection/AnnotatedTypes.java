package io.github.sporklibrary.reflection;

import io.github.sporklibrary.annotations.Nullable;

import java.lang.annotation.Annotation;

/**
 * A utility class for {@link AnnotatedType}.
 */
public final class AnnotatedTypes
{
	private AnnotatedTypes()
	{
	}

	/**
	 * Get a an annotation from the provided class
	 * @param annotationClass the annotation class
	 * @param annotatedClass the class to search for annotations
	 * @param <AnnotationType> the annotationClass type
	 * @return the annotation instance or null
	 */
	public static @Nullable <AnnotationType extends Annotation> AnnotatedType<AnnotationType> get(Class<AnnotationType> annotationClass, Class<?> annotatedClass)
	{
		AnnotationType annotation = annotatedClass.getAnnotation(annotationClass);

		return annotation != null ? new AnnotatedType<>(annotation, annotatedClass) : null;
	}
}
