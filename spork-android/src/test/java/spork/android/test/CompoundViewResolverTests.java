package spork.android.test;

import org.junit.Test;
import org.mockito.Mockito;

import spork.android.extension.ViewResolver;
import spork.android.internal.CompoundViewResolver;

import static org.mockito.Mockito.verify;

public class CompoundViewResolverTests {
	@Test
	public void resolve() throws Exception {
		CompoundViewResolver compoundResolver = Mockito.spy(new CompoundViewResolver());
		ViewResolver resolver = Mockito.mock(ViewResolver.class);
		compoundResolver.add(resolver);

		Object object = new Object();
		compoundResolver.resolveView(object);
		verify(resolver).resolveView(object);
	}
}
