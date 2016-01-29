package io.github.sporklibrary.binders;

import android.app.Activity;
import android.app.Fragment;
import android.os.Build;
import io.github.sporklibrary.annotations.BindFragment;
import io.github.sporklibrary.annotations.Nullable;
import io.github.sporklibrary.exceptions.BindException;
import io.github.sporklibrary.exceptions.NotSupportedException;
import io.github.sporklibrary.utils.FragmentResolver;

import java.lang.reflect.Field;

public class BindFragmentBinder implements FieldBinder<BindFragment>
{
	@Override
	public Class<BindFragment> getAnnotationClass()
	{
		return BindFragment.class;
	}

	@Override
	public void bind(Object object, AnnotatedField<BindFragment> annotatedField)
	{
		Fragment fragment = resolveFragment(object, annotatedField);

		if (fragment == null)
		{
			throw new BindException(BindFragment.class, object.getClass(), annotatedField.getField(), "Fragment not found");
		}

		AnnotatedFields.set(annotatedField, object, fragment);
	}

	private @Nullable Fragment resolveFragment(Object object, AnnotatedField<BindFragment> annotatedField)
	{
		Field field = annotatedField.getField();

		if (!Fragment.class.isAssignableFrom(field.getType()))
		{
			throw new BindException(BindFragment.class, object.getClass(), field, "field is not a Fragment");
		}

		if (Activity.class.isAssignableFrom(object.getClass()))
		{
			return FragmentResolver.getFragment(field, annotatedField.getAnnotation(), (Activity)object);
		}
		else if (Fragment.class.isAssignableFrom(object.getClass()))
		{
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1)
			{
				throw new NotSupportedException("injecting Fragments from within Fragments is only supported on from API level 17 and higher");
			}

			return FragmentResolver.getFragment(field, annotatedField.getAnnotation(), (Fragment)object);
		}
		else
		{
			throw new BindException(BindFragment.class, object.getClass(), "class must be Fragment or Activity");
		}
	}
}