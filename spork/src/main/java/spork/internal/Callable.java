package spork.internal;

import javax.annotation.Nullable;

public interface Callable<T> {
	@Nullable T call();
}
