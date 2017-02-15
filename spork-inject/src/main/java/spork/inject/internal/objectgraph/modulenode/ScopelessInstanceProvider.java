package spork.inject.internal.objectgraph.modulenode;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Provider;

/**
 * Wrapper around ModuleMethodInvoker that caches the given value the first time it is retrieved.
 * @param <T>
 */
class ScopelessInstanceProvider<T> implements Provider<T> {
	@Nullable
	private ModuleMethodInvoker<T> moduleMethodInvoker;
	private T cachedInstance;

	ScopelessInstanceProvider(@Nonnull ModuleMethodInvoker<T> moduleMethodInvoker) {
		this.moduleMethodInvoker = moduleMethodInvoker;
	}

	@Override
	public T get() {
		if (moduleMethodInvoker != null) {
			cachedInstance = moduleMethodInvoker.invoke();
			moduleMethodInvoker = null;
		}

		return cachedInstance;
	}
}
