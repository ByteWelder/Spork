package io.github.sporklibrary.test.inject;

import org.junit.Test;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.Inject;
import io.github.sporklibrary.annotations.Nullable;
import io.github.sporklibrary.exceptions.BindException;
import io.github.sporklibrary.test.inject.domain.StringNullModule;

import static org.junit.Assert.assertNull;

public class NullableTests {
	private static class GoodParent {
		@Inject @Nullable String string;
	}

	private static class BadParent {
		@Inject String string;
	}

	@Test
	public void nullValueWithNullable() {
		GoodParent parent = new GoodParent();
		Spork.bind(parent, new StringNullModule());
		assertNull(parent.string);
	}

	@Test(expected = BindException.class)
	public void nullValueWithoutNullable() {
		BadParent parent = new BadParent();
		Spork.bind(parent, new StringNullModule());
	}
}
