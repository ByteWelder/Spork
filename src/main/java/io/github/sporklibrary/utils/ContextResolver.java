package io.github.sporklibrary.utils;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.content.Context;
import android.view.View;
import io.github.sporklibrary.exceptions.NotInstantiatableException;
import io.github.sporklibrary.utils.support.SupportFragments;

public final class ContextResolver
{
	private ContextResolver()
	{
		throw new NotInstantiatableException(getClass());
	}

	public static Context getContext(Object object)
	{
		Class<?> object_class = object.getClass();

		if (View.class.isAssignableFrom(object_class))
		{
			return ((View)object).getContext();
		}
		else if (Fragment.class.isAssignableFrom(object_class))
		{
			return ((Fragment)object).getActivity();
		}
		else if (Activity.class.isAssignableFrom(object_class))
		{
			return (Activity)object;
		}
		else if (Application.class.isAssignableFrom(object_class))
		{
			return (Application)object;
		}
		else if (SupportFragments.isFragmentClass(object_class))
		{
			return ((android.support.v4.app.Fragment)object).getActivity();
		}
		else
		{
			return null;
		}
	}
}
