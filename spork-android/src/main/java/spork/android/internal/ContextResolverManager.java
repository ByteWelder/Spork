package spork.android.internal;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import spork.android.interfaces.ContextResolver;
import spork.android.interfaces.Registry;

/**
 * The main {@link ContextResolver} implementation that holds child {@link ContextResolver} instances
 * and uses them one by one to resolve a {@link Context}.
 */
public class ContextResolverManager implements ContextResolver, Registry<ContextResolver> {
	private final List<ContextResolver> contextResolvers;

	public ContextResolverManager(List<ContextResolver> contextResolvers) {
		this.contextResolvers = contextResolvers;
	}

	public ContextResolverManager() {
		this(new ArrayList<ContextResolver>(2));
	}

	@Override
	public void register(ContextResolver contextResolver) {
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