package spork.inject;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;

import spork.exceptions.SporkRuntimeException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class InjectNullabilityTests {
	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	private static class StringNullableModule {
		@Provides
		@Nullable
		public String provideStringValue() {
			return null;
		}
	}

	private static class StringNonnullModule {
		@Provides
		@Nonnull
		public String provideStringValue() {
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

		ObjectGraphs.builder()
				.module(new StringNonnullModule())
				.build()
				.inject(parent);

		assertThat(parent.string, is("test"));
	}

	@Test
	public void injectNonnullWithNullableParent() {
		expectedException.expect(SporkRuntimeException.class);
		expectedException.expectMessage("None of the modules provides an instance for java.lang.String:NULLABLE");

		NullableParent parent = new NullableParent();

		ObjectGraphs.builder()
				.module(new StringNonnullModule())
				.build()
				.inject(parent);

		assertThat(parent.string, is(nullValue()));
	}

	@Test
	public void injectNullWithNullableParent() {
		NullableParent parent = new NullableParent();

		ObjectGraphs.builder()
				.module(new StringNullableModule())
				.build()
				.inject(parent);

		assertThat(parent.string, is(nullValue()));
	}

	@Test
	public void injectNullWithNonnullParent() {
		expectedException.expect(SporkRuntimeException.class);
		expectedException.expectMessage("None of the modules provides an instance for java.lang.String:NONNULL");

		NonnullParent parent = new NonnullParent();

		ObjectGraphs.builder()
				.module(new StringNullableModule())
				.build()
				.inject(parent);
	}
}
