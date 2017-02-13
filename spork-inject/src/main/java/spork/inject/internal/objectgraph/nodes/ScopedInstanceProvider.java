package spork.inject.internal.objectgraph.nodes;

import javax.inject.Provider;

import spork.inject.internal.objectgraph.InjectSignature;

public class ScopedInstanceProvider<T> implements Provider<T> {
	private final InjectSignature injectSignature;
	private final MethodInvoker<T> methodInvoker;
	private final ScopedInstanceCache scopedInstanceCache;

	public ScopedInstanceProvider(InjectSignature injectSignature, MethodInvoker<T> methodInvoker, ScopedInstanceCache scopedInstanceCache) {
		this.injectSignature = injectSignature;
		this.methodInvoker = methodInvoker;
		this.scopedInstanceCache = scopedInstanceCache;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get() {
		synchronized (scopedInstanceCache) {
			T instance = (T) scopedInstanceCache.get(injectSignature);

			if (instance == null) {
				instance = methodInvoker.invoke();
				scopedInstanceCache.put(injectSignature, instance);
			}

			return instance;
		}
	}
}
