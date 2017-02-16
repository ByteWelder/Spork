package spork.inject.internal.objectgraph.modulenode.providers;

import java.lang.annotation.Annotation;

import javax.annotation.Nullable;
import javax.inject.Provider;

import spork.inject.internal.InjectSignature;
import spork.inject.internal.objectgraph.modulenode.InstanceCache;
import spork.inject.internal.objectgraph.modulenode.ModuleMethodInvoker;
import spork.inject.internal.objectgraph.modulenode.Scopes;

/**
 * Wrapper around ModuleMethodInvoker that caches/retrieves the given value in the provided InstanceCache.
 * The InstanceCacheProvider works with Provider instances that are scoped and/or qualified.
 * (e.g. annotated with @Singleton and/or @Named)
 * @param <T> the return type
 */
public class InstanceCacheProvider<T> implements Provider<T> {
	private final InjectSignature injectSignature;
	private final ModuleMethodInvoker<T> moduleMethodInvoker;
	private final InstanceCache instanceCache;
	@Nullable
	private Annotation scopeAnnotation;

	public InstanceCacheProvider(InjectSignature injectSignature,
	                      ModuleMethodInvoker<T> moduleMethodInvoker,
	                      InstanceCache instanceCache,
	                      @Nullable Annotation scopeAnnotation) {
		this.injectSignature = injectSignature;
		this.moduleMethodInvoker = moduleMethodInvoker;
		this.instanceCache = instanceCache;
		this.scopeAnnotation = scopeAnnotation;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get() {
		synchronized (instanceCache) {
			String scopeName = (scopeAnnotation != null) ? Scopes.getName(scopeAnnotation) : null;
			Object instance = instanceCache.get(scopeName, injectSignature);

			if (instance == null) {
				instance = moduleMethodInvoker.invoke();

				instanceCache.put(scopeName, injectSignature, instance);
			}

			return (T) instance;
		}
	}
}
