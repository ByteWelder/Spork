package spork.inject;

import org.junit.Test;

import javax.inject.Inject;
import javax.inject.Provider;

import spork.BindException;
import spork.inject.internal.ObjectGraphBuilder;

import static org.junit.Assert.assertEquals;

public class InjectLazyTests {

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

	@Test(expected = BindException.class)
	public void testNonProviderField() {
		Parent parent = new Parent();

		new ObjectGraphBuilder()
				.module(new FaultyFieldParent())
				.build()
				.inject(parent);
	}
}