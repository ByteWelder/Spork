package io.github.sporklibrary.binders;

import io.github.sporklibrary.reflection.*;
import io.github.sporklibrary.annotations.Nullable;
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

	private final @Nullable ClassBinder<AnnotationType> mClassBinder;

	private final @Nullable AnnotatedFieldRetriever<AnnotationType> mAnnotatedFieldRetriever;

	private final @Nullable AnnotatedMethodRetriever<AnnotationType> mAnnotatedMethodRetriever;

	private final @Nullable	AnnotatedClassRetriever<AnnotationType> mAnnotatedClassRetriever;

	public CompoundBinder(FieldBinder<AnnotationType> fieldBinder)
	{
		this(fieldBinder, null, null);
	}

	public CompoundBinder(MethodBinder<AnnotationType> methodBinder)
	{
		this(null, methodBinder, null);
	}

	public CompoundBinder(ClassBinder<AnnotationType> classBinder)
	{
		this(null, null, classBinder);
	}

	public CompoundBinder(@Nullable FieldBinder<AnnotationType> fieldBinder,
	                      @Nullable MethodBinder<AnnotationType> methodBinder,
	                      @Nullable ClassBinder<AnnotationType> classBinder)
	{
		if (fieldBinder != null)
		{
			mFieldBinder = fieldBinder;
			mAnnotatedFieldRetriever = new AnnotatedFieldRetriever<>(fieldBinder.getAnnotationClass());
		}
		else
		{
			mFieldBinder = null;
			mAnnotatedFieldRetriever = null;
		}

		if (methodBinder != null)
		{
			mMethodBinder = methodBinder;
			mAnnotatedMethodRetriever = new AnnotatedMethodRetriever<>(methodBinder.getAnnotationClass());
		}
		else
		{
			mMethodBinder = null;
			mAnnotatedMethodRetriever = null;
		}

		if (classBinder != null)
		{
			mClassBinder = classBinder;
			mAnnotatedClassRetriever = new AnnotatedClassRetriever<>(classBinder.getAnnotationClass());
		}
		else
		{
			mClassBinder = null;
			mAnnotatedClassRetriever = null;
		}
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

		if (mClassBinder != null && mAnnotatedClassRetriever != null)
		{
			AnnotatedClass<AnnotationType> annotated_class = mAnnotatedClassRetriever.getAnnotatedClass(object.getClass());

			if (annotated_class != null)
			{
				mClassBinder.bind(object, annotated_class);
			}
		}
	}
}
