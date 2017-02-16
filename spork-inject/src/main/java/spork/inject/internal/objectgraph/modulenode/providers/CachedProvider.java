package spork.inject.internal.objectgraph.modulenode.providers;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Provider;

import spork.inject.internal.objectgraph.modulenode.ModuleMethodInvoker;

/**
 * Wrapper around ModuleMethodInvoker that caches the given value the first time it is retrieved.
 * @param <T> the return type
 */
public class CachedProvider<T> implements Provider<T> {
	@Nullable
	private ModuleMethodInvoker<T> moduleMethodInvoker;
	private T cachedInstance;

	public CachedProvider(@Nonnull ModuleMethodInvoker<T> moduleMethodInvoker) {
		this.moduleMethodInvoker = moduleMethodInvoker;
	}

	@Override
	public T get() {
		if (moduleMethodInvoker != null) {
			cachedInstance = moduleMethodInvoker.invoke();
			moduleMethodInvoker = null;
		}

		return cachedInstance;
	}
}
