package io.github.sporklibrary.interfaces;

import java.lang.annotation.Annotation;

/**
 * The base interface for an annotation binder.
 * @param <AnnotationType> the annotation type this binder belongs to
 */
public interface AnnotationClassProvider<AnnotationType extends Annotation>
{
	/**
	 * @return the annotation to provide bindings for
	 */
	Class<AnnotationType> getAnnotationClass();
}
