package io.github.sporklibrary.internal;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import io.github.sporklibrary.binders.FieldBinder;
import io.github.sporklibrary.binders.MethodBinder;
import io.github.sporklibrary.binders.TypeBinder;
import io.github.sporklibrary.internal.caching.ClassBinderCache;

public class BinderCacheImpl implements BinderCache {
	private final Map<Class<?>, ClassBinderCache> classBinderCacheMap = new HashMap<>();
	private final BinderManager binderManager;

	public BinderCacheImpl(BinderManager binderManager) {
		this.binderManager = binderManager;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ClassBinderCache getClassBinderCache(Class<?> type) {
		ClassBinderCache cache = classBinderCacheMap.get(type);

		if (cache == null) {
			cache = createCache(type);
			classBinderCacheMap.put(type, cache);
		}

		return cache;
	}

	@Override
	public <AnnotationType extends Annotation> void cache(FieldBinder<AnnotationType> fieldBinder) {
		// Update cache
		for (ClassBinderCache cache : classBinderCacheMap.values()) {
			cache.register(fieldBinder);
		}
	}

	@Override
	public <AnnotationType extends Annotation> void cache(MethodBinder<AnnotationType> methodBinder) {
		// Update cache
		for (ClassBinderCache cache : classBinderCacheMap.values()) {
			cache.register(methodBinder);
		}
	}

	@Override
	public <AnnotationType extends Annotation> void cache(TypeBinder<AnnotationType> typeBinder) {
		// Update cache
		for (ClassBinderCache cache : classBinderCacheMap.values()) {
			cache.register(typeBinder);
		}
	}

	/**
	 * Allocated the cache for the specified class
	 *
	 * @param classObject the class to create a cache for
	 * @return the cache
	 */
	private ClassBinderCache createCache(Class<?> classObject) {
		ClassBinderCache cache = new ClassBinderCache(classObject);

		for (TypeBinder<?> typeBinder : binderManager.getTypeBinders()) {
			cache.register(typeBinder);
		}

		for (FieldBinder<?> fieldBinder : binderManager.getFieldBinders()) {
			cache.register(fieldBinder);
		}

		for (MethodBinder<?> methodBinder : binderManager.getMethodBinders()) {
			cache.register(methodBinder);
		}

		return cache;
	}
}
