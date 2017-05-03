package spork.inject.internal.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Provider;
import javax.inject.Qualifier;

import spork.inject.internal.lang.Annotations;
import spork.inject.internal.lang.Nullability;

public class InjectSignatureFieldCache {
	private final QualifierCache qualifierCache;
	private final Map<Field, InjectSignature> fieldInjectSignatureMap;

	public InjectSignatureFieldCache(QualifierCache qualifierCache, Map<Field, InjectSignature> injectSignatureMap) {
		this.qualifierCache = qualifierCache;
		this.fieldInjectSignatureMap = injectSignatureMap;
	}

	public InjectSignatureFieldCache(QualifierCache qualifierCache) {
		this(qualifierCache, new HashMap<Field, InjectSignature>());
	}

	/**
	 * Get an InjectSignature instance.
	 * It's either retrieved from cache or it is created and stored in cache.
	 * @param field the target field
	 * @param targetType the real target type (not a {@link Provider})
	 * @return an InjectSignature
	 */
	public InjectSignature getInjectSignature(Field field, Class<?> targetType) {
		synchronized (fieldInjectSignatureMap) {
			if (fieldInjectSignatureMap.containsKey(field)) {
				return fieldInjectSignatureMap.get(field);
			} else {
				InjectSignature injectSignature = createInjectSignature(field, targetType);
				fieldInjectSignatureMap.put(field, injectSignature);
				return injectSignature;
			}
		}
	}

	private InjectSignature createInjectSignature(Field field, Class<?> targetType) {
		Annotation qualifierAnnotation = Annotations.findAnnotationAnnotatedWith(Qualifier.class, field);
		Nullability nullability = Nullability.create(field);
		String qualifier = qualifierAnnotation != null
				? qualifierCache.getQualifier(qualifierAnnotation)
				: null;
		return new InjectSignature(targetType, nullability, qualifier);
	}
}
