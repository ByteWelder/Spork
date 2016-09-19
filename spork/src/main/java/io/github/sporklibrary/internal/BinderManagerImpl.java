package io.github.sporklibrary.internal;


import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.sporklibrary.BinderManager;
import io.github.sporklibrary.binders.FieldBinder;
import io.github.sporklibrary.binders.MethodBinder;
import io.github.sporklibrary.binders.TypeBinder;
import io.github.sporklibrary.internal.caching.ClassBinderCache;

/**
 * The BinderManager manages all bindings and their cache.
 */
public class BinderManagerImpl implements BinderManager {
    private final List<FieldBinder<?>> fieldBinders = new ArrayList<>();
    private final List<MethodBinder<?>> methodBinders = new ArrayList<>();
    private final List<TypeBinder<?>> typeBinders = new ArrayList<>();
    private final Map<Class<?>, ClassBinderCache> classBinderCacheMap = new HashMap<>();

	/**
	 * {@inheritDoc}
	 */
	@Override
    public <AnnotationType extends Annotation> void register(FieldBinder<AnnotationType> fieldBinder) {
        fieldBinders.add(fieldBinder);

        // Update cache
        for (ClassBinderCache cache : classBinderCacheMap.values()) {
            cache.register(fieldBinder);
        }
    }

	/**
	 * {@inheritDoc}
	 */
	@Override
    public <AnnotationType extends Annotation> void register(MethodBinder<AnnotationType> methodBinder) {
        methodBinders.add(methodBinder);

        // Update cache
        for (ClassBinderCache cache : classBinderCacheMap.values()) {
            cache.register(methodBinder);
        }
    }

	/**
	 * {@inheritDoc}
	 */
	@Override
    public <AnnotationType extends Annotation> void register(TypeBinder<AnnotationType> typeBinder) {
        typeBinders.add(typeBinder);

        // Update cache
        for (ClassBinderCache cache : classBinderCacheMap.values()) {
            cache.register(typeBinder);
        }
    }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ClassBinderCache getOrCreateCache(Class<?> type) {
		ClassBinderCache cache = classBinderCacheMap.get(type);

		if (cache == null) {
			cache = createCache(type);
			classBinderCacheMap.put(type, cache);
		}

		return cache;
	}

    /**
     * Allocated the cache for the specified class
     *
     * @param classObject the class to create a cache for
     * @return the cache
     */
    private ClassBinderCache createCache(Class<?> classObject) {
        ClassBinderCache cache = new ClassBinderCache(classObject);

        for (TypeBinder<?> typeBinder : typeBinders) {
            cache.register(typeBinder);
        }

        for (FieldBinder<?> fieldBinder : fieldBinders) {
            cache.register(fieldBinder);
        }

        for (MethodBinder<?> methodBinder : methodBinders) {
            cache.register(methodBinder);
        }

        return cache;
    }
}