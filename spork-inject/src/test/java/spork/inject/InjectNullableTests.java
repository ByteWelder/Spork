package spork.inject;

import org.junit.Test;

import javax.annotation.Nullable;
import javax.inject.Inject;

import spork.Spork;
import spork.exceptions.BindException;
import spork.inject.internal.objectgraph.ObjectGraph;
import spork.inject.modules.StringModule;
import spork.inject.modules.StringNullModule;

import static org.junit.Assert.assertNull;

public class InjectNullableTests {
	private static class GoodParent {
		@Inject
		@Nullable
		String string;
	}

	private static class BadParent {
		@Inject
		String string;
	}

	@Test
	public void nullValueWithNullable() {
		GoodParent parent = new GoodParent();

		ObjectGraph graph = new ObjectGraph.Builder()
				.module(new StringNullModule())
				.build();

		Spork.bind(parent, graph);

		assertNull(parent.string);
	}

	@Test(expected = BindException.class)
	public void nullValueWithoutNullable() {
		BadParent parent = new BadParent();

		ObjectGraph graph = new ObjectGraph.Builder()
				.module(new StringNullModule())
				.build();

		Spork.bind(parent, graph);
	}
}
