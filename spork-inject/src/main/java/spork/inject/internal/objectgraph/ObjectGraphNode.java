package spork.inject.internal.objectgraph;

import javax.inject.Provider;

public interface ObjectGraphNode {
	<T> Provider<T> findProvider(ObjectGraph objectGraph, InjectSignature injectSignature);
}
