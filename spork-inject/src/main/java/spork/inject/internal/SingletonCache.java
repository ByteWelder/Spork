package spork.inject.internal;

import java.util.HashMap;

public class SingletonCache {
	private final HashMap<String, Object> map = new HashMap<>();

	synchronized Object get(String key) {
		return map.get(key);
	}

	synchronized void put(String key, Object instance) {
		map.put(key, instance);
	}
}
