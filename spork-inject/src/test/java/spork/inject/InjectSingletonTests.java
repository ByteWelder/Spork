package spork.inject;

import org.junit.Test;

import javax.inject.Inject;
import javax.inject.Singleton;

import spork.inject.internal.ObjectGraph;
import spork.inject.internal.ObjectGraphBuilder;

import static org.junit.Assert.assertEquals;

public class InjectSingletonTests {

	private static class Module {
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
		graph.inject(parent);
		graph.inject(parent);

		// verify same value
		assertEquals(Integer.valueOf(1), parent.counter);
	}
}
