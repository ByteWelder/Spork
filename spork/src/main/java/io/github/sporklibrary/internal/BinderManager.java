package io.github.sporklibrary.internal;

import java.util.List;

import io.github.sporklibrary.BinderRegistry;
import io.github.sporklibrary.binders.FieldBinder;
import io.github.sporklibrary.binders.MethodBinder;
import io.github.sporklibrary.binders.TypeBinder;

public interface BinderManager extends BinderRegistry {

	interface RegistrationListener {
		void onRegisterFieldBinder(FieldBinder<?> fieldBinder);
		void onRegisterMethodBinder(MethodBinder<?> methodBinder);
		void onRegisterTypeBinder(TypeBinder<?> typeBinder);
	}

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
