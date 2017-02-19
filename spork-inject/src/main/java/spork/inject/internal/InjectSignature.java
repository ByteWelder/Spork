package spork.inject.internal;

import java.lang.annotation.Annotation;

import javax.annotation.Nullable;

import spork.inject.AnnotationSerializers;
import spork.inject.internal.lang.Nullability;

/**
 * Class that defines compatibility of inject target/source
 */
public final class InjectSignature {
	private static final String SEPARATOR = ":";
	private final Class<?> targetType;
	private final Nullability nullability;

	@Nullable
	private final String qualifier;

	public InjectSignature(Class<?> targetType, Nullability nullability, @Nullable Annotation qualifierAnnotation) {
		this.targetType = targetType;
		this.nullability = nullability;
		this.qualifier = (qualifierAnnotation != null) ? AnnotationSerializers.serialize(qualifierAnnotation) : null;
	}

	public Class<?> getType() {
		return targetType;
	}

	public Nullability getNullability() {
		return nullability;
	}

	public boolean hasQualifier() {
		return qualifier != null;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + targetType.hashCode();
		result = 31 * result + nullability.hashCode();
		result = 31 * result + (qualifier != null ? qualifier.hashCode() : 0);
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || o.getClass() != InjectSignature.class) {
			return false;
		}

		InjectSignature other = (InjectSignature) o;

		return (this.qualifier == null) == (other.qualifier == null) // nullability match
				&& this.targetType.equals(other.targetType)
				&& this.nullability.equals(other.nullability)
				&& (this.qualifier == null || this.qualifier.equals(other.qualifier));

	}

	@Override
	public String toString() {
		if (qualifier == null) {
			return targetType.getName()
					+ SEPARATOR + nullability;
		} else {
			return targetType.getName()
					+ SEPARATOR + nullability
					+ SEPARATOR + qualifier;
		}
	}
}
