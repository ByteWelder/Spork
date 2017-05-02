package spork.android.test;

import org.junit.Test;
import org.mockito.Mockito;

import spork.android.extension.ContextResolver;
import spork.android.internal.CompoundContextResolver;

import static org.mockito.Mockito.verify;

public class CompoundContextResolverTests {
	@Test
	public void resolve() throws Exception {
		CompoundContextResolver compoundResolver = Mockito.spy(new CompoundContextResolver());
		ContextResolver resolver = Mockito.mock(ContextResolver.class);
		compoundResolver.add(resolver);

		Object object = new Object();
		compoundResolver.resolveContext(object);
		verify(resolver).resolveContext(object);
	}
}
