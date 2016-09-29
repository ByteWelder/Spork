package io.github.sporklibrary.internal.caching;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import io.github.sporklibrary.Binder;
import io.github.sporklibrary.annotations.Nullable;
import io.github.sporklibrary.binders.FieldBinder;
import io.github.sporklibrary.binders.MethodBinder;
import io.github.sporklibrary.binders.TypeBinder;
import io.github.sporklibrary.internal.reflection.AnnotatedField;
import io.github.sporklibrary.internal.reflection.AnnotatedFields;
import io.github.sporklibrary.internal.reflection.AnnotatedMethod;
import io.github.sporklibrary.internal.reflection.AnnotatedMethods;
import io.github.sporklibrary.internal.reflection.AnnotatedType;
import io.github.sporklibrary.internal.reflection.AnnotatedTypes;

/**
 * Holds the annotation type cache for one class. This only holds the data for the specified class
 * which excludes the cache of its superclasses.
 */
public final class ClassBinderCache {
	private final List<Binder> cachedBinders = new ArrayList<>();
	private final Class<?> annotatedType;

	public ClassBinderCache(Class<?> annotatedType) {
		this.annotatedType = annotatedType;
	}

	/**
	 * Update the cache with this binder
	 *
	 * @param fieldBinder      the field binder to cache annotated fields for
	 * @param <AnnotationType> the annotation to search for in the annotated type
	 */
	public <AnnotationType extends Annotation> void register(FieldBinder<AnnotationType> fieldBinder) {
		List<AnnotatedField<AnnotationType>> annotatedFields = AnnotatedFields.get(fieldBinder.getAnnotationClass(), annotatedType);

		if (!annotatedFields.isEmpty()) {
			cachedBinders.add(new AnnotatedFieldBinderCache<>(fieldBinder, annotatedFields));
		}
	}

	/**
	 * Update the cache with this binder
	 *
	 * @param methodBinder     the method binder to cache annotated methods for
	 * @param <AnnotationType> the annotation to search for in the annotated type
	 */
	public <AnnotationType extends Annotation> void register(MethodBinder<AnnotationType> methodBinder) {
		List<AnnotatedMethod<AnnotationType>> annotatedMethods = AnnotatedMethods.get(methodBinder.getAnnotationClass(), annotatedType);

		if (!annotatedMethods.isEmpty()) {
			cachedBinders.add(new AnnotatedMethodBinderCache<>(methodBinder, annotatedMethods));
		}
	}

	/**
	 * Update the cache with this binder
	 *
	 * @param typeBinder       the type binder to cache annotated types for
	 * @param <AnnotationType> the annotation to search for in the annotated type
	 */
	public <AnnotationType extends Annotation> void register(TypeBinder<AnnotationType> typeBinder) {
		@Nullable AnnotatedType<AnnotationType> annotatedType = AnnotatedTypes.get(typeBinder.getAnnotationClass(), this.annotatedType);

		if (annotatedType != null) {
			cachedBinders.add(new AnnotatedTypeBinderCache<>(typeBinder, annotatedType));
		}
	}

	/**
	 * @return the list of all CachedBinder instances managed for this cache.
	 */
	public List<Binder> getCachedBinders() {
		return cachedBinders;
	}
}
