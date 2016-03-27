package io.github.sporklibrary.binders;

import android.app.Activity;
import android.app.Fragment;
import android.os.Build;
import io.github.sporklibrary.annotations.BindFragment;
import io.github.sporklibrary.annotations.Nullable;
import io.github.sporklibrary.exceptions.BindException;
import io.github.sporklibrary.exceptions.NotSupportedException;
import io.github.sporklibrary.reflection.AnnotatedField;
import io.github.sporklibrary.reflection.AnnotatedFields;
import io.github.sporklibrary.utils.FragmentResolver;
import io.github.sporklibrary.utils.support.SupportLibraries;

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
		Object fragment_object = resolveFragment(object, annotatedField);

		if (fragment_object == null)
		{
			throw new BindException(BindFragment.class, object.getClass(), annotatedField.getField(), "Fragment not found");
		}

		AnnotatedFields.setValue(annotatedField, object, fragment_object);
	}

	private @Nullable Object resolveFragment(Object object, AnnotatedField<BindFragment> annotatedField)
	{
		Field field = annotatedField.getField();

		if (!Fragment.class.isAssignableFrom(field.getType())
			&& (!SupportLibraries.hasSupportV4() || !android.support.v4.app.Fragment.class.isAssignableFrom(field.getType())))
		{
			throw new BindException(BindFragment.class, object.getClass(), field, "field is not a Fragment");
		}

		if (SupportLibraries.hasSupportV4() && android.support.v4.app.Fragment.class.isAssignableFrom(object.getClass()))
		{
			return FragmentResolver.getSupportFragment(field, annotatedField.getAnnotation(), (android.support.v4.app.Fragment) object);
		}
		else if (SupportLibraries.hasAppCompatV7() && android.support.v7.app.AppCompatActivity.class.isAssignableFrom(object.getClass()))
		{
			return FragmentResolver.getSupportFragment(field, annotatedField.getAnnotation(), (android.support.v7.app.AppCompatActivity)object);
		}
		else if (Activity.class.isAssignableFrom(object.getClass()))
		{
			return FragmentResolver.getFragment(field, annotatedField.getAnnotation(), (Activity) object);
		}
		else if (Fragment.class.isAssignableFrom(object.getClass()))
		{
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1)
			{
				throw new NotSupportedException("injecting Fragments from within Fragments is only supported on from API level 17 and higher");
			}

			return FragmentResolver.getFragment(field, annotatedField.getAnnotation(), (Fragment) object);
		}
		else
		{
			throw new BindException(BindFragment.class, object.getClass(), "class must be Fragment or Activity");
		}
	}
}