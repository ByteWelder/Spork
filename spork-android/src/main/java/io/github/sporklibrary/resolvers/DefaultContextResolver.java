package io.github.sporklibrary.resolvers;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.content.ContentProvider;
import android.content.Context;
import android.view.View;

import io.github.sporklibrary.annotations.Nullable;
import io.github.sporklibrary.interfaces.ContextResolver;

public class DefaultContextResolver implements ContextResolver {

	@Override
	public @Nullable Context resolveContext(Object object) {
		Class<?> object_class = object.getClass();

		if (View.class.isAssignableFrom(object_class)) {
			return ((View) object).getContext();
		} else if (Fragment.class.isAssignableFrom(object_class)) {
			return ((Fragment) object).getActivity();
		} else if (Activity.class.isAssignableFrom(object_class)) {
			return (Activity) object;
		} else if (Application.class.isAssignableFrom(object_class)) {
			return (Application) object;
		} else if (ContentProvider.class.isAssignableFrom(object_class)) {
			return ((ContentProvider) object).getContext();
		} else {
			return null;
		}
	}
}
