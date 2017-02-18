package spork.internal;


import java.util.ArrayList;
import java.util.List;

import spork.FieldBinder;
import spork.MethodBinder;
import spork.TypeBinder;

/**
 * {@inheritDoc}
 */
public class BinderManagerImpl implements BinderManager {
	private final List<FieldBinder<?>> fieldBinders = new ArrayList<>();
	private final List<MethodBinder<?>> methodBinders = new ArrayList<>();
	private final List<TypeBinder<?>> typeBinders = new ArrayList<>();
	private final List<RegistrationListener> registrationListeners = new ArrayList<>(1);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void register(FieldBinder<?> fieldBinder) {
		fieldBinders.add(fieldBinder);

		for (RegistrationListener registrationListener : registrationListeners) {
			registrationListener.onRegisterFieldBinder(fieldBinder);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void register(MethodBinder<?> methodBinder) {
		methodBinders.add(methodBinder);

		for (RegistrationListener registrationListener : registrationListeners) {
			registrationListener.onRegisterMethodBinder(methodBinder);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void register(TypeBinder<?> typeBinder) {
		typeBinders.add(typeBinder);

		for (RegistrationListener registrationListener : registrationListeners) {
			registrationListener.onRegisterTypeBinder(typeBinder);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<FieldBinder<?>> getFieldBinders() {
		return fieldBinders;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<MethodBinder<?>> getMethodBinders() {
		return methodBinders;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<TypeBinder<?>> getTypeBinders() {
		return typeBinders;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addRegistrationListener(RegistrationListener registrationListener) {
		registrationListeners.add(registrationListener);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeRegistrationListener(RegistrationListener registrationListener) {
		registrationListeners.remove(registrationListener);
	}
}