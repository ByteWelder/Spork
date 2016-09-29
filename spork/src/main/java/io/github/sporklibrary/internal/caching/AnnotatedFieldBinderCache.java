package io.github.sporklibrary.internal.caching;

import java.lang.annotation.Annotation;
import java.util.List;

import io.github.sporklibrary.Binder;
import io.github.sporklibrary.annotations.Nullable;
import io.github.sporklibrary.binders.FieldBinder;
import io.github.sporklibrary.internal.reflection.AnnotatedField;

/**
 * A binder that caches field bindings for a specific class.
 *
 * @param <AnnotationType> the annotation type that is being bound
 */
class AnnotatedFieldBinderCache<AnnotationType extends Annotation> implements Binder {
	private final List<AnnotatedField<AnnotationType>> annotatedFields;
	private final FieldBinder<AnnotationType> fieldBinder;

	AnnotatedFieldBinderCache(FieldBinder<AnnotationType> fieldBinder, List<AnnotatedField<AnnotationType>> annotatedFields) {
		this.fieldBinder = fieldBinder;
		this.annotatedFields = annotatedFields;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void bind(Object object, @Nullable Object... modules) {
		// Bind all fields for this object
		for (AnnotatedField<AnnotationType> annotatedField : annotatedFields) {
			fieldBinder.bind(object, annotatedField.getAnnotation(), annotatedField.getField(), modules);
		}
	}
}
