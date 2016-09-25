package io.github.sporklibrary.android.support.resolvers;

import android.view.View;

import io.github.sporklibrary.android.resolvers.ViewResolver;
import io.github.sporklibrary.annotations.Nullable;

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
