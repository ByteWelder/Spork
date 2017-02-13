package spork.inject.internal.objectgraph;

import javax.inject.Provider;

public interface ObjectGraphNode {
	<T> Provider<T> findProvider(InjectSignature injectSignature);
}
