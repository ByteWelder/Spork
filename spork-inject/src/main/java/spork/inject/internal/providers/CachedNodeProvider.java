package spork.inject.internal.providers;

import java.util.Map;

import javax.annotation.Nullable;
import javax.inject.Provider;

import spork.exceptions.SporkRuntimeException;
import spork.inject.internal.InjectSignature;
import spork.inject.internal.ObjectGraphException;
import spork.inject.internal.ObjectGraphNode;

/**
 * A provider that returns an instance from a map or otherwise creates it with
 * the given ObjectGraphNode and its arguments.
 */
public class CachedNodeProvider implements Provider<Object> {
	private final ObjectGraphNode node;
	@Nullable
	private final Object[] arguments;
	private final Map<InjectSignature, Object> instanceMap;

	/**
	 * @param node the node that contains the dependency to inject
	 * @param arguments the arguments needed to inject
	 * @param instanceMap the instance map within the correct scope to store the instance in
	 */
	@SuppressWarnings("PMD.UseVarargs")
	public CachedNodeProvider(Map<InjectSignature, Object> instanceMap, ObjectGraphNode node, @Nullable Object[] arguments) {
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
		try {
			if (arguments == null) {
				return node.resolve();
			} else {
				return node.resolve(arguments);
			}
		} catch (ObjectGraphException caught) {
			throw new SporkRuntimeException("failed to resolve provider", caught, caught.getBindContext());
		}
	}
}
