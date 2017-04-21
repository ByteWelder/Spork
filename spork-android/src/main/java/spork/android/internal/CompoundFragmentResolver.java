package spork.android.internal;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import spork.android.extension.FragmentResolver;

/**
 * A {@link FragmentResolver} implementation that can hold multiple child {@link FragmentResolver} instances
 * and use them one by one to resolve a Fragment.
 * It resolves to {@link Object} because the Fragment might be a regular Android one or a support library one.
 */
public class CompoundFragmentResolver implements FragmentResolver {
	private final List<FragmentResolver> fragmentResolvers;

	public CompoundFragmentResolver(List<FragmentResolver> fragmentResolvers) {
		this.fragmentResolvers = fragmentResolvers;
	}

	public CompoundFragmentResolver() {
		this(new ArrayList<FragmentResolver>(2));
	}

	public void add(FragmentResolver fragmentResolver) {
		fragmentResolvers.add(fragmentResolver);
	}

	@Override
	@Nullable
	public Object resolveFragment(Object parent, int id) {
		for (FragmentResolver fragmentResolver : fragmentResolvers) {
			Object fragment = fragmentResolver.resolveFragment(parent, id);

			if (fragment != null) {
				return fragment;
			}
		}

		return null;
	}

	@Override
	@Nullable
	public Object resolveFragment(Object parent, String idName) {
		for (FragmentResolver fragmentResolver : fragmentResolvers) {
			Object fragment = fragmentResolver.resolveFragment(parent, idName);

			if (fragment != null) {
				return fragment;
			}
		}

		return null;
	}
}
