package spork.android.test;

import org.junit.Test;
import org.mockito.Mockito;

import spork.android.interfaces.ViewResolver;
import spork.android.internal.ViewResolverManager;

import static org.mockito.Mockito.verify;

public class ViewResolverTests {
	@Test
	public void resolve() {
		ViewResolverManager manager = Mockito.spy(new ViewResolverManager());
		ViewResolver resolver = Mockito.mock(ViewResolver.class);
		manager.register(resolver);

		Object object = new Object();
		manager.resolveView(object);
		verify(resolver).resolveView(object);
	}
}
