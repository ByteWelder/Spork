package spork.android.internal;

import android.app.Fragment;
import android.content.ContentProvider;
import android.content.Context;
import android.view.View;

import javax.annotation.Nullable;

import spork.android.ContextProvider;
import spork.android.extension.ContextResolver;
import spork.android.ViewProvider;

/**
 * Default {@link ContextResolver} implementation for all regular Android classes that
 * could provide a {@link Context} instance.
 */
public final class DefaultContextResolver implements ContextResolver {
	@Override
	@Nullable
	public Context resolveContext(Object object) throws Exception {
		if (object instanceof View) {
			return ((View) object).getContext();
		} else if (object instanceof Fragment) {
			return ((Fragment) object).getActivity();
		} else if (object instanceof Context) {
			return (Context) object;
		} else if (object instanceof ContentProvider) {
			return ((ContentProvider) object).getContext();
		} else if (object instanceof ContextProvider) {
			return ((ContextProvider) object).getContext();
		} else if (object instanceof ViewProvider) {
			return ((ViewProvider) object).getView().getContext();
		} else {
			return null;
		}
	}
}
