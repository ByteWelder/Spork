package io.github.sporklibrary.binders;

import java.lang.annotation.Annotation;

public interface Binder<AnnotationType extends Annotation>
{
	/**
	 * @return the annotation to provide bindings for
	 */
	Class<AnnotationType> getAnnotationClass();
}
