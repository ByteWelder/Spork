package spork.inject.internal.objectgraph.providers;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Provider;

import spork.inject.Lazy;

public class LazyProvider<T> implements Lazy<T> {
	@Nullable
	private Provider<T> provider;
	private T cachedInstance;

	public LazyProvider(@Nonnull Provider<T> provider) {
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

