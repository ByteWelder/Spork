package io.github.sporklibrary.utils;

import android.content.Context;
import android.view.View;
import io.github.sporklibrary.annotations.BindView;
import io.github.sporklibrary.exceptions.BindException;
import io.github.sporklibrary.exceptions.NotInstantiatableException;

public final class ViewResolver
{
	private ViewResolver()
	{
		throw new NotInstantiatableException(getClass());
	}

	/**
	 * @param viewId R.id.* value or ResourceId.sDefaultValue
	 * @param nameFallback used when ResourceId.sDefaultValue is set, this name will be used to resolve R.id.namefallback
	 * @param object any Activity, Fragment or View (including support library types)
	 * @return the found View
	 */
	public static View getView(int viewId, String nameFallback, Object object)
	{
		View root_view = Views.getRootView(object);

		if (root_view == null)
		{
			throw new BindException(BindView.class, object.getClass(), "incompatible class to find views");
		}

		if (viewId == ResourceId.sDefaultValue)
		{
			// find by name
			Context context = root_view.getContext();

			viewId = context.getResources().getIdentifier(nameFallback, "id", context.getPackageName());

			if (viewId == 0)
			{
				throw new BindException(BindView.class, object.getClass(), "View not found with name '" + nameFallback + "'");
			}
		}

		View view = root_view.findViewById(viewId);

		if (view == null)
		{
			throw new BindException(BindView.class, object.getClass(), "View not found");
		}

		return view;
	}
}
