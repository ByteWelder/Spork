package spork.inject.internal.objectgraph.nodes;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Provider;

/**
 * Wrapper around MethodInvoker that caches the given value the first time it is retrieved.
 * @param <T>
 */
class ScopelessInstanceProvider<T> implements Provider<T> {
	@Nullable
	private MethodInvoker<T> methodInvoker;
	private T cachedInstance;

	ScopelessInstanceProvider(@Nonnull MethodInvoker<T> methodInvoker) {
		this.methodInvoker = methodInvoker;
	}

	@Override
	public T get() {
		if (methodInvoker != null) {
			cachedInstance = methodInvoker.invoke();
			methodInvoker = null;
		}

		return cachedInstance;
	}
}
