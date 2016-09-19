package io.github.sporklibrary.internal;


import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import io.github.sporklibrary.binders.FieldBinder;
import io.github.sporklibrary.binders.MethodBinder;
import io.github.sporklibrary.binders.TypeBinder;

/**
 * The BinderManager manages all bindings and their cache.
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
    public <AnnotationType extends Annotation> void register(FieldBinder<AnnotationType> fieldBinder) {
        fieldBinders.add(fieldBinder);

		for (RegistrationListener registrationListener : registrationListeners) {
			registrationListener.onRegisterFieldBinder(fieldBinder);
		}
    }

	/**
	 * {@inheritDoc}
	 */
	@Override
    public <AnnotationType extends Annotation> void register(MethodBinder<AnnotationType> methodBinder) {
        methodBinders.add(methodBinder);

		for (RegistrationListener registrationListener : registrationListeners) {
			registrationListener.onRegisterMethodBinder(methodBinder);
		}
    }

	/**
	 * {@inheritDoc}
	 */
	@Override
    public <AnnotationType extends Annotation> void register(TypeBinder<AnnotationType> typeBinder) {
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