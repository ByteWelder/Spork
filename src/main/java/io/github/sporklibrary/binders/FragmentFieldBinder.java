package io.github.sporklibrary.binders;

import android.app.Activity;
import android.app.Fragment;
import android.os.Build;
import io.github.sporklibrary.annotations.BindFragment;
import io.github.sporklibrary.exceptions.BindException;
import io.github.sporklibrary.exceptions.NotSupportedException;
import io.github.sporklibrary.utils.FragmentResolver;

import java.lang.reflect.Field;

public class FragmentFieldBinder implements FieldBinder<BindFragment>
{
	private enum Target
	{
		ACTIVITY,
		FRAGMENT,
		INVALID, Target;

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
			else
			{
				return INVALID;
			}
		}
	}

	@Override
	public Class<BindFragment> getAnnotationClass()
	{
		return BindFragment.class;
	}

	@Override
	public void bind(Object object, AnnotatedField<BindFragment> annotatedField)
	{
		Target target = Target.get(object);

		Field field = annotatedField.getField();

		if (!Fragment.class.isAssignableFrom(field.getType()))
		{
			throw new BindException(BindFragment.class, object.getClass(), "not compatibile with " + field.getType().getName());
		}

		BindFragment annotation = annotatedField.getAnnotation();

		Fragment fragment;

		switch (target)
		{
			case ACTIVITY:
				fragment = FragmentResolver.getFragment(field, annotation, (Activity) object);
				break;

			case FRAGMENT:
				if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1)
				{
					throw new NotSupportedException("injecting Fragments from within Fragments is only supported on from API level 17 and higher");
				}
				fragment = FragmentResolver.getFragment(field, annotation, (Fragment) object);
				break;

			default:
				throw new BindException(BindFragment.class, object.getClass(), "not compatible with " + object.getClass().getName());
		}

		boolean is_accessible = field.isAccessible();

		if (fragment == null)
		{
			throw new BindException(BindFragment.class, object.getClass(), "Fragment not found for " + field.getName());
		}

		try
		{
			field.setAccessible(true);
			field.set(object, fragment);
		}
		catch (IllegalAccessException e)
		{
			throw new BindException(BindFragment.class, object.getClass(), "field not accessible", e);
		}
		finally
		{
			field.setAccessible(is_accessible);
		}
	}
}
