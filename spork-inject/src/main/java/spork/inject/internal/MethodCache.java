package spork.inject.internal;

import java.lang.reflect.Method;
import java.util.HashMap;

import javax.annotation.Nullable;

public class MethodCache {
	private final HashMap<String, Method> map = new HashMap<>();

	@Nullable
	synchronized Method get(String key) {
		return map.get(key);
	}

	synchronized void put(String key, Method method) {
		map.put(key, method);
	}
}
