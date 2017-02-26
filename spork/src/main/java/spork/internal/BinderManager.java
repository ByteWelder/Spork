package spork.internal;

import java.util.List;

import spork.interfaces.BinderRegistry;
import spork.interfaces.FieldBinder;
import spork.interfaces.MethodBinder;
import spork.interfaces.TypeBinder;

/**
 * The BinderManager is the main internal entry point for storing and retrieving
 * instances of FieldBinder/MethodBinder/TypeBinder.
 *
 * It also has a registration listener mechanism that allows (for example) a caching mechanism to observe new registrations.
 */
public interface BinderManager extends BinderRegistry {

	/**
	 * Gets notified of new binder registrations.
	 */
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
