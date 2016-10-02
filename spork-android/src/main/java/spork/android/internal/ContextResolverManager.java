package spork.android.internal;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import spork.android.interfaces.ContextResolver;
import spork.android.interfaces.ResolverRegistry;

public class ContextResolverManager implements ContextResolver, ResolverRegistry<ContextResolver> {
	private final List<ContextResolver> contextResolvers = new ArrayList<>(2);

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