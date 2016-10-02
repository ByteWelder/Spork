package spork.android.internal;

import android.app.Activity;
import android.app.Fragment;
import android.view.View;

import javax.annotation.Nullable;

import spork.android.interfaces.ViewProvider;
import spork.android.interfaces.ViewResolver;

public class DefaultViewResolver implements ViewResolver {
	@Override
	@Nullable
	public View resolveView(Object object) {
		Class<?> object_class = object.getClass();

		if (View.class.isAssignableFrom(object_class)) {
			return (View) object;
		} else if (Activity.class.isAssignableFrom(object_class)) {
			return ((Activity) object).getWindow().getDecorView();
		} else if (Fragment.class.isAssignableFrom(object_class)) {
			return ((Fragment) object).getView();
		} else if (ViewProvider.class.isAssignableFrom(object_class)) {
			return ((ViewProvider) object).getView();
		} else {
			return null;
		}
	}
}
