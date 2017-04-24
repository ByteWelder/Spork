package spork.inject.internal.providers;

import javax.inject.Provider;

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
		if (parameters == null) {
			return node.resolve();
		} else {
			return node.resolve(parameters);
		}
	}
}
