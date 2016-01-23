package io.github.sporklibrary;

import android.app.Activity;
import android.app.Fragment;
import android.view.View;

public enum ViewInjectionTarget
{
	ACTIVITY,
	FRAGMENT,
	VIEW,
	INVALID;

	/**
	 * @param object the object to validate
	 * @return the view injection target for the provided object
	 */
	public static ViewInjectionTarget get(Object object)
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
