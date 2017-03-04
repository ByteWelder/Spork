package spork.inject;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.inject.Inject;
import javax.inject.Provider;

import spork.BindException;
import spork.inject.internal.ObjectGraphBuilder;

import static org.junit.Assert.assertEquals;

public class InjectLazyTests {

	@Rule
	public ExpectedException excpectedException = ExpectedException.none();

	/**
	 * A module that increases a counter every time a method is called.
	 */
	public static class Module {
		private int counter = 0;

		@Provides
		public Integer integerValue() {
			return ++counter;
		}
	}


	private static class Parent {
		@Inject
		@Lazy
		Provider<Integer> provider;
	}

	private static class FaultyFieldParent {
		@Inject
		@Lazy
		Integer noProvider;
	}

	/**
	 * Test that when Provider.get() is called multiple times,
	 * the same instance is returned every time.
	 */
	@Test
	public void multipleGet() {
		Parent parent = new Parent();

		new ObjectGraphBuilder()
				.module(new Module())
				.build()
				.inject(parent);

		Integer first = parent.provider.get();
		Integer second = parent.provider.get();

		assertEquals((Integer) 1, first);
		assertEquals((Integer) 1, second);
	}

	@Test
	public void testNonProviderField() {
		excpectedException.expect(BindException.class);
		excpectedException.expectMessage("Inject failed for FaultyFieldParent at field 'noProvider': Lazy annotation can only be used with Provider field");

		FaultyFieldParent parent = new FaultyFieldParent();

		new ObjectGraphBuilder()
				.module(new Module())
				.build()
				.inject(parent);
	}
}