package spork.inject.internal.objectgraph;

import javax.annotation.Nullable;
import javax.inject.Provider;

public interface ObjectGraphNode {
	@Nullable
	<T> Provider<T> findProvider(ObjectGraph objectGraph, InjectSignature injectSignature);
}
