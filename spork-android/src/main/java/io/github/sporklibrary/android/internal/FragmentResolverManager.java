package io.github.sporklibrary.android.internal;

import java.util.ArrayList;
import java.util.List;

import io.github.sporklibrary.android.interfaces.FragmentResolver;
import io.github.sporklibrary.annotations.Nullable;

public class FragmentResolverManager implements FragmentResolver, ResolverRegistry<FragmentResolver> {
	private final List<FragmentResolver> fragmentResolvers = new ArrayList<>(2);

	public void register(FragmentResolver fragmentResolver) {
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
