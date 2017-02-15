package spork.inject;

import org.junit.Test;

import javax.inject.Inject;
import javax.inject.Singleton;

import spork.Spork;
import spork.inject.internal.objectgraph.ObjectGraph;

import static org.junit.Assert.assertEquals;

public class InjectSingletonTests {

	public static class Module {
		private int counter = 1;

		@Provides
		@Singleton
		public int counter() {
			return counter++;
		}
	}
	
	private static class Parent {
		@Inject
		int counter = -1;
	}

	@Test
	public void test() {
		Parent parent = new Parent();

		ObjectGraph graph = new ObjectGraph.Builder()
				.module(new Module())
				.build();

		// ensure wrong defaults
		parent.counter = -1;

		// inject twice
		Spork.bind(parent, graph);
		Spork.bind(parent, graph);

		// verify same value
		assertEquals(1, parent.counter);
	}
}
