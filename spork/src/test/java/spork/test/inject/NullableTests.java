package spork.test.inject;

import org.junit.Test;

import spork.Spork;
import spork.annotations.Inject;
import spork.annotations.Nullable;
import spork.exceptions.BindException;
import spork.test.inject.domain.StringNullModule;

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
