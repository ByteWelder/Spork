package io.github.sporklibrary.binders;

import java.lang.annotation.Annotation;

import io.github.sporklibrary.annotations.Nullable;
import io.github.sporklibrary.internal.reflection.AnnotatedField;

/**
 * A FieldBinder provides binding for a specific Field annotation on an object instance.
 */
public interface FieldBinder<AnnotationType extends Annotation> {
	/**
	 * Bind an annotation for a specific Field of a given object.
	 *
	 * @param object         the parent object that owns the field
	 * @param annotatedField the annotated field to bind
	 * @param modules        either null or an array of non-null modules
	 */
	void bind(Object object, AnnotatedField<AnnotationType> annotatedField, @Nullable Object[] modules);

	/**
	 * @return the annotation to provide bindings for
	 */
	Class<AnnotationType> getAnnotationClass();
}
