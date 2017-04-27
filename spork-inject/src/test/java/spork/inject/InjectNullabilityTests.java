package spork.inject;

import org.junit.Test;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;

import spork.BindFailed;
import spork.inject.internal.ObjectGraphBuilder;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class InjectNullabilityTests {

	private static class StringNullableModule {
		@Provides
		@Nullable
		public String stringValue() {
			return null;
		}
	}

	private static class StringNonnullModule {
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

		assertThat(parent.string, is("test"));
	}

	@Test(expected = BindFailed.class)
	public void injectNonnullWithNullableParent() {
		NullableParent parent = new NullableParent();

		new ObjectGraphBuilder()
				.module(new StringNonnullModule())
				.build()
				.inject(parent);

		assertThat(parent.string, is(nullValue()));
	}

	@Test
	public void injectNullWithNullableParent() {
		NullableParent parent = new NullableParent();

		new ObjectGraphBuilder()
				.module(new StringNullableModule())
				.build()
				.inject(parent);

		assertThat(parent.string, is(nullValue()));
	}

	@Test(expected = BindFailed.class)
	public void injectNullWithNonnullParent() {
		NonnullParent parent = new NonnullParent();

		new ObjectGraphBuilder()
				.module(new StringNullableModule())
				.build()
				.inject(parent);
	}
}
