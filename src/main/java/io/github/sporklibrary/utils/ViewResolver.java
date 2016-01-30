package io.github.sporklibrary.utils;

import android.app.Activity;
import android.app.Fragment;
import android.view.View;
import io.github.sporklibrary.annotations.BindView;
import io.github.sporklibrary.annotations.Nullable;
import io.github.sporklibrary.exceptions.BindException;
import io.github.sporklibrary.exceptions.NotInstantiatableException;

public final class ViewResolver
{
	private ViewResolver()
	{
		throw new NotInstantiatableException(getClass());
	}

	public static @Nullable View getView(int viewId, String nameFallback, Activity activity)
	{
		if (viewId == 0)
		{
			// find by name
			viewId = activity.getResources().getIdentifier(nameFallback, "id", activity.getPackageName());
		}

		if (viewId == 0)
		{
			throw new BindException(BindView.class, activity.getClass(), "View not found");
		}

		return activity.findViewById(viewId);
	}

	public static @Nullable View getView(int viewId, String nameFallback, Fragment fragment)
	{
		View fragment_view = fragment.getView();

		if (fragment_view == null)
		{
			throw new BindException(BindView.class, fragment.getClass(), "cannot inject when Fragment View is not yet created");
		}

		if (viewId == 0)
		{
			// find by name
			viewId = fragment.getResources().getIdentifier(nameFallback, "id", fragment.getActivity().getPackageName());
		}

		if (viewId == 0)
		{
			throw new BindException(BindView.class, fragment.getClass(), "View not found");
		}

		return fragment_view.findViewById(viewId);
	}

	public static @Nullable View getView(int viewId, String nameFallback, View view)
	{
		if (viewId == 0)
		{
			// find by name
			viewId = view.getResources().getIdentifier(nameFallback, "id", view.getContext().getPackageName());
		}

		if (viewId == 0)
		{
			throw new BindException(BindView.class, view.getClass(), "View not found");
		}

		return view.findViewById(viewId);
	}
}
