package io.github.sporklibrary.caching;

import io.github.sporklibrary.binders.TypeBinder;
import io.github.sporklibrary.reflection.AnnotatedType;

import java.lang.annotation.Annotation;

/**
 * A binder that caches type bindings for a specific class.
 *
 * @param <AnnotationType> the annotation type that is being bound
 */
public class AnnotatedTypeBinder<AnnotationType extends Annotation> implements ObjectBinder
{
	private final AnnotatedType<AnnotationType> mAnnotatedType;

	private final TypeBinder<AnnotationType> mTypeBinder;

	public AnnotatedTypeBinder(TypeBinder<AnnotationType> typeBinder, AnnotatedType<AnnotationType> annotatedType)
	{
		mAnnotatedType = annotatedType;
		mTypeBinder = typeBinder;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void bind(Object object)
	{
		mTypeBinder.bind(object, mAnnotatedType);
	}
}
