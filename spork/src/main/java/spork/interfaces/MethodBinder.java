package spork.interfaces;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * A MethodBinder provides binding for a specific Method annotation.
 */
public interface MethodBinder<AnnotationType extends Annotation> {
	/**
	 * Bind an annotation for a specific Method of a given object.
	 *
	 * @param object     the annotated instance
	 * @param annotation the annotation
	 * @param method     the method that was annotated
	 * @param parameters an array of non-null modules
	 */
	void bind(Object object, AnnotationType annotation, Method method, Object[] parameters);

	/**
	 * @return the annotation to provide bindings for
	 */
	Class<AnnotationType> getAnnotationClass();
}
