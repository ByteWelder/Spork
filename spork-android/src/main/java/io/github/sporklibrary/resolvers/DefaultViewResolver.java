package io.github.sporklibrary.resolvers;

import android.app.Activity;
import android.app.Fragment;
import android.view.View;

import io.github.sporklibrary.annotations.Nullable;
import io.github.sporklibrary.interfaces.ViewResolver;

public class DefaultViewResolver implements ViewResolver {
	@Override
	public @Nullable View resolveView(Object object) {
		Class<?> object_class = object.getClass();

		if (View.class.isAssignableFrom(object_class)) {
			return (View) object;
		} else if (Activity.class.isAssignableFrom(object_class)) {
			return ((Activity) object).getWindow().getDecorView();
		} else if (Fragment.class.isAssignableFrom(object_class)) {
			return ((Fragment) object).getView();
		} else {
			return null;
		}
	}
}
