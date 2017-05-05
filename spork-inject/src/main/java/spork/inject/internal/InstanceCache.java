package spork.inject.internal;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import spork.inject.internal.reflection.InjectSignature;

public class InstanceCache {
	private final Lock mapLock;
	private final Map<InjectSignature, Object> map;

	InstanceCache(Map<InjectSignature, Object> map, Lock mapLock) {
		this.map = map;
		this.mapLock = mapLock;
	}

	InstanceCache() {
		this(new HashMap<InjectSignature, Object>(), new ReentrantLock());
	}

	public Object getOrCreate(InjectSignature signature, Factory factory) {
		mapLock.lock();

		try {
			if (map.containsKey(signature)) {
				return map.get(signature);
			} else {
				Object instance = factory.create();
				map.put(signature, instance);
				return instance;
			}
		} finally {
			mapLock.unlock();
		}
	}

	public interface Factory {
		Object create();
	}
}
