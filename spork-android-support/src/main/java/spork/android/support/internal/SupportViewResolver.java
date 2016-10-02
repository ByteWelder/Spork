package spork.android.support.internal;

import android.view.View;

import javax.annotation.Nullable;

import spork.android.interfaces.ViewResolver;

public class SupportViewResolver implements ViewResolver {
	@Override
	@Nullable
	public View resolveView(Object object) {
		if (android.support.v4.app.Fragment.class.isAssignableFrom(object.getClass())) {
			return ((android.support.v4.app.Fragment) object).getView();
		} else {
			return null;
		}
	}
}
