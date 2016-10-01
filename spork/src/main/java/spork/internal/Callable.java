package spork.internal;

import spork.annotations.Nullable;

public interface Callable<T> {
	@Nullable T call();
}
