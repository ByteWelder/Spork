package spork.android.internal;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import spork.android.extension.FragmentResolver;

/**
 * The main {@link FragmentResolver} implementation that holds child {@link FragmentResolver} instances
 * and uses them one by one to resolve a Fragment.
 * It resolves to {@link Object} because the Fragment might be a regular Android one or a support library one.
 */
public class FragmentResolverManager implements FragmentResolver {
	private final List<FragmentResolver> fragmentResolvers;

	public FragmentResolverManager(List<FragmentResolver> fragmentResolvers) {
		this.fragmentResolvers = fragmentResolvers;
	}

	public FragmentResolverManager() {
		this(new ArrayList<FragmentResolver>(2));
	}

	public void register(FragmentResolver fragmentResolver) {
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
