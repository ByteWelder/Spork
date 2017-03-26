package spork.inject.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

class QualifierFactory {
	@SuppressWarnings("PMD.UseConcurrentHashMap") // because we want to be able to store null
	private final Map<Class<? extends Annotation>, Method> cache = new HashMap<>();

	String create(Annotation annotation) {
		Class<? extends Annotation> annotationType = annotation.annotationType();

		Method method;

		synchronized (cache) {
			if (!cache.containsKey(annotationType)) {
				try {
					method = annotationType.getMethod("value");
				} catch (NoSuchMethodException e) {
					method = null;
				}

				cache.put(annotationType, method);
			} else {
				method = cache.get(annotationType);
			}
		}

		if (method == null) {
			return annotationType.getName();
		} else {
			return annotationType.getName() + invoke(annotation, method);
		}
	}

	@SuppressWarnings("PMD.AvoidThrowingRawExceptionTypes") // because these exceptions are never thrown
	private String invoke(Annotation annotation, Method valueMethod) {
		valueMethod.setAccessible(true);

		try {
			return valueMethod.invoke(annotation).toString();
		} catch (InvocationTargetException e) {
			throw new RuntimeException("Failed to invoke " + annotation.annotationType().getName() + ".value()", e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("No access to invoke " + annotation.annotationType().getName() + ".value()", e);
		} finally {
			valueMethod.setAccessible(false);
		}
	}
}
