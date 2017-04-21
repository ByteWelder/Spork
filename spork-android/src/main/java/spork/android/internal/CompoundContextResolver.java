package spork.android.internal;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import spork.android.extension.ContextResolver;

/**
 * A {@link ContextResolver} implementation that can hold multiple {@link ContextResolver} instances
 * and use them one by one to resolve a {@link Context}.
 */
public class CompoundContextResolver implements ContextResolver {
	private final List<ContextResolver> contextResolvers;

	public CompoundContextResolver(List<ContextResolver> contextResolvers) {
		this.contextResolvers = contextResolvers;
	}

	public CompoundContextResolver() {
		this(new ArrayList<ContextResolver>(2));
	}

	public void add(ContextResolver contextResolver) {
		contextResolvers.add(contextResolver);
	}

	@Override
	@Nullable
	public Context resolveContext(Object object) {
		for (ContextResolver contextResolver : contextResolvers) {
			Context context = contextResolver.resolveContext(object);

			if (context != null) {
				return context;
			}
		}

		return null;
	}
}