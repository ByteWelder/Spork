package spork.android.test;

import org.junit.Test;
import org.mockito.Mockito;

import spork.android.extension.FragmentResolver;
import spork.android.internal.FragmentResolverManager;

import static org.mockito.Mockito.verify;

public class FragmentResolverManagerTests {
	@Test
	public void resolveById() {
		FragmentResolverManager manager = Mockito.spy(new FragmentResolverManager());
		FragmentResolver resolver = Mockito.mock(FragmentResolver.class);
		manager.register(resolver);

		manager.resolveFragment(null, 1);
		verify(resolver).resolveFragment(null, 1);
	}

	@Test
	public void resolveByName() {
		FragmentResolverManager manager = Mockito.spy(new FragmentResolverManager());
		FragmentResolver resolver = Mockito.mock(FragmentResolver.class);
		manager.register(resolver);

		manager.resolveFragment(null, "name");
		verify(resolver).resolveFragment(null, "name");
	}
}
