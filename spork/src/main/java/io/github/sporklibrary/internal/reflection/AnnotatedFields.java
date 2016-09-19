package io.github.sporklibrary.internal.reflection;

import io.github.sporklibrary.exceptions.BindException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * A utility  class for {@link AnnotatedField}.
 */
public final class AnnotatedFields {

    private AnnotatedFields() {
    }

    /**
     * Get an immutable set of annotated fields from the provided class
     *
     * @param annotationClass  the annotation class
     * @param annotatedClass   the class to search for annotations
     * @param <AnnotationType> the annotationClass type
     * @return a set of AnnotatedField objects for the specified annotation type
     */
    public static <AnnotationType extends Annotation> Set<AnnotatedField<AnnotationType>> get(Class<AnnotationType> annotationClass, Class<?> annotatedClass) {
        HashSet<AnnotatedField<AnnotationType>> annotatedFieldSet = new HashSet<>();

        for (Field field : annotatedClass.getDeclaredFields()) {
            AnnotationType annotation = field.getAnnotation(annotationClass);

            if (annotation != null) {
                annotatedFieldSet.add(new AnnotatedField<>(annotation, field));
            }
        }

        return !annotatedFieldSet.isEmpty() ? annotatedFieldSet : Collections.<AnnotatedField<AnnotationType>>emptySet();
    }

    /**
     * Set a value for an AnnotatedField on an object
     *
     * @param annotatedField the AnnotatedField
     * @param parentObject   the parent object
     * @param valueObject    the field value to bind
     */
    public static void setValue(AnnotatedField<?> annotatedField, Object parentObject, Object valueObject) {
        Field field = annotatedField.getField();

        boolean accessible = field.isAccessible();

        try {
            if (accessible) {
                field.set(parentObject, valueObject);
            } else {
                field.setAccessible(true);
                field.set(parentObject, valueObject);
                field.setAccessible(false);
            }
        } catch (IllegalAccessException e) {
            throw new BindException(annotatedField.getAnnotation().getClass(), parentObject.getClass(), field, "field not accessible", e);
        } finally {
            // ensure the Field isn't accessible when it shouldn't be
            if (!accessible && field.isAccessible()) {
                field.setAccessible(false);
            }
        }
    }
}
