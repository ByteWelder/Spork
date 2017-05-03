package spork.inject.internal.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;
import javax.inject.Provider;
import javax.inject.Qualifier;

import spork.inject.internal.lang.Annotations;
import spork.inject.internal.lang.Nullability;

public class InjectSignatureMethodCache {
	private final QualifierCache qualifierCache;
	private final Map<Method, InjectSignature[]> methodInjectSignatureMap;

	public InjectSignatureMethodCache(QualifierCache qualifierCache, Map<Method, InjectSignature[]> methodInjectSignatureMap) {
		this.qualifierCache = qualifierCache;
		this.methodInjectSignatureMap = methodInjectSignatureMap;
	}

	public InjectSignatureMethodCache(QualifierCache qualifierCache) {
		this(qualifierCache, new HashMap<Method, InjectSignature[]>());
	}

	/**
	 * Get the InjectSignature instances for the given Method's parameters.
	 * It is either retrieved from cache or otherwise created, cached and returned.
	 * @param method the method to analyze
	 * @return an array of 1 or more InjectSignature instances or null (never an empty array!)
	 */
	@Nullable
	public InjectSignature[] getInjectSignatures(Method method) {
		synchronized (methodInjectSignatureMap) {
			if (methodInjectSignatureMap.containsKey(method)) {
				return methodInjectSignatureMap.get(method);
			} else {
				InjectSignature[] signatures = createInjectSignatures(method);
				methodInjectSignatureMap.put(method, signatures);
				return signatures;
			}
		}
	}

	/**
	 * Cache the InjectSignature instances for the given Method's parameters
	 * @param method the method to analyze
	 * @return an array of 1 or more InjectSignature instances or null (never an empty array!)
	 */
	@Nullable
	private InjectSignature[] createInjectSignatures(Method method) {
		if (method.getParameterTypes().length == 0) {
			return null;
		}

		int parameterCount = method.getParameterTypes().length;
		InjectSignature[] injectSignatures = new InjectSignature[parameterCount];
		for (int i = 0; i < parameterCount; ++i) {
			injectSignatures[i] = createInjectSignature(method, i);
		}
		return injectSignatures;
	}

	/**
	 * Create the InjectSignature for a specific Method parameter.
	 */
	private InjectSignature createInjectSignature(Method method, int parameterIndex) {
		Class<?> parameterClass = method.getParameterTypes()[parameterIndex];

		Annotation[] annotations = method.getParameterAnnotations()[parameterIndex];
		Nullability nullability = Nullability.create(annotations);
		Class<?> targetType = (parameterClass == Provider.class)
				? (Class<?>) ((ParameterizedType) method.getGenericParameterTypes()[parameterIndex]).getActualTypeArguments()[0]
				: parameterClass;

		Annotation qualifierAnnotation = Annotations.findAnnotationAnnotatedWith(Qualifier.class, annotations);
		String qualifier = qualifierAnnotation != null
				? qualifierCache.getQualifier(qualifierAnnotation)
				: null;

		return new InjectSignature(targetType, nullability, qualifier);
	}

	// endregion
}
