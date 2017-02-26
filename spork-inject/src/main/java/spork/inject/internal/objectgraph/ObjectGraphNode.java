package spork.inject.internal.objectgraph;

import javax.annotation.Nullable;
import javax.inject.Provider;

import spork.inject.internal.InjectSignature;

public interface ObjectGraphNode {
	@Nullable
	<T> Provider<T> findProvider(ObjectGraph objectGraph, InjectSignature injectSignature);
}
