package spork.injection.test;

import org.junit.Test;

import javax.annotation.Nullable;
import javax.inject.Inject;

import spork.Spork;
import spork.exceptions.BindException;
import spork.injection.test.modules.StringNullModule;

import static org.junit.Assert.assertNull;

public class NullableTests {
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
		Spork.bind(parent, new StringNullModule());
		assertNull(parent.string);
	}

	@Test(expected = BindException.class)
	public void nullValueWithoutNullable() {
		BadParent parent = new BadParent();
		Spork.bind(parent, new StringNullModule());
	}
}
