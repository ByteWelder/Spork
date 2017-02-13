package spork.inject;

import org.junit.Test;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import spork.Spork;
import spork.exceptions.BindException;
import spork.inject.internal.objectgraph.ObjectGraph;
import spork.inject.modules.IntegerModule;
import spork.inject.modules.StringNullModule;
import spork.inject.modules.StringModule;

import static org.junit.Assert.assertEquals;

public class InjectNonnullTests {
	private static class Parent {
		@Inject
		@Nonnull
		String string = "";
	}

	@Test
	public void instanceValueTest() {
		Parent parent = new Parent();

		ObjectGraph graph = new ObjectGraph.Builder()
				.module(new StringModule())
				.build();

		Spork.bind(parent, graph);

		assertEquals("test", parent.string);
	}

	@Test(expected = BindException.class)
	public void nullValueTest() {
		Parent parent = new Parent();

		ObjectGraph graph = new ObjectGraph.Builder()
				.module(new StringNullModule())
				.build();

		Spork.bind(parent, graph);
	}
}
