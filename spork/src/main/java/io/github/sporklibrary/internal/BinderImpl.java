package io.github.sporklibrary.internal;

import io.github.sporklibrary.Binder;
import io.github.sporklibrary.BinderManager;
import io.github.sporklibrary.annotations.Nullable;
import io.github.sporklibrary.internal.caching.ClassBinderCache;

public class BinderImpl implements Binder {
	private BinderManager binderManager;

	public BinderImpl(BinderManager binderManager) {
		this.binderManager = binderManager;
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
			ClassBinderCache cache = binderManager.getOrCreateCache(objectClass);
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
		for (io.github.sporklibrary.internal.interfaces.Binder binder : cache.getBinders()) {
			binder.bind(object, modules);
		}
	}
}
