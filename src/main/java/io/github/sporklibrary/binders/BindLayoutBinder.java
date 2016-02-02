package io.github.sporklibrary.binders;

import android.app.Activity;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import io.github.sporklibrary.annotations.BindLayout;
import io.github.sporklibrary.exceptions.BindException;
import io.github.sporklibrary.reflection.AnnotatedType;

public class BindLayoutBinder implements TypeBinder<BindLayout>
{
	@Override
	public void bind(Object object, AnnotatedType<BindLayout> annotatedClass)
	{
		int layout_resource_id = annotatedClass.getAnnotation().value();

		if (Activity.class.isAssignableFrom(object.getClass()))
		{
			((Activity)object).setContentView(layout_resource_id);
		}
		else if (ViewGroup.class.isAssignableFrom(object.getClass()))
		{
			ViewGroup view_group = (ViewGroup)object;
			LayoutInflater.from(view_group.getContext()).inflate(layout_resource_id, view_group);
		}
		else if (Fragment.class.isAssignableFrom(object.getClass()))
		{
			Fragment fragment = (Fragment)object;
			View fragment_view = fragment.getView();

			if (fragment_view == null)
			{
				throw new BindException(BindLayout.class, object.getClass(), "Spork.bind() must be called in Fragment's onViewCreated()");
			}

			if (!ViewGroup.class.isAssignableFrom(object.getClass()))
			{
				throw new BindException(BindLayout.class, object.getClass(), "Fragment must have a ViewGroup for a View to be able to inject a layout into it");
			}

			ViewGroup fragment_view_group = (ViewGroup)fragment_view;
			LayoutInflater.from(fragment_view.getContext()).inflate(layout_resource_id, fragment_view_group);
		}
		else
		{
			throw new BindException(BindLayout.class, object.getClass(), "annotation can only be used with Activity or ViewGroup");
		}
	}

	@Override
	public Class<BindLayout> getAnnotationClass()
	{
		return BindLayout.class;
	}
}
