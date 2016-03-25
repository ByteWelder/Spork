package io.github.sporklibrary.caching;

import io.github.sporklibrary.binders.MethodBinder;
import io.github.sporklibrary.interfaces.ObjectBinder;
import io.github.sporklibrary.reflection.AnnotatedMethod;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * A binder that caches method bindings for a specific class.
 *
 * @param <AnnotationType> the annotation type that is being bound
 */
class AnnotatedMethodBinder<AnnotationType extends Annotation> implements ObjectBinder
{
	private final Set<AnnotatedMethod<AnnotationType>> mAnnotatedMethods;

	private final MethodBinder<AnnotationType> mMethodBinder;

	AnnotatedMethodBinder(MethodBinder<AnnotationType> methodBinder, Set<AnnotatedMethod<AnnotationType>> annotatedMethods)
	{
		mMethodBinder = methodBinder;
		mAnnotatedMethods = annotatedMethods;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void bind(Object object)
	{
		// Bind all methods for this object
		for (AnnotatedMethod<AnnotationType> annotated_method : mAnnotatedMethods)
		{
			mMethodBinder.bind(object, annotated_method);
		}
	}
}
