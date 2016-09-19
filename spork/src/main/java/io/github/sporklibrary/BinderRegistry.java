package io.github.sporklibrary;

import java.lang.annotation.Annotation;

import io.github.sporklibrary.binders.FieldBinder;
import io.github.sporklibrary.binders.MethodBinder;
import io.github.sporklibrary.binders.TypeBinder;

public interface BinderRegistry {
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

}
