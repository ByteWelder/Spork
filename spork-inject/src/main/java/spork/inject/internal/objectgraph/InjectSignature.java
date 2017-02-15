package spork.inject.internal.objectgraph;

import java.lang.annotation.Annotation;

import javax.annotation.Nullable;
import javax.inject.Named;

import spork.inject.internal.lang.Nullability;

/**
 * Class that defines compatibility of inject target/source
 */
public class InjectSignature {
	private static final String SEPARATOR = ":";
	private final String value;
	private final Class<?> targetType;

	private final Nullability nullability;
	@Nullable
	private final Annotation qualifierAnnotation;

	public InjectSignature(Class<?> targetType, Nullability nullability, @Nullable Annotation qualifierAnnotation) {
		this.targetType = targetType;
		this.nullability = nullability;
		this.qualifierAnnotation = qualifierAnnotation;
		value = createSignature(targetType, nullability, qualifierAnnotation);
	}

	public Class<?> getType() {
		return targetType;
	}

	public Nullability getNullability() {
		return nullability;
	}

	@Nullable
	public Annotation getQualifierAnnotation() {
		return qualifierAnnotation;
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		return o != null
				&& o instanceof InjectSignature
				&& value.equals(((InjectSignature) o).value);
	}

	@Override
	public String toString() {
		return value;
	}

	/**
	 * @return a signature to match injectable fields with Module methods
	 */
	private static String createSignature(Class<?> type, Nullability nullability, @Nullable Annotation qualifierAnnotation) {
		if (qualifierAnnotation == null) {
			return type.getName()
					+ SEPARATOR + nullability;
		} else {
			return type.getName()
					+ SEPARATOR + nullability
					+ SEPARATOR + getQualifierName(qualifierAnnotation);
		}
	}

	@Nullable
	private static String getQualifierName(@Nullable Annotation qualifierAnnotation) {
		if (qualifierAnnotation == null) {
			return null;
		}

		if (qualifierAnnotation.annotationType() == Named.class) {
			return "Named:" + ((Named) qualifierAnnotation).value();
		} else {
			return qualifierAnnotation.annotationType().getName();
		}
	}
}
