package io.github.sporklibrary.internal.caching;

import java.lang.annotation.Annotation;
import java.util.List;

import io.github.sporklibrary.Binder;
import io.github.sporklibrary.annotations.Nullable;
import io.github.sporklibrary.binders.MethodBinder;
import io.github.sporklibrary.internal.reflection.AnnotatedMethod;

/**
 * A binder that caches method bindings for a specific class.
 *
 * @param <AnnotationType> the annotation type that is being bound
 */
class AnnotatedMethodBinderCache<AnnotationType extends Annotation> implements Binder {
	private final List<AnnotatedMethod<AnnotationType>> annotatedMethods;
	private final MethodBinder<AnnotationType> methodBinder;

	AnnotatedMethodBinderCache(MethodBinder<AnnotationType> methodBinder, List<AnnotatedMethod<AnnotationType>> annotatedMethods) {
		this.methodBinder = methodBinder;
		this.annotatedMethods = annotatedMethods;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void bind(Object object, @Nullable Object... modules) {
		// Bind all methods for this object
		for (AnnotatedMethod<AnnotationType> annotatedMethod : annotatedMethods) {
			methodBinder.bind(object, annotatedMethod.getAnnotation(), annotatedMethod.getMethod(), modules);
		}
	}
}
