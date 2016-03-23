package io.github.sporklibrary.reflection;

import io.github.sporklibrary.binders.MethodBinder;

import java.lang.annotation.Annotation;
import java.util.Set;

public class AnnotatedMethodBinder<AnnotationType extends Annotation> implements ObjectBinder
{
	private final Set<AnnotatedMethod<AnnotationType>> mAnnotatedFields;

	private final MethodBinder<AnnotationType> mFieldBinder;

	public AnnotatedMethodBinder(MethodBinder<AnnotationType> fieldBinder, Set<AnnotatedMethod<AnnotationType>> annotatedFields)
	{
		mFieldBinder = fieldBinder;
		mAnnotatedFields = annotatedFields;
	}

	@Override
	public void bind(Object object)
	{
		// Bind all methods for this object
		for (AnnotatedMethod<AnnotationType> annotated_method : mAnnotatedFields)
		{
			mFieldBinder.bind(object, annotated_method);
		}
	}
}
