package spork.internal;

import org.junit.Before;
import org.junit.Test;

import spork.exceptions.BindFailed;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

public class BinderTests {
	private BindActionProvider actionProvider;
	private Binder binder;

	private static class NoInheritanceTarget {
	}

	private static class InheritanceTarget extends NoInheritanceTarget {
	}

	@Before
	public void setup() {
		Catalog catalog = new Catalog();
		actionProvider = spy(new BindActionProvider(catalog));
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
	}

	@Test
	public void bindWithInheritance() throws BindFailed {
		binder.bind(new InheritanceTarget());

		verify(actionProvider).getBindActions(InheritanceTarget.class);
		verify(actionProvider).getBindActions(NoInheritanceTarget.class);
		verifyNoMoreInteractions(actionProvider);
	}
}