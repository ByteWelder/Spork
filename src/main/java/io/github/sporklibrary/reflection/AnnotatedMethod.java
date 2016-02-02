package io.github.sporklibrary.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * A model that contains an {@link Annotation} and a {@link Method}
 * @param <AnnotationType> the annotation type to store
 */
public class AnnotatedMethod<AnnotationType extends Annotation>
{
	private final AnnotationType mAnnotation;

	private final Method mMethod;

	public AnnotatedMethod(AnnotationType annotation, Method method)
	{
		mAnnotation = annotation;
		mMethod = method;
	}

	public AnnotationType getAnnotation()
	{
		return mAnnotation;
	}

	public Method getMethod()
	{
		return mMethod;
	}
}