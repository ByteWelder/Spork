package spork.inject.internal.objectgraph.providers;

import javax.annotation.Nullable;
import javax.inject.Provider;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import spork.inject.internal.InjectSignature;
import spork.inject.internal.objectgraph.ObjectGraphNode;
import spork.inject.internal.objectgraph.InstanceCache;

public class InstanceCacheProvider implements Provider<Object> {
	private final ObjectGraphNode node;
	@Nullable
	private final Object[] arguments;
	private final InstanceCache instanceCache;

	@SuppressFBWarnings("EI_EXPOSE_REP2")
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
		if (arguments != null) {
			return node.resolve(arguments);
		} else {
			return node.resolve();
		}
	}
}
