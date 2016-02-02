package io.github.sporklibrary.binders;

import io.github.sporklibrary.reflection.AnnotatedTypeTemp;

import java.lang.annotation.Annotation;

/**
 * A TypeBinder provides binding for a specific class/interface.
 */
public interface TypeBinder<AnnotationType extends Annotation> extends Binder<AnnotationType>
{
	/**
	 * Bind an annotation for a specific class
	 * @param object the class instance
	 * @param annotatedClass the annotated class to bind
	 */
	void bind(Object object, AnnotatedTypeTemp<AnnotationType> annotatedClass);
}
