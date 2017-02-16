package spork.inject.internal.objectgraph.modulenode;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import spork.inject.internal.InjectSignature;

/**
 * A 2-dimensional cache that maps as follows:
 *
 *   scope -> injection signature -> instance
 *
 */
public class InstanceCache {
	private final Map<String, Map<InjectSignature, Object>> scopeMap = new HashMap<>();

	public void put(String scope, InjectSignature injectSignature, Object instance) {
		getOrCreateInjectSignatureMap(scope).put(injectSignature, instance);
	}

	@Nullable
	public Object get(String scope, InjectSignature injectSignature) {
		Map<InjectSignature, Object> map = scopeMap.get(scope);

		if (map == null) {
			return null;
		}

		return map.get(injectSignature);
	}

	private Map<InjectSignature, Object> getOrCreateInjectSignatureMap(String scope) {
		Map<InjectSignature, Object> map = scopeMap.get(scope);

		if (map == null) {
			map = new HashMap<>(2);
			scopeMap.put(scope, map);
		}

		return map;
	}
}
