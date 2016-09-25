package io.github.sporklibrary.internal.caching;

import java.lang.annotation.Annotation;

import io.github.sporklibrary.binders.FieldBinder;
import io.github.sporklibrary.binders.MethodBinder;
import io.github.sporklibrary.binders.TypeBinder;

public interface BinderCache {
	/**
	 * Gets the ClassBinderCache for the given type.
	 * If the ClassBinderCache isn't available yet, it will create it.
	 *
	 * @param type the class
	 * @return the cache
	 */
	ClassBinderCache getClassBinderCache(Class<?> type);

	<AnnotationType extends Annotation> void cache(FieldBinder<AnnotationType> fieldBinder);

	<AnnotationType extends Annotation> void cache(MethodBinder<AnnotationType> methodBinder);

	<AnnotationType extends Annotation> void cache(TypeBinder<AnnotationType> typeBinder);
}
