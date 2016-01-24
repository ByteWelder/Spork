package io.github.sporklibrary;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * The BinderManager manages CompoundBinder objects.
 */
public class BinderManager
{
	private final List<CompoundBinder> mBinders = new ArrayList<>();

	/**
	 * Register a new FieldBinder
	 * @param fieldBinder the FieldBinder to register
	 */
	public <AnnotationType extends Annotation> void register(FieldBinder<AnnotationType> fieldBinder)
	{
		mBinders.add(new CompoundBinder<>(fieldBinder));
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