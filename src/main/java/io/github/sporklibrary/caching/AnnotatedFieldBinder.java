package io.github.sporklibrary.caching;

import io.github.sporklibrary.binders.FieldBinder;
import io.github.sporklibrary.reflection.AnnotatedField;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * A binder that caches field bindings for a specific class.
 *
 * @param <AnnotationType> the annotation type that is being bound
 */
public class AnnotatedFieldBinder<AnnotationType extends Annotation> implements ObjectBinder
{
	private final Set<AnnotatedField<AnnotationType>> mAnnotatedFields;

	private final FieldBinder<AnnotationType> mFieldBinder;

	public AnnotatedFieldBinder(FieldBinder<AnnotationType> fieldBinder, Set<AnnotatedField<AnnotationType>> annotatedFields)
	{
		mFieldBinder = fieldBinder;
		mAnnotatedFields = annotatedFields;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void bind(Object object)
	{
		// Bind all fields for this object
		for (AnnotatedField<AnnotationType> annotated_field : mAnnotatedFields)
		{
			mFieldBinder.bind(object, annotated_field);
		}
	}
}
