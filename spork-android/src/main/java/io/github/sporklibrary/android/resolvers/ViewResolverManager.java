package io.github.sporklibrary.android.resolvers;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

import io.github.sporklibrary.annotations.Nullable;

public final class ViewResolverManager implements ViewResolver {

	private static ViewResolverManager instance;

	private ViewResolverManager() {
	}

	public static ViewResolverManager shared() {
		if (instance == null) {
			instance = new ViewResolverManager();
		}

		return instance;
	}

	private final List<ViewResolver> viewResolvers = new ArrayList<>();

	public void addViewResolver(ViewResolver viewResolver) {
		viewResolvers.add(viewResolver);
	}

	@Override
	public @Nullable View resolveView(Object object) {
		for (ViewResolver viewResolver : viewResolvers) {
			View view = viewResolver.resolveView(object);

			if (view != null) {
				return view;
			}
		}

		return null;
	}
}
