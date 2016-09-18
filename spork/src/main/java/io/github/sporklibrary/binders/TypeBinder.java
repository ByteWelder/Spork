package io.github.sporklibrary.binders;

import java.lang.annotation.Annotation;

import io.github.sporklibrary.annotations.Nullable;
import io.github.sporklibrary.interfaces.AnnotationClassProvider;
import io.github.sporklibrary.reflection.AnnotatedType;

/**
 * A TypeBinder provides binding for a specific class/interface.
 */
public interface TypeBinder<AnnotationType extends Annotation> extends AnnotationClassProvider<AnnotationType> {
    /**
     * Bind an annotation for a specific class
     *
     * @param object        the class instance
     * @param annotatedType the annotated class to bind
     * @param modules either null or an array of non-null modules
     */
    void bind(Object object, AnnotatedType<AnnotationType> annotatedType, @Nullable Object[] modules);
}
