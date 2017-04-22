package spork.inject.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import spork.internal.Reflection;

class QualifierFactory {
	@SuppressWarnings("PMD.UseConcurrentHashMap") // because we want to be able to store null
	private final Map<Class<? extends Annotation>, Method> cache = new HashMap<>();

	String create(Annotation annotation) {
		Class<? extends Annotation> annotationType = annotation.annotationType();

		Method method = getMethod(annotationType);

		if (method == null) {
			return annotationType.getName();
		} else {
			Object value = Reflection.invokeMethod(annotation.annotationType(), method, annotation);
			return annotationType.getName() + ":" + value;
		}
	}

	@Nullable
	private Method getMethod(Class<? extends Annotation> annotationType) {
		synchronized (cache) {
			if (cache.containsKey(annotationType)) {
				return cache.get(annotationType);
			} else {
				Method method;

				try {
					method = annotationType.getMethod("value");
				} catch (NoSuchMethodException e) {
					method = null;
				}

				cache.put(annotationType, method);
				return method;
			}
		}
	}
}
