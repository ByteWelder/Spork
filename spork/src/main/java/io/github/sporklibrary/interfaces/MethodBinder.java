package io.github.sporklibrary.interfaces;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import io.github.sporklibrary.annotations.Nullable;

/**
 * A MethodBinder provides binding for a specific Method annotation.
 */
public interface MethodBinder<AnnotationType extends Annotation> {
	/**
	 * Bind an annotation for a specific Method of a given object.
	 *
	 * @param instance   the annotated instance
	 * @param annotation the annotation
	 * @param method     the method that was annotated
	 * @param modules    either null or an array of non-null modules
	 */
	void bind(Object instance, AnnotationType annotation, Method method, @Nullable Object[] modules);

	/**
	 * @return the annotation to provide bindings for
	 */
	Class<AnnotationType> getAnnotationClass();
}
