package spork.inject.internal;

import javax.annotation.Nullable;
import javax.inject.Provider;

import spork.internal.Callable;

/**
 * {@link Callable} implementation for {@link Provider}
 * @param <T> the type that can be lazily retrieved
 */
class CallableProvider<T> implements Provider<T> {
	@Nullable private T value;
	private final Callable<T> callable;
	private boolean isCached = false;

	CallableProvider(Callable<T> callable) {
		this.callable = callable;
	}

	@Override
	public synchronized T get() {
		if (!isCached) {
			value = callable.call();
			isCached = true;
		}

		return value;
	}
}
