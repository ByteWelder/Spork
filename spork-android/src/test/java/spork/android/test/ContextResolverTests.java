package spork.android.test;

import org.junit.Test;
import org.mockito.Mockito;

import spork.android.interfaces.ContextResolver;
import spork.android.internal.ContextResolverManager;

import static org.mockito.Mockito.verify;

public class ContextResolverTests {
	@Test
	public void resolve() {
		ContextResolverManager manager = Mockito.spy(new ContextResolverManager());
		ContextResolver resolver = Mockito.mock(ContextResolver.class);
		manager.register(resolver);

		Object object = new Object();
		manager.resolveContext(object);
		verify(resolver).resolveContext(object);
	}
}
