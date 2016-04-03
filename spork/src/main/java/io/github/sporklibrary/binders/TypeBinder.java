package io.github.sporklibrary.binders;

import io.github.sporklibrary.interfaces.AnnotationClassProvider;
import io.github.sporklibrary.reflection.AnnotatedType;

import java.lang.annotation.Annotation;

/**
 * A TypeBinder provides binding for a specific class/interface.
 */
public interface TypeBinder<AnnotationType extends Annotation> extends AnnotationClassProvider<AnnotationType> {
    /**
     * Bind an annotation for a specific class
     *
     * @param object        the class instance
     * @param annotatedType the annotated class to bind
     */
    void bind(Object object, AnnotatedType<AnnotationType> annotatedType);
}
