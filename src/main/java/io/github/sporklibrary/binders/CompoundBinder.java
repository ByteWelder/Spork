package io.github.sporklibrary.binders;

import io.github.sporklibrary.annotations.Nullable;
import io.github.sporklibrary.reflection.*;

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

	private final @Nullable TypeBinder<AnnotationType> mTypeBinder;

	private final @Nullable AnnotatedFieldRetriever<AnnotationType> mAnnotatedFieldRetriever;

	private final @Nullable AnnotatedMethodRetriever<AnnotationType> mAnnotatedMethodRetriever;

	private final @Nullable	AnnotatedTypeRetriever<AnnotationType> mAnnotatedTypeRetriever;

	public CompoundBinder(FieldBinder<AnnotationType> fieldBinder)
	{
		this(fieldBinder, null, null);
	}

	public CompoundBinder(MethodBinder<AnnotationType> methodBinder)
	{
		this(null, methodBinder, null);
	}

	public CompoundBinder(TypeBinder<AnnotationType> typeBinder)
	{
		this(null, null, typeBinder);
	}

	public CompoundBinder(@Nullable FieldBinder<AnnotationType> fieldBinder,
	                      @Nullable MethodBinder<AnnotationType> methodBinder,
	                      @Nullable TypeBinder<AnnotationType> typeBinder)
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

		if (typeBinder != null)
		{
			mTypeBinder = typeBinder;
			mAnnotatedTypeRetriever = new AnnotatedTypeRetriever<>(typeBinder.getAnnotationClass());
		}
		else
		{
			mTypeBinder = null;
			mAnnotatedTypeRetriever = null;
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
			bindFields(object);
		}

		if (mMethodBinder != null && mAnnotatedMethodRetriever != null)
		{
			bindMethods(object);
		}

		if (mTypeBinder != null && mAnnotatedTypeRetriever != null)
		{
			bindType(object);
		}
	}

	// region field binding

	private void bindFields(Object object)
	{
		Class<?> object_class = object.getClass();

		while (object_class != null && object_class != Object.class)
		{
			bindFields(object, object_class);

			object_class = object_class.getSuperclass();
		}
	}

	private void bindFields(Object object, Class<?> objectClass)
	{
		Set<AnnotatedField<AnnotationType>> annotated_field_set = mAnnotatedFieldRetriever.getAnnotatedFields(objectClass);

		// Bind all fields for this object
		for (AnnotatedField<AnnotationType> annotated_field : annotated_field_set)
		{
			mFieldBinder.bind(object, annotated_field);
		}
	}

	// endregion

	// region method binding

	private void bindMethods(Object object)
	{
		Class<?> object_class = object.getClass();

		while (object_class != null && object_class != Object.class)
		{
			bindMethods(object, object_class);

			object_class = object_class.getSuperclass();
		}
	}

	private void bindMethods(Object object, Class<?> objectClass)
	{
		Set<AnnotatedMethod<AnnotationType>> annotated_method_set = mAnnotatedMethodRetriever.getAnnotatedMethods(objectClass);

		// Bind all fields for this object
		for (AnnotatedMethod<AnnotationType> annotated_method : annotated_method_set)
		{
			mMethodBinder.bind(object, annotated_method);
		}
	}

	// endregion

	// region type binding

	private void bindType(Object object)
	{
		Class<?> object_class = object.getClass();

		while (object_class != null && object_class != Object.class)
		{
			bindType(object, object_class);

			object_class = object_class.getSuperclass();
		}
	}

	private void bindType(Object object, Class<?> objectClass)
	{
		AnnotatedType<AnnotationType> annotated_class = mAnnotatedTypeRetriever.getAnnotatedClass(objectClass);

		if (annotated_class != null)
		{
			mTypeBinder.bind(object, annotated_class);
		}
	}

	// endregion
}
