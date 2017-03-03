package spork.inject.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.annotation.Nullable;
import javax.inject.Provider;
import javax.inject.Qualifier;

import spork.inject.AnnotationSerializerRegistry;
import spork.inject.internal.lang.Annotations;
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

	/**
	 *
	 * @param targetType the real target type (not a Provider)
	 * @param nullability .
	 * @param qualifierAnnotation .
	 */
	InjectSignature(Class<?> targetType, Nullability nullability, @Nullable Annotation qualifierAnnotation) {
		this.targetType = targetType;
		this.nullability = nullability;
		this.qualifier = (qualifierAnnotation != null) ? AnnotationSerializerRegistry.serialize(qualifierAnnotation) : null;
	}

	InjectSignature(Class<?> targetTypeOrProvider, Annotation[] annotations, Type genericParameterType) {
		Annotation qualifierAnnotation = Annotations.findAnnotationAnnotatedWith(Qualifier.class, annotations);
		this.qualifier = (qualifierAnnotation != null) ? AnnotationSerializerRegistry.serialize(qualifierAnnotation) : null;
		this.nullability = Nullability.create(annotations);
		this.targetType = (targetTypeOrProvider == Provider.class) ? (Class<?>) ((ParameterizedType) genericParameterType).getActualTypeArguments()[0] : targetTypeOrProvider;
	}

	Class<?> getType() {
		return targetType;
	}

	Nullability getNullability() {
		return nullability;
	}

	boolean hasQualifier() {
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
