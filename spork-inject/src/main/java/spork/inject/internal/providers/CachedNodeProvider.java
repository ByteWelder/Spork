package spork.inject.internal.providers;

import javax.annotation.Nullable;
import javax.inject.Provider;

import spork.exceptions.SporkRuntimeException;
import spork.inject.internal.reflection.InjectSignature;
import spork.inject.internal.InstanceCache;
import spork.inject.internal.ObjectGraphException;
import spork.inject.internal.ObjectGraphNode;

/**
 * A provider that returns an instance from a map or otherwise creates it with
 * the given ObjectGraphNode and its arguments.
 */
public class CachedNodeProvider implements Provider<Object>, InstanceCache.Factory {
	private final ObjectGraphNode node;
	@Nullable
	private final Object[] arguments;
	private final InstanceCache instanceCache;

	/**
	 * @param node the node that contains the dependency to inject
	 * @param arguments the arguments needed to inject
	 * @param instanceCache contains all scoped and/or qualified instanced
	 */
	@SuppressWarnings("PMD.UseVarargs")
	public CachedNodeProvider(InstanceCache instanceCache, ObjectGraphNode node, @Nullable Object[] arguments) {
		this.node = node;
		this.arguments = arguments;
		this.instanceCache = instanceCache;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object get() {
		InjectSignature injectSignature = node.getInjectSignature();
		return instanceCache.getOrCreate(injectSignature, this);
	}

	@Override
	public Object create() {
		try {
			if (arguments == null) {
				return node.resolve();
			} else {
				return node.resolve(arguments);
			}
		} catch (ObjectGraphException caught) {
			throw new SporkRuntimeException("Failed to resolve provider", caught);
		}
	}
}
