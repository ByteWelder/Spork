package spork.inject.internal.providers;

import java.util.Map;

import javax.annotation.Nullable;
import javax.inject.Provider;

import spork.inject.internal.InjectSignature;
import spork.inject.internal.ObjectGraphNode;

public class InstanceCacheProvider implements Provider<Object> {
	private final ObjectGraphNode node;
	@Nullable
	private final Object[] arguments;
	private final Map<InjectSignature, Object> instanceMap;

	/**
	 * @param node the node that contains the dependency to inject
	 * @param arguments the arguments needed to inject
	 * @param instanceMap the instance map within the correct scope to store the instance in
	 */
	public InstanceCacheProvider(ObjectGraphNode node, @Nullable Object[] arguments, Map<InjectSignature, Object> instanceMap) {
		this.node = node;
		this.arguments = arguments;
		this.instanceMap = instanceMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object get() {
		synchronized (instanceMap) {
			InjectSignature injectSignature = node.getInjectSignature();
			// try to find the instance in the cache first
			Object instance = instanceMap.get(injectSignature);

			// if we don't have an instance, create it and cache it
			if (instance == null) {
				instance = invoke();
				instanceMap.put(injectSignature, instance);
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
