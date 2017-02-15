package spork.inject.internal.objectgraph.modulenode;

import javax.inject.Inject;
import javax.inject.Provider;

import spork.exceptions.BindException;
import spork.inject.internal.InjectSignature;

public class SingletonInstanceProvider<T> implements Provider<T> {
	private final InjectSignature injectSignature;
	private final ModuleMethodInvoker<T> moduleMethodInvoker;
	private final ScopedInstanceCache scopedInstanceCache;

	SingletonInstanceProvider(InjectSignature injectSignature, ModuleMethodInvoker<T> moduleMethodInvoker, ScopedInstanceCache scopedInstanceCache) {
		this.injectSignature = injectSignature;
		this.moduleMethodInvoker = moduleMethodInvoker;
		this.scopedInstanceCache = scopedInstanceCache;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get() {
		synchronized (scopedInstanceCache) {
			Object instance = scopedInstanceCache.get(injectSignature);

			if (instance == null) {
				instance = moduleMethodInvoker.invoke();

				if (instance == null) {
					String message = "singletons are not nullable but is null at " + moduleMethodInvoker.getModule().getClass().getName()
							+ "." + moduleMethodInvoker.getMethod().getName() + "()";
					throw new BindException(Inject.class, moduleMethodInvoker.getModule().getClass(), message);
				}

				scopedInstanceCache.put(injectSignature, instance);
			}

			return (T) instance;
		}
	}
}
