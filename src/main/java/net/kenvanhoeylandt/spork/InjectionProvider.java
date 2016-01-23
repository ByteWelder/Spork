package net.kenvanhoeylandt.spork;

import java.lang.annotation.Annotation;

/**
 * An InjectionProvider provides injection for a specific annotation.
 */
public interface InjectionProvider
{
	/**
	 * @return the annotation to provide injection for
	 */
	Class<? extends Annotation> getAnnotationClass();

	/**
	 * Inject an annotation for a specific field of a given object.
	 * @param object the parent object that owns the field
	 * @param annotatedField the annotated field to inject
	 */
	void inject(Object object, AnnotatedField<?> annotatedField);
}
