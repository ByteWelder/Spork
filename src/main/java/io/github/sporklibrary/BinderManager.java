package io.github.sporklibrary;

import io.github.sporklibrary.binders.FieldBinder;
import io.github.sporklibrary.binders.MethodBinder;
import io.github.sporklibrary.binders.TypeBinder;
import io.github.sporklibrary.caching.BinderCache;
import io.github.sporklibrary.caching.ObjectBinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The BinderManager manages all bindings and their cache.
 */
public class BinderManager
{
	static final Logger sLogger = LoggerFactory.getLogger(BinderManager.class);

	private final List<FieldBinder<?>> mFieldBinders = new ArrayList<>();

	private final List<MethodBinder<?>> mMethodBinders = new ArrayList<>();

	private final List<TypeBinder<?>> mTypeBinders = new ArrayList<>();

	private final Map<Class<?>, BinderCache> mClassBinderCacheMap = new HashMap<>();

	public <AnnotationType extends Annotation> void register(FieldBinder<AnnotationType> binder)
	{
		if (sLogger.isDebugEnabled())
		{
			sLogger.debug("register {}", binder.getClass());
		}

		mFieldBinders.add(binder);

		// Update cache
		for (BinderCache cache : mClassBinderCacheMap.values())
		{
			cache.register(binder);
		}
	}

	public <AnnotationType extends Annotation> void register(MethodBinder<AnnotationType> binder)
	{
		if (sLogger.isDebugEnabled())
		{
			sLogger.debug("register {}", binder.getClass());
		}

		mMethodBinders.add(binder);

		// Update cache
		for (BinderCache cache : mClassBinderCacheMap.values())
		{
			cache.register(binder);
		}
	}

	public <AnnotationType extends Annotation> void register(TypeBinder<AnnotationType> binder)
	{
		if (sLogger.isDebugEnabled())
		{
			sLogger.debug("register {}", binder.getClass());
		}

		mTypeBinders.add(binder);

		// Update cache
		for (BinderCache cache : mClassBinderCacheMap.values())
		{
			cache.register(binder);
		}
	}

	public void bind(Object object)
	{
		if (sLogger.isDebugEnabled())
		{
			sLogger.debug("bind {}", object);
		}

		Class<?> object_class = object.getClass();

		while (object_class != null && object_class != Object.class)
		{
			BinderCache cache = mClassBinderCacheMap.get(object_class);

			if (cache == null)
			{
				cache = createCache(object_class);

				mClassBinderCacheMap.put(object_class, cache);
			}

			bind(object, cache);

			object_class = object_class.getSuperclass();
		}

	}

	private void bind(Object object, BinderCache cache)
	{
		for (ObjectBinder binder : cache.getBinders())
		{
			binder.bind(object);
		}
	}

	private BinderCache createCache(Class<?> classObject)
	{
		if (sLogger.isDebugEnabled())
		{
			sLogger.debug("createCache {}", classObject.getName());
		}

		BinderCache cache = new BinderCache(classObject);

		for (FieldBinder<?> field_binder : mFieldBinders)
		{
			cache.register(field_binder);
		}

		for (MethodBinder<?> field_binder : mMethodBinders)
		{
			cache.register(field_binder);
		}

		for (TypeBinder<?> field_binder : mTypeBinders)
		{
			cache.register(field_binder);
		}

		return cache;
	}
}