package spork.inject.internal.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Nullable;

import spork.exceptions.UnexpectedException;

public final class QualifierCache {
	private final Map<Class<? extends Annotation>, Method> map;
	private final Lock mapLock;

	public QualifierCache(Map<Class<? extends Annotation>, Method> map, Lock mapLock) {
		this.map = map;
		this.mapLock = mapLock;
	}

	@SuppressWarnings("PMD.UseConcurrentHashMap") // because we want to be able to store null
	public QualifierCache() {
		this(new HashMap<Class<? extends Annotation>, Method>(), new ReentrantLock());
	}

	/**
	 * @param annotation the Qualifier annotation
	 * @return the String representing this annotation Qualifier
	 */
	public String getQualifier(Annotation annotation) {
		Method method = getValueMethodThreadSafe(annotation.annotationType());

		if (method == null) {
			return annotation.annotationType().getName();
		} else {
			return getQualifier(method, annotation);
		}
	}

	/**
	 * Get the qualifier String given from the specified method and the provided annotation
	 * that the method belongs too.
	 *
	 * Visible for testing.
	 *
	 * @param valueMethod the value() method of the provided Annotation instance
	 * @param annotation the Annotation instance that has the provided valueMethod
	 * @return the qualifier String
	 */
	String getQualifier(Method valueMethod, Annotation annotation) {
		try {
			valueMethod.setAccessible(true);
			Object value = valueMethod.invoke(annotation);
			return annotation.annotationType().getName() + ":" + value;
		} catch (IllegalAccessException e) {
			throw new UnexpectedException("Failed to access a Method that was previously made accessible. Maybe there is a concurrency problem?", e);
		} catch (InvocationTargetException e) {
			throw new UnexpectedException("The value method of "
					+ annotation.annotationType().getName()
					+ " threw an exception", e);
		} finally {
			valueMethod.setAccessible(false);
		}
	}

	/**
	 * Thread-safe variant of getValueMethod(...).
	 * Get a value() Method from the cache or try to find it through reflection and cache it.
	 * Wraps getValueMethod() in a Lock.
	 *
	 * Visible for testing.
	 *
	 * @param annotationType the annotation type to find a method for
	 * @return the Method or null
	 */
	@Nullable
	Method getValueMethodThreadSafe(Class<? extends Annotation> annotationType) {
		mapLock.lock();
		try {
			return getValueMethod(annotationType);
		} finally {
			mapLock.unlock();
		}
	}

	/**
	 * Get a value() Method from the cache or try to find it through reflection and cache it.

	 * Visible for testing.
	 *
	 * @param annotationType the annotation type to find a method for
	 * @return the Method or null
	 */
	@Nullable
	Method getValueMethod(Class<? extends Annotation> annotationType) {
		if (map.containsKey(annotationType)) {
			return map.get(annotationType);
		} else {
			Method method;

			try {
				method = annotationType.getMethod("value");
			} catch (NoSuchMethodException e) {
				method = null;
			}

			map.put(annotationType, method);
			return method;
		}
	}
}
