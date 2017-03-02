package spork.inject;

import org.junit.Test;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;

import spork.BindException;
import spork.inject.internal.objectgraph.ObjectGraphBuilder;

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

		new ObjectGraphBuilder()
				.module(new StringNonnullModule())
				.build()
				.inject(parent);

		assertEquals("test", parent.string);
	}

	@Test(expected = BindException.class)
	public void injectNonnullWithNullableParent() {
		NullableParent parent = new NullableParent();

		new ObjectGraphBuilder()
				.module(new StringNonnullModule())
				.build()
				.inject(parent);

		assertNull(parent.string);
	}

	@Test
	public void injectNullWithNullableParent() {
		NullableParent parent = new NullableParent();

		new ObjectGraphBuilder()
				.module(new StringNullableModule())
				.build()
				.inject(parent);

		assertNull(parent.string);
	}

	@Test(expected = BindException.class)
	public void injectNullWithNonnullParent() {
		NonnullParent parent = new NonnullParent();

		new ObjectGraphBuilder()
				.module(new StringNullableModule())
				.build()
				.inject(parent);
	}
}
