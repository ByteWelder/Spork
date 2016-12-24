package spork.inject.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.annotation.Nullable;
import javax.inject.Qualifier;

/**
 * Creates signatures to match injectable fields with Module methods
 */
public final class InjectSignatureFactory {
	private InjectSignatureFactory() {
	}

	private static final String QUALIFIER_PREFIX = "Q=";
	private static final String TAG_POSTFIX = ":";

	/**
	 * @return a signature to match injectable fields with Module methods
	 */
	public static String createSignature(Field field) {
		return createSignature(field.getType(), field.getAnnotations());
	}

	/**
	 * @return a signature to match injectable fields with Module methods
	 */
	public static String createSignature(Method method) {
		return createSignature(method.getReturnType(), method.getAnnotations());
	}

	/**
	 * Create a signature for a Method's argument annotations.
	 * This is available because java.lang.reflect.Parameter requires JDK 1.8
	 *
	 * @param type the type to create a signature for
	 * @param annotations the annotations that belong to the type's usage (in case of Field/Parameter) or declaration (in case of Method)
	 * @return a signature
	 */
	public static String createSignature(Class<?> type, Annotation[] annotations) {
		String qualifierName = getQualifierName(annotations);
		return createSignature(type, qualifierName);
	}

	/**
	 * Create a signature to match injectable fields with Module methods.
	 *
	 * @param type the type to create a signature for
	 * @param qualifierName the type's qualifier
	 * @return a signature in the following format: "Q=CustomQualifier:package.name.Type"
	 */
	private static String createSignature(Class<?> type, @Nullable String qualifierName) {
		return QUALIFIER_PREFIX + qualifierName  + TAG_POSTFIX
				+ type.getName();
	}

	@Nullable
	private static String getQualifierName(Annotation[] annotations) {
		for (Annotation annotation : annotations) {
			if (annotation.getClass().getAnnotation(Qualifier.class) != null) {
				return annotation.getClass().getName();
			}
		}

		return null;
	}
}
