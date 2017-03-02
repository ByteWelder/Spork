package spork.inject;

import org.junit.Test;

import javax.inject.Inject;

import spork.Spork;
import spork.inject.internal.objectgraph.ObjectGraph;
import spork.inject.internal.objectgraph.ObjectGraphBuilder;

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
		Lazy<Integer> provider;
	}

	/**
	 * Test that when Provider.get() is called multiple times,
	 * the same instance is returned every time.
	 */
	@Test
	public void multipleGet() {
		// given
		Parent parent = new Parent();

		ObjectGraph graph = new ObjectGraphBuilder()
				.module(new Module())
				.build();

		Spork.bind(parent, graph);

		// when
		Integer first = parent.provider.get();
		Integer second = parent.provider.get();

		// then
		assertEquals((Integer) 1, first);
		assertEquals((Integer) 1, second);
	}
}