package spork;

import org.junit.Test;

import spork.interfaces.Binder;
import spork.interfaces.BinderRegistry;
import spork.internal.BinderImpl;
import spork.internal.BinderManager;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class SporkTests {

	@Test
	public void defaultConstructorTest() {
		Spork spork = new Spork();

		assertTrue(spork.getBinderRegistry() instanceof BinderManager);
		assertTrue(spork.getBinder() instanceof BinderImpl);
	}

	@Test
	public void parametrizedConsturctorTest() {
		Binder binder = mock(Binder.class);
		BinderRegistry binderRegistry = mock(BinderRegistry.class);
		Spork spork = new Spork(binderRegistry, binder);

		assertTrue(binder == spork.getBinder());
		assertTrue(binderRegistry == spork.getBinderRegistry());
	}

	@Test
	public void sharedInstanceTest() {
		Spork spork = Spork.shared();

		assertTrue(spork.getBinderRegistry() instanceof BinderManager);
		assertTrue(spork.getBinder() instanceof BinderImpl);
	}
}
