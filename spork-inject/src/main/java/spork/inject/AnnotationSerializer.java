package spork.inject;

import java.lang.annotation.Annotation;

/**
 * Interface for all AnnotationSerializer classes.
 * @param <T> annotation type
 */
public interface AnnotationSerializer<T extends Annotation> {

	/**
	 * Serializes the annotation instance into a uniquely identifiable String.
	 * @param annotation .
	 * @return a uniquely identifiable String for the given annotation
	 */
	String serialize(T annotation);
}
