package io.github.sporklibrary.reflection;

import java.lang.annotation.Annotation;

public final class AnnotatedTypes
{
	private AnnotatedTypes()
	{
	}

	public static <AnnotationType extends Annotation> AnnotationType get(Class<AnnotationType> annotationClass, Class<?> annotatedClass)
	{
		return annotatedClass.getAnnotation(annotationClass);
	}
}
