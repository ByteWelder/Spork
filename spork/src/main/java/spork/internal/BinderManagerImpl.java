package spork.internal;


import java.util.ArrayList;
import java.util.List;

import spork.interfaces.FieldBinder;
import spork.interfaces.MethodBinder;
import spork.interfaces.TypeBinder;

public class BinderManagerImpl implements BinderManager {
	private final List<FieldBinder<?>> fieldBinders = new ArrayList<>();
	private final List<MethodBinder<?>> methodBinders = new ArrayList<>();
	private final List<TypeBinder<?>> typeBinders = new ArrayList<>();
	private final List<RegistrationListener> registrationListeners = new ArrayList<>(1);

	@Override
	public void register(FieldBinder<?> fieldBinder) {
		fieldBinders.add(fieldBinder);

		for (RegistrationListener registrationListener : registrationListeners) {
			registrationListener.onRegisterFieldBinder(fieldBinder);
		}
	}

	@Override
	public void register(MethodBinder<?> methodBinder) {
		methodBinders.add(methodBinder);

		for (RegistrationListener registrationListener : registrationListeners) {
			registrationListener.onRegisterMethodBinder(methodBinder);
		}
	}

	@Override
	public void register(TypeBinder<?> typeBinder) {
		typeBinders.add(typeBinder);

		for (RegistrationListener registrationListener : registrationListeners) {
			registrationListener.onRegisterTypeBinder(typeBinder);
		}
	}

	@Override
	public List<FieldBinder<?>> getFieldBinders() {
		return fieldBinders;
	}

	@Override
	public List<MethodBinder<?>> getMethodBinders() {
		return methodBinders;
	}

	@Override
	public List<TypeBinder<?>> getTypeBinders() {
		return typeBinders;
	}

	@Override
	public void addRegistrationListener(RegistrationListener registrationListener) {
		registrationListeners.add(registrationListener);
	}

	@Override
	public void removeRegistrationListener(RegistrationListener registrationListener) {
		registrationListeners.remove(registrationListener);
	}
}