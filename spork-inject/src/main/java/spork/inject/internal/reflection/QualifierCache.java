package spork.inject.internal.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import spork.internal.Reflection;
import spork.exceptions.UnexpectedException;

public class QualifierCache {
	@SuppressWarnings("PMD.UseConcurrentHashMap") // because we want to be able to store null
	private final Map<Class<? extends Annotation>, Method> annotationToValueMethodMap = new HashMap<>();

	/**
	 * @param annotation the Qualifier annotation
	 * @return the String representing this annotation Qualifier
	 */
	public String getQualifier(Annotation annotation) {
		Class<? extends Annotation> annotationType = annotation.annotationType();

		Method method = getValueMethod(annotationType);

		if (method == null) {
			return annotationType.getName();
		} else {
			try {
				Object value = Reflection.invokeMethod(method, annotation);
				return annotationType.getName() + ":" + value;
			} catch (InvocationTargetException e) {
				throw new UnexpectedException("The value method of " + annotationType.getName() + " threw an exception", e);
			}
		}
	}

	@Nullable
	private Method getValueMethod(Class<? extends Annotation> annotationType) {
		synchronized (annotationToValueMethodMap) {
			if (annotationToValueMethodMap.containsKey(annotationType)) {
				return annotationToValueMethodMap.get(annotationType);
			} else {
				Method method;

				try {
					method = annotationType.getMethod("value");
				} catch (NoSuchMethodException e) {
					method = null;
				}

				annotationToValueMethodMap.put(annotationType, method);
				return method;
			}
		}
	}
}
