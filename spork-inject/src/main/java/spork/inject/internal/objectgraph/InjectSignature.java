package spork.inject.internal.objectgraph;

import java.lang.annotation.Annotation;

import javax.annotation.Nullable;
import javax.inject.Singleton;

public class InjectSignature {
	private static final String SEPARATOR = ":";
	private final String value;
	private final Class<?> targetType;
	private final Nullability nullability;
	@Nullable
	private final Annotation scopeAnnotation;

	public InjectSignature(Class<?> targetType, Nullability nullability, @Nullable Annotation scopeAnnotation) {
		this.targetType = targetType;
		this.nullability = nullability;
		this.scopeAnnotation = scopeAnnotation;
		value = createSignature(targetType, nullability, scopeAnnotation);
	}

	public Class<?> getType() {
		return targetType;
	}

	@Nullable
	public Annotation getScopeAnnotation() {
		return scopeAnnotation;
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

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new InjectSignature(targetType, nullability, scopeAnnotation);
	}

	/**
	 * @return a signature to match injectable fields with Module methods
	 */
	private static String createSignature(Class<?> type, Nullability nullability, @Nullable Annotation scope) {
		if (scope != null && !(scope instanceof Singleton)) {
			return type.getName() + SEPARATOR + nullability + SEPARATOR + scope.getClass().getName();
		} else {
			return type.getName() + SEPARATOR + nullability;
		}
	}
}
