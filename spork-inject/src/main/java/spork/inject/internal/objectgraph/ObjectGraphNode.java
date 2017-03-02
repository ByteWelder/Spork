package spork.inject.internal.objectgraph;

import javax.annotation.Nullable;

import spork.inject.internal.InjectSignature;

public interface ObjectGraphNode {
	InjectSignature getInjectSignature();
	@Nullable String getScopeId();
	Object resolve(Object... arguments);
	@Nullable Object[] collectParameters(ObjectGraph objectGraph);
}
