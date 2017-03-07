package spork.interfaces;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * A MethodBinder provides binding for a specific Method annotation.
 */
public interface MethodBinder<T extends Annotation> {
	/**
	 * Bind an annotation for a specific Method of a given object.
	 *
	 * @param object     the annotated instance
	 * @param annotation the annotation
	 * @param method     the method that was annotated
	 * @param parameters optional parameters
	 */
	void bind(Object object, T annotation, Method method, Object... parameters);

	/**
	 * @return the annotation to provide bindings for
	 */
	Class<T> getAnnotationClass();
}
