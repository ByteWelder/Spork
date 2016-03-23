package io.github.sporklibrary;

import io.github.sporklibrary.binders.FieldBinder;
import io.github.sporklibrary.binders.MethodBinder;
import io.github.sporklibrary.binders.TypeBinder;
import io.github.sporklibrary.reflection.*;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Holds the annotation type cache for a class.
 * This only holds the data for the specified class and excludes the cache of its superclasses.
 */
final class BinderCache
{
	private final List<ObjectBinder> mObjectBinders = new ArrayList<>();

	private final Class<?> mAnnotatedType;

	BinderCache(Class<?> annotatedType)
	{
		mAnnotatedType = annotatedType;
	}

	<AnnotationType extends Annotation> void register(FieldBinder<AnnotationType> fieldBinder)
	{
		Set<AnnotatedField<AnnotationType>> annotated_fields = AnnotatedFields.get(fieldBinder.getAnnotationClass(), mAnnotatedType);

		if (!annotated_fields.isEmpty())
		{
			mObjectBinders.add(new AnnotatedFieldBinder<>(fieldBinder, annotated_fields));
		}
	}

	<AnnotationType extends Annotation> void register(MethodBinder<AnnotationType> methodBinder)
	{
		Set<AnnotatedMethod<AnnotationType>> annotated_methods = AnnotatedMethods.get(methodBinder.getAnnotationClass(), mAnnotatedType);

		if (!annotated_methods.isEmpty())
		{
			mObjectBinders.add(new AnnotatedMethodBinder<>(methodBinder, annotated_methods));
		}
	}

	<AnnotationType extends Annotation> void register(TypeBinder<AnnotationType> typeBinder)
	{
		AnnotationType annotated_type = AnnotatedTypes.get(typeBinder.getAnnotationClass(), mAnnotatedType);

		mObjectBinders.add(new AnnotatedTypeBinder<>(typeBinder, new AnnotatedType<>(annotated_type, mAnnotatedType)));
	}

	public List<ObjectBinder> getBinders()
	{
		return mObjectBinders;
	}
}
