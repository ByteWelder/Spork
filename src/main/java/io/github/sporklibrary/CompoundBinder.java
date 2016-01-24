package io.github.sporklibrary;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * Manages Binders and their cache for a specific Annotation type.
 * @param <AnnotationType> the binder annotation type
 */
class CompoundBinder<AnnotationType extends Annotation>
{
	private final FieldBinder<AnnotationType> mFieldBinder;

	private final AnnotationFieldRetriever<AnnotationType> mAnnotationFieldRetriever;

	public CompoundBinder(FieldBinder<AnnotationType> fieldBinder)
	{
		mFieldBinder = fieldBinder;
		mAnnotationFieldRetriever = new AnnotationFieldRetriever<>(fieldBinder.getAnnotationClass());
	}

	/**
	 * Apply all bindings to an object
	 * @param object the object to bind
	 */
	public void bind(Object object)
	{
		Set<AnnotatedField<AnnotationType>> annotated_field_set = mAnnotationFieldRetriever.getAnnotatedFields(object.getClass());

		// Bind all fields for this object
		for (AnnotatedField<AnnotationType> annotated_field : annotated_field_set)
		{
			mFieldBinder.bind(object, annotated_field);
		}
	}
}
