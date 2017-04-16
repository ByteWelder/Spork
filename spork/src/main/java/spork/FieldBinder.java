package spork;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * A FieldBinder provides binding for a specific Field annotation on an object instance.
 */
public interface FieldBinder<T extends Annotation> {
	/**
	 * Bind an annotation for a specific Field of a given object.
	 *
	 * @param object     the annotated instance
	 * @param annotation the annotation
	 * @param field      the field that was annotated
	 * @param parameters optional parameters
	 */
	void bind(Object object, T annotation, Field field, Object... parameters);

	/**
	 * @return the annotation to provide bindings for
	 */
	Class<T> getAnnotationClass();
}
