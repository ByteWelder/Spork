package io.github.sporklibrary.binders;

import android.app.Activity;
import android.app.Fragment;
import android.view.View;
import io.github.sporklibrary.annotations.BindView;
import io.github.sporklibrary.annotations.Nullable;
import io.github.sporklibrary.exceptions.BindException;
import io.github.sporklibrary.utils.ViewResolver;

import java.lang.reflect.Field;

public class BindViewBinder implements FieldBinder<BindView>
{
	@Override
	public Class<BindView> getAnnotationClass()
	{
		return BindView.class;
	}

	@Override
	public void bind(final Object object, final AnnotatedField<BindView> annotatedField)
	{
		final @Nullable View view = resolveView(object, annotatedField);

		if (view == null)
		{
			throw new BindException(BindView.class, object.getClass(), annotatedField.getField(), "View not found");
		}

		AnnotatedFields.set(annotatedField, object, view);
	}

	private @Nullable View resolveView(Object object, AnnotatedField<BindView> annotatedField)
	{
		Field field = annotatedField.getField();

		if (!View.class.isAssignableFrom(field.getType()))
		{
			throw new BindException(BindView.class, object.getClass(), field, "field is not a View");
		}

		if (Activity.class.isAssignableFrom(object.getClass()))
		{
			return ViewResolver.getView(annotatedField.getAnnotation().value(), field.getName(), (Activity)object);
		}
		else if (Fragment.class.isAssignableFrom(object.getClass()))
		{
			return ViewResolver.getView(annotatedField.getAnnotation().value(), field.getName(), (Fragment)object);
		}
		else if (View.class.isAssignableFrom(object.getClass()))
		{
			return ViewResolver.getView(annotatedField.getAnnotation().value(), field.getName(), (View)object);
		}
		else
		{
			throw new BindException(BindView.class, object.getClass(), field, "class must be View, Fragment or Activity");
		}
	}
}