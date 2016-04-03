package io.github.sporklibrary.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * A model that contains an {@link Annotation} and a {@link Method}
 *
 * @param <AnnotationType> the annotation type to store
 */
public class AnnotatedMethod<AnnotationType extends Annotation> {
    private final AnnotationType annotation;
    private final Method method;

    public AnnotatedMethod(AnnotationType annotation, Method method) {
        this.annotation = annotation;
        this.method = method;
    }

    public AnnotationType getAnnotation() {
        return annotation;
    }

    public Method getMethod() {
        return method;
    }
}