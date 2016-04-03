package io.github.sporklibrary.caching;

import io.github.sporklibrary.binders.FieldBinder;
import io.github.sporklibrary.interfaces.ObjectBinder;
import io.github.sporklibrary.reflection.AnnotatedField;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * A binder that caches field bindings for a specific class.
 *
 * @param <AnnotationType> the annotation type that is being bound
 */
class AnnotatedFieldBinder<AnnotationType extends Annotation> implements ObjectBinder {
    private final Set<AnnotatedField<AnnotationType>> annotatedFields;
    private final FieldBinder<AnnotationType> fieldBinder;

    AnnotatedFieldBinder(FieldBinder<AnnotationType> fieldBinder, Set<AnnotatedField<AnnotationType>> annotatedFields) {
        this.fieldBinder = fieldBinder;
        this.annotatedFields = annotatedFields;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void bind(Object object) {
        // Bind all fields for this object
        for (AnnotatedField<AnnotationType> annotatedField : annotatedFields) {
            fieldBinder.bind(object, annotatedField);
        }
    }
}
