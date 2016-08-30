package io.github.sporklibrary.android.resolvers;

import java.util.ArrayList;
import java.util.List;

import io.github.sporklibrary.annotations.Nullable;

public class FragmentResolverManager implements FragmentResolver {

	private static FragmentResolverManager instance;

	private FragmentResolverManager() {
	}

	public static FragmentResolverManager shared() {
		if (instance == null) {
			instance = new FragmentResolverManager();
		}

		return instance;
	}

	private final List<FragmentResolver> fragmentResolvers = new ArrayList<>();

	public void addFragmentResolver(FragmentResolver fragmentResolver) {
		fragmentResolvers.add(fragmentResolver);
	}

	@Override
	public @Nullable Object resolveFragment(Object parent, int id) {
		for (FragmentResolver fragmentResolver : fragmentResolvers) {
			Object fragment = fragmentResolver.resolveFragment(parent, id);

			if (fragment != null) {
				return fragment;
			}
		}

		return null;
	}

	@Override
	public @Nullable Object resolveFragment(Object parent, String idName) {
		for (FragmentResolver fragmentResolver : fragmentResolvers) {
			Object fragment = fragmentResolver.resolveFragment(parent, idName);

			if (fragment != null) {
				return fragment;
			}
		}

		return null;
	}
}
