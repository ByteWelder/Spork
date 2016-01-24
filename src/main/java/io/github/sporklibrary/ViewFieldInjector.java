package io.github.sporklibrary;

import android.app.Activity;
import android.app.Fragment;
import android.view.View;
import io.github.sporklibrary.annotations.InjectView;

import java.lang.reflect.Field;

public class ViewFieldInjector implements FieldInjector<InjectView>
{
	@Override
	public Class<InjectView> getAnnotationClass()
	{
		return InjectView.class;
	}

	@Override
	public void inject(Object object, AnnotatedField<InjectView> annotatedField)
	{
		ViewInjectionTarget target = ViewInjectionTarget.get(object);

		Field field = annotatedField.getField();

		if (!View.class.isAssignableFrom(field.getType()))
		{
			throw new RuntimeException("InjectView cannot be used on non-View class " + object.getClass().getName());
		}

		InjectView annotation = annotatedField.getAnnotation();

		View view;

		switch (target)
		{
			case ACTIVITY:
				view = ViewResolver.getView(field, annotation, (Activity) object);
				break;

			case FRAGMENT:
				view = ViewResolver.getView(field, annotation, (Fragment) object);
				break;

			case VIEW:
				view = ViewResolver.getView(field, annotation, (View) object);
				break;

			default:
				throw new RuntimeException("InjectView cannot be used on " + object.getClass().getName() + " (must be used on Activity, Fragment or View)");
		}

		boolean is_accessible = field.isAccessible();

		try
		{
			field.setAccessible(true);
			field.set(object, view);
		}
		catch (IllegalAccessException e)
		{
			throw new RuntimeException("failed to inject View", e);
		}
		finally
		{
			field.setAccessible(is_accessible);
		}
	}
}
