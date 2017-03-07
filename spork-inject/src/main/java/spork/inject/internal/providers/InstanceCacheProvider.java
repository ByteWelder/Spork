package spork.inject.internal.providers;

import javax.annotation.Nullable;
import javax.inject.Provider;

import spork.inject.internal.InjectSignature;
import spork.inject.internal.InstanceCache;
import spork.inject.internal.ObjectGraphNode;

public class InstanceCacheProvider implements Provider<Object> {
	private final ObjectGraphNode node;
	@Nullable
	private final Object[] arguments;
	private final InstanceCache instanceCache;

	public InstanceCacheProvider(ObjectGraphNode node, @Nullable Object[] arguments, InstanceCache instanceCache) {
		this.node = node;
		this.arguments = arguments;
		this.instanceCache = instanceCache;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object get() {
		synchronized (instanceCache) {
			InjectSignature injectSignature = node.getInjectSignature();
			@Nullable String scope = node.getScopeId();
			// try to find the instance in the cache first
			Object instance = instanceCache.get(scope, injectSignature);

			// if we don't have an instance, create it and cache it
			if (instance == null) {
				instance = invoke();
				instanceCache.put(scope, injectSignature, instance);
			}

			return instance;
		}
	}

	private Object invoke() {
		if (arguments == null) {
			return node.resolve();
		} else {
			return node.resolve(arguments);
		}
	}
}
