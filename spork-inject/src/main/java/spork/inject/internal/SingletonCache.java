package spork.inject.internal;

import java.util.HashMap;

import javax.annotation.Nullable;

public class SingletonCache {
	private final HashMap<String, Object> map = new HashMap<>();

	@Nullable
	synchronized Object get(String key) {
		return map.get(key);
	}

	synchronized void put(String key, Object instance) {
		map.put(key, instance);
	}
}
