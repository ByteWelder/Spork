package spork.android.internal;

import android.app.Activity;
import android.app.Fragment;
import android.view.View;

import javax.annotation.Nullable;

import spork.android.ViewProvider;
import spork.android.extension.ViewResolver;

/**
 * Default {@link ViewResolver} implementation for all regular Android classes that
 * could provide a {@link View} instance.
 */
public final class DefaultViewResolver implements ViewResolver {
	@Override
	@Nullable
	public View resolveView(Object object) throws Exception {
		if (object instanceof View) {
			return (View) object;
		} else if (object instanceof Activity) {
			return ((Activity) object).getWindow().getDecorView();
		} else if (object instanceof Fragment) {
			return ((Fragment) object).getView();
		} else if (object instanceof ViewProvider) {
			return ((ViewProvider) object).getView();
		} else {
			return null;
		}
	}
}
