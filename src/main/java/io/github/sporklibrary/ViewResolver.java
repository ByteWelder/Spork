package io.github.sporklibrary;

import android.app.Activity;
import android.app.Fragment;
import android.view.View;
import io.github.sporklibrary.annotations.InjectView;

import java.lang.reflect.Field;

public class ViewResolver
{
	public static View getView(Field field, InjectView annotation, Activity activity)
	{
		int view_id = annotation.value();

		if (view_id == 0)
		{
			// find by name
			view_id = activity.getResources().getIdentifier(field.getName(), "id", activity.getPackageName());
		}

		if (view_id == 0)
		{
			throw new RuntimeException("view not found for field " + field.getName() + " in " + activity.getClass().getName());
		}

		return activity.findViewById(view_id);
	}

	public static View getView(Field field, InjectView annotation, Fragment fragment)
	{
		View fragment_view = fragment.getView();

		if (fragment_view == null)
		{
			throw new RuntimeException("cannot inject when Fragment View is not yet created");
		}

		int view_id = annotation.value();

		if (view_id == 0)
		{
			// find by name
			view_id = fragment.getResources().getIdentifier(field.getName(), "id", fragment.getActivity().getPackageName());
		}

		if (view_id == 0)
		{
			throw new RuntimeException("view not found for field " + field.getName() + " in " + fragment.getClass().getName());
		}

		return fragment_view.findViewById(view_id);
	}

	public static View getView(Field field, InjectView annotation, View view)
	{
		int view_id = annotation.value();

		if (view_id == 0)
		{
			// find by name
			view_id = view.getResources().getIdentifier(field.getName(), "id", view.getContext().getPackageName());
		}

		if (view_id == 0)
		{
			throw new RuntimeException("view not found for field " + field.getName() + " in " + view.getClass().getName());
		}

		return view.findViewById(view_id);
	}
}
