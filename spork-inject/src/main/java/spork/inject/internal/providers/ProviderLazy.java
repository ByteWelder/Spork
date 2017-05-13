package spork.inject.internal.providers;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Provider;

import spork.inject.Lazy;

/**
 * A {@link Lazy} implementation that wraps a Provider and caches its value when get() is called.
 *
 * @param <T> the lazily computed type
 */
public final class ProviderLazy<T> implements Lazy<T> {
	@Nullable
	private Provider<T> provider;
	private T cachedInstance;

	public ProviderLazy(@Nonnull Provider<T> provider) {
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

