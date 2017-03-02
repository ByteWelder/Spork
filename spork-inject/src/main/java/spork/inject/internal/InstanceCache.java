package spork.inject.internal;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

/**
 * A 2-dimensional cache that maps as follows:
 *
 *   scope identifier -&gt; injection signature -&gt; instance
 *
 */
public class InstanceCache {
	private final Map<String, Map<InjectSignature, Object>> scopeMap = new HashMap<>();

	public void put(String scope, InjectSignature injectSignature, Object instance) {
		getOrCreateInjectSignatureMap(scope).put(injectSignature, instance);
	}

	@Nullable
	public Object get(@Nullable String scopeIdentifier, InjectSignature injectSignature) {
		Map<InjectSignature, Object> map = scopeMap.get(scopeIdentifier);

		if (map == null) {
			return null;
		}

		return map.get(injectSignature);
	}

	private Map<InjectSignature, Object> getOrCreateInjectSignatureMap(@Nullable String scopeIdentifier) {
		Map<InjectSignature, Object> map = scopeMap.get(scopeIdentifier);

		if (map == null) {
			map = new HashMap<>(2);
			scopeMap.put(scopeIdentifier, map);
		}

		return map;
	}
}
