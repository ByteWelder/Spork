package spork.inject.internal;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class InstanceCache {
	private final Lock lock;
	@SuppressWarnings("PMD.UseConcurrentHashMap")
	private final Map<spork.inject.internal.reflection.InjectSignature, Object> map = new HashMap<>();

	InstanceCache(Lock lock) {
		this.lock = lock;
	}

	InstanceCache() {
		this(new ReentrantLock());
	}

	public Object getOrCreate(spork.inject.internal.reflection.InjectSignature signature, Factory factory) {
		lock.lock();

		try {
			if (map.containsKey(signature)) {
				return map.get(signature);
			} else {
				Object instance = factory.create();
				map.put(signature, instance);
				return instance;
			}
		} finally {
			lock.unlock();
		}
	}

	public interface Factory {
		Object create();
	}
}
