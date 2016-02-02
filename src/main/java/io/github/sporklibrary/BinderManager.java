package io.github.sporklibrary;

import io.github.sporklibrary.annotations.Nullable;
import io.github.sporklibrary.binders.TypeBinder;
import io.github.sporklibrary.binders.CompoundBinder;
import io.github.sporklibrary.binders.FieldBinder;
import io.github.sporklibrary.binders.MethodBinder;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * The BinderManager manages CompoundBinder objects.
 */
public class BinderManager
{
	private final List<CompoundBinder> mBinders = new ArrayList<>();

	// TODO: prevent binder to be registered multiple times

	public <AnnotationType extends Annotation> void register(FieldBinder<AnnotationType> binder)
	{
		mBinders.add(new CompoundBinder<>(binder));
	}

	public <AnnotationType extends Annotation> void register(MethodBinder<AnnotationType> binder)
	{
		mBinders.add(new CompoundBinder<>(binder));
	}

	public <AnnotationType extends Annotation> void register(TypeBinder<AnnotationType> binder)
	{
		mBinders.add(new CompoundBinder<>(binder));
	}

	public <AnnotationType extends Annotation> void register(@Nullable FieldBinder<AnnotationType> fieldBinder,
	                                                         @Nullable MethodBinder<AnnotationType> methodBinder,
	                                                         @Nullable TypeBinder<AnnotationType> typeBinder)
	{
		mBinders.add(new CompoundBinder<>(fieldBinder, methodBinder, typeBinder));
	}

	/**
	 * Bind all annotations or the provided object.
	 * @param object the object to bind into
	 */
	public void bind(Object object)
	{
		for (CompoundBinder binder : mBinders)
		{
			binder.bind(object);
		}
	}
}