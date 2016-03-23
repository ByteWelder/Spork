package io.github.sporklibrary.reflection;

import io.github.sporklibrary.binders.TypeBinder;

import java.lang.annotation.Annotation;

public class AnnotatedTypeBinder<AnnotationType extends Annotation> implements ObjectBinder
{
	private final AnnotatedType<AnnotationType> mAnnotatedType;

	private final TypeBinder<AnnotationType> mTypeBinder;

	public AnnotatedTypeBinder(TypeBinder<AnnotationType> typeBinder, AnnotatedType<AnnotationType> annotatedType)
	{
		mAnnotatedType = annotatedType;
		mTypeBinder = typeBinder;
	}

	@Override
	public void bind(Object object)
	{
		mTypeBinder.bind(object, mAnnotatedType);
	}
}
