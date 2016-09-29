package io.github.sporklibrary.internal;

import io.github.sporklibrary.Binder;
import io.github.sporklibrary.annotations.Nullable;
import io.github.sporklibrary.internal.caching.BinderCache;
import io.github.sporklibrary.internal.caching.ClassBinderCache;

public class BinderImpl implements Binder {
	private BinderCache binderCache;

	public BinderImpl(BinderCache binderCache) {
		this.binderCache = binderCache;
	}

	/**
	 * {@inheritDoc}
	 */
	public void bind(Object object) {
		bind(object, (Object[]) null);
	}

	/**
	 * {@inheritDoc}
	 */
	public void bind(Object object, @Nullable Object... modules) {
		Class<?> objectClass = object.getClass();

		while (objectClass != null && objectClass != Object.class) {
			ClassBinderCache cache = binderCache.getClassBinderCache(objectClass);
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
	private void bindInternal(Object object, ClassBinderCache cache, @Nullable Object[] modules) {
		for (Binder cachedBinder : cache.getCachedBinders()) {
			cachedBinder.bind(object, modules);
		}
	}
}
