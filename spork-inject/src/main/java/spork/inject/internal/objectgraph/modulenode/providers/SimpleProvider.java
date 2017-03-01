package spork.inject.internal.objectgraph.modulenode.providers;

import javax.inject.Provider;

import spork.inject.internal.objectgraph.modulenode.ModuleMethodInvoker;

/**
 * Wrapper around ModuleMethodInvoker.
 * It fetches the module dependency every time get() is called.
 * @param <T> the return type
 */
public class SimpleProvider<T> implements Provider<T> {
	private final ModuleMethodInvoker<T> moduleMethodInvoker;

	public SimpleProvider(ModuleMethodInvoker<T> moduleMethodInvoker) {
		this.moduleMethodInvoker = moduleMethodInvoker;
	}

	@Override
	public T get() {
		return moduleMethodInvoker.invoke();
	}
}
