package spork.inject.internal;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import spork.exceptions.BindException;

class InstanceProvider<T> implements Provider<T> {
	private Field field;
	private Object parent;
	private Method method;
	private SingletonCache singletonCache;
	private T cachedInstance;

	InstanceProvider(Field field, Object parent, Method method, SingletonCache singletonCache) {
		this.field = field;
		this.parent = parent;
		this.method = method;
		this.singletonCache = singletonCache;
	}

	@Override
	public T get() {
		if (cachedInstance == null) {
			cachedInstance = resolveInstance();
			verifyInstance(cachedInstance);
		}

		return cachedInstance;
	}

	@SuppressWarnings("unchecked")
	private T resolveInstance() {
		boolean isSingleton = method.getAnnotation(Singleton.class) != null;

		if (!isSingleton) {
			return invokeMethod();
		} else {
			String typeName = method.getReturnType().getName();
			Object instance = singletonCache.get(typeName);

			if (instance == null) {
				instance = invokeMethod();
				singletonCache.put(typeName, instance);
			}

			return (T)instance;
		}
	}

	private void verifyInstance(T instance) {
		boolean isNullableAnnotated = field.getAnnotation(Nullable.class) != null;
		boolean isNonNullAnnotated = field.getAnnotation(Nonnull.class) != null;

		if (!isNullableAnnotated && instance == null) {
			throw new BindException(Inject.class, parent.getClass(), field, "field is not annotated as Nullable but module tries to inject null value");
		}

		if (isNonNullAnnotated && instance == null) {
			throw new BindException(Inject.class, parent.getClass(), field, "field is annotated as NonNull but module tries to inject null value");
		}
	}

	@SuppressWarnings("unchecked")
	private T invokeMethod() {
		try {
			return (T)method.invoke(parent, (Object[]) null);
		} catch (IllegalAccessException e) {
			throw new BindException(Inject.class, parent.getClass(), field, "method \"" + method.getName() + "\" on module " + parent.getClass().getName() + " is not public", e);
		} catch (Exception e) {
			throw new BindException(Inject.class, parent.getClass(), field, "failed to invoke method \"" + method.getName() + "\" on module " + parent.getClass().getName(), e);
		}
	}
}
