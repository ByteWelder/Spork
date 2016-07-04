package io.github.sporklibrary.resolvers;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import io.github.sporklibrary.annotations.Nullable;
import io.github.sporklibrary.interfaces.ContextResolver;

public class ContextResolverManager implements ContextResolver {
	private static ContextResolverManager instance;
	private final List<ContextResolver> contextResolvers = new ArrayList<>();

	private ContextResolverManager() {
	}

	public static ContextResolverManager shared() {
		if (instance == null) {
			instance = new ContextResolverManager();
		}

		return instance;
	}

	public void addContextResolver(ContextResolver contextResolver) {
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