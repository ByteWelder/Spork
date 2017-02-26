package spork.inject;

import java.lang.annotation.Annotation;

public interface AnnotationSerializer<T extends Annotation> {
	String serialize(T annotation);
}
