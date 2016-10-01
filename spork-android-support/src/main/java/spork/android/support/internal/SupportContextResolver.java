package spork.android.support.internal;

import android.content.Context;
import android.support.annotation.Nullable;

import spork.android.interfaces.ContextResolver;

public class SupportContextResolver implements ContextResolver {

	@Override
	public @Nullable Context resolveContext(Object object) {
		if (android.support.v4.app.Fragment.class.isAssignableFrom(object.getClass())) {
			return ((android.support.v4.app.Fragment) object).getActivity();
		} else {
			return null;
		}
	}
}
