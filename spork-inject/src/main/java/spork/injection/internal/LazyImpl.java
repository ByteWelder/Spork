package spork.injection.internal;

import spork.injection.Lazy;
import spork.internal.Callable;

/**
 * {@link Callable} implementation for {@link Lazy}
 * @param <T> the type that can be lazily retrieved
 */
class LazyImpl<T> implements Lazy<T> {
	private Callable<T> callable;

	LazyImpl(Callable<T> callable) {
		this.callable = callable;
	}

	@Override
	public T get() {
		return callable.call();
	}
}
