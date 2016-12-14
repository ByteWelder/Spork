package spork.inject.internal;

import javax.annotation.Nullable;

import spork.inject.Lazy;
import spork.internal.Callable;

/**
 * {@link Callable} implementation for {@link Lazy}
 * @param <T> the type that can be lazily retrieved
 */
class LazyImpl<T> implements Lazy<T> {
	@Nullable private T value;
	private final Callable<T> callable;
	private boolean isCached = false;

	LazyImpl(Callable<T> callable) {
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
