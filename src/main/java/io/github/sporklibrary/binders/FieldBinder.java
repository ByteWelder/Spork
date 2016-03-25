package io.github.sporklibrary.binders;

import io.github.sporklibrary.interfaces.AnnotationClassProvider;
import io.github.sporklibrary.reflection.AnnotatedField;

import java.lang.annotation.Annotation;

/**
 * A FieldBinder provides binding for a specific Field annotation on an object instance.
 */
public interface FieldBinder<AnnotationType extends Annotation> extends AnnotationClassProvider<AnnotationType>
{
	/**
	 * Bind an annotation for a specific Field of a given object.
	 * @param object the parent object that owns the field
	 * @param annotatedField the annotated field to bind
	 */
	void bind(Object object, AnnotatedField<AnnotationType> annotatedField);
}
