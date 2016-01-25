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
			throw new BindException(BindFragment.class, object.getClass(), field, "field is not a Fragment");
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
				throw new BindException(BindFragment.class, object.getClass(), "class must be Fragment or Activity");
		}

		if (fragment == null)
		{
			throw new BindException(BindFragment.class, object.getClass(), field, "Fragment not found");
		}

		AnnotatedFields.set(annotatedField, object, fragment);
	}
}
