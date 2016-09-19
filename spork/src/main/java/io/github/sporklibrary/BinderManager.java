package io.github.sporklibrary;

import java.lang.annotation.Annotation;

import io.github.sporklibrary.annotations.Nullable;
import io.github.sporklibrary.binders.FieldBinder;
import io.github.sporklibrary.binders.MethodBinder;
import io.github.sporklibrary.binders.TypeBinder;

public interface BinderManager {

	/**
	 * Register a FieldBinder
	 *
	 * @param fieldBinder the binder instance
	 * @param <AnnotationType> the annotation type of the binder
	 */
	<AnnotationType extends Annotation> void register(FieldBinder<AnnotationType> fieldBinder);

	/**
	 * Register a MethodBinder
	 *
	 * @param methodBinder the binder instance
	 * @param <AnnotationType> the annotation type of the binder
	 */
	<AnnotationType extends Annotation> void register(MethodBinder<AnnotationType> methodBinder);

	/**
	 * Register a TypeBinder
	 *
	 * @param typeBinder the binder instance
	 * @param <AnnotationType> the annotation type of the binder
	 */
	<AnnotationType extends Annotation> void register(TypeBinder<AnnotationType> typeBinder);

	/**
	 * Bind all annotations for an object instance on all levels of inheritance.
	 *
	 * @param object the instance to bind annotations for
	 */
	void bind(Object object);

	/**
	 * Bind all annotations for an object instance on all levels of inheritance.
	 *
	 * @param object the instance to bind annotations for
	 * @param modules either null or an array of non-null modules
	 */
	void bind(Object object, @Nullable Object... modules);
}
