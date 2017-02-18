package spork;

import java.lang.annotation.Annotation;

/**
 * A TypeBinder provides binding for a specific class/interface.
 */
public interface TypeBinder<AnnotationType extends Annotation> {
	/**
	 * Bind an annotation for a specific class
	 *
	 * @param object        the annotated instance
	 * @param annotation    the annotation
	 * @param annotatedType the class level where this annotation was found
	 * @param parameters    an array of non-null modules
	 */
	void bind(Object object, AnnotationType annotation, Class<?> annotatedType, Object[] parameters);

	/**
	 * @return the annotation to provide bindings for
	 */
	Class<AnnotationType> getAnnotationClass();
}
