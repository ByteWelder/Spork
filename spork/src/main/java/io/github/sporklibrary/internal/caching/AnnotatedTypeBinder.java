package io.github.sporklibrary.internal.caching;

import java.lang.annotation.Annotation;

import io.github.sporklibrary.annotations.Nullable;
import io.github.sporklibrary.binders.TypeBinder;
import io.github.sporklibrary.internal.interfaces.ObjectBinder;
import io.github.sporklibrary.internal.reflection.AnnotatedType;

/**
 * A binder that caches type bindings for a specific class.
 *
 * @param <AnnotationType> the annotation type that is being bound
 */
class AnnotatedTypeBinder<AnnotationType extends Annotation> implements ObjectBinder {
    private final AnnotatedType<AnnotationType> annotatedType;
    private final TypeBinder<AnnotationType> typeBinder;

    AnnotatedTypeBinder(TypeBinder<AnnotationType> typeBinder, AnnotatedType<AnnotationType> annotatedType) {
        this.annotatedType = annotatedType;
        this.typeBinder = typeBinder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void bind(Object object, @Nullable Object[] modules) {
        typeBinder.bind(object, annotatedType, modules);
    }
}
