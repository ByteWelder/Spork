package spork.android.support.internal;

import android.view.View;

import spork.android.interfaces.ViewResolver;
import spork.annotations.Nullable;

public class SupportViewResolver implements ViewResolver {

	@Override
	public @Nullable View resolveView(Object object) {
		if (android.support.v4.app.Fragment.class.isAssignableFrom(object.getClass())) {
			return ((android.support.v4.app.Fragment) object).getView();
		} else {
			return null;
		}
	}
}
