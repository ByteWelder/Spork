package spork.inject.internal.objectgraph.nodes;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import spork.inject.internal.objectgraph.InjectSignature;

public class ScopedInstanceCache {
	private final Map<InjectSignature, Object> map = new HashMap<>();

	public void put(InjectSignature injectSignature, Object instance) {
		map.put(injectSignature, instance);
	}

	@Nullable
	public Object get(InjectSignature injectSignature) {
		return map.get(injectSignature);
	}
}
