package spork.android.internal;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import spork.android.extension.ViewResolver;

/**
 * A {@link ViewResolver} implementation that can hold multiple child {@link ViewResolver} instances
 * and use them one by one to resolve a {@link View}.
 */
public final class CompoundViewResolver implements ViewResolver {
	private final List<ViewResolver> viewResolvers;

	public CompoundViewResolver(List<ViewResolver> contextResolvers) {
		this.viewResolvers = contextResolvers;
	}

	public CompoundViewResolver() {
		this(new ArrayList<ViewResolver>(2));
	}

	public void add(ViewResolver viewResolver) {
		viewResolvers.add(viewResolver);
	}

	@Override
	@Nullable
	public View resolveView(Object object) throws Exception {
		for (ViewResolver viewResolver : viewResolvers) {
			View view = viewResolver.resolveView(object);

			if (view != null) {
				return view;
			}
		}

		return null;
	}
}
