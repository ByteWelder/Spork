package spork.inject;

import org.junit.Test;

import javax.inject.Inject;
import javax.inject.Named;

import spork.Spork;
import spork.inject.internal.objectgraph.ObjectGraph;
import spork.inject.internal.objectgraph.ObjectGraphBuilder;

import static org.junit.Assert.assertEquals;

public class InjectNamedTests {
	public static class Module {

		@Provides @Named("one")
		public int one() {
			return 1;
		}

		@Provides @Named("two")
		public int two() {
			return 2;
		}

		@Provides
		public int unnamed() {
			return 3;
		}
	}

	private static class Parent {
		@Inject @Named("one")
		public int one;

		@Inject @Named("two")
		public int two;

		@Inject
		public int unnamed;
	}

	@Test
	public void namedTest() {
		Parent parent = new Parent();

		ObjectGraph graph = new ObjectGraphBuilder()
				.module(new Module())
				.build();

		Spork.bind(parent, graph);

		assertEquals(1, parent.one);
		assertEquals(2, parent.two);
		assertEquals(3, parent.unnamed);
	}
}