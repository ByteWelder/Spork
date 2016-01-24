package io.github.sporklibrary;

import java.lang.annotation.Annotation;

/**
 * An FieldBinder provides binding for a specific Field annotation on an object instance.
 */
public interface FieldBinder<AnnotationType extends Annotation>
{
	/**
	 * @return the annotation to provide bindings for
	 */
	Class<AnnotationType> getAnnotationClass();

	/**
	 * Bind an annotation for a specific field of a given object.
	 * @param object the parent object that owns the field
	 * @param annotatedField the annotated field to bind
	 */
	void bind(Object object, AnnotatedField<AnnotationType> annotatedField);
}
