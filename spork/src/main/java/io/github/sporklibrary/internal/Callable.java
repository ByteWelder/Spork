package io.github.sporklibrary.internal;

import io.github.sporklibrary.annotations.Nullable;

public interface Callable<T> {
	@Nullable T call();
}
