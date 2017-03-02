package spork.inject;

import org.junit.Test;

import javax.inject.Inject;
import javax.inject.Singleton;

import spork.Spork;
import spork.inject.internal.objectgraph.ObjectGraph;
import spork.inject.internal.objectgraph.ObjectGraphBuilder;

import static org.junit.Assert.assertEquals;

public class InjectSingletonTests {

	public static class Module {
		private Integer counter = 1;

		@Provides
		@Singleton
		public Integer counter() {
			return counter++;
		}
	}

	private static class Parent {
		@Inject
		Integer counter = -1;
	}

	@Test
	public void singleInstanceTest() {
		Parent parent = new Parent();

		ObjectGraph graph = new ObjectGraphBuilder()
				.module(new Module())
				.build();

		// ensure wrong defaults
		parent.counter = -1;

		// inject twice
		Spork.bind(parent, graph);
		Spork.bind(parent, graph);

		// verify same value
		assertEquals(Integer.valueOf(1), parent.counter);
	}
}
