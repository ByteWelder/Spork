package spork.inject.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;
import javax.inject.Provider;
import javax.inject.Qualifier;

import spork.inject.internal.lang.Annotations;
import spork.inject.internal.lang.Nullability;

class InjectSignatureCache {
	private final Map<Field, InjectSignature> fieldInjectSignatureMap;
	private final Map<Method, InjectSignature[]> methodInjectSignatureMap;

	public InjectSignatureCache(Map<Field, InjectSignature> fieldInjectSignatureMap, Map<Method, InjectSignature[]> methodInjectSignatureMap) {
		this.fieldInjectSignatureMap = fieldInjectSignatureMap;
		this.methodInjectSignatureMap = methodInjectSignatureMap;
	}

	public InjectSignatureCache() {
		this(new HashMap<Field, InjectSignature>(), new HashMap<Method, InjectSignature[]>());
	}

	// region Fields

	/**
	 * Get an InjectSignature instance.
	 * It's either retrieved from cache or it is created and stored in cache.
	 * @param field the target field
	 * @param targetType the real target type (not a {@link Provider})
	 * @return an InjectSignature
	 */
	InjectSignature getInjectSignature(Field field, Class<?> targetType) {
		if (fieldInjectSignatureMap.containsKey(field)) {
			return fieldInjectSignatureMap.get(field);
		} else {
			InjectSignature injectSignature = createInjectSignature(field, targetType);
			fieldInjectSignatureMap.put(field, injectSignature);
			return injectSignature;
		}
	}

	private InjectSignature createInjectSignature(Field field, Class<?> targetType) {
		Annotation qualifierAnnotation = Annotations.findAnnotationAnnotatedWith(Qualifier.class, field);
		Nullability nullability = Nullability.create(field);
		return new InjectSignature(targetType, nullability, qualifierAnnotation);
	}

	// endregion

	// region Methods

	/**
	 * Get the InjectSignature for a specific Method parameter.
	 * It is either retrieved from cache or otherwise created, cached and returned.
	 */
	InjectSignature[] getInjectSignatures(Method method) {
		if (methodInjectSignatureMap.containsKey(method)) {
			return methodInjectSignatureMap.get(method);
		} else {
			return cacheInjectSignatures(method);
		}
	}

	@Nullable
	private InjectSignature[] cacheInjectSignatures(Method method) {
		if (method.getParameterTypes().length > 0) {
			int parameterCount = method.getParameterTypes().length;
			InjectSignature[] injectSignatures = new InjectSignature[parameterCount];
			for (int i = 0; i < parameterCount; ++i) {
				injectSignatures[i] = createInjectSignature(method, i);
			}
			methodInjectSignatureMap.put(method, injectSignatures);
			return injectSignatures;
		} else {
			methodInjectSignatureMap.put(method, null);
			return null;
		}
	}

	/**
	 * Create the InjectSignature for a specific Method parameter.
	 */
	private InjectSignature createInjectSignature(Method method, int parameterIndex) {
		Class<?> parameterClass = method.getParameterTypes()[parameterIndex];

		Annotation[] annotations = method.getParameterAnnotations()[parameterIndex];
		Annotation qualifierAnnotation = Annotations.findAnnotationAnnotatedWith(Qualifier.class, annotations);
		Nullability nullability = Nullability.create(annotations);
		Class<?> targetType = (parameterClass == Provider.class)
				? (Class<?>) ((ParameterizedType) method.getGenericParameterTypes()[parameterIndex]).getActualTypeArguments()[0]
				: parameterClass;

		return new InjectSignature(targetType, nullability, qualifierAnnotation);
	}

	// endregion
}
