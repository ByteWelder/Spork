package io.github.sporklibrary;

import java.lang.annotation.Annotation;
import java.util.List;

import io.github.sporklibrary.binders.FieldBinder;
import io.github.sporklibrary.binders.MethodBinder;
import io.github.sporklibrary.binders.TypeBinder;

public interface BinderManager {

	interface RegistrationListener {
		void onRegisterFieldBinder(FieldBinder<?> fieldBinder);
		void onRegisterMethodBinder(MethodBinder<?> methodBinder);
		void onRegisterTypeBinder(TypeBinder<?> typeBinder);
	}

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
	 * @return all registered FieldBinder instances
	 */
	List<FieldBinder<?>> getFieldBinders();

	/**
	 * @return all registered MethodBinder instances
	 */
	List<MethodBinder<?>> getMethodBinders();

	/**
	 * @return all registered TypeBinder instances
	 */
	List<TypeBinder<?>> getTypeBinders();

	/**
	 * Add a registration listener
	 * @param registrationListener the listener
	 */
	void addRegistrationListener(RegistrationListener registrationListener);

	/**
	 * Remove a registration listener
	 * @param registrationListener the listener
	 */
	void removeRegistrationListener(RegistrationListener registrationListener);
}
