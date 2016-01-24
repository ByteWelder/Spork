package io.github.sporklibrary.binders;

import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * Manages Binders and their cache for a specific Annotation type.
 * @param <AnnotationType> the binder annotation type
 */
public class CompoundBinder<AnnotationType extends Annotation>
{
	private final @Nullable FieldBinder<AnnotationType> mFieldBinder;

	private final @Nullable MethodBinder<AnnotationType> mMethodBinder;

	private final @Nullable AnnotatedFieldRetriever<AnnotationType> mAnnotatedFieldRetriever;

	private final @Nullable AnnotatedMethodRetriever<AnnotationType> mAnnotatedMethodRetriever;

	public CompoundBinder(FieldBinder<AnnotationType> fieldBinder)
	{
		mFieldBinder = fieldBinder;
		mAnnotatedFieldRetriever = new AnnotatedFieldRetriever<>(fieldBinder.getAnnotationClass());
		mMethodBinder = null;
		mAnnotatedMethodRetriever = null;
	}

	public CompoundBinder(MethodBinder<AnnotationType> methodBinder)
	{
		mFieldBinder = null;
		mAnnotatedFieldRetriever = null;
		mMethodBinder = methodBinder;
		mAnnotatedMethodRetriever = new AnnotatedMethodRetriever<>(methodBinder.getAnnotationClass());
	}

	public CompoundBinder(FieldBinder<AnnotationType> fieldBinder, MethodBinder<AnnotationType> methodBinder)
	{
		mFieldBinder = fieldBinder;
		mAnnotatedFieldRetriever = new AnnotatedFieldRetriever<>(fieldBinder.getAnnotationClass());
		mMethodBinder = methodBinder;
		mAnnotatedMethodRetriever = new AnnotatedMethodRetriever<>(methodBinder.getAnnotationClass());
	}

	/**
	 * Apply all bindings to an object
	 * @param object the object to bind
	 */
	public void bind(Object object)
	{
		if (mFieldBinder != null && mAnnotatedFieldRetriever != null)
		{
			Set<AnnotatedField<AnnotationType>> annotated_field_set = mAnnotatedFieldRetriever.getAnnotatedFields(object.getClass());

			// Bind all fields for this object
			for (AnnotatedField<AnnotationType> annotated_field : annotated_field_set)
			{
				mFieldBinder.bind(object, annotated_field);
			}
		}

		if (mMethodBinder != null && mAnnotatedMethodRetriever != null)
		{
			Set<AnnotatedMethod<AnnotationType>> annotated_method_set = mAnnotatedMethodRetriever.getAnnotatedMethods(object.getClass());

			// Bind all fields for this object
			for (AnnotatedMethod<AnnotationType> annotated_method : annotated_method_set)
			{
				mMethodBinder.bind(object, annotated_method);
			}
		}
	}
}
