package spork.inject.internal.objectgraph.modulenode.providers;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Provider;

import spork.inject.Lazy;

/**
 * Wrapper around ModuleMethodInvoker.
 * It only fetches the module dependency ones and returns the same (cached) value get()
 * is called consecutively.
 * @param <T> the return type
 */
public class CachedProvider<T> implements Lazy<T> {
	@Nullable
	private Provider<T> provider;
	private T cachedInstance;

	public CachedProvider(@Nonnull Provider<T> provider) {
		this.provider = provider;
	}

	@Override
	public T get() {
		if (provider != null) {
			cachedInstance = provider.get();
			provider = null;
		}

		return cachedInstance;
	}
}

