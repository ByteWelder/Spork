package io.github.sporklibrary.reflection;

import io.github.sporklibrary.annotations.Nullable;

import java.lang.annotation.Annotation;

public final class AnnotatedTypes
{
	private AnnotatedTypes()
	{
	}

	public static @Nullable <AnnotationType extends Annotation> AnnotationType get(Class<AnnotationType> annotationClass, Class<?> annotatedClass)
	{
		return annotatedClass.getAnnotation(annotationClass);
	}
}
