package io.github.sporklibrary.binders;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * A model that contains an Annotation and a Method
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