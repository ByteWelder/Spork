package io.github.sporklibrary.binders;

import io.github.sporklibrary.reflection.AnnotatedClass;

import java.lang.annotation.Annotation;

/**
 * A ClassBinder provides binding for a specific class.
 */
public interface ClassBinder<AnnotationType extends Annotation> extends Binder<AnnotationType>
{
	/**
	 * Bind an annotation for a specific class
	 * @param object the class instance
	 * @param annotatedClass the annotated class to bind
	 */
	void bind(Object object, AnnotatedClass<AnnotationType> annotatedClass);
}
