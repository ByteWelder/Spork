package io.github.sporklibrary.reflection;

import java.lang.annotation.Annotation;

/**
 * A model that contains an Annotation and a Class type
 * @param <AnnotationType> the annotation type to store
 */
public class AnnotatedType<AnnotationType extends Annotation>
{
	private final AnnotationType mAnnotation;

	private final Class<?> mAnnotatedClass;

	public AnnotatedType(AnnotationType annotation, Class<?> annotatedClass)
	{
		mAnnotation = annotation;
		mAnnotatedClass = annotatedClass;
	}

	public AnnotationType getAnnotation()
	{
		return mAnnotation;
	}

	public Class<?> getAnnotatedClass()
	{
		return mAnnotatedClass;
	}
}
