package spork.android.test;

import org.junit.Test;
import org.mockito.Mockito;

import spork.android.extension.FragmentResolver;
import spork.android.internal.CompoundFragmentResolver;

import static org.mockito.Mockito.verify;

public class CompoundFragmentResolverTests {
	@Test
	public void resolveById() throws Exception {
		CompoundFragmentResolver compoundResolver = Mockito.spy(new CompoundFragmentResolver());
		FragmentResolver resolver = Mockito.mock(FragmentResolver.class);
		compoundResolver.add(resolver);

		compoundResolver.resolveFragment(null, 1);
		verify(resolver).resolveFragment(null, 1);
	}

	@Test
	public void resolveByName() throws Exception {
		CompoundFragmentResolver compoundResolver = Mockito.spy(new CompoundFragmentResolver());
		FragmentResolver resolver = Mockito.mock(FragmentResolver.class);
		compoundResolver.add(resolver);

		compoundResolver.resolveFragment(null, "name");
		verify(resolver).resolveFragment(null, "name");
	}
}
