package spork.inject;

import org.junit.Test;

import javax.inject.Inject;

import spork.inject.internal.ObjectGraphBuilder;

import static org.junit.Assert.assertEquals;

public class InjectLazyTests {

	/**
	 * A module that increases a counter every time a method is called.
	 */
	private static class Module {
		private int counter = 0;

		@Provides
		public Integer integerValue() {
			return ++counter;
		}
	}

	private static class Parent {
		@Inject
		Lazy<Integer> provider;
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
}