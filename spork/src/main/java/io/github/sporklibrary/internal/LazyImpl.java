package io.github.sporklibrary.internal;

import io.github.sporklibrary.Lazy;
import io.github.sporklibrary.annotations.Nullable;

public class LazyImpl<T> implements Lazy<T> {
	private @Nullable T value;
	private Callable<T> callable;

	public LazyImpl(Callable<T> callable) {
		this.callable = callable;
	}

	@Override
	public T get() {
		if (callable != null) {
			value = callable.call();
			callable = null;
		}

		return value;
	}
}
