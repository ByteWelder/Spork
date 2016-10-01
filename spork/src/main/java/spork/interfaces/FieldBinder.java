package spork.interfaces;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * A FieldBinder provides binding for a specific Field annotation on an object instance.
 */
public interface FieldBinder<AnnotationType extends Annotation> {
	/**
	 * Bind an annotation for a specific Field of a given object.
	 *
	 * @param object     the annotated instance
	 * @param annotation the annotation
	 * @param field      the field that was annotated
	 * @param modules    either null or an array of non-null modules
	 */
	void bind(Object object, AnnotationType annotation, Field field, Object[] modules);

	/**
	 * @return the annotation to provide bindings for
	 */
	Class<AnnotationType> getAnnotationClass();
}
