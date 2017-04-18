package spork.android.internal;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import spork.android.extension.ViewResolver;

/**
 * The main {@link ViewResolver} implementation that holds child {@link ViewResolver} instances
 * and uses them one by one to resolve a {@link View}.
 */
public class ViewResolverManager implements ViewResolver {
	private final List<ViewResolver> viewResolvers;

	public ViewResolverManager(List<ViewResolver> contextResolvers) {
		this.viewResolvers = contextResolvers;
	}

	public ViewResolverManager() {
		this(new ArrayList<ViewResolver>(2));
	}

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
