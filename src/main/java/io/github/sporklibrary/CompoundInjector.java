package io.github.sporklibrary;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * Manages Injectors and their cache for a specific Annotation type.
 * @param <AnnotationType> the injection annotation type
 */
class CompoundInjector<AnnotationType extends Annotation>
{
	private final FieldInjector<AnnotationType> mFieldInjector;

	private final AnnotationFieldRetriever<AnnotationType> mAnnotationFieldRetriever;

	public CompoundInjector(FieldInjector<AnnotationType> fieldInjector)
	{
		mFieldInjector = fieldInjector;
		mAnnotationFieldRetriever = new AnnotationFieldRetriever<>(fieldInjector.getAnnotationClass());
	}

	/**
	 * Apply all injection to an object
	 * @param object the object to inject
	 */
	public void inject(Object object)
	{
		Set<AnnotatedField<AnnotationType>> annotated_field_set = mAnnotationFieldRetriever.getAnnotatedFields(object.getClass());

		// Inject all fields for this object
		for (AnnotatedField<AnnotationType> annotated_field : annotated_field_set)
		{
			mFieldInjector.inject(object, annotated_field);
		}
	}
}
