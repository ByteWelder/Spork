package spork;

import org.junit.Test;

import spork.interfaces.FieldBinder;
import spork.interfaces.MethodBinder;
import spork.interfaces.TypeBinder;
import spork.internal.BinderManager;
import spork.internal.BinderManagerImpl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class BinderManagerTests {

	@Test
	public void registrationListenerTest() {
		// Create real BinderManager
		BinderManager binderManager = new BinderManagerImpl();

		// Create mocked binders
		FieldBinder fieldBinder = mock(FieldBinder.class);
		TypeBinder typeBinder = mock(TypeBinder.class);
		MethodBinder methodBinder = mock(MethodBinder.class);
		BinderManager.RegistrationListener registrationListener = mock(BinderManager.RegistrationListener.class);

		// Add registration listener and register all 3 types
		binderManager.addRegistrationListener(registrationListener);
		binderManager.register(fieldBinder);
		binderManager.register(typeBinder);
		binderManager.register(methodBinder);

		// Verify that the registration listener was working
		verify(registrationListener, times(1)).onRegisterFieldBinder(fieldBinder);
		verify(registrationListener, times(1)).onRegisterTypeBinder(typeBinder);
		verify(registrationListener, times(1)).onRegisterMethodBinder(methodBinder);

		// Remove the registration listener and register the same types
		binderManager.removeRegistrationListener(registrationListener);
		binderManager.register(fieldBinder);
		binderManager.register(typeBinder);
		binderManager.register(methodBinder);

		// Verify that the registration listener was not called again
		verify(registrationListener, times(1)).onRegisterFieldBinder(fieldBinder);
		verify(registrationListener, times(1)).onRegisterTypeBinder(typeBinder);
		verify(registrationListener, times(1)).onRegisterMethodBinder(methodBinder);
	}
}
