package spork.internal;

import org.junit.Before;
import org.junit.Test;

import spork.exceptions.BindFailed;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

public class BinderTests {
	private BindActionProvider actionProvider;
	private Binder binder;
	private BindActionCache.Factory factory;

	private static class NoInheritanceTarget {
	}

	private static class InheritanceTarget extends NoInheritanceTarget {
	}

	@Before
	public void setup() {
		Catalog catalog = new Catalog();
		factory = mock(BindActionCache.Factory.class);
		actionProvider = spy(new BindActionProvider(catalog, factory));
		binder = new Binder(actionProvider);
	}

	@Test
	public void bindObject() throws BindFailed {
		binder.bind(new Object());

		verifyZeroInteractions(actionProvider);
	}

	@Test
	public void bindNoInheritance() throws BindFailed {
		binder.bind(new NoInheritanceTarget());

		verify(actionProvider).getBindActions(NoInheritanceTarget.class);
		verifyNoMoreInteractions(actionProvider);

		verify(factory).create(NoInheritanceTarget.class);
		verifyNoMoreInteractions(factory);
	}

	@Test
	public void bindNoInheritanceCache() throws BindFailed {
		binder.bind(new NoInheritanceTarget());
		binder.bind(new NoInheritanceTarget());

		// Bound twice
		verify(actionProvider, times(2)).getBindActions(NoInheritanceTarget.class);
		verifyNoMoreInteractions(actionProvider);

		// Cached once
		verify(factory).create(NoInheritanceTarget.class);
		verifyNoMoreInteractions(factory);
	}


	@Test
	public void bindWithInheritance() throws BindFailed {
		binder.bind(new InheritanceTarget());

		verify(actionProvider).getBindActions(InheritanceTarget.class);
		verify(actionProvider).getBindActions(NoInheritanceTarget.class);
		verifyNoMoreInteractions(actionProvider);

		verify(factory).create(InheritanceTarget.class);
		verify(factory).create(NoInheritanceTarget.class);
		verifyNoMoreInteractions(factory);
	}
}