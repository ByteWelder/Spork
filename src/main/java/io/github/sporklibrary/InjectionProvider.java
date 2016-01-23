package io.github.sporklibrary;

import java.lang.annotation.Annotation;

/**
 * An InjectionProvider provides injection for a specific annotation.
 */
public interface InjectionProvider<AnnotationType extends Annotation>
{
	/**
	 * @return the annotation to provide injection for
	 */
	Class<AnnotationType> getAnnotationClass();

	/**
	 * Inject an annotation for a specific field of a given object.
	 * @param object the parent object that owns the field
	 * @param annotatedField the annotated field to inject
	 */
	void inject(Object object, AnnotatedField<AnnotationType> annotatedField);
}
