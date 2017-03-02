package spork.inject.internal.providers;

import javax.inject.Provider;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import spork.inject.internal.ObjectGraphNode;

public class InstanceProvider implements Provider<Object> {
	private final ObjectGraphNode node;
	private final Object[] parameters;

	@SuppressFBWarnings("EI_EXPOSE_REP2")
	public InstanceProvider(ObjectGraphNode node, Object[] parameters) {
		this.node = node;
		this.parameters = parameters;
	}

	@Override
	public Object get() {
		if (parameters != null) {
			return node.resolve(parameters);
		} else {
			return node.resolve();
		}
	}
}
