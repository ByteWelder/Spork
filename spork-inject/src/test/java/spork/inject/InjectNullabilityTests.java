package spork.inject;

import org.junit.Test;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;

import spork.Spork;
import spork.exceptions.BindException;
import spork.inject.internal.objectgraph.ObjectGraph;
import spork.inject.modules.StringNullModule;
import spork.inject.modules.StringModule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class InjectNullabilityTests {

	private static class NonnullParent {
		@Inject
		@Nonnull
		String string = "";
	}

	private static class NullableParent {
		@Inject
		@Nullable
		String string;
	}

	@Test
	public void injectNonnullWithNonnullParent() {
		NonnullParent parent = new NonnullParent();

		ObjectGraph graph = new ObjectGraph.Builder()
				.module(new StringModule())
				.build();

		Spork.bind(parent, graph);

		assertEquals("test", parent.string);
	}

	@Test(expected = BindException.class)
	public void injectNonnullWithNullableParent() {
		NullableParent parent = new NullableParent();

		ObjectGraph graph = new ObjectGraph.Builder()
				.module(new StringModule())
				.build();

		Spork.bind(parent, graph);

		assertNull(parent.string);
	}

	@Test
	public void injectNullWithNullableParent() {
		NullableParent parent = new NullableParent();

		ObjectGraph graph = new ObjectGraph.Builder()
				.module(new StringNullModule())
				.build();

		Spork.bind(parent, graph);

		assertNull(parent.string);
	}

	@Test(expected = BindException.class)
	public void injectNullWithNonnullParent() {
		NonnullParent parent = new NonnullParent();

		ObjectGraph graph = new ObjectGraph.Builder()
				.module(new StringNullModule())
				.build();

		Spork.bind(parent, graph);
	}
}
