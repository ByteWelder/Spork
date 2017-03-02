package spork.inject;

import org.junit.Test;

import javax.inject.Inject;
import javax.inject.Provider;

import spork.Spork;
import spork.inject.internal.objectgraph.ObjectGraph;
import spork.inject.internal.objectgraph.ObjectGraphBuilder;

import static org.junit.Assert.assertEquals;

public class InjectProviderTests {

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
		Provider<Integer> provider;
	}

	/**
	 * Test that when Provider.get() is called multiple times,
	 * a new instance is returned every time.
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
		assertEquals((Integer) 2, second);
	}
}
