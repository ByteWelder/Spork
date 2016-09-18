package io.github.sporklibrary.binders;

import java.lang.annotation.Annotation;

import io.github.sporklibrary.annotations.Nullable;
import io.github.sporklibrary.interfaces.AnnotationClassProvider;
import io.github.sporklibrary.reflection.AnnotatedMethod;

/**
 * A MethodBinder provides binding for a specific Method annotation.
 */
public interface MethodBinder<AnnotationType extends Annotation> extends AnnotationClassProvider<AnnotationType> {
    /**
     * Bind an annotation for a specific Method of a given object.
     *
     * @param object          the parent object that owns the field
     * @param annotatedMethod the annotated method to bind
     * @param modules either null or an array of non-null modules
     */
    void bind(Object object, AnnotatedMethod<AnnotationType> annotatedMethod, @Nullable Object[] modules);
}
