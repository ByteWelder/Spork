package spork.inject.internal.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.annotation.Nullable;
import javax.inject.Provider;

public final class ReflectionCache {
	private final QualifierCache qualifierCache;
	private final InjectSignatureFieldCache fieldProvider;
	private final InjectSignatureMethodCache methodProvider;

	/**
	 * Primary constructor.
	 */
	public ReflectionCache(QualifierCache qualifierCache, InjectSignatureFieldCache fieldCache, InjectSignatureMethodCache methodCache) {
		this.qualifierCache = qualifierCache;
		this.fieldProvider = fieldCache;
		this.methodProvider = methodCache;
	}

	/**
	 * @param annotation the Qualifier annotation
	 * @return the String representing this annotation Qualifier
	 */
	public String getQualifier(Annotation annotation) {
		return qualifierCache.getQualifier(annotation);
	}

	/**
	 * Get an InjectSignature instance.
	 * @param field the target field
	 * @param targetType the real target type (not a {@link Provider})
	 * @return an InjectSignature
	 */
	public InjectSignature getInjectSignature(Field field, Class<?> targetType) {
		return fieldProvider.getInjectSignature(field, targetType);
	}

	/**
	 * Get the InjectSignature instances for the given Method's parameters.
	 * @param method the method to analyze
	 * @return an array of 1 or more InjectSignature instances or null (never an empty array!)
	 */
	@Nullable
	public InjectSignature[] getInjectSignatures(Method method) {
		return methodProvider.getInjectSignatures(method);
	}
}
