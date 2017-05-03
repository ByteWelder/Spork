package spork.inject.internal.providers;

import javax.inject.Provider;

import spork.exceptions.SporkRuntimeException;
import spork.inject.internal.ObjectGraphException;
import spork.inject.internal.ObjectGraphNode;

public class NodeProvider implements Provider<Object> {
	private final ObjectGraphNode node;
	private final Object[] parameters;

	public NodeProvider(ObjectGraphNode node, Object... parameters) {
		this.node = node;
		this.parameters = parameters;
	}

	@Override
	public Object get() {
		try {
			if (parameters == null) {
				return node.resolve();
			} else {
				return node.resolve(parameters);
			}
		} catch (ObjectGraphException caught) {
			throw new SporkRuntimeException("Failed to resolve provider", caught, caught.getBindContext());
		}
	}
}
