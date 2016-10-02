package spork.android.internal;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import spork.android.interfaces.ResolverRegistry;
import spork.android.interfaces.ViewResolver;

public final class ViewResolverManager implements ViewResolver, ResolverRegistry<ViewResolver> {
	private final List<ViewResolver> viewResolvers = new ArrayList<>(2);

	public void register(ViewResolver viewResolver) {
		viewResolvers.add(viewResolver);
	}

	@Override
	@Nullable
	public View resolveView(Object object) {
		for (ViewResolver viewResolver : viewResolvers) {
			View view = viewResolver.resolveView(object);

			if (view != null) {
				return view;
			}
		}

		return null;
	}
}
