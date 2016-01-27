package io.github.sporklibrary.binders;

import java.lang.annotation.Annotation;

/**
 * A MethodBinder provides binding for a specific Method annotation.
 */
public interface MethodBinder<AnnotationType extends Annotation>
{
	/**
	 * @return the annotation to provide bindings for
	 */
	Class<AnnotationType> getAnnotationClass();

	/**
	 * Bind an annotation for a specific Method of a given object.
	 * @param object the parent object that owns the field
	 * @param annotatedMethod the annotated method to bind
	 */
	void bind(Object object, AnnotatedMethod<AnnotationType> annotatedMethod);
}
