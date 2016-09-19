package io.github.sporklibrary.internal;


import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.sporklibrary.BinderManager;
import io.github.sporklibrary.annotations.Nullable;
import io.github.sporklibrary.binders.FieldBinder;
import io.github.sporklibrary.binders.MethodBinder;
import io.github.sporklibrary.binders.TypeBinder;
import io.github.sporklibrary.internal.caching.BinderCache;
import io.github.sporklibrary.internal.interfaces.ObjectBinder;

/**
 * The BinderManager manages all bindings and their cache.
 */
public class BinderManagerImpl implements BinderManager {
    private final List<FieldBinder<?>> fieldBinders = new ArrayList<>();
    private final List<MethodBinder<?>> methodBinders = new ArrayList<>();
    private final List<TypeBinder<?>> typeBinders = new ArrayList<>();
    private final Map<Class<?>, BinderCache> classBinderCacheMap = new HashMap<>();

	/**
	 * {@inheritDoc}
	 */
    public <AnnotationType extends Annotation> void register(FieldBinder<AnnotationType> fieldBinder) {
        fieldBinders.add(fieldBinder);

        // Update cache
        for (BinderCache cache : classBinderCacheMap.values()) {
            cache.register(fieldBinder);
        }
    }

	/**
	 * {@inheritDoc}
	 */
    public <AnnotationType extends Annotation> void register(MethodBinder<AnnotationType> methodBinder) {
        methodBinders.add(methodBinder);

        // Update cache
        for (BinderCache cache : classBinderCacheMap.values()) {
            cache.register(methodBinder);
        }
    }

	/**
	 * {@inheritDoc}
	 */
    public <AnnotationType extends Annotation> void register(TypeBinder<AnnotationType> typeBinder) {
        typeBinders.add(typeBinder);

        // Update cache
        for (BinderCache cache : classBinderCacheMap.values()) {
            cache.register(typeBinder);
        }
    }

	/**
	 * {@inheritDoc}
	 */
    public void bind(Object object) {
        bind(object, (Object[])null);
    }

	/**
	 * {@inheritDoc}
	 */
    public void bind(Object object, @Nullable Object... modules) {
        Class<?> objectClass = object.getClass();

        while (objectClass != null && objectClass != Object.class) {
            BinderCache cache = classBinderCacheMap.get(objectClass);

            if (cache == null) {
                cache = createCache(objectClass);
                classBinderCacheMap.put(objectClass, cache);
            }

            bindInternal(object, cache, modules);

            objectClass = objectClass.getSuperclass();
        }
    }

    /**
     * Bind all annotations for an object instance for one specific class (one level of
     * inheritance).
     *
     * @param object the instance to bind annotations for
     * @param cache  the cache to bind with
     * @param modules either null or an array of non-null modules
     */
    private void bindInternal(Object object, BinderCache cache, @Nullable Object[] modules) {
        for (ObjectBinder binder : cache.getBinders()) {
            binder.bind(object, modules);
        }
    }

    /**
     * Allocated the cache for the specified class
     *
     * @param classObject the class to create a cache for
     * @return the cache
     */
    private BinderCache createCache(Class<?> classObject) {
        BinderCache cache = new BinderCache(classObject);

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