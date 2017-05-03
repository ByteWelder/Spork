package spork.internal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BindActionCache {
	private final Map<Class<?>, List<BindAction>> map;
	private final Lock mapLock;

	public BindActionCache(Map<Class<?>, List<BindAction>> map, Lock mapLock) {
		this.map = map;
		this.mapLock = mapLock;
	}

	@SuppressWarnings("PMD.UseConcurrentHashMap")
	public BindActionCache() {
		this(new HashMap<Class<?>, List<BindAction>>(), new ReentrantLock());
	}

	public List<BindAction> getOrCreate(Class<?> type, Factory factory) {
		mapLock.lock();

		try {
			List<BindAction> binderList = map.get(type);

			// If no cache exists, create cache
			if (binderList == null) {
				binderList = factory.create(type);
				map.put(type, binderList);
			}

			return binderList;
		} finally {
			mapLock.unlock();
		}
	}

	public interface Factory {
		List<BindAction> create(Class<?> type);
	}
}
