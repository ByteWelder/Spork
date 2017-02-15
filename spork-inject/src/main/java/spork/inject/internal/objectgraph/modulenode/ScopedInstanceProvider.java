package spork.inject.internal.objectgraph.modulenode;

import javax.inject.Provider;

import spork.inject.internal.InjectSignature;

public class ScopedInstanceProvider<T> implements Provider<T> {
	private final InjectSignature injectSignature;
	private final ModuleMethodInvoker<T> moduleMethodInvoker;
	private final ScopedInstanceCache scopedInstanceCache;

	public ScopedInstanceProvider(InjectSignature injectSignature, ModuleMethodInvoker<T> moduleMethodInvoker, ScopedInstanceCache scopedInstanceCache) {
		this.injectSignature = injectSignature;
		this.moduleMethodInvoker = moduleMethodInvoker;
		this.scopedInstanceCache = scopedInstanceCache;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get() {
		synchronized (scopedInstanceCache) {
			T instance = (T) scopedInstanceCache.get(injectSignature);

			if (instance == null) {
				instance = moduleMethodInvoker.invoke();
				scopedInstanceCache.put(injectSignature, instance);
			}

			return instance;
		}
	}
}
