package io.github.sporklibrary.android.internal;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import io.github.sporklibrary.android.interfaces.ResolverRegistry;
import io.github.sporklibrary.android.interfaces.ContextResolver;
import io.github.sporklibrary.annotations.Nullable;

public class ContextResolverManager implements ContextResolver, ResolverRegistry<ContextResolver> {
	private final List<ContextResolver> contextResolvers = new ArrayList<>(2);

	public void register(ContextResolver contextResolver) {
		contextResolvers.add(contextResolver);
	}

	@Override
	public @Nullable Context resolveContext(Object object) {
		for (ContextResolver contextResolver : contextResolvers) {
			Context context = contextResolver.resolveContext(object);

			if (context != null) {
				return context;
			}
		}

		return null;
	}
}