package io.github.sporklibrary.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import io.github.sporklibrary.annotations.BindFragment;
import io.github.sporklibrary.annotations.Nullable;
import io.github.sporklibrary.exceptions.BindException;

import java.lang.reflect.Field;

public final class FragmentResolver
{
	private FragmentResolver()
	{
	}

	public static @Nullable Fragment getFragment(Field field, BindFragment annotation, Activity activity)
	{
		int fragment_id = annotation.value();

		if (fragment_id == ResourceId.sDefaultValue)
		{
			// find by name
			fragment_id = activity.getResources().getIdentifier(field.getName(), "id", activity.getPackageName());

			if (fragment_id == 0)
			{
				throw new BindException(BindFragment.class, activity.getClass(), "Fragment not found by name '" + field.getName() + "'");
			}
		}

		return activity.getFragmentManager().findFragmentById(fragment_id);
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
	public static @Nullable Fragment getFragment(Field field, BindFragment annotation, Fragment fragment)
	{
		int fragment_id = annotation.value();

		if (fragment_id == ResourceId.sDefaultValue)
		{
			// find by name
			fragment_id = fragment.getResources().getIdentifier(field.getName(), "id", fragment.getActivity().getPackageName());

			if (fragment_id == 0)
			{
				throw new BindException(BindFragment.class, fragment.getClass(), "Fragment not found by name '" + field.getName() + "'");
			}
		}

		Fragment child_fragment = fragment.getChildFragmentManager().findFragmentById(fragment_id);

		if (child_fragment == null)
		{
			// On a Wiko Sunset handset, the child fragment was registered with the regular FragmentManager:
			return fragment.getFragmentManager().findFragmentById(fragment_id);
		}
		else
		{
			return child_fragment;
		}
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
	public static @Nullable android.support.v4.app.Fragment getSupportFragment(Field field, BindFragment annotation, android.support.v4.app.Fragment fragment)
	{
		int fragment_id = annotation.value();

		if (fragment_id == ResourceId.sDefaultValue)
		{
			// find by name
			fragment_id = fragment.getResources().getIdentifier(field.getName(), "id", fragment.getActivity().getPackageName());

			if (fragment_id == 0)
			{
				throw new BindException(BindFragment.class, fragment.getClass(), "Fragment not found by name '" + field.getName() + "'");
			}
		}

		android.support.v4.app.Fragment child_fragment = fragment.getChildFragmentManager().findFragmentById(fragment_id);

		if (child_fragment == null)
		{
			// On a Wiko Sunset handset, the child fragment was registered with the regular FragmentManager:
			return fragment.getFragmentManager().findFragmentById(fragment_id);
		}
		else
		{
			return child_fragment;
		}
	}

	public static @Nullable android.support.v4.app.Fragment getSupportFragment(Field field, BindFragment annotation, AppCompatActivity activity)
	{
		int fragment_id = annotation.value();

		if (fragment_id == ResourceId.sDefaultValue)
		{
			// find by name
			fragment_id = activity.getResources().getIdentifier(field.getName(), "id", activity.getPackageName());

			if (fragment_id == 0)
			{
				throw new BindException(BindFragment.class, activity.getClass(), "Fragment not found by name '" + field.getName() + "'");
			}
		}

		return activity.getSupportFragmentManager().findFragmentById(fragment_id);
	}
}
