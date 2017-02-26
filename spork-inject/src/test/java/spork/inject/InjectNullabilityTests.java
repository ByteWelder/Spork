package spork.inject;

import org.junit.Test;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;

import spork.Spork;
import spork.BindException;
import spork.inject.internal.objectgraph.ObjectGraph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class InjectNullabilityTests {

	public static class StringNullableModule {
		@Provides
		@Nullable
		public String stringValue() {
			return null;
		}
	}

	public static class StringNonnullModule {
		@Provides
		@Nonnull
		public String stringValue() {
			return "test";
		}
	}

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
				.module(new StringNonnullModule())
				.build();

		Spork.bind(parent, graph);

		assertEquals("test", parent.string);
	}

	@Test(expected = BindException.class)
	public void injectNonnullWithNullableParent() {
		NullableParent parent = new NullableParent();

		ObjectGraph graph = new ObjectGraph.Builder()
				.module(new StringNonnullModule())
				.build();

		Spork.bind(parent, graph);

		assertNull(parent.string);
	}

	@Test
	public void injectNullWithNullableParent() {
		NullableParent parent = new NullableParent();

		ObjectGraph graph = new ObjectGraph.Builder()
				.module(new StringNullableModule())
				.build();

		Spork.bind(parent, graph);

		assertNull(parent.string);
	}

	@Test(expected = BindException.class)
	public void injectNullWithNonnullParent() {
		NonnullParent parent = new NonnullParent();

		ObjectGraph graph = new ObjectGraph.Builder()
				.module(new StringNullableModule())
				.build();

		Spork.bind(parent, graph);
	}
}
