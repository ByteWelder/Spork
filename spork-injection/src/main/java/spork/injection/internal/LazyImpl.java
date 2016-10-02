package spork.injection.internal;

import javax.annotation.Nullable;

import spork.injection.Lazy;
import spork.internal.Callable;

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
