package io.github.sporklibrary.reflection;

import io.github.sporklibrary.binders.FieldBinder;

import java.lang.annotation.Annotation;
import java.util.Set;

public class AnnotatedFieldBinder<AnnotationType extends Annotation> implements ObjectBinder
{
	private final Set<AnnotatedField<AnnotationType>> mAnnotatedFields;

	private final FieldBinder<AnnotationType> mFieldBinder;

	public AnnotatedFieldBinder(FieldBinder<AnnotationType> fieldBinder, Set<AnnotatedField<AnnotationType>> annotatedFields)
	{
		mFieldBinder = fieldBinder;
		mAnnotatedFields = annotatedFields;
	}

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
