package io.github.sporklibrary.caching;

import io.github.sporklibrary.binders.TypeBinder;
import io.github.sporklibrary.interfaces.ObjectBinder;
import io.github.sporklibrary.reflection.AnnotatedType;

import java.lang.annotation.Annotation;

/**
 * A binder that caches type bindings for a specific class.
 *
 * @param <AnnotationType> the annotation type that is being bound
 */
class AnnotatedTypeBinder<AnnotationType extends Annotation> implements ObjectBinder
{
	private final AnnotatedType<AnnotationType> annotatedType;

	private final TypeBinder<AnnotationType> typeBinder;

	AnnotatedTypeBinder(TypeBinder<AnnotationType> typeBinder, AnnotatedType<AnnotationType> annotatedType)
	{
		this.annotatedType = annotatedType;
		this.typeBinder = typeBinder;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void bind(Object object)
	{
		typeBinder.bind(object, annotatedType);
	}
}
