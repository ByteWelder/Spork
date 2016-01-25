package io.github.sporklibrary.binders;

import android.app.Activity;
import android.app.Fragment;
import android.view.View;
import io.github.sporklibrary.annotations.BindView;
import io.github.sporklibrary.exceptions.BindException;
import io.github.sporklibrary.utils.ViewResolver;

import java.lang.reflect.Field;

public class ViewFieldBinder implements FieldBinder<BindView>
{
	private enum Target
	{
		ACTIVITY,
		FRAGMENT,
		VIEW,
		INVALID;

		/**
		 * @param object the object to validate
		 * @return the binding target for the provided object
		 */
		public static Target get(Object object)
		{
			if (Activity.class.isAssignableFrom(object.getClass()))
			{
				return ACTIVITY;
			}
			else if (Fragment.class.isAssignableFrom(object.getClass()))
			{
				return FRAGMENT;
			}
			else if (View.class.isAssignableFrom(object.getClass()))
			{
				return VIEW;
			}
			else
			{
				return INVALID;
			}
		}
	}

	@Override
	public Class<BindView> getAnnotationClass()
	{
		return BindView.class;
	}

	@Override
	public void bind(Object object, AnnotatedField<BindView> annotatedField)
	{
		Target target = Target.get(object);

		Field field = annotatedField.getField();

		if (!View.class.isAssignableFrom(field.getType()))
		{
			throw new BindException(BindView.class, object.getClass(), field, "field is not a View");
		}

		BindView annotation = annotatedField.getAnnotation();

		View view;

		switch (target)
		{
			case ACTIVITY:
				view = ViewResolver.getView(annotation.value(), field.getName(), (Activity) object);
				break;

			case FRAGMENT:
				view = ViewResolver.getView(annotation.value(), field.getName(), (Fragment) object);
				break;

			case VIEW:
				view = ViewResolver.getView(annotation.value(), field.getName(), (View) object);
				break;

			default:
				throw new BindException(BindView.class, object.getClass(), "class must be View, Fragment or Activity");
		}

		if (view == null)
		{
			throw new BindException(BindView.class, object.getClass(), field, "View not found");
		}

		AnnotatedFields.set(annotatedField, object, view);
	}
}
